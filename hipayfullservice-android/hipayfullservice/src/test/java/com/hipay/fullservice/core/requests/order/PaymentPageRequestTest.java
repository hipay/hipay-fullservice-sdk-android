package com.hipay.fullservice.core.requests.order;

import org.junit.Before;
import org.junit.Test;

/**
 * Created by nfillion on 22/02/16.
 */
public class PaymentPageRequestTest {

    PaymentPageRequest paymentPageRequest;

    @Before
    public void setUp() throws Exception {

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