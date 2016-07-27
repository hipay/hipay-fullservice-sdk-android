package com.hipay.hipayfullservice.screen.fragment.interfaces;

import android.content.Context;
import android.support.design.widget.TextInputLayout;
import android.text.InputFilter;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.hipay.hipayfullservice.R;
import com.hipay.hipayfullservice.core.models.PaymentProduct;
import com.hipay.hipayfullservice.screen.helper.FormHelper;

/**
 * Created by nfillion on 20/05/16.
 */
public class MaestroBehaviour implements ICardBehaviour {

    @Override
    public void updateForm(EditText cardNumber, EditText cardCVV, EditText cardExpiry, TextInputLayout securityCodeLayout, TextView securityCodeInfoTextview, ImageView securityCodeInfoImageview, boolean networked, Context context) {

        cardCVV.setFilters(new InputFilter[]{new InputFilter.LengthFilter(3)});
        securityCodeLayout.setVisibility(View.GONE);

        cardNumber.setHint(context.getString(R.string.card_number_placeholder_maestro_bcmc));
        cardNumber.setCompoundDrawablesWithIntrinsicBounds(0, 0, networked? R.drawable.ic_credit_card_bcmc:R.drawable.ic_credit_card_maestro, 0);
        cardExpiry.setImeOptions(EditorInfo.IME_ACTION_DONE);

        securityCodeInfoTextview.setText(context.getString(R.string.card_security_code_description_cvv));
        securityCodeInfoImageview.setImageResource(R.drawable.cvc_mv);

        cardNumber.setFilters( new InputFilter[] { new InputFilter.LengthFilter(FormHelper.getMaxCardNumberLength(PaymentProduct.PaymentProductCodeMaestro, context))});
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

    @Override
    public boolean hasSpaceAtIndex(Integer index, Context context) {

        return FormHelper.isIndexSpace(index, PaymentProduct.PaymentProductCodeMaestro, context);
    }

    @Override
    public String getProductCode() {
        return PaymentProduct.PaymentProductCodeMaestro;
    }
}
