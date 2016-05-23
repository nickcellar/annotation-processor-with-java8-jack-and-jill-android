package com.example.nickwph.jackandjillannotationtest;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

//import butterknife.ButterKnife;

public class MainActivity extends Activity {

    private MainComponent component;

    @BindView(R.id.hello)
    TextView view;

    @Inject
    Utility utility;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        component = DaggerMainComponent.builder().mainModule(new MainModule(this)).build();
        component.inject(this);
        utility.run();
        ButterKnife.bind(this);
        view.setText("hello back");
        view.setOnClickListener(view1 -> {
            Toast.makeText(this, "hello back back", Toast.LENGTH_LONG).show();
        });

        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5, 6);
        list.forEach(integer -> {
            view.setText(view.getText() + " => " + integer);
        });
    }
}
