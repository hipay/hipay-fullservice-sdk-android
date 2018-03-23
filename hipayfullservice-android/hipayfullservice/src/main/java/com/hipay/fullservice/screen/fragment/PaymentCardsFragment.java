package com.hipay.fullservice.screen.fragment;

/**
 * Created by nfillion on 13/06/16.
 */

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;

import com.hipay.fullservice.R;
import com.hipay.fullservice.core.client.AbstractClient;
import com.hipay.fullservice.core.client.GatewayClient;
import com.hipay.fullservice.core.client.interfaces.callbacks.OrderRequestCallback;
import com.hipay.fullservice.core.client.interfaces.callbacks.TransactionDetailsCallback;
import com.hipay.fullservice.core.client.interfaces.callbacks.TransactionsDetailsCallback;
import com.hipay.fullservice.core.models.PaymentCardToken;
import com.hipay.fullservice.core.models.PaymentProduct;
import com.hipay.fullservice.core.models.Transaction;
import com.hipay.fullservice.core.requests.order.OrderRequest;
import com.hipay.fullservice.core.requests.order.PaymentPageRequest;
import com.hipay.fullservice.core.requests.payment.CardTokenPaymentMethodRequest;
import com.hipay.fullservice.core.utils.enums.ECI;
import com.hipay.fullservice.core.utils.PaymentCardTokenDatabase;
import com.hipay.fullservice.core.utils.Utils;
import com.hipay.fullservice.screen.activity.PaymentProductsActivity;
import com.hipay.fullservice.screen.model.CustomTheme;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;
import java.util.Locale;
import java.util.Set;

public class PaymentCardsFragment extends ListFragment implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {

    private Button mPayButton;
    private AppCompatButton mPaymentProductsButton;
    private FrameLayout mPayButtonLayout;
    private TextView mSelectCardTextview;

    private CustomTheme mCustomTheme;
    private PaymentPageRequest mPaymentPageRequest;
    private String mSignature;

    private List<PaymentCardToken> mPaymentCardTokens;
    private ListView mListView;

    private int mSelectedCard = AbsListView.INVALID_POSITION;

    private GatewayClient mGatewayClient;
    private ProgressBar mProgressBar;

    protected int mCurrentLoading = -1;
    protected boolean mLoadingMode = false;

    OnCallbackOrderListener mCallback;

    public interface OnCallbackOrderListener {

        void onCallbackOrderReceived(Transaction transaction, Exception exception);
    }

    public static PaymentCardsFragment newInstance(Bundle args) {

        PaymentCardsFragment fragment = new PaymentCardsFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //important for the magic lines
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_payment_cards, container, false);

        Bundle args = getArguments();

        final Bundle customThemeBundle = args.getBundle(CustomTheme.TAG);
        mCustomTheme = CustomTheme.fromBundle(customThemeBundle);

        final Bundle paymentPageRequestBundle = args.getBundle(PaymentPageRequest.TAG);
        mPaymentPageRequest = PaymentPageRequest.fromBundle(paymentPageRequestBundle);

        mSignature = args.getString(GatewayClient.SIGNATURE_TAG);

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            mCallback = (OnCallbackOrderListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnCallbackOrderListener");
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mListView = getListView();
        mListView.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);
        mListView.setItemsCanFocus(true);
        mListView.setOnItemClickListener(this);
        mListView.setOnItemLongClickListener(this);

        mPaymentCardTokens = new ArrayList<>();
        ArrayAdapter adapter = new PaymentCardsArrayAdapter(getActivity(), mPaymentCardTokens);
        setListAdapter(adapter);

        reloadList();

        // ---- magic lines starting here -----
        // call this to re-connect with an existing
        // loader (after screen configuration changes for e.g!)

        if (mGatewayClient != null && mCurrentLoading > 0 ) {

            if (mGatewayClient.canRelaunch()) {
                mGatewayClient.reLaunchOperations(mCurrentLoading);

            } else {

                cancelLoaderId(mCurrentLoading);
                launchRequest();
            }
        }

        //----- end magic lines -----

    }

    @Override
    public void onResume() {
        super.onResume();

        boolean isSelected = mSelectedCard != AbsListView.INVALID_POSITION;
        validatePayButton(isSelected);
        this.setLoadingMode(mLoadingMode);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mPayButton = (Button)view.findViewById(R.id.pay_button);
        mPayButtonLayout = (FrameLayout)view.findViewById(R.id.pay_button_layout);

        mSelectCardTextview = (TextView) view.findViewById(R.id.select_card_textview);
        mSelectCardTextview.setTextColor(ContextCompat.getColor(getActivity(), mCustomTheme.getColorPrimaryDarkId()));

        //AppCompatButton paymentProductsButton = (AppCompatButton) view.findViewById(R.id.payment_products_button);
        //paymentProductsButton.setTextColor(ContextCompat.getColor(getActivity(), customTheme.getColorPrimaryDarkId()));
        //Drawable wrapDrawable = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_navigate_next_black, null);
        //DrawableCompat.setTint(wrapDrawable, ContextCompat.getColor(getActivity(), customTheme.getColorPrimaryDarkId()));
        //paymentProductsButton.setCompoundDrawablesWithIntrinsicBounds(null, null, wrapDrawable ,null);

        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(Locale.getDefault());
        Currency c = Currency.getInstance(mPaymentPageRequest.getCurrency());
        currencyFormatter.setCurrency(c);
        String moneyFormatted = currencyFormatter.format(mPaymentPageRequest.getAmount());

        String moneyString = getString(R.string.pay, moneyFormatted);

        mPayButton.setText(moneyString);

        mPayButtonLayout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if (mSelectedCard != AbsListView.INVALID_POSITION) {

                    setLoadingMode(true);
                    launchRequest();
                }
            }
        });

        mPaymentProductsButton = (AppCompatButton)view.findViewById(R.id.payment_products_button);
        mPaymentProductsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                PaymentProductsActivity.start(getActivity(), mPaymentPageRequest, mSignature, mCustomTheme);
            }
        });

        mProgressBar = (ProgressBar)view.findViewById(R.id.progress);
    }

    private class PaymentCardsArrayAdapter extends ArrayAdapter<PaymentCardToken> {
        private final Context context;
        private final List<PaymentCardToken> list;

        public PaymentCardsArrayAdapter(Context context, List<PaymentCardToken> list) {
            super(context, R.layout.item_payment_card, list);
            this.context = context;
            this.list = list;
        }

        private class ViewHolder {
            public TextView textView;
            public TextView textView2;
            public ImageView imageView;
            public RadioButton radioButton;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            View rowView = convertView;

            if (rowView == null) {

                LayoutInflater inflater = ((Activity)context).getLayoutInflater();

                rowView = inflater.inflate(R.layout.item_payment_card, parent, false);

                ViewHolder viewHolder = new ViewHolder();
                viewHolder.textView = (TextView) rowView.findViewById(R.id.text1);
                viewHolder.textView2 = (TextView) rowView.findViewById(R.id.text2);
                viewHolder.imageView = (ImageView) rowView.findViewById(R.id.image1);
                viewHolder.radioButton = (RadioButton) rowView.findViewById(R.id.radio1);

                rowView.setTag(viewHolder);
            }

            ViewHolder holder = (ViewHolder) rowView.getTag();

            PaymentCardToken cardToken = list.get(position);
            holder.textView.setText(cardToken.getPan());

            String issuer = cardToken.getIssuer();
            if (issuer == null || issuer.isEmpty()) {
                holder.textView2.setVisibility(View.GONE);
            } else {
                holder.textView2.setVisibility(View.VISIBLE);
            }

            holder.textView2.setText(cardToken.getIssuer());

            if (mSelectedCard == position) {
                holder.radioButton.setChecked(true);
            } else {
                holder.radioButton.setChecked(false);
            }

            holder.imageView.setImageResource(brandToImage(cardToken.getBrand()));

            return rowView;
        }
    }

    private int brandToImage(String brand) {

        if (brand != null && !brand.isEmpty()) {

            if (brand.equalsIgnoreCase(PaymentProduct.PaymentProductCodeMasterCard))
            {
                return R.drawable.ic_credit_card_mastercard;

            } else if (brand.equalsIgnoreCase(PaymentProduct.PaymentProductCodeVisa))
            {
                return R.drawable.ic_credit_card_visa;

            } else if (brand.equalsIgnoreCase(PaymentProduct.PaymentProductCodeAmericanExpress))
            {
                return R.drawable.ic_credit_card_amex;

            } else if (brand.equalsIgnoreCase(PaymentProduct.PaymentProductCodeDiners))
            {
                return R.drawable.ic_credit_card_diners;

            } else if (brand.equalsIgnoreCase(PaymentProduct.PaymentProductCodeMaestro))
            {
                return R.drawable.ic_credit_card_maestro;
            }
        }

        return R.drawable.ic_credit_card_cb;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,long id) {

        if (mSelectedCard == position) {
            mSelectedCard = AbsListView.INVALID_POSITION;
        } else {
            mSelectedCard = position;
        }

        ((ArrayAdapter)mListView.getAdapter()).notifyDataSetChanged();

        boolean isSelected = mSelectedCard != AbsListView.INVALID_POSITION;
        validatePayButton(isSelected);
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {

        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE: {
                        dialog.dismiss();

                        //mListView.clearChoices();

                        if (mSelectedCard == position) {

                            mSelectedCard = AbsListView.INVALID_POSITION;
                            validatePayButton(false);
                        }

                        if (mSelectedCard != AbsListView.INVALID_POSITION) {

                            if (mSelectedCard > position) {

                                // index decreases after removing
                                mSelectedCard = mSelectedCard - 1;
                            }

                            mListView.setItemChecked(mSelectedCard, true);
                        }

                        PaymentCardTokenDatabase.getInstance().delete(getActivity(), mPaymentCardTokens.get(position), mPaymentPageRequest.getCurrency());
                        reloadList();

                        if (mPaymentCardTokens.size() == 0) {

                            mSelectCardTextview.setText(R.string.no_payment_card_stored);
                        }

                    }
                    break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        dialog.dismiss();
                        break;
                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.alert_deleting_payment_card_title)
                .setMessage(R.string.alert_deleting_payment_card_body)
                .setNegativeButton(android.R.string.cancel, dialogClickListener)
                .setPositiveButton(android.R.string.yes, dialogClickListener)
                .setCancelable(false)
                .show();

        return true;
    }

    private void reloadList() {

        mPaymentCardTokens.clear();

        Set<String> set = PaymentCardTokenDatabase.getInstance().getPaymentCardTokens(getActivity(), mPaymentPageRequest.getCurrency());

        if (set != null && !set.isEmpty()) {
            for (String paymentCardTokenString : set) {

                Bundle paymentCardTokenBundle = Utils.fromJSONString(paymentCardTokenString);
                if (paymentCardTokenBundle != null) {
                    PaymentCardToken paymentCardToken = PaymentCardToken.fromBundle(paymentCardTokenBundle);
                    mPaymentCardTokens.add(paymentCardToken);
                }
            }
        }

        ((ArrayAdapter)getListAdapter()).notifyDataSetChanged();
    }

    protected void validatePayButton(boolean validate) {

        if (validate) {

            mPayButton.setTextColor(ContextCompat.getColor(getActivity(), mCustomTheme.getTextColorPrimaryId()));
            mPayButtonLayout.setEnabled(true);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                mPayButtonLayout.setBackground(makeSelector(mCustomTheme));

                Drawable[] drawables = mPayButton.getCompoundDrawables();
                Drawable wrapDrawable = DrawableCompat.wrap(drawables[0]);
                DrawableCompat.setTint(wrapDrawable, ContextCompat.getColor(getActivity(), mCustomTheme.getTextColorPrimaryId()));
            }

        } else {

            mPayButton.setTextColor(ContextCompat.getColor(getActivity(), android.R.color.white));
            mPayButtonLayout.setEnabled(false);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                CustomTheme greyTheme = new CustomTheme(R.color.dark_grey, R.color.dark_grey, R.color.dark_grey);
                mPayButtonLayout.setBackground(makeSelector(greyTheme));

                Drawable[] drawables = mPayButton.getCompoundDrawables();
                Drawable wrapDrawable = DrawableCompat.wrap(drawables[0]);
                DrawableCompat.setTint(wrapDrawable, ContextCompat.getColor(getActivity(), android.R.color.white));
            }
        }
    }

    private StateListDrawable makeSelector(CustomTheme theme) {
        StateListDrawable res = new StateListDrawable();
        res.addState(new int[]{android.R.attr.state_pressed}, new ColorDrawable(ContextCompat.getColor(getActivity(), theme.getColorPrimaryDarkId())));
        res.addState(new int[]{}, new ColorDrawable(ContextCompat.getColor(getActivity(), theme.getColorPrimaryId())));
        return res;
    }

    public void setLoadingMode(boolean loadingMode) {

        if (loadingMode) {

            mProgressBar.setVisibility(View.VISIBLE);
            mPayButtonLayout.setVisibility(View.GONE);

            mPaymentProductsButton.setTextColor(ContextCompat.getColor(getActivity(),R.color.dark_grey));

            Drawable wrapDrawable = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_navigate_next_black, null);
            DrawableCompat.setTint(wrapDrawable, ContextCompat.getColor(getActivity(), R.color.dark_grey));
            mPaymentProductsButton.setCompoundDrawablesWithIntrinsicBounds(null, null, wrapDrawable ,null);

        } else {

            mProgressBar.setVisibility(View.GONE);
            mPayButtonLayout.setVisibility(View.VISIBLE);

            mPaymentProductsButton.setTextColor(ContextCompat.getColor(getActivity(), R.color.hpf_accent));

            Drawable wrapDrawable = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_navigate_next_black, null);
            DrawableCompat.setTint(wrapDrawable, ContextCompat.getColor(getActivity(), R.color.hpf_accent));
            mPaymentProductsButton.setCompoundDrawablesWithIntrinsicBounds(null, null, wrapDrawable ,null);
        }

        mPaymentProductsButton.setEnabled(!loadingMode);

        mListView.setEnabled(!loadingMode);

        mLoadingMode = loadingMode;
    }

    public boolean getLoadingMode() {
        return mLoadingMode;
    }

    public void launchRequest() {

        int checkedIndex = mSelectedCard;
        PaymentCardToken paymentCardToken = mPaymentCardTokens.get(checkedIndex);

        // get the brand to get the
        String productCode = paymentCardToken.getBrand();
        productCode = productCode.replace(" ", "_");

        OrderRequest orderRequest = new OrderRequest(mPaymentPageRequest);
        orderRequest.setPaymentProductCode(productCode.toLowerCase(Locale.US));

        CardTokenPaymentMethodRequest cardTokenPaymentMethodRequest =
                new CardTokenPaymentMethodRequest(
                        paymentCardToken.getToken(),
                        ECI.RECURRING_E_COMMERCE,
                        mPaymentPageRequest.getAuthenticationIndicator());

        orderRequest.setPaymentMethod(cardTokenPaymentMethodRequest);

        mGatewayClient = new GatewayClient(getActivity());
        mCurrentLoading = AbstractClient.RequestLoaderId.OrderReqLoaderId.getIntegerValue();
        mGatewayClient.requestNewOrder(orderRequest, mSignature, new OrderRequestCallback() {

            @Override
            public void onSuccess(final Transaction transaction) {
                //Log.i("transaction success", transaction.toString());

                if (mCallback != null) {
                    cancelLoaderId(AbstractClient.RequestLoaderId.OrderReqLoaderId.getIntegerValue());
                    mCallback.onCallbackOrderReceived(transaction, null);
                }

            }

            @Override
            public void onError(Exception error) {
                if (mCallback != null) {
                    cancelLoaderId(AbstractClient.RequestLoaderId.OrderReqLoaderId.getIntegerValue());
                    mCallback.onCallbackOrderReceived(null, error);
                }
            }
        });
    }


    public void launchBackgroundReload(Transaction transaction) {

        if (transaction != null) {

            String transactionReference = transaction.getTransactionReference();

            Bundle args = getArguments();
            final String signature = args.getString(GatewayClient.SIGNATURE_TAG);

            mCurrentLoading = AbstractClient.RequestLoaderId.TransactionReqLoaderId.getIntegerValue();
            mGatewayClient = new GatewayClient(getActivity());
            mGatewayClient.getTransactionWithReference(transactionReference, signature, new TransactionDetailsCallback() {

                @Override
                public void onSuccess(final Transaction transaction) {

                    if (mCallback != null) {
                        cancelLoaderId(AbstractClient.RequestLoaderId.TransactionReqLoaderId.getIntegerValue());
                        mCallback.onCallbackOrderReceived(transaction, null);
                    }
                }

                @Override
                public void onError(Exception error) {

                    if (mCallback != null) {
                        cancelLoaderId(AbstractClient.RequestLoaderId.TransactionReqLoaderId.getIntegerValue());
                        mCallback.onCallbackOrderReceived(null, error);
                    }
                }
            });
        }
        else {

            Bundle args = getArguments();
            final PaymentPageRequest paymentPageRequest = PaymentPageRequest.fromBundle(args.getBundle(PaymentPageRequest.TAG));
            final String signature = args.getString(GatewayClient.SIGNATURE_TAG);

            String orderId = paymentPageRequest.getOrderId();
            mCurrentLoading = AbstractClient.RequestLoaderId.TransactionsReqLoaderId.getIntegerValue();
            mGatewayClient = new GatewayClient(getActivity());
            mGatewayClient.getTransactionsWithOrderId(orderId, signature, new TransactionsDetailsCallback() {

                @Override
                public void onSuccess(List<Transaction> transactions) {
                    if (mCallback != null) {
                        cancelLoaderId(AbstractClient.RequestLoaderId.TransactionsReqLoaderId.getIntegerValue());
                        mCallback.onCallbackOrderReceived(transactions.get(0), null);
                    }
                }

                @Override
                public void onError(Exception error) {
                    if (mCallback != null) {
                        cancelLoaderId(AbstractClient.RequestLoaderId.TransactionsReqLoaderId.getIntegerValue());
                        mCallback.onCallbackOrderReceived(null, error);
                    }
                }
            });
        }
    }

    protected void cancelLoaderId(int loaderId) {

        mCurrentLoading = AbsListView.INVALID_POSITION;

        if (mGatewayClient != null) {
            mGatewayClient.cancelOperation(getActivity());
            mGatewayClient = null;
        }
    }

    public void cancelOperations() {

        if (mGatewayClient != null) {
            mGatewayClient.cancelOperation(getActivity());
            mGatewayClient = null;
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        //don't need that anymore
        //outState.putInt("selectedCard", mSelectedCard);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        this.cancelOperations();
    }
}
