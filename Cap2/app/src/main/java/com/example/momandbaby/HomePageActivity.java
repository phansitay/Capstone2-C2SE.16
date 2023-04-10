package com.example.momandbaby;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class HomePageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        Button buttonProfile = findViewById(R.id.btn_home);
        buttonProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomePageActivity.this,ProfileActivity.class);
                startActivity(intent);
            }
        });

        Button buttonBlog = findViewById(R.id.btn_blog);
        buttonBlog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomePageActivity.this,ForumActivity.class);
                startActivity(intent);
            }
        });

        Button buttonNotification = findViewById(R.id.btn_notification);
        buttonNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomePageActivity.this,NotificationActivity.class);
                startActivity(intent);
            }
        });


        Button buttonUpdateCalendar = findViewById(R.id.btn_update_calendar);
        buttonUpdateCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomePageActivity.this,UpdateCalendarActivity.class);
                startActivity(intent);
            }
        });
    }
}