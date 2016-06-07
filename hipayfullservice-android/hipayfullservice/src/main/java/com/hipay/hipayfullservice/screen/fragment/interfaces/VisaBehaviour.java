package com.hipay.hipayfullservice.screen.fragment.interfaces;

import android.support.design.widget.TextInputLayout;
import android.text.InputFilter;
import android.view.View;
import android.widget.EditText;

import com.hipay.hipayfullservice.core.models.PaymentProduct;

/**
 * Created by nfillion on 20/05/16.
 */
public class VisaBehaviour implements ICardBehaviour {

    @Override
    public void updateForm(EditText cardNumber, EditText cardCVV, TextInputLayout securityCodeLayout) {

        securityCodeLayout.setVisibility(View.VISIBLE);

        cardCVV.setFilters(new InputFilter[]{new InputFilter.LengthFilter(3)});

        //visa
        //"4111 1111 1111 1111",
        //"4000 0000 0000 0002",
        //"4111 1133 3333 3333",
        //cardNumber.setText("4111111111111111");
        //cardCVV.setText("123");
    }
}
