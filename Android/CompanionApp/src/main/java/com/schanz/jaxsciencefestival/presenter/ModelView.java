package com.schanz.jaxsciencefestival.presenter;

import android.support.annotation.Nullable;

/**
 * @param <T> The type of data this {@link ModelView} displays.
 */
public interface ModelView<T> {

    void setModel(@Nullable T model);
}
