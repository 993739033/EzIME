package com.scode.ezime.utils;

import android.content.Context;

/**
 * @Date 2022/11/17
 **/
public class DpUtil {
    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
       float mScale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * mScale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context,float pxValue) {
        float mScale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / mScale + 0.5f);
    }

}
