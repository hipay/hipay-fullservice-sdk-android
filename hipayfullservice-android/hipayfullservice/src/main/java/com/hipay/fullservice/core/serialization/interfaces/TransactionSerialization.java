package com.hipay.fullservice.core.serialization.interfaces;

import android.os.Bundle;

import com.hipay.fullservice.core.models.FraudScreening;
import com.hipay.fullservice.core.models.Order;
import com.hipay.fullservice.core.models.PaymentCardToken;
import com.hipay.fullservice.core.models.PaymentMethod;
import com.hipay.fullservice.core.models.ThreeDSecure;
import com.hipay.fullservice.core.models.Transaction;
import com.hipay.fullservice.core.utils.enums.AVSResult;
import com.hipay.fullservice.core.utils.enums.CVCResult;
import com.hipay.fullservice.core.utils.enums.ECI;
import com.hipay.fullservice.core.utils.enums.TransactionState;

/**
 * Created by nfillion on 08/09/16.
 */

public class TransactionSerialization extends TransactionRelatedItemSerialization {

    public TransactionSerialization(Transaction transaction) {
        super(transaction);
    }

    @Override
    public Bundle getSerializedBundle() {
        super.getSerializedBundle();

        Transaction transaction = (Transaction)this.getModel();

        this.bundle.putString("cdata1", transaction.getCdata1());
        this.bundle.putString("cdata2", transaction.getCdata2());
        this.bundle.putString("cdata3", transaction.getCdata3());
        this.bundle.putString("cdata4", transaction.getCdata4());
        this.bundle.putString("cdata5", transaction.getCdata5());
        this.bundle.putString("cdata6", transaction.getCdata6());
        this.bundle.putString("cdata7", transaction.getCdata7());
        this.bundle.putString("cdata8", transaction.getCdata8());
        this.bundle.putString("cdata9", transaction.getCdata9());
        this.bundle.putString("cdata10", transaction.getCdata10());

        this.bundle.putString("reason", transaction.getReason());
        this.bundle.putString("attemptId", transaction.getAttemptId());
        this.bundle.putString("referenceToPay", transaction.getReferenceToPay());
        this.bundle.putString("ipAddress", transaction.getIpAddress());
        this.bundle.putString("ipCountry", transaction.getIpCountry());
        this.bundle.putString("deviceId", transaction.getDeviceId());
        this.bundle.putString("paymentProduct", transaction.getPaymentProduct());

        this.bundle.putUrl("forwardUrl", transaction.getForwardUrl());

        AVSResult avsResult = transaction.getAvsResult();
        if (avsResult != null) {
            this.bundle.putString("avsResult", Character.toString(avsResult.getValue()));
        }

        CVCResult cvcResult = transaction.getCvcResult();
        if (cvcResult != null) {
            this.bundle.putString("cvcResult", Character.toString(cvcResult.getValue()));
        }

        ECI eci = transaction.getEci();
        if (eci != null) {
            this.bundle.putInt("eci", eci.getValue());
        }

        TransactionState state = transaction.getState();
        if (state != null) {
            this.bundle.putString("state", state.getValue());
        }

        ThreeDSecure threeDSecure = transaction.getThreeDSecure();
        if (threeDSecure != null) {
            Bundle bundle = threeDSecure.toBundle();
            this.bundle.putBundle("threeDSecure", bundle);
        }

        FraudScreening fraudScreening = transaction.getFraudScreening();
        if (fraudScreening != null) {
            Bundle bundle = fraudScreening.toBundle();
            this.bundle.putBundle("fraudScreening", bundle);
        }

        Order order = transaction.getOrder();
        if (order != null) {
            Bundle bundle = order.toBundle();
            this.bundle.putBundle("order", bundle);
        }

        PaymentMethod paymentMethod = transaction.getPaymentMethod();
        if (paymentMethod != null) {
            if (paymentMethod instanceof PaymentCardToken) {

                PaymentCardToken paymentCardToken = (PaymentCardToken) paymentMethod;
                Bundle bundle = paymentCardToken.toBundle();
                this.bundle.putBundle("paymentMethod", bundle);
            }
        }

        return this.getBundle();
    }
}
