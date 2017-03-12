package com.example.phoenix.nab;

import android.app.Application;
import android.content.Context;

/**
 * Created by Phoenix on 3/10/17.
 */

public class NABApplication extends Application {
    private static Context instance;

    public static Context get() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }
}
