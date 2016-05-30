package com.hubel.thu.thelab.ui.util;

import android.content.Context;
import android.graphics.Typeface;

import java.util.HashMap;
import java.util.Map;

/**
 * <p/>
 * Also see https://code.google.com/p/android/issues/detail?id=9904
 */
public class FontUtil {

    private FontUtil() { }

    private static final Map<String, Typeface> sTypefaceCache = new HashMap<String, Typeface>();

    public static Typeface get(Context context, String font) {
        synchronized (sTypefaceCache) {
            if (!sTypefaceCache.containsKey(font)) {
                Typeface tf = Typeface.createFromAsset(
                        context.getApplicationContext().getAssets(), "fonts/" + font + ".ttf");
                sTypefaceCache.put(font, tf);
            }
            return sTypefaceCache.get(font);
        }
    }
}