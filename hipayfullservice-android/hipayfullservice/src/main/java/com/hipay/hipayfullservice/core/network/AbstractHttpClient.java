package com.hipay.hipayfullservice.core.network;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;

import com.hipay.hipayfullservice.core.utils.Utils;

import java.io.IOException;
import java.io.InputStream;
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
    protected abstract HttpMethod getRequestType();
    protected abstract T buildFromHttpResponse(HttpResult httpResult);
    protected abstract String concatUrl();

    protected HttpResult backgroundOperation() {

        HttpResult httpResult = new HttpResult();

        boolean isCanceled = false;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            isCanceled = this.isLoadInBackgroundCanceled();
        }

        if (!isCanceled) {

            HttpURLConnection urlConnection;
            try {
                urlConnection = this.getHttpURLConnection();
            } catch (IOException exception) {
                httpResult.setIoException(exception);
                return httpResult;
            }

            try {
                httpResult.setStatusCode(urlConnection.getResponseCode());
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                httpResult.setBodyStream(Utils.readStream(urlConnection.getInputStream()));
            } catch (IOException e) {
                e.printStackTrace();
            }

            httpResult.setErrorStream(Utils.readStream(urlConnection.getErrorStream()));

            if (urlConnection != null) {
                urlConnection.disconnect();
            }

        }

        return httpResult;
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
