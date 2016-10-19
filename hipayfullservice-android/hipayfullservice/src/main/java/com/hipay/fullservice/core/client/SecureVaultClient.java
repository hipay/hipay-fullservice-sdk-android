package com.hipay.fullservice.core.client;

import android.content.Context;

import com.hipay.fullservice.core.client.interfaces.callbacks.LookupPaymentCardRequestCallback;
import com.hipay.fullservice.core.client.interfaces.callbacks.SecureVaultRequestCallback;
import com.hipay.fullservice.core.client.interfaces.callbacks.UpdatePaymentCardRequestCallback;
import com.hipay.fullservice.core.requests.securevault.GenerateTokenRequest;
import com.hipay.fullservice.core.requests.securevault.LookupPaymentCardRequest;
import com.hipay.fullservice.core.requests.securevault.UpdatePaymentCardRequest;

/**
 * Created by nfillion on 08/03/16.
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
                              SecureVaultRequestCallback callback

    ) {

        GenerateTokenRequest generateTokenRequest = new GenerateTokenRequest(
                cardNumber,
                cardExpiryMonth,
                cardExpiryYear,
                cardHolder,
                cardCVV,
                multiUse);

        super.createRequest(generateTokenRequest, callback);

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

    public void lookupPaymentCard(String token,
                                  String requestId,
                                  LookupPaymentCardRequestCallback callback

    ) {

        LookupPaymentCardRequest lookupPaymentCardRequest = new LookupPaymentCardRequest(
                token,
                requestId);

        super.createRequest(lookupPaymentCardRequest, callback);
    }
}
