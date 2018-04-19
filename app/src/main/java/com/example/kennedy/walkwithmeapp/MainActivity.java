package com.example.kennedy.walkwithmeapp;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

//implements SensorEventListener,StepListener
public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
    private SensorManager sensorManager;
    private Sensor accel;
    private StepDetector simpleStepDetector;
    private BottomNavigationView mMainNav;
    private FrameLayout mainframe;
    private android.support.v4.app.Fragment fragment;
    private Button btnStartEnd;
    private TextView countStep,distance,time;
    private int numStep;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Get an instance of the sensor manager
//        countStep = (TextView)findViewById(R.id.stepCounterTextView);
//        btnStartEnd = (Button)findViewById(R.id.btnActive);
//        sensorManager= (SensorManager) getSystemService(SENSOR_SERVICE);
//        accel = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
//        simpleStepDetector = new StepDetector();
//        simpleStepDetector.registerListener(this);
//
//        btnStartEnd.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(btnStartEnd.getText()=="START"){
//                    numStep = 0;
//                    sensorManager.registerListener(MainActivity.this,accel,SensorManager.SENSOR_DELAY_FASTEST);
//                    btnStartEnd.setText("FINISH");
//                }
//                else if(btnStartEnd.getText()=="FINISH"){
//                    sensorManager.unregisterListener(MainActivity.this);
//                    btnStartEnd.setText("START");
//                }
//            }
//        });


        // navigation bar code
        mMainNav =findViewById(R.id.navigationBar);
        mMainNav.setOnNavigationItemSelectedListener(this);
        loadFragment(new MainActivityFragment());



//        mainframe = (FrameLayout) findViewById(R.id.mMainframe);
//
//        mMainNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener()
//
//        {
//            @Override
//            public boolean onNavigationItemSelected (@NonNull MenuItem item){
//
//                switch (item.getItemId()) {
//                    case R.id.nav_home:
//                        fragment = new MainActivityFragment();
//                        setFragment(fragment);
//                        return true;
//                    case R.id.nav_goal:
//                        fragment = new GoalFragment();
//                        setFragment(fragment);
//                        return true;
//                    case R.id.nav_account:
//                        fragment = new AccountFragment();
//                        setFragment(fragment);
//                        return true;
//                    default:
//                        return false;
//                }
//            }
//        });




    }

    private boolean loadFragment(android.support.v4.app.Fragment fragment) {
        if(fragment  != null){
        android.support.v4.app.FragmentTransaction fragmentTransaction= getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.mMainframe,fragment).commit();
        return true;
        }
        return false;

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

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.nav_home:
                fragment = new MainActivityFragment();
                loadFragment(fragment);
                return true;
            case R.id.nav_goal:
                fragment = new GoalFragment();
                loadFragment(fragment);
                return true;
            case R.id.nav_account:
                fragment = new AccountFragment();
                loadFragment(fragment);
                return true;
            default:
                return loadFragment(fragment);
        }

    }

//    @Override
//    public void onSensorChanged(SensorEvent event) {
//
//        if (event.sensor.getType()== Sensor.TYPE_ACCELEROMETER){
//            simpleStepDetector.updateAccel(event.timestamp,event.values[0],event.values[1],event.values[2]);
//        }
//    }
//
//    @Override
//    public void onAccuracyChanged(Sensor sensor, int accuracy) {
//
//    }
//
//    @Override
//    public void step(long timeNs) {
//        numStep ++;
//        countStep.setText(numStep);
//    }
}
