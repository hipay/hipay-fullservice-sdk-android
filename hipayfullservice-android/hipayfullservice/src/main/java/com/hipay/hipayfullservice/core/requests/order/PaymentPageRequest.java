package com.hipay.hipayfullservice.core.requests.order;

import com.hipay.hipayfullservice.core.models.PaymentProduct;
import com.hipay.hipayfullservice.core.models.Transaction;
import com.hipay.hipayfullservice.core.requests.payment.CardTokenPaymentMethodRequest;
import com.hipay.hipayfullservice.core.serialization.AbstractSerializationMapper;

import java.net.URL;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by nfillion on 03/02/16.
 */
public class PaymentPageRequest extends OrderRelatedRequest {

    List paymentProductList;
    List paymentProductCategoryList;

    Transaction.ECI eci;
    CardTokenPaymentMethodRequest.AuthenticationIndicator authenticationIndicator;
    Boolean multiUse;
    Boolean displaySelector;
    String templateName;
    URL css;
    boolean paymentCardGroupingEnabled;

    Set<String> groupedPaymentCardProductCodes;

    public final static String PaymentPageRequestTemplateNameBasic = "basic-js";
    public final static String PaymentPageRequestTemplateNameFrame = "iframe-js";

    public PaymentPageRequest() {

        this.setAuthenticationIndicator(CardTokenPaymentMethodRequest.AuthenticationIndicator.AuthenticationIndicatorUndefined);
        this.setEci(Transaction.ECI.ECIUndefined);
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

    public String getStringParameters() {

        PaymentPageRequest.PaymentPageRequestSerializationMapper mapper = new PaymentPageRequest.PaymentPageRequestSerializationMapper(this);
        return mapper.getQueryString();
    }

    public boolean isPaymentCardGroupingEnabled() {
        return paymentCardGroupingEnabled;
    }

    public void setPaymentCardGroupingEnabled(boolean paymentCardGroupingEnabled) {
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

    public void setDisplaySelector(boolean displaySelector) {
        this.displaySelector = displaySelector;
    }

    public List getPaymentProductList() {
        return paymentProductList;
    }

    public void setPaymentProductList(List paymentProductList) {
        this.paymentProductList = paymentProductList;
    }

    public List getPaymentProductCategoryList() {
        return paymentProductCategoryList;
    }

    public void setPaymentProductCategoryList(List paymentProductCategoryList) {
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

    public static class PaymentPageRequestSerializationMapper extends AbstractSerializationMapper {

        protected PaymentPageRequestSerializationMapper(PaymentPageRequest request) {
            super(request);
        }

        @Override
        protected String getQueryString() {

            return super.getQueryString();
        }
    }
}