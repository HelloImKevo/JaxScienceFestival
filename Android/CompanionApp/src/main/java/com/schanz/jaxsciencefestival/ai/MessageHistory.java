package com.schanz.jaxsciencefestival.ai;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.ArrayDeque;
import java.util.Deque;

public class MessageHistory {

    @NonNull
    public final Deque<Message> messages;

    public MessageHistory() {
        this.messages = new ArrayDeque<>();
    }

    public boolean isEmpty() {
        return messages.isEmpty();
    }

    public void addMessage(@Nullable Message message) {
        if (message != null) {
            messages.push(message);
        }
    }

    @Nullable
    public Message getMostRecentMessage() {
        if (messages.isEmpty()) {
            return null;
        }
        return messages.peek();
    }
}
