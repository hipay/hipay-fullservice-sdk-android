package com.hipay.fullservice.screen.activity;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.IsoDep;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.github.devnied.emvnfccard.model.EmvCard;
import com.github.devnied.emvnfccard.parser.EmvParser;
import com.hipay.fullservice.R;
import com.hipay.fullservice.core.client.GatewayClient;
import com.hipay.fullservice.core.errors.Errors;
import com.hipay.fullservice.core.errors.exceptions.ApiException;
import com.hipay.fullservice.core.errors.exceptions.HttpException;
import com.hipay.fullservice.core.models.PaymentMethod;
import com.hipay.fullservice.core.models.PaymentProduct;
import com.hipay.fullservice.core.models.Transaction;
import com.hipay.fullservice.core.requests.order.PaymentPageRequest;
import com.hipay.fullservice.core.utils.Provider;
import com.hipay.fullservice.screen.fragment.AbstractPaymentFormFragment;
import com.hipay.fullservice.screen.fragment.TokenizableCardPaymentFormFragment;
import com.hipay.fullservice.screen.helper.ApiLevelHelper;
import com.hipay.fullservice.screen.model.CustomTheme;
import com.hipay.fullservice.screen.widget.TextSharedElementCallback;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Locale;

/**
 * Created by nfillion on 29/02/16.
 */
public class PaymentFormActivity extends AppCompatActivity implements AbstractPaymentFormFragment.OnCallbackOrderListener {

    public static int SCAN_PERMISSION_REQUEST_CODE = 0x2100; // arbitrary int
    public static int SCAN_REQUEST_CODE = 0x2200; // arbitrary int

    private CustomTheme customTheme;

    private ImageButton mToolbarBack;
    private AlertDialog mDialog;
    private ProgressBar mProgressBar;

    private List<PaymentMethod> history;

    public static Intent getStartIntent(Context context, Bundle paymentPageRequestBundle, Bundle themeBundle, PaymentProduct paymentProduct, String signature) {

        Intent starter = new Intent(context, PaymentFormActivity.class);

        starter.putExtra(PaymentPageRequest.TAG, paymentPageRequestBundle);

        Bundle productBundle = paymentProduct.toBundle();
        starter.putExtra(PaymentProduct.TAG, productBundle);

        starter.putExtra(CustomTheme.TAG, themeBundle);
        starter.putExtra(GatewayClient.SIGNATURE_TAG, signature);

        return starter;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {

        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.form_fragment_container);
        if (fragment != null) {

            AbstractPaymentFormFragment abstractPaymentFormFragment = (AbstractPaymentFormFragment)fragment;
            if ( (abstractPaymentFormFragment instanceof TokenizableCardPaymentFormFragment)) {
                fragment.onRequestPermissionsResult(requestCode, permissions, grantResults);
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        //it comes from ForwardWebViewActivity, handling 3DS

        if (requestCode == PaymentPageRequest.REQUEST_ORDER) {

            if (resultCode == R.id.transaction_succeed) {

                Bundle transactionBundle = data.getBundleExtra(Transaction.TAG);
                Transaction transaction = Transaction.fromBundle(transactionBundle);

                this.formResultAction(transaction, null);

            } else if (resultCode == R.id.transaction_failed) {

                Bundle exceptionBundle = data.getBundleExtra(Errors.TAG);
                ApiException exception = ApiException.fromBundle(exceptionBundle);

                this.formResultAction(null, exception);

            } else {

                //back pressed
                if (!isPaymentTokenizable()) {
                    forceBackPressed();
                }
            }

        } else if (requestCode == SCAN_REQUEST_CODE) {

            Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.form_fragment_container);
            if (fragment != null) {

                AbstractPaymentFormFragment abstractPaymentFormFragment = (AbstractPaymentFormFragment)fragment;
                if ( (abstractPaymentFormFragment instanceof TokenizableCardPaymentFormFragment)) {
                    fragment.onActivityResult(requestCode, resultCode, data);
                }
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

    }

    private boolean isPaymentTokenizable() {

        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.form_fragment_container);
        if (fragment != null) {

            AbstractPaymentFormFragment abstractPaymentFormFragment = (AbstractPaymentFormFragment)fragment;
            if ( (abstractPaymentFormFragment instanceof TokenizableCardPaymentFormFragment)) {

                return true;
            }
        }

        return false;
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

                // this never happens for now
                case FormActionReset: {

                    this.setLoadingMode(false, false);
                    if (!isPaymentTokenizable()) {
                        forceBackPressed();
                    }

                } break;

                case FormActionReload: {
                    //this is made directly
                    this.setLoadingMode(false, false);

                } break;

                case FormActionBackgroundReload: {

                    //this.setLoadingMode(true);
                    //it's on loading mode already
                    this.transactionNeedsReload(transaction);

                } break;

                case FormActionForward: {

                    this.setLoadingMode(false, true);
                    //do not dismiss the loading thing now
                } break;

                case FormActionQuit: {

                    //not need to stop the loading mode
                    finish();
                } break;

                default: {
                    //nothing
                    this.setLoadingMode(false, true);
                }
            }
        }
    }

    private void setLoadingMode(boolean loadingMode, boolean delay) {

        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.form_fragment_container);
        if (fragment != null) {

            AbstractPaymentFormFragment abstractPaymentFormFragment = (AbstractPaymentFormFragment)fragment;
            abstractPaymentFormFragment.setLoadingMode(loadingMode, delay);
        }
    }

    private void transactionNeedsReload(Transaction transaction) {

        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.form_fragment_container);
        if (fragment != null) {

            AbstractPaymentFormFragment abstractPaymentFormFragment = (AbstractPaymentFormFragment)fragment;

            //in loading mode already
            //abstractPaymentFormFragment.setLoadingMode(true);
            abstractPaymentFormFragment.launchBackgroundReload(transaction);
        }
    }

    private enum FormResult {

        FormActionReset,
        FormActionReload,
        FormActionBackgroundReload,
        FormActionForward,
        FormActionQuit
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

                    //we don't need to backgroundReload

                    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            if (!isPaymentTokenizable()) {
                                forceBackPressed();
                            }
                        }
                    };

                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle(R.string.error_title_default)
                            .setMessage(R.string.error_body_default)
                            .setNegativeButton(R.string.error_button_dismiss, dialogClickListener)
                            .setCancelable(false)
                            .show();

                    return FormResult.FormActionReload;

                } else if (httpStatusCode.equals(Errors.Code.HTTPNetworkUnavailable.getIntegerValue())) {

                    //we don't need to backgroundReload

                    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            switch (which) {
                                case DialogInterface.BUTTON_POSITIVE: {
                                    dialog.dismiss();

                                    Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.form_fragment_container);
                                    if (fragment != null) {

                                        AbstractPaymentFormFragment abstractPaymentFormFragment = (AbstractPaymentFormFragment)fragment;
                                        abstractPaymentFormFragment.setLoadingMode(true, false);
                                        abstractPaymentFormFragment.launchRequest();
                                    }
                                }
                                break;

                                case DialogInterface.BUTTON_NEGATIVE:
                                    dialog.dismiss();
                                    if (!isPaymentTokenizable()) {
                                        forceBackPressed();
                                    }
                                    break;
                            }
                        }
                    };

                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle(R.string.error_title_connection)
                            .setMessage(R.string.error_body_default)
                            .setNegativeButton(R.string.error_button_dismiss, dialogClickListener)
                            .setPositiveButton(R.string.error_button_retry, dialogClickListener)
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

    private void showCancelRetryDialog() {

        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE: {
                        dialog.dismiss();

                        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.form_fragment_container);
                        if (fragment != null) {

                            AbstractPaymentFormFragment abstractPaymentFormFragment = (AbstractPaymentFormFragment)fragment;
                            abstractPaymentFormFragment.setLoadingMode(true, false);
                            abstractPaymentFormFragment.launchRequest();
                        }
                    }

                    break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        dialog.dismiss();
                        if (!isPaymentTokenizable()) {
                            forceBackPressed();
                        }
                        break;
                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.error_title_default)
                .setMessage(R.string.error_body_default)
                .setNegativeButton(R.string.error_button_dismiss, dialogClickListener)
                .setPositiveButton(R.string.error_button_retry, dialogClickListener)
                .setCancelable(false)
                .show();

    }

    private void savePaymentMethod(PaymentMethod paymentMethod) {

        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.form_fragment_container);
        if (fragment != null)
        {
            AbstractPaymentFormFragment abstractPaymentFormFragment = (AbstractPaymentFormFragment) fragment;
            abstractPaymentFormFragment.savePaymentMethod(paymentMethod);
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

                //save payment card
                PaymentMethod paymentMethod = transaction.getPaymentMethod();
                if (paymentMethod != null)
                {
                    this.savePaymentMethod(paymentMethod);
                }

            } break;

            case TransactionStateDeclined: {

                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        if (!isPaymentTokenizable()) {
                            forceBackPressed();
                        }
                    }
                };

                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle(R.string.transaction_error_declined_title)
                        .setMessage(R.string.transaction_error_declined)
                        .setNegativeButton(R.string.error_button_dismiss, dialogClickListener)
                        .setCancelable(false)
                        .show();

                formResult = FormResult.FormActionReload;

            } break;

            case TransactionStateForwarding: {

                URL forwardUrl = transaction.getForwardUrl();

                Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.form_fragment_container);
                if (fragment != null) {

                    TextView titleView = (TextView) findViewById(R.id.payment_product_title);

                    String title = null;
                    if (!TextUtils.isEmpty(titleView.getText())) {
                        title = titleView.getText().toString();
                    }

                    AbstractPaymentFormFragment abstractPaymentFormFragment = (AbstractPaymentFormFragment)fragment;
                    abstractPaymentFormFragment.launchHostedPaymentPage(forwardUrl.toString(), title);
                }

                //dismiss the dialog before launching the forward webview
                dismissDialogs();

                //formResult = FormResult.FormActionReload;
                formResult = FormResult.FormActionForward;

            } break;

            case TransactionStateError: {

                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        if (!isPaymentTokenizable()) {
                            forceBackPressed();
                        }
                    }
                };

                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle(R.string.transaction_error_declined_title)
                        .setMessage(R.string.transaction_error_other)
                        .setNegativeButton(R.string.error_button_dismiss, dialogClickListener)
                        .setCancelable(false)
                        .show();

                formResult = FormResult.FormActionReload;
                //formResult = null;

            } break;
        }

        return formResult;
    }

    @Override
    public void onCallbackOrderReceived(Transaction transaction, Exception exception) {

        if (transaction != null) {

            this.formResultAction(transaction, null);

        } else {

            this.formResultAction(null, exception);
        }
    }

    @Override
    public void updatePaymentProduct(String title) {

        title = title.substring(0, 1).toUpperCase(Locale.US) + title.substring(1);

        int index = title.indexOf('_');
        if (index != -1) {

            String firstPart = title.substring(0, index);
            String secondPart = title.substring(index, index+2).toUpperCase(Locale.US) + title.substring(index+2);

            title = firstPart + secondPart;
            title = title.replace('_',' ');
        }

        TextView titleView = (TextView) findViewById(R.id.payment_product_title);
        titleView.setText(title);
    }

    @Override
    public void dismissDialogs() {

        if (mDialog != null) {
            mDialog.dismiss();
        }
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        Bundle customThemeBundle = getIntent().getBundleExtra(CustomTheme.TAG);

        CustomTheme customTheme = CustomTheme.fromBundle(customThemeBundle);
        this.setCustomTheme(customTheme);


        setContentView(R.layout.activity_payment_form);

        Bundle paymentProductBundle = getIntent().getBundleExtra(PaymentProduct.TAG);
        PaymentProduct paymentProduct = PaymentProduct.fromBundle(paymentProductBundle);
        initToolbar(paymentProduct);

        mProgressBar = (ProgressBar) findViewById(R.id.progress);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mProgressBar.setIndeterminateTintList(ColorStateList.valueOf(ContextCompat.getColor(this, customTheme.getTextColorPrimaryId())));

        } else {
            mProgressBar.getIndeterminateDrawable().setColorFilter(ContextCompat.getColor(this, customTheme.getTextColorPrimaryId()), PorterDuff.Mode.SRC_IN);
        }

        int categoryNameTextSize = getResources()
                .getDimensionPixelSize(R.dimen.payment_product_item_text_size);
        int paddingStart = getResources().getDimensionPixelSize(R.dimen.spacing_double);
        final int startDelay = getResources().getInteger(R.integer.toolbar_transition_duration);
        ActivityCompat.setEnterSharedElementCallback(this,
                new TextSharedElementCallback(categoryNameTextSize, paddingStart) {
                    @Override
                    public void onSharedElementStart(List<String> sharedElementNames,
                                                     List<View> sharedElements,
                                                     List<View> sharedElementSnapshots) {
                        super.onSharedElementStart(sharedElementNames,
                                sharedElements,
                                sharedElementSnapshots);
                        mToolbarBack.setScaleX(0f);
                        mToolbarBack.setScaleY(0f);
                    }

                    @Override
                    public void onSharedElementEnd(List<String> sharedElementNames,
                                                   List<View> sharedElements,
                                                   List<View> sharedElementSnapshots) {
                        super.onSharedElementEnd(sharedElementNames,
                                sharedElements,
                                sharedElementSnapshots);
                        // Make sure to perform this animation after the transition has ended.
                        ViewCompat.animate(mToolbarBack)
                                .setStartDelay(startDelay)
                                .scaleX(1f)
                                .scaleY(1f)
                                .alpha(1f);
                    }
                });

        if (savedInstanceState == null) {

            Bundle paymentPageRequestBundle = getIntent().getBundleExtra(PaymentPageRequest.TAG);
            String signature = getIntent().getStringExtra(GatewayClient.SIGNATURE_TAG);

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.form_fragment_container, AbstractPaymentFormFragment.newInstance(paymentPageRequestBundle, paymentProduct, signature, customThemeBundle)).commit();
        }

    }

    @Override
    protected void onNewIntent(final Intent intent) {
        super.onNewIntent(intent);

        if (intent != null && intent.getAction().equalsIgnoreCase(NfcAdapter.ACTION_TECH_DISCOVERED)) {

            Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
            IsoDep isoDep = IsoDep.get(tag);

            if (isoDep != null) {

                new NFCAsyncTask().execute(isoDep);
            }
        }
    }

    private class NFCAsyncTask extends AsyncTask<IsoDep, Void, EmvCard> {

        @Override
        protected void onPreExecute() {

            mProgressBar.setVisibility(View.VISIBLE);

        }

        @Override
        protected EmvCard doInBackground(IsoDep... params) {

            IsoDep isoDep = params[0];

            try {
                // Open connection
                isoDep.connect();

                Provider mProvider = new Provider(isoDep);
                mProvider.setTagCom(isoDep);

                EmvParser parser = new EmvParser(mProvider, true);
                EmvCard card = parser.readEmvCard();
                if (card == null) {
                    card = new EmvCard();
                }
                return card;

            } catch (IOException e)
            {
                return null;

            } finally
            {
                IOUtils.closeQuietly(isoDep);
            }
        }

        @Override
        protected void onPostExecute(EmvCard card) {

            mProgressBar.setVisibility(View.GONE);

            Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.form_fragment_container);
            if (fragment != null) {

                AbstractPaymentFormFragment abstractPaymentFormFragment = (AbstractPaymentFormFragment)fragment;
                if ( (abstractPaymentFormFragment instanceof TokenizableCardPaymentFormFragment)) {
                    TokenizableCardPaymentFormFragment formFragment = (TokenizableCardPaymentFormFragment) abstractPaymentFormFragment;

                    Snackbar snackbar = null;

                    if (card != null) {
                        if (StringUtils.isNotBlank(card.getCardNumber()))
                        {
                            String cardNumber = card.getCardNumber();
                            formFragment.fillCardNumber(cardNumber, card.getExpireDate());

                        } else if (card.isNfcLocked())
                        {
                            snackbar = Snackbar.make(formFragment.getView(), getString(R.string.nfc_locked), Snackbar.LENGTH_LONG);
                        } else
                        {
                            snackbar = Snackbar.make(formFragment.getView(), getString(R.string.nfc_error_unknown_card), Snackbar.LENGTH_LONG);
                        }

                    } else
                    {
                        snackbar = Snackbar.make(formFragment.getView(), getString(R.string.nfc_error_io), Snackbar.LENGTH_LONG);
                    }

                    if (snackbar != null) {
                        snackbar.show();
                    }
                }
            }

        }

        @Override
        protected void onProgressUpdate(Void... values) {}
    }


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void initToolbar(PaymentProduct paymentProduct) {

        if (ApiLevelHelper.isAtLeast(Build.VERSION_CODES.LOLLIPOP)) {

            Window window = getWindow();
            window.setStatusBarColor(ContextCompat.getColor(this,
                    this.getCustomTheme().getColorPrimaryDarkId()));
        }

        mToolbarBack = (ImageButton) findViewById(R.id.back);
        mToolbarBack.setColorFilter((ContextCompat.getColor(this,
                getCustomTheme().getTextColorPrimaryId())));

        mToolbarBack.setOnClickListener(mOnClickListener);
        TextView titleView = (TextView) findViewById(R.id.payment_product_title);

        titleView.setText(paymentProduct.getPaymentProductDescription());
        titleView.setTextColor(ContextCompat.getColor(this,
                getCustomTheme().getTextColorPrimaryId()));

        titleView.setBackgroundColor(ContextCompat.getColor(this,
                getCustomTheme().getColorPrimaryId()));

    }


    @SuppressLint("NewApi")
    public void setToolbarElevation(boolean shouldElevate) {
        if (ApiLevelHelper.isAtLeast(Build.VERSION_CODES.LOLLIPOP)) {
            mToolbarBack.setElevation(shouldElevate ?
                    getResources().getDimension(R.dimen.elevation_header) : 0);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    final View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(final View v) {
            int i = v.getId();
            if (i == R.id.back) {
                onBackPressed();

            } else {
                throw new UnsupportedOperationException(
                        "OnClick has not been implemented for " + getResources().
                                getResourceName(v.getId()));
            }
        }
    };

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    private void forceBackPressed() {

        //is it different from finish?
        super.onBackPressed();
        //finish();
    }

    @Override
    public void onBackPressed() {

        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.form_fragment_container);
        if (fragment != null) {

            AbstractPaymentFormFragment abstractPaymentFormFragment = (AbstractPaymentFormFragment)fragment;

            boolean loadingMode = abstractPaymentFormFragment.getLoadingMode();
            if (loadingMode == true) {

                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case DialogInterface.BUTTON_POSITIVE: {
                                dialog.dismiss();

                                Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.form_fragment_container);
                                if (fragment != null) {

                                    AbstractPaymentFormFragment abstractPaymentFormFragment = (AbstractPaymentFormFragment)fragment;
                                    abstractPaymentFormFragment.cancelOperations();
                                }

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
                mDialog = builder.setTitle(R.string.alert_transaction_loading_title)
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

    public CustomTheme getCustomTheme() {
        return customTheme;
    }

    public void setCustomTheme(CustomTheme customTheme) {
        this.customTheme = customTheme;
    }
}
