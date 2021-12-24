package com.hipay.fullservice.screen.activity;

import android.app.Activity;

import androidx.appcompat.app.AppCompatActivity;

import com.hipay.fullservice.core.client.config.ClientConfig;
import com.hipay.fullservice.core.requests.order.PaymentPageRequest;
import com.hipay.fullservice.core.utils.PaymentCardTokenDatabase;
import com.hipay.fullservice.screen.model.CustomTheme;

import java.util.Set;

/**
 * Created by HiPay on 21/12/2016.
 */

public abstract class PaymentScreenActivity extends AppCompatActivity {

    public static void start(Activity activity, PaymentPageRequest paymentPageRequest, String signature, CustomTheme theme) {

        boolean startPaymentCardsScreen = false;

        if (ClientConfig.getInstance().isPaymentCardStorageEnabled())
        {
            Set<String> paymentCardTokens = PaymentCardTokenDatabase.getInstance().getPaymentCardTokens(activity, paymentPageRequest.getCurrency());
            if (paymentCardTokens != null && !paymentCardTokens.isEmpty())
            {
                startPaymentCardsScreen = true;
                PaymentCardsActivity.start(activity, paymentPageRequest, signature, theme);
            }
        }

        if (startPaymentCardsScreen == false)
        {
            PaymentProductsActivity.start(activity, paymentPageRequest, signature, theme);
        }
    }
}
