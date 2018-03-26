package com.hipay.fullservice.core.serialization.interfaces;

import android.os.Bundle;

import com.hipay.fullservice.core.models.PaymentCardToken;

/**
 * Created by nfillion on 08/09/16.
 */

public class PaymentCardTokenSerialization extends AbstractSerialization {

    public PaymentCardTokenSerialization(PaymentCardToken paymentCardToken) {
        super(paymentCardToken);
    }

    @Override
    public Bundle getSerializedBundle() {
        PaymentCardToken paymentCardToken = (PaymentCardToken)this.getModel();

        this.bundle.putString("token", paymentCardToken.getToken());
        this.bundle.putString("request_id", paymentCardToken.getRequestID());
        this.bundle.putString("pan", paymentCardToken.getPan());
        this.bundle.putString("brand", paymentCardToken.getBrand());
        this.bundle.putString("card_holder", paymentCardToken.getCardHolder());
        this.bundle.putInt("card_expiry_month", paymentCardToken.getCardExpiryMonth());
        this.bundle.putInt("card_expiry_year", paymentCardToken.getCardExpiryYear());
        this.bundle.putString("issuer", paymentCardToken.getIssuer());
        this.bundle.putString("country", paymentCardToken.getCountry());
        this.bundle.putString("domesticNetwork", paymentCardToken.getDomesticNetwork());

        return this.bundle.getBundle();
    }

}

