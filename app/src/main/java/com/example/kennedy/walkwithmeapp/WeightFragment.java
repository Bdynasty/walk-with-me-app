package com.example.kennedy.walkwithmeapp;



import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


import static android.content.Context.MODE_PRIVATE;


public class WeightFragment extends android.support.v4.app.Fragment implements SharedPreferences.OnSharedPreferenceChangeListener {
    public TextView textView;
    public NumberPicker wWeight;
    public Button wBtnSave,wDisplay;
    public File f;

    public SharedPreferences preferences;




    public WeightFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_weight, container, false);


        wWeight = v.findViewById(R.id.numberPicker);
        wBtnSave = v.findViewById(R.id.btnSave);
        wDisplay = v.findViewById(R.id.btnShow);
        textView = v.findViewById(R.id.txtStatus);
        //set max/min of number picker
        wWeight.setMinValue(45);
        wWeight.setMaxValue(300);
        wWeight.setWrapSelectorWheel(true);
        preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        PreferenceManager.getDefaultSharedPreferences(getContext()).registerOnSharedPreferenceChangeListener(this);

        wWeight.setFormatter(new NumberPicker.Formatter() {
            @Override
            public String format(int value) {
                return String.format("%02d", value);
            }
        });

        wBtnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {


                    String yFile = "myWeight.txt";
                    f = new File("/folder/");
                    FileOutputStream oFile = getContext().openFileOutput(yFile, Context.MODE_APPEND);
                    oFile.write(String.valueOf(updateWeight()).getBytes());
                    oFile.close();

                    Toast.makeText(getContext(),"weight saved",Toast.LENGTH_SHORT).show();


                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }



            }
        });
        wDisplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    String yFile = "myWeight.txt";
                    FileInputStream fileInputStream= getContext().openFileInput(yFile);
                    InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
                    BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                    StringBuffer stringBuffer = new StringBuffer();
                    String lines;
                    while ((lines=bufferedReader.readLine())!=null) {
                        stringBuffer.append(lines+"\n");

                    }
                    textView.setText("Saved Weight : " + stringBuffer.toString());
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        return v;

    }

public  String updateWeight(){

    Calendar cal = Calendar.getInstance();
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    String date = sdf.format(cal.getTime());

//    date.toString(); date.toString()+", "+
        String update = date +","+wWeight.getValue()+ "," + units();

        return update;
}

public  String units(){
    String unit;
        boolean units =preferences.getBoolean("pref_weight",true);
    if(units){
        unit = "Kg";
    }else {
        unit = "Lbs";
    }
    return unit;
}


    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        updateWeight();

        Log.d("tag","pref changed ");
    }
}
