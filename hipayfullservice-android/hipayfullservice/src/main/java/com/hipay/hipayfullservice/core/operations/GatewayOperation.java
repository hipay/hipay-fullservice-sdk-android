package com.hipay.hipayfullservice.core.operations;

import android.content.Context;
import android.os.Bundle;

import com.hipay.hipayfullservice.core.client.config.ClientConfig;
import com.hipay.hipayfullservice.core.network.HttpResult;
import com.hipay.hipayfullservice.core.utils.Utils;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * Created by nfillion on 22/01/16.
 */

public abstract class GatewayOperation extends AbstractOperation {

    public GatewayOperation(Context context, Bundle bundle) {
        super(context, bundle);
    }

    protected URL getRequestURL() {

        URL requestUrl;
        try {
            requestUrl = new URL(this.getBaseUrl());
            requestUrl = Utils.concatenatePath(requestUrl, this.concatUrl());

            if (this.getRequestType().equals(HttpMethod.GET)) {

                String params = this.getQueryParams();
                if (params != null) {
                    requestUrl = Utils.concatenateParams(requestUrl, params);
                }
            }

        } catch (URISyntaxException e) {
            requestUrl = null;
        } catch (MalformedURLException e) {
            requestUrl = null;
        }

        return requestUrl;
    }

    private String getBaseUrl() {

        switch (ClientConfig.getInstance().getEnvironment()) {

            case Stage: return ClientConfig.GatewayClientBaseURLStage;
            case Production: return ClientConfig.GatewayClientBaseURLProduction;
            default: return null;
        }
    }

    protected String getQueryParams() {

        if (this.getBundle() != null) {

            return this.getBundle().getString("queryParams");
        }
        return null;
    }

    @Override
    protected HttpURLConnection getHttpURLConnection() throws IOException {

        HttpURLConnection urlConnection = (HttpURLConnection) this.getRequestURL().openConnection();

        urlConnection.setRequestMethod(this.getRequestType().getStringValue());
        urlConnection.setRequestProperty("Accept", "application/json");
        urlConnection.setRequestProperty("Authorization", this.getAuthHeader());

        if (this.getRequestType().equals(HttpMethod.POST)) {

            String queryParams = this.getQueryParams();
            if (queryParams != null) {

                DataOutputStream wr = new DataOutputStream(urlConnection.getOutputStream());
                wr.writeBytes(queryParams);
                wr.flush();
                wr.close();
            }
        }

        return urlConnection;
    }

    @Override
    protected HttpResult buildFromHttpResponse(HttpResult httpResult) {

        return httpResult;
    }
}
