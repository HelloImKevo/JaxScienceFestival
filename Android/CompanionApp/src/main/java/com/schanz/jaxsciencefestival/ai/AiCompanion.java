package com.schanz.jaxsciencefestival.ai;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.UiThread;

public abstract class AiCompanion {

    @UiThread
    public abstract void onUserResponse(@NonNull Message message);

    @NonNull
    public abstract Message getMessage(@NonNull Context c, @NonNull Message.Type type, @NonNull Message.Mood mood);
}
