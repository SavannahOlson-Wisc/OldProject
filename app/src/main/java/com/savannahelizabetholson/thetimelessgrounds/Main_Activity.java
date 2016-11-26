package com.savannahelizabetholson.thetimelessgrounds;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class Main_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

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
}
