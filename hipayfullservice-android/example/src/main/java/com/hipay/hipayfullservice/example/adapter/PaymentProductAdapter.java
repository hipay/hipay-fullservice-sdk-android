package com.hipay.hipayfullservice.example.adapter;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.support.annotation.ColorRes;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hipay.hipayfullservice.core.models.PaymentProduct;
import com.hipay.hipayfullservice.example.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by nfillion on 29/02/16.
 */

public class PaymentProductAdapter extends RecyclerView.Adapter<PaymentProductAdapter.ViewHolder> {

    public static final String DRAWABLE = "drawable";
    private static final String ICON_CATEGORY = "icon_category_";
    //private final Resources mResources;
    //private final String mPackageName;
    private LayoutInflater mLayoutInflater;
    private Activity mActivity;
    private List<PaymentProduct> mCategories;

    private OnItemClickListener mOnItemClickListener;

    public PaymentProductAdapter() {

    }

    public interface OnItemClickListener {
        void onClick(View view, int position);
    }

    public PaymentProductAdapter(Activity activity) {
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
        //Theme theme = category.getTheme();
        setCategoryIcon(paymentProduct, holder.icon);

        holder.itemView.setBackgroundColor(getColor(R.color.cardview_dark_background));

        // put there a normal color
        //holder.itemView.setBackgroundColor(getColor(theme.getWindowBackgroundColor()));
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
            icon.setImageResource(R.drawable.icon_category_music_raster);
        } else {
            icon.setImageResource(R.drawable.icon_category_music_raster);
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

    /**
     * Loads an icon that indicates that a category has already been solved.
     *
     * @param category The solved category to display.
     * @param categoryImageResource The category's identifying image.
     * @return The icon indicating that the category has been solved.
     */
    private Drawable loadSolvedIcon(PaymentProduct category, int categoryImageResource) {

        //TODO check later what happens there
        return null;

        //if (ApiLevelHelper.isAtLeast(Build.VERSION_CODES.LOLLIPOP)) {
            //return loadSolvedIconLollipop(category, categoryImageResource);
        //}
        //return loadSolvedIconPreLollipop(category, categoryImageResource);

    }

    @NonNull
    private LayerDrawable loadSolvedIconLollipop(PaymentProduct category, int categoryImageResource) {

        //TODO check this later
        return null;

        //final Drawable done = loadTintedDoneDrawable();
        //final Drawable categoryIcon = loadTintedCategoryDrawable(category, categoryImageResource);
        //Drawable[] layers = new Drawable[]{categoryIcon, done}; // ordering is back to front
        //return new LayerDrawable(layers);
    }

    private Drawable loadSolvedIconPreLollipop(PaymentProduct category, int categoryImageResource) {
        return loadTintedCategoryDrawable(category, categoryImageResource);
    }

    /**
     * Loads and tints a drawable.
     *
     * @param category The category providing the tint color
     * @param categoryImageResource The image resource to tint
     * @return The tinted resource
     */
    private Drawable loadTintedCategoryDrawable(PaymentProduct category, int categoryImageResource) {

        return null;

        //final Drawable categoryIcon = ContextCompat
                //.getDrawable(mActivity, categoryImageResource).mutate();
        //return wrapAndTint(categoryIcon, category.getTheme().getPrimaryColor());
    }

    /**
     * Loads and tints a check mark.
     *
     * @return The tinted check mark
     */
    private Drawable loadTintedDoneDrawable() {

        return null;

        //final Drawable done = ContextCompat.getDrawable(mActivity, R.drawable.ic_tick);
        //return wrapAndTint(done, android.R.color.white);
    }

    private Drawable wrapAndTint(Drawable done, @ColorRes int color) {
        Drawable compatDrawable = DrawableCompat.wrap(done);
        DrawableCompat.setTint(compatDrawable, getColor(color));
        return compatDrawable;
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
            icon = (ImageView) container.findViewById(R.id.category_icon);
            title = (TextView) container.findViewById(R.id.category_title);
        }
    }
}
