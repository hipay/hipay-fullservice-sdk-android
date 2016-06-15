package com.hipay.hipayfullservice.example;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.hipay.hipayfullservice.core.client.config.ClientConfig;
import com.hipay.hipayfullservice.core.models.PaymentProduct;
import com.hipay.hipayfullservice.example.fragment.DemoFragment;
import com.hipay.hipayfullservice.example.fragment.ProductCategoryListFragment;
import com.hipay.hipayfullservice.screen.model.CustomTheme;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DemoActivity extends AppCompatActivity implements ProductCategoryListFragment.OnPaymentProductSelectedListener {

    private Map<String,Boolean> mPaymentProducts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_demo);

        //easier to customize a toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ClientConfig.Environment environment = ClientConfig.Environment.Stage;

        String username = "94656047.stage-secure-gateway.hipay-tpp.com";
        String password = "Test_pfS0ojds5ZPu1t8ow8PuASCD";

        ClientConfig.getInstance().setConfigEnvironment(
                environment,
                username,
                password
        );

        mPaymentProducts = new HashMap<>(4);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.demo_container, DemoFragment.newInstance()).commit();

            mPaymentProducts.put(PaymentProduct.PaymentProductCategoryCodeCreditCard, Boolean.TRUE);
            mPaymentProducts.put(PaymentProduct.PaymentProductCategoryCodeDebitCard, Boolean.TRUE);
            mPaymentProducts.put(PaymentProduct.PaymentProductCategoryCodeEWallet, Boolean.TRUE);
            mPaymentProducts.put(PaymentProduct.PaymentProductCategoryCodeRealtimeBanking, Boolean.TRUE);

        } else {

            mPaymentProducts.put(PaymentProduct.PaymentProductCategoryCodeCreditCard,savedInstanceState.getBoolean(PaymentProduct.PaymentProductCategoryCodeCreditCard));
            mPaymentProducts.put(PaymentProduct.PaymentProductCategoryCodeDebitCard,savedInstanceState.getBoolean(PaymentProduct.PaymentProductCategoryCodeDebitCard));
            mPaymentProducts.put(PaymentProduct.PaymentProductCategoryCodeEWallet, savedInstanceState.getBoolean(PaymentProduct.PaymentProductCategoryCodeEWallet));
            mPaymentProducts.put(PaymentProduct.PaymentProductCategoryCodeRealtimeBanking, savedInstanceState.getBoolean(PaymentProduct.PaymentProductCategoryCodeRealtimeBanking));
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.demo_container);
        if (fragment != null) {
            fragment.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putBoolean(PaymentProduct.PaymentProductCategoryCodeCreditCard, mPaymentProducts.get(PaymentProduct.PaymentProductCategoryCodeCreditCard));
        outState.putBoolean(PaymentProduct.PaymentProductCategoryCodeDebitCard, mPaymentProducts.get(PaymentProduct.PaymentProductCategoryCodeDebitCard));
        outState.putBoolean(PaymentProduct.PaymentProductCategoryCodeEWallet, mPaymentProducts.get(PaymentProduct.PaymentProductCategoryCodeEWallet));
        outState.putBoolean(PaymentProduct.PaymentProductCategoryCodeRealtimeBanking, mPaymentProducts.get(PaymentProduct.PaymentProductCategoryCodeRealtimeBanking));
    }

    public void onClickPaymentProducts(View view) {

            Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.demo_container);
            if (fragment != null && fragment instanceof DemoFragment) {

                CustomTheme customTheme = ((DemoFragment) fragment).getCustomTheme();

                getSupportFragmentManager()
                        .beginTransaction()
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        //.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                        //.setCustomAnimations(android.R.anim.in_from_left, android.R.anim.out_to_right, android.R.anim.in_from_right, android.R.anim.out_to_left)

                        .replace(R.id.demo_container, ProductCategoryListFragment.newInstance(mPaymentProducts, customTheme))
                        //.add(R.id.demo_container, ProductCategoryListFragment.newInstance(mPaymentProducts, this))
                        .addToBackStack(null)
                        .commit();
            }
    }

    @Override
    public void onPaymentProductSelected(int position, boolean isChecked) {

        switch (position) {

            case 0:
                mPaymentProducts.put(PaymentProduct.PaymentProductCategoryCodeCreditCard, isChecked);
            break;

            case 1:
                mPaymentProducts.put(PaymentProduct.PaymentProductCategoryCodeDebitCard, isChecked);
            break;

            case 2:
                mPaymentProducts.put(PaymentProduct.PaymentProductCategoryCodeEWallet, isChecked);
            break;

            case 3:
                mPaymentProducts.put(PaymentProduct.PaymentProductCategoryCodeRealtimeBanking, isChecked);
            break;

        }
    }

    public ArrayList<String> getPaymentProductsAsList() {

        ArrayList<String> list = new ArrayList<>();
        for (Map.Entry<String, Boolean> entry : mPaymentProducts.entrySet())
        {

            if (entry.getValue().equals(Boolean.TRUE)) {
                list.add(entry.getKey());
            }
        }

        return list;
    }
}
