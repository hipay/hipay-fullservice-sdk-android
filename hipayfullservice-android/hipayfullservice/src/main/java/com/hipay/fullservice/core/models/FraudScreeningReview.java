package com.hipay.fullservice.core.models;

/**
 * Created by nfillion on 23/03/2018.
 */
public enum FraudScreeningReview {

    FRAUDSCREENINGREVIEW_NONE("none"),
    FRAUDSCREENINGREVIEW_PENDING("pending"),
    FRAUDSCREENINGREVIEW_ALLOWED("allowed"),
    FRAUDSCREENINGREVIEW_DENIED("denied");

    protected final String review;

    FraudScreeningReview(String review) {
        this.review = review;
    }

    public String getStringValue() {
        return this.review;
    }

    public static FraudScreeningReview fromStringValue(String value) {

        if (value == null) return null;

        if (value.equalsIgnoreCase(FRAUDSCREENINGREVIEW_PENDING.getStringValue())) {
            return FRAUDSCREENINGREVIEW_PENDING;
        }

        if (value.equalsIgnoreCase(FRAUDSCREENINGREVIEW_ALLOWED.getStringValue())) {
            return FRAUDSCREENINGREVIEW_ALLOWED;
        }

        if (value.equalsIgnoreCase(FRAUDSCREENINGREVIEW_DENIED.getStringValue())) {
            return FRAUDSCREENINGREVIEW_DENIED;
        }

        return null;
    }
}
