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

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/**
 * Class used to maintain settings.
 */
public class Settings {
    private static final String ANDPY_CONFS_KEYSOUND_KEY = "Sound";
    private static final String ANDPY_CONFS_VIBRATE_KEY = "Vibrate";
    private static final String ANDPY_CONFS_PREDICTION_KEY = "Prediction";
    private static final String ANDPY_CONFS_SIZE_Ratio = "Size_Ratio";
    private static final String ANDPY_CONFS_ANCHOR_X = "Anchor_X";
    private static final String ANDPY_CONFS_ANCHOR_Y = "Anchor_Y";
    private static final String ANDPY_CONFS_KB_SHOW = "Kb_Show";

    private static boolean mKeySound;
    private static boolean mVibrate;
    private static boolean mPrediction;
    private static int mSizeRatio;
    private static int mAnchroX, mAnchroY;
    public static int mDragBarH = 22;//拖动栏高度 单位dp
    private static boolean KbShow = true;//是否展示键盘

    private static Settings mInstance = null;

    private static int mRefCount = 0;

    private static SharedPreferences mSharedPref = null;

    protected Settings(SharedPreferences pref) {
        mSharedPref = pref;
        initConfs();
    }

    public static Settings getInstance(SharedPreferences pref) {
        if (mInstance == null) {
            mInstance = new Settings(pref);
        }
        assert (pref == mSharedPref);
        mRefCount++;
        return mInstance;
    }

    public static void writeBack() {
        Editor editor = mSharedPref.edit();
        editor.putBoolean(ANDPY_CONFS_VIBRATE_KEY, mVibrate);
        editor.putBoolean(ANDPY_CONFS_KEYSOUND_KEY, mKeySound);
        editor.putBoolean(ANDPY_CONFS_PREDICTION_KEY, mPrediction);
        editor.putInt(ANDPY_CONFS_SIZE_Ratio, mSizeRatio);
        editor.putInt(ANDPY_CONFS_ANCHOR_X, mAnchroX);
        editor.putInt(ANDPY_CONFS_ANCHOR_Y, mAnchroY);
        editor.putBoolean(ANDPY_CONFS_KB_SHOW, KbShow);
        editor.commit();
    }

    public static void releaseInstance() {
        mRefCount--;
        if (mRefCount == 0) {
            mInstance = null;
        }
    }

    private void initConfs() {
        mKeySound = mSharedPref.getBoolean(ANDPY_CONFS_KEYSOUND_KEY, true);
        mVibrate = mSharedPref.getBoolean(ANDPY_CONFS_VIBRATE_KEY, false);
        mPrediction = mSharedPref.getBoolean(ANDPY_CONFS_PREDICTION_KEY, true);
        mSizeRatio = mSharedPref.getInt(ANDPY_CONFS_SIZE_Ratio, 60);
        mAnchroX = mSharedPref.getInt(ANDPY_CONFS_ANCHOR_X, -1);
        mAnchroY = mSharedPref.getInt(ANDPY_CONFS_ANCHOR_Y, -1);
        KbShow = mSharedPref.getBoolean(ANDPY_CONFS_KB_SHOW, true);
    }

    public static boolean getKeySound() {
        return mKeySound;
    }

    public static void setKeySound(boolean v) {
        if (mKeySound == v) return;
        mKeySound = v;
    }

    public static boolean getVibrate() {
        return mVibrate;
    }

    public static void setVibrate(boolean v) {
        if (mVibrate == v) return;
        mVibrate = v;
    }

    public static boolean getPrediction() {
        return mPrediction;
    }

    public static void setPrediction(boolean v) {
        if (mPrediction == v) return;
        mPrediction = v;
    }

    public static float getSizeRatioPercent() {
        return mSizeRatio/100f;
    }


    public static int getSizeRatio() {
        return mSizeRatio;
    }

    public static void setSizeRatio(int v) {
        if (mSizeRatio == v) return;
        mSizeRatio = v;
    }

    public static int getAnchorX(int screenW) {
        if (mAnchroX == -1) {
            mAnchroX = (int) (screenW * (1 - getSizeRatioPercent()) / 2);
        }
        return mAnchroX;
    }

    public static void setAnchorX(int v) {
        if (mAnchroX == v) return;
        mAnchroX = v;
    }

    public static int getAnchorY(int screenH) {
        if (mAnchroY == -1) {
            mAnchroY = screenH/2;
        }
        return mAnchroY;
    }

    public static void setAnchorY(int v) {
        if (mAnchroY == v) return;
        mAnchroY = v;
    }

    public static boolean isKbShow() {
        return KbShow;
    }

    public static void setKbShow(boolean kbShow) {
        KbShow = kbShow;
    }
}
