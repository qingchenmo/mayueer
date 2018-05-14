package com.jlkf.ego.utils;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.util.Log;

import java.util.Locale;

/**
 * @autor zcs
 * @time 2017/9/17
 * @describe 语言切换工具类
 */

public class LanguageUtils {
    public static final String CHINAESE = "chinaese";//中文
    public static final String ENGLISH = "english";//英文
    public static final String ESPAÑOL = "Español";//西班牙语
    public static final String ITALIANO = "Italiano";//意大利语
    public static final String FRANÇAIS = "Français";//法语


    public static void switchLanguage(Context context, String language) {
        Resources resources = context.getResources();
        Configuration configuration = resources.getConfiguration();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        switch (language) {
            case CHINAESE:
                configuration.locale = Locale.SIMPLIFIED_CHINESE;
                break;
            case ENGLISH:
                configuration.locale = Locale.ENGLISH;
                break;
            case ESPAÑOL:
                configuration.locale = new Locale("es");
                break;
            case ITALIANO:
                configuration.locale = Locale.ITALIAN;
                break;
            case FRANÇAIS:
                configuration.locale = Locale.FRENCH;
                break;
        }
//        configuration.locale = Locale.SIMPLIFIED_CHINESE;
        resources.updateConfiguration(configuration, metrics);
        ShardeUtil.putString("language", language);
        Log.e("tag", configuration.locale.getDisplayLanguage() + "-->" + configuration.locale.getDisplayName() + "--" + configuration.locale.getDisplayCountry() + "-->" + configuration.locale.getCountry());
        Log.e("tag", configuration.locale.getLanguage() + "\n" + configuration.locale.toString());
    }

    /**
     * 获取app语言
     *
     * @param context
     * @return
     */
    public static String getLanguage(Context context) {
        Resources resources = context.getResources();
        Configuration configuration = resources.getConfiguration();
        return configuration.locale.getLanguage();
    }
}
