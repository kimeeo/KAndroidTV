package com.kimeeo.kAndroidTV.Demo;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;

import com.kimeeo.kAndroidTV.Demo.fragments.PageFragment;
import com.kimeeo.kAndroidTV.dialog.DialogExampleActivity;

import fr.bmartel.youtubetv.YoutubeTvFragment;

/**
 * Created by BhavinPadhiyar on 5/18/17.
 */

public class YoutubeActivity extends Activity {

    /**
     * Called when the activity is first created.
     */
    @Override public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);
        if (savedInstanceState == null) {
            android.app.FragmentTransaction fTransaction = getFragmentManager().beginTransaction();
            Bundle args = new Bundle();
            args.putString("videoId", "qkt7C0NIuUA");

            YoutubeTvFragment mYtFragment = YoutubeTvFragment.newInstance(args);
            fTransaction.replace(R.id.fragmentContainer, mYtFragment);
            fTransaction.commit();
        }

        DialogExampleActivity.openDialog(this,new int[]{1,2,3},new String[]{"YES","NO","MAY BE"},"Would you like to try","OK", R.drawable.ic_android_black_24dp);
    }
    @Override
    public boolean onSearchRequested() {
        startActivity(new Intent(this, SearchActivity.class));
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == DialogExampleActivity.REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            int what=data.getIntExtra(DialogExampleActivity.CHOICE,-1);

        }
    }

}

