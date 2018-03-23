package com.hipay.fullservice.core.serialization.interfaces;

import android.os.Bundle;
import com.hipay.fullservice.core.models.ThreeDSecure;
import com.hipay.fullservice.core.utils.enums.ThreeDSecureAuthenticationStatus;
import com.hipay.fullservice.core.utils.enums.ThreeDSecureEnrollmentStatus;

import java.util.Map;

/**
 * Created by nfillion on 08/09/16.
 */

public class ThreeDSecureSerialization extends AbstractSerialization {

    public ThreeDSecureSerialization(ThreeDSecure threeDSecure) {
        super(threeDSecure);
    }

    @Override
    public Map<String, String> getSerializedRequest() {
        return null;
    }

    @Override
    public Bundle getSerializedBundle() {
        super.getSerializedBundle();

        ThreeDSecure threeDSecure = (ThreeDSecure)this.getModel();

        this.putStringForKey("enrollmentMessage", threeDSecure.getEnrollmentMessage());

        ThreeDSecureEnrollmentStatus enrollmentStatus = threeDSecure.getEnrollmentStatus();
        if (enrollmentStatus != null) {
            this.putStringForKey("enrollmentStatus", Character.toString(enrollmentStatus.getValue()));
        }

        ThreeDSecureAuthenticationStatus authenticationStatus = threeDSecure.getAuthenticationStatus();
        if (authenticationStatus != null) {
            this.putStringForKey("authenticationStatus", Character.toString(authenticationStatus.getValue()));
        }

        this.putStringForKey("authenticationMessage", threeDSecure.getAuthenticationMessage());
        this.putStringForKey("authenticationToken", threeDSecure.getAuthenticationToken());
        this.putStringForKey("xid", threeDSecure.getXid());

        return this.getBundle();
    }

    @Override
    public String getQueryString() {
        return null;
    }
}
