package com.schanz.jaxsciencefestival.model;

import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;

public class NewsEvent {

    @NonNull
    public final String headline;
    @NonNull
    public final String fullStory;
    @ColorRes
    public final int color;
    @DrawableRes
    public final int thumbnail;
    @DrawableRes
    public final int headerImage;

    public NewsEvent(
            @NonNull String headline,
            @NonNull String fullStory,
            @ColorRes int color,
            @DrawableRes int thumbnail,
            @DrawableRes int headerImage) {
        this.headline = headline;
        this.fullStory = fullStory;
        this.color = color;
        this.thumbnail = thumbnail;
        this.headerImage = headerImage;
    }
}
