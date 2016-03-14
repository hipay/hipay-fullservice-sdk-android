package com.hipay.hipayfullservice.core.serialization;

import android.os.Bundle;

import com.hipay.hipayfullservice.core.models.AbstractModel;
import com.hipay.hipayfullservice.core.models.PaymentProduct;
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

    ISerialization serialization;

    protected String getQueryString() {

        return this.getSerialization().getQueryString();
    }

    protected Map<String, String> getSerializedObject() {

        return this.getSerialization().getSerializedRequest();
    }

    protected Bundle getSerializedBundle() {

        return this.getSerialization().getSerializedBundle();
    }

    public AbstractSerializationMapper(AbstractRequest request) {

        this.initSerializing(request);
    }

    public AbstractSerializationMapper(AbstractModel model) {

        this.initSerializing(model);
    }


    private void initSerializing(AbstractModel model) {


        if (model instanceof PaymentProduct) {

            PaymentProduct paymentProduct = (PaymentProduct) model;
            this.setSerialization(new PaymentProduct.PaymentProductSerialization(paymentProduct));
        }
    }

    private void initSerializing(AbstractRequest request) {


        if (request instanceof PaymentPageRequest) {

            PaymentPageRequest paymentPageRequest = (PaymentPageRequest)request;
            this.setSerialization(new PaymentPageRequestSerialization(paymentPageRequest));

        } else if (request instanceof OrderRequest) {

            OrderRequest orderRequest = (OrderRequest)request;
            this.setSerialization(new OrderRequestSerialization(orderRequest));

        } else if (request instanceof CustomerInfoRequest) {

            CustomerInfoRequest customerInfoRequest = (CustomerInfoRequest)request;
            this.setSerialization(new CustomerInfoRequestSerialization(customerInfoRequest));

        } else if (request instanceof PersonalInfoRequest) {

            PersonalInfoRequest personalInfoRequest = (PersonalInfoRequest)request;
            this.setSerialization(new PersonalInfoRequestSerialization(personalInfoRequest));

        } else if (request instanceof SecureVaultRequest) {

            SecureVaultRequest secureVaultRequest = (SecureVaultRequest)request;
            this.setSerialization(new SecureVaultRequestSerialization(secureVaultRequest));

        } else if (request instanceof CardTokenPaymentMethodRequest) {

            CardTokenPaymentMethodRequest cardTokenPaymentMethodRequest = (CardTokenPaymentMethodRequest)request;
            this.setSerialization(new CardTokenPaymentMethodRequestSerialization(cardTokenPaymentMethodRequest));
        }
    }

    public ISerialization getSerialization() {
        return serialization;
    }

    public void setSerialization(ISerialization serialization) {
        this.serialization = serialization;
    }

}
