package com.hipay.fullservice.core.mapper;

import com.hipay.fullservice.core.mapper.interfaces.MapMapper;
import com.hipay.fullservice.core.requests.info.CustomerInfoRequest;
import com.hipay.fullservice.core.requests.info.PersonalInfoRequest;
import com.hipay.fullservice.core.utils.enums.Gender;

/**
 * Created by nfillion on 22/03/2018.
 */
public class CustomerInfoRequestMapper extends PersonalInfoRequestMapper {
    public CustomerInfoRequestMapper(Object rawData) {
        super(rawData);
    }

    @Override
    public boolean isValid() {
        return getBehaviour() instanceof MapMapper;
    }

    @Override
    public CustomerInfoRequest mappedObject() {
        PersonalInfoRequest personalInfoRequest = super.mappedObject();
        CustomerInfoRequest object = this.customerFromPersonalRequest(personalInfoRequest);

        object.setEmail(this.getStringForKey("email"));
        object.setPhone(this.getStringForKey("phone"));
        object.setBirthDateDay(this.getIntegerForKey("birthDateDay"));
        object.setBirthDateMonth(this.getIntegerForKey("birthDateMonth"));
        object.setBirthDateYear(this.getIntegerForKey("birthDateYear"));

        String genderChar = this.getEnumCharForKey("gender");
        Gender gender = Gender.fromStringValue(genderChar);
        if (gender == null) {
            gender = Gender.UNKNOWN;
        }
        object.setGender(gender);

        return object;
    }

    private CustomerInfoRequest customerFromPersonalRequest(PersonalInfoRequest personalInfoRequest) {

        CustomerInfoRequest customerInfoRequest = new CustomerInfoRequest();

        customerInfoRequest.setFirstname(personalInfoRequest.getFirstname());
        customerInfoRequest.setLastname(personalInfoRequest.getLastname());
        customerInfoRequest.setStreetAddress(personalInfoRequest.getStreetAddress());
        customerInfoRequest.setStreetAddress2(personalInfoRequest.getStreetAddress2());
        customerInfoRequest.setRecipientInfo(personalInfoRequest.getRecipientInfo());
        customerInfoRequest.setCity(personalInfoRequest.getCity());
        customerInfoRequest.setState(personalInfoRequest.getState());
        customerInfoRequest.setZipCode(personalInfoRequest.getZipCode());
        customerInfoRequest.setCountry(personalInfoRequest.getCountry());

        return customerInfoRequest;
    }
}
