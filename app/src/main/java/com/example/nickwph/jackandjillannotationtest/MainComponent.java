package com.example.nickwph.jackandjillannotationtest;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by nickwph on 4/10/16.
 */
@Singleton
@Component(modules = MainModule.class)
interface MainComponent {

    void inject(MainActivity mainActivity);
}
