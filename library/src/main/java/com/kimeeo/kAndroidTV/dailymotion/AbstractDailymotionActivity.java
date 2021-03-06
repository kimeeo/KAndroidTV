/*
 * Copyright (C) 2014 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package com.kimeeo.kAndroidTV.dailymotion;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.os.BuildCompat;

import com.kimeeo.kAndroidTV.R;
import com.kimeeo.kAndroidTV.dialog.DialogActivity;

import java.util.List;

abstract public class AbstractDailymotionActivity extends Activity{
    private static final String TAG = "AbstractDailymotionActivity";
    AbstractDailymotionVideoPlayerFragment dailymotionVideoPlayerFragment;

    DMWebVideoView webVideoView;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout._dailymotion_playback_controls);

        webVideoView = getDMWebVideoView();
        dailymotionVideoPlayerFragment=createAbstractDailymotionVideoPlayerFragment(webVideoView);


        android.app.FragmentTransaction ft1 = getFragmentManager().beginTransaction();
        ft1.replace(R.id.playbackControlsFragmentHolder,dailymotionVideoPlayerFragment, AbstractDailymotionVideoPlayerFragment.TAG);
        ft1.commit();
    }

    protected void play(String videoId) {
        dailymotionVideoPlayerFragment.play(videoId);
    }

    protected abstract AbstractDailymotionVideoPlayerFragment createAbstractDailymotionVideoPlayerFragment(DMWebVideoView youtubeTvView);

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        webVideoView.handleBackPress(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        dailymotionVideoPlayerFragment.closePlayer();
    }
    protected DMWebVideoView getDMWebVideoView() {
        DMWebVideoView youtubeTvView = (DMWebVideoView) findViewById(R.id.dmWebVideoView);
        return youtubeTvView;
    }

    @Override
    public void onPause() {
        super.onPause();
        requestVisibleBehind(true);
        /* TODO
        if (dailymotionVideoPlayerFragment.isPlaying()) {
            boolean isVisibleBehind = requestVisibleBehind(true);
            boolean isPictureInPictureMode = supportsPictureInPicture() && isInPictureInPictureMode();
            if (!isVisibleBehind && !isPictureInPictureMode)
                dailymotionVideoPlayerFragment.getDmWebVideoView().pause();
        } else
            requestVisibleBehind(true);
        */
    }

    @Override
    protected void onStop() {
        super.onStop();
    }
    private void stopPlayback() {
        if (dailymotionVideoPlayerFragment != null) {
            dailymotionVideoPlayerFragment.stopVideo();
        }
    }

    public void openQualitySelector(String[] qualities) {
        DialogActivity.Builder builder=new DialogActivity.Builder(this).title(getSelectQualityTitle());
        for (int i = 0; i < qualities.length; i++) {
            builder.addAction(i,qualities[i]);
        }
        builder.backgroundColor(Color.TRANSPARENT);
        builder.open();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == DialogActivity.REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            int what=data.getIntExtra(DialogActivity.CHOICE,-1);
            dailymotionVideoPlayerFragment.setPlaybackQuality(what);
        }
    }
    public String getSelectQualityTitle() {
        return getResources().getString(R.string._select_quality);
    }
    public boolean supportsPictureInPicture() {
        return BuildCompat.isAtLeastN() && getPackageManager().hasSystemFeature(PackageManager.FEATURE_PICTURE_IN_PICTURE);
    }
}
