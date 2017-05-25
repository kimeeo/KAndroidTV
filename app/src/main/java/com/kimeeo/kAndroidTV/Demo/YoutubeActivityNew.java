package com.kimeeo.kAndroidTV.Demo;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v17.leanback.app.PlaybackOverlayFragment;
import android.support.v4.os.BuildCompat;
import android.view.View;

import com.kimeeo.kAndroidTV.Demo.fragments.YoutubeVideoFragment;
import com.kimeeo.kAndroidTV.youtube.AbstractVideoPlayerFragment;
import com.kimeeo.kAndroidTV.youtube.AbstractYoutubeActivity;
import com.kimeeo.kAndroidTV.youtube.AbstractYoutubeVideoPlayerFragment;

import fr.bmartel.youtubetv.YoutubeTvFragment;
import fr.bmartel.youtubetv.YoutubeTvView;
import fr.bmartel.youtubetv.media.VideoSurfaceFragment;

/**
 * Created by BhavinPadhiyar on 5/18/17.
 */

public class YoutubeActivityNew extends AbstractYoutubeActivity {

    protected AbstractYoutubeVideoPlayerFragment createAbstractYoutubeVideoPlayerFragment(YoutubeTvView youtubeTvView)
    {
        return new YoutubeVideoFragment(youtubeTvView);
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        play("om_SW2jgq-s");
    }
}

