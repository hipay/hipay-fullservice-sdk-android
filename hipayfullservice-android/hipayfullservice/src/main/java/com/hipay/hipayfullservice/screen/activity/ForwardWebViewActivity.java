package com.hipay.hipayfullservice.screen.activity;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.content.ContextCompat;
import android.view.Window;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.hipay.hipayfullservice.R;
import com.hipay.hipayfullservice.core.models.Order;
import com.hipay.hipayfullservice.core.models.Transaction;
import com.hipay.hipayfullservice.core.requests.order.OrderRelatedRequest;
import com.hipay.hipayfullservice.core.requests.order.PaymentPageRequest;
import com.hipay.hipayfullservice.screen.helper.ApiLevelHelper;
import com.hipay.hipayfullservice.screen.model.CustomTheme;

import java.security.InvalidParameterException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by nfillion on 14/04/16.
 */
public class ForwardWebViewActivity extends Activity {

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

    //@Override
    //public void onActivityResult(int requestCode, int resultCode, Intent data) {

        //if (requestCode == PaymentPageRequest.REQUEST_ORDER) {

            //if (resultCode == R.id.transaction_succeed) {

                //setResult(R.id.transaction_succeed, data);

            //} else if (resultCode == R.id.transaction_failed) {

                //setResult(R.id.transaction_failed, data);
            //}

            //finish();
        //}

        //super.onActivityResult(requestCode, resultCode, data);
    //}

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        transactionFromCallbackIntent(intent.getData());
    }

    private void transactionFromCallbackIntent(Uri data) {

        //Uri data = intent.getData();
        //Set set = data.getQueryParameterNames();

        //List<String> l4 = data.getQueryParameters("cardtoken");

        //String l2 = data.getQueryParameter("eci3d");
        //String l3 = data.getQueryParameter("cardtoken");

        Transaction transaction = null;

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

                    transaction = new Transaction();
                    Order order = new Order();
                    order.setOrderId(pathSegments.get(2));
                    transaction.setOrder(order);
                    transaction.setState(transactionStatus.get(pathSegments.get(3)));
                    //orderId a setter transaction.setOrder pathSegments.get(3)
                }
            }
        }

        if (transaction != null) {

            Intent transactionIntent = getIntent();
            transactionIntent.putExtra(Transaction.TAG, transaction.toBundle());
            setResult(R.id.transaction_succeed, transactionIntent);
            finish();

        } else {

            //fail
            finish();
        }
    }

    @Override
    @SuppressLint("SetJavaScriptEnabled")
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_forward_webview);

        if (savedInstanceState == null) {

            WebView myWebView = (WebView) findViewById(R.id.webview);
            myWebView.getSettings().setJavaScriptEnabled(true);
            myWebView.setWebViewClient(new MyWebViewClient());
            myWebView.setWebChromeClient(new WebChromeClient());

            String stringUrl = getIntent().getStringExtra("forwardUrl");

            Bundle customTheme = getIntent().getBundleExtra(CustomTheme.TAG);
            if (customTheme != null) {
                this.initStatusBar(CustomTheme.fromBundle(customTheme));
            }
            myWebView.loadUrl(stringUrl);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    private class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {

            Uri data = Uri.parse(url);

            if (
                    data.getHost().equals(getString(R.string.hipay_host)) &&
                    data.getScheme().equals(getString(R.string.hipay_scheme))) {

                transactionFromCallbackIntent(data);
                return true;
            }

            //let the webview load the page
            return false;
        }

        @TargetApi(Build.VERSION_CODES.KITKAT)
        @Override
        public void onReceivedSslError(WebView view, final SslErrorHandler handler, SslError error) {
            //super.onReceivedSslError(view, handler, error);

            final AlertDialog.Builder builder = new AlertDialog.Builder(ForwardWebViewActivity.this);
            builder.setTitle(getString(R.string.certificate_error_title));

            StringBuilder messageBuilder = new StringBuilder();

            switch (error.getPrimaryError())
            {
                case SslError.SSL_UNTRUSTED:
                    messageBuilder.append(getString(R.string.certificate_error_untrusted)); break;
                case SslError.SSL_EXPIRED:
                    messageBuilder.append(getString(R.string.certificate_error_expired)); break;
                case SslError.SSL_IDMISMATCH:
                    messageBuilder.append(getString(R.string.certificate_error_mismatched)); break;
                case SslError.SSL_NOTYETVALID:
                    messageBuilder.append(getString(R.string.certificate_error_not_yet_valid)); break;
                default:
                    messageBuilder.append(getString(R.string.certificate_error)); break;
            }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                messageBuilder.append(System.lineSeparator());

            } else {
                messageBuilder.append(System.getProperty("line.separator"));
            }

            messageBuilder.append(getString(R.string.certificate_error_continue));
            builder.setMessage(messageBuilder.toString());

            builder.setPositiveButton(getString(R.string.prompt_continue), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    handler.proceed();
                }
            });
            builder.setNegativeButton(getString(R.string.promt_cancel), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    handler.cancel();
                    finish();
                }
            });
            final AlertDialog dialog = builder.create();
            dialog.show();
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
