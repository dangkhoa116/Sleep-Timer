package com.example.sleeptimer;

import android.os.CountDownTimer;

public class SleepTimer {
    protected CountDownTimer myCountDownTimer;
    public SleepTimer(int minLeft)
    {
        this.myCountDownTimer = new CountDownTimer(minLeft * 60000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {

            }
        };
    }
}
