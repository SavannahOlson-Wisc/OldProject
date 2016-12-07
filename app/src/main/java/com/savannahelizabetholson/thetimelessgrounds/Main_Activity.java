package com.savannahelizabetholson.thetimelessgrounds;

import android.content.Intent;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class Main_Activity extends AppCompatActivity implements MainFragment.OnMenuButtonTappedListener {

    /**
     * This method opens up the fragment
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        PreferenceManager.setDefaultValues(this,R.xml.preferences, false);

        if(findViewById(R.id.fragmentContainer) != null) {

            // get an instance of from Activity
            FragmentManager fragmentManager = getSupportFragmentManager();
            Fragment fragment = fragmentManager.findFragmentById(R.id.fragmentContainer);

            if (fragment == null) {
                fragment = new MainFragment();

                // begin and return a fragment transaction
                fragmentManager.beginTransaction()
                        .add(R.id.fragmentContainer, fragment)
                        .commit();
            }


        }

    }

    /**
     * This is a callback method used by MainFragment to open MyPreferencesActivity
     */
    public void menuButtonSelected() {
        //Open up menu (Preference Activity) and do the things there.
        Intent i = new Intent(this, MyPreferencesActivity.class);
        startActivity(i);

    }
}
