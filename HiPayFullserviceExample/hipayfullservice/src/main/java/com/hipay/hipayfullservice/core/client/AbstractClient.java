package com.hipay.hipayfullservice.core.client;

import com.hipay.hipayfullservice.core.network.HttpResponse;

import org.json.JSONObject;

import java.io.InputStream;

/**
 * Created by nfillion on 21/01/16.
 */
public class AbstractClient {

    public AbstractClient(HttpResponse httpResponse) {

        //TODO Mapping


        /*
        InputStream in = httpResponse.getBodyStream();

        try {



        } catch () {

        } finally {

        }

        String bodyString = HttpResponse.
        ret = readStream(in);

        // let's parse in JSON now.

        try {
            JSONObject body = new JSONObject(ret);

        } catch (JSONException exception) {

        }

        String body =


                */

        /*
        try {

            //TODO check the mime type
            if (urlConnection.getResponseCode() / 100 == 2 urlConnection.getContent() ==) {

                InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                ret = readStream(in);

                // let's parse in JSON now.

                try {
                    JSONObject body = new JSONObject(ret);

                } catch (JSONException exception) {

                }
                //urlConnection.

            } else {

                //TODO return the right error
                ret = null;
            }

        } finally {
            urlConnection.disconnect();
            return ret;
        }
        */
    }
}
