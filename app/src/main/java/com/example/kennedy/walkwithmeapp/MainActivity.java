package com.example.kennedy.walkwithmeapp;


import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener{
    private BottomNavigationView mMainNav;
    private android.support.v4.app.Fragment fragment;


    // keys for reading data from SharedPreferences
    public static final String WSYSTEM = "pref_weight";

    private boolean phoneDevice = true; // used to force portrait mode

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        if (phoneDevice) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        // navigation bar code
        mMainNav =findViewById(R.id.navigationBar);
        mMainNav.setOnNavigationItemSelectedListener(this);
        loadFragment(new MainActivityFragment());
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
        switch (item.getItemId()) {
            case R.id.action_settings:
                Intent preferencesIntent = new Intent(this,SettingsActivity.class);
                startActivity(preferencesIntent);
                return super.onOptionsItemSelected(item);
            case R.id.camera:
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivity(intent);

                return super.onOptionsItemSelected(item);
            default:
                return super.onOptionsItemSelected(item);
        }

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

            default:
                return loadFragment(fragment);
        }

    }
        @Override
    protected void onStart(){
        super.onStart();



    }

    @Override
    protected void onStop(){
        super.onStop();

    }



}
