package com.example.sleeptimer;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.CountDownTimer;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.practiceui2.R;
import com.mikhaellopez.circularprogressbar.CircularProgressBar;

public class MainActivity extends AppCompatActivity {
    protected int minLeft;
    protected CircularProgressBar circularProgressBar;
    protected TextView minutesLeft;
    protected Button minusMinutesBnt,plusMinutesBnt,plus1,plus10,minus1,minus10,startBnt,endBnt,extendBnt;
    protected CountDownTimer myCountDownTimer;
    protected AudioManager am;
    protected long progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        circularProgressBar = findViewById(R.id.circularProgressbar);
        minutesLeft = findViewById(R.id.minutesLeft);
        minusMinutesBnt = findViewById(R.id.minusMinutesBnt);
        plusMinutesBnt = findViewById(R.id.plusMinutesBnt);
        plus1 = findViewById(R.id.plus1);
        plus10 = findViewById(R.id.plus10);
        minus1 = findViewById(R.id.minus1);
        minus10 = findViewById(R.id.minus10);
        startBnt = findViewById(R.id.startBnt);
        endBnt = findViewById(R.id.endBnt);
        extendBnt = findViewById(R.id.extendBnt);

    }

    public void controlMinutes(View view) {
        Button bnt = (Button) view;
        int tapped = Integer.parseInt(bnt.getTag().toString());
        minLeft = Integer.parseInt((String) minutesLeft.getText());
        if (tapped + minLeft < 1) {
            return;
        }
        int buff = minLeft + tapped;
        minutesLeft.setText(Integer.toString(buff));
    }

    public void disappearedButton() {
        minusMinutesBnt.setVisibility(View.INVISIBLE);
        plusMinutesBnt.setVisibility(View.INVISIBLE);
        plus1.setVisibility(View.INVISIBLE);
        plus10.setVisibility(View.INVISIBLE);
        minus1.setVisibility(View.INVISIBLE);
        minus10.setVisibility(View.INVISIBLE);
        endBnt.setVisibility(View.VISIBLE);
        extendBnt.setVisibility(View.VISIBLE);
        circularProgressBar.setVisibility(View.VISIBLE);
    }

    public void appearButton() {
        minusMinutesBnt.setVisibility(View.VISIBLE);
        plusMinutesBnt.setVisibility(View.VISIBLE);
        plus1.setVisibility(View.VISIBLE);
        plus10.setVisibility(View.VISIBLE);
        minus1.setVisibility(View.VISIBLE);
        minus10.setVisibility(View.VISIBLE);
        endBnt.setVisibility(View.INVISIBLE);
        extendBnt.setVisibility(View.INVISIBLE);
        startBnt.setVisibility(View.VISIBLE);
        circularProgressBar.setVisibility(View.INVISIBLE);
    }

    public void startCountDown(View view) {
        disappearedButton();
        startBnt.setVisibility(View.INVISIBLE);
        endBnt.setVisibility(View.VISIBLE);
        extendBnt.setVisibility(View.VISIBLE);
        minLeft = Integer.parseInt((String) minutesLeft.getText());
        circularProgressBar.setProgressMax(minLeft * 60);
        myCountDownTimer(minLeft);
    }
    public void myCountDownTimer(int minLeft)
    {
        startService();
        myCountDownTimer = new CountDownTimer(minLeft * 60000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                circularProgressBar.setProgress(millisUntilFinished / 1000);
                minutesLeft.setText(Long.toString(millisUntilFinished / 60000));
            }

            @Override
            public void onFinish() {
                myCountDownTimer.cancel();
                appearButton();
                minutesLeft.setText("1");
                turnOffAudio();
                stopService();
            }
        }.start();
    }
    public void endButton(View view) {
        myCountDownTimer.cancel();
        appearButton();
        minutesLeft.setText("1");
        stopService();

    }
    public void extendButton(View view) {
        minLeft = Integer.parseInt((String) minutesLeft.getText());
        minLeft += 5;
        circularProgressBar.setProgressMax(minLeft * 60);
        myCountDownTimer.cancel();

        myCountDownTimer(minLeft);
    }

    public void turnOffAudio() {
        am = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        am.setStreamMute(AudioManager.STREAM_MUSIC, true);
    }
    public void startService() {
        minLeft = Integer.parseInt((String) minutesLeft.getText());
        Intent serviceIntent = new Intent(this,sleepTimerService.class);
        serviceIntent.putExtra("minLeft",minLeft+" minutes left");
        //serviceIntent.putExtra("inputExtra",(minLeft)+" minutes left");
        ContextCompat.startForegroundService(this,serviceIntent);

    }
    public void stopService() {
        Intent serviceIntent = new Intent(this, sleepTimerService.class);
        stopService(serviceIntent);
    }
}


