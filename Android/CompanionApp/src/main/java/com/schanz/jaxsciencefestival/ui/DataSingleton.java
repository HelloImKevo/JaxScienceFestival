package com.schanz.jaxsciencefestival.ui;

import com.schanz.jaxsciencefestival.ai.ChatCompanion;

// TODO: Delete this temporary class
public class DataSingleton {

    private ChatCompanion data;

    public ChatCompanion getData() {
        return data;
    }

    public void setData(ChatCompanion data) {
        this.data = data;
    }

    //region Singleton Implementation

    private static DataSingleton sInstance;

    public static DataSingleton getInstance() {
        if (sInstance == null) {
            synchronized (DataSingleton.class) {
                if (sInstance == null) {
                    sInstance = new DataSingleton();
                }
            }
        }
        return sInstance;
    }

    private DataSingleton() {
    }

    //endregion
}
