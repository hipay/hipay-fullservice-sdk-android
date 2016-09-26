package com.hipay.hipayfullservice.example.fragment;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;
import android.widget.ListView;
import android.widget.TextView;

import com.hipay.hipayfullservice.core.models.PaymentProduct;
import com.hipay.hipayfullservice.example.DemoActivity;
import com.hipay.hipayfullservice.example.R;
import com.hipay.hipayfullservice.screen.helper.ApiLevelHelper;
import com.hipay.hipayfullservice.screen.model.CustomTheme;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Created by nfillion on 13/06/16.
 */

public class ProductCategoryListFragment extends ListFragment implements AdapterView.OnItemClickListener {

    OnPaymentProductSelectedListener mCallback;

    public interface OnPaymentProductSelectedListener {
        void onPaymentProductSelected(int position, boolean isChecked);
    }

    public static ProductCategoryListFragment newInstance(Map<String, Boolean> paymentProducts, CustomTheme customTheme) {

        ProductCategoryListFragment fragment = new ProductCategoryListFragment();

        Bundle args = new Bundle();

        String key = PaymentProduct.PaymentProductCategoryCodeCreditCard;
        args.putBoolean(key, paymentProducts.get(key));

        key = PaymentProduct.PaymentProductCategoryCodeDebitCard;
        args.putBoolean(key, paymentProducts.get(key));

        key = PaymentProduct.PaymentProductCategoryCodeEWallet;
        args.putBoolean(key, paymentProducts.get(key));

        key = PaymentProduct.PaymentProductCategoryCodeRealtimeBanking;
        args.putBoolean(key, paymentProducts.get(key));

        args.putBundle(CustomTheme.TAG, customTheme.toBundle());

        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product_categories, container, false);
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            mCallback = (OnPaymentProductSelectedListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnPaymentProductSelectedListener");
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ListView listView = getListView();
        listView.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE);
        listView.setItemsCanFocus(false);
        listView.setOnItemClickListener(this);

        List<String> list = Arrays.asList(
                getString(R.string.form_payment_products_credit_card),
                getString(R.string.form_payment_products_debit_card),
                getString(R.string.form_payment_products_ewallet),
                getString(R.string.form_payment_products_realtime_banking)
        );

        ArrayAdapter adapter = new PaymentProductsArrayAdapter(getActivity(), list);
        setListAdapter(adapter);

        Bundle args = getArguments();

        Boolean creditCard = args.getBoolean(PaymentProduct.PaymentProductCategoryCodeCreditCard);
        Boolean debitCard = args.getBoolean(PaymentProduct.PaymentProductCategoryCodeDebitCard);
        Boolean eWallet = args.getBoolean(PaymentProduct.PaymentProductCategoryCodeEWallet);
        Boolean realTime = args.getBoolean(PaymentProduct.PaymentProductCategoryCodeRealtimeBanking);

        listView.setItemChecked(0, creditCard);
        listView.setItemChecked(1, debitCard);
        listView.setItemChecked(2, eWallet);
        listView.setItemChecked(3, realTime);

    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle args = getArguments();
        Bundle customThemeBundle = args.getBundle(CustomTheme.TAG);
        CustomTheme customTheme = CustomTheme.fromBundle(customThemeBundle);

        TextView textView = (TextView) view.findViewById(R.id.payment_products_textview);
        textView.setTextColor(ContextCompat.getColor(getActivity(), customTheme.getColorPrimaryDarkId()));

        DemoActivity demoActivity = (DemoActivity) getActivity();

        if (ApiLevelHelper.isAtLeast(Build.VERSION_CODES.LOLLIPOP)) {

            Window window = demoActivity.getWindow();
            window.setStatusBarColor(ContextCompat.getColor(demoActivity,
                    customTheme.getColorPrimaryDarkId()));
        }

        Toolbar toolbar = (Toolbar) demoActivity.findViewById(R.id.toolbar);
        toolbar.setBackgroundColor(ContextCompat.getColor(demoActivity, customTheme.getColorPrimaryId()));
        toolbar.setTitleTextColor(ContextCompat.getColor(demoActivity, customTheme.getTextColorPrimaryId()));
    }

    private class PaymentProductsArrayAdapter extends ArrayAdapter<String> {
        private final Context context;
        private final List<String> list;

        public PaymentProductsArrayAdapter(Context context, List<String> list) {
            super(context, android.R.layout.simple_list_item_multiple_choice, list);
            this.context = context;
            this.list = list;
        }

        private class ViewHolder {
            public TextView text;
        }

        //*
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            View rowView = convertView;

            if (rowView == null) {

                LayoutInflater inflater = ((Activity)context).getLayoutInflater();

                rowView = inflater.inflate(R.layout.item_product_category, parent, false);

                ViewHolder viewHolder = new ViewHolder();
                viewHolder.text = (TextView) rowView.findViewById(R.id.text1);
                rowView.setTag(viewHolder);
            }

            ViewHolder holder = (ViewHolder) rowView.getTag();
            holder.text.setText(list.get(position));

            return rowView;
        }
        //*/
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,long id) {

        CheckedTextView checkedTextView = (CheckedTextView)view;
        mCallback.onPaymentProductSelected(position, checkedTextView.isChecked());

        //throw new RuntimeException("This is a crash");
    }
}