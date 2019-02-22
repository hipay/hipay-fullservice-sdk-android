package com.hipay.fullservice.core.requests.payment;

import com.hipay.fullservice.core.models.Order;
import com.hipay.fullservice.core.requests.AbstractRequest;
import com.hipay.fullservice.core.serialization.AbstractSerializationMapper;

import java.util.Map;

public class SepaDirectDebitPaymentMethodRequest extends AbstractPaymentMethodRequest {


    protected String firstname;
    protected String lastname;
    protected String iban;
    protected Integer recurringPayment;
    protected Order.Gender gender;
    protected String bankName;
    protected String issuerBankId;
    protected Integer debitAgreementId;

    public SepaDirectDebitPaymentMethodRequest() {
    }

    public SepaDirectDebitPaymentMethodRequest(String firstname, String lastname, String iban, Integer recurringPayment, Order.Gender gender, String bankName, String issuerBankId, Integer debitAgreementId) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.iban = iban;
        this.recurringPayment = recurringPayment;
        this.gender = gender;
        this.bankName = bankName;
        this.issuerBankId = issuerBankId;
        this.debitAgreementId = debitAgreementId;
    }

    public SepaDirectDebitPaymentMethodRequest(String firstname, String lastname, String iban, Integer recurringPayment) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.iban = iban;
        this.recurringPayment = recurringPayment;
    }

    public Map<String, String> getSerializedObject() {

        SepaDirectDebitPaymentMethodRequest.SepaDirectDebitPaymentMethodRequestSerializationMapper mapper = new SepaDirectDebitPaymentMethodRequest.SepaDirectDebitPaymentMethodRequestSerializationMapper(this);
        return mapper.getSerializedObject();
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

    public Order.Gender getGender() {
        return gender;
    }

    public void setGender(Order.Gender gender) {
        this.gender = gender;
    }

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
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

    public Integer getRecurringPayment() {
        return recurringPayment;
    }

    public void setRecurringPayment(Integer recurringPayment) {
        this.recurringPayment = recurringPayment;
    }

    public Integer getDebitAgreementId() {
        return debitAgreementId;
    }

    public void setDebitAgreementId(Integer debitAgreementId) {
        this.debitAgreementId = debitAgreementId;
    }

    public static class SepaDirectDebitPaymentMethodRequestSerializationMapper extends AbstractSerializationMapper {

        public SepaDirectDebitPaymentMethodRequestSerializationMapper(AbstractRequest request) {
            super(request);
        }

        @Override
        public Map<String, String> getSerializedObject() {
            return super.getSerializedObject();
        }
    }

}
