package com.hipay.fullservice.core.utils;

import android.annotation.TargetApi;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;

import com.hipay.fullservice.core.errors.exceptions.ApiException;
import com.hipay.fullservice.core.errors.exceptions.HttpException;
import com.hipay.fullservice.core.logging.Logger;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TimeZone;

import io.card.payment.CardType;

/**
 * Created by nfillion on 25/01/16.
 */

public class Utils {

    public static URL concatenatePath(URL baseUrl, String extraPath) throws URISyntaxException,
            MalformedURLException {

        if (baseUrl == null) return null;

        URI uri = baseUrl.toURI();
        String newPath = uri.getPath() + '/' + extraPath;
        URI newUri = uri.resolve(newPath);
        return newUri.toURL();
    }

    public static URL concatenateParams(URL baseUrl, String extraParams) throws URISyntaxException,
            MalformedURLException {

        if (baseUrl == null) return null;

        URI uri = baseUrl.toURI();
        String newPath = uri.getPath() + '?' + extraParams;
        URI newUri = uri.resolve(newPath);
        return newUri.toURL();
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    public static String queryStringFromMap(Map<String, String> map) {

        if (map == null || map.isEmpty()) return null;

        List<String> parameters = new ArrayList<>(map.size());

        for(Entry<String, String> entry : map.entrySet()) {

            try {

                String encodedKey;
                String encodedValue;

                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT){
                    // Do something for lollipop and above versions
                    encodedKey = URLEncoder.encode(entry.getKey(),StandardCharsets.UTF_8.name()).replaceAll("\\+", "%20");
                    encodedValue = URLEncoder.encode(entry.getValue(),StandardCharsets.UTF_8.name()).replaceAll("\\+", "%20");

                } else {

                    encodedKey = URLEncoder.encode(entry.getKey(),"UTF-8").replaceAll("\\+", "%20");
                    encodedValue = URLEncoder.encode(entry.getValue(), "UTF-8").replaceAll("\\+", "%20");
                }

                String part = new StringBuilder(encodedKey).append("=").append(encodedValue).toString();

                parameters.add(part);

            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                return null;
            }
        }

        Collections.sort(parameters);
        return TextUtils.join("&", parameters);
    }

    public static Date getBasicDateFromString(String stringDate) {

        Date date = null;

        if (!TextUtils.isEmpty(stringDate)) {

            SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ssZZZ", Locale.US);
            try {
                date = dateFormatter.parse(stringDate);
            } catch (java.text.ParseException e) {
                return null;
                //e.printStackTrace();
            }
        }

        return date;
    }

    public static String getStringFromDateISO8601(Date date) {

        String stringDate = null;
        if (date != null) {

            SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZZZZZ", Locale.US);
            stringDate = dateFormatter.format(date);
        }

        return stringDate;
    }

    public static String getStringFromDateUTC(Date date) {

        String stringDate = null;
        if (date != null) {

            SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
            dateFormatter.setTimeZone(TimeZone.getTimeZone("UTC"));
            stringDate = dateFormatter.format(date);
        }

        return stringDate;
    }

    public static String getPaymentFormStringFromDate(Date date) {

        String stringDate = null;
        if (date != null) {

            SimpleDateFormat dateFormatter = new SimpleDateFormat("MM/yy", Locale.US);
            stringDate = dateFormatter.format(date);
        }

        return stringDate;
    }

    public static Date getDateISO8601FromString(String stringDate) {

        Date date = null;

        if (!TextUtils.isEmpty(stringDate)) {

            SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZZZZZ", Locale.US);
            try {
                date = dateFormatter.parse(stringDate);
            } catch (java.text.ParseException e) {
                //e.printStackTrace();
                return null;
            }
        }

        return date;
    }

    public static Date getYearAndMonthFromString(String stringDate) {

        Date date = null;

        if (!TextUtils.isEmpty(stringDate)) {

            SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyyMM", Locale.US);
            try {
                date = dateFormatter.parse(stringDate);
            } catch (java.text.ParseException e) {
                //e.printStackTrace();
                return null;
            }
        }

        return date;
    }

    public static int getScreenHeight() {
        return Resources.getSystem().getDisplayMetrics().heightPixels;
    }

    public static int getScreenWidth() {
        return Resources.getSystem().getDisplayMetrics().widthPixels;
    }

    public static String bundleToString(Bundle bundle) {
        if (bundle == null) {
            return null;
        }
        String string = "Bundle{";
        for (String key : bundle.keySet()) {
            string += " " + key + " => " + bundle.get(key) + ";";
        }
        string += " }Bundle";
        return string;
    }

    public static String readStream(InputStream is) {

        if (is == null) return null;

        String string = null;

        StringBuilder sb = new StringBuilder();
        BufferedReader r = null;
        try {
            r = new BufferedReader(new InputStreamReader(is, "UTF-8"), 1000);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        try {
            for (String line = r.readLine(); line != null; line = r.readLine()) {
                sb.append(line).append(System.getProperty("line.separator"));
            }

            is.close();

        } catch (IOException e) {
            e.printStackTrace();

            return null;
        }

        if (!sb.toString().isEmpty()) {
            string = sb.toString();
        }

        return string;
    }

    public static String mapToJson(Map<String, String> map) {

        if (map != null && !map.isEmpty()) {

            JSONObject jsonObject = null;
            try {
                jsonObject = new JSONObject(map);

            } catch (Exception exception) {
                jsonObject = null;

            } finally {

                if (jsonObject != null) {

                    String mapString = jsonObject.toString();
                    return mapString;
                }
            }
        }

        return null;
    }

    public static Map<String, String> jsonToMap(String t) throws JSONException {

        HashMap<String, String> map = new HashMap<>();
        JSONObject jObject = new JSONObject(t);
        Iterator<?> keys = jObject.keys();

        while( keys.hasNext() ){
            String key = (String)keys.next();
            String value = jObject.getString(key) == "null" ? null : jObject.getString(key);
            map.put(key, value);
        }

        if (!map.isEmpty()) {
            return map;
        }

        return null;
    }

    public static Bundle fromJSONString(String jsonString){

        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            if (jsonObject != null) {
                return fromJSON(jsonObject);
            }

        } catch (JSONException ignored) {}

        return null;
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    private static JSONObject fromBundleToJSON(Bundle bundle) {

        JSONObject json = new JSONObject();
        Set<String> keys = bundle.keySet();
        for (String key : keys) {
            try {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    json.put(key, JSONObject.wrap(bundle.get(key)));
                } else {
                    json.put(key, bundle.get(key));
                }
            } catch(JSONException e) {
                //Handle exception here
                json = null;
            }
        }

        return json;
    }

    public static String fromBundle(Bundle bundle) {

        JSONObject jsonObject = fromBundleToJSON(bundle);
        if (jsonObject != null) {
            return jsonObject.toString();
        }

        return null;
    }

    private static Bundle fromJSON(JSONObject jsonObject) throws JSONException {

        Bundle bundle = new Bundle();

        Iterator iter = jsonObject.keys();

        while(iter.hasNext()){

            String key = (String)iter.next();

            Object object = jsonObject.get(key);
            if (object instanceof String) {
                bundle.putString(key, (String)object);
            }

            if (object instanceof Integer) {
                bundle.putInt(key, (Integer)object);
            }
        }

        return bundle;
    }

    public static String formatCardNumber(String numStr) {
        return formatString(numStr, true, null);
    }

    private static String formatString(String numStr, boolean filterDigits, CardType type) {
        String digits;
        if (filterDigits) {
            digits = getDigitsOnlyString(numStr);
        } else {
            digits = numStr;
        }
        if (type == null) {
            type = CardType.fromCardNumber(digits);
        }
        int numLen = type.numberLength();
        if (digits.length() == numLen) {
            if (numLen == 16) {
                return formatSixteenString(digits);
            } else if (numLen == 15) {
                return formatFifteenString(digits);
            }
        }
        return numStr; // at the worst case, pass back what was given
    }

    public static String getDigitsOnlyString(String numString) {
        StringBuilder sb = new StringBuilder();
        for (char c : numString.toCharArray()) {
            if (Character.isDigit(c)) {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    private static String formatFifteenString(String digits) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 15; i++) {
            if (i == 4 || i == 10) {
                sb.append(' ');
            }
            sb.append(digits.charAt(i));
        }
        return sb.toString();
    }

    private static String formatSixteenString(String digits) {
        StringBuilder sb = new StringBuilder();
        {
            for (int i = 0; i < 16; i++) {
                if (i != 0 && i % 4 == 0) {
                    sb.append(' ');// insert every 4th char, except at end
                }
                sb.append(digits.charAt(i));
            }
        }
        return sb.toString();
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    public static void logFromException(Exception exception, String client) {

        Throwable throwable = exception;

        do
        {

            StringBuilder stringBuilder;

            if (client != null) {
                stringBuilder = new StringBuilder("Error ").append(client).append(":");
            } else {
                stringBuilder = new StringBuilder("Error:");
            }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                stringBuilder.append(System.lineSeparator());

            } else {
                stringBuilder.append(System.getProperty("line.separator"));
            }

            if (throwable instanceof ApiException)
            {
                ApiException apiException = (ApiException) throwable;

                stringBuilder.append("API code: ").append(apiException.getApiCode());

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    stringBuilder.append(System.lineSeparator());

                } else {
                    stringBuilder.append(System.getProperty("line.separator"));
                }

                stringBuilder.append("Status code: ").append(apiException.getStatusCode());

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    stringBuilder.append(System.lineSeparator());

                } else {
                    stringBuilder.append(System.getProperty("line.separator"));
                }

                stringBuilder.append("Message: ").append(apiException.getMessage());

            } else

            if (throwable instanceof HttpException)
            {
                HttpException httpException = (HttpException) throwable;
                stringBuilder.append("Status code: ").append(httpException.getStatusCode());

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    stringBuilder.append(System.lineSeparator());

                } else {
                    stringBuilder.append(System.getProperty("line.separator"));
                }

                stringBuilder.append("Message: ").append(httpException.getMessage());
            }

            Logger.d(stringBuilder.toString());
            throwable = throwable.getCause();

        } while (throwable != null);

    }
}
