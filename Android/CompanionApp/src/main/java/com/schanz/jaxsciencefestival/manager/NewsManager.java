package com.schanz.jaxsciencefestival.manager;

import android.support.annotation.NonNull;

import com.schanz.jaxsciencefestival.ai.ChatCompanion;

public class NewsManager {

    private static NewsManager sInstance;

    private ChatCompanion selectedChatCompanion;

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

    public ChatCompanion getSelectedChatCompanion() {
        return selectedChatCompanion;
    }

    public void setSelectedChatCompanion(ChatCompanion selectedChatCompanion) {
        this.selectedChatCompanion = selectedChatCompanion;
    }
}
