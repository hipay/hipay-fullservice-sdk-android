package com.hipay.fullservice.core.requests.securevault;

import com.hipay.fullservice.core.requests.AbstractRequest;

/**
 * Created by nfillion on 09/03/16.
 */
public class UpdatePaymentCardRequest extends AbstractRequest {

    public UpdatePaymentCardRequest(String token, String requestId, String cardExpiryMonth, String cardExpiryYear, String cardHolder) {

        this.setToken(token);
        this.setRequestId(requestId);
        this.setCardExpiryMonth(cardExpiryMonth);
        this.setCardExpiryYear(cardExpiryYear);
        this.setCardHolder(cardHolder);
    }

    protected String token;
    protected String requestId;
    protected String cardExpiryMonth;
    protected String cardExpiryYear;
    protected String cardHolder;

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

}

