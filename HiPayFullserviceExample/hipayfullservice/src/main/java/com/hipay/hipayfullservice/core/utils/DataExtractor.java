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

    public static String getStringFromField(JSONObject jsonObject, String field) {

        if (checkParams(jsonObject, field)) {
            return jsonObject.optString(field, null);
        }
        return null;
    }

    public static Long getLongFromField(JSONObject jsonObject, String field) {

        if (checkParams(jsonObject, field)) {
            return jsonObject.optLong(field, -1);
        }
        return null;
    }

    public static Integer getIntegerFromField(JSONObject jsonObject, String field) {

        if (checkParams(jsonObject, field)) {
            return jsonObject.optInt(field, -1);
        }
        return null;
    }

    public static Double getDoubleFromField(JSONObject jsonObject, String field) {

        if (checkParams(jsonObject, field)) {
            return jsonObject.optDouble(field, -1);
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

    public static JSONArray getJSONArrayFromField(JSONObject jsonObject, String field) {


        if (checkParams(jsonObject, field)) {
            return jsonObject.optJSONArray(field);
        }

        return null;
    }

    public static JSONObject getJSONObjectFromJSONArray(JSONArray jsonArray, int i) {

        return jsonArray.optJSONObject(i);
    }

    public static JSONArray getJSONArrayFromJSONArray(JSONArray array, int i) {

        return array.optJSONArray(i);
    }

    public static Date getDateFromField(JSONObject jsonObject, String field) {

        //TODO check the right format

        if (checkParams(jsonObject, field)) {

            //TODO check the date format
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



}

