package com.schanz.jaxsciencefestival.util;

public class SingletonTemplate {

    //region Singleton Implementation

    private static SingletonTemplate sInstance;

    public static SingletonTemplate getInstance() {
        if (sInstance == null) {
            synchronized (SingletonTemplate.class) {
                if (sInstance == null) {
                    sInstance = new SingletonTemplate();
                }
            }
        }
        return sInstance;
    }

    private SingletonTemplate() {
    }

    //endregion
}
