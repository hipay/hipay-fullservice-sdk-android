package com.hipay.hipayfullservice.core.models;

/**
 * Created by nfillion on 25/01/16.
 */
public class Order {

    protected String orderId;
    protected String dateCreated;
    protected Integer attemps;
    protected Number amount;
    protected Number shipping;
    protected Number tax;
    protected Number decimals;
    protected String currency;
    protected String customerId;
    protected String language;

    protected Gender gender;
    protected PersonalInformation shippingAddress;

    public Order() {
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Integer getAttemps() {
        return attemps;
    }

    public void setAttemps(Integer attemps) {
        this.attemps = attemps;
    }

    public Number getAmount() {
        return amount;
    }

    public void setAmount(Number amount) {
        this.amount = amount;
    }

    public Number getShipping() {
        return shipping;
    }

    public void setShipping(Number shipping) {
        this.shipping = shipping;
    }

    public Number getTax() {
        return tax;
    }

    public void setTax(Number tax) {
        this.tax = tax;
    }

    public Number getDecimals() {
        return decimals;
    }

    public void setDecimals(Number decimals) {
        this.decimals = decimals;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public PersonalInformation getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(PersonalInformation shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public enum Gender {

        ThreeDSecureEnrollmentStatusUnknown (' '),
        ThreeDSecureEnrollmentStatusAuthenticationAvailable ('Y'),
        ThreeDSecureEnrollmentStatusCardholderNotEnrolled ('N'),
        ThreeDSecureEnrollmentStatusUnableToAuthenticate ('U'),
        ThreeDSecureEnrollmentStatusOtherError ('E');

        protected final char gender;
        Gender(char gender) {
            this.gender = gender;
        }
    }

}
