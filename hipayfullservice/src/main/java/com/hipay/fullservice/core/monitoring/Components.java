package com.hipay.fullservice.core.monitoring;

import org.json.JSONException;
import org.json.JSONObject;

public class Components {

    protected String sdkType;
    protected String sdkVersion;

    public Components() {
        sdkType = "android";
        sdkVersion = com.hipay.fullservice.BuildConfig.LIBRARY_PACKAGE_NAME;
    }

    public JSONObject toJSONObject() {

        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("sdk_client", this.getSdkType());
            jsonObject.put("sdk_client_version", this.getSdkVersion());
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
