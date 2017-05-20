package com.kimeeo.kAndroidTV.Demo;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v17.leanback.app.PlaybackOverlayFragment;
import android.support.v4.os.BuildCompat;
import android.view.View;

import com.kimeeo.kAndroidTV.Demo.fragments.VideoPlayerFragment_backup;

import fr.bmartel.youtubetv.YoutubeTvFragment;
import fr.bmartel.youtubetv.media.VideoPlayerFragment;
import fr.bmartel.youtubetv.media.VideoSurfaceFragment;

/**
 * Created by BhavinPadhiyar on 5/18/17.
 */

public class YoutubeActivity extends Activity {

    /**
     * Called when the activity is first created.
     */
    protected Bundle args;
    @Override public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);
        if (savedInstanceState == null) {
            android.app.FragmentTransaction fTransaction = getFragmentManager().beginTransaction();
            args = new Bundle();
            args.putString("videoId", "qkt7C0NIuUA");

            YoutubeTvFragment mYtFragment = new YoutubeTvFragment();
            mYtFragment.setArguments(args);

            fTransaction.replace(R.id.fragmentContainer, mYtFragment);
            fTransaction.commit();
        }


    }

    /*
        //DialogExampleActivity.openDialog(this,new int[]{1,2,3},new String[]{"YES","NO","MAY BE"},"Would you like to try","OK", R.drawable.ic_android_black_24dp,DialogExampleActivity.NO_BACKGROUND_DRAWABLE);
   @Override
   protected void onActivityResult(int requestCode, int resultCode, Intent data) {
       super.onActivityResult(requestCode, resultCode, data);
       if (requestCode == DialogExampleActivity.REQUEST_CODE && resultCode == Activity.RESULT_OK) {
           int what=data.getIntExtra(DialogExampleActivity.CHOICE,-1);

       }
   }*/

    public static boolean supportsPictureInPicture(Context context) {
        return BuildCompat.isAtLeastN() && context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_PICTURE_IN_PICTURE);
    }

/*
    protected YoutubeTvFragment createNewYoutubeTvFragment(Bundle args) {
        YoutubeTvFragment mYtFragment =new MyYoutubeTvFragment();
        mYtFragment.setArguments(args);
        return mYtFragment;
    }
    protected VideoPlayerFragment createVideoPlayerFragment(Bundle args) {
        VideoPlayerFragment mYtFragment =new VideoPlayerFragment();
        mYtFragment.setArguments(args);
        return mYtFragment;
    }


    public  class MyYoutubeTvFragment extends YoutubeTvFragment
    {
        @Override
        public void onViewCreated(View view, Bundle savedInstanceState) {
            //super.onViewCreated(view,savedInstanceState);
            android.app.FragmentTransaction ft1 = getChildFragmentManager().beginTransaction();
            ft1.replace(fr.bmartel.youtubetv.R.id.videoFragment, new VideoSurfaceFragment(), VideoSurfaceFragment.TAG);
            ft1.commit();

            android.app.FragmentTransaction ft2 = getChildFragmentManager().beginTransaction();
            PlaybackOverlayFragment mVideoFragment = createVideoPlayerFragment(args);
            ft2.add(fr.bmartel.youtubetv.R.id.videoFragment, mVideoFragment, VideoPlayerFragment_backup.TAG);
            ft2.commit();
        }
    }
    */



}

