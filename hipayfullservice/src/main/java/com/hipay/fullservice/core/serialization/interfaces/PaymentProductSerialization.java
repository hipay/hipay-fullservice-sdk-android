package com.hipay.fullservice.core.serialization.interfaces;

import android.os.Bundle;

import com.hipay.fullservice.core.models.PaymentProduct;

import java.util.Map;

/**
 * Created by HiPay on 08/09/16.
 */

public class PaymentProductSerialization extends AbstractSerialization {

    public PaymentProductSerialization(PaymentProduct paymentProduct) {
        super(paymentProduct);
    }

    @Override
    public Map<String, String> getSerializedRequest() {
        return null;
    }

    @Override
    public Bundle getSerializedBundle() {
        super.getSerializedBundle();

        PaymentProduct paymentProduct = (PaymentProduct)this.getModel();

        this.putStringForKey("id", paymentProduct.getPaymentProductId());
        this.putStringForKey("code", paymentProduct.getCode());
        this.putStringForKey("description", paymentProduct.getPaymentProductDescription());
        this.putStringForKey("payment_product_category_code", paymentProduct.getPaymentProductCategoryCode());
        this.putBoolForKey("tokenizable", paymentProduct.isTokenizable());

        return this.getBundle();
    }

    @Override
    public String getQueryString() {
        return null;
    }
}
