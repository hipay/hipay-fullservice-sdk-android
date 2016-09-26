package com.hipay.fullservice.core.requests.payment;

/**
 * Created by nfillion on 03/02/16.
 */
public class QiwiWalletPaymentMethodRequest extends AbstractPaymentMethodRequest {

    public QiwiWalletPaymentMethodRequest() {
    }

    public QiwiWalletPaymentMethodRequest(String username) {
        this.username = username;
    }

    protected String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
