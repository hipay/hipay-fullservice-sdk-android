package com.hipay.hipayfullservice.screen.adapter;

import android.app.Activity;
import android.content.res.Resources;
import android.support.annotation.ColorRes;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hipay.hipayfullservice.R;
import com.hipay.hipayfullservice.core.models.PaymentProduct;
import com.hipay.hipayfullservice.screen.activity.PaymentProductsActivity;
import com.hipay.hipayfullservice.screen.model.CustomTheme;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by nfillion on 29/02/16.
 */

public class PaymentProductsAdapter extends RecyclerView.Adapter<PaymentProductsAdapter.ViewHolder> {

    public static final String DRAWABLE = "drawable";
    private static final String ICON_PAYMENT_PRODUCTS = "icon_product_";
    private final Resources mResources;
    private final String mPackageName;
    private LayoutInflater mLayoutInflater;
    private Activity mActivity;
    private List<PaymentProduct> mPaymentProducts;

    private OnItemClickListener mOnItemClickListener;

    public interface OnItemClickListener {
        void onClick(View view, int position);
    }

    public PaymentProductsAdapter(Activity activity) {
        mActivity = activity;
        mResources = mActivity.getResources();
        mPackageName = mActivity.getPackageName();
        mLayoutInflater = LayoutInflater.from(activity.getApplicationContext());
        updatePaymentProducts(activity);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(mLayoutInflater
                .inflate(R.layout.item_payment_product, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        PaymentProduct paymentProduct = mPaymentProducts.get(position);

        PaymentProductsActivity activity = (PaymentProductsActivity)mActivity;
        CustomTheme theme = activity.getCustomTheme();

        setCategoryIcon(paymentProduct, holder.icon);

        holder.itemView.setBackgroundColor(getColor(android.R.color.background_light));
        holder.title.setText(paymentProduct.getCode());

        holder.title.setTextColor(getColor(theme.getTextColorPrimaryId()));
        holder.title.setBackgroundColor(getColor(theme.getColorPrimaryId()));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnItemClickListener.onClick(v, position);
            }
        });

    }

    @Override
    public long getItemId(int position) {

        //TODO not useful for now
        return -1;
        //return mPaymentProducts.get(position).getId().hashCode();
    }

    @Override
    public int getItemCount() {
        return mPaymentProducts.size();
    }

    public PaymentProduct getItem(int position) {
        return mPaymentProducts.get(position);
    }

    /**
     * @see android.support.v7.widget.RecyclerView.Adapter#notifyItemChanged(int)
     * @param id Id of changed category.
     */
    public final void notifyItemChanged(String id) {
        updatePaymentProducts(mActivity);
        notifyItemChanged(getItemPositionById(id));
    }

    private int getItemPositionById(String id) {

        //TODO not useful
        return -1;

        //for (int i = 0; i < mPaymentProducts.size(); i++) {
            //if (mPaymentProducts.get(i).getId().equals(id)) {
                //return i;
            //}
        //}
        //return -1;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    private void setCategoryIcon(PaymentProduct paymentProduct, ImageView icon) {

        final int categoryImageResource = mResources.getIdentifier(
                ICON_PAYMENT_PRODUCTS + paymentProduct.getCode(), DRAWABLE, mPackageName);

        icon.setImageResource(categoryImageResource);
    }

    private void updatePaymentProducts(Activity activity) {

        PaymentProduct paymentProduct = new PaymentProduct();
        paymentProduct.setCode("visa");
        paymentProduct.setTokenizable(true);

        PaymentProduct paymentProduct2 = new PaymentProduct();
        paymentProduct2.setCode("mastercard");
        paymentProduct2.setTokenizable(false);

        PaymentProduct paymentProduct3 = new PaymentProduct();
        paymentProduct3.setCode("maestro");
        paymentProduct3.setTokenizable(false);

        //TODO check later how to get products
        mPaymentProducts = new ArrayList<>(Arrays.asList(
                paymentProduct,
                paymentProduct2,
                paymentProduct3
                ));
    }

    /**
     * Convenience method for color loading.
     *
     * @param colorRes The resource id of the color to load.
     * @return The loaded color.
     */
    private int getColor(@ColorRes int colorRes) {
        return ContextCompat.getColor(mActivity, colorRes);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        final ImageView icon;
        final TextView title;

        public ViewHolder(View container) {
            super(container);
            icon = (ImageView) container.findViewById(R.id.payment_product_icon);
            title = (TextView) container.findViewById(R.id.payment_product_title);
        }
    }
}
