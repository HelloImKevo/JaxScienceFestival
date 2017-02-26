package com.schanz.jaxsciencefestival.presenter;

import android.os.Bundle;
import android.support.annotation.Nullable;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

public class PresenterManager {

    private static final String SIS_KEY_PRESENTER_ID = "presenter_id";

    private static PresenterManager sInstance;

    private final AtomicLong currentId;
    private final Cache<Long, BasePresenter<?, ?>> presenters;

    private PresenterManager(long maxSize, long expirationValue, TimeUnit expirationUnit) {
        currentId = new AtomicLong();

        presenters = CacheBuilder.newBuilder()
                .maximumSize(maxSize)
                .expireAfterWrite(expirationValue, expirationUnit)
                .build();
    }

    public static PresenterManager instance() {
        if (sInstance == null) {
            synchronized (PresenterManager.class) {
                if (sInstance == null) {
                    sInstance = new PresenterManager(10, 30, TimeUnit.SECONDS);
                }
            }
        }
        return sInstance;
    }

    @Nullable
    public <P extends BasePresenter<?, ?>> P restorePresenter(Bundle savedInstanceState) {
        Long presenterId = savedInstanceState.getLong(SIS_KEY_PRESENTER_ID);
        //noinspection unchecked
        P presenter = (P) presenters.getIfPresent(presenterId);
        presenters.invalidate(presenterId);
        return presenter;
    }

    public void savePresenter(BasePresenter<?, ?> presenter, Bundle outState) {
        long presenterId = currentId.incrementAndGet();
        presenters.put(presenterId, presenter);
        outState.putLong(SIS_KEY_PRESENTER_ID, presenterId);
    }
}
