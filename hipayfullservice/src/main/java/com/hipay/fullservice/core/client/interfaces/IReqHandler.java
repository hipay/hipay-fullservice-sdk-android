package com.hipay.fullservice.core.client.interfaces;

import android.content.Context;
import android.os.Bundle;

import com.hipay.fullservice.core.network.HttpResult;
import com.hipay.fullservice.core.operations.AbstractOperation;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by HiPay on 22/02/16.
 */
public interface IReqHandler {

    String getReqQueryString();
    String getReqSignatureString();
    AbstractOperation getReqOperation(Context context, Bundle bundle);
    void handleCallback(HttpResult result);
    int getLoaderId();
    void onError(Exception exception);
    void onSuccess(JSONObject jsonObject);
    void onSuccess(JSONArray jsonArray);

}
