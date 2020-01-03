package com.hipay.fullservice.core.mapper;

/**
 * Created by HiPay on 09/09/16.
 */

import android.text.TextUtils;

import com.hipay.fullservice.core.models.FraudScreening;

import org.json.JSONObject;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest({TextUtils.class})

public class FraudScreeningMapperTest {

    @Before
    public void setup() throws Exception {

        PowerMockito.mockStatic(TextUtils.class);
    }

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Mock
    FraudScreeningMapper fraudScreeningMapper;

    @Mock
    JSONObject jsonObject;

    @Test
    public void testMethods() throws Exception {

        FraudScreeningMapper fraudScreeningMapper = new FraudScreeningMapper(jsonObject);

        when(jsonObject.optString("result", null)).thenReturn("bLOCked");
        when(jsonObject.opt("scoring")).thenReturn("1");
        when(jsonObject.optString("review", null)).thenReturn("denIEd");

        BDDMockito.given(TextUtils.isDigitsOnly("1")).willReturn(true);

        FraudScreening compareFraudScreeningCompare = new FraudScreening();
        compareFraudScreeningCompare.setScoring(1);
        compareFraudScreeningCompare.setResult(FraudScreening.FraudScreeningResult.FraudScreeningResultBlocked);
        compareFraudScreeningCompare.setReview(FraudScreening.FraudScreeningReview.FraudScreeningReviewDenied);

        FraudScreening fraudScreeningTest = fraudScreeningMapper.mappedObject();

        assertEquals(fraudScreeningTest.getResult(), compareFraudScreeningCompare.getResult());
        assertEquals(fraudScreeningTest.getReview(), compareFraudScreeningCompare.getReview());
        assertEquals(fraudScreeningTest.getScoring(), compareFraudScreeningCompare.getScoring());

    }
}

