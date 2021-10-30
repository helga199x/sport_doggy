package com.example.sportdoggy;

import android.widget.TextView;

import java.util.concurrent.TimeUnit;

public class Stopwatch extends Thread {
    private boolean walking;
    private TextView stopwatch;
    private Integer stopwatchTime;
    private TextView timer;
    private Integer timerTime;

    Stopwatch(TextView stopwatch, TextView timer) {
        this.walking = false;
        this.stopwatch = stopwatch;
        this.stopwatchTime = 0;
        this.timer = timer;
        this.timerTime = 1200;
    }

    public void run() {
        walking = true;
        while(walking) {
            if (timerTime > 0) {
                String result = timerTime();
                timerTime -= 1;
                timer.setText(result);
            }
            String result = time();
            stopwatchTime += 1;
            stopwatch.setText(result);
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public String hours() {
        String hours = Integer.toString(stopwatchTime/3600);
        return hours.length() == 1 ? "0".concat(hours) : hours;
    }

    public String minutes() {
        String minutes = Integer.toString((stopwatchTime%3600)/60);
        return minutes.length() == 1 ? "0".concat(minutes) : minutes;
    }

    public String seconds() {
        String seconds = Integer.toString((stopwatchTime%3600)%60);
        return seconds.length() == 1 ? "0".concat(seconds) : seconds;
    }

    public String time() {
        return hours().concat(".").concat(minutes()).concat(".").concat(seconds());
    }

    public String timerHours() {
        String hours = Integer.toString(timerTime/3600);
        return hours.length() == 1 ? "0".concat(hours) : hours;
    }

    public String timerMinutes() {
        String minutes = Integer.toString((timerTime%3600)/60);
        return minutes.length() == 1 ? "0".concat(minutes) : minutes;
    }

    public String timerSeconds() {
        String seconds = Integer.toString((timerTime%3600)%60);
        return seconds.length() == 1 ? "0".concat(seconds) : seconds;
    }

    public String timerTime() {
        return timerHours().concat(":").concat(timerMinutes()).concat(":").concat(timerSeconds());
    }

    public void stopWalking() {
        this.walking = false;
        this.stopwatchTime = 0;
        stopwatch.setText("00.00.00");
    }
}
