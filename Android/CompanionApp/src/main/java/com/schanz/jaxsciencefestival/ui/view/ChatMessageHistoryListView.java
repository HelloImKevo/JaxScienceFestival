package com.schanz.jaxsciencefestival.ui.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import com.schanz.jaxsciencefestival.R;
import com.schanz.jaxsciencefestival.ai.Message;
import com.schanz.jaxsciencefestival.ai.MessageHistory;
import com.schanz.jaxsciencefestival.presenter.ModelView;
import com.schanz.jaxsciencefestival.ui.adapter.ChatMessageAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ChatMessageHistoryListView extends FrameLayout implements ModelView<MessageHistory> {

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    private ChatMessageAdapter mAdapter;

    public ChatMessageHistoryListView(Context context) {
        super(context);
        inflate(getContext(), R.layout.chat_message_history_list_view, this);
    }

    public ChatMessageHistoryListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        inflate(getContext(), R.layout.chat_message_history_list_view, this);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        if (isInEditMode()) {
            return;
        }

        ButterKnife.bind(this, this);

        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        // TODO: Since we're currently doing an 'addAll' operation, this function probably won't work as desired
        layoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        mAdapter = new ChatMessageAdapter(new ChatMessageAdapter.Listener() {
            @Override
            public void onItemClick(@NonNull Message item) {
                // if (mListener != null) {
                //     mListener.onItemSelected(item);
                // }
            }
        });
        recyclerView.setAdapter(mAdapter);
    }

    // TODO: Change model to MessageHistory, or List<Message>
    @Override
    public void setModel(@Nullable MessageHistory model) {
        // TODO: Determine newly added messages only, and implement sorting
        mAdapter.clear();
        if (model != null) {
            mAdapter.addAll(model.messages);
        }
    }
}
