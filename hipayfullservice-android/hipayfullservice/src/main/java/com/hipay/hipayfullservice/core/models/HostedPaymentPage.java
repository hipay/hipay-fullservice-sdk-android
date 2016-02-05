package com.hipay.hipayfullservice.core.models;

import com.hipay.hipayfullservice.core.mapper.AbstractMapper;
import com.hipay.hipayfullservice.core.mapper.interfaces.MapBehaviour;

import java.net.URL;

/**
 * Created by nfillion on 25/01/16.
 */
public class HostedPaymentPage {

    protected Boolean test;
    protected String mid;
    protected URL forwardUrl;

    protected Order order;

    protected String cdata1;
    protected String cdata2;
    protected String cdata3;
    protected String cdata4;
    protected String cdata5;
    protected String cdata6;
    protected String cdata7;
    protected String cdata8;
    protected String cdata9;
    protected String cdata10;

    public HostedPaymentPage() {
    }

    public Boolean getTest() {
        return test;
    }

    public void setTest(Boolean test) {
        this.test = test;
    }

    public String getMid() {
        return mid;
    }

    public void setMid(String mid) {
        this.mid = mid;
    }

    public URL getForwardUrl() {
        return forwardUrl;
    }

    public void setForwardUrl(URL forwardUrl) {
        this.forwardUrl = forwardUrl;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public String getCdata1() {
        return cdata1;
    }

    public void setCdata1(String cdata1) {
        this.cdata1 = cdata1;
    }

    public String getCdata2() {
        return cdata2;
    }

    public void setCdata2(String cdata2) {
        this.cdata2 = cdata2;
    }

    public String getCdata3() {
        return cdata3;
    }

    public void setCdata3(String cdata3) {
        this.cdata3 = cdata3;
    }

    public String getCdata4() {
        return cdata4;
    }

    public void setCdata4(String cdata4) {
        this.cdata4 = cdata4;
    }

    public String getCdata5() {
        return cdata5;
    }

    public void setCdata5(String cdata5) {
        this.cdata5 = cdata5;
    }

    public String getCdata6() {
        return cdata6;
    }

    public void setCdata6(String cdata6) {
        this.cdata6 = cdata6;
    }

    public String getCdata7() {
        return cdata7;
    }

    public void setCdata7(String cdata7) {
        this.cdata7 = cdata7;
    }

    public String getCdata8() {
        return cdata8;
    }

    public void setCdata8(String cdata8) {
        this.cdata8 = cdata8;
    }

    public String getCdata9() {
        return cdata9;
    }

    public void setCdata9(String cdata9) {
        this.cdata9 = cdata9;
    }

    public String getCdata10() {
        return cdata10;
    }

    public void setCdata10(String cdata10) {
        this.cdata10 = cdata10;
    }



    public static class HostedPaymentPageMapper extends AbstractMapper {
        public HostedPaymentPageMapper() {
            //super();
        }

        @Override
        protected boolean isClassValid() {

            if (this.getBehaviour() instanceof MapBehaviour) {

                if (this.getURLForKey("forwardUrl") != null) {
                    return true;
                }
            }

            return false;
        }

        protected Object mappedObject() {

            HostedPaymentPage object = new HostedPaymentPage();

            object.setCdata1(this.getStringForKey("cdata1"));
            object.setCdata2(this.getStringForKey("cdata2"));
            object.setCdata3(this.getStringForKey("cdata3"));
            object.setCdata4(this.getStringForKey("cdata4"));
            object.setCdata5(this.getStringForKey("cdata5"));
            object.setCdata6(this.getStringForKey("cdata6"));
            object.setCdata7(this.getStringForKey("cdata7"));
            object.setCdata8(this.getStringForKey("cdata8"));
            object.setCdata9(this.getStringForKey("cdata9"));
            object.setCdata10(this.getStringForKey("cdata10"));

            object.setForwardUrl(this.getURLForKey("forwardUrl"));
            object.setTest(this.getBoolForKey("test"));
            object.setMid(this.getStringForKey("mid"));

            //TODO set orderMapper
            //[object setValue:[[HPFOrderMapper mapperWithRawData:[self getDictionaryForKey:@"order"]] mappedObject] forKey:@"order"];

            return object;

        }
    }

}
