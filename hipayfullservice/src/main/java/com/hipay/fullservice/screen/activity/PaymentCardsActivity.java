package com.hipay.fullservice.screen.activity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.app.ActivityOptionsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import android.view.Window;
import android.widget.TextView;

import com.hipay.fullservice.R;
import com.hipay.fullservice.core.client.GatewayClient;
import com.hipay.fullservice.core.errors.Errors;
import com.hipay.fullservice.core.errors.exceptions.ApiException;
import com.hipay.fullservice.core.errors.exceptions.HttpException;
import com.hipay.fullservice.core.models.Transaction;
import com.hipay.fullservice.core.requests.order.PaymentPageRequest;
import com.hipay.fullservice.screen.fragment.PaymentCardsFragment;
import com.hipay.fullservice.screen.helper.ApiLevelHelper;
import com.hipay.fullservice.screen.model.CustomTheme;

/**
 * Created by HiPay on 21/12/2016.
 */

public class PaymentCardsActivity extends PaymentScreenActivity implements PaymentCardsFragment.OnCallbackOrderListener {

    private CustomTheme customTheme;

    public static void start(Activity activity, PaymentPageRequest paymentPageRequest, String signature, CustomTheme theme) {
        Intent starter = getStartIntent(activity, paymentPageRequest, signature, theme);

        ActivityOptionsCompat activityOptions = ActivityOptionsCompat
                .makeSceneTransitionAnimation(activity);

        ActivityCompat.startActivityForResult(activity, starter, PaymentPageRequest.REQUEST_ORDER, activityOptions.toBundle());
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
    static Intent getStartIntent(Context context, PaymentPageRequest paymentPageRequest, String signature, CustomTheme theme) {

        Intent starter = new Intent(context, PaymentCardsActivity.class);

        Bundle bundle = paymentPageRequest.toBundle();
        starter.putExtra(PaymentPageRequest.TAG, bundle);

        if (theme == null) {
            theme = new CustomTheme(R.color.hpf_primary,R.color.hpf_primary_dark,R.color.hpf_light);
        }
        starter.putExtra(CustomTheme.TAG, theme.toBundle());
        starter.putExtra(GatewayClient.SIGNATURE_TAG, signature);

        return starter;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PaymentPageRequest.REQUEST_ORDER) {

            if (resultCode == R.id.transaction_succeed) {

                setResult(R.id.transaction_succeed, data);
                finish();

            } else if (resultCode == R.id.transaction_failed) {

                setResult(R.id.transaction_failed, data);
                finish();
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_payment_cards);

        Bundle customThemeBundle = getIntent().getBundleExtra(CustomTheme.TAG);
        CustomTheme customTheme = CustomTheme.fromBundle(customThemeBundle);

        this.setCustomTheme(customTheme);

        setUpToolbar();
        if (savedInstanceState == null) {

            attachPaymentCardsListFragment();
        }

        //useful when this activity is gonna be called with makeTransition
        //supportPostponeEnterTransition();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void setUpToolbar() {

        if (ApiLevelHelper.isAtLeast(Build.VERSION_CODES.LOLLIPOP)) {

            Window window = getWindow();
            window.setStatusBarColor(ContextCompat.getColor(this,
                    this.getCustomTheme().getColorPrimaryDarkId()));
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_payment_cards);
        toolbar.setBackgroundColor(ContextCompat.getColor(this, this.getCustomTheme().getColorPrimaryId()));
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowTitleEnabled(false);

        TextView textView = (TextView) toolbar.findViewById(R.id.title);
        textView.setText(R.string.payment_cards_title);
        textView.setTextColor(ContextCompat.getColor(this, this.getCustomTheme().getTextColorPrimaryId()));
    }

    private void attachPaymentCardsListFragment() {

        FragmentManager supportFragmentManager = getSupportFragmentManager();
        Fragment fragment = supportFragmentManager.findFragmentById(R.id.payment_cards_container);
        if (!(fragment instanceof PaymentCardsFragment)) {

            Bundle paymentPageRequestBundle = getIntent().getBundleExtra(PaymentPageRequest.TAG);
            Bundle customThemeBundle = getIntent().getBundleExtra(CustomTheme.TAG);
            String signature = getIntent().getStringExtra(GatewayClient.SIGNATURE_TAG);

            Bundle args = new Bundle();
            args.putBundle(PaymentPageRequest.TAG, paymentPageRequestBundle);
            args.putBundle(CustomTheme.TAG, customThemeBundle);
            args.putString(GatewayClient.SIGNATURE_TAG, signature);

            fragment = PaymentCardsFragment.newInstance(args);
        }

        supportFragmentManager.beginTransaction()
                .replace(R.id.payment_cards_container, fragment)
                .commit();
    }

    public CustomTheme getCustomTheme() {
        return customTheme;
    }

    public void setCustomTheme(CustomTheme customTheme) {
        this.customTheme = customTheme;
    }

    @Override
    public void onCallbackOrderReceived(Transaction transaction, Exception exception) {

        if (transaction != null) {

            this.formResultAction(transaction, null);

        } else {

            this.formResultAction(null, exception);
        }
    }

    private enum FormResult {

        FormActionReset,
        FormActionReload,
        FormActionBackgroundReload,
        FormActionForward,
        FormActionQuit
    }
    private void formResultAction(Transaction transaction, Exception exception) {

        FormResult formResult = null;

        if (transaction != null) {
            formResult = this.manageTransactionState(transaction);

        } else if (exception != null) {
            formResult = this.manageTransactionError(exception);

        } else {
            //no-op
        }

        if (formResult != null) {

            switch (formResult) {
                case FormActionBackgroundReload: {

                    //this.setLoadingMode(true);
                    //it's on loading mode already
                    this.transactionNeedsReload(transaction);

                } break;

                case FormActionQuit: {
                    finish();
                } break;

                // this never happens for now
                case FormActionReset:
                case FormActionReload:
                default: {
                    //nothing
                    this.setLoadingMode(false);
                }
            }
        }

    }

    private FormResult manageTransactionState(Transaction transaction) {

        FormResult formResult = null;

        switch (transaction.getState()) {

            case TransactionStateCompleted:
            case TransactionStatePending: {

                Intent intent = getIntent();
                intent.putExtra(Transaction.TAG, transaction.toBundle());
                setResult(R.id.transaction_succeed, intent);

                formResult = FormResult.FormActionQuit;

            } break;

            case TransactionStateDeclined: {

                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                };

                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle(R.string.alert_transaction_declined_title)
                        .setMessage(R.string.alert_transaction_declined_body)
                        .setNegativeButton(R.string.button_ok, dialogClickListener)
                        .setCancelable(false)
                        .show();

                formResult = FormResult.FormActionReload;

            } break;

            case TransactionStateError: {

                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                };

                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle(R.string.alert_transaction_error_title)
                        .setMessage(R.string.alert_transaction_error_body)
                        .setNegativeButton(R.string.button_ok, dialogClickListener)
                        .setCancelable(false)
                        .show();

                formResult = FormResult.FormActionReload;

            } break;
        }

        return formResult;
    }

    private FormResult manageTransactionError(Exception exception) {

        ApiException apiException = (ApiException) exception;

        // Duplicate order
        if (this.isOrderAlreadyPaid(apiException)) {

            return FormResult.FormActionBackgroundReload;

        }

        // Final error (ex. max attempts exceeded)
        else if (this.isTransactionErrorFinal(apiException)) {

            Intent intent = getIntent();
            intent.putExtra(Errors.TAG, apiException.toBundle());
            setResult(R.id.transaction_failed, intent);
            return FormResult.FormActionQuit;
        }

        // Client error
        else if (apiException.getCause() != null) {

            HttpException httpException = (HttpException)apiException.getCause();

            Integer httpStatusCode = httpException.getStatusCode();
            if (httpStatusCode != null) {

                if (httpStatusCode.equals(Errors.Code.HTTPClient.getIntegerValue())) {

                    Intent intent = getIntent();
                    intent.putExtra(Errors.TAG, apiException.getCause().getLocalizedMessage());
                    setResult(R.id.technical_error, intent);
                    return FormResult.FormActionQuit;

                } else if (httpStatusCode.equals(Errors.Code.HTTPNetworkUnavailable.getIntegerValue())) {

                    //we don't need to backgroundReload

                    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            switch (which) {
                                case DialogInterface.BUTTON_POSITIVE: {
                                    dialog.dismiss();

                                    Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.payment_cards_container);
                                    if (fragment != null) {

                                        PaymentCardsFragment paymentCardsFragment = (PaymentCardsFragment) fragment;
                                        paymentCardsFragment.setLoadingMode(true);
                                        paymentCardsFragment.launchRequest();
                                    }
                                }
                                break;

                                case DialogInterface.BUTTON_NEGATIVE:
                                    dialog.dismiss();
                                    break;
                            }
                        }
                    };

                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle(R.string.alert_error_connection_title)
                            .setMessage(R.string.alert_error_default_body)
                            .setNegativeButton(R.string.button_ok, dialogClickListener)
                            .setPositiveButton(R.string.button_retry, dialogClickListener)
                            .setCancelable(false)
                            .show();

                    return FormResult.FormActionReload;

                }
            }
        }

        // Other connection or server error, unknown error
        this.showCancelRetryDialog();
        return FormResult.FormActionReload;

    }

    private void setLoadingMode(boolean loadingMode) {

        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.payment_cards_container);
        if (fragment != null) {

            PaymentCardsFragment paymentCardsFragment = (PaymentCardsFragment) fragment;
            paymentCardsFragment.setLoadingMode(loadingMode);
        }
    }

    private void showCancelRetryDialog() {

        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE: {
                        dialog.dismiss();

                        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.payment_cards_container);
                        if (fragment != null) {

                            PaymentCardsFragment paymentCardsFragment = (PaymentCardsFragment) fragment;
                            paymentCardsFragment.setLoadingMode(true);
                            paymentCardsFragment.launchRequest();
                        }
                    }

                    break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        dialog.dismiss();
                        break;
                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.alert_error_default_title)
                .setMessage(R.string.alert_error_default_body)
                .setNegativeButton(R.string.button_ok, dialogClickListener)
                .setPositiveButton(R.string.button_retry, dialogClickListener)
                .setCancelable(false)
                .show();

    }

    private boolean isTransactionErrorFinal(ApiException exception) {

        Integer statusCode = exception.getStatusCode();
        if (statusCode.equals(Errors.Code.APICheckout.getIntegerValue())) {

            Integer apiCode = exception.getApiCode();
            if (
                    apiCode != null &&
                            (apiCode.equals(Errors.APIReason.APIDuplicateOrder.getIntegerValue())
                                    ||
                                    apiCode.equals(Errors.APIReason.APIMaxAttemptsExceeded.getIntegerValue()))
                    ) {

                return true;
            }
        }

        return false;
    }

    private boolean isOrderAlreadyPaid(ApiException exception) {

        Integer statusCode = exception.getStatusCode();
        Integer apiCode = exception.getApiCode();

        if (statusCode.equals(Errors.Code.APICheckout.getIntegerValue()) &&
                apiCode != null &&
                apiCode.equals(Errors.APIReason.APIDuplicateOrder.getIntegerValue())
                ) {
            return true;
        }

        return false;
    }

    private void transactionNeedsReload(Transaction transaction) {

        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.payment_cards_container);
        if (fragment != null) {

            PaymentCardsFragment paymentCardsFragment = (PaymentCardsFragment) fragment;

            //in loading mode already
            paymentCardsFragment.setLoadingMode(true);

            //TODO need to know when that happens
            paymentCardsFragment.launchBackgroundReload(transaction);
        }
    }

    private void forceBackPressed() {

        //is it different from finish?
        super.onBackPressed();
        //finish();
    }

    @Override
    public void onBackPressed() {

        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.payment_cards_container);
        if (fragment != null) {

            final PaymentCardsFragment paymentCardsFragment = (PaymentCardsFragment) fragment;

            boolean loadingMode = paymentCardsFragment.getLoadingMode();
            if (loadingMode == true) {

                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case DialogInterface.BUTTON_POSITIVE: {
                                dialog.dismiss();

                                paymentCardsFragment.cancelOperations();

                                forceBackPressed();
                            }
                            break;

                            case DialogInterface.BUTTON_NEGATIVE:
                                dialog.dismiss();
                                break;
                        }
                    }
                };

                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle(R.string.alert_transaction_loading_title)
                        .setMessage(R.string.alert_transaction_loading_body)
                        .setNegativeButton(R.string.alert_transaction_loading_no, dialogClickListener)
                        .setPositiveButton(R.string.alert_transaction_loading_yes, dialogClickListener)
                        .setCancelable(false)
                        .show();

            } else {

                super.onBackPressed();
            }

        } else {
            super.onBackPressed();
        }
    }
}
