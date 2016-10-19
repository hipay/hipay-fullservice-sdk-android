package com.hipay.fullservice.core.serialization.interfaces;

import android.os.Bundle;

import com.hipay.fullservice.core.models.PaymentCardToken;
import com.hipay.fullservice.core.models.PaymentProduct;

import java.util.Map;

/**
 * Created by nfillion on 08/09/16.
 */

public class PaymentCardTokenSerialization extends AbstractSerialization {

    public PaymentCardTokenSerialization(PaymentCardToken paymentCardToken) {
        super(paymentCardToken);
    }

    @Override
    public Map<String, String> getSerializedRequest() {
        return null;
    }

    @Override
    public Bundle getSerializedBundle() {
        super.getSerializedBundle();

        PaymentCardToken paymentCardToken = (PaymentCardToken)this.getModel();

        this.putStringForKey("token", paymentCardToken.getToken());
        this.putStringForKey("request_id", paymentCardToken.getRequestID());
        this.putStringForKey("pan", paymentCardToken.getPan());
        this.putStringForKey("brand", paymentCardToken.getBrand());
        this.putStringForKey("card_holder", paymentCardToken.getCardHolder());
        this.putIntForKey("card_expiry_month", paymentCardToken.getCardExpiryMonth());
        this.putIntForKey("card_expiry_year", paymentCardToken.getCardExpiryYear());
        this.putStringForKey("issuer", paymentCardToken.getIssuer());
        this.putStringForKey("country", paymentCardToken.getCountry());
        this.putStringForKey("domesticNetwork", paymentCardToken.getDomesticNetwork());

        return this.getBundle();
    }

    @Override
    public String getQueryString() {
        return null;
    }
}

