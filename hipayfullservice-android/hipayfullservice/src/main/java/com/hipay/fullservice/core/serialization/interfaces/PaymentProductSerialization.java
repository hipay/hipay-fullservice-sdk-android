package com.hipay.fullservice.core.serialization.interfaces;

import android.os.Bundle;

import com.hipay.fullservice.core.models.PaymentProduct;


/**
 * Created by nfillion on 08/09/16.
 */

public class PaymentProductSerialization extends AbstractSerialization {

    public PaymentProductSerialization(PaymentProduct paymentProduct) {
        super(paymentProduct);
    }

    @Override
    public Bundle getSerializedBundle() {
        PaymentProduct paymentProduct = (PaymentProduct)this.getModel();

        this.bundle.putString("id", paymentProduct.getPaymentProductId());
        this.bundle.putString("code", paymentProduct.getCode());
        this.bundle.putString("description", paymentProduct.getPaymentProductDescription());
        this.bundle.putString("payment_product_category_code", paymentProduct.getPaymentProductCategoryCode());
        this.bundle.putBool("tokenizable", paymentProduct.isTokenizable());

        return this.bundle.getBundle();
    }

}
