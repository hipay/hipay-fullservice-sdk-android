package com.hipay.fullservice.core.mapper;

import android.os.Bundle;

import com.hipay.fullservice.core.mapper.interfaces.MapMapper;
import com.hipay.fullservice.core.models.FraudScreening;
import com.hipay.fullservice.core.models.Order;
import com.hipay.fullservice.core.models.ThreeDSecure;
import com.hipay.fullservice.core.models.Transaction;
import com.hipay.fullservice.core.models.TransactionRelatedItem;

import org.json.JSONObject;

/**
 * Created by nfillion on 08/09/16.
 */

public class TransactionMapper extends TransactionRelatedItemMapper {
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
    public Transaction mappedObjectFromBundle() {

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
        Transaction.AVSResult result = Transaction.AVSResult.fromStringValue(resultString);
        if (result == null) {
            result = Transaction.AVSResult.AVSResultNotApplicable;
        }
        object.setAvsResult(result);

        String cvcResultString = this.getEnumCharForKey("cvcResult");
        Transaction.CVCResult cvcResult = Transaction.CVCResult.fromStringValue(cvcResultString);
        if (cvcResult == null) {
            cvcResult = Transaction.CVCResult.CVCResultNotApplicable;
        }
        object.setCvcResult(cvcResult);

        Integer eciString = this.getIntegerForKey("eci");
        Transaction.ECI eci = Transaction.ECI.fromIntegerValue(eciString);
        if (eci == null) {
            eci = Transaction.ECI.Undefined;
        }
        object.setEci(eci);

        String stateString = this.getStringForKey("state");
        Transaction.TransactionState state = Transaction.TransactionState.fromStringValue(stateString);
        if (state == null) {
            state = Transaction.TransactionState.TransactionStateError;
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
    public Transaction mappedObject() {

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
        Transaction.AVSResult result = Transaction.AVSResult.fromStringValue(resultString);
        if (result == null) {
            result = Transaction.AVSResult.AVSResultNotApplicable;
        }
        object.setAvsResult(result);

        String cvcResultString = this.getEnumCharForKey("cvcResult");
        Transaction.CVCResult cvcResult = Transaction.CVCResult.fromStringValue(cvcResultString);
        if (cvcResult == null) {
            cvcResult = Transaction.CVCResult.CVCResultNotApplicable;
        }
        object.setCvcResult(cvcResult);

        Integer eciString = this.getIntegerForKey("eci");
        Transaction.ECI eci = Transaction.ECI.fromIntegerValue(eciString);
        if (eci == null) {
            eci = Transaction.ECI.Undefined;
        }
        object.setEci(eci);

        String stateString = this.getStringForKey("state");
        Transaction.TransactionState state = Transaction.TransactionState.fromStringValue(stateString);
        if (state == null) {
            state = Transaction.TransactionState.TransactionStateError;
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

