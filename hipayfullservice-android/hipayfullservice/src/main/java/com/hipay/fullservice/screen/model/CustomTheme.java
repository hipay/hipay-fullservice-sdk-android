package com.hipay.fullservice.screen.model;

import android.os.Bundle;

import com.hipay.fullservice.core.mapper.CustomThemeMapper;
import com.hipay.fullservice.core.models.AbstractModel;

/**
 * Created by nfillion on 29/03/16.
 */
public class CustomTheme extends AbstractModel {

    private int colorPrimaryId;
    private int colorPrimaryDarkId;
    private int textColorPrimaryId;

    public static final String TAG = "theme";

    public CustomTheme() {
    }

    public static CustomTheme fromBundle(Bundle bundle) {

        CustomThemeMapper mapper = new CustomThemeMapper(bundle);
        return mapper.mappedObject();
    }

    public CustomTheme(int colorPrimaryId, int colorPrimaryDarkId, int textColorPrimaryId) {

        this.colorPrimaryId = colorPrimaryId;
        this.colorPrimaryDarkId = colorPrimaryDarkId;
        this.textColorPrimaryId = textColorPrimaryId;
    }

    public int getColorPrimaryId() {
        return colorPrimaryId;
    }

    public void setColorPrimaryId(int colorPrimaryId) {
        this.colorPrimaryId = colorPrimaryId;
    }

    public int getColorPrimaryDarkId() {
        return colorPrimaryDarkId;
    }

    public void setColorPrimaryDarkId(int colorPrimaryDarkId) {
        this.colorPrimaryDarkId = colorPrimaryDarkId;
    }

    public int getTextColorPrimaryId() {
        return textColorPrimaryId;
    }

    public void setTextColorPrimaryId(int textColorPrimaryId) {
        this.textColorPrimaryId = textColorPrimaryId;
    }
}
