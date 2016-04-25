package com.hipay.hipayfullservice.screen.activity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.content.ContextCompat;
import android.util.ArrayMap;
import android.view.Window;
import android.webkit.WebView;

import com.hipay.hipayfullservice.R;
import com.hipay.hipayfullservice.core.models.HostedPaymentPage;
import com.hipay.hipayfullservice.core.models.Transaction;
import com.hipay.hipayfullservice.core.requests.order.OrderRelatedRequest;
import com.hipay.hipayfullservice.core.requests.order.PaymentPageRequest;
import com.hipay.hipayfullservice.screen.helper.ApiLevelHelper;
import com.hipay.hipayfullservice.screen.model.CustomTheme;

import java.net.URL;
import java.security.InvalidParameterException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by nfillion on 14/04/16.
 */
public class ForwardWebViewActivity extends Activity {

    public static void start(Activity activity, HostedPaymentPage hostedPaymentPage, Bundle theme) {
        Intent starter = getStartIntent(activity, hostedPaymentPage, theme);

        ActivityOptionsCompat activityOptions = ActivityOptionsCompat
                .makeSceneTransitionAnimation(activity, null);

        ActivityCompat.startActivityForResult(activity, starter, PaymentPageRequest.REQUEST_ORDER, activityOptions.toBundle());
    }

    public static void start(Activity activity, String forwardURLString, Bundle theme) {
        Intent starter = getStartIntent(activity, forwardURLString, theme);

        ActivityOptionsCompat activityOptions = ActivityOptionsCompat
                .makeSceneTransitionAnimation(activity, null);

        ActivityCompat.startActivityForResult(activity, starter, PaymentPageRequest.REQUEST_ORDER, activityOptions.toBundle());
    }

    public static Intent getStartIntent(Context context, String forwardURLString, Bundle theme) {

        if (forwardURLString != null) {

            Intent starter = new Intent(context, ForwardWebViewActivity.class);
            starter.putExtra("forwardUrl", forwardURLString);
            starter.putExtra(CustomTheme.TAG, theme);
            return starter;

        } else {

            throw new InvalidParameterException();
        }
    }

    public static Intent getStartIntent(Context context, HostedPaymentPage hostedPaymentPage, Bundle theme) {

        if (hostedPaymentPage != null) {

            URL forwardUrl = hostedPaymentPage.getForwardUrl();
            Intent starter = new Intent(context, ForwardWebViewActivity.class);
            starter.putExtra("forwardUrl", forwardUrl.toString());
            starter.putExtra(CustomTheme.TAG, theme);
            return starter;

        } else {

            throw new InvalidParameterException();
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        //setIntent(intent);

                //private static final String OrderRelatedRequestRedirectPathAccept = "accept";
                //private static final String OrderRelatedRequestRedirectPathDecline = "decline";
                //private static final String OrderRelatedRequestRedirectPathPending = "pending";
                //private static final String OrderRelatedRequestRedirectPathException = "exception";
                //private static final String OrderRelatedRequestRedirectPathCancel = "cancel";

        Transaction transaction = transactionFromCallbackIntent(intent);
        if (transaction != null) {

            Intent transactionIntent = getIntent();
            transactionIntent.putExtra(Transaction.TAG, transaction.toBundle());
            setResult(R.id.transaction_succeed, transactionIntent);
            finish();

        } else {

            //TODO handle the callback error
        }
    }

    private Transaction transactionFromCallbackIntent(Intent intent) {

        Uri data = intent.getData();

        //TODO will be useful for
        //Set set = data.getQueryParameterNames();

        //List<String> l4 = data.getQueryParameters("cardtoken");

        //String l2 = data.getQueryParameter("eci3d");
        //String l3 = data.getQueryParameter("cardtoken");

        if (data != null) {

            List<String> pathSegments = data.getPathSegments();

            Map<String, Transaction.TransactionState> transactionStatus = new HashMap<>(5);

            transactionStatus.put(OrderRelatedRequest.OrderRelatedRequestRedirectPathAccept,Transaction.TransactionState.TransactionStateCompleted);
            transactionStatus.put(OrderRelatedRequest.OrderRelatedRequestRedirectPathCancel,Transaction.TransactionState.TransactionStateDeclined);
            transactionStatus.put(OrderRelatedRequest.OrderRelatedRequestRedirectPathDecline,Transaction.TransactionState.TransactionStateDeclined);
            transactionStatus.put(OrderRelatedRequest.OrderRelatedRequestRedirectPathException,Transaction.TransactionState.TransactionStateError);
            transactionStatus.put(OrderRelatedRequest.OrderRelatedRequestRedirectPathPending,Transaction.TransactionState.TransactionStatePending);


            //List<String> statusList = Arrays.asList(

                    //OrderRelatedRequest.OrderRelatedRequestRedirectPathAccept,
                    //OrderRelatedRequest.OrderRelatedRequestRedirectPathCancel,
                    //OrderRelatedRequest.OrderRelatedRequestRedirectPathDecline,
                    //OrderRelatedRequest.OrderRelatedRequestRedirectPathException,
                    //OrderRelatedRequest.OrderRelatedRequestRedirectPathPending
            //);

            if (!pathSegments.isEmpty() && pathSegments.size() == 4) {

                if (
                        pathSegments.get(0).equalsIgnoreCase(OrderRelatedRequest.GatewayCallbackURLPathName) &&
                        pathSegments.get(1).equalsIgnoreCase(OrderRelatedRequest.GatewayCallbackURLOrderPathName) &&

                                transactionStatus.containsKey(pathSegments.get(3))
                        )
                {


                    Transaction transaction = new Transaction();
                    transaction.setState( transactionStatus.get(pathSegments.get(3)));
                    //orderId a setter transaction.setOrder pathSegments.get(3)
                    return transaction;
                }
            }
        }

        return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_forward_webview);
        if (savedInstanceState == null) {

            WebView myWebView = (WebView) findViewById(R.id.webview);
            myWebView.getSettings().setJavaScriptEnabled(true);

            //if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                //WebView.setWebContentsDebuggingEnabled(true);
            //}
            String stringUrl = getIntent().getStringExtra("forwardUrl");

            //TODO handle better the savedInstance state
            Bundle customTheme = getIntent().getBundleExtra(CustomTheme.TAG);
            if (customTheme != null) {
                this.initStatusBar(CustomTheme.fromBundle(customTheme));
            }

            myWebView.loadUrl(stringUrl);
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void initStatusBar(CustomTheme theme) {

        if (ApiLevelHelper.isAtLeast(Build.VERSION_CODES.LOLLIPOP)) {

            Window window = getWindow();
            window.setStatusBarColor(ContextCompat.getColor(this,
                    theme.getColorPrimaryDarkId()));
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }
}
