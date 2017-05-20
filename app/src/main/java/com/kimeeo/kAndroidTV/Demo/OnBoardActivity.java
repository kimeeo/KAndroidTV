package com.kimeeo.kAndroidTV.Demo;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;

import com.kimeeo.kAndroidTV.Demo.fragments.OnboardingFragment;
import com.kimeeo.kAndroidTV.Demo.fragments.VideoDetailsFragment;

/**
 * Created by BhavinPadhiyar on 5/18/17.
 */

public class OnBoardActivity extends Activity {

    public static final String SHARED_ELEMENT_NAME = "hero";
    public static final String MOVIE = "Movie";

    @Override public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);
        if (savedInstanceState == null) {
            Fragment fragment = new OnboardingFragment();
            getFragmentManager().beginTransaction().replace(R.id.fragmentContainer, fragment).commit();
        }
    }
    @Override
    public boolean onSearchRequested() {
        startActivity(new Intent(this, SearchActivity.class));
        return true;
    }
}

