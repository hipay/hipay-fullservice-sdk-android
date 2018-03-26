package com.hipay.fullservice.core.requests.securevault;

import com.hipay.fullservice.core.requests.AbstractRequest;

/**
 * Created by nfillion on 09/03/16.
 */
public class GenerateTokenRequest extends AbstractRequest {

    public GenerateTokenRequest(String cardNumber, String cardExpiryMonth, String cardExpiryYear, String cardHolder, String cardCVV, boolean multiUse) {

        this.setCardNumber(cardNumber);
        this.setCardExpiryMonth(cardExpiryMonth);
        this.setCardExpiryYear(cardExpiryYear);
        this.setCardHolder(cardHolder);
        this.setCardCVC(cardCVV);
        this.setMultiUse(multiUse);
    }

    protected String cardNumber;
    protected String cardExpiryMonth;
    protected String cardExpiryYear;
    protected String cardHolder;
    protected String cardCVC;
    protected Boolean multiUse;

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
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

    public String getCardCVC() {
        return cardCVC;
    }

    public void setCardCVC(String cardCVC) {
        this.cardCVC = cardCVC;
    }

    public Boolean getMultiUse() {
        return multiUse;
    }

    public void setMultiUse(Boolean multiUse) {
        this.multiUse = multiUse;
    }

}
