package com.hipay.hipayfullservice.screen.fragment.interfaces;

import android.content.Context;
import android.support.design.widget.TextInputLayout;
import android.text.InputFilter;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;

import com.hipay.hipayfullservice.R;
import com.hipay.hipayfullservice.core.models.PaymentProduct;

/**
 * Created by nfillion on 20/05/16.
 */
public class AmexBehaviour implements ICardBehaviour {

    @Override
    public void updateForm(EditText cardNumber, EditText cardCVV, EditText cardExpiry, TextInputLayout securityCodeLayout, Context context) {

        securityCodeLayout.setVisibility(View.VISIBLE);

        //card_cvv
        //security code is 4 digits long for amex
        cardCVV.setFilters(new InputFilter[]{new InputFilter.LengthFilter(4)});

        cardCVV.setHint(context.getString(R.string.card_security_code_placeholder_cid));
        cardExpiry.setImeOptions(EditorInfo.IME_ACTION_NEXT);

        //cardNumber.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_credit_card_amex, 0);
        cardNumber.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_credit_card_black, 0);

        //<string name="card_security_code_placeholder_cvv" translatable="false">123</string>

        //American Express
        //"3712 4493 1714 724",
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
}
