package com.hipay.fullservice.core.requests.securevault;

import com.hipay.fullservice.core.requests.AbstractRequest;
import com.hipay.fullservice.core.serialization.AbstractSerializationMapper;

/**
 * Created by HiPay on 09/03/16.
 */
public class UpdatePaymentCardRequest extends AbstractRequest {

    public UpdatePaymentCardRequest(String token, String requestId, String cardExpiryMonth, String cardExpiryYear, String cardHolder) {

        this.setToken(token);
        this.setRequestId(requestId);
        this.setCardExpiryMonth(cardExpiryMonth);
        this.setCardExpiryYear(cardExpiryYear);
        this.setCardHolder(cardHolder);
        this.setSecurityCode(null);
    }

    public UpdatePaymentCardRequest(String token, String requestId, String cardExpiryMonth, String cardExpiryYear, String cardHolder, String securityCode) {

        this.setToken(token);
        this.setRequestId(requestId);
        this.setCardExpiryMonth(cardExpiryMonth);
        this.setCardExpiryYear(cardExpiryYear);
        this.setCardHolder(cardHolder);
        this.setSecurityCode(securityCode);
    }

    protected String token;
    protected String requestId;
    protected String cardExpiryMonth;
    protected String cardExpiryYear;
    protected String cardHolder;
    protected String securityCode;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getCardExpiryMonth() {
        return cardExpiryMonth;
    }

    public void setCardExpiryMonth(String cardExpiryMonth) {
        this.cardExpiryMonth = cardExpiryMonth;
    }

    public String getCardExpiryYear() {
        return cardExpiryYear;
    }

    public void setCardExpiryYear(String cardExpiryYear) {
        this.cardExpiryYear = cardExpiryYear;
    }

    public String getCardHolder() {
        return cardHolder;
    }

    public void setCardHolder(String cardHolder) {
        this.cardHolder = cardHolder;
    }

    public String getSecurityCode() {
        return securityCode;
    }

    public void setSecurityCode(String securityCode) {
        this.securityCode = securityCode;
    }

    public String getStringParameters() {

        UpdatePaymentCardRequest.UpdatePaymentCardRequestSerializationMapper mapper = new UpdatePaymentCardRequest.UpdatePaymentCardRequestSerializationMapper(this);
        return mapper.getQueryString();
    }

    public static class UpdatePaymentCardRequestSerializationMapper extends AbstractSerializationMapper {

        public UpdatePaymentCardRequestSerializationMapper(AbstractRequest request) {
            super(request);
        }

        @Override
        public String getQueryString() {
            return super.getQueryString();
        }
    }
}

