package com.hipay.hipayfullservice.core.models;

import org.junit.Before;
import org.junit.Test;

/**
 * Created by nfillion on 16/02/16.
 */
public class FraudScreeningTest {

    private FraudScreening fraudScreening;

    @Before
    public void createInstance() throws Exception {

        fraudScreening = new FraudScreening();
    }

    @Test
    public void testGetters() throws Exception {

        fraudScreening.getResult();
        fraudScreening.getReview();
        fraudScreening.getScoring();
    }

    @Test
    public void testSetters() throws Exception {

        fraudScreening.setResult(null);
        fraudScreening.setReview(null);
        fraudScreening.setScoring(null);
    }
}