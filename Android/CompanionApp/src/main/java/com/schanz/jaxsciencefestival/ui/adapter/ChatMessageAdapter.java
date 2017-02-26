package com.schanz.jaxsciencefestival.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import com.schanz.jaxsciencefestival.R;
import com.schanz.jaxsciencefestival.ai.Message;
import com.schanz.jaxsciencefestival.util.StringHelper;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ChatMessageAdapter extends RecyclerView.Adapter<ChatMessageAdapter.ChatMessageViewHolder> {

    public interface Listener {

        /**
         * @param item The model bound to the clicked view holder.
         */
        void onItemClick(@NonNull Message item);
    }

    @NonNull
    private Listener mListener;
    @NonNull
    private List<Message> mItems;

    public ChatMessageAdapter(@NonNull Listener listener) {
        super();
        mListener = listener;
        mItems = new ArrayList<>();
    }

    @Override
    public ChatMessageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ChatMessageViewHolder(parent, mListener);
    }

    @Override
    public void onBindViewHolder(ChatMessageViewHolder holder, int position) {
        holder.bind(getItem(position));
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public void addAll(Collection<? extends Message> models) {
        mItems.addAll(models);
        super.notifyDataSetChanged();
    }

    public void clear() {
        mItems.clear();
        super.notifyDataSetChanged();
    }

    @Nullable
    private Message getItem(int position) {
        return position < mItems.size() ? mItems.get(position) : null;
    }

    public static class ChatMessageViewHolder extends BaseViewHolder<Message> {

        @NonNull
        CardView cardView;

        @BindView(R.id.lbl_name)
        TextView lblName;
        @BindView(R.id.lbl_message_header)
        TextView lblMessageHeader;
        @BindView(R.id.lbl_message_date)
        TextView lblMessageDate;

        @Nullable
        private Message mModel;

        public ChatMessageViewHolder(ViewGroup parent, final Listener listener) {
            super(LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.chat_message_view_holder, parent, false));

            ButterKnife.bind(this, view());
            cardView = (CardView) view();
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null && mModel != null) {
                        listener.onItemClick(mModel);
                    }
                }
            });
        }

        @Override
        public void bind(@Nullable Message item) {
            mModel = item;
            if (item != null) {
                Context context = view().getContext();
                lblName.setText(item.text);
                lblMessageDate.setText(StringHelper.toDateAtTime(new Date()));
            }
        }
    }
}
