package com.hipay.hipayfullservice.screen.helper;

/**
 * Created by nfillion on 08/03/16.
 */

import android.text.TextUtils;

/**
 * Easy storage and retrieval of preferences.
 */
public class FormHelper {

    private FormHelper() {
        //no instance
    }

    public static boolean isInputDataValid(CharSequence cardNumber, CharSequence cardExpiration, CharSequence cardCVV, CharSequence cardOwner) {
        return  !TextUtils.isEmpty(cardNumber) &&
                !TextUtils.isEmpty(cardExpiration) &&
                !TextUtils.isEmpty(cardCVV) &&
                !TextUtils.isEmpty(cardOwner);
    }
}
