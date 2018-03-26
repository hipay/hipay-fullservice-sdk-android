package com.hipay.fullservice.core.models;

import android.net.Uri;
import android.os.Bundle;

import com.hipay.fullservice.core.mapper.TransactionMapper;
import com.hipay.fullservice.core.utils.enums.AVSResult;
import com.hipay.fullservice.core.utils.enums.CVCResult;
import com.hipay.fullservice.core.utils.enums.ECI;
import com.hipay.fullservice.core.utils.enums.TransactionState;

import org.json.JSONObject;

import java.io.Serializable;
import java.net.URL;

/**
 * Created by nfillion on 25/01/16.
 */
public class Transaction extends TransactionRelatedItem  implements Serializable {

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

    protected PaymentMethod paymentMethod;
    protected ThreeDSecure threeDSecure;
    protected FraudScreening fraudScreening;
    protected Order order;

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

        this.eci = ECI.UNDEFINED;
        this.avsResult = AVSResult.AVS_RESULT_NOT_APPLICABLE;
        this.cvcResult = CVCResult.CVC_RESULT_NOT_APPLICABLE;
    }

    public static Transaction fromJSONObject(JSONObject object) {

        TransactionMapper mapper = new TransactionMapper(object);
        return mapper.mappedObject();
    }

    public static Transaction fromBundle(Bundle bundle) {

        TransactionMapper mapper = new TransactionMapper(bundle);
        return mapper.mappedObject();
    }

    public static Transaction fromUri(Uri uri) {

        TransactionMapper mapper = new TransactionMapper(uri);
        return mapper.mappedObject();

    }

    public Boolean isHandled() {

        if (this.state.equals(TransactionState.TRANSACTION_STATE_PENDING) ||
                this.state.equals(TransactionState.TRANSACTION_STATE_COMPLETED)) {
            return Boolean.valueOf(true);
        }
        return Boolean.valueOf(false);
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

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }
}
