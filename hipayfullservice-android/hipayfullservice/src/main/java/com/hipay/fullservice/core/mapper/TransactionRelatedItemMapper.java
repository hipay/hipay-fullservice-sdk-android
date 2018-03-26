package com.hipay.fullservice.core.mapper;

import com.hipay.fullservice.core.models.TransactionRelatedItem;
import com.hipay.fullservice.core.utils.enums.TransactionStatus;

/**
 * Created by nfillion on 08/09/16.
 */

public abstract class TransactionRelatedItemMapper extends AbstractMapper {
    public TransactionRelatedItemMapper(Object rawData) {
        super(rawData);
    }

    @Override
    public boolean isValid() {
        return this.getStringForKey("transactionReference") != null;
    }

    public TransactionRelatedItem mappedObject() {

        TransactionRelatedItem object = new TransactionRelatedItem();

        object.setTest(this.getBoolForKey("test"));
        object.setMid(this.getStringForKey("mid"));
        object.setAuthorizationCode(this.getStringForKey("authorizationCode"));
        object.setTransactionReference(this.getStringForKey("transactionReference"));
        object.setDateCreated(this.getDateForKey("dateCreated"));
        object.setDateUpdated(this.getDateForKey("dateUpdated"));
        object.setDateAuthorized(this.getDateForKey("dateAuthorized"));

        Integer statusInteger = this.getIntegerForKey("status");
        TransactionStatus status = TransactionStatus.fromIntegerValue(statusInteger);
        if (status == null) {
            status = TransactionStatus.TRANSACTION_STATUS_UNKNOWN;
        }
        object.setStatus(status);

        object.setMessage(this.getStringForKey("message"));
        object.setAuthorizedAmount(this.getFloatForKey("authorizedAmount"));
        object.setCapturedAmount(this.getFloatForKey("capturedAmount"));
        object.setRefundedAmount(this.getFloatForKey("refundedAmount"));
        object.setDecimals(this.getFloatForKey("decimals"));
        object.setCurrency(this.getStringForKey("currency"));

        return object;
    }
}
