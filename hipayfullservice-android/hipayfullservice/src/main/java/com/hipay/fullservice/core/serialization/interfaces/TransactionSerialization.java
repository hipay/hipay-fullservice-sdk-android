package com.hipay.fullservice.core.serialization.interfaces;

import android.os.Bundle;

import com.hipay.fullservice.core.models.FraudScreening;
import com.hipay.fullservice.core.models.Order;
import com.hipay.fullservice.core.models.PaymentCardToken;
import com.hipay.fullservice.core.models.PaymentMethod;
import com.hipay.fullservice.core.models.ThreeDSecure;
import com.hipay.fullservice.core.models.Transaction;
import com.hipay.fullservice.core.utils.enums.CVCResult;
import com.hipay.fullservice.core.utils.enums.ECI;
import com.hipay.fullservice.core.utils.enums.AVSResult;
import com.hipay.fullservice.core.utils.enums.TransactionState;

import java.util.Map;

/**
 * Created by nfillion on 08/09/16.
 */

public class TransactionSerialization extends TransactionRelatedItemSerialization {

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
            this.putStringForKey("avsResult", Character.toString(avsResult.getValue()));
        }

        CVCResult cvcResult = transaction.getCvcResult();
        if (cvcResult != null) {
            this.putStringForKey("cvcResult", Character.toString(cvcResult.getValue()));
        }

        ECI eci = transaction.getEci();
        if (eci != null) {
            this.putIntForKey("eci", eci.getValue());
        }

        TransactionState state = transaction.getState();
        if (state != null) {
            this.putStringForKey("state", state.getValue());
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

        PaymentMethod paymentMethod = transaction.getPaymentMethod();
        if (paymentMethod != null) {
            if (paymentMethod instanceof PaymentCardToken) {

                PaymentCardToken paymentCardToken = (PaymentCardToken) paymentMethod;
                Bundle bundle = paymentCardToken.toBundle();
                this.putBundleForKey("paymentMethod", bundle);
            }
        }

        return this.getBundle();
    }

    @Override
    public String getQueryString() {
        return null;
    }
}
