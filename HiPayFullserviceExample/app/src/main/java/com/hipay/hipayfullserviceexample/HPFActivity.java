package com.hipay.hipayfullserviceexample;

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

import com.hipay.hipayfullservice.core.network.HttpClient;
import com.hipay.hipayfullservice.core.network.HttpResponse;

import java.io.IOException;
import java.util.Locale;

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

        getLoaderManager().initLoader(0, null, this);


        String locale1 = Locale.getDefault().getDisplayLanguage();
        String locale2 = Locale.getDefault().toString();
        String locale3 = Locale.getDefault().getLanguage();

        String hello;

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
        return new HttpClient(this, null);
    }

    @Override
    public void onLoadFinished(Loader<HttpResponse> loader, HttpResponse data) {

        Log.i("data", "data : " + data);
        Log.i("data", "data : " + data);
        //User user = new Gson().fromJson(data, User.class);

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
