package com.hipay.fullservice.core.serialization.interfaces.order;

import android.os.Bundle;

import com.hipay.fullservice.core.models.Order;
import com.hipay.fullservice.core.serialization.interfaces.PersonalInformationSerialization;
import com.hipay.fullservice.core.utils.enums.Gender;

/**
 * Created by nfillion on 08/09/16.
 */

public class OrderSerialization extends PersonalInformationSerialization {

    public OrderSerialization(Order order) {
        super(order);
    }

    @Override
    public Bundle getSerializedBundle() {
        super.getSerializedBundle();

        Order order = (Order)this.getModel();

        this.bundle.putString("currency", order.getCurrency());
        this.bundle.putString("customerId", order.getCustomerId());
        this.bundle.putString("language", order.getLanguage());
        this.bundle.putString("id", order.getOrderId());
        this.bundle.putInt("attempts", order.getAttemps());
        this.bundle.putFloat("amount", order.getAmount());
        this.bundle.putFloat("shipping", order.getShipping());
        this.bundle.putFloat("tax", order.getTax());
        this.bundle.putInt("decimals", order.getDecimals());

        Gender gender = order.getGender();
        if (gender != null) {
            this.bundle.putString("gender", Character.toString(gender.getValue()));
        }

        this.bundle.putDate("dateCreated", order.getDateCreated());

        return this.getBundle();
    }

}
