package com.hipay.hipayfullservice.core.models;

import android.os.Bundle;

import com.hipay.hipayfullservice.core.mapper.AbstractMapper;
import com.hipay.hipayfullservice.core.mapper.interfaces.MapMapper;
import com.hipay.hipayfullservice.core.serialization.interfaces.AbstractSerialization;

import java.util.Date;
import java.util.Map;

/**
 * Created by nfillion on 25/01/16.
 */
public class TransactionRelatedItem extends AbstractModel {

    protected Boolean test;
    protected String mid;
    protected String authorizationCode;
    protected String transactionReference;
    protected Date dateCreated;
    protected Date dateUpdated;
    protected Date dateAuthorized;
    protected TransactionStatus status;
    protected String message;
    protected Float authorizedAmount;
    protected Float capturedAmount;
    protected Float refundedAmount;
    protected Float decimals;
    protected String currency;

    public TransactionRelatedItem() {
    }

    public enum TransactionStatus {

        TransactionStatusUnknown (0),
        TransactionStatusCreated (101),
        TransactionStatusCardholderEnrolled (103),
        TransactionStatusCardholderNotEnrolled (104),
        TransactionStatusUnableToAuthenticate (105),
        TransactionStatusCardholderAuthenticated (106),
        TransactionStatusAuthenticationAttempted (107),
        TransactionStatusCouldNotAuthenticate (108),
        TransactionStatusAuthenticationFailed (109),
        TransactionStatusBlocked (110),
        TransactionStatusDenied (111),
        TransactionStatusAuthorizedAndPending (112),
        TransactionStatusRefused (113),
        TransactionStatusExpired (114),
        TransactionStatusCancelled (115),
        TransactionStatusAuthorized (116),
        TransactionStatusCaptureRequested (117),
        TransactionStatusCaptured (118),
        TransactionStatusPartiallyCaptured (119),
        TransactionStatusCollected (120),
        TransactionStatusPartiallyCollected (121),
        TransactionStatusSettled (122),
        TransactionStatusPartiallySettled (123),
        TransactionStatusRefundRequested (124),
        TransactionStatusRefunded (125),
        TransactionStatusPartiallyRefunded (126),
        TransactionStatusChargedBack (129),
        TransactionStatusDebited (131),
        TransactionStatusPartiallyDebited (132),
        TransactionStatusAuthenticationRequested (140),
        TransactionStatusAuthenticated (141),
        TransactionStatusAuthorizationRequested (142),
        TransactionStatusAcquirerFound (150),
        TransactionStatusAcquirernotFound (151),
        TransactionStatusCardholderEnrollmentUnknown (160),
        TransactionStatusRiskAccepted (161),
        TransactionStatusAuthorizationRefused (163),
        TransactionStatusCaptureRefused (173),
        TransactionStatusPendingPayment (200);

        protected final int status;
        TransactionStatus(int status) {
            this.status = status;
        }

        public Integer getIntegerValue() {
            return this.status;
        }

        public static TransactionStatus fromIntegerValue(Integer value) {

            if (value == null) return null;

            if (value == TransactionStatusUnknown.getIntegerValue()) {
                return TransactionStatusUnknown;
            }

            if (value == TransactionStatusCreated.getIntegerValue()) {
                return TransactionStatusCreated;
            }

            if (value == TransactionStatusCardholderEnrolled.getIntegerValue()) {
                return TransactionStatusCardholderEnrolled;
            }

            if (value == TransactionStatusCardholderNotEnrolled.getIntegerValue()) {
                return TransactionStatusCardholderNotEnrolled;
            }

            if (value == TransactionStatusUnableToAuthenticate.getIntegerValue()) {
                return TransactionStatusUnableToAuthenticate;
            }

            if (value == TransactionStatusCardholderAuthenticated.getIntegerValue()) {
                return TransactionStatusCardholderAuthenticated;
            }

            if (value ==TransactionStatusAuthenticationAttempted.getIntegerValue()) {
                return TransactionStatusAuthenticationAttempted;
            }

            if (value == TransactionStatusCouldNotAuthenticate.getIntegerValue()) {
                return TransactionStatusCouldNotAuthenticate;
            }

            if (value == TransactionStatusAuthenticationFailed.getIntegerValue()) {
                return TransactionStatusAuthenticationFailed;
            }

            if (value == TransactionStatusBlocked.getIntegerValue()) {
                return TransactionStatusBlocked;
            }

            if (value == TransactionStatusDenied.getIntegerValue()) {
                return TransactionStatusDenied;
            }

            if (value == TransactionStatusAuthorizedAndPending.getIntegerValue()) {
                return TransactionStatusAuthorizedAndPending;
            }

            if (value == TransactionStatusRefused.getIntegerValue()) {
                return TransactionStatusRefused;
            }

            if (value == TransactionStatusExpired.getIntegerValue()) {
                return TransactionStatusExpired;
            }

            if (value == TransactionStatusCancelled.getIntegerValue()) {
                return TransactionStatusCancelled;
            }

            if (value == TransactionStatusAuthorized.getIntegerValue()) {
                return TransactionStatusAuthorized;
            }

            if (value == TransactionStatusCaptureRequested.getIntegerValue()) {
                return TransactionStatusCaptureRequested;
            }

            if (value == TransactionStatusCaptured.getIntegerValue()) {
                return TransactionStatusCaptured;
            }

            if (value == TransactionStatusPartiallyCaptured.getIntegerValue()) {
                return TransactionStatusPartiallyCaptured;
            }

            if (value == TransactionStatusCollected.getIntegerValue()) {
                return TransactionStatusCollected;
            }

            if (value == TransactionStatusPartiallyCollected.getIntegerValue()) {
                return TransactionStatusPartiallyCollected;
            }

            if (value == TransactionStatusSettled.getIntegerValue()) {
                return TransactionStatusSettled;
            }

            if (value == TransactionStatusPartiallySettled.getIntegerValue()) {
                return TransactionStatusPartiallySettled;
            }

            if (value == TransactionStatusRefundRequested.getIntegerValue()) {
                return TransactionStatusRefundRequested;
            }

            if (value == TransactionStatusRefunded.getIntegerValue()) {
                return TransactionStatusRefunded;
            }

            if (value == TransactionStatusPartiallyRefunded.getIntegerValue()) {
                return TransactionStatusPartiallyRefunded;
            }

            if (value == TransactionStatusChargedBack.getIntegerValue()) {
                return TransactionStatusChargedBack;
            }

            if (value == TransactionStatusDebited.getIntegerValue()) {
                return TransactionStatusDebited;
            }

            if (value == TransactionStatusPartiallyDebited.getIntegerValue()) {
                return TransactionStatusPartiallyDebited;
            }

            if (value == TransactionStatusAuthenticationRequested.getIntegerValue()) {
                return TransactionStatusAuthenticationRequested;
            }

            if (value == TransactionStatusAuthenticated.getIntegerValue()) {
                return TransactionStatusAuthenticated;
            }

            if (value == TransactionStatusAuthorizationRequested.getIntegerValue()) {
                return TransactionStatusAuthorizationRequested;
            }

            if (value == TransactionStatusAcquirerFound.getIntegerValue()) {
                return TransactionStatusAcquirerFound;
            }

            if (value == TransactionStatusAcquirernotFound.getIntegerValue()) {
                return TransactionStatusAcquirernotFound;
            }

            if (value == TransactionStatusCardholderEnrollmentUnknown.getIntegerValue()) {
                return TransactionStatusCardholderEnrollmentUnknown;
            }

            if (value == TransactionStatusRiskAccepted.getIntegerValue()) {
                return TransactionStatusRiskAccepted;
            }

            if (value == TransactionStatusAuthorizationRefused.getIntegerValue()) {
                return TransactionStatusAuthorizationRefused;
            }

            if (value == TransactionStatusCaptureRefused.getIntegerValue()) {
                return TransactionStatusCaptureRefused;
            }

            if (value == TransactionStatusPendingPayment.getIntegerValue()) {
                return TransactionStatusPendingPayment;
            }

            return null;
        }
    }


    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Boolean getTest() {
        return test;
    }

    public void setTest(Boolean test) {
        this.test = test;
    }

    public String getMid() {
        return mid;
    }

    public void setMid(String mid) {
        this.mid = mid;
    }

    public String getAuthorizationCode() {
        return authorizationCode;
    }

    public void setAuthorizationCode(String authorizationCode) {
        this.authorizationCode = authorizationCode;
    }

    public String getTransactionReference() {
        return transactionReference;
    }

    public void setTransactionReference(String transactionReference) {
        this.transactionReference = transactionReference;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Date getDateUpdated() {
        return dateUpdated;
    }

    public void setDateUpdated(Date dateUpdated) {
        this.dateUpdated = dateUpdated;
    }

    public Date getDateAuthorized() {
        return dateAuthorized;
    }

    public void setDateAuthorized(Date dateAuthorized) {
        this.dateAuthorized = dateAuthorized;
    }

    public TransactionStatus getStatus() {
        return status;
    }

    public void setStatus(TransactionStatus status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Float getAuthorizedAmount() {
        return authorizedAmount;
    }

    public void setAuthorizedAmount(Float authorizedAmount) {
        this.authorizedAmount = authorizedAmount;
    }

    public Float getCapturedAmount() {
        return capturedAmount;
    }

    public void setCapturedAmount(Float capturedAmount) {
        this.capturedAmount = capturedAmount;
    }

    public Float getRefundedAmount() {
        return refundedAmount;
    }

    public void setRefundedAmount(Float refundedAmount) {
        this.refundedAmount = refundedAmount;
    }

    public Float getDecimals() {
        return decimals;
    }

    public void setDecimals(Float decimals) {
        this.decimals = decimals;
    }

    public static class TransactionRelatedItemSerialization extends AbstractSerialization {

        public TransactionRelatedItemSerialization(TransactionRelatedItem transactionRelatedItem) {
            super(transactionRelatedItem);
        }

        @Override
        public Map<String, String> getSerializedRequest() {
            // we don't send it as a queryString
            return null;
        }

        @Override
        public Bundle getSerializedBundle() {
            super.getSerializedBundle();

            TransactionRelatedItem transactionRelatedItem = (TransactionRelatedItem)this.getModel();
            //TODO put the transactionRelatedItem elements

            TransactionRelatedItem object = new TransactionRelatedItem();

            this.putBoolForKey("test", object.getTest());
            this.putStringForKey("mid", object.getMid());
            this.putStringForKey("authorizationCode", object.getAuthorizationCode());
            this.putStringForKey("transactionReference", object.getTransactionReference());
            this.putDateForKey("dateCreated", object.getDateCreated());
            this.putDateForKey("dateUpdated", object.getDateUpdated());
            this.putDateForKey("dateAuthorized", object.getDateUpdated());

            TransactionStatus status = object.getStatus();
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

        @Override
        public String getQueryString() {
            return null;
        }
    }

    public static class TransactionRelatedItemMapper extends AbstractMapper {
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
            TransactionStatus status = TransactionStatus.fromIntegerValue(statusInteger);
            if (status == null) {
                status = TransactionStatus.TransactionStatusUnknown;
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
            TransactionStatus status = TransactionStatus.fromIntegerValue(statusInteger);
            if (status == null) {
                status = TransactionStatus.TransactionStatusUnknown;
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
}
