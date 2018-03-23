package com.hipay.fullservice.core.mapper;

import com.hipay.fullservice.core.mapper.interfaces.BundleMapper;
import com.hipay.fullservice.core.mapper.interfaces.MapMapper;
import com.hipay.fullservice.core.models.FraudScreening;
import com.hipay.fullservice.core.models.FraudScreeningReview;
import com.hipay.fullservice.core.utils.enums.FraudScreeningResult;

/**
 * Created by nfillion on 08/09/16.
 */

public class FraudScreeningMapper extends AbstractMapper {

    public FraudScreeningMapper(Object rawData) {
        super(rawData);
    }

    @Override
    public boolean isValid() {
        // from JSON only, not bundle?
        if (this.getBehaviour() instanceof MapMapper || this.getBehaviour() instanceof BundleMapper) {
            if (this.getStringForKey("result") != null &&
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
        FraudScreeningResult result = FraudScreeningResult.fromStringValue(resultString);
        if (result == null) {
            result = FraudScreeningResult.FRAUDSCREENINGRESULT_UNKNOWN;
        }
        object.setResult(result);

        String reviewString = this.getLowercaseStringForKey("review");
        FraudScreeningReview review = FraudScreeningReview.fromStringValue(reviewString);
        if (review == null) {
            review = FraudScreeningReview.FRAUDSCREENINGREVIEW_NONE;
        }
        object.setReview(review);

        return object;

    }
}

