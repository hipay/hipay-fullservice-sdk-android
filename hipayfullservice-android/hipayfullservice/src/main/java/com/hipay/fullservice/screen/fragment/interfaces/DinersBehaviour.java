package com.hipay.fullservice.screen.fragment.interfaces;

import android.content.Context;
import android.support.design.widget.TextInputLayout;
import android.text.InputFilter;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.hipay.fullservice.R;
import com.hipay.fullservice.core.models.PaymentProduct;
import com.hipay.fullservice.screen.helper.FormHelper;

/**
 * Created by nfillion on 20/05/16.
 */
public class DinersBehaviour implements ICardBehaviour {

    @Override
    public void updateForm(EditText cardNumber, EditText cardCVV, EditText cardExpiry, TextInputLayout securityCodeLayout, TextView securityCodeInfoTextview, ImageView securityCodeInfoImageview, boolean networked, Context context) {

        securityCodeLayout.setVisibility(View.VISIBLE);

        cardCVV.setFilters(new InputFilter[]{new InputFilter.LengthFilter(3)});

        cardExpiry.setImeOptions(EditorInfo.IME_ACTION_NEXT);
        cardCVV.setHint(context.getString(R.string.card_security_code_placeholder_cvv));
        cardNumber.setHint(context.getString(R.string.card_number_placeholder_diners));

        cardNumber.setFilters( new InputFilter[] { new InputFilter.LengthFilter(FormHelper.getMaxCardNumberLength(PaymentProduct.PaymentProductCodeDiners, context))});
        cardNumber.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_credit_card_diners, 0);

        securityCodeInfoTextview.setText(context.getString(R.string.card_security_code_description_cvv));
        securityCodeInfoImageview.setImageResource(R.drawable.cvc_mv);

        //no test card
        //Diners Club International
        //"3608 2634 5678 90" does not work
        //"3056 9309 0259 04"
        //"3852 0000 0232 37"
        //cardNumber.setText("38520000023237");
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

        return FormHelper.isIndexSpace(index, PaymentProduct.PaymentProductCodeDiners, context);
    }

    @Override
    public String getProductCode() {
        return PaymentProduct.PaymentProductCodeDiners;
    }
}
