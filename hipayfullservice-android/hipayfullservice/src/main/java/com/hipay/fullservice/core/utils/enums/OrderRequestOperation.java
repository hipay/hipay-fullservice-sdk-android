package com.hipay.fullservice.core.utils.enums;

/**
 * Created by nfillion on 23/03/2018.
 */
public enum OrderRequestOperation {

    AUTHORIZATION("AUTHORIZATION"), SALE("SALE");

    protected final String operation;

    OrderRequestOperation(String operation) {
        this.operation = operation;
    }

    public static OrderRequestOperation fromStringValue(String value) {
        if (value != null) {
            for (OrderRequestOperation  state : OrderRequestOperation .values()) {
                if (state.getValue().equalsIgnoreCase(value)) {
                    return state;
                }
            }
        }
        return null;
    }

    public String getValue() {
        return this.operation;
    }
}
