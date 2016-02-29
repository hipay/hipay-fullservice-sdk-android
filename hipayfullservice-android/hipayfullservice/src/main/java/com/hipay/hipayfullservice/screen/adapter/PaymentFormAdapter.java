package com.hipay.hipayfullservice.screen.adapter;

import android.app.Activity;
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
import com.hipay.hipayfullservice.screen.model.Theme;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by nfillion on 29/02/16.
 */

public class PaymentFormAdapter extends RecyclerView.Adapter<PaymentFormAdapter.ViewHolder> {

    private LayoutInflater mLayoutInflater;
    private Activity mActivity;
    private List<PaymentProduct> mCategories;

    private OnItemClickListener mOnItemClickListener;

    public PaymentFormAdapter() {

    }

    public interface OnItemClickListener {
        void onClick(View view, int position);
    }

    public PaymentFormAdapter(Activity activity) {
        mActivity = activity;
        //mResources = mActivity.getResources();
        //mPackageName = mActivity.getPackageName();
        mLayoutInflater = LayoutInflater.from(activity.getApplicationContext());
        updateCategories(activity);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(mLayoutInflater
                .inflate(R.layout.item_payment_product, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        PaymentProduct paymentProduct = mCategories.get(position);

        Theme theme = Theme.blue;
        setCategoryIcon(paymentProduct, holder.icon);

        // put there a normal color
        holder.itemView.setBackgroundColor(getColor(theme.getWindowBackgroundColor()));

        //holder.title.setText(category.getName());
        //holder.title.setTextColor(getColor(theme.getTextPrimaryColor()));
        //holder.title.setBackgroundColor(getColor(theme.getPrimaryColor()));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnItemClickListener.onClick(v, position);
            }
        });
    }

    @Override
    public long getItemId(int position) {

        return -1;
        //return mCategories.get(position).getId().hashCode();
    }

    @Override
    public int getItemCount() {
        return mCategories.size();
    }

    public PaymentProduct getItem(int position) {
        return mCategories.get(position);
    }

    /**
     * @see android.support.v7.widget.RecyclerView.Adapter#notifyItemChanged(int)
     * @param id Id of changed category.
     */
    public final void notifyItemChanged(String id) {
        updateCategories(mActivity);
        notifyItemChanged(getItemPositionById(id));
    }

    private int getItemPositionById(String id) {

        //TODO not useful
        return -1;

        //for (int i = 0; i < mCategories.size(); i++) {
        //if (mCategories.get(i).getId().equals(id)) {
        //return i;
        //}
        //}
        //return -1;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    private void setCategoryIcon(PaymentProduct category, ImageView icon) {
        //final int categoryImageResourIce = mResources.getIdentifier(
        //ICON_CATEGORY + category.getId(), DRAWABLE, mPackageName);
        //final boolean solved = category.isSolved();

        final boolean solved = false;

        if (solved) {
            //Drawable solvedIcon = loadSolvedIcon(category, categoryImageResource);
            icon.setImageResource(R.drawable.payment_card);
        } else {
            icon.setImageResource(R.drawable.payment_card);
        }
    }

    private void updateCategories(Activity activity) {

        //TODO check later
        //mCategories = TopekaDatabaseHelper.getCategories(activity, true);
        mCategories = new ArrayList<>(Arrays.asList(
                new PaymentProduct(),
                new PaymentProduct(),
                new PaymentProduct(),
                new PaymentProduct(),
                new PaymentProduct(),
                new PaymentProduct(),
                new PaymentProduct()
        ));
    }

    private int getColor(@ColorRes int colorRes) {
        return ContextCompat.getColor(mActivity, colorRes);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        final ImageView icon;
        final TextView title;

        public ViewHolder(View container) {
            super(container);
            icon = (ImageView) container.findViewById(R.id.category_icon);
            title = (TextView) container.findViewById(R.id.category_title);
        }
    }
}
