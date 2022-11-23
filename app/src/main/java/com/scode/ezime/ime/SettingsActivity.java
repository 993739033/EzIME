/*
 * Copyright (C) 2009 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.scode.ezime.ime;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.os.Handler;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceGroup;
import android.preference.PreferenceManager;
import android.preference.PreferenceScreen;
//import android.provider.Settings;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.scode.ezime.R;
import com.scode.ezime.utils.ImeUtil;
import com.scode.ezime.view.ImeOption;

import java.util.List;

/**
 * Setting activity of Pinyin IME.
 */
public class SettingsActivity extends PreferenceActivity implements
        Preference.OnPreferenceChangeListener {

    private static String TAG = "SettingsActivity";

    private CheckBoxPreference mKeySoundPref;
    private CheckBoxPreference mVibratePref;
    private CheckBoxPreference mPredictionPref;
    private Preference imeOption;
    private ImeOption sizeOption;
    private boolean toNextOption = false;//是否自动弹出设置输入法


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.settings);

        PreferenceScreen prefSet = getPreferenceScreen();

        mKeySoundPref = (CheckBoxPreference) prefSet
                .findPreference(getString(R.string.setting_sound_key));
        mVibratePref = (CheckBoxPreference) prefSet
                .findPreference(getString(R.string.setting_vibrate_key));
        mPredictionPref = (CheckBoxPreference) prefSet
                .findPreference(getString(R.string.setting_prediction_key));
        imeOption = prefSet
                .findPreference("ime_option");

        prefSet.setOnPreferenceChangeListener(this);

        Settings.getInstance(PreferenceManager
                .getDefaultSharedPreferences(getApplicationContext()));

        updatePreference(prefSet, getString(R.string.setting_advanced_key));
        updateWidgets();
    }


    //开启输入
    public void initImeOption() {
        List<String> imeList = ImeUtil.getEnableImeList(SettingsActivity.this);
        String defalutIme = ImeUtil.getDefaultInputMethodPkgName(SettingsActivity.this);
        if (!imeList.contains(getPackageName())) {
            toNextOption = false;
            imeOption.setTitle("点击开启输入法");
            imeOption.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                @Override
                public boolean onPreferenceClick(Preference preference) {
                    toNextOption = true;
                    Intent intent = new Intent();
                    intent.setAction(android.provider.Settings.ACTION_INPUT_METHOD_SETTINGS);
                    startActivity(intent);
                    Toast.makeText(SettingsActivity.this, "请开启" + getString(R.string.ime_name), Toast.LENGTH_SHORT).show();
                    return true;
                }
            });
        } else if (!defalutIme.contains(getPackageName())) {
            imeOption.setTitle("点击设置输入法");
            if (toNextOption) {
                new Handler().postDelayed(() -> {
                    ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).showInputMethodPicker();
                    Toast.makeText(SettingsActivity.this, "请设置" + getString(R.string.ime_name) + "为默认输入法!", Toast.LENGTH_SHORT).show();
                }, 500);
            }
            imeOption.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                @Override
                public boolean onPreferenceClick(Preference preference) {
                    String defalutIme = ImeUtil.getDefaultInputMethodPkgName(SettingsActivity.this);
                    if (defalutIme.contains(getPackageName())) {
                        imeOption.setTitle("输入法已开启!");
                        Toast.makeText(SettingsActivity.this, "输入法已开启!", Toast.LENGTH_SHORT).show();
                        return true;
                    }
                    ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).showInputMethodPicker();
                    Toast.makeText(SettingsActivity.this, "请设置" + getString(R.string.ime_name) + "为默认输入法!", Toast.LENGTH_SHORT).show();
                    return true;
                }
            });
        } else {
            toNextOption = false;
            imeOption.setTitle("输入法已开启");
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateWidgets();
        initImeOption();
    }

    private void reStartImeService() {
        try {
            PinyinIME.getInstance().resetView();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        Settings.releaseInstance();
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Settings.setKeySound(mKeySoundPref.isChecked());
        Settings.setVibrate(mVibratePref.isChecked());
        Settings.setPrediction(mPredictionPref.isChecked());
        Settings.setAnchorX(-1);
        Settings.setAnchorY(-1);
        Settings.writeBack();
        Environment.getInstance().onConfigurationChanged(getResources().getConfiguration(), this, true);
        reStartImeService();
    }

    public boolean onPreferenceChange(Preference preference, Object newValue) {
        return true;
    }

    private void updateWidgets() {
        mKeySoundPref.setChecked(Settings.getKeySound());
        mVibratePref.setChecked(Settings.getVibrate());
        mPredictionPref.setChecked(Settings.getPrediction());
    }

    public void updatePreference(PreferenceGroup parentPref, String prefKey) {
        Preference preference = parentPref.findPreference(prefKey);
        if (preference == null) {
            return;
        }
        Intent intent = preference.getIntent();
        if (intent != null) {
            PackageManager pm = getPackageManager();
            List<ResolveInfo> list = pm.queryIntentActivities(intent, 0);
            int listSize = list.size();
            if (listSize == 0)
                parentPref.removePreference(preference);
        }
    }


}
