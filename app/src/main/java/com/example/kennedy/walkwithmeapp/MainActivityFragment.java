package com.example.kennedy.walkwithmeapp;


import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;


import java.util.prefs.PreferenceChangeEvent;
import java.util.prefs.PreferenceChangeListener;

import static android.content.Context.SENSOR_SERVICE;


public class MainActivityFragment extends android.support.v4.app.Fragment implements SensorEventListener,StepListener {
    private SensorManager sensorManager;
    private Sensor accel;
    private StepDetector simpleStepDetector;
    private FloatingActionButton fabStartEnd,fabWeight;
    private TextView countStep,duration,status,targetWeight;
    private ProgressBar progWeight, progStep;
    private SharedPreferences preferences;

    private int numStep,Seconds, Minutes;
    long MillisecondTime, StartTime, TimeBuff, UpdateTime = 0L ;
    final Handler handler = new Handler();
    private android.support.v4.app.Fragment fragment;


    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_main, container, false);
        preferences = PreferenceManager.getDefaultSharedPreferences(getContext());

        //button and views
        countStep = v.findViewById(R.id.stepCounterTextView);
        status =v.findViewById(R.id.activeTextView);
        duration = v.findViewById(R.id.durationTextView);
        fabStartEnd = v.findViewById(R.id.fabActive);
        fabWeight = v.findViewById(R.id.fabWeightCapture);
//        targetWeight = v.findViewById(R.id.tWeight);
        progWeight = v.findViewById(R.id.weightProgressBar);
        progStep = v.findViewById(R.id.stepsProgressBar);
        WeightFragment gw = new WeightFragment();
        setProgressValue(updateWeightProgress());
        countStep.setText(String.valueOf(updateWeightProgress()));

        // Get an instance of the sensor manager
        sensorManager= (SensorManager)getActivity().getSystemService(SENSOR_SERVICE);
        accel = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        simpleStepDetector = new StepDetector();
        simpleStepDetector.registerListener(this);

        fabStartEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(status.getText()=="FINISH")  {
                    sensorManager.unregisterListener(MainActivityFragment.this);
                    TimeBuff += MillisecondTime;
                    handler.removeCallbacks(runnable);
                    MillisecondTime = 0L ;
                    StartTime = 0L ;
                    TimeBuff = 0L ;
                    UpdateTime = 0L ;
                    Seconds = 0 ;
                    Minutes = 0 ;
                    duration.setText("00:00");
                    status.setText("START");
                    countStep.setText("0");
                    fabStartEnd.setImageResource(R.drawable.ic_start_activity);
                }
                else{
                    numStep = 0;
                    sensorManager.registerListener(MainActivityFragment.this, accel, SensorManager.SENSOR_DELAY_FASTEST);
                    StartTime = SystemClock.uptimeMillis();
                    handler.postDelayed(runnable, 0);
                   fabStartEnd.setImageResource(R.drawable.ic_running);
                    status.setText("FINISH");
                }
            }
        });
        fabWeight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment = new WeightFragment();
                android.support.v4.app.FragmentTransaction fragmentTransaction= getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.mMainframe,fragment).commit();
            }
        });

        return v;
    }
    public int updateWeightProgress(){
        int target;
        int progress;

        target = 90 - 45;
        progress = getWeight();
        return progress;
    }
    public int updateTargetSteps(){
        int target;
        String weight = preferences.getString("targetSteps","");
        target = Integer.parseInt(weight);
        return target;
    }
    public int getWeight(){
        int w;
        String weight = preferences.getString("targetWeight"," ");
        w = Integer.parseInt(weight);
        return w;
    }
    private void setProgressValue(final int progress) {

        // set the progress
        progWeight.setProgress(progress);
        // thread is used to change the progress value
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                setProgressValue(progress);
            }
        });
        thread.start();
    }


    @Override
    public void onSensorChanged(SensorEvent event) {

        if (event.sensor.getType()== Sensor.TYPE_ACCELEROMETER){
            simpleStepDetector.updateAccel(event.timestamp,event.values[0],event.values[1],event.values[2]);
        }
    }
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            MillisecondTime = SystemClock.uptimeMillis() - StartTime;
            UpdateTime = TimeBuff + MillisecondTime;
            Seconds = (int) (UpdateTime / 1000);
            Minutes = Seconds / 60;
            Seconds = Seconds % 60;

            duration.setText("" + Minutes + ":"
                    + String.format("%02d", Seconds) );
            handler.postDelayed(this, 0);
        }
    };

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    public void step(long timeNs) {
        numStep ++;

        countStep.setText(String.valueOf(numStep));
    }


}


