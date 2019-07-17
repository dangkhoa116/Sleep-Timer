package com.example.sleeptimer;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.net.wifi.WifiManager;
import android.os.CountDownTimer;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
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
    protected SharedPreferences sharedPreferences;
    protected SharedPreferences.Editor editor;

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
        sharedPreferences = getSharedPreferences("com.example.sleeptimer", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        minutesLeft.setText(Integer.toString(sharedPreferences.getInt("minLeft",1)));

    }

    public void controlMinutes(View view) {
        Button bnt = (Button) view;
        int tapped = Integer.parseInt(bnt.getTag().toString());
        minLeft = Integer.parseInt((String) minutesLeft.getText());
        if (tapped + minLeft < 1) {
            minutesLeft.setText("1");
            editor.putInt("minLeft",1);
            editor.commit();
        }
        else
        {
            int buff = minLeft + tapped;
            minutesLeft.setText(Integer.toString(buff));
            editor.putInt("minLeft",buff);
            editor.commit();
        }

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
        myCountDownTimer = new CountDownTimer(minLeft * 60000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                circularProgressBar.setProgress(millisUntilFinished / 1000);
                minutesLeft.setText(Long.toString(millisUntilFinished / 60000));
                startService();
            }
            @Override
            public void onFinish() {
                myCountDownTimer.cancel();
                appearButton();
                minutesLeft.setText("1");
                getCheckAction();
                stopService();
            }
        }.start();
    }
    public void endButton(View view) {
        myCountDownTimer.cancel();
        appearButton();
        minutesLeft.setText(Integer.toString(sharedPreferences.getInt("minLeft",1)));
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
        am.requestAudioFocus(null,AudioManager.STREAM_MUSIC,AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
    }
    public void turnOffBluetooth()
    {
        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBluetoothAdapter.isEnabled()) {
            mBluetoothAdapter.disable();
        }
    }
    public void turnOffWifi()
    {
        WifiManager wifi = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        wifi.setWifiEnabled(false);
    }
    public void startService() {
        minLeft = Integer.parseInt((String) minutesLeft.getText());
        Intent serviceIntent = new Intent(this,sleepTimerService.class);
        serviceIntent.putExtra("minLeft",minLeft +" minutes left");
        ContextCompat.startForegroundService(this,serviceIntent);

    }
    public void openSetting(View view)
    {
        Intent intent = new Intent(MainActivity.this, setting.class);
        startActivity(intent);
    }

    public void stopService() {
        Intent serviceIntent = new Intent(this, sleepTimerService.class);
        stopService(serviceIntent);
    }
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

    }
    // fix back button
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            moveTaskToBack(true);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
    public void getCheckAction()
    {
        sharedPreferences = getSharedPreferences("com.example.sleeptimer", Context.MODE_PRIVATE);
        boolean music = sharedPreferences.getBoolean("music",false);
        boolean wifi = sharedPreferences.getBoolean("wifi",false);
        boolean bluetooth = sharedPreferences.getBoolean("bluetooth",false);
        if (music)
        {
            turnOffAudio();
        }
        if (wifi)
        {
            turnOffWifi();
        }
        if (bluetooth)
        {
            turnOffBluetooth();
        }


    }
}


