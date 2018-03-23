package com.hipay.fullservice.core.mapper;

import android.os.Bundle;

import com.hipay.fullservice.core.mapper.interfaces.BundleMapper;
import com.hipay.fullservice.core.requests.order.OrderRelatedRequest;
import com.hipay.fullservice.core.requests.order.PaymentPageRequest;
import com.hipay.fullservice.core.utils.enums.ECI;
import com.hipay.fullservice.core.utils.enums.AuthenticationIndicator;

import java.util.Arrays;
import java.util.List;

/**
 * Created by nfillion on 22/03/2018.
 */
public class PaymentPageRequestMapper extends AbstractMapper{

    private Bundle  object;

    public PaymentPageRequestMapper(Bundle object) {
        super(object);
        this.object = object;
    }

    @Override
    public boolean isValid() {
        return this.getBehaviour() instanceof BundleMapper;
    }

    @Override
    public PaymentPageRequest mappedObject() {

        OrderRelatedRequest orderRelatedRequest = new OrderRelatedRequestMapper(this.object).mappedObject() ;

        PaymentPageRequest paymentPageRequest = this.pageRequestFromRelatedRequest(orderRelatedRequest);

        String paymentProductsString = this.getStringForKey("payment_product_list");
        if (paymentProductsString != null) {
            List paymentProducts = Arrays.asList(paymentProductsString.split(","));
            paymentPageRequest.setPaymentProductList(paymentProducts);
        }

        String paymentProductsCategoryString = this.getStringForKey("payment_product_category_list");
        if (paymentProductsCategoryString != null) {
            List<String> paymentProductsCategory = Arrays.asList(paymentProductsCategoryString.split(","));
            paymentPageRequest.setPaymentProductCategoryList(paymentProductsCategory);
        }

        Integer eciInt = this.getIntegerForKey("eci");
        ECI eci = ECI.fromIntegerValue(eciInt);
        paymentPageRequest.setEci(eci);

        Integer authenticationIndicatorInteger = this.getIntegerForKey("authentication_indicator");
        AuthenticationIndicator authenticationIndicator = AuthenticationIndicator.fromIntegerValue(authenticationIndicatorInteger);
        paymentPageRequest.setAuthenticationIndicator(authenticationIndicator);

        paymentPageRequest.setMultiUse(this.getBoolForKey("multi_use"));
        paymentPageRequest.setDisplaySelector(this.getBoolForKey("display_selector"));
        paymentPageRequest.setTemplateName(this.getStringForKey("template"));

        paymentPageRequest.setCss(this.getURLForKey("css"));

        paymentPageRequest.setPaymentCardGroupingEnabled(this.getBoolForKey("card_grouping"));

        return paymentPageRequest;
    }

    private PaymentPageRequest pageRequestFromRelatedRequest(OrderRelatedRequest orderRelatedRequest) {

        PaymentPageRequest paymentPageRequest = new PaymentPageRequest();

        paymentPageRequest.setOrderId(orderRelatedRequest.getOrderId());

        paymentPageRequest.setOperation(orderRelatedRequest.getOperation());

        paymentPageRequest.setShortDescription(orderRelatedRequest.getShortDescription());
        paymentPageRequest.setLongDescription(orderRelatedRequest.getLongDescription());
        paymentPageRequest.setCurrency(orderRelatedRequest.getCurrency());

        paymentPageRequest.setAmount(orderRelatedRequest.getAmount());
        paymentPageRequest.setShipping(orderRelatedRequest.getShipping());
        paymentPageRequest.setTax(orderRelatedRequest.getTax());

        paymentPageRequest.setClientId(orderRelatedRequest.getClientId());
        paymentPageRequest.setIpAddress(orderRelatedRequest.getIpAddress());
        paymentPageRequest.setHTTPAccept(orderRelatedRequest.getHTTPUserAgent());
        paymentPageRequest.setHTTPUserAgent(orderRelatedRequest.getHTTPUserAgent());
        paymentPageRequest.setDeviceFingerprint(orderRelatedRequest.getDeviceFingerprint());
        paymentPageRequest.setLanguage(orderRelatedRequest.getLanguage());

        paymentPageRequest.setAcceptScheme(orderRelatedRequest.getAcceptScheme());
        paymentPageRequest.setDeclineScheme(orderRelatedRequest.getDeclineScheme());
        paymentPageRequest.setPendingScheme(orderRelatedRequest.getPendingScheme());
        paymentPageRequest.setExceptionScheme(orderRelatedRequest.getExceptionScheme());
        paymentPageRequest.setCancelScheme(orderRelatedRequest.getCancelScheme());

        paymentPageRequest.setCustomer(orderRelatedRequest.getCustomer());
        paymentPageRequest.setShippingAddress(orderRelatedRequest.getShippingAddress());

        paymentPageRequest.setCustomData(orderRelatedRequest.getCustomData());

        paymentPageRequest.setCdata1(orderRelatedRequest.getCdata1());
        paymentPageRequest.setCdata2(orderRelatedRequest.getCdata2());
        paymentPageRequest.setCdata3(orderRelatedRequest.getCdata3());
        paymentPageRequest.setCdata4(orderRelatedRequest.getCdata4());
        paymentPageRequest.setCdata5(orderRelatedRequest.getCdata5());
        paymentPageRequest.setCdata6(orderRelatedRequest.getCdata6());
        paymentPageRequest.setCdata7(orderRelatedRequest.getCdata7());
        paymentPageRequest.setCdata8(orderRelatedRequest.getCdata8());
        paymentPageRequest.setCdata9(orderRelatedRequest.getCdata9());
        paymentPageRequest.setCdata10(orderRelatedRequest.getCdata10());

        return paymentPageRequest;
    }
}
