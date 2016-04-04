package com.hipay.hipayfullservice.core.operations;

import android.content.Context;
import android.os.Bundle;

import com.hipay.hipayfullservice.core.client.config.ClientConfig;
import com.hipay.hipayfullservice.core.network.HttpResult;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by nfillion on 22/01/16.
 */


public abstract class GatewayOperation extends AbstractOperation {

    public GatewayOperation(Context context, Bundle bundle) {
        super(context, bundle);
    }

    protected URL getRequestURL() {

        String baseURL;

        switch (ClientConfig.getInstance().getEnvironment()) {

            case Stage: baseURL = ClientConfig.GatewayClientBaseURLStage; break;
            case Production: baseURL = ClientConfig.GatewayClientBaseURLProduction; break;
            default: baseURL = null; break;
        }

        if (baseURL != null) {
            try {
                return new URL(baseURL);
            } catch (MalformedURLException e) {
                e.printStackTrace();
                return null;
            }
        }

        return null;
    }

    @Override
    protected HttpURLConnection getHttpURLConnection() throws IOException {

        HttpURLConnection urlConnection = (HttpURLConnection) this.getRequestURL().openConnection();

        urlConnection.setRequestMethod(this.getRequestType().getStringValue());
        urlConnection.setRequestProperty("Accept", "application/json");
        urlConnection.setRequestProperty("Authorization", this.getAuthHeader());

        return urlConnection;
    }

    @Override
    protected HttpResult buildFromHttpResponse(HttpResult httpResult) {

        return httpResult;
    }
}
