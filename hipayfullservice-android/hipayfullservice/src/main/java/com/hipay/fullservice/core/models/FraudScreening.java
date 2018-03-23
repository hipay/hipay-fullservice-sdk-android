package com.hipay.fullservice.core.models;

import android.os.Bundle;

import com.hipay.fullservice.core.mapper.FraudScreeningMapper;
import com.hipay.fullservice.core.serialization.AbstractSerializationMapper;
import com.hipay.fullservice.core.utils.enums.FraudScreeningResult;

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
