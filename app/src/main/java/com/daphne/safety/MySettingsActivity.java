//package com.daphne.safety;
//
//import android.os.Bundle;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//
//public class MySettingsActivity extends AppCompatActivity {
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
////        getSupportFragmentManager()
////                .beginTransaction()
////                .replace(R.id.settings_container, new MySettingsFragment())
////                .commit();
//    }
//}

package com.daphne.safety;

import android.os.Bundle;


import androidx.preference.PreferenceFragmentCompat;


public class MySettingsActivity extends PreferenceFragmentCompat {

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootkey) {
        super.onCreate(savedInstanceState);
        setPreferencesFromResource(R.xml.preferences, rootkey);
    }

}
