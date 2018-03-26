package com.hipay.fullservice.core.serialization.interfaces.order;

import android.os.Bundle;
import android.text.TextUtils;

import com.hipay.fullservice.core.requests.order.PaymentPageRequest;
import com.hipay.fullservice.core.utils.Utils;
import com.hipay.fullservice.core.utils.enums.AuthenticationIndicator;
import com.hipay.fullservice.core.utils.enums.ECI;

import java.net.URL;
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

        ECI eci = paymentPageRequest.getEci();
        if (eci != null && !eci.equals(ECI.UNDEFINED)) {
            relatedRequestMap.put("eci", String.valueOf(eci.getValue()));
        }

        AuthenticationIndicator authenticationIndicator = paymentPageRequest.getAuthenticationIndicator();
        if (authenticationIndicator != null) {
            relatedRequestMap.put("authentication_indicator", String.valueOf(authenticationIndicator.getValue()));
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

        //handle the map better, particularly with booleans.

        while (relatedRequestMap.values().remove(null));

        return relatedRequestMap;
    }

    @Override
    public Bundle getSerializedBundle() {
        super.getSerializedBundle();

        PaymentPageRequest paymentPageRequest = (PaymentPageRequest)this.getModel();

        List<String> paymentProducts = paymentPageRequest.getPaymentProductList();
        if (paymentProducts != null) {
            this.bundle.putString("payment_product_list", TextUtils.join(",", paymentProducts));
        }

        List<String> paymentProductsCategory = paymentPageRequest.getPaymentProductCategoryList();
        if (paymentProductsCategory != null) {
            this.bundle.putString("payment_product_category_list", TextUtils.join(",", paymentProductsCategory));
        }

        ECI eci = paymentPageRequest.getEci();
        if (eci != null) {
            this.bundle.putInt("eci", eci.getValue());
        }

        AuthenticationIndicator authenticationIndicator = paymentPageRequest.getAuthenticationIndicator();
        if (authenticationIndicator != null) {
            this.bundle.putInt("authentication_indicator", authenticationIndicator.getValue());
        }

        this.bundle.putBool("multi_use", paymentPageRequest.getMultiUse());
        this.bundle.putBool("display_selector", paymentPageRequest.getDisplaySelector());

        this.bundle.putString("template", paymentPageRequest.getTemplateName());

        this.bundle.putUrl("css", paymentPageRequest.getCss());

        this.bundle.putBool("card_grouping", paymentPageRequest.isPaymentCardGroupingEnabled());

        return this.getBundle();
    }

    @Override
    public String getQueryString() {
        return Utils.queryStringFromMap(this.getSerializedRequest());
    }
}
