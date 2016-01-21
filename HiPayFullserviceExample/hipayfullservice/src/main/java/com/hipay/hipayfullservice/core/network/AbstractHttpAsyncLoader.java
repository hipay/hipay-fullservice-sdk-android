package com.hipay.hipayfullservice.core.network;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.os.Bundle;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by nfillion on 21/01/16.
 */

public abstract class AbstractHttpAsyncLoader extends AsyncTaskLoader<String> {

    protected String mLastData;

    public AbstractHttpAsyncLoader(Context context, Bundle bundle) {
        super(context);
        mLastData = bundle.getString("DATA");
    }

    protected abstract String backgroundOperation() throws IOException;

    public HttpURLConnection getHttpConnection(String url, String type) throws IOException {
        URL uri;
        HttpURLConnection connection;
        uri = new URL(url);
        connection = (HttpURLConnection) uri.openConnection();
        connection.setRequestMethod(type); //type: POST, PUT, DELETE, GET
        connection.setDoOutput(true);
        connection.setDoInput(true);
        connection.setConnectTimeout(60000); //60 secs
        connection.setReadTimeout(60000); //60 secs
        connection.setRequestProperty("Accept", "application/json");
        connection.setRequestProperty("Content-Type", "application/json");
        return connection;
    }

    @Override
    public String loadInBackground() {
        try {
            return backgroundOperation();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void deliverResult(String data) {

        if (isReset()) {
            // An async query came in while the loader is stopped
            onReleaseResources(data);
            return;
        }

        String oldData = mLastData;
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
    public void onCanceled(String dataList) {
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

    protected void onReleaseResources(String data) {
        mLastData = null;
    }
}
