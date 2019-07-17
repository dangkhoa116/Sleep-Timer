package com.example.sleeptimer;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.practiceui2.R;
import com.varunest.sparkbutton.SparkButton;
import com.varunest.sparkbutton.SparkEventListener;

public class setting extends AppCompatActivity {
    protected ImageView backBnt;
    protected SparkButton music;
    protected SparkButton bluetooth;
    protected SparkButton wifi;
    protected SharedPreferences sharedPreferences;
    protected SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        backBnt = findViewById(R.id.backBnt);
        music = findViewById(R.id.spark_button);
        wifi = findViewById(R.id.spark_button_wifi);
        bluetooth = findViewById(R.id.spark_button_bluetooth);
        sharedPreferences = getSharedPreferences("com.example.sleeptimer", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        music.setChecked(sharedPreferences.getBoolean("music",false));
        wifi.setChecked(sharedPreferences.getBoolean("wifi",false));
        bluetooth.setChecked(sharedPreferences.getBoolean("bluetooth",false));
        music.setEventListener(new SparkEventListener() {
            @Override
            public void onEvent(ImageView button, boolean buttonState) {
                if(buttonState)
                {
                    editor.putBoolean("music",buttonState);
                    editor.commit();
                }
                else
                {
                    editor.putBoolean("music",false);
                    editor.commit();
                }
            }

            @Override
            public void onEventAnimationEnd(ImageView button, boolean buttonState) {

            }

            @Override
            public void onEventAnimationStart(ImageView button, boolean buttonState) {

            }
        });
        wifi.setEventListener(new SparkEventListener() {
            @Override
            public void onEvent(ImageView button, boolean buttonState) {
                if(buttonState)
                {
                    editor.putBoolean("wifi",buttonState);
                    editor.commit();
                }
                else
                {
                    editor.putBoolean("wifi",false);
                    editor.commit();
                }
            }

            @Override
            public void onEventAnimationEnd(ImageView button, boolean buttonState) {

            }

            @Override
            public void onEventAnimationStart(ImageView button, boolean buttonState) {

            }
        });
        bluetooth.setEventListener(new SparkEventListener() {
            @Override
            public void onEvent(ImageView button, boolean buttonState) {
                if(buttonState)
                {
                    editor.putBoolean("bluetooth",buttonState);
                    editor.commit();
                }
                else
                {
                    editor.putBoolean("bluetooth",false);
                    editor.commit();
                }
            }

            @Override
            public void onEventAnimationEnd(ImageView button, boolean buttonState) {

            }

            @Override
            public void onEventAnimationStart(ImageView button, boolean buttonState) {

            }
        });

    }
    public void backToMain(View view)
    {
        onBackPressed();
    }



}
