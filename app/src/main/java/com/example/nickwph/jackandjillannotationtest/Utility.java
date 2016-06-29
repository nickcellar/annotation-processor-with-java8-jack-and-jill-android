package com.example.nickwph.jackandjillannotationtest;

import android.content.Context;
import android.widget.Toast;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by nickwph on 5/23/16.
 */
@Singleton
class Utility {

    @Inject Context context;

    @Inject
    Utility() {
    }

    void run() {
        Toast.makeText(context, "hello from utility", Toast.LENGTH_LONG).show();
    }
}
