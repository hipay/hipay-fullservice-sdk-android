package com.hipay.fullservice.core.utils.enums;

/**
 * Created by nfillion on 23/03/2018.
 */
public enum ECI {

    UNDEFINED(Integer.MAX_VALUE),
    MOTO(1),
    RECURRING_MOTO(2),
    INSTALLMENT_PAYMENT(3),
    MANUALLY_KEYED_CARD_PRESENT(4),
    SECURE_E_COMMERCE(7),
    RECURRING_E_COMMERCE(9);

    protected final int eci;

    ECI(int eci) {
        this.eci = eci;
    }

    public static ECI fromIntegerValue(Integer value) {
        if (value != null) {
            for (ECI state : ECI.values()) {
                if (state.getValue() == value) {
                    return state;
                }
            }
        }
        return null;
    }

    public Integer getValue() {
        return this.eci;
    }
}
