package com.hipay.fullservice.core.requests.order;

import android.os.Bundle;

import com.hipay.fullservice.core.mapper.PaymentPageRequestMapper;
import com.hipay.fullservice.core.models.PaymentProduct;
import com.hipay.fullservice.core.models.Transaction;
import com.hipay.fullservice.core.requests.payment.CardTokenPaymentMethodRequest;
import com.hipay.fullservice.core.serialization.AbstractSerializationMapper;

import java.net.URL;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by nfillion on 03/02/16.
 */
public class PaymentPageRequest extends OrderRelatedRequest {

    public static final String TAG = "Payment_page_request";
    public static final int REQUEST_ORDER = 0x2300;

    List<String> paymentProductList;
    List<String> paymentProductCategoryList;

    Transaction.ECI eci;
    CardTokenPaymentMethodRequest.AuthenticationIndicator authenticationIndicator;
    Boolean multiUse;
    Boolean displaySelector;
    String templateName;
    URL css;
    Boolean paymentCardGroupingEnabled;

    Set<String> groupedPaymentCardProductCodes;

    public final static String PaymentPageRequestTemplateNameBasic = "basic-js";
    public final static String PaymentPageRequestTemplateNameFrame = "iframe-js";

    public PaymentPageRequest() {

        this.setEci(Transaction.ECI.SecureECommerce);
        this.setMultiUse(false);
        this.setDisplaySelector(false);
        this.setPaymentCardGroupingEnabled(false);

        Set<String> s = new HashSet<>(Arrays.asList(

                PaymentProduct.PaymentProductCodeCB,
                PaymentProduct.PaymentProductCodeMasterCard,
                PaymentProduct.PaymentProductCodeVisa,
                PaymentProduct.PaymentProductCodeAmericanExpress,
                PaymentProduct.PaymentProductCodeMaestro,
                PaymentProduct.PaymentProductCodeDiners
        ));

        this.setGroupedPaymentCardProductCodes(s);
    }

    public PaymentPageRequest(PaymentPageRequest paymentPageRequest) {
        super(paymentPageRequest);
    }

    public static PaymentPageRequest fromBundle(Bundle bundle) {

        PaymentPageRequestMapper mapper = new PaymentPageRequestMapper(bundle);
        return mapper.mappedObject();
    }

    public Bundle toBundle() {

        PaymentPageRequest.PaymentPageRequestSerializationMapper mapper = new PaymentPageRequest.PaymentPageRequestSerializationMapper(this);
        return mapper.getSerializedBundle();
    }

    public String getStringParameters() {

        PaymentPageRequest.PaymentPageRequestSerializationMapper mapper = new PaymentPageRequest.PaymentPageRequestSerializationMapper(this);
        return mapper.getQueryString();
    }

    public Boolean isPaymentCardGroupingEnabled() {
        return paymentCardGroupingEnabled;
    }

    public void setPaymentCardGroupingEnabled(Boolean paymentCardGroupingEnabled) {
        this.paymentCardGroupingEnabled = paymentCardGroupingEnabled;
    }

    public URL getCss() {
        return css;
    }

    public void setCss(URL css) {
        this.css = css;
    }

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    public boolean isDisplaySelector() {
        return displaySelector;
    }

    public List<String> getPaymentProductList() {
        return paymentProductList;
    }

    public List<String> getPaymentProductCategoryList() {
        return paymentProductCategoryList;
    }

    public void setPaymentProductCategoryList(List<String> paymentProductCategoryList) {
        this.paymentProductCategoryList = paymentProductCategoryList;
    }

    public Transaction.ECI getEci() {
        return eci;
    }

    public void setEci(Transaction.ECI eci) {
        this.eci = eci;
    }

    public CardTokenPaymentMethodRequest.AuthenticationIndicator getAuthenticationIndicator() {
        return authenticationIndicator;
    }

    public void setAuthenticationIndicator(CardTokenPaymentMethodRequest.AuthenticationIndicator authenticationIndicator) {
        this.authenticationIndicator = authenticationIndicator;
    }

    public boolean isMultiUse() {
        return multiUse;
    }

    public void setMultiUse(boolean multiUse) {
        this.multiUse = multiUse;
    }

    public Set<String> getGroupedPaymentCardProductCodes() {
        return groupedPaymentCardProductCodes;
    }

    public void setGroupedPaymentCardProductCodes(Set<String> groupedPaymentCardProductCodes) {
        this.groupedPaymentCardProductCodes = groupedPaymentCardProductCodes;
    }

    public Boolean getMultiUse() {
        return multiUse;
    }

    public void setMultiUse(Boolean multiUse) {
        this.multiUse = multiUse;
    }

    public Boolean getDisplaySelector() {
        return displaySelector;
    }

    public void setDisplaySelector(Boolean displaySelector) {
        this.displaySelector = displaySelector;
    }

    public void setPaymentProductList(List<String> paymentProductList) {
        this.paymentProductList = paymentProductList;
    }

    protected static class PaymentPageRequestSerializationMapper extends AbstractSerializationMapper {

        protected PaymentPageRequestSerializationMapper(PaymentPageRequest request) {
            super(request);
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