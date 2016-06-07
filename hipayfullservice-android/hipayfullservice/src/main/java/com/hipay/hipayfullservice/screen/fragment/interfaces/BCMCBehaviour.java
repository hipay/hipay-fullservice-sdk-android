package com.hipay.hipayfullservice.screen.fragment.interfaces;

import android.support.design.widget.TextInputLayout;
import android.text.InputFilter;
import android.view.View;
import android.widget.EditText;

/**
 * Created by nfillion on 20/05/16.
 */
public class BCMCBehaviour implements ICardBehaviour {

    @Override
    public void updateForm(EditText cardNumber, EditText cardCVV, TextInputLayout securityCodeLayout) {

        cardCVV.setFilters(new InputFilter[]{new InputFilter.LengthFilter(3)});
        //hide the security code for bcmc
        securityCodeLayout.setVisibility(View.GONE);

        //bcmc
        //"6703 0000 0000 00003",
        //cardNumber.setText("67030000000000003");
        //cardCVV.setText("123");

    }
}
