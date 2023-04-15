package com.example.momandbaby;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.RatingBar;

public class Evaluate extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evaluate);
    }

    RatingBar ratingBar = findViewById(R.id.ratingBar);
    float rating = ratingBar.getRating();

}