package com.hipay.fullservice.core.mapper;

import com.hipay.fullservice.core.mapper.interfaces.MapMapper;
import com.hipay.fullservice.core.models.HostedPaymentPage;

/**
 * Created by HiPay on 08/09/16.
 */
public class HostedPaymentPageMapper extends AbstractMapper {
    public HostedPaymentPageMapper(Object rawdata) {
        super(rawdata);
    }

    @Override
    public boolean isValid() {

        if (this.getBehaviour() instanceof MapMapper) {

            if (this.getURLForKey("forwardUrl") != null) {
                return true;
            }
        }

        return false;
    }

    public HostedPaymentPage mappedObject() {

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

        return object;

    }

    @Override
    protected Object mappedObjectFromBundle() {
        return null;
    }

    @Override
    protected Object mappedObjectFromUri() {
        return null;
    }
}


