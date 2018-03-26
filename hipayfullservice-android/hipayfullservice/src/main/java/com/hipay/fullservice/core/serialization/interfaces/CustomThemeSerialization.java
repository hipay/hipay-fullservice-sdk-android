package com.hipay.fullservice.core.serialization.interfaces;

import android.os.Bundle;

import com.hipay.fullservice.screen.model.CustomTheme;

/**
 * Created by nfillion on 08/09/16.
 */

public class CustomThemeSerialization extends AbstractSerialization {

    public CustomThemeSerialization(CustomTheme customTheme) {
        super(customTheme);
    }

    @Override
    public Bundle getSerializedBundle() {
        CustomTheme customTheme = (CustomTheme)this.getModel();

        this.bundle.putInt("colorPrimary", customTheme.getColorPrimaryId());
        this.bundle.putInt("colorPrimaryDark", customTheme.getColorPrimaryDarkId());
        this.bundle.putInt("colorTextPrimary", customTheme.getTextColorPrimaryId());

        return this.bundle.getBundle();
    }
}
