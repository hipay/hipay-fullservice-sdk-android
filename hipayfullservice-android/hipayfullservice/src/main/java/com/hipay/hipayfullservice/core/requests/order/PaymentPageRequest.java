package com.hipay.hipayfullservice.core.requests.order;

import android.os.Bundle;

import com.hipay.hipayfullservice.core.mapper.interfaces.BundleMapper;
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

    public static final String TAG = "Payment_page_request";
    public static final int REQUEST_ORDER = 0x2300;

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

    public PaymentPageRequest(PaymentPageRequest paymentPageRequest) {
        super(paymentPageRequest);
    }

    public static PaymentPageRequest fromBundle(Bundle bundle) {

        PaymentPageRequestMapper mapper = new PaymentPageRequestMapper(bundle);
        return mapper.mappedObjectFromBundle();
    }

    public Bundle toBundle() {

        PaymentPageRequest.PaymentPageRequestSerializationMapper mapper = new PaymentPageRequest.PaymentPageRequestSerializationMapper(this);
        return mapper.getSerializedBundle();
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

    protected static class PaymentPageRequestMapper extends OrderRelatedRequestMapper {
        public PaymentPageRequestMapper(Bundle object) {
            super(object);
        }

        @Override
        protected boolean isValid() {

            if (this.getBehaviour() instanceof BundleMapper) {

                //TODO add more validation
                return true;
            }

            return true;
        }

        @Override
        protected PaymentPageRequest mappedObject() {

            return null;
        }

        @Override
        protected PaymentPageRequest mappedObjectFromBundle() {
            //TODO get mapped object from transactionRelatedItem superclass

            //PaymentPageRequest paymentPageRequest = (PaymentPageRequest)super.mappedObject();
            PaymentPageRequest paymentPageRequest = this.pageRequestFromRelatedRequest(super.mappedObjectFromBundle());

            //TODO handle this
            //relatedRequestMap.put("payment_product_list", null);
            //relatedRequestMap.put("payment_product_category_list", null);

            //relatedRequestMap.put("eci", null);
            //relatedRequestMap.put("authentication_indicator", null);

            //TODO check what multiUse returns

            paymentPageRequest.setMultiUse(this.getBoolForKey("multi_use"));
            paymentPageRequest.setDisplaySelector(this.getBoolForKey("display_selector"));
            paymentPageRequest.setTemplateName(this.getStringForKey("template"));

            return paymentPageRequest;
        }

        private PaymentPageRequest pageRequestFromRelatedRequest(OrderRelatedRequest orderRelatedRequest) {

            PaymentPageRequest paymentPageRequest = new PaymentPageRequest();

            //TODO handle this
            //relatedRequestMap.put("payment_product_list", null);
            //relatedRequestMap.put("payment_product_category_list", null);

            //relatedRequestMap.put("eci", null);
            //relatedRequestMap.put("authentication_indicator", null);

            //TODO check what multiUse returns
            //TODO get tax

            paymentPageRequest.setOrderId(orderRelatedRequest.getOrderId());

            paymentPageRequest.setOperation(orderRelatedRequest.getOperation());

            paymentPageRequest.setShortDescription(orderRelatedRequest.getShortDescription());
            paymentPageRequest.setLongDescription(orderRelatedRequest.getLongDescription());
            paymentPageRequest.setCurrency(orderRelatedRequest.getCurrency());
            paymentPageRequest.setAmount(orderRelatedRequest.getAmount());

            //TODO amount, shipping, tax

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
}