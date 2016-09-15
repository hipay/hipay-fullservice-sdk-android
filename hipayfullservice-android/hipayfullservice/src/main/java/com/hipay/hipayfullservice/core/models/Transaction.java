package com.hipay.hipayfullservice.core.models;

import android.os.Bundle;

import com.hipay.hipayfullservice.core.mapper.TransactionMapper;
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


}
