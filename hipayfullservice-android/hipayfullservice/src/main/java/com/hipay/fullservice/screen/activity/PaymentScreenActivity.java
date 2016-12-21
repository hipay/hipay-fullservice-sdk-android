package com.hipay.fullservice.screen.activity;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;

import com.hipay.fullservice.core.requests.order.PaymentPageRequest;
import com.hipay.fullservice.screen.model.CustomTheme;

/**
 * Created by nfillion on 21/12/2016.
 */

public abstract class PaymentScreenActivity extends AppCompatActivity {

    public static void start(Activity activity, PaymentPageRequest paymentPageRequest, String signature, CustomTheme theme) {

        boolean isCardStorageEnabled = true;
        // check if we got some in database

        if (isCardStorageEnabled)
        {
            PaymentProductsActivity.start(activity, paymentPageRequest, signature, theme);

        } else
        {
            PaymentProductsActivity.start(activity, paymentPageRequest, signature, theme);
        }
    }
}
