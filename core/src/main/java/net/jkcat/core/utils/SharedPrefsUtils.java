package net.jkcat.core.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.google.gson.Gson;

import net.jkcat.core.base.BaseApplication;

/**
 * SharedPreferences工具类
 *
 * @author JokerCats
 */
public class SharedPrefsUtils {

    /**
     * 向SharedPreferences中写入Object类型数据
     *
     * @param value 值
     */
    public static void putValue(String key, Object value) {
        Editor sp = getEditor();
        // 将对象转换成Json
        sp.putString(key, new Gson().toJson(value));
        sp.commit();
    }

    /**
     * 向SharedPreferences中写入int类型数据
     *
     * @param key   键
     * @param value 值
     */
    public static void putValue(String key, int value) {
        Editor sp = getEditor();
        sp.putInt(key, value);
        sp.apply();
    }

    /**
     * 向SharedPreferences中写入boolean类型的数据
     *
     * @param key   键
     * @param value 值
     */
    public static void putValue(String key, boolean value) {
        Editor sp = getEditor();
        sp.putBoolean(key, value);
        sp.apply();
    }

    /**
     * 向SharedPreferences中写入String类型的数据
     *
     * @param key   键
     * @param value 值
     */
    public static void putValue(String key, String value) {
        Editor sp = getEditor();
        sp.putString(key, value);
        sp.apply();
    }

    /**
     * 向SharedPreferences中写入float类型的数据
     *
     * @param key   键
     * @param value 值
     */
    public static void putValue(String key, float value) {
        Editor sp = getEditor();
        sp.putFloat(key, value);
        sp.apply();
    }

    /**
     * 向SharedPreferences中写入long类型的数据
     *
     * @param key   键
     * @param value 值
     */
    public static void putValue(String key, long value) {
        Editor sp = getEditor();
        sp.putLong(key, value);
        sp.apply();
    }

    /**
     * 从SharedPreferences中读取int类型的数据
     *
     * @param key      键
     * @param defValue 如果读取不成功则使用默认值
     * @return 返回读取的值
     */
    public static int getValue(String key, int defValue) {
        SharedPreferences sp = getSharedPreferences();
        return sp.getInt(key, defValue);
    }

    /**
     * 从SharedPreferences中读取boolean类型的数据
     *
     * @param key      键
     * @param defValue 如果读取不成功则使用默认值
     * @return 返回读取的值
     */
    public static boolean getValue(String key, boolean defValue) {
        SharedPreferences sp = getSharedPreferences();
        return sp.getBoolean(key, defValue);
    }

    /**
     * 从SharedPreferences中读取String类型的数据
     *
     * @param key      键
     * @param defValue 如果读取不成功则使用默认值
     * @return 返回读取的值
     */
    public static String getValue(String key, String defValue) {
        SharedPreferences sp = getSharedPreferences();
        return sp.getString(key, defValue);
    }

    /**
     * 从SharedPreferences中读取float类型的数据
     *
     * @param key      键
     * @param defValue 如果读取不成功则使用默认值
     * @return 返回读取的值
     */
    public static float getValue(String key, float defValue) {
        SharedPreferences sp = getSharedPreferences();
        return sp.getFloat(key, defValue);
    }

    /**
     * 从SharedPreferences中读取long类型的数据
     *
     * @param key      键
     * @param defValue 如果读取不成功则使用默认值
     * @return 返回读取的值
     */
    public static long getValue(String key, long defValue) {
        SharedPreferences sp = getSharedPreferences();
        return sp.getLong(key, defValue);
    }

    /**
     * 获取Editor实例
     *
     * @return Editor
     */
    private static Editor getEditor() {
        return getSharedPreferences().edit();
    }


    /**
     * 获取SharedPreferences实例
     *
     * @return SharedPreferences
     */
    private static SharedPreferences getSharedPreferences() {
        return BaseApplication.getInstance().
                getSharedPreferences("JKCat", Context.MODE_PRIVATE);
    }

}
