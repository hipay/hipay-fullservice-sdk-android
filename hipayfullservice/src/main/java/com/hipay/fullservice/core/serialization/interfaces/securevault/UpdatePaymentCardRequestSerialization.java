package com.hipay.fullservice.core.serialization.interfaces.securevault;

import android.os.Bundle;

import com.hipay.fullservice.core.requests.securevault.UpdatePaymentCardRequest;
import com.hipay.fullservice.core.serialization.interfaces.AbstractSerialization;
import com.hipay.fullservice.core.utils.Utils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by HiPay on 09/03/16.
 */
public class UpdatePaymentCardRequestSerialization extends AbstractSerialization {

    public UpdatePaymentCardRequestSerialization(UpdatePaymentCardRequest updatePaymentCardRequest) {

        super(updatePaymentCardRequest);
    }

    public Map<String, String> getSerializedRequest() {

        UpdatePaymentCardRequest updatePaymentCardRequest = (UpdatePaymentCardRequest) this.getModel();

        Map<String, String> retMap = new HashMap<>();
        retMap.put("request_id", updatePaymentCardRequest.getRequestId());
        retMap.put("card_token", updatePaymentCardRequest.getToken());
        retMap.put("card_expiry_month", updatePaymentCardRequest.getCardExpiryMonth());
        retMap.put("card_expiry_year", updatePaymentCardRequest.getCardExpiryYear());
        retMap.put("card_holder", updatePaymentCardRequest.getCardHolder());
        retMap.put("cvc", updatePaymentCardRequest.getSecurityCode());

        while (retMap.values().remove(null));

        return retMap;
    }

    @Override
    public Bundle getSerializedBundle() {
        return null;
    }

    public String getQueryString() {

        return Utils.queryStringFromMap(this.getSerializedRequest());
    }
}

