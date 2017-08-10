package com.paezand.eggtimer;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    SeekBar timerSeekBar;
    TextView timerTextView;
    Button manageButton;
    boolean isRunning = false;
    CountDownTimer countDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        timerSeekBar = (SeekBar) findViewById(R.id.timerSeekBar);
        timerTextView = (TextView) findViewById(R.id.timerTextView);
        manageButton = (Button) findViewById(R.id.controlerButton);

        timerSeekBar.setMax(300);
        timerSeekBar.setProgress(150);

        calculateMinutesAndSeconds(timerSeekBar.getProgress());

        timerSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUSer) {
                calculateMinutesAndSeconds(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    public void manageActions(View view) {

        if (!isRunning) {
            updateTextInButton("Stop");
            isRunning = true;
            timerSeekBar.setEnabled(false);
            countDownTimer = new CountDownTimer(timerSeekBar.getProgress() * 1000 + 100, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    calculateMinutesAndSeconds((int) millisUntilFinished / 1000);
                    timerSeekBar.setProgress((int) millisUntilFinished / 1000);
                }

                @Override
                public void onFinish() {
                    timerTextView.setText("0:00");
                    restartCounter();
                }
            }.start();
        } else {
            stopCounter();
        }
    }

    private void calculateMinutesAndSeconds(int time) {
        int minutes = time / 60;
        int seconds = time % 60;
        String secondsS = (seconds <= 9 ? ("0" + seconds): Integer.toString(seconds));

        setTextInTextView(Integer.toString(minutes), secondsS);
    }

    private void setTextInTextView(final String minutes, final String seconds) {
        timerTextView.setText(minutes + ":" + seconds);
    }

    private void updateTextInButton(final String description) {
        manageButton.setText(description);
    }

    private void stopCounter() {
        updateTextInButton("Go!");
        isRunning = false;
        timerSeekBar.setEnabled(true);
        countDownTimer.cancel();
    }

    private void restartCounter() {
        updateTextInButton("Start");
        isRunning = false;
        timerSeekBar.setEnabled(true);
        timerSeekBar.setProgress(150);
        calculateMinutesAndSeconds(timerSeekBar.getProgress());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
