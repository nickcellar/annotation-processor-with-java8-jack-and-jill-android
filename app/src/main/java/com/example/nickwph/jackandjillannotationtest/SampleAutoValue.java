package com.example.nickwph.jackandjillannotationtest;

import com.google.auto.value.AutoValue;

/**
 * Created by nickwph on 6/29/16.
 */

@AutoValue
abstract class SampleAutoValue {

    abstract String getName();

    static Builder builder() {
        return new AutoValue_SampleAutoValue.Builder();
    }

    @AutoValue.Builder
    abstract static class Builder {

        abstract Builder name(String name);

        abstract SampleAutoValue build();
    }
}
