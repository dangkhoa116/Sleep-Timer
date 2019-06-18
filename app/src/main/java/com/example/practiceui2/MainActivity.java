package com.example.practiceui2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.mikhaellopez.circularprogressbar.CircularProgressBar;

public class MainActivity extends AppCompatActivity {
    private CircularProgressBar circularProgressBar;
    private TextView minutesLeft;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        circularProgressBar = findViewById(R.id.circularProgressbar);
        minutesLeft = findViewById(R.id.minutesLeft);

    }

    public void controlMinutes(View view)
    {
        Button bnt = (Button) view;
        int tapped = Integer.parseInt(bnt.getTag().toString());
        int minLeft = Integer.parseInt((String) minutesLeft.getText());
        if (tapped+minLeft < 1)
        {
            return;
        }
        int buff = minLeft + tapped;
        minutesLeft.setText(Integer.toString(buff));
    }


}
