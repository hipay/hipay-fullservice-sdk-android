package com.hipay.fullservice.core.mapper;

import com.hipay.fullservice.core.models.SepaDirectDebit;

public class SepaDirectDebitMapper extends AbstractMapper {
    public SepaDirectDebitMapper(Object rawData) {
        super(rawData);
    }

    @Override
    protected boolean isValid() {

        if (this.getStringForKey("token") != null) return true;
        return false;
    }

    @Override
    public SepaDirectDebit mappedObject() {
        SepaDirectDebit object = new SepaDirectDebit();

        object.setFirstname(this.getStringForKey("authenticationMessage"));

        return object;
    }

    @Override
    public SepaDirectDebit mappedObjectFromBundle() {
        return null;
    }

    @Override
    protected SepaDirectDebit mappedObjectFromUri() {
        return null;
    }




}
