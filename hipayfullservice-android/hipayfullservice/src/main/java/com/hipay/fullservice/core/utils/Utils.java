package com.hipay.fullservice.core.utils;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;

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
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;

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
        BufferedReader r = new BufferedReader(new InputStreamReader(is), 1000);
        try {
            for (String line = r.readLine(); line != null; line = r.readLine()) {
                sb.append(line);
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
}
