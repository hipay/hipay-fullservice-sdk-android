package com.hipay.hipayfullservice.core.models;

/**
 * Created by nfillion on 25/01/16.
 */
public class FraudScreening {

    protected Integer scoring;
    protected FraudScreeningResult result;
    protected FraudScreeningReview review;

    public FraudScreening() {
    }

    public enum FraudScreeningResult {

        FraudScreeningResultUnknown,
        FraudScreeningResultPending,
        FraudScreeningResultAccepted,
        FraudScreeningResultBlocked,
        FraudScreeningResultChallenged;
    }

    public enum FraudScreeningReview {

        FraudScreeningReviewNone,
        FraudScreeningReviewPending,
        FraudScreeningReviewAllowed,
        FraudScreeningReviewDenied
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

}
