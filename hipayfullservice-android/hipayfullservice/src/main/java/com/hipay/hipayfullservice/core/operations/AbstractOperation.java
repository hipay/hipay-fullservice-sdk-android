package com.hipay.hipayfullservice.core.operations;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.util.Base64;

import com.hipay.hipayfullservice.core.client.config.ClientConfig;
import com.hipay.hipayfullservice.core.network.AbstractHttpClient;
import com.hipay.hipayfullservice.core.network.HttpResult;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

/**
 * Created by nfillion on 09/03/16.
 */

public abstract class AbstractOperation extends AbstractHttpClient<HttpResult> {
    public AbstractOperation(Context context, Bundle bundle) {
        super(context, bundle);
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    protected String getAuthHeader() {

        String username = ClientConfig.getInstance().getUsername();
        String password = ClientConfig.getInstance().getPassword();

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
                .append(Base64.encodeToString(b, Base64.NO_WRAP))
                .toString();
    }
}
