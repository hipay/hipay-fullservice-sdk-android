package com.hipay.hipayfullservice.core.mapper;

import com.hipay.hipayfullservice.core.mapper.interfaces.MapMapper;
import com.hipay.hipayfullservice.core.models.ThreeDSecure;

/**
 * Created by nfillion on 08/09/16.
 */

public class ThreeDSecureMapper extends AbstractMapper {
    public ThreeDSecureMapper(Object rawData) {
        super(rawData);
    }

    @Override
    protected boolean isValid() {

        if (this.getBehaviour() instanceof MapMapper) {

            if (this.getStringForKey("enrollmentStatus") != null) return true;
        }

        return false;
    }

    public ThreeDSecure mappedObject() {

        ThreeDSecure object = new ThreeDSecure();

        object.setEnrollmentMessage(this.getStringForKey("enrollmentMessage"));

        String enrollmentStatus = this.getEnumCharForKey("enrollmentStatus");
        ThreeDSecure.ThreeDSecureEnrollmentStatus status = ThreeDSecure.ThreeDSecureEnrollmentStatus.fromStringValue(enrollmentStatus);
        if (status == null) {
            status = ThreeDSecure.ThreeDSecureEnrollmentStatus.ThreeDSecureEnrollmentStatusUnknown;
        }
        object.setEnrollmentStatus(status);

        String authenticationStatus = this.getEnumCharForKey("authenticationStatus");
        ThreeDSecure.ThreeDSecureAuthenticationStatus authStatus = ThreeDSecure.ThreeDSecureAuthenticationStatus.fromStringValue(authenticationStatus);
        if (authStatus == null) {
            authStatus = ThreeDSecure.ThreeDSecureAuthenticationStatus.ThreeDSecureAuthenticationStatusUnknown;
        }
        object.setAuthenticationStatus(authStatus);

        object.setAuthenticationMessage(this.getStringForKey("authenticationMessage"));
        object.setAuthenticationToken(this.getStringForKey("authenticationToken"));
        object.setXid(this.getStringForKey("xid"));

        return object;

    }

    @Override
    public ThreeDSecure mappedObjectFromBundle() {

        ThreeDSecure object = new ThreeDSecure();

        object.setEnrollmentMessage(this.getStringForKey("enrollmentMessage"));

        String enrollmentStatus = this.getEnumCharForKey("enrollmentStatus");
        ThreeDSecure.ThreeDSecureEnrollmentStatus status = ThreeDSecure.ThreeDSecureEnrollmentStatus.fromStringValue(enrollmentStatus);
        if (status == null) {
            status = ThreeDSecure.ThreeDSecureEnrollmentStatus.ThreeDSecureEnrollmentStatusUnknown;
        }
        object.setEnrollmentStatus(status);

        String authenticationStatus = this.getEnumCharForKey("authenticationStatus");
        ThreeDSecure.ThreeDSecureAuthenticationStatus authStatus = ThreeDSecure.ThreeDSecureAuthenticationStatus.fromStringValue(authenticationStatus);
        if (authStatus == null) {
            authStatus = ThreeDSecure.ThreeDSecureAuthenticationStatus.ThreeDSecureAuthenticationStatusUnknown;
        }
        object.setAuthenticationStatus(authStatus);

        object.setAuthenticationMessage(this.getStringForKey("authenticationMessage"));
        object.setAuthenticationToken(this.getStringForKey("authenticationToken"));
        object.setXid(this.getStringForKey("xid"));

        return object;
    }
}


