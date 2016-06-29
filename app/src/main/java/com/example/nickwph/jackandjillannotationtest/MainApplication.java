package com.example.nickwph.jackandjillannotationtest;

import android.app.Application;

/**
 * Created by nickwph on 6/29/16.
 */

public class MainApplication extends Application {

    private MainComponent component;

    @Override
    public void onCreate() {
        super.onCreate();
        component = DaggerMainComponent.builder()
                .mainModule(new MainModule(this))
                .build();
    }

    public MainComponent getComponent() {
        return component;
    }
}
