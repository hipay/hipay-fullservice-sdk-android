package com.hipay.hipayfullservice.core.serialization.interfaces.order;

import android.os.Bundle;
import android.text.TextUtils;

import com.hipay.hipayfullservice.core.models.Transaction;
import com.hipay.hipayfullservice.core.requests.order.PaymentPageRequest;
import com.hipay.hipayfullservice.core.requests.payment.CardTokenPaymentMethodRequest;
import com.hipay.hipayfullservice.core.utils.Utils;

import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by nfillion on 04/02/16.
 */
public class PaymentPageRequestSerialization extends OrderRelatedRequestSerialization {

    public PaymentPageRequestSerialization(PaymentPageRequest paymentPageRequest) {

        super(paymentPageRequest);
    }

    @Override
    public Map<String, String> getSerializedRequest() {

        // get the OrderRelatedRequest serialization
        Map<String, String> relatedRequestMap = super.getSerializedRequest();

        PaymentPageRequest paymentPageRequest = (PaymentPageRequest)this.getModel();

        List<String> paymentProducts = paymentPageRequest.getPaymentProductList();
        if (paymentProducts != null) {
            relatedRequestMap.put("payment_product_list", TextUtils.join(",", paymentProducts));
        }

        List<String> paymentProductsCategory = paymentPageRequest.getPaymentProductCategoryList();
        if (paymentProductsCategory != null) {
            relatedRequestMap.put("payment_product_category_list", TextUtils.join(",", paymentProductsCategory));
        }

        Transaction.ECI eci = paymentPageRequest.getEci();
        if (eci != null && !eci.equals(Transaction.ECI.Undefined)) {
            relatedRequestMap.put("eci", String.valueOf(eci.getIntegerValue()));
        }

        CardTokenPaymentMethodRequest.AuthenticationIndicator authenticationIndicator = paymentPageRequest.getAuthenticationIndicator();
        if (authenticationIndicator != null) {
            relatedRequestMap.put("authentication_indicator", String.valueOf(authenticationIndicator.getIntegerValue()));
        }

        Boolean multiUseBoolean = paymentPageRequest.getMultiUse();
        if (multiUseBoolean != null) {
            relatedRequestMap.put("multi_use", multiUseBoolean == true ? "1" : "0");
        }

        Boolean displaySelectorBoolean = paymentPageRequest.getDisplaySelector();
        if (displaySelectorBoolean != null) {
            relatedRequestMap.put("display_selector", displaySelectorBoolean == true ? "1" : "0");
        }

        relatedRequestMap.put("template", paymentPageRequest.getTemplateName());

        URL cssUrl = paymentPageRequest.getCss();
        if (cssUrl != null) {
            relatedRequestMap.put("css", (cssUrl.toString()));
        }

        //card grouping is not sent to server

        //TODO handle the map better

        while (relatedRequestMap.values().remove(null));

        return relatedRequestMap;
    }

    @Override
    public Bundle getSerializedBundle() {
        super.getSerializedBundle();

        PaymentPageRequest paymentPageRequest = (PaymentPageRequest)this.getModel();

        List<String> paymentProducts = paymentPageRequest.getPaymentProductList();
        if (paymentProducts != null) {
            this.putStringForKey("payment_product_list", TextUtils.join(",", paymentProducts));
        }

        List<String> paymentProductsCategory = paymentPageRequest.getPaymentProductCategoryList();
        if (paymentProductsCategory != null) {
            this.putStringForKey("payment_product_category_list", TextUtils.join(",", paymentProductsCategory));
        }

        Transaction.ECI eci = paymentPageRequest.getEci();
        if (eci != null) {
            this.putIntForKey("eci", eci.getIntegerValue());
        }

        CardTokenPaymentMethodRequest.AuthenticationIndicator authenticationIndicator = paymentPageRequest.getAuthenticationIndicator();
        if (authenticationIndicator != null) {
            this.putIntForKey("authentication_indicator", authenticationIndicator.getIntegerValue());
        }

        this.putBoolForKey("multi_use", paymentPageRequest.getMultiUse());
        this.putBoolForKey("display_selector", paymentPageRequest.getDisplaySelector());

        this.putStringForKey("template", paymentPageRequest.getTemplateName());

        this.putUrlForKey("css", paymentPageRequest.getCss());

        this.putBoolForKey("card_grouping", paymentPageRequest.isPaymentCardGroupingEnabled());

        return this.getBundle();
    }

    public String getQueryString() {

        return Utils.queryStringFromMap(this.getSerializedRequest());
    }
}
