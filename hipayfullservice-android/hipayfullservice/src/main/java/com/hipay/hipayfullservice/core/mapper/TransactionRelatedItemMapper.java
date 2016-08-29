package com.hipay.hipayfullservice.core.mapper;

import com.hipay.hipayfullservice.core.mapper.interfaces.MapMapper;
import com.hipay.hipayfullservice.core.models.TransactionRelatedItem;

/**
 * Created by nfillion on 08/09/16.
 */

public abstract class TransactionRelatedItemMapper extends AbstractMapper {
    public TransactionRelatedItemMapper(Object rawData) {
        super(rawData);
    }

    @Override
    protected boolean isValid() {

        if (this.getBehaviour() instanceof MapMapper) {

            if (this.getStringForKey("transactionReference") != null) {
                return true;
            }
        }

        return true;
    }

    protected TransactionRelatedItem mappedObject() {

        TransactionRelatedItem object = new TransactionRelatedItem();

        object.setTest(this.getBoolForKey("test"));
        object.setMid(this.getStringForKey("mid"));
        object.setAuthorizationCode(this.getStringForKey("authorizationCode"));
        object.setTransactionReference(this.getStringForKey("transactionReference"));
        object.setDateCreated(this.getDateForKey("dateCreated"));
        object.setDateUpdated(this.getDateForKey("dateUpdated"));
        object.setDateAuthorized(this.getDateForKey("dateAuthorized"));

        Integer statusInteger = this.getIntegerForKey("status");
        TransactionRelatedItem.TransactionStatus status = TransactionRelatedItem.TransactionStatus.fromIntegerValue(statusInteger);
        if (status == null) {
            status = TransactionRelatedItem.TransactionStatus.TransactionStatusUnknown;
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

    @Override
    protected TransactionRelatedItem mappedObjectFromBundle() {

        TransactionRelatedItem object = new TransactionRelatedItem();

        object.setTest(this.getBoolForKey("test"));
        object.setMid(this.getStringForKey("mid"));
        object.setAuthorizationCode(this.getStringForKey("authorizationCode"));
        object.setTransactionReference(this.getStringForKey("transactionReference"));
        object.setDateCreated(this.getDateForKey("dateCreated"));
        object.setDateUpdated(this.getDateForKey("dateUpdated"));
        object.setDateAuthorized(this.getDateForKey("dateAuthorized"));

        Integer statusInteger = this.getIntegerForKey("status");
        TransactionRelatedItem.TransactionStatus status = TransactionRelatedItem.TransactionStatus.fromIntegerValue(statusInteger);
        if (status == null) {
            status = TransactionRelatedItem.TransactionStatus.TransactionStatusUnknown;
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
