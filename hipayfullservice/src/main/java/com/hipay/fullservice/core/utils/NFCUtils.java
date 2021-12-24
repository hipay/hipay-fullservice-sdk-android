package com.hipay.fullservice.core.utils;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NfcAdapter;
import android.nfc.tech.IsoDep;
import android.os.Build;

/**
 * Created by HiPay on 01/02/17.
 */

public class NFCUtils {

    public static NfcAdapter getNfcAdapter(final Context pContext) {

        return NfcAdapter.getDefaultAdapter(pContext);
    }

    public static boolean isNfcAvailable(final Context pContext) {

        NfcAdapter adapter = NfcAdapter.getDefaultAdapter(pContext);
        if (adapter != null) {
            return true;
        }

        return false;
    }

    public static boolean isNfcEnabled(final Context pContext) {

        if (isNfcAvailable(pContext)) {
            NfcAdapter adapter = getNfcAdapter(pContext);
            return adapter.isEnabled();
        }

        return false;
    }

    /**
     * Disable dispacher Remove the most important priority for foreground application
     */
    public static void disableDispatch(Activity activity) {

        if (isNfcEnabled(activity)) {

            NfcAdapter adapter = getNfcAdapter(activity);
            adapter.disableForegroundDispatch(activity);
        }
    }

    /**
     * Activate NFC dispacher to read NFC Card Set the most important priority to the foreground
     * application
     */
    public static void enableDispatch(Activity activity) {

        if (isNfcEnabled(activity)) {

            NfcAdapter adapter = getNfcAdapter(activity);

            PendingIntent pendingIntent = null;
            if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                pendingIntent = PendingIntent.getActivity
                        (activity,
                         0,
                         new Intent(activity,
                                    activity.getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP),
                         PendingIntent.FLAG_IMMUTABLE);
            } else {
                pendingIntent = PendingIntent.getActivity(
                        activity,
                        0,
                        new Intent(activity,
                                   activity.getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP),
                        0);

            }

            IntentFilter[] intentFilter =
                    new IntentFilter[]{new IntentFilter(NfcAdapter.ACTION_TECH_DISCOVERED)};
            String[][] techList = new String[][]{{IsoDep.class.getName()}};

            adapter.enableForegroundDispatch(activity,
                                             pendingIntent,
                                             intentFilter,
                                             techList);
        }
    }
}
