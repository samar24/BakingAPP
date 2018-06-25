package com.example.samar.backingapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Samar on 20/06/2018.
 */

public class StepActivity  extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container_Step, new StepFragment())
                    .commit();
        } else {

        }

    }

}