package com.hipay.fullservice.screen.fragment;

/**
 * Created by nfillion on 13/06/16.
 */

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
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
import com.hipay.fullservice.screen.model.CustomTheme;

import java.text.NumberFormat;
import java.util.Arrays;
import java.util.Currency;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class PaymentCardsFragment extends ListFragment implements AdapterView.OnItemClickListener {

    OnPaymentCardSelectedListener mCallback;
    private Button mPayButton;
    private FrameLayout mPayButtonLayout;
    private TextView mSelectCardTextview;

    public interface OnPaymentCardSelectedListener {
        void onPaymentCardSelected(int position, boolean isChecked);
    }

    public static PaymentCardsFragment newInstance(Map<String, Boolean> paymentCards, CustomTheme customTheme) {

        PaymentCardsFragment fragment = new PaymentCardsFragment();

        Bundle args = new Bundle();

        //TODO il faudra donner la liste des cards au bundle.

        /*
        String key = PaymentProduct.PaymentProductCategoryCodeCreditCard;
        args.putBoolean(key, paymentCards.get(key));

        key = PaymentProduct.PaymentProductCategoryCodeDebitCard;
        args.putBoolean(key, paymentCards.get(key));

        key = PaymentProduct.PaymentProductCategoryCodeEWallet;
        args.putBoolean(key, paymentCards.get(key));

        key = PaymentProduct.PaymentProductCategoryCodeRealtimeBanking;
        args.putBoolean(key, paymentCards.get(key));

        */
        args.putBundle(CustomTheme.TAG, customTheme.toBundle());

        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_payment_cards, container, false);
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

        ListView listView = getListView();
        listView.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);
        listView.setItemsCanFocus(true);
        listView.setOnItemClickListener(this);

        List<String> list = Arrays.asList(
                "411111******2226",
                "411111******2226",
                "411111******2226",
                "411111******2226",
                "411111******1111"
        );

        ArrayAdapter adapter = new PaymentCardsArrayAdapter(getActivity(), list);
        setListAdapter(adapter);

        Bundle args = getArguments();

        // args

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

        Bundle args = getArguments();
        Bundle customThemeBundle = args.getBundle(CustomTheme.TAG);
        CustomTheme customTheme = CustomTheme.fromBundle(customThemeBundle);

        mPayButton = (Button)view.findViewById(R.id.pay_button);
        mPayButtonLayout = (FrameLayout)view.findViewById(R.id.pay_button_layout);

        mSelectCardTextview = (TextView) view.findViewById(R.id.select_card_textview);
        mSelectCardTextview.setTextColor(ContextCompat.getColor(getActivity(), customTheme.getColorPrimaryDarkId()));

        //AppCompatButton paymentProductsButton = (AppCompatButton) view.findViewById(R.id.payment_products_button);
        //paymentProductsButton.setTextColor(ContextCompat.getColor(getActivity(), customTheme.getColorPrimaryDarkId()));
        //Drawable wrapDrawable = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_navigate_next_black, null);
        //DrawableCompat.setTint(wrapDrawable, ContextCompat.getColor(getActivity(), customTheme.getColorPrimaryDarkId()));
        //paymentProductsButton.setCompoundDrawablesWithIntrinsicBounds(null, null, wrapDrawable ,null);

        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(Locale.getDefault());
        //Currency c = Currency.getInstance(paymentPageRequest.getCurrency());
        Currency c = Currency.getInstance("EUR");
        currencyFormatter.setCurrency(c);
        //String moneyFormatted = currencyFormatter.format(paymentPageRequest.getAmount());
        String moneyFormatted = currencyFormatter.format(2.0);

        String moneyString = getString(R.string.pay, moneyFormatted);

        mPayButton.setText(moneyString);

        mPayButtonLayout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                //setLoadingMode(true,false);
                //launchRequest();
            }
        });

        //handle the screen orientation

    }

    private class PaymentCardsArrayAdapter extends ArrayAdapter<String> {
        private final Context context;
        private final List<String> list;

        public PaymentCardsArrayAdapter(Context context, List<String> list) {
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
            holder.text.setText(list.get(position));

            return rowView;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,long id) {

        CheckedTextView checkedTextView = (CheckedTextView)view;

        //TODO no need to call the activity, just enable the payButton.
        //mCallback.onPaymentCardSelected(position, checkedTextView.isChecked());
        validatePayButton(checkedTextView.isChecked());
    }

    protected void validatePayButton(boolean validate) {

        if (validate) {

            final Bundle customThemeBundle = getArguments().getBundle(CustomTheme.TAG);
            CustomTheme theme = CustomTheme.fromBundle(customThemeBundle);

            mPayButton.setTextColor(ContextCompat.getColor(getActivity(), theme.getTextColorPrimaryId()));
            mPayButtonLayout.setEnabled(true);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                mPayButtonLayout.setBackground(makeSelector(theme));

                Drawable[] drawables = mPayButton.getCompoundDrawables();
                Drawable wrapDrawable = DrawableCompat.wrap(drawables[0]);
                DrawableCompat.setTint(wrapDrawable, ContextCompat.getColor(getActivity(), theme.getTextColorPrimaryId()));
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
}
