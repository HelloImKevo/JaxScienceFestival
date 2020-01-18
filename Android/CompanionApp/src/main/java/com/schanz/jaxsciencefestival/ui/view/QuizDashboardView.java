package com.schanz.jaxsciencefestival.ui.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.schanz.jaxsciencefestival.R;
import com.schanz.jaxsciencefestival.model.QuizQuestion;
import com.schanz.jaxsciencefestival.util.Logger;

import butterknife.BindView;
import butterknife.ButterKnife;

public class QuizDashboardView extends FrameLayout implements View.OnClickListener {

    public interface Listener {
        void onCategorySelected(@NonNull QuizQuestion.Type category);
    }

    @BindView(R.id.container_science)
    ViewGroup containerScience;
    @BindView(R.id.container_technology)
    ViewGroup containerTechnology;
    @BindView(R.id.container_engineering)
    ViewGroup containerEngineering;
    @BindView(R.id.container_math)
    ViewGroup containerMath;

    private Listener mListener;

    public QuizDashboardView(Context context) {
        super(context);
        inflate(getContext(), R.layout.quiz_dashboard_view, this);
    }

    public QuizDashboardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        inflate(getContext(), R.layout.quiz_dashboard_view, this);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        if (isInEditMode()) {
            return;
        }

        ButterKnife.bind(this, this);

        containerScience.setOnClickListener(this);
        containerTechnology.setOnClickListener(this);
        containerEngineering.setOnClickListener(this);
        containerMath.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (mListener == null) {
            Logger.e(QuizDashboardView.class.getSimpleName(), "Listener is null");
            return;
        }
        if (v == containerScience) {
            mListener.onCategorySelected(QuizQuestion.Type.SCIENCE);
        } else if (v == containerTechnology) {
            mListener.onCategorySelected(QuizQuestion.Type.TECHNOLOGY);
        } else if (v == containerEngineering) {
            mListener.onCategorySelected(QuizQuestion.Type.ENGINEERING);
        } else if (v == containerMath) {
            mListener.onCategorySelected(QuizQuestion.Type.MATH);
        }
    }

    public void setListener(Listener listener) {
        mListener = listener;
    }
}
