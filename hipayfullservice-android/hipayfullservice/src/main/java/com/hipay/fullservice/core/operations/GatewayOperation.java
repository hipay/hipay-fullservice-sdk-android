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

                    //if the request contains params like transaction?orderid=transactionOrderId
                    if (params.contains("=")) {
                        requestUrl = Utils.concatenateParams(requestUrl, params);
                    }

                    //if the request is just an url like transaction/referenceTransaction
                    else {
                        requestUrl = Utils.concatenatePath(requestUrl, params);
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

                if (this.isV2()) {
                    return ClientConfig.GatewayClientBaseURLNewStage;

                } else {
                    return ClientConfig.GatewayClientBaseURLStage;
                }
            }

            case PRODUCTION: {

                if (this.isV2()) {
                    return ClientConfig.GatewayClientBaseURLNewProduction;

                } else {
                    return ClientConfig.GatewayClientBaseURLProduction;
                }
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
    protected String getSignature() {

        if (this.getBundle() != null) {

            return this.getBundle().getString("HS_signature");
        }
        return null;
    }

    @Override
    protected HttpURLConnection getHttpURLConnection() throws IOException {

        HttpURLConnection urlConnection = (HttpURLConnection) this.getRequestURL().openConnection();

        urlConnection.setRequestMethod(this.getRequestType().getStringValue());
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
