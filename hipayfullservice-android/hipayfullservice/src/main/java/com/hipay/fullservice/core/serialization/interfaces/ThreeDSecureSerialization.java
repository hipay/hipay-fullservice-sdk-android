package com.hipay.fullservice.core.serialization.interfaces;

import android.os.Bundle;

import com.hipay.fullservice.core.models.ThreeDSecure;
import com.hipay.fullservice.core.utils.enums.ThreeDSecureAuthenticationStatus;
import com.hipay.fullservice.core.utils.enums.ThreeDSecureEnrollmentStatus;

/**
 * Created by nfillion on 08/09/16.
 */

public class ThreeDSecureSerialization extends AbstractSerialization {

    public ThreeDSecureSerialization(ThreeDSecure threeDSecure) {
        super(threeDSecure);
    }

    @Override
    public Bundle getSerializedBundle() {
        ThreeDSecure threeDSecure = (ThreeDSecure)this.getModel();

        this.bundle.putString("enrollmentMessage", threeDSecure.getEnrollmentMessage());

        ThreeDSecureEnrollmentStatus enrollmentStatus = threeDSecure.getEnrollmentStatus();
        if (enrollmentStatus != null) {
            this.bundle.putString("enrollmentStatus", Character.toString(enrollmentStatus.getValue()));
        }

        ThreeDSecureAuthenticationStatus authenticationStatus = threeDSecure.getAuthenticationStatus();
        if (authenticationStatus != null) {
            this.bundle.putString("authenticationStatus", Character.toString(authenticationStatus.getValue()));
        }

        this.bundle.putString("authenticationMessage", threeDSecure.getAuthenticationMessage());
        this.bundle.putString("authenticationToken", threeDSecure.getAuthenticationToken());
        this.bundle.putString("xid", threeDSecure.getXid());

        return this.getBundle();
    }
}
