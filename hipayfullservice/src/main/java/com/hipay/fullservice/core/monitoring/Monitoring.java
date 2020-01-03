package com.hipay.fullservice.core.monitoring;

import com.hipay.fullservice.core.utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

public class Monitoring {

    protected Date initDate;
    protected Date displayDate;
    protected Date payDate;
    protected Date requestDate;
    protected Date responseDate;


    public JSONObject toJSONObject() {

        JSONObject jsonObject = new JSONObject();

        try {

            if (this.getInitDate() != null) {
                jsonObject.put("date_init",  Utils.getStringFromDateUTC(this.getInitDate()));
            }
            if (this.getDisplayDate() != null) {
                jsonObject.put("date_display",  Utils.getStringFromDateUTC(this.getDisplayDate()));
            }
            if (this.getPayDate() != null) {
                jsonObject.put("date_pay",  Utils.getStringFromDateUTC(this.getPayDate()));
            }
            if (this.getRequestDate() != null) {
                jsonObject.put("date_request",  Utils.getStringFromDateUTC(this.getRequestDate()));
            }
            if (this.getResponseDate() != null) {
                jsonObject.put("date_response",  Utils.getStringFromDateUTC(this.getResponseDate()));
            }
            return jsonObject;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }


    public Date getInitDate() {
        return initDate;
    }

    public void setInitDate(Date initDate) {
        this.initDate = initDate;
    }

    public Date getDisplayDate() {
        return displayDate;
    }

    public void setDisplayDate(Date displayDate) {
        this.displayDate = displayDate;
    }

    public Date getPayDate() {
        return payDate;
    }

    public void setPayDate(Date payDate) {
        this.payDate = payDate;
    }

    public Date getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(Date requestDate) {
        this.requestDate = requestDate;
    }

    public Date getResponseDate() {
        return responseDate;
    }

    public void setResponseDate(Date responseDate) {
        this.responseDate = responseDate;
    }

}
