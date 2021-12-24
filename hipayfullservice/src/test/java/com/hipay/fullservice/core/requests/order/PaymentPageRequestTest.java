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
public class PaymentPageRequestTest {

    PaymentPageRequest paymentPageRequest;

    @Before
    public void setUp() throws Exception {

        mockStatic(Utils.class);

        when(Utils.getScreenHeight()).thenReturn(1920);
        when(Utils.getScreenWidth()).thenReturn(1080);

        paymentPageRequest = new PaymentPageRequest();
    }

    @Test
    public void getTestGetters() throws Exception {

        paymentPageRequest.getCss();
        paymentPageRequest.getTemplateName();
        paymentPageRequest.getDisplaySelector();
        paymentPageRequest.getPaymentProductList();
        paymentPageRequest.getPaymentProductCategoryList();
        paymentPageRequest.getEci();
        paymentPageRequest.getAuthenticationIndicator();
        paymentPageRequest.getMultiUse();
        paymentPageRequest.getGroupedPaymentCardProductCodes();
        paymentPageRequest.getDisplaySelector();

    }

    @Test
    public void getTestSetters() throws Exception {

        paymentPageRequest.setCss(null);
        paymentPageRequest.setTemplateName(null);
        paymentPageRequest.setDisplaySelector(null);
        paymentPageRequest.setPaymentProductList(null);
        paymentPageRequest.setPaymentProductCategoryList(null);
        paymentPageRequest.setEci(null);
        paymentPageRequest.setAuthenticationIndicator(null);
        paymentPageRequest.setMultiUse(null);
        paymentPageRequest.setGroupedPaymentCardProductCodes(null);
        paymentPageRequest.setDisplaySelector(null);

    }
}