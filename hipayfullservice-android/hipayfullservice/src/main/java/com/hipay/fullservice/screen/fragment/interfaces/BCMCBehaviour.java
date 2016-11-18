package com.hipay.fullservice.screen.fragment.interfaces;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.design.widget.TextInputLayout;
import android.text.InputFilter;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.hipay.fullservice.R;
import com.hipay.fullservice.core.models.PaymentProduct;
import com.hipay.fullservice.screen.helper.FormHelper;

/**
 * Created by nfillion on 20/05/16.
 */
public class BCMCBehaviour implements ICardBehaviour {

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    public void updateForm(EditText cardNumber, EditText cardCVV, EditText cardExpiry, TextInputLayout securityCodeLayout, TextView securityCodeInfoTextview, ImageView securityCodeInfoImageview, boolean networked, Context context) {

        cardCVV.setFilters(new InputFilter[]{new InputFilter.LengthFilter(3)});
        //hide the security code for bcmc
        securityCodeLayout.setVisibility(View.GONE);

        cardExpiry.setImeOptions(EditorInfo.IME_ACTION_DONE);
        cardNumber.setHint(context.getString(R.string.card_number_placeholder_maestro_bcmc));

        //cardNumber.setFilters( new InputFilter[] { new InputFilter.LengthFilter(FormHelper.getMaxCardNumberLength(PaymentProduct.PaymentProductCodeAmericanExpress, context))});
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            cardNumber.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.ic_credit_card_bcmc, 0);
        } else {
            cardNumber.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_credit_card_bcmc, 0);
        }


        securityCodeInfoTextview.setText(context.getString(R.string.card_security_code_description_cvv));
        securityCodeInfoImageview.setImageResource(R.drawable.cvc_mv);

        //bcmc
        //"6703 0000 0000 00003",
        //cardNumber.setText("67030000000000003");
        //cardCVV.setText("123");
    }

    @Override
    public boolean isSecurityCodeValid(EditText cardCVV) {
                return true;
    }

    @Override
    public boolean hasSecurityCode() {
        return false;
    }

    @Override
    public boolean hasSpaceAtIndex(Integer index, Context context) {

        return FormHelper.isIndexSpace(index, PaymentProduct.PaymentProductCodeMaestro, context);
    }

    @Override
    public String getProductCode() {
        return PaymentProduct.PaymentProductCodeBCMC;
    }
}
