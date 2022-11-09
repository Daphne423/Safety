package com.daphne.safety;

import android.os.Bundle;


import androidx.preference.PreferenceFragmentCompat;


public class MySettingsFragment extends PreferenceFragmentCompat {

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootkey) {
        super.onCreate(savedInstanceState);
        setPreferencesFromResource(R.xml.preferences, rootkey);
    }

}