package com.hipay.fullservice.core.operations;

import android.content.Context;
import android.os.Bundle;

import com.hipay.fullservice.core.client.config.ClientConfig;
import com.hipay.fullservice.core.network.HttpResult;
import com.hipay.fullservice.core.utils.Utils;
import com.hipay.fullservice.core.utils.enums.HttpMethod;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * Created by nfillion on 22/01/16.
 */

public abstract class SecureVaultOperation extends AbstractOperation {

    public SecureVaultOperation(Context context, Bundle bundle) {
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

                    //needed for the lookupPaymentCard request
                    for (String param : params.split("&")) {

                        if (param.contains("token")) {

                            String token = param.substring(param.lastIndexOf('=') + 1);
                            requestUrl = Utils.concatenatePath(requestUrl, token);

                        }
                    }

                    for (String param : params.split("&")) {

                        if (param.contains("request_id")) {
                            requestUrl = Utils.concatenateParams(requestUrl, param);

                        }
                    }
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

            case STAGE: {
                return ClientConfig.SecureVaultClientBaseURLStage;
            }
            case PRODUCTION: {
                return ClientConfig.SecureVaultClientBaseURLProduction;
            }

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

        urlConnection.setRequestMethod(this.getRequestType().getValue());
        urlConnection.setRequestProperty("Accept", "application/json");
        urlConnection.setRequestProperty("AUTHORIZATION", this.getAuthHeader());

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

