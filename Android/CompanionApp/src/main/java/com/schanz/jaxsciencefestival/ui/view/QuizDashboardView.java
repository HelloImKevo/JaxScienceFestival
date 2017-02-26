package com.schanz.jaxsciencefestival.ui.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import java.util.List;

import com.schanz.jaxsciencefestival.R;
import com.schanz.jaxsciencefestival.model.NewsEvent;
import com.schanz.jaxsciencefestival.presenter.ModelView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class QuizDashboardView extends FrameLayout implements ModelView<List<NewsEvent>> {

    public interface Listener {
        void onItemSelected(@NonNull NewsEvent item);
    }

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
    }

    @Override
    public void setModel(@Nullable List<NewsEvent> model) {
    }

    public void setListener(Listener listener) {
        mListener = listener;
    }
}
