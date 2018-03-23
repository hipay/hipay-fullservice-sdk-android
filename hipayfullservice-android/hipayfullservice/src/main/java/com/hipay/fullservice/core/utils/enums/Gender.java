package com.hipay.fullservice.core.utils.enums;

/**
 * Created by nfillion on 23/03/2018.
 */
public enum Gender {

    UNDEFINED(' '),
    UNKNOWN('U'),
    MALE('M'),
    FEMALE('F');

    protected final char gender;

    Gender(char gender) {
        this.gender = gender;
    }

    public static Gender fromStringValue(String value) {
        if (value != null) {
            for (Gender state : Gender.values()) {
                if (state.getValue() == value.charAt(0)) {
                    return state;
                }
            }
        }
        return null;
    }

    public char getValue() {

        return this.gender;
    }
}
