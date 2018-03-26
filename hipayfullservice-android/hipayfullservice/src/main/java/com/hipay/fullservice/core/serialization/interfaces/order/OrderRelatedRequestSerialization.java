package com.hipay.fullservice.core.serialization.interfaces.order;

import android.os.Bundle;

import com.hipay.fullservice.core.requests.info.CustomerInfoRequest;
import com.hipay.fullservice.core.requests.info.PersonalInfoRequest;
import com.hipay.fullservice.core.requests.order.OrderRelatedRequest;
import com.hipay.fullservice.core.serialization.interfaces.AbstractSerialization;
import com.hipay.fullservice.core.utils.Utils;
import com.hipay.fullservice.core.utils.enums.OrderRequestOperation;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by nfillion on 04/02/16.
 */
public abstract class OrderRelatedRequestSerialization extends AbstractSerialization {

    public OrderRelatedRequestSerialization(OrderRelatedRequest orderRelatedRequest) {
        super(orderRelatedRequest);
    }

    @Override
    public Map<String, String> getSerializedRequest() {

        OrderRelatedRequest orderRelatedRequest = (OrderRelatedRequest)this.getModel();

        Map<String, String> retMap = new HashMap<>();

        retMap.put("orderid", orderRelatedRequest.getOrderId());

        OrderRequestOperation operation = orderRelatedRequest.getOperation();
        if (operation != null) {
            retMap.put("operation", operation.getValue());
        }

        retMap.put("description", orderRelatedRequest.getShortDescription());
        retMap.put("long_description", orderRelatedRequest.getLongDescription());
        retMap.put("currency", orderRelatedRequest.getCurrency());

        Float amount = orderRelatedRequest.getAmount();
        if (amount != null) {
            retMap.put("amount", String.valueOf(orderRelatedRequest.getAmount()));
        }

        Float shipping = orderRelatedRequest.getShipping();
        if (shipping != null) {
            retMap.put("shipping", String.valueOf(orderRelatedRequest.getShipping()));
        }

        Float tax = orderRelatedRequest.getTax();
        if (tax != null) {
            retMap.put("tax", String.valueOf(orderRelatedRequest.getTax()));
        }

        retMap.put("cid", orderRelatedRequest.getClientId());
        retMap.put("ipaddr", orderRelatedRequest.getIpAddress());

        retMap.put("http_accept", orderRelatedRequest.getHTTPAccept());
        retMap.put("http_user_agent", orderRelatedRequest.getHTTPUserAgent());
        retMap.put("device_fingerprint", orderRelatedRequest.getDeviceFingerprint());
        retMap.put("language", orderRelatedRequest.getLanguage());

        retMap.put("accept_url", orderRelatedRequest.getAcceptScheme());
        retMap.put("decline_url", orderRelatedRequest.getDeclineScheme());
        retMap.put("pending_url", orderRelatedRequest.getPendingScheme());
        retMap.put("exception_url", orderRelatedRequest.getExceptionScheme());
        retMap.put("cancel_url", orderRelatedRequest.getCancelScheme());

        String cdata = Utils.mapToJson(orderRelatedRequest.getCustomData());
        retMap.put("custom_data", cdata);

        retMap.put("cdata1", orderRelatedRequest.getCdata1());
        retMap.put("cdata2", orderRelatedRequest.getCdata2());
        retMap.put("cdata3", orderRelatedRequest.getCdata3());
        retMap.put("cdata4", orderRelatedRequest.getCdata4());
        retMap.put("cdata5", orderRelatedRequest.getCdata5());
        retMap.put("cdata6", orderRelatedRequest.getCdata6());
        retMap.put("cdata7", orderRelatedRequest.getCdata7());
        retMap.put("cdata8", orderRelatedRequest.getCdata8());
        retMap.put("cdata9", orderRelatedRequest.getCdata9());
        retMap.put("cdata10", orderRelatedRequest.getCdata10());

        CustomerInfoRequest customerInfoRequest = orderRelatedRequest.getCustomer();
        if (customerInfoRequest != null) {
            Map<String, String> customerInfoMap = customerInfoRequest.getSerializedObject();
            retMap.putAll(customerInfoMap);
        }

        PersonalInfoRequest personalInfoRequest = orderRelatedRequest.getShippingAddress();
        if (personalInfoRequest != null) {

            Map<String, String> personalInfoMap = personalInfoRequest.getSerializedObject();

            Map<String, String> shipToPersonalInfoMap = new HashMap<>(personalInfoMap.size());
            for (Map.Entry<String, String> entry : personalInfoMap.entrySet())
            {
                shipToPersonalInfoMap.put("shipto_" + entry.getKey(), entry.getValue());
            }

            retMap.putAll(shipToPersonalInfoMap);
        }

        String source = Utils.mapToJson(orderRelatedRequest.getSource());
        retMap.put("source", source);

        while (retMap.values().remove(null));

        return retMap;
    }

    @Override
    public Bundle getSerializedBundle() {
        OrderRelatedRequest orderRelatedRequest = (OrderRelatedRequest)this.getModel();

        this.bundle.putString("orderid", orderRelatedRequest.getOrderId());

        OrderRequestOperation operation = orderRelatedRequest.getOperation();
        if (operation != null) {
            this.bundle.putString("operation", operation.getValue());
        }

        this.bundle.putString("description", orderRelatedRequest.getShortDescription());
        this.bundle.putString("long_description", orderRelatedRequest.getLongDescription());
        this.bundle.putString("currency", orderRelatedRequest.getCurrency());
        this.bundle.putFloat("amount", orderRelatedRequest.getAmount());

        this.bundle.putFloat("shipping", orderRelatedRequest.getShipping());
        this.bundle.putFloat("tax", orderRelatedRequest.getTax());

        this.bundle.putString("cid", orderRelatedRequest.getClientId());
        this.bundle.putString("ipaddr", orderRelatedRequest.getIpAddress());

        this.bundle.putString("http_accept", orderRelatedRequest.getHTTPAccept());
        this.bundle.putString("http_user_agent", orderRelatedRequest.getHTTPUserAgent());
        this.bundle.putString("device_fingerprint", orderRelatedRequest.getDeviceFingerprint());
        this.bundle.putString("language", orderRelatedRequest.getLanguage());

        this.bundle.putString("accept_url", orderRelatedRequest.getAcceptScheme());
        this.bundle.putString("decline_url", orderRelatedRequest.getDeclineScheme());
        this.bundle.putString("pending_url", orderRelatedRequest.getPendingScheme());
        this.bundle.putString("exception_url", orderRelatedRequest.getExceptionScheme());
        this.bundle.putString("cancel_url", orderRelatedRequest.getCancelScheme());

        this.bundle.putMapJSON("custom_data", orderRelatedRequest.getCustomData());

        this.bundle.putString("cdata1", orderRelatedRequest.getCdata1());
        this.bundle.putString("cdata2", orderRelatedRequest.getCdata2());
        this.bundle.putString("cdata3", orderRelatedRequest.getCdata3());
        this.bundle.putString("cdata4", orderRelatedRequest.getCdata4());
        this.bundle.putString("cdata5", orderRelatedRequest.getCdata5());
        this.bundle.putString("cdata6", orderRelatedRequest.getCdata6());
        this.bundle.putString("cdata7", orderRelatedRequest.getCdata7());
        this.bundle.putString("cdata8", orderRelatedRequest.getCdata8());
        this.bundle.putString("cdata9", orderRelatedRequest.getCdata9());
        this.bundle.putString("cdata10", orderRelatedRequest.getCdata10());

        CustomerInfoRequest customerInfoRequest = orderRelatedRequest.getCustomer();
        Bundle customerInfoBundle = customerInfoRequest.toBundle();
        this.bundle.putBundle("customer", customerInfoBundle);

        PersonalInfoRequest personalInfoRequest = orderRelatedRequest.getShippingAddress();
        Bundle personalInfoBundle = personalInfoRequest.toBundle();
        this.bundle.putBundle("shipping_address", personalInfoBundle);

        this.bundle.putMapJSON("source", orderRelatedRequest.getSource());

        return this.getBundle();
    }
}
