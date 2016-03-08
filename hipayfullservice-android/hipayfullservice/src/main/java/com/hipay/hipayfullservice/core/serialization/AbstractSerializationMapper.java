package com.hipay.hipayfullservice.core.serialization;

import com.hipay.hipayfullservice.core.requests.AbstractRequest;
import com.hipay.hipayfullservice.core.requests.info.CustomerInfoRequest;
import com.hipay.hipayfullservice.core.requests.info.PersonalInfoRequest;
import com.hipay.hipayfullservice.core.requests.order.OrderRequest;
import com.hipay.hipayfullservice.core.requests.order.PaymentPageRequest;
import com.hipay.hipayfullservice.core.requests.payment.CardTokenPaymentMethodRequest;
import com.hipay.hipayfullservice.core.requests.securevault.SecureVaultRequest;
import com.hipay.hipayfullservice.core.serialization.interfaces.ISerialization;
import com.hipay.hipayfullservice.core.serialization.interfaces.info.CustomerInfoRequestSerialization;
import com.hipay.hipayfullservice.core.serialization.interfaces.info.PersonalInfoRequestSerialization;
import com.hipay.hipayfullservice.core.serialization.interfaces.order.OrderRequestSerialization;
import com.hipay.hipayfullservice.core.serialization.interfaces.order.PaymentPageRequestSerialization;
import com.hipay.hipayfullservice.core.serialization.interfaces.payment.CardTokenPaymentMethodRequestSerialization;
import com.hipay.hipayfullservice.core.serialization.interfaces.securevault.SecureVaultRequestSerialization;

import java.util.Map;

/**
 * Created by nfillion on 04/02/16.
 */
public abstract class AbstractSerializationMapper {

    protected AbstractRequest request;
    ISerialization serialization;

    protected String getQueryString() {

        return this.getSerialization().getQueryString();
    }

    protected Map<String, String> getSerializedObject() {

        return this.getSerialization().getSerializedRequest();
    }

    public AbstractSerializationMapper(AbstractRequest request) {

        this.initSerializing(request);
    }

    private void initSerializing(AbstractRequest request) {

        this.setRequest(request);

        if (this.getRequest() instanceof PaymentPageRequest) {

            PaymentPageRequest paymentPageRequest = (PaymentPageRequest)this.getRequest();
            this.setSerialization(new PaymentPageRequestSerialization(paymentPageRequest));

        } else if (this.getRequest() instanceof OrderRequest) {

            OrderRequest orderRequest = (OrderRequest)this.getRequest();
            this.setSerialization(new OrderRequestSerialization(orderRequest));

        } else if (this.getRequest() instanceof CustomerInfoRequest) {

            CustomerInfoRequest customerInfoRequest = (CustomerInfoRequest)this.getRequest();
            this.setSerialization(new CustomerInfoRequestSerialization(customerInfoRequest));

        } else if (this.getRequest() instanceof PersonalInfoRequest) {

            PersonalInfoRequest personalInfoRequest = (PersonalInfoRequest)this.getRequest();
            this.setSerialization(new PersonalInfoRequestSerialization(personalInfoRequest));

        } else if (this.getRequest() instanceof SecureVaultRequest) {

            SecureVaultRequest secureVaultRequest = (SecureVaultRequest)this.getRequest();
            this.setSerialization(new SecureVaultRequestSerialization(secureVaultRequest));

        } else if (this.getRequest() instanceof CardTokenPaymentMethodRequest) {

            CardTokenPaymentMethodRequest cardTokenPaymentMethodRequest = (CardTokenPaymentMethodRequest)this.getRequest();
            this.setSerialization(new CardTokenPaymentMethodRequestSerialization(cardTokenPaymentMethodRequest));
        }
    }

    public ISerialization getSerialization() {
        return serialization;
    }

    public void setSerialization(ISerialization serialization) {
        this.serialization = serialization;
    }

    public AbstractRequest getRequest() {
        return request;
    }

    public void setRequest(AbstractRequest request) {
        this.request = request;
    }

}
