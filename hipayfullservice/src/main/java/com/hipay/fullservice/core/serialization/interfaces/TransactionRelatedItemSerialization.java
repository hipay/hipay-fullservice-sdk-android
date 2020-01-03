package com.hipay.fullservice.core.serialization.interfaces;

import android.os.Bundle;

import com.hipay.fullservice.core.models.TransactionRelatedItem;

import java.util.Map;

/**
 * Created by HiPay on 08/09/16.
 */

public abstract class TransactionRelatedItemSerialization extends AbstractSerialization {

    public TransactionRelatedItemSerialization(TransactionRelatedItem transactionRelatedItem) {
        super(transactionRelatedItem);
    }

    @Override
    public String getQueryString() {
        return null;
    }

    @Override
    public Map<String, String> getSerializedRequest() {
        // we don't send it as a queryString
        return null;
    }

    @Override
    public Bundle getSerializedBundle() {
        super.getSerializedBundle();

        TransactionRelatedItem object = (TransactionRelatedItem) this.getModel();

        this.putBoolForKey("test", object.getTest());
        this.putStringForKey("mid", object.getMid());
        this.putStringForKey("authorizationCode", object.getAuthorizationCode());
        this.putStringForKey("transactionReference", object.getTransactionReference());
        this.putDateForKey("dateCreated", object.getDateCreated());
        this.putDateForKey("dateUpdated", object.getDateUpdated());
        this.putDateForKey("dateAuthorized", object.getDateUpdated());

        TransactionRelatedItem.TransactionStatus status = object.getStatus();
        if (status != null) {
            this.putIntForKey("status", object.getStatus().getIntegerValue());
        }

        this.putStringForKey("message", object.getMessage());
        this.putFloatForKey("authorizedAmount", object.getAuthorizedAmount());
        this.putFloatForKey("capturedAmount", object.getCapturedAmount());
        this.putFloatForKey("refundedAmount", object.getRefundedAmount());

        this.putFloatForKey("decimals", object.getDecimals());
        this.putStringForKey("currency", object.getCurrency());

        return this.getBundle();
    }
}

