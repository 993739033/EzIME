package com.scode.ezime.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;

import com.scode.ezime.ime.Environment;

/**
 * @Date 2022/11/17
 **/
public class DragView extends LinearLayout {

    //最后触摸事件的x坐标。
    private int mXLast;
    //最后触摸事件的 y坐标。
    private int mYLast;

    private static final int MOVE_TOLERANCE = 3;

    private OnEventListener mOnEventListener;

    public static interface OnEventListener {
        public void onTouchMove(float x, float y);
    }

    public void setOnEventListener(OnEventListener l) {
        mOnEventListener = l;
    }

    public OnEventListener getOnEventListener() {
        return mOnEventListener;
    }

    public DragView(Context context) {
        super(context);
    }

    public DragView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public DragView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);
        int x = (int) event.getRawX();
        int y = (int) event.getRawY();

        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            mXLast = x;
            mYLast = y;
            showDragAnim(true);
            return true;
        }
        if (event.getAction() == MotionEvent.ACTION_MOVE) {
            if ((Math.abs(x - mXLast) > MOVE_TOLERANCE
                    || Math.abs(y - mYLast) > MOVE_TOLERANCE)) {
                int offX = x - mXLast;
                int offY = y - mYLast;
                mXLast = x;
                mYLast = y;
                if (mOnEventListener != null) {
                    mOnEventListener.onTouchMove(offX, offY);
                }
            }
            return true;
        }
        if (event.getAction() == MotionEvent.ACTION_UP) {
            showDragAnim(false);
            return true;
        }
        return true;
    }

    ValueAnimator mDragAnim;

    public void showDragAnim(boolean isTouch) {
        if (mDragAnim != null) {
            mDragAnim.cancel();
        }
        if (isTouch) {
            mDragAnim = ValueAnimator.ofInt(getMeasuredWidth(), (int) (getMeasuredWidth() + Environment.getInstance().getSKBWidth()*0.25f));
        } else {
            mDragAnim = ValueAnimator.ofInt(getMeasuredWidth(), (int) (Environment.getInstance().getSKBWidth() / 1.6f));
        }

        mDragAnim.setDuration(200);
        mDragAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) getLayoutParams();
                layoutParams.width = (int) animation.getAnimatedValue();
                setLayoutParams(layoutParams);
            }
        });
        mDragAnim.start();
    }
}
