package com.hipay.hipayfullservice.core.mapper;

import com.hipay.hipayfullservice.core.mapper.interfaces.BundleMapper;
import com.hipay.hipayfullservice.core.mapper.interfaces.MapMapper;
import com.hipay.hipayfullservice.core.models.FraudScreening;

/**
 * Created by nfillion on 08/09/16.
 */

public class FraudScreeningMapper extends AbstractMapper {

    public FraudScreeningMapper(Object rawData) {
        super(rawData);
    }

    @Override
    protected boolean isValid() {

        // from JSON only, not bundle?
        if (this.getBehaviour() instanceof MapMapper) {

            if (    this.getStringForKey("result") != null &&
                    this.getIntegerForKey("scoring") != null) {

                return true;
            }

        } else if (this.getBehaviour() instanceof BundleMapper) {

            if (    this.getStringForKey("result") != null &&
                    this.getIntegerForKey("scoring") != null) {

                return true;
            }
        }

        return false;
    }

    @Override
    public FraudScreening mappedObject() {

        FraudScreening object = new FraudScreening();

        object.setScoring(this.getIntegerForKey("scoring"));

        String resultString = this.getLowercaseStringForKey("result");
        FraudScreening.FraudScreeningResult result = FraudScreening.FraudScreeningResult.fromStringValue(resultString);
        if (result == null) {
            result = FraudScreening.FraudScreeningResult.FraudScreeningResultUnknown;
        }
        object.setResult(result);

        String reviewString = this.getLowercaseStringForKey("review");
        FraudScreening.FraudScreeningReview review = FraudScreening.FraudScreeningReview.fromStringValue(reviewString);
        if (review == null) {
            review = FraudScreening.FraudScreeningReview.FraudScreeningReviewNone;
        }
        object.setReview(review);

        return object;

    }

    @Override
    public FraudScreening mappedObjectFromBundle() {

        FraudScreening object = new FraudScreening();

        object.setScoring(this.getIntegerForKey("scoring"));

        String resultString = this.getLowercaseStringForKey("result");
        FraudScreening.FraudScreeningResult result = FraudScreening.FraudScreeningResult.fromStringValue(resultString);
        if (result == null) {
            result = FraudScreening.FraudScreeningResult.FraudScreeningResultUnknown;
        }
        object.setResult(result);

        String reviewString = this.getLowercaseStringForKey("review");
        FraudScreening.FraudScreeningReview review = FraudScreening.FraudScreeningReview.fromStringValue(reviewString);
        if (review == null) {
            review = FraudScreening.FraudScreeningReview.FraudScreeningReviewNone;
        }
        object.setReview(review);

        return object;
    }
}

