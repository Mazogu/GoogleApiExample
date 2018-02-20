package com.example.micha.locationandmaps;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewGroup;

public class GeoActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewGroup view = findViewById(R.id.content);
        getLayoutInflater().inflate(R.layout.activity_geo, view);
    }
}
