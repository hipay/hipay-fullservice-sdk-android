package com.hipay.fullservice.core.client.interfaces;

import android.content.Context;
import android.os.Bundle;

import com.hipay.fullservice.core.client.interfaces.callbacks.AbstractRequestCallback;
import com.hipay.fullservice.core.errors.Errors;
import com.hipay.fullservice.core.errors.exceptions.ApiException;
import com.hipay.fullservice.core.errors.exceptions.HttpException;
import com.hipay.fullservice.core.network.HttpResult;
import com.hipay.fullservice.core.operations.AbstractOperation;
import com.hipay.fullservice.core.utils.DataExtractor;
import com.hipay.fullservice.core.utils.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.IOException;

/**
 * Created by HiPay on 22/02/16.
 */
public abstract class AbstractReqHandler<T> implements IReqHandler {

    public AbstractReqHandler(T paymentPageRequest, String signature, AbstractRequestCallback callback) {

        this.setRequest(paymentPageRequest);
        this.setSignature(signature);
        this.setCallback(callback);
    }

    public AbstractReqHandler(T paymentPageRequest, AbstractRequestCallback callback) {

        this.setRequest(paymentPageRequest);
        this.setCallback(callback);
    }

    protected T request;

    protected T getRequest() {
        return request;
    }
    protected void setRequest(T request) {
        this.request = request;
    }

    protected AbstractRequestCallback callback;
    public AbstractRequestCallback getCallback() {
        return callback;
    }
    public void setCallback(AbstractRequestCallback callback) {
        this.callback = callback;
    }

    protected String signature;
    public String getSignature() {
        return signature;
    }
    public void setSignature(String signature) {
        this.signature = signature;
    }

    @Override
    public String getReqSignatureString() {
        return this.getSignature();
    }

    protected abstract String getDomain();

    public abstract String getReqQueryString();
    public abstract AbstractOperation getReqOperation(Context context, Bundle bundle);
    public abstract int getLoaderId();

    public void onError(Exception exception)
    {
        Utils.logFromException(exception, this.getDomain());
    }

    public abstract void onSuccess(JSONObject jsonObject);
    public abstract void onSuccess(JSONArray jsonArray);

    @Override
    public void handleCallback(HttpResult data) {

        HttpException httpException = null;
        JSONObject jsonObject = null;
        JSONArray jsonArray = null;

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

                Object json = new JSONTokener(bodyStream).nextValue();
                if (json instanceof JSONObject) {

                    jsonObject = new JSONObject(bodyStream);

                } else if (json instanceof JSONArray) {

                    jsonArray = new JSONArray(bodyStream);
                }

            } catch (JSONException e) {

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

        } else if (jsonArray != null ){

            onSuccess(jsonArray);
            //no-op
        }
    }

    private ApiException getApiException(HttpException httpException, JSONObject jsonObject) {

        String code = DataExtractor.getStringFromField(jsonObject, "code");
        String message = DataExtractor.getStringFromField(jsonObject, "message");
        String description = DataExtractor.getStringFromField(jsonObject, "description");

        Integer apiCode = null;
        if (code != null) {
            apiCode = Integer.valueOf(code);
        }

        Errors.Code errorCode = this.errorCodeForNumber(code);
        return new ApiException(
                message,
                errorCode.getIntegerValue(),
                description,
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
