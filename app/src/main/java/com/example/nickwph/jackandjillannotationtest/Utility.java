package com.example.nickwph.jackandjillannotationtest;

import android.content.Context;
import android.widget.Toast;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by nickwph on 5/23/16.
 */
@Singleton
public class Utility {

    @Inject
    Context context;

    @Inject
    public Utility() {
    }

    public void run() {
        Toast.makeText(context, "hello world", Toast.LENGTH_LONG).show();
    }
}
