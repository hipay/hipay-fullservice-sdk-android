package com.hipay.hipayfullservice.core.network;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.os.Bundle;

import java.io.IOException;
import java.net.HttpURLConnection;

/**
 * Created by nfillion on 21/01/16.
 */

public abstract class AbstractHttpClient<T> extends AsyncTaskLoader<T> {

    protected T mLastData;
    protected Bundle bundle;

    public AbstractHttpClient(Context context, Bundle bundle) {
        super(context);

        this.setBundle(bundle);
    }

    protected abstract HttpURLConnection getHttpURLConnection() throws IOException;
    protected abstract HttpMethod getRequestType() throws IOException;
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


    protected enum HttpMethod {

        GET ("GET"),
        POST ("POST");

        protected final String method;
        HttpMethod(String method) {

            this.method = method;
        }

        public String getStringValue() {
            return this.method;
        }
    }

    public Bundle getBundle() {
        return bundle;
    }

    public void setBundle(Bundle bundle) {
        this.bundle = bundle;
    }
}
