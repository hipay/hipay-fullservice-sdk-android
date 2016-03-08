package com.hipay.hipayfullservice.example;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.hipay.hipayfullservice.core.client.config.ClientConfig;
import com.hipay.hipayfullservice.core.models.PaymentProduct;
import com.hipay.hipayfullservice.core.requests.order.OrderRequest;
import com.hipay.hipayfullservice.core.requests.order.PaymentPageRequest;
import com.hipay.hipayfullservice.core.requests.payment.CardTokenPaymentMethodRequest;
import com.hipay.hipayfullservice.screen.activity.PaymentProductsActivity;

import java.util.Arrays;
import java.util.Calendar;

public class HPFActivity extends AppCompatActivity {

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
                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                //.setAction("Action", null).show();
                PaymentProductsActivity.start(HPFActivity.this);
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

        return;

        /*
        new SecureVaultClient(this).createTokenRequest(
                "4111111111111111",
                "12",
                "2019",
                "John Doe",
                "123",
                false,

                new SecureVaultRequestCallback() {
                    @Override
                    public void onSuccess(PaymentCardToken paymentCardToken) {

                        Log.i(paymentCardToken.toString(), paymentCardToken.toString());

                        PaymentPageRequest paymentPageRequest = HPFActivity.this.hardPageRequest();

                        OrderRequest orderRequest = HPFActivity.this.hardOrderRequest(paymentPageRequest);
                        orderRequest.setPaymentProductCode("visa");

                        CardTokenPaymentMethodRequest cardTokenPaymentMethodRequest = new CardTokenPaymentMethodRequest(paymentCardToken.getToken(), paymentPageRequest.getEci(), paymentPageRequest.getAuthenticationIndicator());
                        orderRequest.setPaymentMethod(cardTokenPaymentMethodRequest);

                        new GatewayClient(HPFActivity.this)
                                .createOrderRequest(orderRequest, new OrderRequestCallback() {

                                    @Override
                                    public void onSuccess(Transaction transaction) {
                                        Log.i("transaction success", transaction.toString());
                                    }

                                    @Override
                                    public void onError(Exception error) {
                                        Log.i("transaction failed", error.getLocalizedMessage());
                                    }
                                });

                    }

                    @Override
                    public void onError(Exception error) {

                    }
                }
        );
        */

        //activity.finish();

        //return;

    }

    protected OrderRequest hardOrderRequest(PaymentPageRequest paymentPageRequest) {

        OrderRequest orderRequest = new OrderRequest(paymentPageRequest);

        //TODO
        orderRequest.setPaymentProductCode("bcmc-mobile");

        return orderRequest;
    }

    protected PaymentPageRequest hardPageRequest() {

        PaymentPageRequest paymentPageRequest = new PaymentPageRequest();

        paymentPageRequest.setAmount(225);
        paymentPageRequest.setCurrency("EUR");

        StringBuilder stringBuilder = new StringBuilder("TEST_SDK_IOS_").append(Calendar.getInstance().getTimeInMillis()/1000);

        paymentPageRequest.setOrderId(stringBuilder.toString());
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

        return paymentPageRequest;
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
}
