package com.hipay.fullservice.core.mapper;

import com.hipay.fullservice.core.requests.info.PersonalInfoRequest;

/**
 * Created by nfillion on 22/03/2018.
 */

public class PersonalInfoRequestMapper extends AbstractMapper {

    public PersonalInfoRequestMapper(Object rawData) {
        super(rawData);
    }

    @Override
    public boolean isValid() {
        return true;
    }

    public PersonalInfoRequest mappedObject() {

        PersonalInfoRequest object = new PersonalInfoRequest();

        object.setFirstname(this.getStringForKey("firstname"));
        object.setLastname(this.getStringForKey("lastname"));
        object.setStreetAddress(this.getStringForKey("streetaddress"));
        object.setStreetAddress2(this.getStringForKey("streetaddress2"));
        object.setRecipientInfo(this.getStringForKey("recipientinfo"));
        object.setCity(this.getStringForKey("city"));
        object.setState(this.getStringForKey("state"));
        object.setZipCode(this.getStringForKey("zipcode"));
        object.setCountry(this.getStringForKey("country"));

        return object;
    }

}