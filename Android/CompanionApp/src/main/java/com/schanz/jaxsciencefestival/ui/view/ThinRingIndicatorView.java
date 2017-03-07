package com.schanz.jaxsciencefestival.ui.view;

import android.content.Context;
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

public class ThinRingIndicatorView extends FrameLayout {

    @BindView(R.id.indicator)
    View indicator;

    private GradientDrawable mShape;

    public ThinRingIndicatorView(Context context) {
        super(context);
        inflate(getContext(), R.layout.thin_ring_indicator_view, this);
    }

    public ThinRingIndicatorView(Context context, AttributeSet attrs) {
        super(context, attrs);
        inflate(getContext(), R.layout.thin_ring_indicator_view, this);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        ButterKnife.bind(this, this);

        indicator = findViewById(R.id.indicator);
        LayerDrawable layerDrawable = (LayerDrawable) indicator.getBackground();
        mShape = (GradientDrawable) layerDrawable.findDrawableByLayerId(R.id.ring_indicator_shape);

        // if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        //     mShape = (GradientDrawable) mClippingMask.getDrawable();
        // } else {
        //     mShape = (GradientDrawable) layerDrawable.findDrawableByLayerId(R.id.ring_indicator_shape);
        // }

        if (isInEditMode()) {
            setColor(R.color.orange_ecstasy);
            return;
        }
    }

    public void setColor(@ColorRes int colorRes) {
        mShape.mutate();
        mShape.setColor(ContextCompat.getColor(getContext(), colorRes));
    }
}
