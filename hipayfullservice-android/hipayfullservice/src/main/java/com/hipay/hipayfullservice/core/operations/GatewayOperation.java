package com.hipay.hipayfullservice.core.operations;

import android.content.Context;
import android.os.Bundle;
import android.util.Base64;

import com.hipay.hipayfullservice.core.client.config.ClientConfig;
import com.hipay.hipayfullservice.core.network.AbstractHttpClient;
import com.hipay.hipayfullservice.core.network.HttpResult;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

/**
 * Created by nfillion on 22/01/16.
 */


public abstract class GatewayOperation extends AbstractHttpClient<HttpResult> {

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

    private String getAuthHeader() {

        String username = ClientConfig.getInstance().getUsername();
        String password = ClientConfig.getInstance().getPassword();

        //if (username == null || password == null) {}

        StringBuilder authHeaderBuilder = new StringBuilder();
        authHeaderBuilder.append(username).append(":").append(password);

        String authHeaderString = new StringBuilder(username)
                .append(":")
                .append(password)
                .toString();

        byte[] b;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
            b = authHeaderString.getBytes(StandardCharsets.US_ASCII);
        } else {

            try {
                b = authHeaderString.getBytes("US-ASCII");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                return null;
            }
        }

        return new StringBuilder("Basic ")
                .append(Base64.encodeToString(b, Base64.DEFAULT))
                .toString();
    }

    @Override
    protected HttpResult buildFromHttpResponse(HttpResult httpResult) {

        return httpResult;
    }
}
