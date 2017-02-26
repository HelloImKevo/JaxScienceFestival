package com.schanz.jaxsciencefestival.model;

import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;

public class NewsEvent {

    @NonNull
    public final String name;
    @DrawableRes
    public final int profilePicture;

    public NewsEvent(
            @NonNull String name,
            @DrawableRes int profilePicture) {
        this.name = name;
        this.profilePicture = profilePicture;
    }
}
