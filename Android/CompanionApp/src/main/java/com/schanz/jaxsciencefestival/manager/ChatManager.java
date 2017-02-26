package com.schanz.jaxsciencefestival.manager;

import android.support.annotation.NonNull;

import com.schanz.jaxsciencefestival.ai.ChatCompanion;

public class ChatManager {

    private static ChatManager sInstance;

    private ChatCompanion selectedChatCompanion;

    private ChatManager() {
    }

    @NonNull
    public static ChatManager instance() {
        if (sInstance == null) {
            synchronized (ChatManager.class) {
                if (sInstance == null) {
                    sInstance = new ChatManager();
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
