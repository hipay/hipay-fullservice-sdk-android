package com.hipay.fullservice.core.requests.order;

import android.os.Bundle;

import com.hipay.fullservice.core.mapper.interfaces.BundleMapper;
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

    protected static class PaymentPageRequestMapper extends OrderRelatedRequestMapper {
        public PaymentPageRequestMapper(Bundle object) {
            super(object);
        }

        @Override
        protected boolean isValid() {

            if (this.getBehaviour() instanceof BundleMapper) {

                return true;
            }
            return false;
        }

        @Override
        protected PaymentPageRequest mappedObject() {

            //we don't receive it from json
            return null;
        }

        @Override
        protected PaymentPageRequest mappedObjectFromBundle() {

            OrderRelatedRequest orderRelatedRequest = super.mappedObjectFromBundle();
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
            Transaction.ECI eci = Transaction.ECI.fromIntegerValue(eciInt);
            paymentPageRequest.setEci(eci);

            Integer authenticationIndicatorInteger = this.getIntegerForKey("authentication_indicator");
            CardTokenPaymentMethodRequest.AuthenticationIndicator authenticationIndicator = CardTokenPaymentMethodRequest.AuthenticationIndicator.fromIntegerValue(authenticationIndicatorInteger);
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
}