package com.hipay.fullservice.core.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import com.hipay.fullservice.core.models.PaymentCardToken;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by nfillion on 05/01/2017.
 */
public class PaymentCardTokenDatabase {

    private static PaymentCardTokenDatabase mInstance = null;
    private static Context context = null;

    final private static String SHARED_PREFERENCES_NAME = "HiPay";

    private PaymentCardTokenDatabase() {
    }

    public static PaymentCardTokenDatabase getInstance(){
        if(mInstance == null)
        {
            mInstance = new PaymentCardTokenDatabase();
        }
        return mInstance;
    }

    private void clearPaymentCardTokens(Context context, String currency) {
        this.context = context;
        SharedPreferences preferences = context.getSharedPreferences(SHARED_PREFERENCES_NAME, 0);
        SharedPreferences.Editor editor = preferences.edit();
        editor.remove("paymentCardToken_" + currency);
        //editor.commit();
        editor.apply();
    }

    public void clearPaymentCardTokens(Context context) {
        this.context = context;
        Set<String> currencyList = this.getPaymentCardTokensCurrencyList(context);
        if (currencyList != null && !currencyList.isEmpty()) {

            for (String currency : currencyList) {
                clearPaymentCardTokens(context, currency);
            }
        }
    }

    public Set<String> getPaymentCardTokens(Context context, String currency) {
        if (context == null) {
            return null;
        }
        this.context = context;

        SharedPreferences preferences = context.getSharedPreferences(SHARED_PREFERENCES_NAME, 0);

        //return a copy to avoid the Android bug
        Set<String> set = preferences.getStringSet("paymentCardToken_" + currency, null);
        if (set != null && !set.isEmpty()) {
            return new HashSet<>(set);
        }

        return null;
    }

    private Set<String> getPaymentCardTokensCurrencyList(Context context) {
        if (context == null) {
            return null;
        }
        this.context = context;

        SharedPreferences preferences = context.getSharedPreferences(SHARED_PREFERENCES_NAME, 0);

        //return a copy to avoid the Android bug
        Set<String> set = preferences.getStringSet("paymentCardTokenCurrencyList", null);
        if (set != null && !set.isEmpty()) {
            return new HashSet<>(set);
        }

        return null;
    }

    private void deleteCurrencyInList(Context context, String currency) {
        if (context == null) {
            return;
        }
        this.context = context;

        Set<String> currencyList = this.getPaymentCardTokensCurrencyList(context);
        if (currencyList != null && !currencyList.isEmpty()) {

            if (currencyList.remove(currency)) {

                SharedPreferences preferences = context.getSharedPreferences(SHARED_PREFERENCES_NAME, 0);
                SharedPreferences.Editor editor = preferences.edit();

                editor.putStringSet("paymentCardTokenCurrencyList", currencyList);
                editor.apply();
            }
        }
    }

    private void addCurrencyInList(Context context, String currency) {
        if (context == null) {
            return;
        }
        this.context = context;

        Set<String> currencyList = this.getPaymentCardTokensCurrencyList(context);
        if (currencyList != null && !currencyList.isEmpty()) {

            if (currencyList.add(currency)) {

                SharedPreferences preferences = context.getSharedPreferences(SHARED_PREFERENCES_NAME, 0);
                SharedPreferences.Editor editor = preferences.edit();

                editor.putStringSet("paymentCardTokenCurrencyList", currencyList);
                //editor.commit();
                editor.apply();
            }

        } else {

            SharedPreferences preferences = context.getSharedPreferences(SHARED_PREFERENCES_NAME, 0);
            SharedPreferences.Editor editor = preferences.edit();

            editor.putStringSet("paymentCardTokenCurrencyList", new HashSet<>(Arrays.asList(currency)));
            //editor.commit();
            editor.apply();
        }
    }

    public void delete(Context context, PaymentCardToken paymentCardToken, String currency) {
        if (context == null) {
            return;
        }
        this.context = context;

        Set<String> tokens = this.getPaymentCardTokens(context, currency);

        if (tokens != null && !tokens.isEmpty()) {

            Bundle paymentCardTokenBundle = paymentCardToken.toBundle();
            String paymentCardTokenJSONString = Utils.fromBundle(paymentCardTokenBundle);

            if (tokens.remove(paymentCardTokenJSONString)) {

                if (tokens.isEmpty()) {
                    deleteCurrencyInList(context, currency);
                }

                SharedPreferences preferences = context.getSharedPreferences(SHARED_PREFERENCES_NAME, 0);
                SharedPreferences.Editor editor = preferences.edit();

                editor.putStringSet("paymentCardToken_" + currency, tokens);
                //editor.commit();
                editor.apply();
            }
        }
    }

    public void save(Context context, PaymentCardToken paymentCardToken, String currency) {
        if (context == null) {
            return;
        }
        this.context = context;

        Set<String> tokens = this.getPaymentCardTokens(context, currency);

        paymentCardToken.setDateAdded(new Date());
        Bundle paymentCardTokenBundle = paymentCardToken.toBundle();
        String paymentCardTokenJSONString = Utils.fromBundle(paymentCardTokenBundle);

        if (tokens != null && !tokens.isEmpty()) {

            if (tokens.add(paymentCardTokenJSONString)) {

                SharedPreferences preferences = context.getSharedPreferences(SHARED_PREFERENCES_NAME, 0);
                SharedPreferences.Editor editor = preferences.edit();

                editor.putStringSet("paymentCardToken_" + currency, tokens);
                //editor.commit();
                editor.apply();
            }

        } else {

            this.addCurrencyInList(context, currency);
            //create it
            SharedPreferences preferences = context.getSharedPreferences(SHARED_PREFERENCES_NAME, 0);
            SharedPreferences.Editor editor = preferences.edit();

            editor.putStringSet("paymentCardToken_" + currency, new HashSet<>(Arrays.asList(paymentCardTokenJSONString)));
            //editor.commit();
            editor.apply();
        }
    }

    public int numberOfCardSavedInLast24Hours(String currency) {
        Set<String> cardTokens = getPaymentCardTokens(context, currency);
        int count = 0;

        if (cardTokens == null) {
            return count;
        }

        for (String token : cardTokens) {
            Bundle tokenBundle = Utils.fromJSONString(token);
            if (tokenBundle != null) {
                PaymentCardToken paymentCardToken = PaymentCardToken.fromBundle(tokenBundle);

                Calendar calendar = Calendar.getInstance();
                calendar.add(Calendar.DAY_OF_YEAR, -1);
                Date beforeDate = calendar.getTime();

                if (paymentCardToken.getDateAdded().after(beforeDate)) {
                    count++;
                }

            }
        }

        return count;

    }

    public Date getEnrollmentDate(String token, String currency) {
        Set<String> cardTokens = getPaymentCardTokens(context, currency);

        if (cardTokens == null) {
            return null;
        }

        for (String cardToken : cardTokens) {
            Bundle tokenBundle = Utils.fromJSONString(cardToken);
            if (tokenBundle != null) {
                PaymentCardToken paymentCardToken = PaymentCardToken.fromBundle(tokenBundle);

                if (paymentCardToken.getToken() != null && token != null && paymentCardToken.getToken().equals(token)) {
                    return paymentCardToken.getDateAdded();
                }
            }
        }

        return null;
    }
}
