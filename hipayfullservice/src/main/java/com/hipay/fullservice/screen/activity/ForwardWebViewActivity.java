package com.hipay.fullservice.screen.activity;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.hipay.fullservice.R;
import com.hipay.fullservice.core.logging.Logger;
import com.hipay.fullservice.core.models.Order;
import com.hipay.fullservice.core.models.Transaction;
import com.hipay.fullservice.core.requests.order.OrderRelatedRequest;
import com.hipay.fullservice.core.requests.order.PaymentPageRequest;
import com.hipay.fullservice.screen.helper.ApiLevelHelper;
import com.hipay.fullservice.screen.model.CustomTheme;

import java.security.InvalidParameterException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by HiPay on 14/04/16.
 */
public class ForwardWebViewActivity extends AppCompatActivity {

    public static void start(Activity activity, String forwardURLString, String title, Bundle theme) {
        Intent starter = getStartIntent(activity, forwardURLString, title, theme);

        ActivityOptionsCompat activityOptions = ActivityOptionsCompat
                .makeSceneTransitionAnimation(activity);

        ActivityCompat.startActivityForResult(activity, starter, PaymentPageRequest.REQUEST_ORDER, activityOptions.toBundle());
    }

    public static Intent getStartIntent(Context context, String forwardURLString, String title, Bundle theme) {

        if (forwardURLString != null) {

            Intent starter = new Intent(context, ForwardWebViewActivity.class);
            starter.putExtra("forwardUrl", forwardURLString);
            starter.putExtra("title", title);
            starter.putExtra(CustomTheme.TAG, theme);
            return starter;

        } else {

            throw new InvalidParameterException();
        }
    }


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        transactionFromCallbackIntent(intent.getData());
    }

    private void transactionFromCallbackIntent(Uri data) {

        Transaction transaction = null;

        if (data != null) {

            List<String> pathSegments = data.getPathSegments();

            Map<String, Transaction.TransactionState> transactionStatus = new HashMap<>(5);

            transactionStatus.put(OrderRelatedRequest.OrderRelatedRequestRedirectPathAccept,Transaction.TransactionState.TransactionStateCompleted);
            transactionStatus.put(OrderRelatedRequest.OrderRelatedRequestRedirectPathCancel,Transaction.TransactionState.TransactionStateDeclined);
            transactionStatus.put(OrderRelatedRequest.OrderRelatedRequestRedirectPathDecline,Transaction.TransactionState.TransactionStateDeclined);
            transactionStatus.put(OrderRelatedRequest.OrderRelatedRequestRedirectPathException,Transaction.TransactionState.TransactionStateError);
            transactionStatus.put(OrderRelatedRequest.OrderRelatedRequestRedirectPathPending,Transaction.TransactionState.TransactionStatePending);

            if (!pathSegments.isEmpty() && pathSegments.size() == 4) {

                if (
                        pathSegments.get(0).equalsIgnoreCase(OrderRelatedRequest.GatewayCallbackURLPathName) &&
                                pathSegments.get(1).equalsIgnoreCase(OrderRelatedRequest.GatewayCallbackURLOrderPathName) &&

                                transactionStatus.containsKey(pathSegments.get(3))
                        )
                {

                    transaction = Transaction.fromUri(data);

                    if (transaction.getTransactionReference() == null ) {

                        Logger.d("<Forward>: The option \"Feedback Parameters\" is disabled on your HiPay Fullservice back office. It means that when a transaction finishes, the SDK is unable to receive all the transaction parameters, such as fraud screening and 3DS results, etc. This option is not mandatory in order for the SDK to run properly. However, if you wish to receive transactions with a comprehensive set of proporties filled in your transaction callback methods, go in your HiPay Fullservice back office > Integration > Redirect Pages and enable the  \"Feedback Parameters\" option.");

                        transaction = new Transaction();
                        Order order = new Order();

                        order.setOrderId(pathSegments.get(2));
                        transaction.setOrder(order);
                        transaction.setState(transactionStatus.get(pathSegments.get(3)));
                    }
                }
            }
        }

        if (transaction != null) {

            Logger.d("<Gateway>: Handles valid URL");

            Intent transactionIntent = getIntent();
            transactionIntent.putExtra(Transaction.TAG, transaction.toBundle());
            setResult(R.id.transaction_succeed, transactionIntent);
            finish();

        } else
        {
            //fail, should not happen

            if (data != null)
            {
                if (data.getScheme().equals("bepgenapp"))
                {
                    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            finish();
                        }
                    };

                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle(R.string.alert_error_bcmc_app_not_found_title)
                            .setMessage(R.string.alert_error_bcmc_app_not_found_body)
                            .setNegativeButton(R.string.button_ok, dialogClickListener)
                            .setCancelable(false)
                            .show();

                } else
                {
                    finish();
                }

            } else
            {
                finish();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_webview, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_webview) {

            WebView myWebView = (WebView) findViewById(R.id.webview);
            myWebView.reload();

            return true;

        } else if (id == android.R.id.home) {

            WebView myWebView = (WebView) findViewById(R.id.webview);

            if (myWebView.canGoBack()) {
                myWebView.goBack();

            } else {
                onBackPressed();
            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    @SuppressLint("SetJavaScriptEnabled")
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_forward_webview);

        Bundle customTheme = getIntent().getBundleExtra(CustomTheme.TAG);
        if (customTheme != null) {
            this.initStatusBar(CustomTheme.fromBundle(customTheme));
        }

        if (savedInstanceState == null) {

            WebView myWebView = (WebView) findViewById(R.id.webview);
            myWebView.getSettings().setJavaScriptEnabled(true);
            myWebView.setWebViewClient(new MyWebViewClient());
            myWebView.setWebChromeClient(new WebChromeClient());

            String stringUrl = getIntent().getStringExtra("forwardUrl");
            myWebView.loadUrl(stringUrl);
        }
    }

    public static Drawable setTint(Drawable d, int color) {
        Drawable wrappedDrawable = DrawableCompat.wrap(d);
        DrawableCompat.setTint(wrappedDrawable, color);
        return wrappedDrawable;
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

            if (data.getScheme().equals("bepgenapp"))
            {
                Intent viewIntent = new Intent("android.intent.action.VIEW", data);
                startActivity(viewIntent);
                return true;
            }

            return false;

        }

        @TargetApi(Build.VERSION_CODES.KITKAT)
        @Override
        public void onReceivedSslError(WebView view, final SslErrorHandler handler, SslError error) {
            //super.onReceivedSslError(view, handler, error);

            final AlertDialog.Builder builder = new AlertDialog.Builder(ForwardWebViewActivity.this);
            builder.setTitle(getString(R.string.alert_certificate_error_title));

            StringBuilder messageBuilder = new StringBuilder();

            switch (error.getPrimaryError())
            {
                case SslError.SSL_UNTRUSTED:
                    messageBuilder.append(getString(R.string.alert_certificate_error_untrusted_message)); break;
                case SslError.SSL_EXPIRED:
                    messageBuilder.append(getString(R.string.alert_certificate_error_expired_message)); break;
                case SslError.SSL_IDMISMATCH:
                    messageBuilder.append(getString(R.string.alert_certificate_error_mismatched_message)); break;
                case SslError.SSL_NOTYETVALID:
                    messageBuilder.append(getString(R.string.alert_certificate_error_not_yet_valid_message)); break;
                default:
                    messageBuilder.append(getString(R.string.alert_certificate_error_message)); break;
            }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                messageBuilder.append(System.lineSeparator());

            } else {
                messageBuilder.append(System.getProperty("line.separator"));
            }

            messageBuilder.append(getString(R.string.alert_certificate_error_continue_message));
            builder.setMessage(messageBuilder.toString());

            builder.setPositiveButton(getString(R.string.button_continue), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    handler.proceed();
                }
            });
            builder.setNegativeButton(getString(R.string.button_cancel), new DialogInterface.OnClickListener() {
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

        Toolbar toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        toolbar.setTitleTextColor(ContextCompat.getColor(this, theme.getTextColorPrimaryId()));

        toolbar.setBackgroundColor(ContextCompat.getColor(this, theme.getColorPrimaryId()));

        Drawable icon;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            icon = getResources().getDrawable(R.drawable.ic_arrow_back_black, null);
        } else {
            icon = getResources().getDrawable(R.drawable.ic_arrow_back_black);
        }

        toolbar.setNavigationIcon(setTint(icon,ContextCompat.getColor(this, theme.getTextColorPrimaryId())));

        Drawable drawable = toolbar.getOverflowIcon();
        if(drawable != null) {
            toolbar.setOverflowIcon(setTint(drawable, ContextCompat.getColor(this, theme.getTextColorPrimaryId())));
        }

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String title = getIntent().getStringExtra("title");
        getSupportActionBar().setTitle(title);

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onBackPressed() {

        WebView myWebView = (WebView) findViewById(R.id.webview);

        if (myWebView.canGoBack()) {
            myWebView.goBack();

        } else {
            super.onBackPressed();
        }

    }

}
