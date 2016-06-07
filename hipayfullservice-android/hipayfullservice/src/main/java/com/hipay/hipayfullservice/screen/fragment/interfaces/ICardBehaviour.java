package com.hipay.hipayfullservice.screen.fragment.interfaces;

import android.support.design.widget.TextInputLayout;
import android.widget.EditText;

/**
 * Created by nfillion on 20/05/16.
 */
public interface ICardBehaviour {

    void updateForm(EditText cardNumber, EditText cardCVV, TextInputLayout securityCodeLayout);
}
