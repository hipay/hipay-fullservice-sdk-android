package com.hipay.fullservice.core.serialization.interfaces.securevault;

import android.os.Bundle;

import com.hipay.fullservice.core.requests.securevault.GenerateTokenRequest;
import com.hipay.fullservice.core.serialization.interfaces.AbstractSerialization;
import com.hipay.fullservice.core.utils.Utils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by HiPay on 09/03/16.
 */
public class SecureVaultRequestSerialization extends AbstractSerialization {

    public SecureVaultRequestSerialization(GenerateTokenRequest generateTokenRequest) {

        super(generateTokenRequest);
    }

    public Map<String, String> getSerializedRequest() {

        GenerateTokenRequest generateTokenRequest = (GenerateTokenRequest)this.getModel();

        Map<String, String> retMap = new HashMap<>();
        retMap.put("card_number", generateTokenRequest.getCardNumber());
        retMap.put("card_expiry_month", generateTokenRequest.getCardExpiryMonth());
        retMap.put("card_expiry_year", generateTokenRequest.getCardExpiryYear());
        retMap.put("card_holder", generateTokenRequest.getCardHolder());
        retMap.put("cvc", generateTokenRequest.getCardCVC());
        retMap.put("multi_use", String.valueOf(generateTokenRequest.getMultiUse() ? 1 : 0) );

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
