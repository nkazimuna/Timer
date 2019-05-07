package com.example.specialtimer;

import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    SeekBar timerSeekbar;
    TextView timerTextView;
    Button controllerButton;
    boolean counterIsActive = false;
    CountDownTimer countDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timerSeekbar = (SeekBar) findViewById(R.id.timerSeekBar);
        timerTextView = (TextView) findViewById(R.id.timerTextView);
        controllerButton = (Button) findViewById(R.id.controllerButton);

        timerSeekbar.setMax(600);
        timerSeekbar.setProgress(30);

        timerSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                updateTimer(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }

    public void controlTimer(View view) {

        if (counterIsActive == false) {
            counterIsActive = true;
            timerSeekbar.setEnabled(false);
            controllerButton.setText("STOP");

            Log.i("Button Pressed : ", "Pressed");
            countDownTimer = new CountDownTimer(timerSeekbar.getProgress() * 1000 + 100, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    updateTimer((int) millisUntilFinished / 1000);
                }

                @Override
                public void onFinish() {
                    resetTimer();
                    Log.i("Finished ", "timer done");
                    MediaPlayer mediaPlayer = MediaPlayer.create(MainActivity.this, R.raw.asylum);
                    mediaPlayer.start();
                }
            }.start();
        } else {

            resetTimer();

        }
    }

    public void updateTimer(int secondsLeft) {
        int minutes = (int) secondsLeft / 60;
        int seconds = secondsLeft - minutes * 60;

        String secondString = Integer.toString(seconds);
        /*if (secondString == "0") {
            secondString = "00";
        }else */
        if (seconds <= 9) {
            secondString = "0" + seconds;
        }


        timerTextView.setText(Integer.toString(minutes) + ":" + secondString);
    }

    public void resetTimer() {
        timerTextView.setText("00:30");
        timerSeekbar.setProgress(30);
        countDownTimer.cancel();
        controllerButton.setText("START");
        timerSeekbar.setEnabled(true);
        counterIsActive = false;
    }

}
