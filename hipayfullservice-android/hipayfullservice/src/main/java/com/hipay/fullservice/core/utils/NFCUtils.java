package com.hipay.fullservice.core.utils;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NfcAdapter;
import android.nfc.tech.IsoDep;

/**
 * Created by nfillion on 01/02/17.
 */

public class NFCUtils {

    /**
     * Intent filter
     */
    private static final IntentFilter[] INTENT_FILTER = new IntentFilter[] { new IntentFilter(NfcAdapter.ACTION_TECH_DISCOVERED) };

    /**
     * Tech List
     */
    private static final String[][] TECH_LIST = new String[][] { { IsoDep.class.getName() } };

    public static NfcAdapter getNfcAdapter(final Context pContext) {

        return NfcAdapter.getDefaultAdapter(pContext);
    }

	public static boolean isNfcAvailable(final Context pContext) {

		NfcAdapter adapter = NfcAdapter.getDefaultAdapter(pContext);
        if (adapter != null)
        {
            return true;
        }

        return false;
	}
	
	public static boolean isNfcEnabled(final Context pContext) {

        if (isNfcAvailable(pContext))
        {
            NfcAdapter adapter = getNfcAdapter(pContext);
            return adapter.isEnabled();
        }

        return false;
	}


	/**
	 * Disable dispacher Remove the most important priority for foreground application
	 */
	public void disableDispatch(Activity activity) {

        if (isNfcEnabled(activity)) {

            NfcAdapter adapter = getNfcAdapter(activity);
            adapter.disableForegroundDispatch(activity);
        }
	}

	/**
	 * Activate NFC dispacher to read NFC Card Set the most important priority to the foreground application
	 */
	public void enableDispatch(Activity activity) {

        if (isNfcEnabled(activity)) {

            NfcAdapter adapter = getNfcAdapter(activity);

            PendingIntent pendingIntent = PendingIntent.getActivity(
                    activity,
                    0,
                    new Intent(activity, activity.getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP),
                    0);
            adapter.enableForegroundDispatch(activity, pendingIntent, INTENT_FILTER, TECH_LIST);
        }
	}
}
