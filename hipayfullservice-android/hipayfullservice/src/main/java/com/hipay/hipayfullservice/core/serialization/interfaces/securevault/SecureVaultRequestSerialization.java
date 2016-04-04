package com.hipay.hipayfullservice.core.serialization.interfaces.securevault;

import android.os.Bundle;

import com.hipay.hipayfullservice.core.requests.securevault.SecureVaultRequest;
import com.hipay.hipayfullservice.core.serialization.interfaces.AbstractSerialization;
import com.hipay.hipayfullservice.core.utils.Utils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by nfillion on 09/03/16.
 */
public class SecureVaultRequestSerialization extends AbstractSerialization {

    public SecureVaultRequestSerialization(SecureVaultRequest secureVaultRequest) {

        this.setRequest(secureVaultRequest);
    }

    public Map<String, String> getSerializedRequest() {

        SecureVaultRequest secureVaultRequest = (SecureVaultRequest)this.getRequest();

        Map<String, String> retMap = new HashMap<>();
        retMap.put("card_number", secureVaultRequest.getCardNumber());
        retMap.put("card_expiry_month", secureVaultRequest.getCardExpiryMonth());
        retMap.put("card_expiry_year", secureVaultRequest.getCardExpiryYear());
        retMap.put("card_holder", secureVaultRequest.getCardHolder());
        retMap.put("cvc", secureVaultRequest.getCardCVC());
        retMap.put("multi_use", String.valueOf(secureVaultRequest.getMultiUse() ? 1 : 0) );

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
