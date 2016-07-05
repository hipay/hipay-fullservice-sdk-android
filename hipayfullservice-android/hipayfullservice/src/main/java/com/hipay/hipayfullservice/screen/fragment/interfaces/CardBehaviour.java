package com.hipay.hipayfullservice.screen.fragment.interfaces;

import android.content.Context;
import android.support.design.widget.TextInputLayout;
import android.widget.EditText;

import com.hipay.hipayfullservice.core.models.PaymentProduct;

/**
 * Created by nfillion on 22/05/16.
 */

public class CardBehaviour {

    private ICardBehaviour cardBehaviour;

    public CardBehaviour(PaymentProduct paymentProduct) {

        this.updatePaymentProduct(paymentProduct.getCode());
    }

    public void updatePaymentProduct(String paymentProductCode) {

        //String productCode = paymentProduct.getCode();

        paymentProductCode = paymentProductCode.replace("_", "-");

        if (paymentProductCode.equals(PaymentProduct.PaymentProductCodeBCMC)) {

            this.setCardBehaviour(new BCMCBehaviour());

        } else if (paymentProductCode.equals(PaymentProduct.PaymentProductCodeMaestro)) {

            this.setCardBehaviour(new MaestroBehaviour());

        } else if (paymentProductCode.equals(PaymentProduct.PaymentProductCodeAmericanExpress)) {

            this.setCardBehaviour(new AmexBehaviour());

        } else if (paymentProductCode.equals(PaymentProduct.PaymentProductCodeVisa) ||
                paymentProductCode.equals(PaymentProduct.PaymentProductCodeCB)) {

            this.setCardBehaviour(new VisaBehaviour());

        } else if (paymentProductCode.equals(PaymentProduct.PaymentProductCodeMasterCard)) {

            this.setCardBehaviour(new MastercardBehaviour());

        } else if (paymentProductCode.equals(PaymentProduct.PaymentProductCodeDiners)) {

            this.setCardBehaviour(new DinersBehaviour());

        } else  {

            this.setCardBehaviour(new DefaultBehaviour());
        }
    }

    public ICardBehaviour getCardBehaviour() {
        return cardBehaviour;
    }

    public void setCardBehaviour(ICardBehaviour cardBehaviour) {
        this.cardBehaviour = cardBehaviour;
    }

    public void updateForm(EditText cardNumber, EditText cardCVV, EditText cardExpiry, TextInputLayout securityCodeLayout, Context context) {

        this.getCardBehaviour().updateForm(cardNumber, cardCVV, cardExpiry, securityCodeLayout, context);
    }

    public boolean isSecurityCodeValid(EditText cardCVV) {

        return this.getCardBehaviour().isSecurityCodeValid(cardCVV);
    }

    public boolean hasSecurityCode() {

        return this.getCardBehaviour().hasSecurityCode();
    }

    public boolean hasSpaceAtIndex(Integer index, Context context) {
        return this.getCardBehaviour().hasSpaceAtIndex(index, context);
    }
}
