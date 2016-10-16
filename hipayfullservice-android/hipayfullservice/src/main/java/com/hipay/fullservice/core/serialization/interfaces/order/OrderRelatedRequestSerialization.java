package com.hipay.fullservice.core.serialization.interfaces.order;

import android.os.Bundle;

import com.hipay.fullservice.core.requests.info.CustomerInfoRequest;
import com.hipay.fullservice.core.requests.info.PersonalInfoRequest;
import com.hipay.fullservice.core.requests.order.OrderRelatedRequest;
import com.hipay.fullservice.core.serialization.interfaces.AbstractSerialization;

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

        OrderRelatedRequest.OrderRequestOperation operation = orderRelatedRequest.getOperation();
        if (operation != null) {
            retMap.put("operation", String.valueOf(operation.getIntegerValue()));
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

        //TODO custom data json object

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

        while (retMap.values().remove(null));

        return retMap;
    }

    @Override
    public Bundle getSerializedBundle() {

        super.getSerializedBundle();

        OrderRelatedRequest orderRelatedRequest = (OrderRelatedRequest)this.getModel();

        this.putStringForKey("orderid", orderRelatedRequest.getOrderId());

        OrderRelatedRequest.OrderRequestOperation operation = orderRelatedRequest.getOperation();
        if (operation != null) {
            this.putIntForKey("operation", operation.getIntegerValue());
        }

        this.putStringForKey("description", orderRelatedRequest.getShortDescription());
        this.putStringForKey("long_description", orderRelatedRequest.getLongDescription());
        this.putStringForKey("currency", orderRelatedRequest.getCurrency());
        this.putFloatForKey("amount",orderRelatedRequest.getAmount());

        this.putFloatForKey("shipping",orderRelatedRequest.getShipping());
        this.putFloatForKey("tax",orderRelatedRequest.getTax());

        this.putStringForKey("cid", orderRelatedRequest.getClientId());
        this.putStringForKey("ipaddr", orderRelatedRequest.getIpAddress());

        this.putStringForKey("http_accept", orderRelatedRequest.getHTTPAccept());
        this.putStringForKey("http_user_agent", orderRelatedRequest.getHTTPUserAgent());
        this.putStringForKey("device_fingerprint", orderRelatedRequest.getDeviceFingerprint());
        this.putStringForKey("language", orderRelatedRequest.getLanguage());

        this.putStringForKey("accept_url", orderRelatedRequest.getAcceptScheme());
        this.putStringForKey("decline_url", orderRelatedRequest.getDeclineScheme());
        this.putStringForKey("pending_url", orderRelatedRequest.getPendingScheme());
        this.putStringForKey("exception_url", orderRelatedRequest.getExceptionScheme());
        this.putStringForKey("cancel_url", orderRelatedRequest.getCancelScheme());

        //TODO custom data json object

        this.putStringForKey("cdata1", orderRelatedRequest.getCdata1());
        this.putStringForKey("cdata2", orderRelatedRequest.getCdata2());
        this.putStringForKey("cdata3", orderRelatedRequest.getCdata3());
        this.putStringForKey("cdata4", orderRelatedRequest.getCdata4());
        this.putStringForKey("cdata5", orderRelatedRequest.getCdata5());
        this.putStringForKey("cdata6", orderRelatedRequest.getCdata6());
        this.putStringForKey("cdata7", orderRelatedRequest.getCdata7());
        this.putStringForKey("cdata8", orderRelatedRequest.getCdata8());
        this.putStringForKey("cdata9", orderRelatedRequest.getCdata9());
        this.putStringForKey("cdata10", orderRelatedRequest.getCdata10());

        CustomerInfoRequest customerInfoRequest = orderRelatedRequest.getCustomer();
        Bundle customerInfoBundle = customerInfoRequest.toBundle();
        this.putBundleForKey("customer", customerInfoBundle);

        PersonalInfoRequest personalInfoRequest = orderRelatedRequest.getShippingAddress();
        Bundle personalInfoBundle = personalInfoRequest.toBundle();
        this.putBundleForKey("shipping_address", personalInfoBundle);

        return this.getBundle();
    }
}
