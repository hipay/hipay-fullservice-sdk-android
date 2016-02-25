package com.hipay.hipayfullservice.example.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentActivity;
import android.view.View;

import com.hipay.hipayfullservice.example.R;
import com.hipay.hipayfullservice.example.fragment.PaymentScreenFragment;

/**
 * Created by nfillion on 25/02/16.
 */
public class PaymentScreenActivity extends FragmentActivity {

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

    @NonNull
    static Intent getStartIntent(Context context) {
        Intent starter = new Intent(context, PaymentScreenActivity.class);
        //starter.putExtra(EXTRA_USER, player);
        return starter;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_screen);
        //final boolean edit = isInEditMode();
        if (savedInstanceState == null) {

            attachCategoryGridFragment();
            // getFragmentManager().beginTransaction()
                    //.replace(R.id.sign_in_container, SignInFragment.newInstance(edit)).commit();
        } else {

            // setProgressBarVisibility(View.GONE)
            // the fragment is loaded already, then remove the loading
        }

        //TODO useful if this activity is called with makeTransition
        supportPostponeEnterTransition();
        //postponeEnterTransition();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //no-op
    }

    private void attachCategoryGridFragment() {
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        Fragment fragment = supportFragmentManager.findFragmentById(R.id.category_container);
        if (!(fragment instanceof PaymentScreenFragment)) {
            fragment = PaymentScreenFragment.newInstance();
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

