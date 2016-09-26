package com.hipay.hipayfullservice.core.mapper.interfaces;

import android.os.Bundle;
import android.text.TextUtils;

import com.hipay.hipayfullservice.core.utils.DataExtractor;
import com.hipay.hipayfullservice.core.utils.Utils;

import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Created by nfillion on 28/01/16.
 */
public class MapMapper implements IBehaviour {

    protected JSONObject jsonObject;

    public MapMapper(JSONObject jsonObject) {
        this.setJsonObject(jsonObject);
    }

    public Object getObjectForKey(String key) {
        return DataExtractor.getObjectFromField(this.getJsonObject(), key);
    }

    public String getStringForKey(String key) {
        return DataExtractor.getStringFromField(this.getJsonObject(), key);
    }

    @Override
    public Float getFloatForKey(String key) {

        Object object = this.getObjectForKey(key);
        if (object != null ) {
            if (object instanceof String) {
                String digits = (String)object;

                Float floatForKey;
                try {
                    floatForKey = Float.parseFloat(digits);
                } catch (NumberFormatException e) {
                    floatForKey = null;
                }
                return floatForKey;
            }
        }

        return null;
    }

    public String getLowercaseStringForKey(String key) {

        String string = this.getStringForKey(key);
        if (string != null) {
            return string.toLowerCase(Locale.US);
        }

        return null;
    }

    public String getEnumCharForKey(String key) {

        String string = this.getStringForKey(key);
        if (string != null && string.length() == 1)  {

            return string;
        }

        return null;
    }

    @Override
    public Bundle getBundleForKey(String key) {
        return null;
    }

    public Number getNumberForKey(String key) {

        Object object = this.getObjectForKey(key);
        if (object != null ) {
            if (object instanceof String) {

                String digits = (String)object;
                if (TextUtils.isDigitsOnly(digits)) {

                    Number number = Integer.parseInt(digits);
                    return number;
                }
            }
        }

        return null;
    }

    public Integer getIntegerForKey(String key) {

        Object object = this.getObjectForKey(key);
        if (object != null ) {
            if (object instanceof String) {

                String digits = (String)object;
                if (TextUtils.isDigitsOnly(digits)) {

                    return Integer.parseInt(digits);
                }
            }
        }

        return null;
    }

    @Override
    public JSONObject getJSONObjectForKey(String key) {

        return DataExtractor.getJSONObjectFromField(this.getJsonObject(), key);
    }

    @Override
    public Date getDateForKey(String key) {

        String stringDate = this.getStringForKey(key);

        Date date = Utils.getBasicDateFromString(stringDate);

        if (date == null) {
            date = Utils.getDateISO8601FromString(stringDate);
        }

        return date;
    }

    public Boolean getBoolForKey(String key) {

        Boolean number = this.getBoolNumberForKey(key);
        if (number != null) {
            return number;
        }

        return null;
    }

    public Boolean getBoolNumberForKey(String key) {

        Object object = this.getObjectForKey(key);

        if (object instanceof String) {

            String string = (String) object;

            if (string.equalsIgnoreCase("true")) {
                return Boolean.TRUE;

            } else if (string.equalsIgnoreCase("false")) {
                return Boolean.FALSE;
            }
        } else if (object instanceof Boolean) {

            return (Boolean)object;
        }

        return null;
    }

    public Map getMapForKey(String key) {

        Object object = this.getObjectForKey(key);
        if (object instanceof Map) {
            return (Map)object;
        }
        return null;
    }

    public URL getURLForKey(String key) {

        String string = this.getStringForKey(key);
        if (string != null && !string.isEmpty()) {

            URL url = null;
            try {
                url = new URL(string);
                return new URL(string);

            } catch (MalformedURLException e) {
                e.printStackTrace();
                url = null;

            } finally {
                return url;
            }
        }

        return null;
    }

    public List getArrayFromObject(Object object) {

        return null;
    }

    public JSONObject getJsonObject() {
        return jsonObject;
    }

    public void setJsonObject(JSONObject jsonObject) {
        this.jsonObject = jsonObject;
    }
}
