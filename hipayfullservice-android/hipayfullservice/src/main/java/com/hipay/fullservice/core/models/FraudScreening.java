package com.hipay.fullservice.core.models;

import android.os.Bundle;

import com.hipay.fullservice.core.mapper.FraudScreeningMapper;
import com.hipay.fullservice.core.serialization.AbstractSerializationMapper;

import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by nfillion on 25/01/16.
 */
public class FraudScreening extends AbstractModel implements Serializable {

    protected Integer scoring;
    protected FraudScreeningResult result;
    protected FraudScreeningReview review;

    public FraudScreening() {
    }

    public static FraudScreening fromJSONObject(JSONObject object) {

        FraudScreeningMapper mapper = new FraudScreeningMapper(object);
        return mapper.mappedObject();
    }

    public static FraudScreening fromBundle(Bundle bundle) {

        FraudScreeningMapper mapper = new FraudScreeningMapper(bundle);
        return mapper.mappedObject();
    }

    public Bundle toBundle() {

        FraudScreening.FraudScreeningSerializationMapper mapper = new FraudScreening.FraudScreeningSerializationMapper(this);
        return mapper.getSerializedBundle();
    }

    public enum FraudScreeningResult {

        FraudScreeningResultUnknown ("unknown"),
        FraudScreeningResultPending ("pending"),
        FraudScreeningResultAccepted ("accepted"),
        FraudScreeningResultBlocked ("blocked"),
        FraudScreeningResultChallenged ("challenged");

        protected final String result;

        FraudScreeningResult(String result) {
            this.result = result;
        }

        public String getStringValue() {
            return this.result;
        }

        public static FraudScreeningResult fromStringValue(String value) {

            if (value == null) return null;

            if (value.equalsIgnoreCase(FraudScreeningResultPending.getStringValue())) {
                return FraudScreeningResultPending;
            }

            if (value.equalsIgnoreCase(FraudScreeningResultAccepted.getStringValue())) {
                return FraudScreeningResultAccepted;
            }

            if (value.equalsIgnoreCase(FraudScreeningResultBlocked.getStringValue())) {
                return FraudScreeningResultBlocked;
            }

            if (value.equalsIgnoreCase(FraudScreeningResultChallenged.getStringValue())) {
                return FraudScreeningResultChallenged;
            }
            return null;
        }
    }

    public enum FraudScreeningReview {

        FraudScreeningReviewNone("none"),
        FraudScreeningReviewPending("pending"),
        FraudScreeningReviewAllowed("allowed"),
        FraudScreeningReviewDenied("denied");

        protected final String review;

        FraudScreeningReview(String review) {
            this.review = review;
        }

        public String getStringValue() {
            return this.review;
        }

        public static FraudScreeningReview fromStringValue(String value) {

            if (value == null) return null;

            if (value.equalsIgnoreCase(FraudScreeningReviewPending.getStringValue())) {
                return FraudScreeningReviewPending;
            }

            if (value.equalsIgnoreCase(FraudScreeningReviewAllowed.getStringValue())) {
                return FraudScreeningReviewAllowed;
            }

            if (value.equalsIgnoreCase(FraudScreeningReviewDenied.getStringValue())) {
                return FraudScreeningReviewDenied;
            }

            return null;
        }
    }

    public Integer getScoring() {
        return scoring;
    }

    public void setScoring(Integer scoring) {
        this.scoring = scoring;
    }

    public FraudScreeningResult getResult() {
        return result;
    }

    public void setResult(FraudScreeningResult result) {
        this.result = result;
    }

    public FraudScreeningReview getReview() {
        return review;
    }

    public void setReview(FraudScreeningReview review) {
        this.review = review;
    }



    protected static class FraudScreeningSerializationMapper extends AbstractSerializationMapper {

        protected FraudScreeningSerializationMapper(FraudScreening fraudScreening) {
            super(fraudScreening);
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
