package com.hipay.hipayfullservice.screen.fragment.interfaces;

import android.content.Context;
import android.support.design.widget.TextInputLayout;
import android.text.InputFilter;
import android.text.TextUtils;
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
public class VisaBehaviour implements ICardBehaviour {

    @Override
    public void updateForm(EditText cardNumber, EditText cardCVV, EditText cardExpiry, TextInputLayout securityCodeLayout, TextView securityCodeInfoTextview, ImageView securityCodeInfoImageview, boolean networked, Context context) {

        securityCodeLayout.setVisibility(View.VISIBLE);
        cardNumber.setHint(context.getString(R.string.card_number_placeholder_visa_mastercard));

        cardExpiry.setImeOptions(EditorInfo.IME_ACTION_NEXT);
        cardCVV.setFilters(new InputFilter[]{new InputFilter.LengthFilter(3)});
        cardCVV.setHint(context.getString(R.string.card_security_code_placeholder_cvv));

        cardNumber.setFilters( new InputFilter[] { new InputFilter.LengthFilter(FormHelper.getMaxCardNumberLength(PaymentProduct.PaymentProductCodeVisa, context))});
        cardNumber.setCompoundDrawablesWithIntrinsicBounds(0, 0, networked?R.drawable.ic_credit_card_cb:R.drawable.ic_credit_card_visa, 0);

        securityCodeInfoTextview.setText(context.getString(R.string.card_security_code_description_cvv));
        securityCodeInfoImageview.setImageResource(R.drawable.cvc_mv);

        //visa
        //"4111 1111 1111 1111",
        //"4000 0000 0000 0002",
        //"4111 1133 3333 3333",
        //cardNumber.setText("4111111111111111");
        //cardCVV.setText("123");
    }

    @Override
    public boolean isSecurityCodeValid(EditText cardCVV) {

        if (!TextUtils.isEmpty(cardCVV.getText())) {

            String cvvString = cardCVV.getText().toString().trim();
            if (cvvString.length() == 3 && TextUtils.isDigitsOnly(cvvString)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean hasSecurityCode() {
        return true;
    }

    @Override
    public boolean hasSpaceAtIndex(Integer index, Context context) {

        return FormHelper.isIndexSpace(index, PaymentProduct.PaymentProductCodeVisa, context);
    }

    @Override
    public String getProductCode() {
        return PaymentProduct.PaymentProductCodeVisa;
    }
}
