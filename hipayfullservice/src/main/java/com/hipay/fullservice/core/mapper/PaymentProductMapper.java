package com.hipay.fullservice.core.mapper;

import com.hipay.fullservice.core.mapper.interfaces.BundleMapper;
import com.hipay.fullservice.core.mapper.interfaces.MapMapper;
import com.hipay.fullservice.core.models.PaymentProduct;

/**
 * Created by HiPay on 08/09/16.
 */

public class PaymentProductMapper extends AbstractMapper {

    public PaymentProductMapper(Object rawData) {
        super(rawData);
    }

    @Override
    protected boolean isValid() {

        if (this.getBehaviour() instanceof MapMapper) {

            if (this.getStringForKey("code") != null) return true;

        } else if (getBehaviour() instanceof BundleMapper) {

            return true;
        }

        return true;
    }

    @Override
    public PaymentProduct mappedObject() {

        PaymentProduct object = new PaymentProduct();

        object.setPaymentProductId(this.getStringForKey("id"));
        object.setCode(this.getStringForKey("code"));
        object.setPaymentProductDescription(this.getStringForKey("description"));
        object.setPaymentProductCategoryCode(this.getStringForKey("payment_product_category_code"));
        object.setTokenizable(this.getBoolForKey("tokenizable"));

        return object;
    }

    @Override
    public PaymentProduct mappedObjectFromBundle() {

        return this.mappedObject();
    }

    @Override
    protected Object mappedObjectFromUri() {
        return null;
    }
}

