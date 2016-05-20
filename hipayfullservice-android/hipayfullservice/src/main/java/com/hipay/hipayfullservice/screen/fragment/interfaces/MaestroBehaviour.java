package com.hipay.hipayfullservice.screen.fragment.interfaces;

import android.content.Context;
import android.support.design.widget.TextInputLayout;
import android.text.InputFilter;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;

import com.hipay.hipayfullservice.R;

/**
 * Created by nfillion on 20/05/16.
 */
public class MaestroBehaviour implements ICardBehaviour {

    @Override
    public void updateForm(EditText cardNumber, EditText cardCVV, EditText cardExpiry, TextInputLayout securityCodeLayout, Context context) {

        cardCVV.setFilters(new InputFilter[]{new InputFilter.LengthFilter(3)});
        securityCodeLayout.setVisibility(View.GONE);

        cardNumber.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_credit_card_maestro, 0);
        cardExpiry.setImeOptions(EditorInfo.IME_ACTION_DONE);
        cardCVV.setHint(context.getString(R.string.card_security_code_placeholder_cvv));
        //Maestro
        //"6759 4111 0000 0008",
        //cardCVV.setText("123");
        //cardNumber.setText("6759411100000008");
    }

    @Override
    public boolean isSecurityCodeValid(EditText cardCVV) {
        return true;
    }

    @Override
    public boolean hasSecurityCode() {
        return false;
    }
}
