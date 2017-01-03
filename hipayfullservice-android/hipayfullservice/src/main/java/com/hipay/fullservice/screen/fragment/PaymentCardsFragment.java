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
import android.support.v4.app.ListFragment;
import android.support.v4.content.ContextCompat;
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
import android.widget.CheckedTextView;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.hipay.fullservice.R;
import com.hipay.fullservice.core.client.GatewayClient;
import com.hipay.fullservice.core.models.PaymentCardToken;
import com.hipay.fullservice.core.requests.order.PaymentPageRequest;
import com.hipay.fullservice.screen.activity.PaymentProductsActivity;
import com.hipay.fullservice.screen.model.CustomTheme;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;
import java.util.Locale;

public class PaymentCardsFragment extends ListFragment implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {

    OnPaymentCardSelectedListener mCallback;
    private Button mPayButton;
    private AppCompatButton mPaymentProductsButton;
    private FrameLayout mPayButtonLayout;
    private TextView mSelectCardTextview;

    private CustomTheme mCustomTheme;
    private PaymentPageRequest mPaymentPageRequest;
    private String mSignature;

    private List<PaymentCardToken> mPaymentCardTokens;
    private ListView mListView;

    private int mSelectedCard = -1;


    public interface OnPaymentCardSelectedListener {
        void onPaymentCardSelected(int position, boolean isChecked);
    }

    public static PaymentCardsFragment newInstance(Bundle args) {

        PaymentCardsFragment fragment = new PaymentCardsFragment();
        fragment.setArguments(args);

        return fragment;
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
            mCallback = (OnPaymentCardSelectedListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnPaymentCardSelectedListener");
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

        PaymentCardToken paymentCardToken = new PaymentCardToken();
        paymentCardToken.setPan("4111 1111 1111 1111");
        mPaymentCardTokens.add(paymentCardToken);

        PaymentCardToken paymentCardToken2 = new PaymentCardToken();
        paymentCardToken2.setPan("4222 2222 2222 2222");
        mPaymentCardTokens.add(paymentCardToken2);

        PaymentCardToken paymentCardToken3 = new PaymentCardToken();
        paymentCardToken3.setPan("4333 3333 3333 3333");
        mPaymentCardTokens.add(paymentCardToken3);

        ArrayAdapter adapter = new PaymentCardsArrayAdapter(getActivity(), mPaymentCardTokens);
        setListAdapter(adapter);

        if (savedInstanceState != null) {
            mSelectedCard = savedInstanceState.getInt("selectedCard");

            if (mSelectedCard != -1) {
                mListView.setItemChecked(mSelectedCard, true);
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        boolean cardChecked = getListView().getCheckedItemCount() > 0;
        validatePayButton(cardChecked);
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

                //setLoadingMode(true,false);
                //launchRequest();
            }
        });

        mPaymentProductsButton = (AppCompatButton)view.findViewById(R.id.payment_products_button);
        mPaymentProductsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PaymentProductsActivity.start(getActivity(), mPaymentPageRequest, mSignature, mCustomTheme);
            }
        });

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
            public TextView text;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            View rowView = convertView;

            if (rowView == null) {

                LayoutInflater inflater = ((Activity)context).getLayoutInflater();

                rowView = inflater.inflate(R.layout.item_payment_card, parent, false);

                ViewHolder viewHolder = new ViewHolder();
                viewHolder.text = (TextView) rowView.findViewById(R.id.text1);
                rowView.setTag(viewHolder);
            }

            ViewHolder holder = (ViewHolder) rowView.getTag();

            PaymentCardToken cardToken = list.get(position);
            holder.text.setText(cardToken.getPan());

            return rowView;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,long id) {

        CheckedTextView checkedTextView = (CheckedTextView)view;
        if (checkedTextView.isChecked() == true) {
            mSelectedCard = position;
        }

        validatePayButton(checkedTextView.isChecked());
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {

        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE: {
                        dialog.dismiss();

                        mListView.clearChoices();
                        mPaymentCardTokens.remove(position);

                        if (mSelectedCard == position) {

                            mSelectedCard = -1;
                            validatePayButton(false);
                        }

                        if (mSelectedCard != -1) {

                            if (mSelectedCard > position) {

                                // index decreases after removing
                                mSelectedCard = mSelectedCard - 1;
                            }

                            mListView.setItemChecked(mSelectedCard, true);
                        }

                        ((ArrayAdapter)getListAdapter()).notifyDataSetChanged();

                        if (mPaymentCardTokens.size() == 0) {

                            //TODO reinit this in setupCard method
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

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt("selectedCard", mSelectedCard);
    }
}
