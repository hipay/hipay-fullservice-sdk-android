package com.hipay.hipayfullservice.core.serialization;

import com.hipay.hipayfullservice.core.requests.AbstractRequest;
import com.hipay.hipayfullservice.core.requests.order.OrderRequest;
import com.hipay.hipayfullservice.core.requests.order.PaymentPageRequest;
import com.hipay.hipayfullservice.core.serialization.interfaces.ISerialization;
import com.hipay.hipayfullservice.core.serialization.interfaces.OrderRequestSerialization;
import com.hipay.hipayfullservice.core.serialization.interfaces.PaymentPageRequestSerialization;

import java.util.Map;

/**
 * Created by nfillion on 04/02/16.
 */
public abstract class AbstractSerializationMapper {

    protected AbstractRequest request;
    ISerialization serialization;

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
        }
    }

    public ISerialization getSerialization() {
        return serialization;
    }

    public void setSerialization(ISerialization serialization) {
        this.serialization = serialization;
    }

    /*

    protected String getStringURLForKey(String key) {

        return null;
    }

    public Object getObjectForKey(String key) {

        this.getRequest().get

        return DataExtractor.getObjectFromField(this.getJsonObject(), key);
    }


    public String  getURLForKey(String key) {


        Object url = this.getR
        if (string != null && !string.isEmpty()) {

            URL url = null;
            try {
                url = new URL(string);
                return new URL(string);

            } catch (MalformedURLException e) {
                e.printStackTrace();
                url = null;

            } finally {
                return url;
            }
        }

        return null;
    }
    */

    public AbstractRequest getRequest() {
        return request;
    }

    public void setRequest(AbstractRequest request) {
        this.request = request;
    }
}
