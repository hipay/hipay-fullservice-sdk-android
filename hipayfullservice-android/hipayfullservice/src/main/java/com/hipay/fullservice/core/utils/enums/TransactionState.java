package com.hipay.fullservice.core.utils.enums;

/**
 * Created by nfillion on 23/03/2018.
 */
public enum TransactionState {

    TRANSACTION_STATE_ERROR("error"),
    TRANSACTION_STATE_COMPLETED("completed"),
    TRANSACTION_STATE_FORWARDING("forwarding"),
    TRANSACTION_STATE_PENDING("pending"),
    TRANSACTION_STATE_DECLINED("declined");

    protected final String state;

    TransactionState(String state) {
        this.state = state;
    }

    public static TransactionState fromStringValue(String value) {
        if (value != null) {
            for (TransactionState state : TransactionState.values()) {
                if (state.getValue().equalsIgnoreCase(value)) {
                    return state;
                }
            }
        }
        return null;
    }

    public String getValue() {
        return this.state;
    }
}
