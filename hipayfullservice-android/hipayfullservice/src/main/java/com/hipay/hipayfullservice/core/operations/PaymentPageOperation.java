package com.hipay.hipayfullservice.core.operations;

import android.content.Context;
import android.os.Bundle;

import com.hipay.hipayfullservice.core.utils.Utils;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * Created by nfillion on 22/01/16.
 */

public class PaymentPageOperation extends GatewayOperation {

    public PaymentPageOperation(Context context, Bundle bundle) {
        super(context, bundle);
    }

    protected URL getRequestURL() {

        URL requestURL;

        try {
            requestURL = Utils.concatenatePath(super.getRequestURL(), "payment_products");

            if (this.getBundle() != null) {

                String queryParams = this.getBundle().getString("queryParams");

                if (queryParams != null) {

                    requestURL = Utils.concatenateParams(requestURL, queryParams);
                }
            }

        } catch (URISyntaxException e) {
            e.printStackTrace();
            requestURL = null;

        } catch (MalformedURLException e) {

            e.printStackTrace();
            requestURL = null;
        }

        return requestURL;
    }


    @Override
    protected HttpMethod getRequestType() {
        return HttpMethod.GET;
    }
}
