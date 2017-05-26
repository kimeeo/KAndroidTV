package com.kimeeo.kAndroidTV.Demo;

import android.os.Bundle;
import android.os.Handler;

import com.kimeeo.kAndroidTV.Demo.fragments.YoutubeVideoFragment;
import com.kimeeo.kAndroidTV.youtube.AbstractYoutubeActivity;
import com.kimeeo.kAndroidTV.youtube.AbstractYoutubeVideoPlayerFragment;

import fr.bmartel.youtubetv.YoutubeTvView;

/**
 * Created by BhavinPadhiyar on 5/18/17.
 */

public class YoutubeActivityRow extends AbstractYoutubeActivity {

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

