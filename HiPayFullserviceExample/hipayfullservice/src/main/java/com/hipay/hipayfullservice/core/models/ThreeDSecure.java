package com.hipay.hipayfullservice.core.models;

/**
 * Created by nfillion on 25/01/16.
 */
public class ThreeDSecure {

    protected ThreeDSecureEnrollmentStatus enrollmentStatus;
    protected String enrollmentMessage;

    protected ThreeDSecureAuthenticationStatus authenticationStatus;
    protected String authenticationMessage;
    protected String authenticationToken;
    protected String xid;

    public ThreeDSecure() {
    }

    public enum ThreeDSecureEnrollmentStatus {

        ThreeDSecureEnrollmentStatusUnknown (' '),
        ThreeDSecureEnrollmentStatusAuthenticationAvailable ('Y'),
        ThreeDSecureEnrollmentStatusCardholderNotEnrolled ('N'),
        ThreeDSecureEnrollmentStatusUnableToAuthenticate ('U'),
        ThreeDSecureEnrollmentStatusOtherError ('E');

        protected final char status;
        ThreeDSecureEnrollmentStatus(char status) {
            this.status = status;
        }
    }

    public enum ThreeDSecureAuthenticationStatus {

        ThreeDSecureAuthenticationStatusUnknown (' '),
        ThreeDSecureAuthenticationStatusSuccessful ('Y'),
        ThreeDSecureAuthenticationStatusAttempted ('A'),
        ThreeDSecureAuthenticationStatusCouldNotBePerformed ('U'),
        ThreeDSecureAuthenticationStatusAuthenticationFailed ('N'),
        ThreeDSecureAuthenticationStatusOther ('E');

        protected final char status;
        ThreeDSecureAuthenticationStatus(char status) {
            this.status = status;
        }
    }



    public ThreeDSecureEnrollmentStatus getEnrollmentStatus() {
        return enrollmentStatus;
    }

    public void setEnrollmentStatus(ThreeDSecureEnrollmentStatus enrollmentStatus) {
        this.enrollmentStatus = enrollmentStatus;
    }

    public String getEnrollmentMessage() {
        return enrollmentMessage;
    }

    public void setEnrollmentMessage(String enrollmentMessage) {
        this.enrollmentMessage = enrollmentMessage;
    }

    public ThreeDSecureAuthenticationStatus getAuthenticationStatus() {
        return authenticationStatus;
    }

    public void setAuthenticationStatus(ThreeDSecureAuthenticationStatus authenticationStatus) {
        this.authenticationStatus = authenticationStatus;
    }

    public String getAuthenticationMessage() {
        return authenticationMessage;
    }

    public void setAuthenticationMessage(String authenticationMessage) {
        this.authenticationMessage = authenticationMessage;
    }

    public String getAuthenticationToken() {
        return authenticationToken;
    }

    public void setAuthenticationToken(String authenticationToken) {
        this.authenticationToken = authenticationToken;
    }

    public String getXid() {
        return xid;
    }

    public void setXid(String xid) {
        this.xid = xid;
    }

}
