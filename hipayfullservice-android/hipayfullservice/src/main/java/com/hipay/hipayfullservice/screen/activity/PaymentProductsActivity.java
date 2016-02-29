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
import android.view.View;
import android.widget.TextView;

import com.hipay.hipayfullservice.R;
import com.hipay.hipayfullservice.screen.fragment.PaymentProductsFragment;

/**
 * Created by nfillion on 25/02/16.
 */
public class PaymentProductsActivity extends AppCompatActivity {

    //TODO handle extra user
    private static final String EXTRA_USER = "user";

    public static void start(Activity activity, ActivityOptionsCompat options) {
        Intent starter = getStartIntent(activity);
        ActivityCompat.startActivity(activity, starter, options.toBundle());
    }

    public static void start(Context context) {
        Intent starter = getStartIntent(context);
        context.startActivity(starter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //no-op
    }

    @NonNull
    static Intent getStartIntent(Context context) {
        Intent starter = new Intent(context, PaymentProductsActivity.class);
        //starter.putExtra(EXTRA_USER, player);
        return starter;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_payment_products);

        setUpToolbar();
        if (savedInstanceState == null) {

            attachCategoryGridFragment();

        } else {

            setProgressBarVisibility(View.GONE);
            //the fragment is loaded already, then remove the loading
        }

        //TODO useful if this activity is called with makeTransition
        supportPostponeEnterTransition();
    }

    private void setUpToolbar() {

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_payment_products);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowTitleEnabled(false);

        //noinspection PrivateResource
        ((TextView) toolbar.findViewById(R.id.title)).setText(R.string.paiment_products_title);
    }

    private void attachCategoryGridFragment() {
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        Fragment fragment = supportFragmentManager.findFragmentById(R.id.category_container);
        if (!(fragment instanceof PaymentProductsFragment)) {
            fragment = PaymentProductsFragment.newInstance();
        }
        supportFragmentManager.beginTransaction()
                .replace(R.id.category_container, fragment)
                .commit();
        setProgressBarVisibility(View.GONE);
    }

    private void setProgressBarVisibility(int visibility) {
        findViewById(R.id.progress).setVisibility(visibility);
    }

    @Override
    protected void onStop() {
        super.onStop();
        //no-op
    }
}

