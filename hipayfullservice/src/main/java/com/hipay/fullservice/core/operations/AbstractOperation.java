package com.hipay.fullservice.core.operations;

import android.content.Context;
import android.os.Bundle;
import android.util.Base64;

import com.hipay.fullservice.core.client.config.ClientConfig;
import com.hipay.fullservice.core.network.AbstractHttpClient;
import com.hipay.fullservice.core.network.HttpResult;

import java.nio.charset.StandardCharsets;

/**
 * Created by HiPay on 09/03/16.
 */

public abstract class AbstractOperation extends AbstractHttpClient<HttpResult> {
    public AbstractOperation(Context context, Bundle bundle) {
        super(context, bundle);
    }

    protected String getAuthHeader() {

        String username = ClientConfig.getInstance().getUsername();

        StringBuilder authHeaderStringBuilder = new StringBuilder(username).append(":");

        //sha1( orderId . amount . currency . passPhrase )
        String signature = this.getSignature();

        // include the md1 signature from merchant server
        if (signature != null) {

            authHeaderStringBuilder
                    .append(signature);

        } else {

            String password = ClientConfig.getInstance().getPassword();
            authHeaderStringBuilder.append(password);
        }


        String authHeaderString = authHeaderStringBuilder.toString();

        byte[] b = authHeaderString.getBytes(StandardCharsets.US_ASCII);

        String keySign = "Basic";
        if (signature != null) {
            keySign = "HS";
        }

        return new StringBuilder(keySign)
                .append(" ")
                .append(Base64.encodeToString(b, Base64.NO_WRAP))
                .toString();
    }
}
