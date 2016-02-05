package com.hipay.hipayfullservice.core.network;

import android.content.Context;
import android.os.Bundle;

import com.hipay.hipayfullservice.core.client.AbstractClient;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by nfillion on 22/01/16.
 */


public class HttpClient extends AbstractHttpClient<HttpResponse> {

    public HttpClient(Context context, Bundle bundle) {
        super(context, bundle);
    }

    protected URL getRequestURL() throws IOException {
        return new URL("http://www.google.fr/humans.txt");
    }

    @Override
    protected HttpMethod getRequestType() {
        return HttpMethod.GET;
    }

    @Override
    protected HttpURLConnection getHttpURLConnection() throws IOException {

        HttpURLConnection urlConnection = (HttpURLConnection) this.getRequestURL().openConnection();

        urlConnection.setRequestMethod(this.getRequestType().getStringValue()); //type: POST, PUT, DELETE, GET
        urlConnection.setRequestProperty("Accept", "application/json");

        //urlConnection.setDoOutput(true);
        //urlConnection.setDoInput(true);
        //urlConnection.setConnectTimeout(60000); //60 secs
        //urlConnection.setReadTimeout(60000); //60 secs
        //urlConnection.setRequestProperty("Accept", "application/json");
        //urlConnection.setRequestProperty("Content-Type", "application/json");

        return urlConnection;
    }

    @Override
    protected HttpResponse buildFromHttpResponse(HttpResponse httpResponse) {

        return httpResponse;
    }
}
