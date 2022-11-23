package com.scode.ezime.utils;

import android.util.Log;

import com.scode.ezime.BuildConfig;


/**
 * @Date 2022/11/11
 **/
public class Loger {
    private static final String tag = "PinyinIME";
    private static final boolean logClassPath = true;//是否输入调用路径

    public static void i(String msg) {
        if (BuildConfig.DEBUG) {

            Log.i(tag, getClassPath() + msg);
        }
    }

    public static void e(String msg) {
        if (BuildConfig.DEBUG) {
            Log.e(tag, getClassPath() + msg);
        }
    }


    private static String getClassPath() {
        String classInfo = "";
        try {
            classInfo = "[" + ClassUtil.getClassName(5) + ">>" + ClassUtil.getLineNumber(5) + "]";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return classInfo;
    }
}
