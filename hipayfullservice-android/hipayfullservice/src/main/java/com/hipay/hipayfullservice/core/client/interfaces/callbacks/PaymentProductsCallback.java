package com.hipay.hipayfullservice.core.client.interfaces.callbacks;

import com.hipay.hipayfullservice.core.models.PaymentProduct;
import java.util.List;

/**
 * Created by nfillion on 28/06/16.
 */

public abstract class PaymentProductsCallback extends AbstractRequestCallback {
    public abstract void onSuccess(List<PaymentProduct> paymentProducts);
}
