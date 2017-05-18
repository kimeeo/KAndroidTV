package com.kimeeo.kAndroidTV.Demo;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;

import com.kimeeo.kAndroidTV.Demo.searchFragment.SearchFragment;
import com.kimeeo.kAndroidTV.Demo.verticalGridFragment.VerticalGridFragment;

/**
 * Created by BhavinPadhiyar on 5/18/17.
 */

public class VerticalGridActivity extends Activity {


    @Override public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);
        if (savedInstanceState == null) {
            Fragment fragment = new VerticalGridFragment();
            getFragmentManager().beginTransaction().replace(R.id.fragmentContainer, fragment).commit();
        }
    }
    @Override
    public boolean onSearchRequested() {
        startActivity(new Intent(this, SearchActivity.class));
        return true;
    }

}

