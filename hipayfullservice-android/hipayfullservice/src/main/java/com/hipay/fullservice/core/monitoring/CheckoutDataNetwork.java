package com.hipay.fullservice.core.monitoring;

import android.os.AsyncTask;

import com.hipay.fullservice.core.client.config.ClientConfig;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;

public class CheckoutDataNetwork extends AsyncTask<CheckoutData, Void, Integer> {

    private static final String statsURLStage = "https://stage-data.hipay.com/checkout-data";
    private static final String statsURLProduction = "https://data.hipay.com/checkout-data";

    @Override
    protected Integer doInBackground(CheckoutData... datas) {
        CheckoutData data = datas[0];
        String json = data.toJSON();
        HttpURLConnection urlConnection;

        String statsURL = null;

        switch (ClientConfig.getInstance().getEnvironment()) {
            case Stage:
                statsURL = statsURLStage;
                break;
            case Production:
                statsURL = statsURLProduction;
                break;
        }

        try {
            //Connect
            urlConnection = (HttpURLConnection) ((new URL(statsURL).openConnection()));
            urlConnection.setDoOutput(true);
            urlConnection.setRequestProperty("Content-Type", "application/json");
            urlConnection.setRequestProperty("Accept", "application/json");
            urlConnection.setRequestProperty("X-Who-Api", "sdk-android-hipay");
            urlConnection.setRequestMethod("POST");
            urlConnection.setConnectTimeout(1000);
            urlConnection.connect();

            //Write
            OutputStream outputStream = urlConnection.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
            writer.write(json);
            writer.close();
            outputStream.close();

            //Read
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "UTF-8"));

            String line = null;
            StringBuilder sb = new StringBuilder();

            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line);
            }

            bufferedReader.close();
            return urlConnection.getResponseCode();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(Integer integer) {
        super.onPostExecute(integer);
    }
}
