package com.hipay.fullservice.core.serialization.interfaces;

import android.os.Bundle;

import com.hipay.fullservice.core.models.TransactionRelatedItem;
import com.hipay.fullservice.core.utils.enums.TransactionStatus;

/**
 * Created by nfillion on 08/09/16.
 */

public abstract class TransactionRelatedItemSerialization extends AbstractSerialization {

    public TransactionRelatedItemSerialization(TransactionRelatedItem transactionRelatedItem) {
        super(transactionRelatedItem);
    }

    public Bundle getSerializedBundle() {
        TransactionRelatedItem object = (TransactionRelatedItem) this.getModel();

        this.bundle.putBool("test", object.getTest());
        this.bundle.putString("mid", object.getMid());
        this.bundle.putString("authorizationCode", object.getAuthorizationCode());
        this.bundle.putString("transactionReference", object.getTransactionReference());
        this.bundle.putDate("dateCreated", object.getDateCreated());
        this.bundle.putDate("dateUpdated", object.getDateUpdated());
        this.bundle.putDate("dateAuthorized", object.getDateUpdated());

        TransactionStatus status = object.getStatus();
        if (status != null) {
            this.bundle.putInt("status", object.getStatus().getValue());
        }

        this.bundle.putString("message", object.getMessage());
        this.bundle.putFloat("authorizedAmount", object.getAuthorizedAmount());
        this.bundle.putFloat("capturedAmount", object.getCapturedAmount());
        this.bundle.putFloat("refundedAmount", object.getRefundedAmount());

        this.bundle.putFloat("decimals", object.getDecimals());
        this.bundle.putString("currency", object.getCurrency());

        return this.getBundle();
    }
}

