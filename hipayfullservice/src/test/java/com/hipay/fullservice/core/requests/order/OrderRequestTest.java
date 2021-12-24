package com.hipay.fullservice.core.requests.order;

import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.when;

import com.hipay.fullservice.core.utils.Utils;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * Created by HiPay on 22/02/16.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(Utils.class)
public class OrderRequestTest {

    OrderRequest orderRequestTest;

    @Before
    public void setUp() throws Exception {
        mockStatic(Utils.class);

        when(Utils.getScreenHeight()).thenReturn(1920);
        when(Utils.getScreenWidth()).thenReturn(1080);

        orderRequestTest = new OrderRequest(new OrderRelatedRequest());
    }

    @Test
    public void testGetters() throws Exception {

        orderRequestTest.getPaymentProductCode();
    }

    @Test
    public void testSetters() throws Exception {

        orderRequestTest.setPaymentProductCode(null);
    }
}