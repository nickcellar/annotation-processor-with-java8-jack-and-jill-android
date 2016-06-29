package com.example.nickwph.jackandjillannotationtest;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;

import java.util.List;

/**
 * Created by nickwph on 6/29/16.
 */
@JsonObject
class SampleJson {

    @JsonField String hello;
    @JsonField(name = "double_value") Double doubleValue;
    @JsonField List<Integer> numbers;
}
