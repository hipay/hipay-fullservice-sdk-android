package com.hipay.hipayfullservice.core.client.interfaces;

import android.content.Context;
import android.os.Bundle;

import com.hipay.hipayfullservice.core.client.interfaces.callbacks.AbstractRequestCallback;
import com.hipay.hipayfullservice.core.client.interfaces.callbacks.SecureVaultRequestCallback;
import com.hipay.hipayfullservice.core.errors.Errors;
import com.hipay.hipayfullservice.core.errors.exceptions.HttpException;
import com.hipay.hipayfullservice.core.models.PaymentCardToken;
import com.hipay.hipayfullservice.core.network.HttpResult;
import com.hipay.hipayfullservice.core.operations.AbstractOperation;
import com.hipay.hipayfullservice.core.requests.AbstractRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * Created by nfillion on 22/02/16.
 */
public abstract class AbstractReqHandler implements IReqHandler {

    protected AbstractRequest request;
    protected AbstractRequestCallback callback;

    protected AbstractRequest getRequest() {
        return request;
    }

    protected void setRequest(AbstractRequest request) {
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

    //TODO abstract class with handle callback
    public abstract void onError(HttpException httpException);
    public abstract void onSuccess(JSONObject jsonObject);

    @Override
    public void handleCallback(HttpResult data) {

        HttpException httpException = null;
        JSONObject jsonObject = null;

        //TODO first on check la IOException
        IOException ioException = data.getIoException();
        String bodyStream = data.getBodyStream();
        String errorStream = data.getErrorStream();
        Integer statusCode = data.getStatusCode() != null ? data.getStatusCode()/100 : null;

        if (ioException != null) {

            //TODO exception io failed, on la renvoie telle quelle.

            //io exception
            httpException = new HttpException(
                    Errors.HTTPConnectionFailedDescription,
                    Errors.Code.HTTPConnectionFailed.getIntegerValue(),
                    new HttpException(ioException)
            );

        } else if (bodyStream != null) {

            //TODO typically a 200.
            // continue if json body received from hipay server

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

            //TODO typically a 400/500 client/server error

            JSONObject object;
            try {
                object = new JSONObject(errorStream);

            } catch (JSONException e) {
                object = null;

                httpException = new HttpException(
                        Errors.HTTPServerDescription,
                        Errors.Code.HTTPServer.getIntegerValue(),
                        new HttpException(e));
            }

            if (object != null) {

                //on check les codes d'erreurs

                switch (statusCode) {

                    case 4: {

                        //TODO on traite le json pour avoir plus d'infos a renvoyer au-dessus

                        //TODO httpclient error
                        httpException = new HttpException(
                                Errors.HTTPClientDescription,
                                Errors.Code.HTTPClient.getIntegerValue(),
                                null
                        );
                    } break;

                    case 5: {

                        //TODO on traite le json
                        //TODO httpserver error
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

            //TODO wtf error (OTHER), data is totally null

            httpException = new HttpException(
                    Errors.HTTPOtherDescription,
                    Errors.Code.HTTPOther.getIntegerValue(),
                    null
            );
        }

        if (jsonObject != null) {
            onSuccess(jsonObject);

        } else if (httpException != null) {
           onError(httpException);

        } else {
            //no-op
        }

    }
    //TODO concrete classes will get json object or HTTPException

}
