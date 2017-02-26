package com.schanz.jaxsciencefestival.ui.view;

import android.content.Context;
import android.graphics.drawable.ClipDrawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.support.annotation.ColorRes;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import com.schanz.jaxsciencefestival.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RingIndicatorView extends FrameLayout {

    @BindView(R.id.indicator)
    View indicator;

    private ClipDrawable mClippingMask;
    private GradientDrawable mShape;

    public RingIndicatorView(Context context) {
        super(context);
        inflate(getContext(), R.layout.ring_indicator_view, this);
    }

    public RingIndicatorView(Context context, AttributeSet attrs) {
        super(context, attrs);
        inflate(getContext(), R.layout.ring_indicator_view, this);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        ButterKnife.bind(this, this);

        indicator = findViewById(R.id.indicator);
        LayerDrawable layerDrawable = (LayerDrawable) indicator.getBackground();
        mShape = (GradientDrawable) layerDrawable.findDrawableByLayerId(R.id.ring_indicator_shape);
        mClippingMask = (ClipDrawable) layerDrawable.findDrawableByLayerId(R.id.ring_indicator_clipping_mask);

        // if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        //     mShape = (GradientDrawable) mClippingMask.getDrawable();
        // } else {
        //     mShape = (GradientDrawable) layerDrawable.findDrawableByLayerId(R.id.ring_indicator_shape);
        // }

        if (isInEditMode()) {
            setLevel(75);
            setColor(R.color.red_thunderbird);
            return;
        }
    }

    /**
     * @param percentFilled 0 - 100; 0 for empty, 100 for completely filled.
     */
    public void setLevel(int percentFilled) {
        // Legacy logic required ClipDrawable.getDrawable() which is only
        // available in API level 23. The XML is adjusted to draw the
        // clipping mask from the top, and requires the "percent remaining"
        // instead of the "percent filled".
        // If the level should be 75% filled, the level should be set to 25.
        final int maxFill = 100;
        final int minFill = 0;
        if (percentFilled < minFill) {
            percentFilled = minFill;
        } else if (percentFilled > maxFill) {
            percentFilled = maxFill;
        }
        final int percentRemaining = 10000 - (percentFilled * maxFill);
        mClippingMask.setLevel(percentRemaining);
    }

    public void setColor(@ColorRes int colorRes) {
        mShape.mutate();
        mShape.setColor(ContextCompat.getColor(getContext(), colorRes));
    }
}
