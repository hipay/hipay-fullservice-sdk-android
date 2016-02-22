package com.hipay.hipayfullservice.core.operations;

import android.content.Context;
import android.os.Bundle;

import com.hipay.hipayfullservice.core.utils.Utils;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * Created by nfillion on 16/02/16.
 */

public class OrderOperation extends GatewayOperation {

    public OrderOperation(Context context, Bundle bundle) {
        super(context, bundle);
    }

    @Override
    protected HttpMethod getRequestType() {
        return HttpMethod.POST;
    }

    protected URL getRequestURL() {

        URL requestURL;

        try {
            requestURL = Utils.concatenatePath(super.getRequestURL(), "order");

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
    protected HttpURLConnection getHttpURLConnection() throws IOException {


        HttpURLConnection urlConnection = super.getHttpURLConnection();

        if (this.getBundle() != null) {

            String queryParams = this.getBundle().getString("queryParams");

            if (queryParams != null) {

                /*
                String newP = "description=Outstanding%20item&" +
                        "pending_url=hipayexample%3A%2F%2Fhipay-fullservice%2Fgateway%2Forders%2FTEST_SDK_IOS_1455810276%2Fpending&" +
                        "amount=225.00&" +
                        "cancel_url=hipayexample%3A%2F%2Fhipay-fullservice%2Fgateway%2Forders%2FTEST_SDK_IOS_1455810276%2Fcancel&" +
                        "device_fingerprint=0500013z8oAZXqyERcrrYolxtopTMaCcC3EsiF20c9IrWlo6DvbyEWzQeZn65e7VHAO1v3yOjb0wFNV6Pf2E9%2BitwR3xqQ1UWDdtTEV%2F72Ub%2FVMzwGHcDrMBbWcNmsOZBuTP4KGkwSW25EO%2FxeUX6ghafOpLgdjqlXh1N%2F3855LFINTOdfdahTYPiuCa95zr8sQ2CCXp%2F1y3VIhlsFT0EXEXsVGN0dWruSJ52ahzuP6paUg4pwruyt9%2FDpibfkB1bdNu%2FYX3r27mCgXLp6Ky13Qb4rN3Qlboz3ztKEDhUTF%2Fcs2uIaT63ROhUlPDc0CPWjBXaNuJy9S0sB8m62o7KXo%2Fq1fouwcIkbCUxe%2BDbuyO5PZ9R8sGZaetxWv%2BtYZtCk9Ef%2FzUFNysCCej1bYJuCNCbiFwSY3SjlKVIJhvQsHDT9tK71BVgM5lC2FPUTbCtdBg7GMmM3Lx%2FuB6GPpldmlm2Nxej5zXtt726YWo%2F7RK%2BnTaK9k6d2WSB0SP5GWN1ZEHtV9hG%2BiGjtNWdmP0256XiqOLEvRMRi6vlATNXTp3Ztd6rE7hSp5hp0k4Yt2LqapTLj%2B1m5v9vZiCEPf%2BFSG%2FEdgjy0D9OqrI9cT4Ip4cq9j0XHzfF3GTYGl8PGOXbnSNQR8MpJtwEXpkkL6qAa6sp%2FaxE%2BNW%2BDBjGYmnEHtsHT74H44d9wtYv88pGs1s26dfrhj%2BXXbxySw0flZjQN13B9khxSH%2BchxJGI0WNCQql3t6wmEfZVlB4SULjHsslKZbwslAertAh77uwpqWIDxSTrZQjxMqCWVqIbITkwnTiO%2FTwbq8tT%2FKB86XV%2Bb5LXqAKNdp53xXYJbNXYonkQFKV3eATWwyknQznugrJjc0%2FV4T8FDZ4F3%2BjZshWejwkVvsX10Xnj6tLZsKfybyaGJBXvx8dbIF4I2Vz328nd1aCdPcLvm5lUcvLH3E1%2FutFjg%2FqsFtwvxFTczFi%2Fhm27%2FVyyVS3%2FQPaKZy%2BU51cnyoaWLG7LZzO8nzilXzmqXeS55cDVb2o9MKYVoklzhPRWH0%2F2K4hF%2Bu9T%2FQdAA8JWRO7Fzw%2Fdk4Nbw3Xby10f0Exqe8S7RooOelOy1640J2DAfvVTTkZB%2FbMhrfJu5k4KoaXWeM7kd5ZjTOYYZKi2rdB%2F8lMPZ%2FdeI3CIfFQE%2BBTiBH0UHjnxzHrjecIEJL2fD4V3g6gfqGkCfFBLBMfvVZJSAnfeZbZ%2FfC3kffe53ZcHQFUwXtPzJxnqAsThoNv%2BhTioqTtpCgO8%2BK9BHBKnbP%2FF4LpPiDQN0nM4KZTjB0IEkhTa%2Fxa6Wx1%2FVJOmFymg48eJQ0yeH2qWlVL%2BMpJKwtpyOVvGC4gXKpezPyCQcY6I4q2N53hkQqD09%2BlZIR1XUEGGNRg1%2FNtWQFTi7XWOTW%2Bct7faJA9ezhEMom4qnT1od1SPZNk2rH2q6PNDDWOvxa55D%2FYUDEDqmr35v1qez%2B%2FUXx%2BOTuAT%2FCpgQRB5CBoBtBQRfONBXWSJ3c6m7P3v8ZclM9aMu08urKv5U87ZHxroV6X%2BzvW0egCiWjnHxNa%2BIkwdwniLn%2BQK1Vpy8LewpNg7cF8t7sWz%2F%2BoRk2ZApBuuNBJV1oh7sLfqRxeZ2lGtIbYXofcFxGzDLLKHzyfch62%2FXGJhowbZ%2FUQUVzG2dLrEnvfLdWa4jyolsr%2Bb8DTFidg6dDgl9rYD0zEjgCjkdTLP4V5eID5rDL%2FYJ6Y8R4%2FQ6yEYyCo7%2BE9MKqy%2BQLLF0XOX1Hz0Aa3v93VWhq%2FILAnoA%2BP2y9S3fAT6hQPWc60ZeovfzU%2FBuahHNQqdFDrgypKxNVIkmj2U2Eu5AR%2BoF0vfn1AKvE%2BwbXAvPe8jZVq0%2BvMC%2BiR6VhILGnSs6po4j%2BMV4g1YGHB9Hco8fUwcH8r%2FM%3D&" +
                        "accept_url=hipayexample%3A%2F%2Fhipay-fullservice%2Fgateway%2Forders%2FTEST_SDK_IOS_1455810276%2Faccept&" +
                        "http_user_agent=Mozilla%2F5.0%20%28iPhone%3B%20CPU%20iPhone%20OS%209_2%20like%20Mac%20OS%20X%29%20AppleWebKit%2F601.1.46%20%28KHTML%2C%20like%20Gecko%29%20Mobile%2F13C75&" +
                        "lastname=Doe&" +
                        "firstname=John&" +
                        "payment_product=bcmc-mobile&" +
                        "exception_url=hipayexample%3A%2F%2Fhipay-fullservice%2Fgateway%2Forders%2FTEST_SDK_IOS_1455810276%2Fexception&" +
                        "orderid=TEST_SDK_IOS_1455810276&" +
                        "language=en_FR&" +
                        "decline_url=hipayexample%3A%2F%2Fhipay-fullservice%2Fgateway%2Forders%2FTEST_SDK_IOS_1455810276%2Fdecline&" +
                        "currency=EUR&" +
                        "email=jtiret%40hipay.com&" +
                        "country=FR";
                */

                DataOutputStream wr = new DataOutputStream(urlConnection.getOutputStream());
                wr.writeBytes(queryParams);
                wr.flush();
                wr.close();
            }
        }

        return urlConnection;
    }

}