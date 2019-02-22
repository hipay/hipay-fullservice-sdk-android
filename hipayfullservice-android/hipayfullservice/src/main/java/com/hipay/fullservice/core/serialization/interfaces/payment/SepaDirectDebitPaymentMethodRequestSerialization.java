package com.hipay.fullservice.core.serialization.interfaces.payment;

import android.os.Bundle;

import com.hipay.fullservice.core.requests.payment.CardTokenPaymentMethodRequest;
import com.hipay.fullservice.core.requests.payment.SepaDirectDebitPaymentMethodRequest;
import com.hipay.fullservice.core.serialization.interfaces.AbstractSerialization;
import com.hipay.fullservice.core.utils.Utils;

import java.util.HashMap;
import java.util.Map;

public class SepaDirectDebitPaymentMethodRequestSerialization  extends AbstractSerialization  {

    public SepaDirectDebitPaymentMethodRequestSerialization(SepaDirectDebitPaymentMethodRequest sepaDirectDebitPaymentMethodRequest ) {
        super(sepaDirectDebitPaymentMethodRequest);
    }

    public Map<String, String> getSerializedRequest() {

        Map<String, String> json = new HashMap<>();
        SepaDirectDebitPaymentMethodRequest sepaDirectDebitPaymentMethodRequest = (SepaDirectDebitPaymentMethodRequest)this.getModel();

        json.put("firstname", sepaDirectDebitPaymentMethodRequest.getFirstname());
        json.put("lastname", sepaDirectDebitPaymentMethodRequest.getLastname());
        json.put("iban", sepaDirectDebitPaymentMethodRequest.getIban());

        if (sepaDirectDebitPaymentMethodRequest.getRecurringPayment() != null) {
            json.put("recurring_payment", String.valueOf(sepaDirectDebitPaymentMethodRequest.getRecurringPayment()));
        }

        return json;
    }

    @Override
    public Bundle getSerializedBundle() {
        return null;
    }

    public String getQueryString() {

        return Utils.queryStringFromMap(this.getSerializedRequest());
    }
}
