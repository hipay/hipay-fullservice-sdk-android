package com.hipay.fullservice.core.serialization;

import com.hipay.fullservice.core.errors.exceptions.ApiException;
import com.hipay.fullservice.core.errors.exceptions.HttpException;
import com.hipay.fullservice.core.models.FraudScreening;
import com.hipay.fullservice.core.models.Order;
import com.hipay.fullservice.core.models.PaymentCardToken;
import com.hipay.fullservice.core.models.PaymentProduct;
import com.hipay.fullservice.core.models.PersonalInformation;
import com.hipay.fullservice.core.models.ThreeDSecure;
import com.hipay.fullservice.core.models.Transaction;
import com.hipay.fullservice.core.requests.info.CustomerInfoRequest;
import com.hipay.fullservice.core.requests.info.PersonalInfoRequest;
import com.hipay.fullservice.core.requests.order.OrderRequest;
import com.hipay.fullservice.core.requests.order.PaymentPageRequest;
import com.hipay.fullservice.core.requests.payment.CardTokenPaymentMethodRequest;
import com.hipay.fullservice.core.requests.securevault.GenerateTokenRequest;
import com.hipay.fullservice.core.requests.securevault.LookupPaymentCardRequest;
import com.hipay.fullservice.core.requests.securevault.UpdatePaymentCardRequest;
import com.hipay.fullservice.core.serialization.interfaces.CustomThemeSerialization;
import com.hipay.fullservice.core.serialization.interfaces.CustomerInfoRequestSerialization;
import com.hipay.fullservice.core.serialization.interfaces.FraudScreeningSerialization;
import com.hipay.fullservice.core.serialization.interfaces.ISerialization;
import com.hipay.fullservice.core.serialization.interfaces.PaymentCardTokenSerialization;
import com.hipay.fullservice.core.serialization.interfaces.PaymentProductSerialization;
import com.hipay.fullservice.core.serialization.interfaces.PersonalInfoRequestSerialization;
import com.hipay.fullservice.core.serialization.interfaces.PersonalInformationSerialization;
import com.hipay.fullservice.core.serialization.interfaces.ThreeDSecureSerialization;
import com.hipay.fullservice.core.serialization.interfaces.TransactionSerialization;
import com.hipay.fullservice.core.serialization.interfaces.errors.ApiExceptionSerialization;
import com.hipay.fullservice.core.serialization.interfaces.errors.HttpExceptionSerialization;
import com.hipay.fullservice.core.serialization.interfaces.order.OrderRequestSerialization;
import com.hipay.fullservice.core.serialization.interfaces.order.OrderSerialization;
import com.hipay.fullservice.core.serialization.interfaces.order.PaymentPageRequestSerialization;
import com.hipay.fullservice.core.serialization.interfaces.payment.CardTokenPaymentMethodRequestSerialization;
import com.hipay.fullservice.core.serialization.interfaces.securevault.LookupPaymentCardRequestSerialization;
import com.hipay.fullservice.core.serialization.interfaces.securevault.SecureVaultRequestSerialization;
import com.hipay.fullservice.core.serialization.interfaces.securevault.UpdatePaymentCardRequestSerialization;
import com.hipay.fullservice.screen.model.CustomTheme;

import java.security.InvalidParameterException;

/**
 * Created by nfillion on 26/03/2018.
 */

public class SerializationFactory {

    /**
     * Provide Interface for Serialization for Request
     *
     * @param model
     * @return ISerialization
     */
    public static ISerialization newInstance(Object model) {
        if (model instanceof ApiException) {
            return new ApiExceptionSerialization((ApiException) model);
        } else if (model instanceof HttpException) {
            return new HttpExceptionSerialization((HttpException) model);
        } else if (model instanceof PaymentProduct) {
            return new PaymentProductSerialization((PaymentProduct) model);
        } else if (model instanceof CustomTheme) {
            return new CustomThemeSerialization((CustomTheme) model);
        } else if (model instanceof Transaction) {
            return new TransactionSerialization((Transaction) model);
        } else if (model instanceof ThreeDSecure) {
            return new ThreeDSecureSerialization((ThreeDSecure) model);
        } else if (model instanceof FraudScreening) {
            return new FraudScreeningSerialization((FraudScreening) model);
        } else if (model instanceof Order) {
            return new OrderSerialization((Order) model);
        } else if (model instanceof PersonalInformation) {
            return new PersonalInformationSerialization((PersonalInformation) model);
        } else if (model instanceof PaymentCardToken) {
            return new PaymentCardTokenSerialization((PaymentCardToken) model);
        } else if (model instanceof PaymentPageRequest) {
            return new PaymentPageRequestSerialization((PaymentPageRequest) model);
        } else if (model instanceof OrderRequest) {
            return new OrderRequestSerialization((OrderRequest) model);
        } else if (model instanceof CustomerInfoRequest) {
            return new CustomerInfoRequestSerialization((CustomerInfoRequest) model);
        } else if (model instanceof PersonalInfoRequest) {
            return new PersonalInfoRequestSerialization((PersonalInfoRequest) model);
        } else if (model instanceof GenerateTokenRequest) {
            return new SecureVaultRequestSerialization((GenerateTokenRequest) model);
        } else if (model instanceof UpdatePaymentCardRequest) {
            return new UpdatePaymentCardRequestSerialization((UpdatePaymentCardRequest) model);
        } else if (model instanceof LookupPaymentCardRequest) {
            return new LookupPaymentCardRequestSerialization((LookupPaymentCardRequest) model);
        } else if (model instanceof CardTokenPaymentMethodRequest) {
            return new CardTokenPaymentMethodRequestSerialization((CardTokenPaymentMethodRequest) model);
        } else {
            throw new InvalidParameterException("No serialization exist for this Object");
        }
    }
}
