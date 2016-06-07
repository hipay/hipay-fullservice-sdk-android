package com.hipay.hipayfullservice.screen.fragment.interfaces;

import android.support.design.widget.TextInputLayout;
import android.widget.EditText;

import com.hipay.hipayfullservice.core.models.PaymentProduct;

/**
 * Created by nfillion on 22/05/16.
 */

public class CardBehaviour {

    private ICardBehaviour cardBehaviour;

    public CardBehaviour(PaymentProduct paymentProduct) {

        this.updatePaymentProduct(paymentProduct);
    }

    public void updatePaymentProduct(PaymentProduct paymentProduct) {

        String productCode = paymentProduct.getCode();

        if (productCode.equals(PaymentProduct.PaymentProductCodeBCMC)) {

            this.setCardBehaviour(new BCMCBehaviour());

        } else if (productCode.equals(PaymentProduct.PaymentProductCodeMaestro)) {

            this.setCardBehaviour(new MaestroBehaviour());

        } else if (productCode.equals(PaymentProduct.PaymentProductCodeAmericanExpress)) {

            this.setCardBehaviour(new AmexBehaviour());

        } else if (productCode.equals(PaymentProduct.PaymentProductCodeVisa) ||
                productCode.equals(PaymentProduct.PaymentProductCodeCB)) {

            this.setCardBehaviour(new VisaBehaviour());

        } else if (productCode.equals(PaymentProduct.PaymentProductCodeMasterCard)) {

            this.setCardBehaviour(new MastercardBehaviour());

        } else if (productCode.equals(PaymentProduct.PaymentProductCodeDiners)) {

            this.setCardBehaviour(new DinersBehaviour());
        }
    }

    public ICardBehaviour getCardBehaviour() {
        return cardBehaviour;
    }

    public void setCardBehaviour(ICardBehaviour cardBehaviour) {
        this.cardBehaviour = cardBehaviour;
    }

    public void updateForm(EditText cardNumber, EditText cardCVV, TextInputLayout securityCodeLayout) {

        this.getCardBehaviour().updateForm(cardNumber, cardCVV, securityCodeLayout);
    }

}
