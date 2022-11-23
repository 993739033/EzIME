package com.scode.ezime.view;

import android.content.Context;
import android.preference.Preference;
import android.util.AttributeSet;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatSeekBar;

import com.scode.ezime.R;
import com.scode.ezime.ime.Settings;

import java.text.DecimalFormat;

/**
 * @Date 2022/11/16
 **/
public class ImeOption extends Preference {
    DecimalFormat decimalFormat = new DecimalFormat("##.#");

    public ImeOption(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public ImeOption(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onBindView(View view) {
        super.onBindView(view);
        AppCompatSeekBar sizeBar = view.findViewById(R.id.slide_ime_size);
        TextView tvSize = view.findViewById(R.id.tv_ime_size);
        sizeBar.setProgress(Settings.getSizeRatio());
        tvSize.setText(Settings.getSizeRatio() + "%");
        sizeBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                Settings.setSizeRatio(progress);
                tvSize.setText(progress + "%");
                Settings.writeBack();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
    }
}
