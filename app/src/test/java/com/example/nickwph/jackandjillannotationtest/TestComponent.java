package com.example.nickwph.jackandjillannotationtest;

/**
 * Created by nickwph on 6/29/16.
 */
class TestComponent implements MainComponent {

    private Utility utility;

    void setUtility(Utility utility) {
        this.utility = utility;
    }

    @Override
    public void inject(MainActivity mainActivity) {
        mainActivity.utility = utility;
    }
}
