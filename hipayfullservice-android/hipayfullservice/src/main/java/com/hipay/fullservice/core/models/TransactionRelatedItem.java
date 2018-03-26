package com.hipay.fullservice.core.models;

import com.hipay.fullservice.core.utils.enums.TransactionStatus;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by nfillion on 25/01/16.
 */
public class TransactionRelatedItem extends AbstractModel implements Serializable {

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
