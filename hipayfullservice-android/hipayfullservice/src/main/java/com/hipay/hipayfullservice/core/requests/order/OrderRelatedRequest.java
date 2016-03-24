package com.hipay.hipayfullservice.core.requests.order;

import android.os.Bundle;

import com.hipay.hipayfullservice.core.client.config.ClientConfig;
import com.hipay.hipayfullservice.core.mapper.AbstractMapper;
import com.hipay.hipayfullservice.core.mapper.interfaces.BundleMapper;
import com.hipay.hipayfullservice.core.requests.AbstractRequest;
import com.hipay.hipayfullservice.core.requests.info.CustomerInfoRequest;
import com.hipay.hipayfullservice.core.requests.info.PersonalInfoRequest;
import com.hipay.hipayfullservice.core.utils.Utils;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
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

    protected URL acceptURL;
    protected URL declineURL;
    protected URL pendingURL;
    protected URL exceptionURL;
    protected URL cancelURL;

    protected String HTTPAccept;
    protected String HTTPUserAgent;
    protected String deviceFingerprint;
    protected String language;

    protected CustomerInfoRequest customer;
    protected PersonalInfoRequest shippingAddress;

    protected Map customData;

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

    private static final String OrderRelatedRequestRedirectPathAccept = "accept";
    private static final String OrderRelatedRequestRedirectPathDecline = "decline";
    private static final String OrderRelatedRequestRedirectPathPending = "pending";
    private static final String OrderRelatedRequestRedirectPathException = "exception";
    private static final String OrderRelatedRequestRedirectPathCancel = "cancel";

    public OrderRelatedRequest() {

        this.setLanguage(Locale.getDefault().toString());
        this.setHTTPUserAgent(ClientConfig.getInstance().getUserAgent());

        this.setCustomer(new CustomerInfoRequest());
        this.setShippingAddress(new PersonalInfoRequest());

        try {
            this.initURLParameters();
        } catch (MalformedURLException e) {
            e.printStackTrace();
            //TODO handle exception

        } catch (URISyntaxException e) {

            e.printStackTrace();
            //TODO handle exception
        }
    }

    public OrderRelatedRequest(OrderRelatedRequest orderRelatedRequest) {

        this.setLanguage(Locale.getDefault().toString());
        this.setHTTPUserAgent(ClientConfig.getInstance().getUserAgent());

        this.setCustomer(new CustomerInfoRequest());
        this.setShippingAddress(new PersonalInfoRequest());

        try {
            this.initURLParameters();
        } catch (MalformedURLException e) {
            e.printStackTrace();
            //TODO handle exception

        } catch (URISyntaxException e) {

            e.printStackTrace();
            //TODO handle exception
        }

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
        this.setAcceptURL(orderRelatedRequest.getAcceptURL());
        this.setDeclineURL(orderRelatedRequest.getDeclineURL());
        this.setPendingURL(orderRelatedRequest.getPendingURL());
        this.setExceptionURL(orderRelatedRequest.getExceptionURL());
        this.setCancelURL(orderRelatedRequest.getCancelURL());
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

    private void initURLParameters() throws MalformedURLException, URISyntaxException {

        URL appURL = ClientConfig.getInstance().getAppRedirectionURL();

        StringBuilder stringBuilder = new StringBuilder("/")
                .append(ClientConfig.GatewayCallbackURLPathName)
                .append("/")
                .append(ClientConfig.GatewayCallbackURLOrderPathName);

        if (this.getOrderId() != null) {
            stringBuilder.append("/").append(this.getOrderId());
        }

        Utils.concatenatePath(appURL, stringBuilder.toString());

        if (appURL != null) {

            this.setAcceptURL(Utils.concatenatePath(appURL, OrderRelatedRequestRedirectPathAccept));
            this.setDeclineURL(Utils.concatenatePath(appURL, OrderRelatedRequestRedirectPathDecline));
            this.setPendingURL(Utils.concatenatePath(appURL, OrderRelatedRequestRedirectPathPending));
            this.setCancelURL(Utils.concatenatePath(appURL, OrderRelatedRequestRedirectPathCancel));
            this.setExceptionURL(Utils.concatenatePath(appURL, OrderRelatedRequestRedirectPathException));
        }

    }

    public enum OrderRequestOperation {

        OrderRequestOperationUndefined (0),
        OrderRequestOperationAuthorization (1),
        OrderRequestOperationSale (2);

        protected final Integer operation;
        OrderRequestOperation(Integer operation) {

            this.operation = operation;
        }

        public static OrderRequestOperation fromIntegerValue(Integer value) {

            if (value == null) return null;

            if (value == OrderRequestOperationAuthorization.getIntegerValue()) {
                return OrderRequestOperationAuthorization;
            }

            if (value == OrderRequestOperationSale.getIntegerValue()) {
                return OrderRequestOperationSale;
            }

            return null;
        }

        public Integer getIntegerValue() {
            return this.operation;
        }
    }


    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
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

    public URL getAcceptURL() {
        return acceptURL;
    }

    public void setAcceptURL(URL acceptURL) {
        this.acceptURL = acceptURL;
    }

    public URL getDeclineURL() {
        return declineURL;
    }

    public void setDeclineURL(URL declineURL) {
        this.declineURL = declineURL;
    }

    public URL getPendingURL() {
        return pendingURL;
    }

    public void setPendingURL(URL pendingURL) {
        this.pendingURL = pendingURL;
    }

    public URL getExceptionURL() {
        return exceptionURL;
    }

    public void setExceptionURL(URL exceptionURL) {
        this.exceptionURL = exceptionURL;
    }

    public URL getCancelURL() {
        return cancelURL;
    }

    public void setCancelURL(URL cancelURL) {
        this.cancelURL = cancelURL;
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

    public Map getCustomData() {
        return customData;
    }

    public void setCustomData(Map customData) {
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

                //TODO add more validation
                return true;
            }

            return false;
        }

        protected OrderRelatedRequest mappedObjectFromBundle() {

            //TODO get mapped object from transactionRelatedItem superclass

            //OrderRelatedRequest orderRelatedRequest = super.mappedObject();

            OrderRelatedRequest orderRelatedRequest = new OrderRelatedRequest();

            //TODO handle this
            //relatedRequestMap.put("payment_product_list", null);
            //relatedRequestMap.put("payment_product_category_list", null);

            //relatedRequestMap.put("eci", null);
            //relatedRequestMap.put("authentication_indicator", null);

            //TODO check what multiUse returns

            orderRelatedRequest.setOrderId(this.getStringForKey("orderid"));

            Integer operationValue = this.getIntegerForKey("operation");
            if (operationValue != null) {
                orderRelatedRequest.setOperation(OrderRequestOperation.fromIntegerValue(operationValue));
            }

            orderRelatedRequest.setShortDescription(this.getStringForKey("description"));
            orderRelatedRequest.setLongDescription(this.getStringForKey("long_description"));
            orderRelatedRequest.setCurrency(this.getStringForKey("currency"));
            orderRelatedRequest.setAmount(this.getFloatForKey("amount"));

            //retMap.put("tax", String.valueOf(orderRelatedRequest.getTax()));

            orderRelatedRequest.setClientId(this.getStringForKey("cid"));
            orderRelatedRequest.setIpAddress(this.getStringForKey("ipaddr"));
            orderRelatedRequest.setHTTPAccept(this.getStringForKey("http_accept"));
            orderRelatedRequest.setHTTPUserAgent(this.getStringForKey("http_user_agent"));
            orderRelatedRequest.setDeviceFingerprint(this.getStringForKey("device_fingerprint"));
            orderRelatedRequest.setLanguage(this.getStringForKey("language"));

            String acceptUrlValue = this.getStringForKey("accept_url");
            if (acceptUrlValue != null) {

                try {
                    URL acceptUrl = new URL(acceptUrlValue);
                    orderRelatedRequest.setAcceptURL(acceptUrl);

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
            }

            String declineUrlValue = this.getStringForKey("decline_url");
            if (declineUrlValue != null) {

                try {
                    URL declineUrl = new URL(declineUrlValue);
                    orderRelatedRequest.setDeclineURL(declineUrl);

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
            }

            String pendingUrlValue = this.getStringForKey("pending_url");
            if (pendingUrlValue != null) {

                try {
                    URL pendingUrl = new URL(pendingUrlValue);
                    orderRelatedRequest.setPendingURL(pendingUrl);

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
            }

            String exceptionUrlValue = this.getStringForKey("exception_url");
            if (exceptionUrlValue != null) {

                try {
                    URL exceptionUrl = new URL(exceptionUrlValue);
                    orderRelatedRequest.setExceptionURL(exceptionUrl);

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
            }

            String cancelUrlValue = this.getStringForKey("cancel_url");
            if (cancelUrlValue != null) {

                try {
                    URL cancelUrl = new URL(cancelUrlValue);
                    orderRelatedRequest.setCancelURL(cancelUrl);

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
            }

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

            return orderRelatedRequest;
        }

        @Override
        protected OrderRelatedRequest mappedObject() {
            return null;
        }
    }
}
