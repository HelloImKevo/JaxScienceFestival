package com.schanz.jaxsciencefestival;

import android.app.Application;

public class App extends Application {

    private static App sInstance;

    @Override
    public void onCreate() {
        super.onCreate();

        sInstance = this;
    }

    public static App instance() {
        return sInstance;
    }

    public boolean isDebugBuild() {
        return BuildConfig.DEBUG;
    }
}
