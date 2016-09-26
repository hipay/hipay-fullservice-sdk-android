package com.hipay.fullservice.core.mapper;

import com.hipay.fullservice.core.mapper.interfaces.MapMapper;
import com.hipay.fullservice.core.models.PersonalInformation;

/**
 * Created by nfillion on 08/09/16.
 */

public abstract class PersonalInformationMapper extends AbstractMapper {
    public PersonalInformationMapper(Object rawData) {
        super(rawData);
    }

    @Override
    protected boolean isValid() {

        if (this.getBehaviour() instanceof MapMapper) {

            if (this.getStringForKey("email") != null) return true;
        }

        return false;
    }

    protected Object mappedObject() {

        PersonalInformation object = new PersonalInformation();

        object.setFirstname(this.getStringForKey("firstname"));
        object.setLastname(this.getStringForKey("lastname"));
        object.setStreetAddress(this.getStringForKey("streetAddress"));
        object.setLocality(this.getStringForKey("streetLocality"));
        object.setPostalCode(this.getStringForKey("postalCode"));
        object.setCountry(this.getStringForKey("country"));
        object.setMsisdn(this.getStringForKey("msisdn"));
        object.setPhone(this.getStringForKey("phone"));
        object.setPhoneOperator(this.getStringForKey("phoneOperator"));
        object.setEmail(this.getStringForKey("email"));

        return object;

    }

    @Override
    protected Object mappedObjectFromBundle() {
        return null;
    }
}


