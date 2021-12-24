package com.hipay.fullservice.screen.adapter;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.PorterDuff;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.ColorRes;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.hipay.fullservice.R;
import com.hipay.fullservice.core.models.PaymentProduct;
import com.hipay.fullservice.screen.activity.PaymentProductsActivity;
import com.hipay.fullservice.screen.model.CustomTheme;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HiPay on 29/02/16.
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
        void onClick(View view, PaymentProduct paymentProduct);
    }

    public PaymentProductsAdapter(Activity activity) {

        mActivity = activity;
        mResources = mActivity.getResources();
        mPackageName = mActivity.getPackageName();
        mLayoutInflater = LayoutInflater.from(activity.getApplicationContext());

        mPaymentProducts = new ArrayList<>();

        //updatePaymentProducts(activity);
        //emptyPaymentProducts();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(mLayoutInflater
                .inflate(R.layout.item_payment_product, parent, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        PaymentProduct paymentProduct = mPaymentProducts.get(holder.getAdapterPosition());

        PaymentProductsActivity activity = (PaymentProductsActivity)mActivity;
        CustomTheme theme = activity.getCustomTheme();

        setCategoryIcon(paymentProduct, holder.icon);

        holder.itemView.setBackgroundColor(getColor(android.R.color.background_light));
        holder.title.setText(paymentProduct.getPaymentProductDescription());

        holder.title.setTextColor(getColor(theme.getTextColorPrimaryId()));
        holder.title.setBackgroundColor(getColor(theme.getColorPrimaryId()));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnItemClickListener.onClick(v, getItem(holder.getAdapterPosition()));
            }
        });
    }

    @Override
    public long getItemId(int position) {

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

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    private void setCategoryIcon(PaymentProduct paymentProduct, ImageView icon) {

        int categoryImageResource;

        categoryImageResource = mResources.getIdentifier(
                ICON_PAYMENT_PRODUCTS + replaceString(paymentProduct.getCode()), DRAWABLE, mPackageName);

        if (categoryImageResource != 0) {
            icon.setImageResource(categoryImageResource);

        } else {
            icon.setImageResource(mResources.getIdentifier(
                    ICON_PAYMENT_PRODUCTS + "default", DRAWABLE, mPackageName));
        }

        if (paymentProduct.getCode().equals(PaymentProduct.PaymentProductCategoryCodeCard)) {

            PaymentProductsActivity activity = (PaymentProductsActivity)mActivity;
            CustomTheme theme = activity.getCustomTheme();
            icon.setColorFilter(getColor(theme.getColorPrimaryId()), PorterDuff.Mode.SRC_IN);
        }
    }

    public void updatePaymentProducts(List<PaymentProduct> paymentProducts) {

        mPaymentProducts.clear();
        mPaymentProducts.addAll(paymentProducts);
        notifyDataSetChanged();

        /*
        if (isGroupingEnabled != null || isGroupingEnabled.equals(Boolean.TRUE)) {

            boolean atLeastOneCard = false;

            Iterator<PaymentProduct> iter = paymentProducts.iterator();
            while (iter.hasNext()) {

                PaymentProduct p = iter.next();
                if (p.isTokenizable()) {
                    iter.remove();
                }
            }

            if (atLeastOneCard) {

                PaymentProduct cardProduct = new PaymentProduct();
                cardProduct.setCode(PaymentProduct.PaymentProductCategoryCodeCard);
                cardProduct.setPaymentProductDescription(mActivity.getString(R.string.payment_product_card_description));
                cardProduct.setTokenizable(true);

                mOnItemClickListener.onClick(null, cardProduct);
                //mPaymentProducts.add(cardProduct);
            }
        }
        */


        //mOnItemClickListener.onClick(null, mPaymentProducts.get(0));

        /*
        if (groupedCards != null && groupedCards.equals(Boolean.TRUE)) {

            mPaymentProducts.add(paymentProduct(PaymentProduct.PaymentProductCategoryCodeCreditCard, true));

        } else {

            //tokenizable card
            mPaymentProducts.add(paymentProduct(PaymentProduct.PaymentProductCodeMaestro, true));
            mPaymentProducts.add(paymentProduct(PaymentProduct.PaymentProductCodeBCMC, true));

            mPaymentProducts.add(paymentProduct(PaymentProduct.PaymentProductCodeVisa, true));
            mPaymentProducts.add(paymentProduct(PaymentProduct.PaymentProductCodeAmericanExpress, true));

            mPaymentProducts.add(paymentProduct(PaymentProduct.PaymentProductCodeCB, true));
            mPaymentProducts.add(paymentProduct(PaymentProduct.PaymentProductCodeDiners, true));
            mPaymentProducts.add(paymentProduct(PaymentProduct.PaymentProductCodeMasterCard, true));
        }

        // forward
        mPaymentProducts.add(paymentProduct(PaymentProduct.PaymentProductCodePayPal, false));
        mPaymentProducts.add(paymentProduct(PaymentProduct.PaymentProductCodeYandex, false));

        mPaymentProducts.add(paymentProduct(PaymentProduct.PaymentProductCodePrzelewy24, false));

        mPaymentProducts.add(paymentProduct(PaymentProduct.PaymentProductCodeSofortUberweisung, false));
        mPaymentProducts.add(paymentProduct(PaymentProduct.PaymentProductCodeSisal, false));
        mPaymentProducts.add(paymentProduct(PaymentProduct.PaymentProductCodeSDD, false));
        mPaymentProducts.add(paymentProduct(PaymentProduct.PaymentProductCodePayULatam, false));
        mPaymentProducts.add(paymentProduct(PaymentProduct.PaymentProductCodeINGHomepay, false));
        mPaymentProducts.add(paymentProduct(PaymentProduct.PaymentProductCodeBCMCMobile, false));
        mPaymentProducts.add(paymentProduct(PaymentProduct.PaymentProductCodeBankTransfer, false));
        mPaymentProducts.add(paymentProduct(PaymentProduct.PaymentProductCodePaysafecard, false));
        mPaymentProducts.add(paymentProduct(PaymentProduct.PaymentProductCodeDexiaDirectNet, false));

        //not found
        //mPaymentProducts.add(paymentProduct("account_klarna", false));

        mPaymentProducts.add(paymentProduct(PaymentProduct.PaymentProductCodeKlarnacheckout, false));
        mPaymentProducts.add(paymentProduct(PaymentProduct.PaymentProductCodeKlarnaInvoice, false));

        mPaymentProducts.add(paymentProduct(PaymentProduct.PaymentProductCodeArgencard, false));
        mPaymentProducts.add(paymentProduct(PaymentProduct.PaymentProductCodeAura, false));
        mPaymentProducts.add(paymentProduct(PaymentProduct.PaymentProductCodeBaloto, false));
        mPaymentProducts.add(paymentProduct(PaymentProduct.PaymentProductCodeBanamex, false));

        mPaymentProducts.add(paymentProduct(PaymentProduct.PaymentProductCodeBancoDoBrasil, false));
        // banco provincia
        mPaymentProducts.add(paymentProduct(PaymentProduct.PaymentProductCodeProvincia, false));

        //banque_accord
        mPaymentProducts.add(paymentProduct(PaymentProduct.PaymentProductCodeCarteAccord, false));

        mPaymentProducts.add(paymentProduct(PaymentProduct.PaymentProductCodeBBVABancomer, false));
        mPaymentProducts.add(paymentProduct(PaymentProduct.PaymentProductCodeBCP, false));
        mPaymentProducts.add(paymentProduct(PaymentProduct.PaymentProductCodeBitcoin, false));
        mPaymentProducts.add(paymentProduct(PaymentProduct.PaymentProductCodeBoletoBancario, false));
        mPaymentProducts.add(paymentProduct(PaymentProduct.PaymentProductCodeBradesco, false));

        mPaymentProducts.add(paymentProduct(PaymentProduct.PaymentProductCodeCabal, false));
        mPaymentProducts.add(paymentProduct(PaymentProduct.PaymentProductCodeCaixa, false));
        mPaymentProducts.add(paymentProduct(PaymentProduct.PaymentProductCode4xcb, false));
        mPaymentProducts.add(paymentProduct(PaymentProduct.PaymentProductCodeCBCOnline, false));
        mPaymentProducts.add(paymentProduct(PaymentProduct.PaymentProductCode3xcb, false));

        //cencosud
        mPaymentProducts.add(paymentProduct(PaymentProduct.PaymentProductCodeCensosud, false));

        mPaymentProducts.add(paymentProduct(PaymentProduct.PaymentProductCodeCobroExpress, false));
        mPaymentProducts.add(paymentProduct(PaymentProduct.PaymentProductCodeCofinoga, false));

        // not found
        //mPaymentProducts.add(paymentProduct("dcb", false));

        mPaymentProducts.add(paymentProduct(PaymentProduct.PaymentProductCodeDiscover, false));

        mPaymentProducts.add(paymentProduct(PaymentProduct.PaymentProductCodeEfecty, false));
        mPaymentProducts.add(paymentProduct(PaymentProduct.PaymentProductCodeElo, false));

        mPaymentProducts.add(paymentProduct(PaymentProduct.PaymentProductCodeGiropay, false));

        //not found
        //mPaymentProducts.add(paymentProduct("hipercard", false));

        mPaymentProducts.add(paymentProduct(PaymentProduct.PaymentProductCodeIDEAL, false));


        mPaymentProducts.add(paymentProduct(PaymentProduct.PaymentProductCodeItau, false));
        mPaymentProducts.add(paymentProduct(PaymentProduct.PaymentProductCodeIxe, false));

        //not found
        //mPaymentProducts.add(paymentProduct("jcb", false));

        mPaymentProducts.add(paymentProduct(PaymentProduct.PaymentProductCodeKBCOnline, false));


        //not found
        //mPaymentProducts.add(paymentProduct("mastercard_debit", false));

        //not found
        //mPaymentProducts.add(paymentProduct("mercado_livre", false));

        //not found
        //mPaymentProducts.add(paymentProduct("multibanco", false));

        mPaymentProducts.add(paymentProduct(PaymentProduct.PaymentProductCodeOxxo, false));

        mPaymentProducts.add(paymentProduct(PaymentProduct.PaymentProductCodePagoFacil, false));
        mPaymentProducts.add(paymentProduct(PaymentProduct.PaymentProductCode3xcbNoFees, false));
        mPaymentProducts.add(paymentProduct(PaymentProduct.PaymentProductCode4xcbNoFees, false));

        //not found
        //mPaymentProducts.add(paymentProduct("payshop", false));

        //not found
        //mPaymentProducts.add(paymentProduct("prelevement", false));

        mPaymentProducts.add(paymentProduct(PaymentProduct.PaymentProductCodeQiwiWallet, false));

        mPaymentProducts.add(paymentProduct(PaymentProduct.PaymentProductCodeRapipago, false));
        mPaymentProducts.add(paymentProduct(PaymentProduct.PaymentProductCodeRipsa, false));


        mPaymentProducts.add(paymentProduct(PaymentProduct.PaymentProductCodeSantanderCash, false));
        mPaymentProducts.add(paymentProduct(PaymentProduct.PaymentProductCodeSantanderHomeBanking, false));

        //not found
        //mPaymentProducts.add(paymentProduct("sepa_bank_transfer", false));

        //not found
        //mPaymentProducts.add(paymentProduct("sofort_lastshrift", false));

        mPaymentProducts.add(paymentProduct(PaymentProduct.PaymentProductCodeTarjetaShopping, false));

        //not found
        mPaymentProducts.add(paymentProduct(PaymentProduct.PaymentProductCodeNaranja, false));

        //not found
        //mPaymentProducts.add(paymentProduct("visa_debit", false));

        mPaymentProducts.add(paymentProduct(PaymentProduct.PaymentProductCodeWebmoneyTransfer, false));
        */

        //notifyItemInserted(0);
        //notifyItemRangeInserted(0, 4);
        //notifyDataSetChanged();
    }

    private String replaceString(String string) {

        return string.replace("-", "_");
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
