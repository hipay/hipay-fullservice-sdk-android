package com.hipay.fullservice.core.utils.enums;

/**
 * Created by nfillion on 23/03/2018.
 */
public enum Environment {

    STAGE(0),
    PRODUCTION(1);

    protected final Integer status;

    Environment(Integer status) {

        this.status = status;
    }

    public static Environment fromIntegerValue(Integer value) {
        if (value != null) {
            for (Environment state : Environment.values()) {
                if (state.getValue() == value) {
                    return state;
                }
            }
        }
        return null;
    }

    public Integer getValue() {
        return this.status;
    }
}
