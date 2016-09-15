package com.hipay.hipayfullservice.core.utils;

import java.util.Date;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by nfillion on 25/01/16.
 */

public class DataExtractor {

    private static boolean checkParams(JSONObject jsonObject, String field) {

        if (jsonObject == null || field == null) {
            return false;
        }
        return true;
    }

    private static boolean checkParams(JSONArray jsonArray, int i) {

        if (jsonArray == null || jsonArray.length() <= i ) {
            return false;
        }
        return true;
    }

    public static Object getObjectFromField(JSONObject jsonObject, String field) {

        if (checkParams(jsonObject, field)) {
            return jsonObject.opt(field);
        }
        return null;
    }

    public static String getStringFromField(JSONObject jsonObject, String field) {

        if (checkParams(jsonObject, field)) {
            return jsonObject.optString(field, null);
        }
        return null;
    }

    public static Integer getIntegerFromField(JSONObject jsonObject, String field) {

        if (checkParams(jsonObject, field)) {

            Integer optInt = jsonObject.optInt(field, Integer.MIN_VALUE);
            if (optInt != Integer.MIN_VALUE) {
                return optInt;
            }
        }
        return null;
    }

    public static Integer getIntegerFromField(JSONArray jsonArray, int i) {

        if (checkParams(jsonArray, i)) {
            return jsonArray.optInt(i, -1);
        }
        return null;
    }

    public static JSONObject getJSONObjectFromField(JSONObject jsonObject,
                                                    String field) {

        if (checkParams(jsonObject, field)) {
            return jsonObject.optJSONObject(field);
        }
        return null;
    }

    public static JSONObject getJSONObjectFromField(JSONArray jsonArray,
                                                    int i) {

        if (checkParams(jsonArray, i)) {
            return jsonArray.optJSONObject(i);
        }
        return null;
    }

    public static JSONArray getJSONArrayFromField(JSONObject jsonObject, String field) {

        if (checkParams(jsonObject, field)) {
            return jsonObject.optJSONArray(field);
        }

        return null;
    }

    //public static JSONArray getJSONArrayFromField(JSONArray jsonArray, int i) {

        //if (checkParams(jsonArray, i)) {
            //return jsonArray.optJSONArray(i);
        //}

        //return null;
    //}

    public static Date getDateFromField(JSONObject jsonObject, String field) {

        //TODO check the right format

        if (checkParams(jsonObject, field)) {

            //return jsonObject.optLong(field, -1);
        }

        return null;
        /*
        if ((jsonObject == null) || (field == null))
            return null;
        try {
            long timestamp = jsonObject.getLong(field);
            Date date = new Date(timestamp * 1000);
            return date;

        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        */
    }

    public static Date getDateFromField(JSONArray jsonArray, int i) {

        //TODO check the right format

        if (checkParams(jsonArray, i)) {

            //return jsonObject.optLong(field, -1);
        }

        return null;
    }
}
