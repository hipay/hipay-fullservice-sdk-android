package com.hipay.fullservice.core.serialization.interfaces;

import android.os.Bundle;

import com.hipay.fullservice.core.models.FraudScreening;
import com.hipay.fullservice.core.models.FraudScreeningReview;
import com.hipay.fullservice.core.utils.enums.FraudScreeningResult;

/**
 * Created by nfillion on 07/09/16.
 */
public class FraudScreeningSerialization extends AbstractSerialization {

    public FraudScreeningSerialization(FraudScreening fraudScreening) {
        super(fraudScreening);
    }

    @Override
    public Bundle getSerializedBundle() {
        FraudScreening fraudScreening = (FraudScreening)this.getModel();

        this.bundle.putInt("scoring", fraudScreening.getScoring());

        FraudScreeningResult result = fraudScreening.getResult();
        if (result != null) {
            this.bundle.putString("result", result.getValue());
        }

        FraudScreeningReview review = fraudScreening.getReview();
        if (review != null) {
            this.bundle.putString("review", review.getStringValue());
        }

        return this.getBundle();
    }
}
