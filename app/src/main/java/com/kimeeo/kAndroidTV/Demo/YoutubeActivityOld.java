package com.kimeeo.kAndroidTV.Demo;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v17.leanback.app.PlaybackOverlayFragment;
import android.support.v4.os.BuildCompat;
import android.view.View;

import com.kimeeo.kAndroidTV.Demo.fragments.YoutubeVideoFragment;

import fr.bmartel.youtubetv.YoutubeTvFragment;
import fr.bmartel.youtubetv.media.VideoPlayerFragment;
import fr.bmartel.youtubetv.media.VideoSurfaceFragment;

/**
 * Created by BhavinPadhiyar on 5/18/17.
 */

public class YoutubeActivityOld extends Activity {
    protected Bundle args;
    @Override public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);
        if (savedInstanceState == null) {
            android.app.FragmentTransaction fTransaction = getFragmentManager().beginTransaction();
            args = new Bundle();
            args.putString("videoId", "qkt7C0NIuUA");

            YoutubeTvFragment mYtFragment = createNewYoutubeTvFragment(args);
            mYtFragment.setArguments(args);

            fTransaction.replace(R.id.fragmentContainer, mYtFragment);
            fTransaction.commit();
        }


    }

    protected YoutubeTvFragment createNewYoutubeTvFragment(Bundle args) {
        YoutubeTvFragment mYtFragment =new YoutubeTvFragment();
        mYtFragment.setArguments(args);
        return mYtFragment;
    }
}

