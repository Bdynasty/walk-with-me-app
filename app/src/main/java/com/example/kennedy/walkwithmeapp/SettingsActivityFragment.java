package com.example.kennedy.walkwithmeapp;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceGroup;
import android.preference.PreferenceManager;
import android.preference.SwitchPreference;




public class SettingsActivityFragment extends PreferenceFragment implements SharedPreferences.OnSharedPreferenceChangeListener {


    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        addPreferencesFromResource(R.xml.preferences);
        PreferenceManager.setDefaultValues(getContext(), R.xml.preferences,
                false);
        initSummary(getPreferenceScreen());


    }

    @Override
    public void onResume() {

        getPreferenceScreen().getSharedPreferences()
                .registerOnSharedPreferenceChangeListener(this);


        super.onResume();
    }
    @Override
    public void onPause() {
        super.onPause();
        // Unregister the listener whenever a key changes
        getPreferenceScreen().getSharedPreferences()
                .unregisterOnSharedPreferenceChangeListener(this);
    }
    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        updatePrefSummary(findPreference(key));

    }


    private void updatePrefSummary(Preference p) {


        if (p instanceof android.preference.SwitchPreference ) {
            android.preference.SwitchPreference switchPreference = (SwitchPreference)findPreference("pref_weight");
            switchPreference.setDefaultValue(true);
            switchPreference.setSummaryOff("Lbs");
            switchPreference.setSummaryOn("Kg");


        }
        if (p instanceof EditTextPreference) {

            EditTextPreference editTextPref = (EditTextPreference) p;
            if (p.getKey().equals("pref_password"))
            {
                p.setSummary("******");
            }
            else if (p.getKey().equals("pref_target_weight")){

                p.setSummary(editTextPref.getText());
            }
            else {
                p.setSummary(editTextPref.getText());
            }

        }

    }
    private void initSummary(Preference p) {
        if (p instanceof PreferenceGroup) {
            PreferenceGroup pGrp = (PreferenceGroup) p;
            for (int i = 0; i < pGrp.getPreferenceCount(); i++) {
                initSummary(pGrp.getPreference(i));
            }
        } else {
            updatePrefSummary(p);
        }
    }
}
