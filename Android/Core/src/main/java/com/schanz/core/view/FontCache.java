package com.schanz.core.view;

import android.content.Context;
import android.graphics.Typeface;

import java.util.HashMap;

/**
 * A utility class for loading fonts from {@code assets/fonts/}. This directory can be changed by
 * calling {@link #setFontPath(String)}. Font filenames should ideally be put into strings.xml so
 * they can be accessed from Java as well as from XML. There is a <a
 * href="http://code.google.com/p/android/issues/detail?id=9904">bug in pre-4.0 Android</a> that
 * causes the OS to not free up TypeFaces properly, so this class will cache any loaded fonts and
 * return them on subsequent calls to retrieve the font.
 */
final class FontCache {
    private static HashMap<String, Typeface> sFontCache = new HashMap<>();
    private static String sFontPath = "fonts";

    /**
     * Sets the directory where font files are stored. By default, this will be {@code fonts} in
     * the {@code assets} directory.
     *
     * @param path The path under {@code assets} where fonts are stored.
     */
    public static void setFontPath(String path) {
        sFontPath = path;
    }

    /**
     * Gets a Typeface font by creating it or getting it from the cache.
     *
     * @param context The context to use.
     * @param fontName Filename of the font in the font path.
     */
    public static Typeface getFont(Context context, String fontName) {
        Typeface tf = sFontCache.get(fontName);
        if (tf == null) {
            tf = Typeface.createFromAsset(context.getAssets(), sFontPath + "/" + fontName);
            sFontCache.put(fontName, tf);
        }

        return tf;
    }

    /**
     * Gets a Typeface font by creating it or getting it from the cache.
     *
     * @param context The context to use.
     * @param fontNameResId String resource for filename of the font in the font path.
     */
    public static Typeface getFont(Context context, int fontNameResId) {
        return FontCache.getFont(context, context.getString(fontNameResId));
    }

    private FontCache() {
    }
}
