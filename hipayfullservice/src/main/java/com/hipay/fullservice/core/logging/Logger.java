package com.hipay.fullservice.core.logging;

import android.util.Log;

/**
 * Created by HiPay on 23/08/16.
 */

public class Logger {

    private static final boolean DEBUG = true;
    public static String TAG = "<HiPay>";

    public static int d(String string) {
        if(DEBUG) {
            return Log.d(TAG, string);
        }
        return -1;
    }

    public static int v(String string) {
        if(DEBUG) {
            return Log.v(TAG, string);
        }

        return -1;
    }

    public static int i(String string) {
        if(DEBUG) {
            return Log.i(TAG, string);
        }
        return -1;
    }

    public static int e(String string) {
        if(DEBUG) {
            return Log.e(TAG, string);
        }
        return -1;
    }

    public static int w(String string) {
        if(DEBUG) {
            return Log.w(TAG, string);
        }
        return -1;
    }
}
