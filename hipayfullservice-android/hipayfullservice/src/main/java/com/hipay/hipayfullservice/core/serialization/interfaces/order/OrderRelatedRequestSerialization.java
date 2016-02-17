package com.hipay.hipayfullservice.core.serialization.interfaces.order;

import com.hipay.hipayfullservice.core.requests.info.CustomerInfoRequest;
import com.hipay.hipayfullservice.core.requests.order.OrderRelatedRequest;
import com.hipay.hipayfullservice.core.serialization.interfaces.AbstractRequestSerialization;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by nfillion on 04/02/16.
 */
public abstract class OrderRelatedRequestSerialization extends AbstractRequestSerialization {

    public Map<String, String> getSerializedRequest() {

        OrderRelatedRequest orderRelatedRequest = (OrderRelatedRequest)this.getRequest();

        Map<String, String> retMap = new HashMap<>();

        retMap.put("orderid", orderRelatedRequest.getOrderId());
        //TODO retMap.put("operation")

        OrderRelatedRequest.OrderRequestOperation operation = orderRelatedRequest.getOperation();

        //TODO change OrderRequestOperation from Integer to String
        if (operation != null) {
            retMap.put("operation", String.valueOf(operation.getIntegerValue()));
        }

        retMap.put("description", orderRelatedRequest.getShortDescription());
        retMap.put("long_description", orderRelatedRequest.getLongDescription());
        retMap.put("currency", orderRelatedRequest.getCurrency());
        retMap.put("amount", String.valueOf(orderRelatedRequest.getAmount()));
        //retMap.put("shipping", String.valueOf(orderRelatedRequest.getShipping()));
        //retMap.put("tax", String.valueOf(orderRelatedRequest.getTax()));
        retMap.put("cid", orderRelatedRequest.getClientId());
        retMap.put("ipaddr", orderRelatedRequest.getIpAddress());

        retMap.put("http_accept", orderRelatedRequest.getHTTPAccept());
        retMap.put("http_user_agent", orderRelatedRequest.getHTTPUserAgent());
        retMap.put("device_fingerprint", orderRelatedRequest.getDeviceFingerprint());
        retMap.put("language", orderRelatedRequest.getLanguage());

        URL acceptUrl = orderRelatedRequest.getAcceptURL();
        if (acceptUrl != null) {
            retMap.put("accept_url",(acceptUrl.toString()));
        }

        URL declineUrl = orderRelatedRequest.getDeclineURL();
        if (declineUrl != null) {
            retMap.put("decline_url",(declineUrl.toString()));
        }

        URL pendingUrl = orderRelatedRequest.getPendingURL();
        if (pendingUrl != null) {
            retMap.put("pending_url",(pendingUrl.toString()));
        }

        URL exceptionUrl = orderRelatedRequest.getExceptionURL();
        if (exceptionUrl != null) {
            retMap.put("exception_url", (exceptionUrl.toString()));
        }

        URL cancelUrl = orderRelatedRequest.getCancelURL();
        if (cancelUrl != null) {
            retMap.put("cancel_url", (cancelUrl.toString()));
        }

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
        Map<String, String> customerInfoMap = customerInfoRequest.getSerializedObject();
        retMap.putAll(customerInfoMap);

        //TODO check about personal info prefix.

        /*
        PersonalInfoRequest personalInfoRequest = orderRelatedRequest.getShippingAddress();
        Map<String, String> personalInfoMap = personalInfoRequest.getSerializedObject();
        retMap.putAll(personalInfoMap);
        */

        //TODO check if objects are removed
        while (retMap.values().remove(null));

        return retMap;
    }
}
