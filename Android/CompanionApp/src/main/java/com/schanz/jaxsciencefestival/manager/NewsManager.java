package com.schanz.jaxsciencefestival.manager;

import android.support.annotation.NonNull;

import com.schanz.jaxsciencefestival.model.NewsEvent;

public class NewsManager {

    private static NewsManager sInstance;

    private NewsEvent selectedNewsEvent;

    private NewsManager() {
    }

    @NonNull
    public static NewsManager instance() {
        if (sInstance == null) {
            synchronized (NewsManager.class) {
                if (sInstance == null) {
                    sInstance = new NewsManager();
                }
            }
        }
        return sInstance;
    }

    public NewsEvent getSelectedNewsEvent() {
        return selectedNewsEvent;
    }

    public void setSelectedNewsEvent(NewsEvent selectedNewsEvent) {
        this.selectedNewsEvent = selectedNewsEvent;
    }
}
