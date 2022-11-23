package com.scode.ezime.utils;

/**
 * @Date 2022/9/16
 **/
public class ClassUtil {
    private static int originStackIndex = 4;

    public static String getFileName() {
        return Thread.currentThread().getStackTrace()[originStackIndex].getFileName();

    }

    public static String getClassName(int logIndex) {
        String name = Thread.currentThread().getStackTrace()[logIndex].getClassName();
        return name.substring(name.lastIndexOf(".") + 1);
    }

    public static String getClassName() {
        String name = Thread.currentThread().getStackTrace()[originStackIndex].getClassName();
        return name.substring(name.lastIndexOf(".") + 1);
    }

    public static String getMethodName() {
        return Thread.currentThread().getStackTrace()[originStackIndex].getMethodName();
    }

    public static String getMethodName(int logIndex) {
        return Thread.currentThread().getStackTrace()[logIndex].getMethodName();
    }

    public static int getLineNumber() {
        return Thread.currentThread().getStackTrace()[originStackIndex].getLineNumber();
    }

    public static int getLineNumber(int logIndex) {
        return Thread.currentThread().getStackTrace()[logIndex].getLineNumber();
    }
}
