package com.hipay.hipayfullservice.core.models;

import java.util.Date;

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

            if (value.equals(TransactionStatusUnknown.getIntegerValue())) {
                return TransactionStatusUnknown;
            }

            if (value.equals(TransactionStatusCreated.getIntegerValue())) {
                return TransactionStatusCreated;
            }

            if (value.equals(TransactionStatusCardholderEnrolled.getIntegerValue())) {
                return TransactionStatusCardholderEnrolled;
            }

            if (value.equals(TransactionStatusCardholderNotEnrolled.getIntegerValue())) {
                return TransactionStatusCardholderNotEnrolled;
            }

            if (value.equals(TransactionStatusUnableToAuthenticate.getIntegerValue())) {
                return TransactionStatusUnableToAuthenticate;
            }

            if (value.equals(TransactionStatusCardholderAuthenticated.getIntegerValue())) {
                return TransactionStatusCardholderAuthenticated;
            }

            if (value.equals(TransactionStatusAuthenticationAttempted.getIntegerValue())) {
                return TransactionStatusAuthenticationAttempted;
            }

            if (value.equals(TransactionStatusCouldNotAuthenticate.getIntegerValue())) {
                return TransactionStatusCouldNotAuthenticate;
            }

            if (value.equals(TransactionStatusAuthenticationFailed.getIntegerValue())) {
                return TransactionStatusAuthenticationFailed;
            }

            if (value.equals(TransactionStatusBlocked.getIntegerValue())) {
                return TransactionStatusBlocked;
            }

            if (value.equals(TransactionStatusDenied.getIntegerValue())) {
                return TransactionStatusDenied;
            }

            if (value.equals(TransactionStatusAuthorizedAndPending.getIntegerValue())) {
                return TransactionStatusAuthorizedAndPending;
            }

            if (value.equals(TransactionStatusRefused.getIntegerValue())) {
                return TransactionStatusRefused;
            }

            if (value.equals(TransactionStatusExpired.getIntegerValue())) {
                return TransactionStatusExpired;
            }

            if (value.equals(TransactionStatusCancelled.getIntegerValue())) {
                return TransactionStatusCancelled;
            }

            if (value.equals(TransactionStatusAuthorized.getIntegerValue())) {
                return TransactionStatusAuthorized;
            }

            if (value.equals(TransactionStatusCaptureRequested.getIntegerValue())) {
                return TransactionStatusCaptureRequested;
            }

            if (value.equals(TransactionStatusCaptured.getIntegerValue())) {
                return TransactionStatusCaptured;
            }

            if (value.equals(TransactionStatusPartiallyCaptured.getIntegerValue())) {
                return TransactionStatusPartiallyCaptured;
            }

            if (value.equals(TransactionStatusCollected.getIntegerValue())) {
                return TransactionStatusCollected;
            }

            if (value.equals(TransactionStatusPartiallyCollected.getIntegerValue())) {
                return TransactionStatusPartiallyCollected;
            }

            if (value.equals(TransactionStatusSettled.getIntegerValue())) {
                return TransactionStatusSettled;
            }

            if (value.equals(TransactionStatusPartiallySettled.getIntegerValue())) {
                return TransactionStatusPartiallySettled;
            }

            if (value.equals(TransactionStatusRefundRequested.getIntegerValue())) {
                return TransactionStatusRefundRequested;
            }

            if (value.equals(TransactionStatusRefunded.getIntegerValue())) {
                return TransactionStatusRefunded;
            }

            if (value.equals(TransactionStatusPartiallyRefunded.getIntegerValue())) {
                return TransactionStatusPartiallyRefunded;
            }

            if (value.equals(TransactionStatusChargedBack.getIntegerValue())) {
                return TransactionStatusChargedBack;
            }

            if (value.equals(TransactionStatusDebited.getIntegerValue())) {
                return TransactionStatusDebited;
            }

            if (value.equals(TransactionStatusPartiallyDebited.getIntegerValue())) {
                return TransactionStatusPartiallyDebited;
            }

            if (value.equals(TransactionStatusAuthenticationRequested.getIntegerValue())) {
                return TransactionStatusAuthenticationRequested;
            }

            if (value.equals(TransactionStatusAuthenticated.getIntegerValue())) {
                return TransactionStatusAuthenticated;
            }

            if (value.equals(TransactionStatusAuthorizationRequested.getIntegerValue())) {
                return TransactionStatusAuthorizationRequested;
            }

            if (value.equals(TransactionStatusAcquirerFound.getIntegerValue())) {
                return TransactionStatusAcquirerFound;
            }

            if (value.equals(TransactionStatusAcquirernotFound.getIntegerValue())) {
                return TransactionStatusAcquirernotFound;
            }

            if (value.equals(TransactionStatusCardholderEnrollmentUnknown.getIntegerValue())) {
                return TransactionStatusCardholderEnrollmentUnknown;
            }

            if (value.equals(TransactionStatusRiskAccepted.getIntegerValue())) {
                return TransactionStatusRiskAccepted;
            }

            if (value.equals(TransactionStatusAuthorizationRefused.getIntegerValue())) {
                return TransactionStatusAuthorizationRefused;
            }

            if (value.equals(TransactionStatusCaptureRefused.getIntegerValue())) {
                return TransactionStatusCaptureRefused;
            }

            if (value.equals(TransactionStatusPendingPayment.getIntegerValue())) {
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
}
