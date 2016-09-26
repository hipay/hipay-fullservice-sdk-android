package com.hipay.fullservice.core.serialization.interfaces;

import android.os.Bundle;

import com.hipay.fullservice.screen.model.CustomTheme;

import java.util.Map;

/**
 * Created by nfillion on 08/09/16.
 */

public class CustomThemeSerialization extends AbstractSerialization {

    public CustomThemeSerialization(CustomTheme customTheme) {
        super(customTheme);
    }

    @Override
    public Map<String, String> getSerializedRequest() {
        return null;
    }

    @Override
    public Bundle getSerializedBundle() {

        super.getSerializedBundle();

        CustomTheme customTheme = (CustomTheme)this.getModel();

        this.putIntForKey("colorPrimary", customTheme.getColorPrimaryId());
        this.putIntForKey("colorPrimaryDark", customTheme.getColorPrimaryDarkId());
        this.putIntForKey("colorTextPrimary", customTheme.getTextColorPrimaryId());

        return this.getBundle();
    }

    @Override
    public String getQueryString() {
        return null;
    }
}
