package com.kimeeo.kAndroidTV.Demo;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;

import com.kimeeo.kAndroidTV.Demo.fragments.PageFragment;

/**
 * Created by BhavinPadhiyar on 5/18/17.
 */

public class PageActivity extends Activity {

    /**
     * Called when the activity is first created.
     */
    @Override public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);
        if (savedInstanceState == null) {
            Fragment fragment = new PageFragment();
            getFragmentManager().beginTransaction().replace(R.id.fragmentContainer, fragment).commit();
        }
    }
    @Override
    public boolean onSearchRequested() {
        startActivity(new Intent(this, SearchActivity.class));
        return true;
    }

}

