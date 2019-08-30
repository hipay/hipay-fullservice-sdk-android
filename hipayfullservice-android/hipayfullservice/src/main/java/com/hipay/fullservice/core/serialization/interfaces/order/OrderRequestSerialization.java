package com.hipay.fullservice.core.serialization.interfaces.order;

import android.os.Bundle;

import com.hipay.fullservice.core.logging.Logger;
import com.hipay.fullservice.core.models.PaymentProduct;
import com.hipay.fullservice.core.models.Transaction;
import com.hipay.fullservice.core.requests.order.OrderRequest;
import com.hipay.fullservice.core.requests.payment.AbstractPaymentMethodRequest;
import com.hipay.fullservice.core.requests.payment.CardTokenPaymentMethodRequest;
import com.hipay.fullservice.core.requests.payment.SepaDirectDebitPaymentMethodRequest;
import com.hipay.fullservice.core.utils.PaymentCardTokenDatabase;
import com.hipay.fullservice.core.utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Map;

/**
 * Created by nfillion on 04/02/16.
 */
public class OrderRequestSerialization extends OrderRelatedRequestSerialization {

    public OrderRequestSerialization(OrderRequest orderRequest) {
        super(orderRequest);
    }

    @Override
    public Map<String, String> getSerializedRequest() {

        Map<String, String> relatedRequestMap = super.getSerializedRequest();

        OrderRequest orderRequest = (OrderRequest)this.getModel();

        relatedRequestMap.put("payment_product", orderRequest.getPaymentProductCode());

        AbstractPaymentMethodRequest paymentMethodRequest = orderRequest.getPaymentMethod();

        //we don't have qiwi wallet token or ideal for now
        if (paymentMethodRequest instanceof CardTokenPaymentMethodRequest) {

            CardTokenPaymentMethodRequest cardTokenPaymentMethodRequest = (CardTokenPaymentMethodRequest)paymentMethodRequest;
            Map<String, String> cardTokenSerializedObject = cardTokenPaymentMethodRequest.getSerializedObject();

            relatedRequestMap.putAll(cardTokenSerializedObject);
        }
        else if (paymentMethodRequest instanceof SepaDirectDebitPaymentMethodRequest) {
            SepaDirectDebitPaymentMethodRequest sepaDirectDebitPaymentMethodRequest = (SepaDirectDebitPaymentMethodRequest)paymentMethodRequest;
            Map<String, String> sepaDirectDebitSerializedObject = sepaDirectDebitPaymentMethodRequest.getSerializedObject();

            relatedRequestMap.putAll(sepaDirectDebitSerializedObject);
        }

        //DSP2
        PaymentProduct paymentProduct = new PaymentProduct(orderRequest.getPaymentProductCode());

        if (paymentProduct.isDSP2Supported()) {
            addCardStored24hIfNeeded(relatedRequestMap);

            String eciValue = relatedRequestMap.get("eci");

            if (eciValue != null) {
                int eciInt = Integer.parseInt(eciValue);
                if (Transaction.ECI.fromIntegerValue(eciInt) == Transaction.ECI.RecurringECommerce) {
                    addEnrollmentDateIfNeeded(relatedRequestMap);
                }
            }
        }

        return relatedRequestMap;
    }

    @Override
    public String getQueryString() {

        return Utils.queryStringFromMap(this.getSerializedRequest());
    }

    @Override
    public Bundle getSerializedBundle() {
        return super.getSerializedBundle();
    }

    private void addCardStored24hIfNeeded(Map<String, String> map) {
        String accountInfo = map.get("account_info");

        JSONObject accountInfoJSON = null;
        JSONObject purchaseJSON = null;
        Integer cardStored24h = null;

        if (accountInfo != null) {
            try {
                accountInfoJSON = new JSONObject(accountInfo);
                purchaseJSON = accountInfoJSON.getJSONObject("purchase");

                if (purchaseJSON != null) {
                    cardStored24h = purchaseJSON.getInt("card_stored_24h");
                }
            } catch (JSONException e) {
            }
        }

        if (cardStored24h == null) {
            String currency = map.get("currency");
            cardStored24h = PaymentCardTokenDatabase.getInstance().numberOfCardSavedInLast24Hours(currency);

            if (purchaseJSON == null) {
                purchaseJSON = new JSONObject();
            }
            try {
                purchaseJSON.put("card_stored_24h", cardStored24h);

                if (accountInfoJSON == null) {
                    accountInfoJSON = new JSONObject();
                }
                accountInfoJSON.put("purchase", purchaseJSON);

            } catch (JSONException e) {
            }

            map.put("account_info", accountInfoJSON.toString());

            Logger.d("<Order> card_stored_24 attribute added to Order Request with value : " + cardStored24h);
        }
    }

    private  void addEnrollmentDateIfNeeded(Map<String, String> map) {
        String accountInfo = map.get("account_info");

        JSONObject accountInfoJSON = null;
        JSONObject paymentJSON = null;
        Integer enrollmentDateInteger = null;

        if (accountInfo != null) {
            try {
                accountInfoJSON = new JSONObject(accountInfo);
                paymentJSON = accountInfoJSON.getJSONObject("payment");

                if (paymentJSON != null) {
                    enrollmentDateInteger = paymentJSON.getInt("enrollment_date");
                }
            } catch (JSONException e) {
            }
        }

        String token = map.get("cardtoken");

        if (enrollmentDateInteger == null && token != null) {
            String currency = map.get("currency");
            Date enrollmentDate = PaymentCardTokenDatabase.getInstance().getEnrollmentDate(token, currency);

            if (enrollmentDate != null) {
                SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyyMMdd", Locale.US);
                enrollmentDateInteger = Integer.valueOf(dateFormatter.format(enrollmentDate));

                if (paymentJSON == null) {
                    paymentJSON = new JSONObject();
                }

                try {
                    paymentJSON.put("enrollment_date", enrollmentDateInteger);

                    if (accountInfoJSON == null) {
                        accountInfoJSON = new JSONObject();
                    }
                    accountInfoJSON.put("payment", paymentJSON);
                }
                catch (JSONException e) {
                }

                map.put("account_info", accountInfoJSON.toString());

                Logger.d("<Order> enrollment_date attribute added to Order Request with value : " + enrollmentDateInteger);
            }
        }
    }
}
