package com.hipay.fullservice.core.monitoring;

import com.hipay.fullservice.BuildConfig;

import org.json.JSONException;
import org.json.JSONObject;

public class Components {

    protected String sdkType;
    protected String sdkVersion;

    public Components() {
        sdkType = "android";
        sdkVersion = BuildConfig.VERSION_NAME;
    }

    public JSONObject toJSONObject() {

        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("sdk_server", this.getSdkType());
            jsonObject.put("sdk_server_version", this.getSdkVersion());
            return jsonObject;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    private String getSdkType() {
        return sdkType;
    }

    private String getSdkVersion() {
        return sdkVersion;
    }

}
