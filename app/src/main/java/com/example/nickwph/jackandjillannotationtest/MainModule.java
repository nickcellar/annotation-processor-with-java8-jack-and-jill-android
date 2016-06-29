package com.example.nickwph.jackandjillannotationtest;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by nickwph on 4/10/16.
 */
@Module
class MainModule {

    private Context context;

    MainModule(Context context) {
        this.context = context;
    }

    @Provides
    @Singleton
    Context context() {
        return context;
    }
}
