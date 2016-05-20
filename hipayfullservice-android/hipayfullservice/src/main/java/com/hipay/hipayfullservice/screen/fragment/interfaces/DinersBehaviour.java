package com.hipay.hipayfullservice.screen.fragment.interfaces;

import android.support.design.widget.TextInputLayout;
import android.text.InputFilter;
import android.view.View;
import android.widget.EditText;

/**
 * Created by nfillion on 20/05/16.
 */
public class DinersBehaviour implements ICardBehaviour {

    @Override
    public void updateForm(EditText cardNumber, EditText cardCVV, TextInputLayout securityCodeLayout) {

        securityCodeLayout.setVisibility(View.VISIBLE);

        cardCVV.setFilters(new InputFilter[]{new InputFilter.LengthFilter(3)});

        //no test card
        //Diners Club International
        //"3608 2634 5678 90" does not work
        //"3056 9309 0259 04"
        //"3852 0000 0232 37"
        //cardNumber.setText("38520000023237");
        //cardCVV.setText("123");
    }
}
