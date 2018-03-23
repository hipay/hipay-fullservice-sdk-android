package com.hipay.fullservice.core.models;

import android.os.Bundle;

import com.hipay.fullservice.core.mapper.ThreeDSecureMapper;
import com.hipay.fullservice.core.serialization.AbstractSerializationMapper;
import com.hipay.fullservice.core.utils.enums.ThreeDSecureAuthenticationStatus;
import com.hipay.fullservice.core.utils.enums.ThreeDSecureEnrollmentStatus;


import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by nfillion on 25/01/16.
 */
public class ThreeDSecure extends AbstractModel  implements Serializable {

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
        return mapper.mappedObject();
    }

    public Bundle toBundle() {

        ThreeDSecure.ThreeDSecureSerializationMapper mapper = new ThreeDSecure.ThreeDSecureSerializationMapper(this);
        return mapper.getSerializedBundle();
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
