package com.hipay.fullservice.example;

import android.content.Context;
import android.content.SharedPreferences;

public class Preferences {

    final public static String SHARED_PREFERENCES_DEMO = "HiPay_demo";

    final public static String PREF_ENVIRONMENT_KEY = "PREF_ENVIRONMENT_KEY";


    final public static int STAGE = 0;
    final public static int PRODUCTION = 1;
    final public static int CUSTOM = 2;

    final public static String PREF_CUSTOM_USERNAME_KEY = "PREF_CUSTOM_USERNAME_KEY";
    final public static String PREF_CUSTOM_PASSWORD_KEY = "PREF_CUSTOM_PASSWORD_KEY";
    final public static String PREF_CUSTOM_IS_PRODUCTION_URL_KEY = "PREF_CUSTOM_IS_PRODUCTION_URL_KEY";

    final public static String PREF_LOCAL_SIGNATURE_ACTIVATION_KEY = "PREF_LOCAL_SIGNATURE_ACTIVATION_KEY";
    final public static String PREF_LOCAL_SIGNATURE_PASSWORD_KEY = "PREF_LOCAL_SIGNATURE_PASSWORD_KEY";

    public static boolean isStageEnvironment(Context context) {
        return getEnvironment(context) == STAGE;
    }

    public static boolean isProductionEnvironment(Context context) {
        return getEnvironment(context) == PRODUCTION;
    }

    public static boolean isCustomEnvironment(Context context) {
        return getEnvironment(context) == CUSTOM;
    }

    public static int getEnvironment(Context context) {
        return getEnvironmentInt(context, PREF_ENVIRONMENT_KEY);
    }

    public static void setEnvironment(Context context, int environment) {
        saveEnvironmentInt(context, PREF_ENVIRONMENT_KEY, environment);
    }

    public static String getCustomUsername(Context context) {
        return getEnvironmentString(context, PREF_CUSTOM_USERNAME_KEY);
    }

    public static void setCustomUsername(Context context, String value) {
        saveEnvironmentString(context, PREF_CUSTOM_USERNAME_KEY, value);
    }

    public static String getCustomPassword(Context context) {
        return getEnvironmentString(context, PREF_CUSTOM_PASSWORD_KEY);
    }

    public static void setCustomPassword(Context context, String value) {
        saveEnvironmentString(context, PREF_CUSTOM_PASSWORD_KEY, value);
    }

    public static boolean isProductionUrl(Context context) {
        return getEnvironmentBoolean(context, PREF_CUSTOM_IS_PRODUCTION_URL_KEY);
    }

    public static void setIsProductionUrl(Context context, boolean value) {
        saveEnvironmentBoolean(context, PREF_CUSTOM_IS_PRODUCTION_URL_KEY, value);
    }

    public static boolean isLocalSignature(Context context) {
        return getEnvironmentBoolean(context, PREF_LOCAL_SIGNATURE_ACTIVATION_KEY);
    }

    public static void setIsLocalSignature(Context context, boolean value) {
        saveEnvironmentBoolean(context, PREF_LOCAL_SIGNATURE_ACTIVATION_KEY, value);
    }

    public static String getLocalSignaturePassword(Context context) {
        return getEnvironmentString(context, PREF_LOCAL_SIGNATURE_PASSWORD_KEY);
    }

    public static void setLocalSignaturePassword(Context context, String value) {
        saveEnvironmentString(context, PREF_LOCAL_SIGNATURE_PASSWORD_KEY, value);
    }

    private static void saveEnvironmentString(Context context, String key, String value) {
        SharedPreferences sharedPref = context.getSharedPreferences(SHARED_PREFERENCES_DEMO, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(key, value);
        editor.commit();
    }

    private static void saveEnvironmentInt(Context context, String key, int value) {
        SharedPreferences sharedPref = context.getSharedPreferences(SHARED_PREFERENCES_DEMO, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt(key, value);
        editor.commit();
    }

    private static void saveEnvironmentBoolean(Context context, String key, boolean value) {
        SharedPreferences sharedPref = context.getSharedPreferences(SHARED_PREFERENCES_DEMO, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    private static String getEnvironmentString(Context context, String key) {
        SharedPreferences sharedPref = context.getSharedPreferences(SHARED_PREFERENCES_DEMO, Context.MODE_PRIVATE);
        return sharedPref.getString(key, null);
    }

    private static int getEnvironmentInt(Context context, String key) {
        SharedPreferences sharedPref = context.getSharedPreferences(SHARED_PREFERENCES_DEMO, Context.MODE_PRIVATE);
        return sharedPref.getInt(key, 0);
    }

    private static boolean getEnvironmentBoolean(Context context, String key) {
        SharedPreferences sharedPref = context.getSharedPreferences(SHARED_PREFERENCES_DEMO, Context.MODE_PRIVATE);
        return sharedPref.getBoolean(key, false);
    }


}
