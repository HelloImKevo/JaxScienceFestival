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
import com.schanz.jaxsciencefestival.ui.adapter.NewsDashboardAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NewsDashboardListView extends FrameLayout implements ModelView<List<NewsEvent>> {

    public interface Listener {
        void onItemSelected(@NonNull NewsEvent item);
    }

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    private Listener mListener;
    private NewsDashboardAdapter mAdapter;

    public NewsDashboardListView(Context context) {
        super(context);
        inflate(getContext(), R.layout.news_dashboard_list_view, this);
    }

    public NewsDashboardListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        inflate(getContext(), R.layout.news_dashboard_list_view, this);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        if (isInEditMode()) {
            return;
        }

        ButterKnife.bind(this, this);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        mAdapter = new NewsDashboardAdapter(new NewsDashboardAdapter.Listener() {
            @Override
            public void onItemClick(@NonNull NewsEvent item) {
                if (mListener != null) {
                    mListener.onItemSelected(item);
                }
            }
        });
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    public void setModel(@Nullable List<NewsEvent> model) {
        mAdapter.clear();
        if (model != null) {
            mAdapter.addAll(model);
        }
    }

    public void setListener(Listener listener) {
        mListener = listener;
    }
}
