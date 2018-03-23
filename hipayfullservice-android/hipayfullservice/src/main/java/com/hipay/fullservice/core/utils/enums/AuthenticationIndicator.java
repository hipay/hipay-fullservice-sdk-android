package com.hipay.fullservice.core.utils.enums;

/**
 * Created by nfillion on 23/03/2018.
 */
public enum AuthenticationIndicator {

    //UNDEFINED (-1),
    BYPASS(0),
    IFAVAILABLE(1),
    MANDATORY(2);

    protected final Integer indicator;

    AuthenticationIndicator(Integer indicator) {

        this.indicator = indicator;
    }

    public static AuthenticationIndicator fromIntegerValue(Integer value) {
        if (value != null) {
            for (AuthenticationIndicator state : AuthenticationIndicator.values()) {
                if (state.getValue() == value) {
                    return state;
                }
            }
        }
        return null;
    }

    public Integer getValue() {
        return this.indicator;
    }
}
