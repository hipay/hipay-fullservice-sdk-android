package com.hipay.fullservice.core.utils.enums;

/**
 * Created by nfillion on 25/01/16.
 */
public enum ThreeDSecureEnrollmentStatus {

    THREED_SECURE_ENROLLMENT_STATUS_UNKNOWN(' '),
    THREED_SECURE_ENROLLMENT_STATUS_AVAILABLE('Y'),
    THREED_SECURE_ENROLLMENT_STATUS_CARDHOLDERNOTENROLLED('N'),
    THREED_SECURE_ENROLLMENT_STATUS_UNABLETOAUTHENTICATE('U'),
    THREED_SECURE_ENROLLMENT_STATUS_OTHERERROR('E');

    protected final char status;

    ThreeDSecureEnrollmentStatus(char status) {

        this.status = status;
    }

    public static ThreeDSecureEnrollmentStatus fromStringValue(String value) {
        if (value != null) {
            for (ThreeDSecureEnrollmentStatus state : ThreeDSecureEnrollmentStatus.values()) {
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
