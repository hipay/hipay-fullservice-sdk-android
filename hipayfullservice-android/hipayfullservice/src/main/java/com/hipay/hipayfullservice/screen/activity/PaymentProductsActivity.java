package com.hipay.hipayfullservice.screen.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.hipay.hipayfullservice.R;
import com.hipay.hipayfullservice.core.client.GatewayClient;
import com.hipay.hipayfullservice.core.client.SecureVaultClient;
import com.hipay.hipayfullservice.core.client.interfaces.callbacks.OrderRequestCallback;
import com.hipay.hipayfullservice.core.client.interfaces.callbacks.SecureVaultRequestCallback;
import com.hipay.hipayfullservice.core.models.PaymentCardToken;
import com.hipay.hipayfullservice.core.models.PaymentProduct;
import com.hipay.hipayfullservice.core.models.Transaction;
import com.hipay.hipayfullservice.core.requests.order.OrderRequest;
import com.hipay.hipayfullservice.core.requests.order.PaymentPageRequest;
import com.hipay.hipayfullservice.core.requests.payment.CardTokenPaymentMethodRequest;
import com.hipay.hipayfullservice.screen.fragment.PaymentProductsFragment;

import java.util.Arrays;
import java.util.Calendar;

/**
 * Created by nfillion on 25/02/16.
 */
public class PaymentProductsActivity extends AppCompatActivity {

    //TODO handle extra user

    public static void start(Activity activity, PaymentPageRequest paymentPageRequest, ActivityOptionsCompat options) {
        Intent starter = getStartIntent(activity, paymentPageRequest);
        ActivityCompat.startActivityForResult(activity, starter, PaymentPageRequest.REQUEST_ORDER, options.toBundle());
    }

    @Override
    protected void onResume() {
        super.onResume();
        //no-op
    }

    @Override
    protected void onStop() {
        super.onStop();
        //no-op
    }

    @NonNull
    static Intent getStartIntent(Context context, PaymentPageRequest paymentPageRequest) {

        Intent starter = new Intent(context, PaymentProductsActivity.class);

        Bundle bundle = paymentPageRequest.toBundle();
        starter.putExtra(PaymentPageRequest.TAG, bundle);

        return starter;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_payment_products);

        //goRequest();

        setUpToolbar();
        if (savedInstanceState == null) {

            attachProductGridFragment();

        } else {

            setProgressBarVisibility(View.GONE);
            //the fragment is loaded already, then remove the loading
        }

        //TODO useful when this activity is gonna be called with makeTransition

        supportPostponeEnterTransition();
    }

    private void goRequest() {

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

                        //final PaymentPageRequest paymentPageRequest = PaymentProductsActivity.this.hardPageRequest();


                        //Bundle bundle = getIntent().getExtras(EXTRA_USER);
                        Bundle bundle = getIntent().getBundleExtra(PaymentPageRequest.TAG);

                        PaymentPageRequest paymentPageRequest = PaymentPageRequest.fromBundle(bundle);
                        OrderRequest orderRequest = PaymentProductsActivity.this.hardOrderRequest(paymentPageRequest);
                        orderRequest.setPaymentProductCode("visa");

                        CardTokenPaymentMethodRequest cardTokenPaymentMethodRequest = new CardTokenPaymentMethodRequest(paymentCardToken.getToken(), paymentPageRequest.getEci(), paymentPageRequest.getAuthenticationIndicator());
                        orderRequest.setPaymentMethod(cardTokenPaymentMethodRequest);

                        new GatewayClient(PaymentProductsActivity.this)
                                .createOrderRequest(orderRequest, new OrderRequestCallback() {

                                    @Override
                                    public void onSuccess(Transaction transaction) {
                                        Log.i("transaction success", transaction.toString());

                                        //Bundle bundle = paymentPageRequest.toBundle();

                                        setResultSucceed(null);
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
    }

    private void setResultSucceed(Bundle bundle) {

        Intent intent = new Intent();

        //intent.putExtra("hello", bundle);

        setResult(R.id.transaction_failed, intent);
        ActivityCompat.finishAfterTransition(this);
    }

    protected OrderRequest hardOrderRequest(PaymentPageRequest paymentPageRequest) {

        OrderRequest orderRequest = new OrderRequest(paymentPageRequest);

        //TODO
        orderRequest.setPaymentProductCode("bcmc-mobile");

        return orderRequest;
    }

    protected PaymentPageRequest hardPageRequest() {

        PaymentPageRequest paymentPageRequest = new PaymentPageRequest();

        paymentPageRequest.setAmount(225.f);
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

    private void setUpToolbar() {

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_payment_products);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowTitleEnabled(false);

        //TODO handle the price (montant)

        //noinspection PrivateResource
        ((TextView) toolbar.findViewById(R.id.title)).setText(R.string.paiment_products_title);
    }

    private void attachProductGridFragment() {

        FragmentManager supportFragmentManager = getSupportFragmentManager();
        Fragment fragment = supportFragmentManager.findFragmentById(R.id.payment_products_container);
        if (!(fragment instanceof PaymentProductsFragment)) {

            Bundle paymentPageRequestBundle = getIntent().getBundleExtra(PaymentPageRequest.TAG);
            fragment = PaymentProductsFragment.newInstance(paymentPageRequestBundle);

            //fragment.setArguments(paymentPageRequestBundle);
        }

        supportFragmentManager.beginTransaction()
                .replace(R.id.payment_products_container, fragment)
                .commit();

        setProgressBarVisibility(View.GONE);
    }

    private void setProgressBarVisibility(int visibility) {
        findViewById(R.id.progress).setVisibility(visibility);
    }

}

