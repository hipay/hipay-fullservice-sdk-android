package com.hipay.fullservice.core.models;

import android.os.Bundle;

import com.hipay.fullservice.core.mapper.ThreeDSecureMapper;
import com.hipay.fullservice.core.serialization.AbstractSerializationMapper;

import org.json.JSONObject;

/**
 * Created by nfillion on 25/01/16.
 */
public class ThreeDSecure extends AbstractModel {

    protected ThreeDSecureEnrollmentStatus enrollmentStatus;
    protected String enrollmentMessage;

    protected ThreeDSecureAuthenticationStatus authenticationStatus;
    protected String authenticationMessage;
    protected String authenticationToken;
    protected String xid;

    public ThreeDSecure() {
    }

    public static ThreeDSecure fromJSONObject(JSONObject object) {

        ThreeDSecureMapper mapper = new ThreeDSecureMapper(object);
        return mapper.mappedObject();
    }

    public static ThreeDSecure fromBundle(Bundle bundle) {

        ThreeDSecureMapper mapper = new ThreeDSecureMapper(bundle);
        return mapper.mappedObjectFromBundle();
    }

    public Bundle toBundle() {

        ThreeDSecure.ThreeDSecureSerializationMapper mapper = new ThreeDSecure.ThreeDSecureSerializationMapper(this);
        return mapper.getSerializedBundle();
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

        public static ThreeDSecureEnrollmentStatus fromStringValue(String value) {

            if (value == null) return null;

            char c = value.charAt(0);

            if (c == ThreeDSecureEnrollmentStatusAuthenticationAvailable.getCharValue()) {
                return ThreeDSecureEnrollmentStatusAuthenticationAvailable;
            }

            if (c == ThreeDSecureEnrollmentStatusCardholderNotEnrolled.getCharValue()) {
                return ThreeDSecureEnrollmentStatusCardholderNotEnrolled;
            }

            if (c == ThreeDSecureEnrollmentStatusUnableToAuthenticate.getCharValue()) {
                return ThreeDSecureEnrollmentStatusUnableToAuthenticate;
            }

            if (c == ThreeDSecureEnrollmentStatusOtherError.getCharValue()) {
                return ThreeDSecureEnrollmentStatusOtherError;
            }

            return null;
        }

        public char getCharValue() {
            return this.status;
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

        public char getCharValue() {
            return this.status;
        }

        public static ThreeDSecureAuthenticationStatus fromStringValue(String value) {

            if (value == null) return null;

            char c = value.charAt(0);

            if (c == ThreeDSecureAuthenticationStatusSuccessful.getCharValue()) {
                return ThreeDSecureAuthenticationStatusSuccessful;
            }

            if (c == ThreeDSecureAuthenticationStatusAttempted.getCharValue()) {
                return ThreeDSecureAuthenticationStatusAttempted;
            }

            if (c == ThreeDSecureAuthenticationStatusCouldNotBePerformed.getCharValue()) {
                return ThreeDSecureAuthenticationStatusCouldNotBePerformed;
            }

            if (c == ThreeDSecureAuthenticationStatusAuthenticationFailed.getCharValue()) {
                return ThreeDSecureAuthenticationStatusAuthenticationFailed;
            }

            if (c == ThreeDSecureAuthenticationStatusOther.getCharValue()) {
                return ThreeDSecureAuthenticationStatusOther;
            }

            return null;
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

    protected static class ThreeDSecureSerializationMapper extends AbstractSerializationMapper {

        protected ThreeDSecureSerializationMapper(ThreeDSecure threeDSecure) {
            super(threeDSecure);
        }

        @Override
        protected String getQueryString() {

            return super.getQueryString();
        }

        @Override
        protected Bundle getSerializedBundle() {

            return super.getSerializedBundle();
        }
    }

}
