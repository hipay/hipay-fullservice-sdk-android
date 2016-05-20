package com.hipay.hipayfullservice.screen.helper;

/**
 * Created by nfillion on 08/03/16.
 */

import android.content.Context;
import android.text.TextUtils;

import com.hipay.hipayfullservice.R;
import com.hipay.hipayfullservice.core.utils.DataExtractor;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Easy storage and retrieval of preferences.
 */
public class FormHelper {

    private FormHelper() {
        //no instance
    }

    public static boolean isInputDataValid(CharSequence cardNumber, CharSequence cardExpiration, CharSequence cardCVV, CharSequence cardOwner) {
        return  !TextUtils.isEmpty(cardNumber) &&
                !TextUtils.isEmpty(cardExpiration) &&
                !TextUtils.isEmpty(cardCVV) &&
                !TextUtils.isEmpty(cardOwner);
    }

    // http://rosettacode.org/wiki/Luhn_test_of_credit_card_numbers#Java
    public static boolean luhnTest(String number){
        int s1 = 0, s2 = 0;
        String reverse = new StringBuffer(number).reverse().toString();
        for(int i = 0 ;i < reverse.length();i++){
            int digit = Character.digit(reverse.charAt(i), 10);
            if(i % 2 == 0){//this is for odd digits, they are 1-indexed in the algorithm
                s1 += digit;
            }else{//add 2 * digit for 0-4, add 2 * digit - 9 for 5-9
                s2 += 2 * digit;
                if(digit >= 5){
                    s2 -= 9;
                }
            }
        }
        return (s1 + s2) % 10 == 0;
    }

    public static Set<String> getPaymentProductCodes(String plainTextNumber, Context context) {

        Set<String> set = new HashSet<>();

        String digitsOnly = plainTextNumber.replaceAll(" ", "");

        List<String> myArrayList = Arrays.asList(context.getResources().getStringArray(R.array.card_products_list));

        outerloop:
        for (String productCode: myArrayList) {

            String cardVisaInfoString = FormHelper.getStringResourceByName("card_"+productCode+"_info", context);

            try {

                JSONObject cardVisaInfo = new JSONObject(cardVisaInfoString);

                JSONArray ranges = DataExtractor.getJSONArrayFromField(cardVisaInfo, "ranges");

                for (int i = 0; i < ranges.length() ; i++) {

                    JSONObject range = DataExtractor.getJSONObjectFromField(ranges, i);

                    Integer firstInteger = DataExtractor.getIntegerFromField(range, "first");
                    String first = String.valueOf(firstInteger);

                    List<String> numbers = new ArrayList<>();
                    numbers.add(first);

                    Integer variable = DataExtractor.getIntegerFromField(range, "variable");
                    if (variable != null) {
                        for (int j = firstInteger+1; j <= firstInteger+variable; j++) {

                            numbers.add(String.valueOf(j));
                        }
                    }

                    //ok pour chaque element on tente la formule

                    for (String number: numbers) {

                        String digitsOnlyCopy = digitsOnly;

                        if (digitsOnlyCopy.length() > number.length()) {
                            digitsOnlyCopy = digitsOnlyCopy.substring(0, number.length());

                        } else {
                            number = number.substring(0, digitsOnlyCopy.length());
                        }

                        boolean result = number.contains(digitsOnlyCopy);

                        if (result) {
                            set.add(productCode);

                            //cut to the next product code
                            continue outerloop;
                        }
                    }
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return set;
    }

    public static String PACKAGE_NAME = "com.hipay.hipayfullservice";

    public static String getStringResourceByName(String aString, Context context) {

        String packageName = context.getPackageName();
        aString.replace("-", "_");
        int resId = context.getResources().getIdentifier(aString, "string", packageName);
        return context.getString(resId);
    }

    public static boolean hasValidCardLength(String plainTextNumber, String productCode, Context context) {

        String cardVisaInfoString = FormHelper.getStringResourceByName("card_"+productCode+"_info", context);

        try {

            JSONObject cardVisaInfo = new JSONObject(cardVisaInfoString);
            JSONObject lengths = DataExtractor.getJSONObjectFromField(cardVisaInfo, "lengths");

            Integer integer = DataExtractor.getIntegerFromField(lengths, "length");
            Set<Integer> integerSet = new HashSet<>(Arrays.asList(integer));

            Integer variable = DataExtractor.getIntegerFromField(lengths, "variable");
            if (variable != null) {
                for (int j = integer+1; j <= integer+variable; j++) {

                    integerSet.add(j);
                }
            }

            String onlyDigitsString = plainTextNumber.replaceAll(" ", "");
            int length = onlyDigitsString.length();

            if (integerSet.contains(Integer.valueOf(length))) {
                return true;
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return false;

    }

}
