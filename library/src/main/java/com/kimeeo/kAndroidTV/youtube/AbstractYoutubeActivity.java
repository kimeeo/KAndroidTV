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

package com.kimeeo.kAndroidTV.youtube;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.media.session.MediaSession;
import android.media.session.PlaybackState;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;

import com.kimeeo.kAndroidTV.R;
import com.kimeeo.kAndroidTV.dialog.DialogActivity;
import com.kimeeo.kAndroidTV.errorFragment.ErrorFragmentHelper;

import java.util.List;

import fr.bmartel.youtubetv.YoutubeTvView;
import fr.bmartel.youtubetv.model.VideoQuality;

abstract public class AbstractYoutubeActivity extends Activity{
    private static final String TAG = "PlaybackOverlayActivity";
    AbstractYoutubeVideoPlayerFragment youtubeVideoPlayerFragment;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout._playback_controls);

        YoutubeTvView youtubeTvView = getYoutubeTvView();
        youtubeVideoPlayerFragment=createAbstractYoutubeVideoPlayerFragment(youtubeTvView);

        android.app.FragmentTransaction ft1 = getFragmentManager().beginTransaction();
        ft1.replace(R.id.playbackControlsFragmentHolder,youtubeVideoPlayerFragment, AbstractYoutubeVideoPlayerFragment.TAG);
        ft1.commit();
    }

    protected void play(String videoId) {
        youtubeVideoPlayerFragment.play(videoId);
    }

    protected abstract AbstractYoutubeVideoPlayerFragment createAbstractYoutubeVideoPlayerFragment(YoutubeTvView youtubeTvView);


    @Override
    public void onDestroy() {
        super.onDestroy();
        youtubeVideoPlayerFragment.closePlayer();
    }
    protected YoutubeTvView getYoutubeTvView() {
        YoutubeTvView youtubeTvView = (YoutubeTvView) findViewById(R.id.youtubeTvView);
        youtubeTvView.setFocusable(false);
        youtubeTvView.setFocusableInTouchMode(false);
        return youtubeTvView;
    }

    @Override
    public void onPause() {
        super.onPause();
        if (youtubeVideoPlayerFragment.isPlaying()) {
            if (!requestVisibleBehind(true)) {
                stopPlayback();
            }
        } else {
            requestVisibleBehind(false);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        youtubeVideoPlayerFragment.closePlayer();
    }
    private void stopPlayback() {
        if (youtubeVideoPlayerFragment != null) {
            youtubeVideoPlayerFragment.stopVideo();
        }
    }

    public void openQualitySelector(List<VideoQuality> availableQualityLevels) {
        DialogActivity.Builder builder=new DialogActivity.Builder(this).title(getSelectQualityTitle());
        for (int i = 0; i < availableQualityLevels.size(); i++) {
            builder.addAction(i,availableQualityLevels.get(i).name());
        }
        builder.open();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == DialogActivity.REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            int what=data.getIntExtra(DialogActivity.CHOICE,-1);
            youtubeVideoPlayerFragment.setPlaybackQuality(what);
        }
    }
    public String getSelectQualityTitle() {
        return getResources().getString(R.string._select_quality);
    }
}
