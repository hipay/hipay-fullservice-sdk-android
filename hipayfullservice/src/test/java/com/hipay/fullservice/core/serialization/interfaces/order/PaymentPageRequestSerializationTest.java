package com.hipay.fullservice.core.serialization.interfaces.order;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

import android.text.TextUtils;

import com.hipay.fullservice.core.models.Transaction;
import com.hipay.fullservice.core.requests.order.PaymentPageRequest;
import com.hipay.fullservice.core.requests.payment.CardTokenPaymentMethodRequest;

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

import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by HiPay on 01/09/16.
 */

@RunWith(PowerMockRunner.class)
@PrepareForTest(TextUtils.class)

public class PaymentPageRequestSerializationTest {

    @Before
    public void setup() throws Exception {
        PowerMockito.mockStatic(TextUtils.class);
    }

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Mock
    PaymentPageRequest paymentPageRequest;

    @Test
    public void testMethods() throws Exception {

        PaymentPageRequestSerialization paymentPageRequestSerialization = new PaymentPageRequestSerialization(paymentPageRequest);
        assertNotNull(paymentPageRequestSerialization);

        List<String> productsList = Arrays.asList("product1", "product2");
        String resultProducts = "product1,product2";
        BDDMockito.given(TextUtils.join(",", productsList)).willReturn(resultProducts);

        List<String> productsCatList = Arrays.asList("productCat1", "productCat2");
        String resultProductsCat = "productCat1,productCat2";
        BDDMockito.given(TextUtils.join(",", productsCatList)).willReturn(resultProductsCat);

        when(paymentPageRequest.getPaymentProductList()).thenReturn(productsList);
        when(paymentPageRequest.getPaymentProductCategoryList()).thenReturn(productsCatList);

        when(paymentPageRequest.getEci()).thenReturn(Transaction.ECI.RecurringMOTO);
        when(paymentPageRequest.getAuthenticationIndicator()).thenReturn(CardTokenPaymentMethodRequest.AuthenticationIndicator.IfAvailable);
        when(paymentPageRequest.getMultiUse()).thenReturn(Boolean.TRUE);
        when(paymentPageRequest.getDisplaySelector()).thenReturn(Boolean.TRUE);
        when(paymentPageRequest.getTemplateName()).thenReturn("templateName");
        when(paymentPageRequest.getCss()).thenReturn(new URL("https://www.hipay.com/misc/devportal/css1.css"));

        //to avoid problems with orderRelatedRequestSerialization superclass
        when(paymentPageRequest.getShipping()).thenReturn(null);
        when(paymentPageRequest.getAmount()).thenReturn(null);
        when(paymentPageRequest.getTax()).thenReturn(null);

        Map<String, String> testMap = new HashMap<>(9);

        testMap.put("payment_product_list", resultProducts);
        testMap.put("payment_product_category_list", resultProductsCat);
        testMap.put("eci", "2");
        testMap.put("authentication_indicator", "1");
        testMap.put("multi_use", "1");
        testMap.put("display_selector", "1");
        testMap.put("template", "templateName");
        testMap.put("css", "https://www.hipay.com/misc/devportal/css1.css");

        //test getSerializedRequest
        assertEquals(paymentPageRequestSerialization.getSerializedRequest(), testMap);

//        List<String> parameters = new ArrayList<>(9);
//        parameters.add("template=templateName");
//        parameters.add("css=https%3A%2F%2Fwww.hipay.com%2Fmisc%2Fdevportal%2Fcss1.css");
//        parameters.add("eci=2");
//        parameters.add("payment_product_list=product1%2Cproduct2");
//        parameters.add("payment_product_category_list=productCat1%2CproductCat2");
//        parameters.add("display_selector=1");
//        parameters.add("multi_use=1");
//        parameters.add("authentication_indicator=1");
//        parameters.add("account_info={\"shipping\":{\"name_indicator\":2}}");
//
//        Collections.sort(parameters);
//
//        //TextUtils transforms the list in String
//        StringBuilder stringBuilder = new StringBuilder(parameters.get(0)).append("&")
//                .append(parameters.get(1)).append("&")
//                .append(parameters.get(2)).append("&")
//                .append(parameters.get(3)).append("&")
//                .append(parameters.get(4)).append("&")
//                .append(parameters.get(5)).append("&")
//                .append(parameters.get(6)).append("&")
//                .append(parameters.get(7)).append("&")
//                .append(parameters.get(8));
//
//        BDDMockito.given(TextUtils.join("&", parameters)).willReturn(stringBuilder.toString());

        //assertEquals(paymentPageRequestSerialization.getQueryString(), stringBuilder.toString());
    }
}