package com.hipay.fullservice.screen.helper;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.transition.ChangeBounds;
import android.util.Property;
import android.util.TypedValue;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.core.view.ViewCompat;

public class ViewUtils {

    private ViewUtils() {
        //no instance
    }

    public static final Property<FrameLayout, Integer> FOREGROUND_COLOR =
            new IntProperty<FrameLayout>("foregroundColor") {

                @Override
                public void setValue(FrameLayout layout, int value) {
                    if (layout.getForeground() instanceof ColorDrawable) {
                        ((ColorDrawable) layout.getForeground().mutate()).setColor(value);
                    } else {
                        layout.setForeground(new ColorDrawable(value));
                    }
                }

                @Override
                public Integer get(FrameLayout layout) {
                    if (layout.getForeground() instanceof ColorDrawable) {
                        return ((ColorDrawable) layout.getForeground()).getColor();
                    } else {
                        return Color.TRANSPARENT;
                    }
                }
            };

    public static final Property<View, Integer> BACKGROUND_COLOR =
            new IntProperty<View>("backgroundColor") {

                @Override
                public void setValue(View view, int value) {
                    view.setBackgroundColor(value);
                }

                @Override
                public Integer get(View view) {
                    Drawable d = view.getBackground();
                    if (d instanceof ColorDrawable) {
                        return ((ColorDrawable) d).getColor();
                    }
                    return Color.TRANSPARENT;
                }
            };

    /**
     * Allows changes to the text size in transitions and animations.
     * Using this with something else than {@link ChangeBounds}
     * can result in a severe performance penalty due to layout passes.
     */
    public static final Property<TextView, Float> PROPERTY_TEXT_SIZE =
            new FloatProperty<TextView>("textSize") {
                @Override
                public Float get(TextView view) {
                    return view.getTextSize();
                }

                @Override
                public void setValue(TextView view, float textSize) {
                    view.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
                }
            };

    /**
     * Allows making changes to the start padding of a view.
     * Using this with something else than {@link ChangeBounds}
     * can result in a severe performance penalty due to layout passes.
     */
    public static final Property<TextView, Integer> PROPERTY_TEXT_PADDING_START =
            new IntProperty<TextView>("paddingStart") {
                @Override
                public Integer get(TextView view) {
                    return ViewCompat.getPaddingStart(view);
                }

                @Override
                public void setValue(TextView view, int paddingStart) {
                    ViewCompat.setPaddingRelative(view, paddingStart, view.getPaddingTop(),
                            ViewCompat.getPaddingEnd(view), view.getPaddingBottom());
                }
            };

    public static abstract class IntProperty<T> extends Property<T, Integer> {

        public IntProperty(String name) {
            super(Integer.class, name);
        }

        public abstract void setValue(T object, int value);

        @Override
        final public void set(T object, Integer value) {
            //noinspection UnnecessaryUnboxing
            setValue(object, value.intValue());
        }
    }

    public static abstract class FloatProperty<T> extends Property<T, Float> {

        public FloatProperty(String name) {
            super(Float.class, name);
        }

        public abstract void setValue(T object, float value);

        @Override
        final public void set(T object, Float value) {
            //noinspection UnnecessaryUnboxing
            setValue(object, value.floatValue());
        }
    }

    public static void setPaddingStart(TextView target, int paddingStart) {
        ViewCompat.setPaddingRelative(target, paddingStart, target.getPaddingTop(),
                ViewCompat.getPaddingEnd(target), target.getPaddingBottom());
    }

}
