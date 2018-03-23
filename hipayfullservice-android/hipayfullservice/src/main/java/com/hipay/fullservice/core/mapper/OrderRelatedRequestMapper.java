package com.hipay.fullservice.core.mapper;

import android.os.Bundle;

import com.hipay.fullservice.core.mapper.interfaces.BundleMapper;
import com.hipay.fullservice.core.requests.info.CustomerInfoRequest;
import com.hipay.fullservice.core.requests.info.PersonalInfoRequest;
import com.hipay.fullservice.core.requests.order.OrderRelatedRequest;
import com.hipay.fullservice.core.utils.enums.OrderRequestOperation;

/**
 * Created by nfillion on 22/03/2018.
 */

public class OrderRelatedRequestMapper extends AbstractMapper {

    public OrderRelatedRequestMapper(Object rawData) {
        super(rawData);
    }

    @Override
    public boolean isValid() {
        return this.getBehaviour() instanceof BundleMapper;
    }

    @Override
    public OrderRelatedRequest mappedObject() {
        OrderRelatedRequest orderRelatedRequest = new OrderRelatedRequest();

        orderRelatedRequest.setOrderId(this.getStringForKey("orderid"));
        String operationValue = this.getStringForKey("operation");
        if (operationValue != null) {
            orderRelatedRequest.setOperation(OrderRequestOperation.fromStringValue(operationValue));
        }

        orderRelatedRequest.setShortDescription(this.getStringForKey("description"));
        orderRelatedRequest.setLongDescription(this.getStringForKey("long_description"));
        orderRelatedRequest.setCurrency(this.getStringForKey("currency"));
        orderRelatedRequest.setAmount(this.getFloatForKey("amount"));

        orderRelatedRequest.setShipping(this.getFloatForKey("shipping"));
        orderRelatedRequest.setTax(this.getFloatForKey("tax"));

        orderRelatedRequest.setClientId(this.getStringForKey("cid"));
        orderRelatedRequest.setIpAddress(this.getStringForKey("ipaddr"));

        orderRelatedRequest.setHTTPAccept(this.getStringForKey("http_accept"));
        orderRelatedRequest.setHTTPUserAgent(this.getStringForKey("http_user_agent"));
        orderRelatedRequest.setDeviceFingerprint(this.getStringForKey("device_fingerprint"));
        orderRelatedRequest.setLanguage(this.getStringForKey("language"));

        orderRelatedRequest.setAcceptScheme(this.getStringForKey("accept_url"));
        orderRelatedRequest.setDeclineScheme(this.getStringForKey("decline_url"));
        orderRelatedRequest.setPendingScheme(this.getStringForKey("pending_url"));
        orderRelatedRequest.setExceptionScheme(this.getStringForKey("exception_url"));
        orderRelatedRequest.setCancelScheme(this.getStringForKey("cancel_url"));

        orderRelatedRequest.setCustomData(this.getMapJSONForKey("custom_data"));

        orderRelatedRequest.setCdata1(this.getStringForKey("cdata1"));
        orderRelatedRequest.setCdata2(this.getStringForKey("cdata2"));
        orderRelatedRequest.setCdata3(this.getStringForKey("cdata3"));
        orderRelatedRequest.setCdata4(this.getStringForKey("cdata4"));
        orderRelatedRequest.setCdata5(this.getStringForKey("cdata5"));
        orderRelatedRequest.setCdata6(this.getStringForKey("cdata6"));
        orderRelatedRequest.setCdata7(this.getStringForKey("cdata7"));
        orderRelatedRequest.setCdata8(this.getStringForKey("cdata8"));
        orderRelatedRequest.setCdata9(this.getStringForKey("cdata9"));
        orderRelatedRequest.setCdata10(this.getStringForKey("cdata10"));

        Bundle customerBundle = this.getBundleForKey("customer");
        CustomerInfoRequest customerInfoRequest = null;
        if (customerBundle != null) {
            customerInfoRequest = CustomerInfoRequest.fromBundle(customerBundle);
        }
        orderRelatedRequest.setCustomer(customerInfoRequest);

        Bundle shippingBundle = this.getBundleForKey("shipping_address");
        PersonalInfoRequest personalInfoRequest = null;
        if (shippingBundle != null) {
            personalInfoRequest = PersonalInfoRequest.fromBundle(shippingBundle);
        }
        orderRelatedRequest.setShippingAddress(personalInfoRequest);

        orderRelatedRequest.setSource(this.getMapJSONForKey("source"));

        return orderRelatedRequest;
    }
}
