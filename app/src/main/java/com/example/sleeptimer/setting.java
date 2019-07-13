package com.example.sleeptimer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.practiceui2.R;

public class setting extends AppCompatActivity {
    protected ImageView backBnt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        backBnt = findViewById(R.id.backBnt);
    }
    public void backToMain(View view)
    {
        onBackPressed();
    }
}
