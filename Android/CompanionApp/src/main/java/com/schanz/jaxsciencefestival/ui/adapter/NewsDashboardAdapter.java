package com.schanz.jaxsciencefestival.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import com.schanz.jaxsciencefestival.R;
import com.schanz.jaxsciencefestival.model.NewsEvent;
import com.schanz.jaxsciencefestival.util.StringHelper;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NewsDashboardAdapter extends RecyclerView.Adapter<NewsDashboardAdapter.NewsDashboardViewHolder> {

    public interface Listener {

        /**
         * @param item The model bound to the clicked view holder.
         */
        void onItemClick(@NonNull NewsEvent item);
    }

    @NonNull
    private Listener mListener;
    @NonNull
    private List<NewsEvent> mItems;

    public NewsDashboardAdapter(@NonNull Listener listener) {
        super();
        mListener = listener;
        mItems = new ArrayList<>();
    }

    @Override
    public NewsDashboardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new NewsDashboardViewHolder(parent, mListener);
    }

    @Override
    public void onBindViewHolder(NewsDashboardViewHolder holder, int position) {
        holder.bind(getItem(position));
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public void addAll(Collection<? extends NewsEvent> models) {
        mItems.addAll(models);
        super.notifyDataSetChanged();
    }

    public void clear() {
        mItems.clear();
        super.notifyDataSetChanged();
    }

    @Nullable
    private NewsEvent getItem(int position) {
        return position < mItems.size() ? mItems.get(position) : null;
    }

    public static class NewsDashboardViewHolder extends BaseViewHolder<NewsEvent> {

        @NonNull
        CardView cardView;

        @BindView(R.id.img_profile_picture)
        ImageView imgProfilePicture;
        @BindView(R.id.lbl_name)
        TextView lblName;
        @BindView(R.id.lbl_message_header)
        TextView lblMessageHeader;
        @BindView(R.id.lbl_message_date)
        TextView lblMessageDate;

        @Nullable
        private NewsEvent mModel;

        public NewsDashboardViewHolder(ViewGroup parent, final Listener listener) {
            super(LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.news_dashboard_view_holder, parent, false));

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
        public void bind(@Nullable NewsEvent item) {
            mModel = item;
            if (item != null) {
                Context context = view().getContext();
                configureColors(context, item);
                imgProfilePicture.setImageResource(item.profilePicture);
                lblName.setText(item.name);
                lblMessageDate.setText(StringHelper.toDateAtTime(new Date()));
            }
        }

        private void configureColors(@NonNull Context context, @NonNull NewsEvent model) {
            BigDecimal percentChange = BigDecimal.ZERO;// StockHelper.getPercentChange(model);
            if (percentChange != null) {
                final int primaryColor;
                if (percentChange.signum() < 0) {
                    primaryColor = ContextCompat.getColor(context, R.color.red_thunderbird);
                } else if (percentChange.signum() > 0) {
                    primaryColor = ContextCompat.getColor(context, R.color.green_shamrock);
                } else {
                    primaryColor = ContextCompat.getColor(context, R.color.gray_pumice);
                }
                //cardView.setCardBackgroundColor(primaryColor);
            }
        }
    }
}
