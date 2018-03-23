package com.hipay.fullservice.core.utils.enums;

/**
 * Created by nfillion on 23/03/2018.
 */
public enum CVCResult {

    CVC_RESULT_NOT_APPLICABLE(' '),
    CVC_RESULT_MATCH('M'),
    CVC_RESULT_NO_MATCH('N'),
    CVC_RESULT_NOT_PROCESSED('P'),
    CVC_RESULT_MISSING('S'),
    CVC_RESULT_NOT_SUPPORTED('U');

    protected final char result;

    CVCResult(char result) {
        this.result = result;
    }

    public static CVCResult fromStringValue(String value) {
        if (value != null) {
            for (CVCResult state : CVCResult.values()) {
                if (state.getValue() == value.charAt(0)) {
                    return state;
                }
            }
        }
        return null;
    }

    public char getValue() {
        return this.result;
    }
}
