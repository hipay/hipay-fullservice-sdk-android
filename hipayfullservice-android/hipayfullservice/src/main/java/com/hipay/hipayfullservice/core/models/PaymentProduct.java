package com.hipay.hipayfullservice.core.models;

import android.os.Bundle;

import com.hipay.hipayfullservice.core.mapper.AbstractMapper;
import com.hipay.hipayfullservice.core.mapper.interfaces.BundleMapper;
import com.hipay.hipayfullservice.core.mapper.interfaces.MapMapper;
import com.hipay.hipayfullservice.core.serialization.AbstractSerializationMapper;
import com.hipay.hipayfullservice.core.serialization.interfaces.AbstractSerialization;

import org.json.JSONObject;

import java.util.Map;
import java.util.Set;

/**
 * Created by nfillion on 25/01/16.
 */
public class PaymentProduct extends AbstractModel {

    public static final String TAG = "Payment_product";

    protected String code;
    protected String paymentProductId;
    protected String paymentProductDescription;
    protected String paymentProductCategoryCode;
    protected Boolean tokenizable;
    protected Set<String> groupedPaymentProductCodes;

    public PaymentProduct(String code) {
        this.code = code;
    }

    public PaymentProduct(Set<String> groupedPaymentProductCodes) {
        this.groupedPaymentProductCodes = groupedPaymentProductCodes;

        //TODO get payment card from resources, need context.
        //this.paymentProductDescription = res.getString(R.string.payment_product_group_payment_card);
        this.paymentProductCategoryCode = PaymentProductCategoryCodeCreditCard;
    }

    public PaymentProduct() {

    }

    public static PaymentProduct fromJSONObject(JSONObject object) {

        PaymentProductMapper mapper = new PaymentProductMapper(object);
        return mapper.mappedObject();
    }
    public static PaymentProduct fromBundle(Bundle bundle) {

        PaymentProductMapper mapper = new PaymentProductMapper(bundle);
        return mapper.mappedObjectFromBundle();
    }

    public Bundle toBundle() {

        PaymentProduct.PaymentProductSerializationMapper mapper = new PaymentProduct.PaymentProductSerializationMapper(this);
        return mapper.getSerializedBundle();
    }

    public static SecurityCodeType securityCodeTypeForPaymentProductCode(String paymentProductCode) {

        if (    paymentProductCode.equalsIgnoreCase(PaymentProductCodeVisa) ||
                paymentProductCode.equalsIgnoreCase(PaymentProductCodeMasterCard) ||
                paymentProductCode.equalsIgnoreCase(PaymentProductCodeDiners)
                ) {

            return SecurityCodeType.SecurityCodeTypeCVV;

        } else if (paymentProductCode.equalsIgnoreCase(PaymentProductCodeAmericanExpress)) {

            return SecurityCodeType.SecurityCodeTypeCID;

        } else if ( paymentProductCode.equalsIgnoreCase(PaymentProductCodeBCMC) ||
                    paymentProductCode.equalsIgnoreCase(PaymentProductCodeMaestro)
                ) {

            return SecurityCodeType.SecurityCodeTypeNone;
        }

        return SecurityCodeType.SecurityCodeTypeNotApplicable;
    }

    public boolean isPaymentProductCodeDomesticNetworkOfPaymentProductCode(String domesticPaymentProductCode, String paymentProductCode) {

        if (domesticPaymentProductCode.equalsIgnoreCase(PaymentProductCodeBCMC)) {
            return paymentProductCode.equalsIgnoreCase(PaymentProductCodeMaestro);
        }

        if (domesticPaymentProductCode.equalsIgnoreCase(PaymentProductCodeCB)) {
            return  paymentProductCode.equalsIgnoreCase(PaymentProductCodeMasterCard) ||
                    paymentProductCode.equalsIgnoreCase(PaymentProductCodeVisa);
        }

        return false;
    }

    protected boolean isEqualToPaymentProduct(Object object) {

        if (!(object instanceof PaymentProduct)) {
            return false;

        } else {
            PaymentProduct paymentProduct = (PaymentProduct)object;
            return this.code.equals(paymentProduct.code);
        }
    }

    protected boolean isEqual(Object object) {

        return this.isEqualToPaymentProduct(object);
    }


    public enum SecurityCodeType {

        // Ex. : Maestro
        SecurityCodeTypeNone (0),

        // Ex. : BCMC (for domestic networks or specific issuer payment products, we don't know if there's a security code as it depends on the card scheme)
        SecurityCodeTypeNotApplicable (1),

        // Ex. : Visa, MasterCard
        SecurityCodeTypeCVV (2),

        // Ex. : American Express
        SecurityCodeTypeCID (3);


        protected final Integer status;
        SecurityCodeType(Integer status) {
            this.status = status;
        }

        public Integer getIntegerValue() {
            return this.status;
        }
    }

    //TODO tranform to ENUM type

    public final static String PaymentProductCode3xcb                      = "3xcb";
    public final static String PaymentProductCode3xcbNoFees                = "3xcb-no-fees";
    public final static String PaymentProductCode4xcb                      = "4xcb";
    public final static String PaymentProductCode4xcbNoFees                = "4xcb-no-fees";
    public final static String PaymentProductCodeAmericanExpress           = "american-express";
    public final static String PaymentProductCodeArgencard                 = "argencard";
    public final static String PaymentProductCodeBaloto                    = "baloto";
    public final static String PaymentProductCodeBankTransfer              = "bank-transfer";
    public final static String PaymentProductCodeBCMC                      = "bcmc";
    public final static String PaymentProductCodeBCMCMobile                = "bcmc-mobile";
    public final static String PaymentProductCodeBCP                       = "bcp";
    public final static String PaymentProductCodeBitcoin                   = "bitcoin";
    public final static String PaymentProductCodeCabal                     = "cabal";
    public final static String PaymentProductCodeCarteAccord               = "carte-accord";
    public final static String PaymentProductCodeCB                        = "cb";
    public final static String PaymentProductCodeCBCOnline                 = "cbc-online";
    public final static String PaymentProductCodeCensosud                  = "censosud";
    public final static String PaymentProductCodeCobroExpress              = "cobro-express";
    public final static String PaymentProductCodeCofinoga                  = "cofinoga";
    public final static String PaymentProductCodeDexiaDirectNet            = "dexia-directnet";
    public final static String PaymentProductCodeDiners                    = "diners";
    public final static String PaymentProductCodeEfecty                    = "efecty";
    public final static String PaymentProductCodeElo                       = "elo";
    public final static String PaymentProductCodeGiropay                   = "giropay";
    public final static String PaymentProductCodeIDEAL                     = "ideal";
    public final static String PaymentProductCodeINGHomepay                = "ing-homepay";
    public final static String PaymentProductCodeIxe                       = "ixe";
    public final static String PaymentProductCodeKBCOnline                 = "kbc-online";
    public final static String PaymentProductCodeKlarnacheckout            = "klarnacheckout";
    public final static String PaymentProductCodeKlarnaInvoice             = "klarnainvoice";
    public final static String PaymentProductCodeMaestro                   = "maestro";
    public final static String PaymentProductCodeMasterCard                = "mastercard";
    public final static String PaymentProductCodeNaranja                   = "naranja";
    public final static String PaymentProductCodePagoFacil                 = "pago-facil";
    public final static String PaymentProductCodePayPal                    = "paypal";
    public final static String PaymentProductCodePaysafecard               = "paysafecard";
    public final static String PaymentProductCodePayULatam                 = "payulatam";
    public final static String PaymentProductCodeProvincia                 = "provincia";
    public final static String PaymentProductCodePrzelewy24                = "przelewy24";
    public final static String PaymentProductCodeQiwiWallet                = "qiwi-wallet";
    public final static String PaymentProductCodeRapipago                  = "rapipago";
    public final static String PaymentProductCodeRipsa                     = "ripsa";
    public final static String PaymentProductCodeSDD                       = "sdd";
    public final static String PaymentProductCodeSisal                     = "sisal";
    public final static String PaymentProductCodeSofortUberweisung         = "sofort-uberweisung";
    public final static String PaymentProductCodeTarjetaShopping           = "tarjeta-shopping";
    public final static String PaymentProductCodeVisa                      = "visa";
    public final static String PaymentProductCodeWebmoneyTransfer          = "webmoney-transfer";
    public final static String PaymentProductCodeYandex                    = "yandex";
    public final static String PaymentProductCodeAura                      = "aura";
    public final static String PaymentProductCodeBanamex                   = "banamex";
    public final static String PaymentProductCodeBancoDoBrasil             = "banco-do-brasil";
    public final static String PaymentProductCodeBBVABancomer              = "bbva-bancomer";
    public final static String PaymentProductCodeBoletoBancario            = "boleto-bancario";
    public final static String PaymentProductCodeBradesco                  = "bradesco";
    public final static String PaymentProductCodeCaixa                     = "caixa";
    public final static String PaymentProductCodeDiscover                  = "discover";
    public final static String PaymentProductCodeItau                      = "itau";
    public final static String PaymentProductCodeOxxo                      = "oxxo";
    public final static String PaymentProductCodeSantanderCash             = "santander-cash";
    public final static String PaymentProductCodeSantanderHomeBanking      = "santander-home-banking";

    public final static String PaymentProductCategoryCodeCreditCard        = "credit-card";
    public final static String PaymentProductCategoryCodeCard              = "card";
    public final static String PaymentProductCategoryCodeDebitCard         = "debit-card";
    public final static String PaymentProductCategoryCodeRealtimeBanking   = "realtime-banking";
    public final static String PaymentProductCategoryCodeEWallet           = "ewallet";

    public Set<String> getGroupedPaymentProductCodes() {
        return groupedPaymentProductCodes;
    }

    public void setGroupedPaymentProductCodes(Set<String> groupedPaymentProductCodes) {
        this.groupedPaymentProductCodes = groupedPaymentProductCodes;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getPaymentProductId() {
        return paymentProductId;
    }

    public void setPaymentProductId(String paymentProductId) {
        this.paymentProductId = paymentProductId;
    }

    public String getPaymentProductDescription() {
        return paymentProductDescription;
    }

    public void setPaymentProductDescription(String paymentProductDescription) {
        this.paymentProductDescription = paymentProductDescription;
    }

    public String getPaymentProductCategoryCode() {
        return paymentProductCategoryCode;
    }

    public void setPaymentProductCategoryCode(String paymentProductCategoryCode) {
        this.paymentProductCategoryCode = paymentProductCategoryCode;
    }

    public Boolean isTokenizable() {
        return tokenizable;
    }

    public void setTokenizable(Boolean tokenizable) {
        this.tokenizable = tokenizable;
    }

    protected static class PaymentProductSerializationMapper extends AbstractSerializationMapper {

        protected PaymentProductSerializationMapper(PaymentProduct paymentProduct) {
            super(paymentProduct);
        }

        @Override
        protected String getQueryString() {

            return super.getQueryString();
        }

        @Override
        protected Bundle getSerializedBundle() {

            return super.getSerializedBundle();
        }
    }

    public static class PaymentProductMapper extends AbstractMapper {

        public PaymentProductMapper(Object rawData) {
            super(rawData);
        }

        @Override
        protected boolean isValid() {

            if (this.getBehaviour() instanceof MapMapper) {

                if (this.getStringForKey("code") != null) return true;

            } else if (getBehaviour() instanceof BundleMapper) {

                return true;
            }

            return true;
        }

        protected PaymentProduct mappedObject() {

            PaymentProduct object = new PaymentProduct();

            object.setPaymentProductId(this.getStringForKey("id"));
            object.setCode(this.getStringForKey("code"));
            object.setPaymentProductDescription(this.getStringForKey("description"));
            object.setPaymentProductCategoryCode(this.getStringForKey("payment_product_category_code"));
            object.setTokenizable(this.getBoolForKey("tokenizable"));

            return object;
        }

        @Override
        protected PaymentProduct mappedObjectFromBundle() {

            return this.mappedObject();
        }
    }


    public static class PaymentProductSerialization extends AbstractSerialization {

        public PaymentProductSerialization(PaymentProduct paymentProduct) {
            super(paymentProduct);
        }

        @Override
        public Map<String, String> getSerializedRequest() {
            return null;
        }

        @Override
        public Bundle getSerializedBundle() {
            super.getSerializedBundle();

            PaymentProduct paymentProduct = (PaymentProduct)this.getModel();

            this.putStringForKey("id", paymentProduct.getPaymentProductId());
            this.putStringForKey("code", paymentProduct.getCode());
            this.putStringForKey("description", paymentProduct.getPaymentProductDescription());
            this.putStringForKey("payment_product_category_code", paymentProduct.getPaymentProductCategoryCode());
            this.putBoolForKey("tokenizable", paymentProduct.isTokenizable());

            return this.getBundle();
        }

        @Override
        public String getQueryString() {
            return null;
        }
    }
}