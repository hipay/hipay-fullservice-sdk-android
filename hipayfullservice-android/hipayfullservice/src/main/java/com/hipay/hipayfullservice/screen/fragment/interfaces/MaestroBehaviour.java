package com.hipay.hipayfullservice.screen.fragment.interfaces;

import android.support.design.widget.TextInputLayout;
import android.text.InputFilter;
import android.view.View;
import android.widget.EditText;

/**
 * Created by nfillion on 20/05/16.
 */
public class MaestroBehaviour implements ICardBehaviour {

    @Override
    public void updateForm(EditText cardNumber, EditText cardCVV, TextInputLayout securityCodeLayout) {

        cardCVV.setFilters(new InputFilter[]{new InputFilter.LengthFilter(3)});
        securityCodeLayout.setVisibility(View.GONE);

        //Maestro
        //"6759 4111 0000 0008",
        //cardCVV.setText("123");
        //cardNumber.setText("6759411100000008");
    }
}
