package com.example.practiceui2;

import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

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
        minusMinutesBnt.setVisibility(View.GONE);
        plusMinutesBnt.setVisibility(View.GONE);
        plus1.setVisibility(View.GONE);
        plus10.setVisibility(View.GONE);
        minus1.setVisibility(View.GONE);
        minus10.setVisibility(View.GONE);
        circularProgressBar.setVisibility(View.VISIBLE);
    }
    public void startCountDown(View view)
    {
       disappearedButton();
       startBnt.setVisibility(View.INVISIBLE);
       endBnt.setVisibility(View.VISIBLE);
        minLeft = Integer.parseInt((String) minutesLeft.getText());
        circularProgressBar.setProgressMax(minLeft);

        new CountDownTimer(minLeft*60000, 1000*60) {
            @Override
            public void onTick(long millisUntilFinished) {
                circularProgressBar.setProgress(millisUntilFinished/60000);
                minutesLeft.setText(Long.toString(millisUntilFinished/60000));
            }

            @Override
            public void onFinish() {
                minutesLeft.setText("0");
            }
        }.start();

    }



}
