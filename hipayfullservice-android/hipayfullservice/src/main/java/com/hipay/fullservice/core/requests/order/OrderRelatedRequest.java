package com.hipay.fullservice.core.requests.order;

import android.os.Build;
import android.os.Bundle;

import com.hipay.fullservice.BuildConfig;
import com.hipay.fullservice.core.client.config.ClientConfig;
import com.hipay.fullservice.core.mapper.AbstractMapper;
import com.hipay.fullservice.core.mapper.interfaces.BundleMapper;
import com.hipay.fullservice.core.requests.AbstractRequest;
import com.hipay.fullservice.core.requests.info.CustomerInfoRequest;
import com.hipay.fullservice.core.requests.info.PersonalInfoRequest;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * Created by nfillion on 02/02/16.
 */
public class OrderRelatedRequest extends AbstractRequest {

    protected String orderId;

    OrderRequestOperation operation;

    protected String shortDescription;
    protected String longDescription;
    protected String currency;
    protected Float amount;
    protected Float shipping;
    protected Float tax;
    protected String clientId;
    protected String ipAddress;

    protected String acceptScheme;
    protected String declineScheme;
    protected String pendingScheme;
    protected String exceptionScheme;
    protected String cancelScheme;

    protected String HTTPAccept;
    protected String HTTPUserAgent;
    protected String deviceFingerprint;
    protected String language;

    protected CustomerInfoRequest customer;
    protected PersonalInfoRequest shippingAddress;

    protected Map<String, String> customData;

    protected String cdata1;
    protected String cdata2;
    protected String cdata3;
    protected String cdata4;
    protected String cdata5;
    protected String cdata6;
    protected String cdata7;
    protected String cdata8;
    protected String cdata9;
    protected String cdata10;

    protected Map<String,String> source;

    public static final String OrderRelatedRequestRedirectPathAccept = "accept";
    public static final String OrderRelatedRequestRedirectPathDecline = "decline";
    public static final String OrderRelatedRequestRedirectPathPending = "pending";
    public static final String OrderRelatedRequestRedirectPathException = "exception";
    public static final String OrderRelatedRequestRedirectPathCancel = "cancel";

    public static final String GatewayCallbackURLPathName = "gateway";
    public static final String GatewayCallbackURLOrderPathName = "orders";

    public OrderRelatedRequest() {

        this.setLanguage(Locale.getDefault().toString());
        this.setHTTPUserAgent(ClientConfig.getInstance().getUserAgent());

        this.setCustomer(new CustomerInfoRequest());
        this.setShippingAddress(new PersonalInfoRequest());

        Map<String,String> sourceMap = new HashMap<>(4);
        sourceMap.put("source",  "CSDK");
        sourceMap.put("brand", "android");
        sourceMap.put("brand_version", Build.VERSION.RELEASE);
        sourceMap.put("integration_version", BuildConfig.VERSION_NAME);

        this.setSource(sourceMap);

    }

    public OrderRelatedRequest(OrderRelatedRequest orderRelatedRequest) {

        this.setLanguage(Locale.getDefault().toString());
        this.setHTTPUserAgent(ClientConfig.getInstance().getUserAgent());

        this.setCustomer(new CustomerInfoRequest());
        this.setShippingAddress(new PersonalInfoRequest());

        this.setSource(orderRelatedRequest.getSource());

        this.setOrderId(orderRelatedRequest.getOrderId());
        this.setOperation(orderRelatedRequest.getOperation());
        this.setShortDescription(orderRelatedRequest.getShortDescription());
        this.setLongDescription(orderRelatedRequest.getLongDescription());
        this.setCurrency(orderRelatedRequest.getCurrency());
        this.setAmount(orderRelatedRequest.getAmount());
        this.setShipping(orderRelatedRequest.getShipping());
        this.setTax(orderRelatedRequest.getTax());
        this.setClientId(orderRelatedRequest.getClientId());
        this.setIpAddress(orderRelatedRequest.getIpAddress());
        this.setAcceptScheme(orderRelatedRequest.getAcceptScheme());
        this.setDeclineScheme(orderRelatedRequest.getDeclineScheme());
        this.setPendingScheme(orderRelatedRequest.getPendingScheme());
        this.setExceptionScheme(orderRelatedRequest.getExceptionScheme());
        this.setCancelScheme(orderRelatedRequest.getCancelScheme());
        this.setHTTPUserAgent(orderRelatedRequest.getHTTPUserAgent());
        this.setDeviceFingerprint(orderRelatedRequest.getDeviceFingerprint());
        this.setLanguage(orderRelatedRequest.getLanguage());

        this.setCustomer(orderRelatedRequest.getCustomer());
        this.setShippingAddress(orderRelatedRequest.getShippingAddress());

        this.setCustomData(orderRelatedRequest.getCustomData());

        this.setCdata1(orderRelatedRequest.getCdata1());
        this.setCdata2(orderRelatedRequest.getCdata2());
        this.setCdata3(orderRelatedRequest.getCdata3());
        this.setCdata4(orderRelatedRequest.getCdata4());
        this.setCdata5(orderRelatedRequest.getCdata5());
        this.setCdata6(orderRelatedRequest.getCdata6());
        this.setCdata7(orderRelatedRequest.getCdata7());
        this.setCdata8(orderRelatedRequest.getCdata8());
        this.setCdata9(orderRelatedRequest.getCdata9());
        this.setCdata10(orderRelatedRequest.getCdata10());

    }

    private static final String ClientConfigCallbackURLHost = "hipay-fullservice";

    private void initURLParameters() {

        StringBuilder appSchemeBuilder = new StringBuilder("hipay")
                .append("://")
                .append(ClientConfigCallbackURLHost)

                .append("/")
                .append(GatewayCallbackURLPathName)
                .append("/")
                .append(GatewayCallbackURLOrderPathName)
                .append("/");

        if (this.getOrderId() != null) {
            appSchemeBuilder.append(this.getOrderId()).append("/");
        }

        this.setAcceptScheme(appSchemeBuilder.toString().concat(OrderRelatedRequestRedirectPathAccept));
        this.setDeclineScheme(appSchemeBuilder.toString().concat(OrderRelatedRequestRedirectPathDecline));
        this.setPendingScheme(appSchemeBuilder.toString().concat(OrderRelatedRequestRedirectPathPending));
        this.setCancelScheme(appSchemeBuilder.toString().concat(OrderRelatedRequestRedirectPathCancel));
        this.setExceptionScheme(appSchemeBuilder.toString().concat(OrderRelatedRequestRedirectPathException));

    }

    public enum OrderRequestOperation {

        Undefined(null),
        Authorization("Authorization"), Sale("Sale");

        protected final String operation;

        OrderRequestOperation(String operation)
        {
            this.operation = operation;
        }

        public static OrderRequestOperation fromStringValue(String value) {

            if (value == null) return null;

            if (value.equals(Authorization.getStringValue())) {
                return Authorization;
            }

            if (value.equals(Sale.getStringValue())) {
                return Sale;
            }

            return null;
        }

        public String getStringValue()
        {
            return this.operation;
        }
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
        this.initURLParameters();
    }

    public OrderRequestOperation getOperation() {
        return operation;
    }

    public void setOperation(OrderRequestOperation operation) {
        this.operation = operation;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getLongDescription() {
        return longDescription;
    }

    public void setLongDescription(String longDescription) {
        this.longDescription = longDescription;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Float getAmount() {
        return amount;
    }

    public void setAmount(Float amount) {
        this.amount = amount;
    }

    public Float getShipping() {
        return shipping;
    }

    public void setShipping(Float shipping) {
        this.shipping = shipping;
    }

    public Float getTax() {
        return tax;
    }

    public void setTax(Float tax) {
        this.tax = tax;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getAcceptScheme() {
        return acceptScheme;
    }

    public void setAcceptScheme(String acceptScheme) {
        this.acceptScheme = acceptScheme;
    }

    public String getDeclineScheme() {
        return declineScheme;
    }

    public void setDeclineScheme(String declineScheme) {
        this.declineScheme = declineScheme;
    }

    public String getPendingScheme() {
        return pendingScheme;
    }

    public void setPendingScheme(String pendingScheme) {
        this.pendingScheme = pendingScheme;
    }

    public String getExceptionScheme() {
        return exceptionScheme;
    }

    public void setExceptionScheme(String exceptionScheme) {
        this.exceptionScheme = exceptionScheme;
    }

    public String getCancelScheme() {
        return cancelScheme;
    }

    public void setCancelScheme(String cancelScheme) {
        this.cancelScheme = cancelScheme;
    }

    public String getHTTPAccept() {
        return HTTPAccept;
    }

    public void setHTTPAccept(String HTTPAccept) {
        this.HTTPAccept = HTTPAccept;
    }

    public String getHTTPUserAgent() {
        return HTTPUserAgent;
    }

    public void setHTTPUserAgent(String HTTPUserAgent) {
        this.HTTPUserAgent = HTTPUserAgent;
    }

    public String getDeviceFingerprint() {
        return deviceFingerprint;
    }

    public void setDeviceFingerprint(String deviceFingerprint) {
        this.deviceFingerprint = deviceFingerprint;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public Map<String, String> getCustomData() {
        return customData;
    }

    public void setCustomData(Map<String, String> customData) {
        this.customData = customData;
    }

    public String getCdata1() {
        return cdata1;
    }

    public void setCdata1(String cdata1) {
        this.cdata1 = cdata1;
    }

    public String getCdata2() {
        return cdata2;
    }

    public void setCdata2(String cdata2) {
        this.cdata2 = cdata2;
    }

    public String getCdata3() {
        return cdata3;
    }

    public void setCdata3(String cdata3) {
        this.cdata3 = cdata3;
    }

    public String getCdata4() {
        return cdata4;
    }

    public void setCdata4(String cdata4) {
        this.cdata4 = cdata4;
    }

    public String getCdata5() {
        return cdata5;
    }

    public void setCdata5(String cdata5) {
        this.cdata5 = cdata5;
    }

    public String getCdata6() {
        return cdata6;
    }

    public void setCdata6(String cdata6) {
        this.cdata6 = cdata6;
    }

    public String getCdata7() {
        return cdata7;
    }

    public void setCdata7(String cdata7) {
        this.cdata7 = cdata7;
    }

    public String getCdata8() {
        return cdata8;
    }

    public void setCdata8(String cdata8) {
        this.cdata8 = cdata8;
    }

    public String getCdata9() {
        return cdata9;
    }

    public void setCdata9(String cdata9) {
        this.cdata9 = cdata9;
    }

    public String getCdata10() {
        return cdata10;
    }

    public void setCdata10(String cdata10) {

        this.cdata10 = cdata10;
    }

    public Map<String, String> getSource() {
        return source;
    }

    private void setSource(Map<String, String> source) {
        this.source = source;
    }

    public CustomerInfoRequest getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerInfoRequest customer) {
        this.customer = customer;
    }

    public PersonalInfoRequest getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(PersonalInfoRequest shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    protected static class OrderRelatedRequestMapper extends AbstractMapper {
        public OrderRelatedRequestMapper(Bundle object) {
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
        protected OrderRelatedRequest mappedObjectFromBundle() {

            OrderRelatedRequest orderRelatedRequest = new OrderRelatedRequest();

            orderRelatedRequest.setOrderId(this.getStringForKey("orderid"));

            String operationValue = this.getStringForKey("operation");
            if (operationValue != null) {
                orderRelatedRequest.setOperation(OrderRequestOperation.fromStringValue(operationValue));
            }

            orderRelatedRequest.setShortDescription(this.getStringForKey("description"));
            orderRelatedRequest.setLongDescription(this.getStringForKey("long_description"));
            orderRelatedRequest.setCurrency(this.getStringForKey("currency"));
            orderRelatedRequest.setAmount(this.getFloatForKey("amount"));

            orderRelatedRequest.setShipping(this.getFloatForKey("shipping"));
            orderRelatedRequest.setTax(this.getFloatForKey("tax"));

            orderRelatedRequest.setClientId(this.getStringForKey("cid"));
            orderRelatedRequest.setIpAddress(this.getStringForKey("ipaddr"));

            orderRelatedRequest.setHTTPAccept(this.getStringForKey("http_accept"));
            orderRelatedRequest.setHTTPUserAgent(this.getStringForKey("http_user_agent"));
            orderRelatedRequest.setDeviceFingerprint(this.getStringForKey("device_fingerprint"));
            orderRelatedRequest.setLanguage(this.getStringForKey("language"));

            orderRelatedRequest.setAcceptScheme(this.getStringForKey("accept_url"));
            orderRelatedRequest.setDeclineScheme(this.getStringForKey("decline_url"));
            orderRelatedRequest.setPendingScheme(this.getStringForKey("pending_url"));
            orderRelatedRequest.setExceptionScheme(this.getStringForKey("exception_url"));
            orderRelatedRequest.setCancelScheme(this.getStringForKey("cancel_url"));

            orderRelatedRequest.setCustomData(this.getMapJSONForKey("custom_data"));

            orderRelatedRequest.setCdata1(this.getStringForKey("cdata1"));
            orderRelatedRequest.setCdata2(this.getStringForKey("cdata2"));
            orderRelatedRequest.setCdata3(this.getStringForKey("cdata3"));
            orderRelatedRequest.setCdata4(this.getStringForKey("cdata4"));
            orderRelatedRequest.setCdata5(this.getStringForKey("cdata5"));
            orderRelatedRequest.setCdata6(this.getStringForKey("cdata6"));
            orderRelatedRequest.setCdata7(this.getStringForKey("cdata7"));
            orderRelatedRequest.setCdata8(this.getStringForKey("cdata8"));
            orderRelatedRequest.setCdata9(this.getStringForKey("cdata9"));
            orderRelatedRequest.setCdata10(this.getStringForKey("cdata10"));

            Bundle customerBundle = this.getBundleForKey("customer");
            CustomerInfoRequest customerInfoRequest = null;
            if (customerBundle != null) {
                customerInfoRequest = CustomerInfoRequest.fromBundle(customerBundle);
            }
            orderRelatedRequest.setCustomer(customerInfoRequest);

            Bundle shippingBundle = this.getBundleForKey("shipping_address");
            PersonalInfoRequest personalInfoRequest = null;
            if (shippingBundle != null) {
                personalInfoRequest = PersonalInfoRequest.fromBundle(shippingBundle);
            }
            orderRelatedRequest.setShippingAddress(personalInfoRequest);

            orderRelatedRequest.setSource(this.getMapJSONForKey("source"));

            return orderRelatedRequest;
        }

        @Override
        protected Object mappedObjectFromUri() {
            return null;
        }

        @Override
        protected OrderRelatedRequest mappedObject() {

            return null;
            //actually this won't come from JSON
        }
    }
}
