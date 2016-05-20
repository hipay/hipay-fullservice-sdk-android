package com.hipay.hipayfullservice.screen.fragment.interfaces;

import android.support.design.widget.TextInputLayout;
import android.text.InputFilter;
import android.view.View;
import android.widget.EditText;

import com.hipay.hipayfullservice.core.models.PaymentProduct;

/**
 * Created by nfillion on 20/05/16.
 */
public class AmexBehaviour implements ICardBehaviour {

    @Override
    public void updateForm(EditText cardNumber, EditText cardCVV, TextInputLayout securityCodeLayout) {

        securityCodeLayout.setVisibility(View.VISIBLE);

        //card_cvv
        //security code is 4 digits long for amex
        cardCVV.setFilters(new InputFilter[]{new InputFilter.LengthFilter(4)});

        //American Express
        //"3712 4493 1714 724",
        //cardNumber.setText("371244931714724");
        //cardCVV.setText("1234");
    }
}
