package com.hipay.fullservice.core.mapper;

import android.os.Bundle;

import com.hipay.fullservice.core.mapper.interfaces.MapMapper;
import com.hipay.fullservice.core.models.FraudScreening;
import com.hipay.fullservice.core.models.Order;
import com.hipay.fullservice.core.models.PaymentCardToken;
import com.hipay.fullservice.core.models.PaymentMethod;
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

        if (super.isValid()) {

            if (this.getStringForKey("state") != null)
                return true;
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

        Bundle paymentMethodBundle = this.getBundleForKey("paymentMethod");
        PaymentMethod paymentMethod = null;
        if (paymentMethodBundle != null) {
            // check if it's a paymentCardToken
            paymentMethod = PaymentCardToken.fromBundle(paymentMethodBundle);
        }
        object.setPaymentMethod(paymentMethod);

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

        JSONObject paymentMethodObject = this.getJSONObjectForKey("paymentMethod");
        PaymentMethod paymentMethod = null;
        if (paymentMethodObject != null) {
            paymentMethod = PaymentCardToken.fromJSONObject(paymentMethodObject);
        }
        object.setPaymentMethod(paymentMethod);

        JSONObject threeDSecureObject = this.getJSONObjectForKey("threeDSecure");
        ThreeDSecure threeDSecure = null;
        if (threeDSecure != null) {
            threeDSecure = ThreeDSecure.fromJSONObject(threeDSecureObject);
        }
        object.setThreeDSecure(threeDSecure);

        return object;

    }

    @Override
    public Transaction mappedObjectFromUri() {

        Transaction transaction = new Transaction();

        transaction.setTransactionReference(this.getStringForKey("reference"));

        String stateString = this.getStringForKey("state");
        Transaction.TransactionState state = Transaction.TransactionState.fromStringValue(stateString);
        if (state == null) {
            state = Transaction.TransactionState.TransactionStateError;
        }
        transaction.setState(state);

        Integer statusInteger = this.getIntegerForKey("status");
        TransactionRelatedItem.TransactionStatus status = TransactionRelatedItem.TransactionStatus.fromIntegerValue(statusInteger);
        if (status == null) {
            status = TransactionRelatedItem.TransactionStatus.TransactionStatusUnknown;
        }
        transaction.setStatus(status);

        transaction.setTest(this.getBoolForKey("test"));

        transaction.setIpAddress(this.getStringForKey("ip"));

        transaction.setIpCountry(this.getStringForKey("country"));


        String avsString = this.getEnumCharForKey("avscheck");
        Transaction.AVSResult avsResult = Transaction.AVSResult.fromStringValue(avsString);
        if (avsResult == null) {
            avsResult = Transaction.AVSResult.AVSResultNotApplicable;
        }
        transaction.setAvsResult(avsResult);

        String cvcResultString = this.getEnumCharForKey("cvccheck");
        Transaction.CVCResult cvcResult = Transaction.CVCResult.fromStringValue(cvcResultString);
        if (cvcResult == null) {
            cvcResult = Transaction.CVCResult.CVCResultNotApplicable;
        }
        transaction.setCvcResult(cvcResult);

        transaction.setPaymentProduct(this.getStringForKey("pp"));

        transaction.setReason(this.getStringForKey("reason"));

        transaction.setCdata1(this.getStringForKey("cdata1"));
        transaction.setCdata2(this.getStringForKey("cdata2"));
        transaction.setCdata3(this.getStringForKey("cdata3"));
        transaction.setCdata4(this.getStringForKey("cdata4"));
        transaction.setCdata5(this.getStringForKey("cdata5"));
        transaction.setCdata6(this.getStringForKey("cdata6"));
        transaction.setCdata7(this.getStringForKey("cdata7"));
        transaction.setCdata8(this.getStringForKey("cdata8"));
        transaction.setCdata9(this.getStringForKey("cdata9"));
        transaction.setCdata10(this.getStringForKey("cdata10"));


        String orderId = this.getStringForKey("orderid");
        if (orderId != null)
        {
            Order order = new Order();

            order.setOrderId(orderId);
            order.setLanguage(this.getStringForKey("lang"));
            order.setEmail(this.getStringForKey("email"));
            order.setCustomerId(this.getStringForKey("cid"));

            transaction.setOrder(order);
        }

        String scoring = this.getStringForKey("score");
        if (scoring != null) {

            FraudScreening fraudScreening = new FraudScreening();

            fraudScreening.setScoring(this.getIntegerForKey("score"));

            String resultString = this.getLowercaseStringForKey("fraud");
            FraudScreening.FraudScreeningResult result = FraudScreening.FraudScreeningResult.fromStringValue(resultString);
            if (result == null) {
                result = FraudScreening.FraudScreeningResult.FraudScreeningResultUnknown;
            }
            fraudScreening.setResult(result);

            String reviewString = this.getLowercaseStringForKey("review");
            FraudScreening.FraudScreeningReview review = FraudScreening.FraudScreeningReview.fromStringValue(reviewString);
            if (review == null) {
                review = FraudScreening.FraudScreeningReview.FraudScreeningReviewNone;
            }
            fraudScreening.setReview(review);

            transaction.setFraudScreening(fraudScreening);
        }







        //TODO a lot to do here
        String enrollmentStatus = sanitizer.getValue("veres");
        String authenticationStatus = sanitizer.getValue("pares");

        //TODO a lot to do here
        String cardToken = sanitizer.getValue("cardtoken");
        String cardPan = sanitizer.getValue("cardpan");
        String cardBrand = sanitizer.getValue("cardbrand");
        String cardCountry = sanitizer.getValue("cardcountry");

        String cardExpiry = sanitizer.getValue("cardexpiry");






        if (uri != null) {

            UrlQuerySanitizer sanitizer = new UrlQuerySanitizer(uri.toString());

            //on va build un Bundle de gg.

            Bundle bundle = new Bundle();

        }

        return transaction;





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


        //nothing for now

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

