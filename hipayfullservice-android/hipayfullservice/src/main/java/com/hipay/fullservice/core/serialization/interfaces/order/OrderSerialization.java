package com.hipay.fullservice.core.serialization.interfaces.order;

import android.os.Bundle;

import com.hipay.fullservice.core.models.Order;
import com.hipay.fullservice.core.serialization.interfaces.PersonalInformationSerialization;
import com.hipay.fullservice.core.utils.enums.Gender;

import java.util.Map;

/**
 * Created by nfillion on 08/09/16.
 */

public class OrderSerialization extends PersonalInformationSerialization {

    public OrderSerialization(Order order) {
        super(order);
    }

    @Override
    public Map<String, String> getSerializedRequest() {
        return null;
    }

    @Override
    public Bundle getSerializedBundle() {
        super.getSerializedBundle();

        Order order = (Order)this.getModel();

        this.putStringForKey("currency", order.getCurrency());
        this.putStringForKey("customerId", order.getCustomerId());
        this.putStringForKey("language", order.getLanguage());
        this.putStringForKey("id", order.getOrderId());
        this.putIntForKey("attempts", order.getAttemps());
        this.putFloatForKey("amount", order.getAmount());
        this.putFloatForKey("shipping", order.getShipping());
        this.putFloatForKey("tax", order.getTax());
        this.putIntForKey("decimals", order.getDecimals());

        Gender gender = order.getGender();
        if (gender != null) {
            this.putStringForKey("gender", Character.toString(gender.getValue()));
        }

        this.putDateForKey("dateCreated", order.getDateCreated());

        return this.getBundle();
    }

    @Override
    public String getQueryString() {
        return null;
    }
}
