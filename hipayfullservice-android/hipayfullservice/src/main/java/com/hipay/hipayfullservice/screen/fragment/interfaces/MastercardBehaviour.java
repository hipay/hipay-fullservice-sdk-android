package com.hipay.hipayfullservice.screen.fragment.interfaces;

import android.support.design.widget.TextInputLayout;
import android.text.InputFilter;
import android.view.View;
import android.widget.EditText;

/**
 * Created by nfillion on 20/05/16.
 */
public class MastercardBehaviour implements ICardBehaviour {

    @Override
    public void updateForm(EditText cardNumber, EditText cardCVV, TextInputLayout securityCodeLayout) {

        securityCodeLayout.setVisibility(View.VISIBLE);

        cardCVV.setFilters(new InputFilter[]{new InputFilter.LengthFilter(3)});

        //Mastercard
        //"5399 9999 9999 9999",
        //cardNumber.setText("5399999999999999");
        //cardCVV.setText("123");
    }
}
