package com.hipay.hipayfullservice.core.models;

import android.os.Bundle;

import com.hipay.hipayfullservice.core.mapper.AbstractMapper;
import com.hipay.hipayfullservice.core.mapper.interfaces.MapMapper;
import com.hipay.hipayfullservice.core.serialization.AbstractSerializationMapper;
import com.hipay.hipayfullservice.core.serialization.interfaces.AbstractSerialization;

import org.json.JSONObject;

import java.util.Map;

/**
 * Created by nfillion on 25/01/16.
 */
public class FraudScreening extends AbstractModel {

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
        return mapper.mappedObjectFromBundle();
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

    public static class FraudScreeningSerialization extends AbstractSerialization {

        public FraudScreeningSerialization(FraudScreening fraudScreening) {
            super(fraudScreening);
        }

        @Override
        public Map<String, String> getSerializedRequest() {
            return null;
        }

        @Override
        public String getQueryString() {
            return null;
        }

        @Override
        public Bundle getSerializedBundle() {

            super.getSerializedBundle();

            FraudScreening fraudScreening = (FraudScreening)this.getModel();

            this.putIntForKey("scoring", fraudScreening.getScoring());

            FraudScreeningResult result = fraudScreening.getResult();
            if (result != null) {
                this.putStringForKey("result", result.getStringValue());
            }

            FraudScreeningReview review = fraudScreening.getReview();
            if (review != null) {
                this.putStringForKey("review", review.getStringValue());
            }

            return this.getBundle();
        }
    }

    public static class FraudScreeningMapper extends AbstractMapper {

        public FraudScreeningMapper(Object rawData) {
            super(rawData);
        }

        @Override
        protected boolean isValid() {

            if (this.getBehaviour() instanceof MapMapper) {

                if (    this.getStringForKey("result") != null &&
                        this.getIntegerForKey("scoring") != null) {

                    return true;
                }
            }

            return false;
        }

        protected FraudScreening mappedObject() {

            FraudScreening object = new FraudScreening();

            object.setScoring(this.getIntegerForKey("scoring"));

            String resultString = this.getLowercaseStringForKey("result");
            FraudScreeningResult result = FraudScreeningResult.fromStringValue(resultString);
            if (result == null) {
                result = FraudScreeningResult.FraudScreeningResultUnknown;
            }
            object.setResult(result);
            String reviewString = this.getLowercaseStringForKey("review");
            FraudScreeningReview review = FraudScreeningReview.fromStringValue(reviewString);
            if (review == null) {
                review = FraudScreeningReview.FraudScreeningReviewNone;
            }
            object.setReview(review);

            return object;

        }

        @Override
        protected FraudScreening mappedObjectFromBundle() {

            FraudScreening object = new FraudScreening();

            object.setScoring(this.getIntegerForKey("scoring"));

            String resultString = this.getLowercaseStringForKey("result");
            FraudScreeningResult result = FraudScreeningResult.fromStringValue(resultString);
            if (result == null) {
                result = FraudScreeningResult.FraudScreeningResultUnknown;
            }
            object.setResult(result);
            String reviewString = this.getLowercaseStringForKey("review");
            FraudScreeningReview review = FraudScreeningReview.fromStringValue(reviewString);
            if (review == null) {
                review = FraudScreeningReview.FraudScreeningReviewNone;
            }
            object.setReview(review);

            return object;
        }
    }
}
