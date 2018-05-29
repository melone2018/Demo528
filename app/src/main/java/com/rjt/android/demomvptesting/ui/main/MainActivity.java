package com.rjt.android.demomvptesting.ui.main;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.rjt.android.demomvptesting.R;
import com.rjt.android.demomvptesting.ui.map.MapActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void showMap(View view) {

        startActivity(new Intent(this, MapActivity.class));
    }
}
