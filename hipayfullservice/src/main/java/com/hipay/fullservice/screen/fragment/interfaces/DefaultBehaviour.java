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
 * Created by HiPay on 01/07/16.
 */
public class DefaultBehaviour implements ICardBehaviour {

    @Override
    public void updateForm(EditText cardNumber, EditText cardCVV, EditText cardExpiry, TextInputLayout securityCodeLayout, TextView securityCodeInfoTextview, ImageView securityCodeInfoImageview, LinearLayout switchLayout, boolean networked, Context context) {

        securityCodeLayout.setVisibility(View.VISIBLE);

        cardExpiry.setImeOptions(EditorInfo.IME_ACTION_NEXT);
        cardCVV.setFilters(new InputFilter[]{new InputFilter.LengthFilter(3)});
        securityCodeLayout.setPlaceholderText(context.getString(R.string.card_security_code_placeholder_cvv));

        TextInputLayout cardNumberInputLayout = (TextInputLayout) cardNumber.getParent().getParent();
        cardNumberInputLayout.setPlaceholderText(context.getString(R.string.card_number_placeholder_visa_mastercard));
        cardNumber.setFilters( new InputFilter[] { new InputFilter.LengthFilter(FormHelper.getMaxCardNumberLength(PaymentProduct.PaymentProductCodeVisa, context))});

        cardNumber.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.ic_credit_card_black, 0);

        securityCodeInfoTextview.setText(context.getString(R.string.card_security_code_description_cvv));
        securityCodeInfoImageview.setImageResource(R.drawable.cvc_mv);
        if (switchLayout != null) {
            switchLayout.setVisibility(isCardStorageEnabled() ? View.VISIBLE: View.GONE);
        }
    }

    @Override
    public boolean isSecurityCodeValid(EditText cardCVV) {

        if (!TextUtils.isEmpty(cardCVV.getText())) {

            String cvvString = cardCVV.getText().toString().trim();
            if (cvvString.length() == 3 && TextUtils.isDigitsOnly(cvvString)) {
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
        return false;
    }

    @Override
    public String getProductCode() {
        return null;
    }

    @Override
    public boolean isCardStorageEnabled() {
        return true;
    }
}
