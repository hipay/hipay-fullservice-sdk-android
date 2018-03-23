package com.hipay.fullservice.core.mapper;

import android.os.Bundle;

import com.hipay.fullservice.core.mapper.interfaces.BundleMapper;
import com.hipay.fullservice.screen.model.CustomTheme;

/**
 * Created by nfillion on 22/03/2018.
 */
 public class CustomThemeMapper extends AbstractMapper {
    public CustomThemeMapper(Bundle object) {
        super(object);
    }

    @Override
    public boolean isValid() {
        return this.getBehaviour() instanceof BundleMapper;
    }

    @Override
    public CustomTheme mappedObject() {
        CustomTheme customTheme = new CustomTheme();

        customTheme.setColorPrimaryId(this.getIntegerForKey("colorPrimary"));
        customTheme.setColorPrimaryDarkId(this.getIntegerForKey("colorPrimaryDark"));
        customTheme.setTextColorPrimaryId(this.getIntegerForKey("colorTextPrimary"));

        return customTheme;
    }
}
