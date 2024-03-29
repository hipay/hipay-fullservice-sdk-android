package com.hipay.fullservice.screen.fragment.interfaces;

import android.content.Context;
import android.text.InputFilter;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;
import com.hipay.fullservice.R;
import com.hipay.fullservice.core.models.PaymentProduct;
import com.hipay.fullservice.screen.helper.FormHelper;

/**
 * Created by HiPay on 20/05/16.
 */
public class AmexBehaviour implements ICardBehaviour {

    @Override
    public void updateForm(EditText cardNumber, EditText cardCVV, EditText cardExpiry, TextInputLayout securityCodeLayout, TextView securityCodeInfoTextview, ImageView securityCodeInfoImageview, LinearLayout switchLayout, boolean networked, Context context) {

        securityCodeLayout.setVisibility(View.VISIBLE);

        //card_cvv
        //security code is 4 digits long for amex
        cardCVV.setFilters(new InputFilter[]{new InputFilter.LengthFilter(4)});

        cardNumber.setHint(context.getString(R.string.card_number_placeholder_amex));

        securityCodeLayout.setPlaceholderText(context.getString(R.string.card_security_code_placeholder_cid));
        cardExpiry.setImeOptions(EditorInfo.IME_ACTION_NEXT);

        cardNumber.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.ic_credit_card_amex, 0);

        cardNumber.setFilters( new InputFilter[] { new InputFilter.LengthFilter(FormHelper.getMaxCardNumberLength(PaymentProduct.PaymentProductCodeAmericanExpress, context))});

        securityCodeInfoTextview.setText(context.getString(R.string.card_security_code_description_cid));
        securityCodeInfoImageview.setImageResource(R.drawable.cvc_amex);

        if (switchLayout != null) {
            switchLayout.setVisibility(isCardStorageEnabled() ? View.VISIBLE: View.GONE);
        }
        //cardNumber.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_credit_card_black, 0);

        //American Express
        //"3712 449317 14724",
        //cardNumber.setText("371244931714724");
        //cardCVV.setText("1234");
    }


    @Override
    public boolean isSecurityCodeValid(EditText cardCVV) {

        if (!TextUtils.isEmpty(cardCVV.getText())) {

            String cvvString = cardCVV.getText().toString().trim();
            if (cvvString.length() == 4 && TextUtils.isDigitsOnly(cvvString)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean hasSecurityCode() {
        return true;
    }

    @Override
    public boolean hasSpaceAtIndex(Integer index, Context context) {

        return FormHelper.isIndexSpace(index, PaymentProduct.PaymentProductCodeAmericanExpress, context);
    }

    @Override
    public String getProductCode() {
        return PaymentProduct.PaymentProductCodeAmericanExpress;
    }

    @Override
    public boolean isCardStorageEnabled() {
        return true;
    }
}
