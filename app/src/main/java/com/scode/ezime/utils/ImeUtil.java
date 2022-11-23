package com.scode.ezime.utils;

import static android.content.Context.INPUT_METHOD_SERVICE;

import android.content.Context;
import android.text.TextUtils;
import android.view.inputmethod.InputMethodInfo;
import android.view.inputmethod.InputMethodManager;

import java.util.LinkedList;
import java.util.List;

/**
 * @Date 2022/11/16
 **/
public class ImeUtil {

    //获取开启的输入列表
    public static List<String> getEnableImeList(Context context) {
        InputMethodManager imeManager = (InputMethodManager) context.getSystemService(INPUT_METHOD_SERVICE);
        List<InputMethodInfo> InputMethods = imeManager.getEnabledInputMethodList();
        List<String> list = new LinkedList<>();
        for (InputMethodInfo info : InputMethods) {
            String label = info.loadLabel(context.getPackageManager()).toString();
            list.add(info.getPackageName());
        }
        return list;
    }

    //获取默认的输入
    public static String getDefaultInputMethodPkgName(Context context) {
        String mDefaultInputMethodPkg = null;
        String mDefaultInputMethodCls = android.provider.Settings.Secure.getString(
                context.getContentResolver(),
                android.provider.Settings.Secure.DEFAULT_INPUT_METHOD);
        //输入法类名信息
        if (!TextUtils.isEmpty(mDefaultInputMethodCls)) {
            //输入法包名
            mDefaultInputMethodPkg = mDefaultInputMethodCls.split("/")[0];
            Loger.i("当前输入法:" + mDefaultInputMethodCls);
        }
        return mDefaultInputMethodPkg;
    }
}
