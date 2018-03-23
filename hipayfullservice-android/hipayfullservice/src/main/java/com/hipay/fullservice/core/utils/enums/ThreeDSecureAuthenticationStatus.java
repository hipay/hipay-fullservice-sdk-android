package com.hipay.fullservice.core.utils.enums;

/**
 * Created by nfillion on 25/01/16.
 */
public enum ThreeDSecureAuthenticationStatus {

    THREED_SECURE_AUTHENTICATION_STATUS_UNKNOWN(' '),
    THREED_SECURE_AUTHENTICATION_STATUS_SUCCESSFUL('Y'),
    THREED_SECURE_AUTHENTICATION_STATUS_ATTEMPTED('A'),
    THREED_SECURE_AUTHENTICATION_STATUS_COULDNOTBEPERFORMED('U'),
    THREED_SECURE_AUTHENTICATION_STATUS_AUTHENTICATIONFAILED('N'),
    THREED_SECURE_AUTHENTICATION_STATUS_OTHER('E');

    protected final char status;

    ThreeDSecureAuthenticationStatus(char status) {
        this.status = status;
    }

    public static ThreeDSecureAuthenticationStatus fromStringValue(String value) {
        if (value != null) {
            for (ThreeDSecureAuthenticationStatus state : ThreeDSecureAuthenticationStatus.values()) {
                if (state.getValue() == value.charAt(0)) {
                    return state;
                }
            }
        }
        return null;
    }

    public char getValue() {
        return this.status;
    }

}
