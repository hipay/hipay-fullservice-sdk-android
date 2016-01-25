package com.hipay.hipayfullservice.core.network;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.os.Bundle;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;

/**
 * Created by nfillion on 21/01/16.
 */

public abstract class AbstractHttpAsyncLoader<T> extends AsyncTaskLoader<T> {

    protected T mLastData;

    public AbstractHttpAsyncLoader(Context context, Bundle bundle) {
        super(context);
    }

    protected abstract HttpURLConnection getHttpURLConnection() throws IOException;
    protected abstract String getRequestType() throws IOException;
    protected abstract T buildFromHttpResponse(HttpResponse httpResponse);

    protected HttpResponse backgroundOperation() {

        HttpResponse httpResponse = new HttpResponse();
        HttpURLConnection urlConnection = null;

        try {

            urlConnection = this.getHttpURLConnection();

            httpResponse.setStatusCode(urlConnection.getResponseCode());
            httpResponse.setBodyStream(urlConnection.getInputStream());
            httpResponse.setErrorStream(urlConnection.getErrorStream());

        } catch (IOException exception) {

            httpResponse.setIoException(exception);

        } finally {

            if (urlConnection != null) {
                urlConnection.disconnect();
            }

            return httpResponse;

        }
    }

    protected String readStream(InputStream is) throws IOException {

        if (is == null) return null;

        StringBuilder sb = new StringBuilder();
        BufferedReader r = new BufferedReader(new InputStreamReader(is),1000);
        for (String line = r.readLine(); line != null; line =r.readLine()){
            sb.append(line);
        }
        is.close();
        return sb.toString();
    }

    @Override
    public T loadInBackground() {
        mLastData = this.buildFromHttpResponse(backgroundOperation());
        return mLastData;
    }

    @Override
    public void deliverResult(T data) {

        if (isReset()) {
            // An async query came in while the loader is stopped
            onReleaseResources(data);
            return;
        }

        T oldData = mLastData;
        mLastData = data;

        if (isStarted()) {
            super.deliverResult(data);
        }
        if (oldData != null) {
            onReleaseResources(oldData);
        }
    }

    @Override
    protected void onStartLoading() {
        if (mLastData != null) {
            deliverResult(mLastData);
        }

        if (takeContentChanged() || mLastData == null) {
            forceLoad();
        }
    }


    @Override
    protected void onStopLoading() {
        // Attempt to cancel the current load task if possible.
        cancelLoad();
    }

    @Override
    public void onCanceled(T dataList) {
        if (dataList != null) {
            onReleaseResources(dataList);
        }
    }

    @Override
    protected void onReset() {
        super.onReset();

        // Ensure the loader is stopped
        onStopLoading();

        if (mLastData != null) {
            onReleaseResources(mLastData);
        }
    }

    protected void onReleaseResources(T data) {

        mLastData = data;
        mLastData = null;
    }
}
