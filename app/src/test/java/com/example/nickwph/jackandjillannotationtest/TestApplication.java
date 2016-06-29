package com.example.nickwph.jackandjillannotationtest;

/**
 * Created by nickwph on 6/29/16.
 */
class TestApplication extends MainApplication {

    private TestComponent component = new TestComponent();

    @Override
    public MainComponent getComponent() {
        return component;
    }
}
