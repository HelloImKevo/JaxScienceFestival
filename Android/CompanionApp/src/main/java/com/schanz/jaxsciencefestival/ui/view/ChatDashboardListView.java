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
import com.schanz.jaxsciencefestival.ai.ChatCompanion;
import com.schanz.jaxsciencefestival.presenter.ModelView;
import com.schanz.jaxsciencefestival.ui.adapter.ChatDashboardAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ChatDashboardListView extends FrameLayout implements ModelView<List<ChatCompanion>> {

    public interface Listener {
        void onItemSelected(@NonNull ChatCompanion item);
    }

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    private Listener mListener;
    private ChatDashboardAdapter mAdapter;

    public ChatDashboardListView(Context context) {
        super(context);
        inflate(getContext(), R.layout.chat_dashboard_list_view, this);
    }

    public ChatDashboardListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        inflate(getContext(), R.layout.chat_dashboard_list_view, this);
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
        mAdapter = new ChatDashboardAdapter(new ChatDashboardAdapter.Listener() {
            @Override
            public void onItemClick(@NonNull ChatCompanion item) {
                if (mListener != null) {
                    mListener.onItemSelected(item);
                }
            }
        });
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    public void setModel(@Nullable List<ChatCompanion> model) {
        mAdapter.clear();
        if (model != null) {
            mAdapter.addAll(model);
        }
    }

    public void setListener(Listener listener) {
        mListener = listener;
    }
}
