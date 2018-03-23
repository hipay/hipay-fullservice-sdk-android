package com.hipay.fullservice.core.utils.enums;

/**
 * Created by nfillion on 23/03/2018.
 */
public enum AVSResult {

    AVS_RESULT_NOT_APPLICABLE(' '),
    AVS_RESULT_EXACT_MATCH('Y'),
    AVS_RESULT_ADDRESS_MATCH('A'),
    AVS_RESULT_POSTAL_CODE_MATCH('P'),
    AVS_RESULT_NO_MATCH('N'),
    AVS_RESULT_NOT_COMPATIBLE('C'),
    AVS_RESULT_NOT_ALLOWED('E'),
    AVS_RESULT_UNAVAILABLE('U'),
    AVS_RESULT_RETRY('R'),
    AVS_RESULT_NOT_SUPPORTED('S');

    protected final char result;

    AVSResult(char result) {

        this.result = result;
    }

    public static AVSResult fromStringValue(String value) {
        if (value != null) {
            for (AVSResult state : AVSResult.values()) {
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
