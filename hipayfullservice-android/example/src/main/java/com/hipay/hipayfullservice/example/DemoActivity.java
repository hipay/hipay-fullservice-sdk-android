package com.hipay.hipayfullservice.example;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.hipay.hipayfullservice.core.client.config.ClientConfig;
import com.hipay.hipayfullservice.example.fragment.DemoFragment;

public class DemoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ClientConfig.Environment environment = ClientConfig.Environment.Stage;
        //String username = "94654679.api.hipay-tpp.com";
        //String password = "U5hQh4ZT0UyXpYhG8WI4trYQ";

        String username = "94656047.stage-secure-gateway.hipay-tpp.com";
        String password = "Test_pfS0ojds5ZPu1t8ow8PuASCD";

        ClientConfig.getInstance().setConfigEnvironment(
                environment,
                username,
                password
        );

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.demo_container, DemoFragment.newInstance()).commit();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.demo_container);
        if (fragment != null) {
            fragment.onActivityResult(requestCode, resultCode, data);
        }
    }
}
