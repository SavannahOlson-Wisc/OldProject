package com.savannahelizabetholson.thetimelessgrounds;

import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;

/**
 * Created by savannaholson on 12/6/16.
 */

public class MyPreferencesActivity extends PreferenceActivity {

    /**
     * This method does some stuff to pull up the preference thing.
     *
     * @param savedInstanceState the saved instance state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //addPreferencesFromResource(R.xml.preferences);  // running from Activity
        getFragmentManager().beginTransaction()
                .replace(android.R.id.content, new MyPreferenceFragment())
                .commit();
    }

    /**
     * This class is the preferences fragment
     */
    public static class MyPreferenceFragment extends PreferenceFragment
    {
        /**
         * This method tells the preference fragment what to load
         */
        @Override
        public void onCreate(final Bundle savedInstanceState)
        {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.preferences);

        }

        /*
        @Override
        protected void onSetInitialValue(boolean restorePersistedValue, Object defaultValue) {
            if (restorePersistedValue) {
                // restore existing state
                mCurrentValue = this.getPersistedInt(DEFAULT_VALUE);
            } else {
                // set default state from the XML attribute
                mCurrentValue = (Integer) defaultValue;
                persistInt(mCurrentValue);
            }


        }
        */

        /*
        @Override
        protected void onDialogClosed(boolean positiveResult) {
            // When the user selects ok, persist the new value
            if (positiveResult) {
                persistInt(mNewValue);
            }
        }
        */
    }
}