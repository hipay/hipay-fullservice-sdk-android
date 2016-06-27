package com.hipay.hipayfullservice.core.models;

import android.os.Bundle;

import com.hipay.hipayfullservice.core.mapper.interfaces.MapMapper;
import com.hipay.hipayfullservice.core.serialization.AbstractSerializationMapper;

import org.json.JSONObject;

import java.net.URL;
import java.util.Map;

/**
 * Created by nfillion on 25/01/16.
 */
public class Transaction extends TransactionRelatedItem {

    public static final String TAG = "Transaction";

    protected TransactionState state;
    protected String reason;
    protected URL forwardUrl;
    protected String attemptId;
    protected String referenceToPay;
    protected String ipAddress;
    protected String ipCountry;
    protected String deviceId;

    protected AVSResult avsResult;
    protected CVCResult cvcResult;
    protected ECI eci;

    protected String paymentProduct;
    //TODO paymentMethod
    protected ThreeDSecure threeDSecure;
    protected FraudScreening fraudScreening;
    protected Order order;
    protected Map debitAgreement;

    protected String cdata1;
    protected String cdata2;
    protected String cdata3;
    protected String cdata4;
    protected String cdata5;
    protected String cdata6;
    protected String cdata7;
    protected String cdata8;
    protected String cdata9;
    protected String cdata10;

    protected boolean isHandled;

    public Transaction() {

        this.eci = ECI.Undefined;
        this.avsResult = AVSResult.AVSResultNotApplicable;
        this.cvcResult = CVCResult.CVCResultNotApplicable;
    }

    public static Transaction fromJSONObject(JSONObject object) {

        TransactionMapper mapper = new TransactionMapper(object);
        return mapper.mappedObject();
    }

    public static Transaction fromBundle(Bundle bundle) {

        TransactionMapper mapper = new TransactionMapper(bundle);
        return mapper.mappedObjectFromBundle();
    }

    public Bundle toBundle() {

        Transaction.TransactionSerializationMapper mapper = new Transaction.TransactionSerializationMapper(this);
        return mapper.getSerializedBundle();
    }

    public Boolean isHandled() {

        if (this.state.equals(TransactionState.TransactionStatePending) ||
                this.state.equals(TransactionState.TransactionStateCompleted )) {
            return Boolean.valueOf(true);
        }
        return Boolean.valueOf(false);
    }

    public enum AVSResult {

        AVSResultNotApplicable (' '),
        AVSResultExactMatch ('Y'),
        AVSResultAddressMatch ('A'),
        AVSResultPostalCodeMatch ('P'),
        AVSResultNoMatch ('N'),
        AVSResultNotCompatible ('C'),
        AVSResultNotAllowed ('E'),
        AVSResultUnavailable ('U'),
        AVSResultRetry ('R'),
        AVSResultNotSupported ('S');

        protected final char result;
        AVSResult(char result) {

            this.result = result;
        }

        public char getCharValue() {

            return this.result;
        }

        public static AVSResult fromStringValue(String value) {

            if (value == null) return null;

            char c = value.charAt(0);

            if (c == AVSResultExactMatch.getCharValue()) {
                return AVSResultExactMatch;
            }

            if (c == AVSResultAddressMatch.getCharValue()) {
                return AVSResultAddressMatch;
            }

            if (c == AVSResultPostalCodeMatch.getCharValue()) {
                return AVSResultPostalCodeMatch;
            }

            if (c == AVSResultNoMatch.getCharValue()) {
                return AVSResultNoMatch;
            }

            if (c == AVSResultNotCompatible.getCharValue()) {
                return AVSResultNotCompatible;
            }

            if (c == AVSResultNotAllowed.getCharValue()) {
                return AVSResultNotAllowed;
            }

            if (c == AVSResultUnavailable.getCharValue()) {
                return AVSResultUnavailable;
            }

            if (c == AVSResultRetry.getCharValue()) {
                return AVSResultRetry;
            }

            if (c == AVSResultNotSupported.getCharValue()) {
                return AVSResultNotSupported;
            }

            return null;
        }

    }

    public enum ECI {

        Undefined(Integer.MAX_VALUE),
        MOTO(1),
        RecurringMOTO(2),
        InstallmentPayment(3),
        ManuallyKeyedCardPresent(4),
        SecureECommerce(7),
        RecurringECommerce(9);

        protected final int eci;
        ECI(int eci) {
            this.eci = eci;
        }

        public Integer getIntegerValue() {
            return this.eci;
        }

        public static ECI fromIntegerValue(Integer value) {

            if (value == null) return null;

            if (value.equals(MOTO.getIntegerValue())) {
                return MOTO;
            }

            if (value.equals(RecurringMOTO.getIntegerValue())) {
                return RecurringMOTO;
            }

            if (value.equals(InstallmentPayment.getIntegerValue())) {
                return InstallmentPayment;
            }

            if (value.equals(ManuallyKeyedCardPresent.getIntegerValue())) {
                return ManuallyKeyedCardPresent;
            }

            if (value.equals(SecureECommerce.getIntegerValue())) {
                return SecureECommerce;
            }

            if (value.equals(RecurringECommerce.getIntegerValue())) {
                return RecurringECommerce;
            }

            return null;
        }
    }

    public enum CVCResult {

        CVCResultNotApplicable(' '),
        CVCResultMatch('M'),
        CVCResultNoMatch('N'),
        CVCResultNotProcessed('P'),
        CVCResultMissing('S'),
        CVCResultNotSupported('U');

        protected final char result;

        CVCResult(char result) {
            this.result = result;
        }

        public char getCharValue() {
            return this.result;
        }

        public static CVCResult fromStringValue(String value) {

            if (value == null) return null;

            char c = value.charAt(0);

            if (c == CVCResultNotApplicable.getCharValue()) {
                return CVCResultNotApplicable;
            }

            if (c == CVCResultMatch.getCharValue()) {
                return CVCResultMatch;
            }

            if (c == CVCResultNoMatch.getCharValue()) {
                return CVCResultNoMatch;
            }

            if (c == CVCResultNotProcessed.getCharValue()) {
                return CVCResultNotProcessed;
            }

            if (c == CVCResultMissing.getCharValue()) {
                return CVCResultMissing;
            }

            if (c == CVCResultNotSupported.getCharValue()) {
                return CVCResultNotSupported;
            }

            return null;
        }
    }

    public enum TransactionState {

        TransactionStateError ("error"),
        TransactionStateCompleted ("completed"),
        TransactionStateForwarding ("forwarding"),
        TransactionStatePending ("pending"),
        TransactionStateDeclined ("declined");

        protected final String state;
        TransactionState(String state) {
            this.state = state;
        }

        public String getStringValue() {
            return this.state;
        }

        public static TransactionState fromStringValue(String value) {

            if (value == null) return null;

            if (value.equalsIgnoreCase(TransactionStateError.getStringValue())) {
                return TransactionStateError;
            }

            if (value.equalsIgnoreCase(TransactionStateCompleted.getStringValue())) {
                return TransactionStateCompleted;
            }

            if (value.equalsIgnoreCase(TransactionStateForwarding.getStringValue())) {
                return TransactionStateForwarding;
            }

            if (value.equalsIgnoreCase(TransactionStatePending.getStringValue())) {
                return TransactionStatePending;
            }

            if (value.equalsIgnoreCase(TransactionStateDeclined.getStringValue())) {
                return TransactionStateDeclined;
            }

            return null;
        }
    }

    public TransactionState getState() {
        return state;
    }

    public void setState(TransactionState state) {
        this.state = state;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public URL getForwardUrl() {
        return forwardUrl;
    }

    public void setForwardUrl(URL forwardUrl) {
        this.forwardUrl = forwardUrl;
    }

    public String getAttemptId() {
        return attemptId;
    }

    public void setAttemptId(String attemptId) {
        this.attemptId = attemptId;
    }

    public String getReferenceToPay() {
        return referenceToPay;
    }

    public void setReferenceToPay(String referenceToPay) {
        this.referenceToPay = referenceToPay;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getIpCountry() {
        return ipCountry;
    }

    public void setIpCountry(String ipCountry) {
        this.ipCountry = ipCountry;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public AVSResult getAvsResult() {
        return avsResult;
    }

    public void setAvsResult(AVSResult avsResult) {
        this.avsResult = avsResult;
    }

    public CVCResult getCvcResult() {
        return cvcResult;
    }

    public void setCvcResult(CVCResult cvcResult) {
        this.cvcResult = cvcResult;
    }

    public ECI getEci() {
        return eci;
    }

    public void setEci(ECI eci) {
        this.eci = eci;
    }

    public String getPaymentProduct() {
        return paymentProduct;
    }

    public void setPaymentProduct(String paymentProduct) {
        this.paymentProduct = paymentProduct;
    }

    public ThreeDSecure getThreeDSecure() {
        return threeDSecure;
    }

    public void setThreeDSecure(ThreeDSecure threeDSecure) {
        this.threeDSecure = threeDSecure;
    }

    public FraudScreening getFraudScreening() {
        return fraudScreening;
    }

    public void setFraudScreening(FraudScreening fraudScreening) {
        this.fraudScreening = fraudScreening;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Map getDebitAgreement() {
        return debitAgreement;
    }

    public void setDebitAgreement(Map debitAgreement) {
        this.debitAgreement = debitAgreement;
    }

    public String getCdata1() {
        return cdata1;
    }

    public void setCdata1(String cdata1) {
        this.cdata1 = cdata1;
    }

    public String getCdata2() {
        return cdata2;
    }

    public void setCdata2(String cdata2) {
        this.cdata2 = cdata2;
    }

    public String getCdata3() {
        return cdata3;
    }

    public void setCdata3(String cdata3) {
        this.cdata3 = cdata3;
    }

    public String getCdata4() {
        return cdata4;
    }

    public void setCdata4(String cdata4) {
        this.cdata4 = cdata4;
    }

    public String getCdata5() {
        return cdata5;
    }

    public void setCdata5(String cdata5) {
        this.cdata5 = cdata5;
    }

    public String getCdata6() {
        return cdata6;
    }

    public void setCdata6(String cdata6) {
        this.cdata6 = cdata6;
    }

    public String getCdata7() {
        return cdata7;
    }

    public void setCdata7(String cdata7) {
        this.cdata7 = cdata7;
    }

    public String getCdata8() {
        return cdata8;
    }

    public void setCdata8(String cdata8) {
        this.cdata8 = cdata8;
    }

    public String getCdata9() {
        return cdata9;
    }

    public void setCdata9(String cdata9) {
        this.cdata9 = cdata9;
    }

    public String getCdata10() {
        return cdata10;
    }

    public void setCdata10(String cdata10) {

        this.cdata10 = cdata10;
    }

    protected static class TransactionSerializationMapper extends AbstractSerializationMapper {

        protected TransactionSerializationMapper(Transaction transaction) {
            super(transaction);
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

    public static class TransactionSerialization extends TransactionRelatedItemSerialization {

        public TransactionSerialization(Transaction transaction) {
            super(transaction);
        }

        @Override
        public Map<String, String> getSerializedRequest() {
            return null;
        }

        @Override
        public Bundle getSerializedBundle() {
            super.getSerializedBundle();

            Transaction transaction = (Transaction)this.getModel();

            this.putStringForKey("cdata1", transaction.getCdata1());
            this.putStringForKey("cdata2", transaction.getCdata2());
            this.putStringForKey("cdata3", transaction.getCdata3());
            this.putStringForKey("cdata4", transaction.getCdata4());
            this.putStringForKey("cdata5", transaction.getCdata5());
            this.putStringForKey("cdata6", transaction.getCdata6());
            this.putStringForKey("cdata7", transaction.getCdata7());
            this.putStringForKey("cdata8", transaction.getCdata8());
            this.putStringForKey("cdata9", transaction.getCdata9());
            this.putStringForKey("cdata10", transaction.getCdata10());

            this.putStringForKey("reason", transaction.getReason());
            this.putStringForKey("attemptId", transaction.getAttemptId());
            this.putStringForKey("referenceToPay", transaction.getReferenceToPay());
            this.putStringForKey("ipAddress", transaction.getIpAddress());
            this.putStringForKey("ipCountry", transaction.getIpCountry());
            this.putStringForKey("deviceId", transaction.getDeviceId());
            this.putStringForKey("paymentProduct", transaction.getPaymentProduct());

            this.putUrlForKey("forwardUrl", transaction.getForwardUrl());

            AVSResult avsResult = transaction.getAvsResult();
            if (avsResult != null) {
                this.putStringForKey("avsResult", Character.toString(avsResult.getCharValue()));
            }

            CVCResult cvcResult = transaction.getCvcResult();
            if (cvcResult != null) {
                this.putStringForKey("cvcResult", Character.toString(cvcResult.getCharValue()));
            }

            ECI eci = transaction.getEci();
            if (eci != null) {
                this.putIntForKey("eci", eci.getIntegerValue());
            }

            TransactionState state = transaction.getState();
            if (state != null) {
                this.putStringForKey("state", state.getStringValue());
            }

            ThreeDSecure threeDSecure = transaction.getThreeDSecure();
            if (threeDSecure != null) {
                Bundle bundle = threeDSecure.toBundle();
                this.putBundleForKey("threeDSecure", bundle);
            }

            FraudScreening fraudScreening = transaction.getFraudScreening();
            if (fraudScreening != null) {
                Bundle bundle = fraudScreening.toBundle();
                this.putBundleForKey("fraudScreening", bundle);
            }

            Order order = transaction.getOrder();
            if (order != null) {
                Bundle bundle = order.toBundle();
                this.putBundleForKey("order", bundle);
            }

            return this.getBundle();
        }

        @Override
        public String getQueryString() {
            return null;
        }
    }

    private static class TransactionMapper extends TransactionRelatedItemMapper {
        public TransactionMapper(Object object) {
            super(object);
        }

        @Override
        protected boolean isValid() {

            if (this.getBehaviour() instanceof MapMapper) {

                if (super.isValid()) {

                    if (this.getStringForKey("state") != null)
                        return true;
                }
            }

            return false;
        }

        @Override
        protected Transaction mappedObjectFromBundle() {

            TransactionRelatedItem transactionRelatedItem = super.mappedObjectFromBundle();
            Transaction object = this.transactionFromRelatedItem(transactionRelatedItem);

            object.setCdata1(this.getStringForKey("cdata1"));
            object.setCdata2(this.getStringForKey("cdata2"));
            object.setCdata3(this.getStringForKey("cdata3"));
            object.setCdata4(this.getStringForKey("cdata4"));
            object.setCdata5(this.getStringForKey("cdata5"));
            object.setCdata6(this.getStringForKey("cdata6"));
            object.setCdata7(this.getStringForKey("cdata7"));
            object.setCdata8(this.getStringForKey("cdata8"));
            object.setCdata9(this.getStringForKey("cdata9"));
            object.setCdata10(this.getStringForKey("cdata10"));

            object.setReason(this.getStringForKey("reason"));
            object.setAttemptId(this.getStringForKey("attemptId"));
            object.setReferenceToPay(this.getStringForKey("referenceToPay"));
            object.setIpAddress(this.getStringForKey("ipAddress"));
            object.setIpCountry(this.getStringForKey("ipCountry"));
            object.setDeviceId(this.getStringForKey("deviceId"));
            object.setPaymentProduct(this.getStringForKey("paymentProduct"));

            object.setForwardUrl(this.getURLForKey("forwardUrl"));

            String resultString = this.getEnumCharForKey("avsResult");
            AVSResult result = AVSResult.fromStringValue(resultString);
            if (result == null) {
                result = AVSResult.AVSResultNotApplicable;
            }
            object.setAvsResult(result);

            String cvcResultString = this.getEnumCharForKey("cvcResult");
            CVCResult cvcResult = CVCResult.fromStringValue(cvcResultString);
            if (cvcResult == null) {
                cvcResult = CVCResult.CVCResultNotApplicable;
            }
            object.setCvcResult(cvcResult);

            Integer eciString = this.getIntegerForKey("eci");
            ECI eci = ECI.fromIntegerValue(eciString);
            if (eci == null) {
                eci = ECI.Undefined;
            }
            object.setEci(eci);

            String stateString = this.getStringForKey("state");
            TransactionState state = TransactionState.fromStringValue(stateString);
            if (state == null) {
                state = TransactionState.TransactionStateError;
            }
            object.setState(state);


            Bundle threeDSecureBundle = this.getBundleForKey("threeDSecure");
            ThreeDSecure threeDSecure = null;
            if (threeDSecureBundle != null) {
                threeDSecure = ThreeDSecure.fromBundle(threeDSecureBundle);
            }
            object.setThreeDSecure(threeDSecure);

            Bundle fraudScreeningBundle = this.getBundleForKey("fraudScreening");
            FraudScreening fraudScreening = null;
            if (fraudScreeningBundle != null) {
                fraudScreening = FraudScreening.fromBundle(fraudScreeningBundle);
            }
            object.setFraudScreening(fraudScreening);

            Bundle orderBundle = this.getBundleForKey("order");
            Order order = null;
            if (orderBundle != null) {
                order = Order.fromBundle(orderBundle);
            }
            object.setOrder(order);

            return object;

        }

        @Override
        protected Transaction mappedObject() {

            //TODO get mapped object from transactionRelatedItem superclass

            TransactionRelatedItem transactionRelatedItem = super.mappedObject();
            Transaction object = this.transactionFromRelatedItem(transactionRelatedItem);

            object.setCdata1(this.getStringForKey("cdata1"));
            object.setCdata2(this.getStringForKey("cdata2"));
            object.setCdata3(this.getStringForKey("cdata3"));
            object.setCdata4(this.getStringForKey("cdata4"));
            object.setCdata5(this.getStringForKey("cdata5"));
            object.setCdata6(this.getStringForKey("cdata6"));
            object.setCdata7(this.getStringForKey("cdata7"));
            object.setCdata8(this.getStringForKey("cdata8"));
            object.setCdata9(this.getStringForKey("cdata9"));
            object.setCdata10(this.getStringForKey("cdata10"));

            object.setReason(this.getStringForKey("reason"));
            object.setAttemptId(this.getStringForKey("attemptId"));
            object.setReferenceToPay(this.getStringForKey("referenceToPay"));
            object.setIpAddress(this.getStringForKey("ipAddress"));
            object.setIpCountry(this.getStringForKey("ipCountry"));
            object.setDeviceId(this.getStringForKey("deviceId"));
            object.setPaymentProduct(this.getStringForKey("paymentProduct"));

            object.setForwardUrl(this.getURLForKey("forwardUrl"));

            String resultString = this.getEnumCharForKey("avsResult");
            AVSResult result = AVSResult.fromStringValue(resultString);
            if (result == null) {
                result = AVSResult.AVSResultNotApplicable;
            }
            object.setAvsResult(result);

            String cvcResultString = this.getEnumCharForKey("cvcResult");
            CVCResult cvcResult = CVCResult.fromStringValue(cvcResultString);
            if (cvcResult == null) {
                cvcResult = CVCResult.CVCResultNotApplicable;
            }
            object.setCvcResult(cvcResult);

            Integer eciString = this.getIntegerForKey("eci");
            ECI eci = ECI.fromIntegerValue(eciString);
            if (eci == null) {
                eci = ECI.Undefined;
            }
            object.setEci(eci);

            String stateString = this.getStringForKey("state");
            TransactionState state = TransactionState.fromStringValue(stateString);
            if (state == null) {
                state = TransactionState.TransactionStateError;
            }
            object.setState(state);

            JSONObject fraudScreeningObject = this.getJSONObjectForKey("fraudScreening");
            FraudScreening fraudScreening = null;
            if (fraudScreeningObject != null) {
                fraudScreening = FraudScreening.fromJSONObject(fraudScreeningObject);
            }
            object.setFraudScreening(fraudScreening);

            JSONObject orderObject = this.getJSONObjectForKey("order");
            Order order = null;
            if (orderObject != null) {
                order = Order.fromJSONObject(orderObject);
            }
            object.setOrder(order);

            //TODO look for debitAgreement and paymentMethod

            return object;

        }

        private Transaction transactionFromRelatedItem(TransactionRelatedItem transactionRelatedItem) {

            Transaction transaction = new Transaction();

            transaction.setTest(transactionRelatedItem.getTest());
            transaction.setMid(transactionRelatedItem.getMid());
            transaction.setAuthorizationCode(transactionRelatedItem.getAuthorizationCode());
            transaction.setTransactionReference(transactionRelatedItem.getTransactionReference());

            transaction.setDateCreated(transactionRelatedItem.getDateCreated());
            transaction.setDateUpdated(transactionRelatedItem.getDateUpdated());
            transaction.setDateAuthorized(transactionRelatedItem.getDateAuthorized());

            transaction.setStatus(transactionRelatedItem.getStatus());
            transaction.setMessage(transactionRelatedItem.getMessage());

            transaction.setAuthorizedAmount(transactionRelatedItem.getAuthorizedAmount());
            transaction.setCapturedAmount(transactionRelatedItem.getCapturedAmount());
            transaction.setRefundedAmount(transactionRelatedItem.getRefundedAmount());

            transaction.setDecimals(transactionRelatedItem.getDecimals());
            transaction.setCurrency(transactionRelatedItem.getCurrency());

            return transaction;
        }

    }
}
