package com.example.sportdoggy;

import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;

public class MainActivity extends ParentActivity implements SensorEventListener {

    static private short menuStatus = 0;
    static private Stopwatch stopwatchThread;
    static SensorManager sensorManager;
    static boolean isRunning = false;
    static Integer stepCount = 0;
    static TextView tv_Steps;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setFlags();
        setContentView(R.layout.activity_main);
        initMainMenuButtons();
        initWalkMenuButtons();
        if(ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACTIVITY_RECOGNITION) == PackageManager.PERMISSION_DENIED){
            //ask for permission
            requestPermissions(new String[]{android.Manifest.permission.ACTIVITY_RECOGNITION}, 0);
        }
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
    }

    //init buttons
    private void initMainMenuButtons() {
        Button dogButton = findViewById(R.id.DogButton);
        dogButton.setOnClickListener(v -> onDogButtonClickProcessing());

        Button actButton = findViewById(R.id.healthActivityButton);
        actButton.setOnClickListener(v -> onActButtonClickProcessing());

        ImageButton goWalkButton = findViewById(R.id.goWalk);
        goWalkButton.setOnClickListener(v -> onGoForWalk());
    }

    private void initWalkMenuButtons() {
        Button goHomeButton = findViewById(R.id.goHomeButton);
        goHomeButton.setOnClickListener(v -> onGoHome());
    }

    //on click events
    private void onDogButtonClickProcessing() {
        TableLayout dogButtons = findViewById(R.id.dogActions);
        TableLayout actButtons = findViewById(R.id.additionalActions);
        int visability;
        if(menuStatus == 0 || menuStatus == 1) {
            visability = View.VISIBLE;
            if(menuStatus == 1) actButtons.setVisibility(View.GONE);
            menuStatus = 2;
        } else {
            visability = View.GONE;
            menuStatus = 0;
        }
        dogButtons.setVisibility(visability);
    }

    private void onActButtonClickProcessing() {
        TableLayout dogButtons = findViewById(R.id.dogActions);
        TableLayout actButtons = findViewById(R.id.additionalActions);
        int visability;
        if(menuStatus == 0 || menuStatus == 2) {
            visability = View.VISIBLE;
            if(menuStatus == 2) dogButtons.setVisibility(View.GONE);
            menuStatus = 1;
        } else {
            visability = View.GONE;
            menuStatus = 0;
        }
        actButtons.setVisibility(visability);
    }

    private void onGoForWalk() {
        FrameLayout mainFrame = findViewById(R.id.mainFrame);
        mainFrame.setVisibility(View.GONE);
        FrameLayout walkScreen = findViewById(R.id.walkScreen);
        walkScreen.setVisibility(View.VISIBLE);
        startStopwatch();
        tv_Steps = findViewById(R.id.distance);
        onStepCounterResume();
    }

    private void onGoHome() {
        stopwatchThread.stopWalking();
        sensorManager.unregisterListener(this);
        FrameLayout walkScreen = findViewById(R.id.walkScreen);
        walkScreen.setVisibility(View.GONE);
        FrameLayout mainFrame = findViewById(R.id.mainFrame);
        mainFrame.setVisibility(View.VISIBLE);
        onStepCounterPause();
    }

    //Time
    private void startStopwatch() {
        TextView timer = findViewById(R.id.timer);
        TextView stopwatch = findViewById(R.id.stopwatch);
        stopwatchThread = new Stopwatch(stopwatch, timer);
        stopwatchThread.start();
    }

    protected void onStepCounterResume()
    {
        isRunning = true;
        Sensor countSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR);
        if (countSensor != null) {
            sensorManager.registerListener(this, countSensor, SensorManager.SENSOR_DELAY_UI);
        } else {
            Toast.makeText(this, "STEP_COUNTER sensor not found", Toast.LENGTH_SHORT).show();
        }
    }

    protected void onStepCounterPause()
    {
        isRunning = false;
        stepCount = 0;
        tv_Steps.setText(String.valueOf(stepCount));
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        stepCount += Math.round(Float.parseFloat(String.valueOf(event.values[0])));
        if (isRunning) {
            tv_Steps.setText(String.valueOf(stepCount));
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}