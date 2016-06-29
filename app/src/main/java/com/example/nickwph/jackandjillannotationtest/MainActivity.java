package com.example.nickwph.jackandjillannotationtest;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends Activity {

    @BindView(R.id.hello) TextView view;

    @Inject Utility utility;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // init dagger and test running injected untility
        ((MainApplication) getApplication()).getComponent().inject(this);
        SampleAutoValue value = SampleAutoValue.builder().name("auto value").build();
        SampleJson json = utility.getSampleJson();
        utility.run();

        // test if view binding works
        ButterKnife.bind(this);
        view.setText("hello " + json.hello + " and " + value.getName());
        view.setOnClickListener(view1 -> {
            Toast.makeText(this, "hello back", Toast.LENGTH_LONG).show();
        });

        // test java8 stream and lambda api works
        List<Integer> list = Arrays.asList(3, 1, 2);
        list.stream()
                .sorted()
                .map(String::valueOf)
                .forEach(integer -> view.setText(view.getText() + " => " + integer));
    }
}
