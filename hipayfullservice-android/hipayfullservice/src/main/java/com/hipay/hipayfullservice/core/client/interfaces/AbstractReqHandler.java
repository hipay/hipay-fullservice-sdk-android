package com.hipay.hipayfullservice.core.client.interfaces;

import android.content.Context;
import android.os.Bundle;

import com.hipay.hipayfullservice.core.client.interfaces.callbacks.AbstractRequestCallback;
import com.hipay.hipayfullservice.core.errors.Errors;
import com.hipay.hipayfullservice.core.errors.exceptions.ApiException;
import com.hipay.hipayfullservice.core.errors.exceptions.HttpException;
import com.hipay.hipayfullservice.core.network.HttpResult;
import com.hipay.hipayfullservice.core.operations.AbstractOperation;
import com.hipay.hipayfullservice.core.utils.DataExtractor;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * Created by nfillion on 22/02/16.
 */
public abstract class AbstractReqHandler<T> implements IReqHandler {

    protected T request;
    protected AbstractRequestCallback callback;

    protected T getRequest() {
        return request;
    }

    protected void setRequest(T request) {
        this.request = request;
    }

    public AbstractRequestCallback getCallback() {
        return callback;
    }

    public void setCallback(AbstractRequestCallback callback) {
        this.callback = callback;
    }

    public abstract String getReqQueryString();
    public abstract AbstractOperation getReqOperation(Context context, Bundle bundle);
    public abstract int getLoaderId();

    public abstract void onError(Exception exception);
    public abstract void onSuccess(JSONObject jsonObject);

    @Override
    public void handleCallback(HttpResult data) {

        HttpException httpException = null;
        JSONObject jsonObject = null;

        IOException ioException = data.getIoException();
        String bodyStream = data.getBodyStream();
        String errorStream = data.getErrorStream();
        Integer statusCode = data.getStatusCode() != null ? data.getStatusCode()/100 : null;

        if (ioException != null) {

            //io exception
            httpException = new HttpException(
                    Errors.HTTPNetworkUnavailableDescription,
                    Errors.Code.HTTPNetworkUnavailable.getIntegerValue(),
                    new HttpException(ioException)
            );

        } else if (bodyStream != null) {

            //typically a 200.
            //continue if json body received from hipay server

            try {
                jsonObject = new JSONObject(bodyStream);

            } catch (JSONException e) {
                jsonObject = null;

                httpException = new HttpException(
                        Errors.HTTPServerDescription,
                        Errors.Code.HTTPServer.getIntegerValue(),
                        new HttpException(e));
            }

        } else if (errorStream != null) {

            //typically a 400/500 client/server error

            try {
                jsonObject = new JSONObject(errorStream);

            } catch (JSONException e) {
                jsonObject = null;

                httpException = new HttpException(
                        Errors.HTTPServerDescription,
                        Errors.Code.HTTPServer.getIntegerValue(),
                        new HttpException(e));
            }

            if (jsonObject != null) {

                //on check les codes d'erreurs

                switch (statusCode) {

                    case 4: {

                        //httpclient error
                        httpException = new HttpException(
                                Errors.HTTPClientDescription,
                                Errors.Code.HTTPClient.getIntegerValue(),
                                null
                        );
                    } break;

                    case 5: {

                        //httpserver error
                        httpException = new HttpException(
                                Errors.HTTPServerDescription,
                                Errors.Code.HTTPServer.getIntegerValue(),
                                null
                        );
                    } break;

                    default: {

                        //wtf error
                        httpException = new HttpException(
                                Errors.HTTPOtherDescription,
                                Errors.Code.HTTPOther.getIntegerValue(),
                                null
                        );

                    } break;
                }
            }

        } else {

            //wtf error (OTHER), data is totally null

            httpException = new HttpException(
                    Errors.HTTPOtherDescription,
                    Errors.Code.HTTPOther.getIntegerValue(),
                    null
            );
        }

        //first on check si l'exception existe

        if (httpException != null) {

            ApiException apiException = this.getApiException(httpException, jsonObject);
            onError(apiException);

        } else if (jsonObject != null) {

            onSuccess(jsonObject);

        } else {

            //no-op
        }
    }

    private ApiException getApiException(HttpException httpException, JSONObject jsonObject) {

        String code = DataExtractor.getStringFromField(jsonObject, "code");
        String message = DataExtractor.getStringFromField(jsonObject, "message");
        //String description = DataExtractor.getStringFromField(jsonObject, "description");

        Integer apiCode = null;
        if (code != null) {
            apiCode = Integer.valueOf(code);
        }

        Errors.Code errorCode = this.errorCodeForNumber(code);
        return new ApiException(
                message,
                errorCode.getIntegerValue(),
                apiCode,
                httpException);
    }

    Errors.Code errorCodeForNumber(String codeNumber)
    {

        if (codeNumber == null || (codeNumber.length() != 3 && codeNumber.length() != 7)) {
            return Errors.Code.APIOther;
        }

        String substring = codeNumber.substring(0, 3);

        if (substring.equalsIgnoreCase("100")) {
            return Errors.Code.APIConfiguration;
        }

        else if (substring.equalsIgnoreCase("101")) {
            return Errors.Code.APIValidation;
        }

        else if (substring.equalsIgnoreCase("102")) {
            return Errors.Code.APICheckout;
        }

        else if (substring.equalsIgnoreCase("300")) {
            return Errors.Code.APICheckout;
        }

        else if (substring.equalsIgnoreCase("301")) {
            return Errors.Code.APICheckout;
        }

        else if (substring.equalsIgnoreCase("302")) {
            return Errors.Code.APIMaintenance;
        }

        else if (substring.equalsIgnoreCase("303")) {
            return Errors.Code.APICheckout;
        }

        else if (substring.equalsIgnoreCase("304")) {
            return Errors.Code.APICheckout;
        }

        else if (substring.equalsIgnoreCase("400")) {
            return Errors.Code.APIAcquirer;
        }

        else if (substring.equalsIgnoreCase("401")) {
            return Errors.Code.APIAcquirer;
        }

        //Luhn check
        if (codeNumber.equalsIgnoreCase("409")) {
            return Errors.Code.APIValidation;
        }

        return Errors.Code.APIOther;
    }
}
