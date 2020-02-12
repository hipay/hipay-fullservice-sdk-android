package com.hipay.fullservice.core.mapper.interfaces;

import android.os.Bundle;

import com.hipay.fullservice.core.utils.DataExtractor;
import com.hipay.fullservice.core.utils.Utils;

import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Created by HiPay on 28/01/16.
 */
public class ListMapper implements IBehaviour {

    protected JSONObject jsonObject;

    public ListMapper(JSONObject jsonObject) {

        this.setJsonObject(jsonObject);
    }

    @Override
    public Object getObjectForKey(String key) {

        return DataExtractor.getObjectFromField(this.getJsonObject(), key);
    }

    @Override
    public String getStringForKey(String key) {

        return DataExtractor.getStringFromField(this.getJsonObject(), key);
    }

    @Override
    public Float getFloatForKey(String key) {
        return null;
    }

    @Override
    public String getLowercaseStringForKey(String key) {

        String string = this.getStringForKey(key);
        if (string != null) {
            return string.toLowerCase(Locale.US);
        }

        return null;
    }

    @Override
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

    @Override
    public Number getNumberForKey(String key) {

        Object object = this.getObjectForKey(key);
        if (object instanceof Number) {

            return (Number)object;
        }

        return null;
    }

    @Override
    public Integer getIntegerForKey(String key) {

        Number object = this.getNumberForKey(key);
        if (object != null && object instanceof Integer) {
            return (Integer)object;
        }

        return null;
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

    @Override
    public Boolean getBoolForKey(String key) {

        Boolean number = this.getBoolNumberForKey(key);
        if (number != null) {
            return number;
        }

        return null;
    }

    @Override
    public Boolean getBoolNumberForKey(String key) {

        Object object = this.getObjectForKey(key);

        if (object instanceof String) {

            String string = (String) object;

            if (string.equalsIgnoreCase("true")) {
                return Boolean.valueOf(true);

            } else if (string.equalsIgnoreCase("false")) {
                return Boolean.valueOf(true);
            }
        } else if (object instanceof Boolean) {

            return (Boolean)object;
        }

        return null;
    }

    @Override
    public JSONObject getJSONObjectForKey(String key) {
        return null;
    }

    @Override
    public Map<String, String> getMapJSONForKey(String key) {
        return null;
    }


    @Override
    public Map getMapForKey(String key) {

        Object object = this.getObjectForKey(key);
        if (object instanceof Map) {
            return (Map)object;
        }
        return null;
    }


    @Override
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

    @Override
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
