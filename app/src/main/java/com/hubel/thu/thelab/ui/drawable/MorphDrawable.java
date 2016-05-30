package com.hubel.thu.thelab.ui.drawable;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Outline;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.util.Property;

import com.hubel.thu.thelab.ui.util.AnimUtils;

/**
 * Created by thu on 25.05.16.
 *  A drawable that can morph size, shape (via it's corner radius) and color.  Specifically this is
 * useful for animating between a FAB and a dialog.
 */

public class MorphDrawable extends Drawable {
    public static final Property<MorphDrawable, Float> CORNER_RADIUS =
            new AnimUtils.FloatProperty<MorphDrawable>("cornerRadius") {

                @Override
                public void setValue(MorphDrawable morphDrawable, float value) {
                    morphDrawable.setCornerRadius(value);
                }

                @Override
                public Float get(MorphDrawable morphDrawable) {
                    return morphDrawable.getCornerRadius();
                }

            };

    public static final Property<MorphDrawable, Integer> COLOR =
            new AnimUtils.IntProperty<MorphDrawable>("color") {

                @Override
                public void setValue(MorphDrawable morphDrawable, int value) {
                    morphDrawable.setColor(value);
                }

                @Override
                public Integer get(MorphDrawable morphDrawable) {
                    return morphDrawable.getColor();
                }

            };

    private Paint paint;
    private float cornerRadius;

    public MorphDrawable(@ColorInt int color, float cornerRadius) {
        this.cornerRadius = cornerRadius;
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(color);
    }

    public float getCornerRadius() {
        return cornerRadius;
    }

    public void setCornerRadius(float cornerRadius) {
        this.cornerRadius = cornerRadius;
        invalidateSelf();
    }

    public int getColor() {
        return paint.getColor();
    }

    public void setColor(@ColorInt int color) {
        paint.setColor(color);
        invalidateSelf();
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawRoundRect(
                getBounds().left,
                getBounds().top,
                getBounds().right,
                getBounds().bottom,
                cornerRadius,
                cornerRadius,
                paint);
    }

    @Override
    public void getOutline(Outline outline) {
        outline.setRoundRect(getBounds(), cornerRadius);
    }

    @Override
    public void setAlpha(int alpha) {
        paint.setAlpha(alpha);
        invalidateSelf();
    }

    @Override
    public void setColorFilter(ColorFilter cf) {
        paint.setColorFilter(cf);
        invalidateSelf();
    }

    @Override
    public int getOpacity() {
        return paint.getAlpha();
    }
}
