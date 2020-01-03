package com.hipay.fullservice.example;

import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.text.TextUtils;

import com.hipay.fullservice.core.client.config.ClientConfig;
import com.hipay.fullservice.core.models.PaymentProduct;
import com.hipay.fullservice.example.fragment.DemoFragment;
import com.hipay.fullservice.example.fragment.ProductCategoryListFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DemoActivity extends AppCompatActivity implements ProductCategoryListFragment.OnPaymentProductSelectedListener {

    private Map<String,Boolean> paymentProducts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_demo);

        //easier to customize a toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(getString(R.string.mcommence_demo));
        setSupportActionBar(toolbar);

        ClientConfig.Environment environment = ClientConfig.Environment.Stage;
        String username = null;
        String password = null;

        if (Preferences.isStageEnvironment(getBaseContext())) {
            username = getString(R.string.username_stage);
            password = getString(R.string.password_stage);
            environment = ClientConfig.Environment.Stage;
        }
        else if (Preferences.isProductionEnvironment(getBaseContext())) {
            username = getString(R.string.username_production);
            password = getString(R.string.password_production);
            environment = ClientConfig.Environment.Production;
        }
        else if (Preferences.isCustomEnvironment(getBaseContext())) {
            username = Preferences.getCustomUsername(getBaseContext());
            password = Preferences.getCustomPassword(getBaseContext());
            environment = Preferences.isProductionUrl(getBaseContext()) ? ClientConfig.Environment.Production : ClientConfig.Environment.Stage;
        }

        if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
            throw new IllegalArgumentException(
                    getString(R.string.config_parameters_error)
            );
        }

        ClientConfig.getInstance().setConfig(
                environment,
                username,
                password
        );

        //options by default: true
        ClientConfig.getInstance().setPaymentCardNfcScanEnabled(true);
        ClientConfig.getInstance().setPaymentCardScanEnabled(true);
        ClientConfig.getInstance().setPaymentCardNfcScanEnabled(true);

        paymentProducts = new HashMap<>(4);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.demo_container, DemoFragment.newInstance()).commit();

            paymentProducts.put(PaymentProduct.PaymentProductCategoryCodeCreditCard, Boolean.TRUE);
            paymentProducts.put(PaymentProduct.PaymentProductCategoryCodeDebitCard, Boolean.TRUE);
            paymentProducts.put(PaymentProduct.PaymentProductCategoryCodeEWallet, Boolean.TRUE);
            paymentProducts.put(PaymentProduct.PaymentProductCategoryCodeRealtimeBanking, Boolean.TRUE);

        } else {

            paymentProducts.put(PaymentProduct.PaymentProductCategoryCodeCreditCard,savedInstanceState.getBoolean(PaymentProduct.PaymentProductCategoryCodeCreditCard));
            paymentProducts.put(PaymentProduct.PaymentProductCategoryCodeDebitCard,savedInstanceState.getBoolean(PaymentProduct.PaymentProductCategoryCodeDebitCard));
            paymentProducts.put(PaymentProduct.PaymentProductCategoryCodeEWallet, savedInstanceState.getBoolean(PaymentProduct.PaymentProductCategoryCodeEWallet));
            paymentProducts.put(PaymentProduct.PaymentProductCategoryCodeRealtimeBanking, savedInstanceState.getBoolean(PaymentProduct.PaymentProductCategoryCodeRealtimeBanking));
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.demo_container);
        if (fragment != null) {
            fragment.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putBoolean(PaymentProduct.PaymentProductCategoryCodeCreditCard, paymentProducts.get(PaymentProduct.PaymentProductCategoryCodeCreditCard));
        outState.putBoolean(PaymentProduct.PaymentProductCategoryCodeDebitCard, paymentProducts.get(PaymentProduct.PaymentProductCategoryCodeDebitCard));
        outState.putBoolean(PaymentProduct.PaymentProductCategoryCodeEWallet, paymentProducts.get(PaymentProduct.PaymentProductCategoryCodeEWallet));
        outState.putBoolean(PaymentProduct.PaymentProductCategoryCodeRealtimeBanking, paymentProducts.get(PaymentProduct.PaymentProductCategoryCodeRealtimeBanking));
    }

    @Override
    public void onPaymentProductSelected(int position, boolean isChecked) {

        switch (position) {

            case 0:
                paymentProducts.put(PaymentProduct.PaymentProductCategoryCodeCreditCard, isChecked);
                break;

            case 1:
                paymentProducts.put(PaymentProduct.PaymentProductCategoryCodeDebitCard, isChecked);
                break;

            case 2:
                paymentProducts.put(PaymentProduct.PaymentProductCategoryCodeEWallet, isChecked);
                break;

            case 3:
                paymentProducts.put(PaymentProduct.PaymentProductCategoryCodeRealtimeBanking, isChecked);
                break;

        }
    }

    public ArrayList<String> getPaymentProductsAsList() {

        ArrayList<String> list = new ArrayList<>();
        for (Map.Entry<String, Boolean> entry : paymentProducts.entrySet())
        {

            if (entry.getValue().equals(Boolean.TRUE)) {
                list.add(entry.getKey());
            }
        }

        return list;
    }

    public Map<String, Boolean> getPaymentProducts() {
        return paymentProducts;
    }

}
