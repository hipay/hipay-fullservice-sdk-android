package com.hipay.fullservice.core.utils.enums;

/**
 * Created by nfillion on 23/03/2018.
 */
public enum SecurityCodeType {

    // Ex. : Maestro
    SECURITY_CODE_TYPE_NONE(0),

    // Ex. : BCMC (for domestic networks or specific issuer payment products, we don't know if there's a security code as it depends on the card scheme)
    SECURITY_CODE_TYPE_NOT_APPLICABLE(1),

    // Ex. : Visa, MasterCard
    SECURITY_CODE_TYPE_CVV(2),

    // Ex. : American Express
    SECURITY_CODE_TYPE_CID(3);


    protected final Integer status;

    SecurityCodeType(Integer status) {
        this.status = status;
    }

    public Integer getValue() {
        return this.status;
    }
}
