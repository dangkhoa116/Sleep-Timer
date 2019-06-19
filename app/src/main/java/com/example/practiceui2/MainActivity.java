package com.example.practiceui2;

import android.content.Context;
import android.media.AudioManager;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.mikhaellopez.circularprogressbar.CircularProgressBar;

public class MainActivity extends AppCompatActivity {
    private int minLeft;
    private CircularProgressBar circularProgressBar;
    private TextView minutesLeft;
    private Button minusMinutesBnt;
    private Button plusMinutesBnt;
    private Button plus1;
    private Button plus10;
    private Button minus1;
    private Button minus10;
    private Button startBnt;
    private Button endBnt;
    private Button extendBnt;
    private CountDownTimer myCountDownTimer;
    private AudioManager am;

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

    public void controlMinutes(View view)
    {
        Button bnt = (Button) view;
        int tapped = Integer.parseInt(bnt.getTag().toString());
        minLeft = Integer.parseInt((String) minutesLeft.getText());
        if (tapped+minLeft < 1)
        {
            return;
        }
        int buff = minLeft + tapped;
        minutesLeft.setText(Integer.toString(buff));
    }
    public void disappearedButton()
    {
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
    public void appearButton()
    {
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
    public void startCountDown(View view)
    {
       disappearedButton();
       startBnt.setVisibility(View.INVISIBLE);
       endBnt.setVisibility(View.VISIBLE);
       extendBnt.setVisibility(View.VISIBLE);
       minLeft = Integer.parseInt((String) minutesLeft.getText());
       circularProgressBar.setProgressMax(minLeft*60);

       myCountDownTimer = new CountDownTimer(minLeft*60000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                circularProgressBar.setProgress(millisUntilFinished/1000);
                minutesLeft.setText(Long.toString(millisUntilFinished/60000));
            }
            @Override
            public void onFinish() {
                myCountDownTimer.cancel();
                appearButton();
                minutesLeft.setText("1");
                turnOffAudio();
            }
       }.start();
    }
    public void endButton(View view)
    {
        myCountDownTimer.cancel();
        appearButton();
        minutesLeft.setText("1");

    }
    public void extendButton(View view)
    {
        myCountDownTimer.cancel();
        minLeft = Integer.parseInt((String) minutesLeft.getText());
        minLeft+=5;
        circularProgressBar.setProgressMax(minLeft*60);
        myCountDownTimer = new CountDownTimer(minLeft*60000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                circularProgressBar.setProgress(millisUntilFinished/1000);
                minutesLeft.setText(Long.toString(millisUntilFinished/60000));
            }
            @Override
            public void onFinish() {
                myCountDownTimer.cancel();
                appearButton();
                minutesLeft.setText("1");
                turnOffAudio();
            }
        }.start();
    }
    public void turnOffAudio()
    {
        am = (AudioManager)getSystemService(Context.AUDIO_SERVICE);
        am.setStreamMute(AudioManager.STREAM_MUSIC, true);
    }
}
