package com.hipay.fullservice.core.utils.enums;

import com.hipay.fullservice.core.models.FraudScreening;

/**
 * Created by nfillion on 23/03/2018.
 */
public enum FraudScreeningResult {

    FRAUDSCREENINGRESULT_UNKNOWN("unknown"),
    FRAUDSCREENINGRESULT_PENDING("pending"),
    FRAUDSCREENINGRESULT_ACCEPTED("accepted"),
    FRAUDSCREENINGRESULT_BLOCKED("blocked"),
    FRAUDSCREENINGRESULT_CHALLENGED("challenged");

    protected final String result;

    FraudScreeningResult(String result) {
        this.result = result;
    }

    public String getValue() {
        return this.result;
    }

    public static FraudScreeningResult fromStringValue(String value) {
        if (value != null) {
            for (FraudScreeningResult state : FraudScreeningResult.values()) {
                if (state.getValue().equalsIgnoreCase(value)) {
                    return state;
                }
            }
        }
        return null;
    }
}
