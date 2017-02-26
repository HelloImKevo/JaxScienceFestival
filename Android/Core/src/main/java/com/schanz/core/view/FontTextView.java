package com.schanz.core.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.StringRes;
import android.util.AttributeSet;
import android.widget.TextView;

import com.schanz.core.R;

public class FontTextView extends TextView {
    public FontTextView(Context context) {
        super(context);
        init(context, null);
    }

    public FontTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public FontTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs);
    }

    public void changeFont(Context context, @StringRes int stringResId) {
        if (!isInEditMode()) {
            String fontName = context.getString(stringResId);
            setTypeface(FontCache.getFont(context, fontName));
        }
    }

    private void init(Context context, AttributeSet attrs) {
        if (attrs != null) {
            TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.FontTextView);
            if (!isInEditMode()) {
                String fontName = a.getString(R.styleable.FontTextView_custom_font);
                if (fontName == null) {
                    fontName = context.getString(R.string.font_century_gothic_regular);
                }
                setTypeface(FontCache.getFont(context, fontName));
            }
            a.recycle();
        }
    }
}
