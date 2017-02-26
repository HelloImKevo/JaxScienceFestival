package com.schanz.jaxsciencefestival.ui.adapter;

import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public abstract class BaseViewHolder<T> extends RecyclerView.ViewHolder {

    public BaseViewHolder(View view) {
        super(view);
    }

    protected View view() {
        return super.itemView;
    }

    public abstract void bind(@Nullable T item);
}
