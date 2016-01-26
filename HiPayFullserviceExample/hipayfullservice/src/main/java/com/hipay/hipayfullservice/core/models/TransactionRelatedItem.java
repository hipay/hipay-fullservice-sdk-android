package com.hipay.hipayfullservice.core.models;

import java.util.Date;

/**
 * Created by nfillion on 25/01/16.
 */
public class TransactionRelatedItem {

    protected Boolean test;
    protected String mid;
    protected String authorizationCode;
    protected String transactionReference;
    protected Date dateCreated;
    protected Date dateUpdated;
    protected Date dateAuthorized;
    protected TransactionStatus status;
    protected String message;
    protected Number authorizedAmount;
    protected Number capturedAmount;
    protected Number refundedAmount;
    protected Number decimals;
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

    public Number getAuthorizedAmount() {
        return authorizedAmount;
    }

    public void setAuthorizedAmount(Number authorizedAmount) {
        this.authorizedAmount = authorizedAmount;
    }

    public Number getCapturedAmount() {
        return capturedAmount;
    }

    public void setCapturedAmount(Number capturedAmount) {
        this.capturedAmount = capturedAmount;
    }

    public Number getRefundedAmount() {
        return refundedAmount;
    }

    public void setRefundedAmount(Number refundedAmount) {
        this.refundedAmount = refundedAmount;
    }

    public Number getDecimals() {
        return decimals;
    }

    public void setDecimals(Number decimals) {
        this.decimals = decimals;
    }

}
