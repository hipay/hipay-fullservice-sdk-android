package com.hipay.hipayfullservice.example;

import android.app.LoaderManager;
import android.content.Loader;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.hipay.hipayfullservice.core.client.ClientConfig;
import com.hipay.hipayfullservice.core.models.PaymentProduct;
import com.hipay.hipayfullservice.core.network.HttpResponse;
import com.hipay.hipayfullservice.core.network.PaymentPageRequestClient;
import com.hipay.hipayfullservice.core.requests.order.PaymentPageRequest;
import com.hipay.hipayfullservice.core.requests.payment.CardTokenPaymentMethodRequest;

import java.io.IOException;
import java.util.Arrays;

public class HPFActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<HttpResponse> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hpf);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        ClientConfig.Environment environment = ClientConfig.Environment.Stage;
        String username = "94654679.api.hipay-tpp.com";
        String password = "U5hQh4ZT0UyXpYhG8WI4trYQ";
        String appURLScheme = "hipayexample";

        ClientConfig.getInstance().setConfigEnvironment(
                environment,
                username,
                password,
                appURLScheme
        );



        String params = this.testRequests();
        Bundle queryBundle = new Bundle();
        queryBundle.putString("queryParams",params);

        getLoaderManager().initLoader(0, queryBundle, this);


    }

    private String testRequests() {

        PaymentPageRequest paymentPageRequest = new PaymentPageRequest();

        paymentPageRequest.setAmount(225);
        paymentPageRequest.setCurrency("EUR");
        paymentPageRequest.setOrderId("TEST_SDK_IOS_1454520936");
        paymentPageRequest.setShortDescription("Outstanding item");
        paymentPageRequest.getCustomer().setCountry("FR");
        paymentPageRequest.getCustomer().setFirstname("John");
        paymentPageRequest.getCustomer().setLastname("Doe");
        paymentPageRequest.setPaymentCardGroupingEnabled(true);
        paymentPageRequest.setMultiUse(true);
        paymentPageRequest.setPaymentProductCategoryList(

                Arrays.asList(

                        PaymentProduct.PaymentProductCodeCB,
                        PaymentProduct.PaymentProductCodeMasterCard,
                        PaymentProduct.PaymentProductCodeVisa,
                        PaymentProduct.PaymentProductCodeAmericanExpress,
                        PaymentProduct.PaymentProductCodeMaestro,
                        PaymentProduct.PaymentProductCodeDiners
                )
        );

        paymentPageRequest.getCustomer().setEmail("nfillion@hipay.com");

        paymentPageRequest.setAuthenticationIndicator(CardTokenPaymentMethodRequest.AuthenticationIndicator.AuthenticationIndicatorUndefined);

        return paymentPageRequest.getStringParameters();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_h, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public Loader<HttpResponse> onCreateLoader(int id, Bundle bundle) {
        return new PaymentPageRequestClient(this, bundle);
    }

    @Override
    public void onLoadFinished(Loader<HttpResponse> loader, HttpResponse data) {

        Log.i("data", "data : " + data);
        Log.i("data", "data : " + data);

        String stream = null;

        try {
            stream = data.readStream(data.getBodyStream());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {

            Log.i("stream", "stream : " + stream);
        }
    }

    @Override
    public void onLoaderReset(Loader<HttpResponse> loader) {
    }
}
