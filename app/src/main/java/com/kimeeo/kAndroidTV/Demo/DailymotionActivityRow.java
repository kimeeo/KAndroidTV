package com.kimeeo.kAndroidTV.Demo;

import android.os.Bundle;
import android.os.Handler;

import com.kimeeo.kAndroidTV.Demo.fragments.DailymotionVideoFragment;
import com.kimeeo.kAndroidTV.Demo.fragments.YoutubeVideoFragment;
import com.kimeeo.kAndroidTV.dailymotion.AbstractDailymotionActivity;
import com.kimeeo.kAndroidTV.dailymotion.AbstractDailymotionVideoPlayerFragment;
import com.kimeeo.kAndroidTV.dailymotion.DMWebVideoView;
import com.kimeeo.kAndroidTV.youtube.AbstractYoutubeActivity;
import com.kimeeo.kAndroidTV.youtube.AbstractYoutubeVideoPlayerFragment;

import fr.bmartel.youtubetv.YoutubeTvView;

/**
 * Created by BhavinPadhiyar on 5/18/17.
 */

public class DailymotionActivityRow extends AbstractDailymotionActivity{

    protected AbstractDailymotionVideoPlayerFragment createAbstractDailymotionVideoPlayerFragment(DMWebVideoView youtubeTvView)
    {
        return new DailymotionVideoFragment(youtubeTvView);
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        play("x5nyib8");
    }
}

