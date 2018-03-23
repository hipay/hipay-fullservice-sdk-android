package com.hipay.fullservice.core.mapper;

import com.hipay.fullservice.core.mapper.interfaces.MapMapper;
import com.hipay.fullservice.core.models.ThreeDSecure;
import com.hipay.fullservice.core.utils.enums.ThreeDSecureAuthenticationStatus;
import com.hipay.fullservice.core.utils.enums.ThreeDSecureEnrollmentStatus;

/**
 * Created by nfillion on 08/09/16.
 */

public class ThreeDSecureMapper extends AbstractMapper {
    public ThreeDSecureMapper(Object rawData) {
        super(rawData);
    }

    @Override
    public boolean isValid() {
        return this.getBehaviour() instanceof MapMapper && this.getStringForKey("enrollmentStatus") != null;
    }

    @Override
    public ThreeDSecure mappedObject() {
        ThreeDSecure object = new ThreeDSecure();

        object.setEnrollmentMessage(this.getStringForKey("enrollmentMessage"));

        String enrollmentStatus = this.getEnumCharForKey("enrollmentStatus");
        ThreeDSecureEnrollmentStatus status = ThreeDSecureEnrollmentStatus.fromStringValue(enrollmentStatus);
        if (status == null) {
            status = ThreeDSecureEnrollmentStatus.THREED_SECURE_ENROLLMENT_STATUS_UNKNOWN;
        }
        object.setEnrollmentStatus(status);

        String authenticationStatus = this.getEnumCharForKey("authenticationStatus");
        ThreeDSecureAuthenticationStatus authStatus = ThreeDSecureAuthenticationStatus.fromStringValue(authenticationStatus);
        if (authStatus == null) {
            authStatus = ThreeDSecureAuthenticationStatus.THREED_SECURE_AUTHENTICATION_STATUS_UNKNOWN;
        }
        object.setAuthenticationStatus(authStatus);

        object.setAuthenticationMessage(this.getStringForKey("authenticationMessage"));
        object.setAuthenticationToken(this.getStringForKey("authenticationToken"));
        object.setXid(this.getStringForKey("xid"));

        return object;
    }
}


