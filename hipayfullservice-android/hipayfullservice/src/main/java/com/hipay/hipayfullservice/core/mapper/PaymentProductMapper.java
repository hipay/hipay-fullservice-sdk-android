package com.hipay.hipayfullservice.core.mapper;

import com.hipay.hipayfullservice.core.mapper.interfaces.BundleMapper;
import com.hipay.hipayfullservice.core.mapper.interfaces.MapMapper;
import com.hipay.hipayfullservice.core.models.PaymentProduct;

/**
 * Created by nfillion on 08/09/16.
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
}

