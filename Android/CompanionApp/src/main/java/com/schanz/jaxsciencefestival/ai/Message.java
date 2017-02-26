package com.schanz.jaxsciencefestival.ai;

import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

public class Message {

    public enum Source {
        AI,
        USER
    }

    public enum Type {
        QUESTION,
        RESPONSE,
        GREETING,
        STATEMENT
    }

    public enum Mood {
        POSITIVE, NEUTRAL, NEGATIVE
    }

    @SerializedName("source")
    @NonNull
    public final Source source;

    @SerializedName("type")
    @NonNull
    public final Type type;

    @SerializedName("mood")
    @NonNull
    public final Mood mood;

    @SerializedName("text")
    @NonNull
    public final String text;

    public Message(
            @NonNull Source source,
            @NonNull Type type,
            @NonNull Mood mood,
            @NonNull String text) {
        this.source = source;
        this.type = type;
        this.mood = mood;
        this.text = text;
    }
}
