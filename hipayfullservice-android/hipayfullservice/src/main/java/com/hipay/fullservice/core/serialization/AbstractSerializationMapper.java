package com.hipay.fullservice.core.serialization;

import android.os.Bundle;

import com.hipay.fullservice.core.errors.exceptions.ApiException;
import com.hipay.fullservice.core.errors.exceptions.HttpException;
import com.hipay.fullservice.core.models.AbstractModel;
import com.hipay.fullservice.core.models.FraudScreening;
import com.hipay.fullservice.core.models.Order;
import com.hipay.fullservice.core.models.PaymentCardToken;
import com.hipay.fullservice.core.models.PaymentProduct;
import com.hipay.fullservice.core.models.PersonalInformation;
import com.hipay.fullservice.core.models.ThreeDSecure;
import com.hipay.fullservice.core.models.Transaction;
import com.hipay.fullservice.core.requests.AbstractRequest;
import com.hipay.fullservice.core.requests.info.CustomerInfoRequest;
import com.hipay.fullservice.core.requests.info.PersonalInfoRequest;
import com.hipay.fullservice.core.requests.order.OrderRequest;
import com.hipay.fullservice.core.requests.order.PaymentPageRequest;
import com.hipay.fullservice.core.requests.payment.CardTokenPaymentMethodRequest;
import com.hipay.fullservice.core.requests.securevault.GenerateTokenRequest;
import com.hipay.fullservice.core.requests.securevault.LookupPaymentCardRequest;
import com.hipay.fullservice.core.requests.securevault.UpdatePaymentCardRequest;
import com.hipay.fullservice.core.serialization.interfaces.CustomThemeSerialization;
import com.hipay.fullservice.core.serialization.interfaces.FraudScreeningSerialization;
import com.hipay.fullservice.core.serialization.interfaces.ISerialization;
import com.hipay.fullservice.core.serialization.interfaces.PaymentCardTokenSerialization;
import com.hipay.fullservice.core.serialization.interfaces.PaymentProductSerialization;
import com.hipay.fullservice.core.serialization.interfaces.PersonalInformationSerialization;
import com.hipay.fullservice.core.serialization.interfaces.ThreeDSecureSerialization;
import com.hipay.fullservice.core.serialization.interfaces.TransactionSerialization;
import com.hipay.fullservice.core.serialization.interfaces.order.OrderRequestSerialization;
import com.hipay.fullservice.core.serialization.interfaces.order.OrderSerialization;
import com.hipay.fullservice.core.serialization.interfaces.order.PaymentPageRequestSerialization;
import com.hipay.fullservice.core.serialization.interfaces.payment.CardTokenPaymentMethodRequestSerialization;
import com.hipay.fullservice.core.serialization.interfaces.securevault.LookupPaymentCardRequestSerialization;
import com.hipay.fullservice.core.serialization.interfaces.securevault.SecureVaultRequestSerialization;
import com.hipay.fullservice.core.serialization.interfaces.securevault.UpdatePaymentCardRequestSerialization;
import com.hipay.fullservice.screen.model.CustomTheme;

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

    public AbstractSerializationMapper(Exception exception) {

        this.initSerializing(exception);
    }

    private void initSerializing(Exception exception) {
        if (exception instanceof ApiException) {
            ApiException apiException = (ApiException) exception;
            this.setSerialization(new ApiException.ApiExceptionSerialization(apiException));
        } else if (exception instanceof HttpException) {
            HttpException httpException = (HttpException) exception;
            this.setSerialization(new HttpException.HttpExceptionSerialization(httpException));
        }
    }

    private void initSerializing(AbstractModel model) {

        if (model instanceof PaymentProduct) {

            PaymentProduct paymentProduct = (PaymentProduct) model;
            this.setSerialization(new PaymentProductSerialization(paymentProduct));

        } else if (model instanceof CustomTheme) {

            CustomTheme customTheme = (CustomTheme) model;
            this.setSerialization(new CustomThemeSerialization(customTheme));

        } else if (model instanceof Transaction) {

            Transaction transaction = (Transaction) model;
            this.setSerialization(new TransactionSerialization(transaction));

        } else if (model instanceof ThreeDSecure) {

            ThreeDSecure threeDSecure = (ThreeDSecure) model;
            this.setSerialization(new ThreeDSecureSerialization(threeDSecure));

        } else if (model instanceof FraudScreening) {

            FraudScreening fraudScreening = (FraudScreening) model;
            this.setSerialization(new FraudScreeningSerialization(fraudScreening));

        } else if (model instanceof Order) {

            Order order = (Order) model;
            this.setSerialization(new OrderSerialization(order));

        } else if (model instanceof PersonalInformation) {

            PersonalInformation personalInformation = (PersonalInformation) model;
            this.setSerialization(new PersonalInformationSerialization(personalInformation));

        } else if (model instanceof PaymentCardToken) {

            PaymentCardToken paymentCardToken = (PaymentCardToken) model;
            this.setSerialization(new PaymentCardTokenSerialization(paymentCardToken));
        }
    }

    private void initSerializing(AbstractRequest request) {

        if (request instanceof PaymentPageRequest) {

            PaymentPageRequest paymentPageRequest = (PaymentPageRequest) request;
            this.setSerialization(new PaymentPageRequestSerialization(paymentPageRequest));

        } else if (request instanceof OrderRequest) {

            OrderRequest orderRequest = (OrderRequest) request;
            this.setSerialization(new OrderRequestSerialization(orderRequest));

        } else if (request instanceof CustomerInfoRequest) {

            CustomerInfoRequest customerInfoRequest = (CustomerInfoRequest) request;
            this.setSerialization(new CustomerInfoRequest.CustomerInfoRequestSerialization(customerInfoRequest));

        } else if (request instanceof PersonalInfoRequest) {

            PersonalInfoRequest personalInfoRequest = (PersonalInfoRequest) request;
            this.setSerialization(new PersonalInfoRequest.PersonalInfoRequestSerialization(personalInfoRequest));

        } else if (request instanceof GenerateTokenRequest) {

            GenerateTokenRequest generateTokenRequest = (GenerateTokenRequest) request;
            this.setSerialization(new SecureVaultRequestSerialization(generateTokenRequest));

        } else if (request instanceof UpdatePaymentCardRequest) {

            UpdatePaymentCardRequest updatePaymentCardRequest = (UpdatePaymentCardRequest) request;
            this.setSerialization(new UpdatePaymentCardRequestSerialization(updatePaymentCardRequest));

        } else if (request instanceof LookupPaymentCardRequest) {

            LookupPaymentCardRequest lookupPaymentCardRequest = (LookupPaymentCardRequest) request;
            this.setSerialization(new LookupPaymentCardRequestSerialization(lookupPaymentCardRequest));
        } else if (request instanceof CardTokenPaymentMethodRequest) {

            CardTokenPaymentMethodRequest cardTokenPaymentMethodRequest = (CardTokenPaymentMethodRequest) request;
            this.setSerialization(new CardTokenPaymentMethodRequestSerialization(cardTokenPaymentMethodRequest));
        }
    }

    protected ISerialization getSerialization() {
        return serialization;
    }

    protected void setSerialization(ISerialization serialization) {
        this.serialization = serialization;
    }

}
