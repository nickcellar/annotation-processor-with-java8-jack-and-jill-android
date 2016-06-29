package com.example.nickwph.jackandjillannotationtest;

import android.content.Context;
import android.widget.Toast;

import com.bluelinelabs.logansquare.LoganSquare;

import java.io.IOException;

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

    SampleJson getSampleJson() {
        try {
            return LoganSquare.parse(context.getResources().openRawResource(R.raw.sample), SampleJson.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
