package com.hipay.fullservice.core.client;

import android.content.Context;
import android.os.AsyncTask;

import com.hipay.fullservice.core.client.interfaces.callbacks.SecureVaultRequestCallback;
import com.hipay.fullservice.core.client.interfaces.callbacks.UpdatePaymentCardRequestCallback;
import com.hipay.fullservice.core.models.PaymentCardToken;
import com.hipay.fullservice.core.monitoring.CheckoutData;
import com.hipay.fullservice.core.monitoring.CheckoutDataNetwork;
import com.hipay.fullservice.core.monitoring.Monitoring;
import com.hipay.fullservice.core.requests.securevault.GenerateTokenRequest;
import com.hipay.fullservice.core.requests.securevault.UpdatePaymentCardRequest;

import java.util.Date;

/**
 * Created by HiPay on 08/03/16.
 */
public class SecureVaultClient extends AbstractClient {

    public SecureVaultClient(Context context) {
        super(context);
    }

    public void generateToken(String cardNumber,
                              String cardExpiryMonth,
                              String cardExpiryYear,
                              String cardHolder,
                              String cardCVV,
                              boolean multiUse,
                              final SecureVaultRequestCallback callback

    ) {

        GenerateTokenRequest generateTokenRequest = new GenerateTokenRequest(
                cardNumber,
                cardExpiryMonth,
                cardExpiryYear,
                cardHolder,
                cardCVV,
                multiUse);

        super.createRequest(generateTokenRequest, new SecureVaultRequestCallback() {
            @Override
            public void onSuccess(PaymentCardToken paymentCardToken) {

                if (CheckoutData.checkoutData == null) {
                    CheckoutData.checkoutData = new CheckoutData();
                }

                CheckoutData.checkoutData.setEvent(CheckoutData.Event.tokenize);
                CheckoutData.checkoutData.setPaymentMethod(paymentCardToken.getBrand().toLowerCase());
                CheckoutData.checkoutData.setCardCountry(paymentCardToken.getCountry());

                Monitoring monitoring = new Monitoring();
                monitoring.setPayDate(new Date());
                CheckoutData.checkoutData.setMonitoring(monitoring);

                AsyncTask<CheckoutData, Void, Integer> task = new CheckoutDataNetwork().execute(CheckoutData.checkoutData);

                callback.onSuccess(paymentCardToken);
            }

            @Override
            public void onError(Exception error) {
                callback.onError(error);
            }
        });

    }

    public void updatePaymentCard(String token,
                                  String requestId,
                                  String cardExpiryMonth,
                                  String cardExpiryYear,
                                  String cardHolder,
                                  UpdatePaymentCardRequestCallback callback

    ) {

        UpdatePaymentCardRequest updatePaymentCardRequest = new UpdatePaymentCardRequest(
                token,
                requestId,
                cardExpiryMonth,
                cardExpiryYear,
                cardHolder);

        super.createRequest(updatePaymentCardRequest, callback);
    }
}
