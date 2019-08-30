package com.hipay.fullservice.core.models;

import android.os.Bundle;

import com.hipay.fullservice.core.serialization.AbstractSerializationMapper;

public class SepaDirectDebit extends AbstractModel {

    protected String firstname;
    protected String lastname;
    protected String iban;
    protected Integer recurringPayment;
    protected Order.Gender gender;
    protected String bankName;
    protected String issuerBankId;
    protected Integer debitAgreementId;

    public SepaDirectDebit() {
    }

    protected static class SepaDirectDebitSerializationMapper extends AbstractSerializationMapper {

        protected SepaDirectDebitSerializationMapper(SepaDirectDebit sepaDirectDebit) {
            super(sepaDirectDebit);
        }

        @Override
        protected String getQueryString() {
            return super.getQueryString();
        }

        @Override
        protected Bundle getSerializedBundle() {
            return super.getSerializedBundle();
        }
    }

    public Bundle toBundle() {

        SepaDirectDebitSerializationMapper mapper = new SepaDirectDebitSerializationMapper(this);
        return mapper.getSerializedBundle();
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    public Integer getRecurringPayment() {
        return recurringPayment;
    }

    public void setRecurringPayment(Integer recurringPayment) {
        this.recurringPayment = recurringPayment;
    }

    public Order.Gender getGender() {
        return gender;
    }

    public void setGender(Order.Gender gender) {
        this.gender = gender;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getIssuerBankId() {
        return issuerBankId;
    }

    public void setIssuerBankId(String issuerBankId) {
        this.issuerBankId = issuerBankId;
    }

    public Integer getDebitAgreementId() {
        return debitAgreementId;
    }

    public void setDebitAgreementId(Integer debitAgreementId) {
        this.debitAgreementId = debitAgreementId;
    }



}
