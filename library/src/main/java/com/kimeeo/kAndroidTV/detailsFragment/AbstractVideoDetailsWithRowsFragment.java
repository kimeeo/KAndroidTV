package com.kimeeo.kAndroidTV.detailsFragment;

import android.app.Fragment;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v17.leanback.app.DetailsFragmentBackgroundController;
import android.support.v17.leanback.media.MediaPlayerGlue;

import com.kimeeo.kAndroidTV.R;

/**
 * Created by BhavinPadhiyar on 5/19/17.
 */

abstract public class AbstractVideoDetailsWithRowsFragment extends AbstractDetailsWithRowsFragment
{

    private MediaPlayerGlue mMediaPlayerGlue;
    protected void updateBackground(DetailsFragmentBackgroundController mDetailsBackground,Object data) {
        mMediaPlayerGlue = createMediaPlayerGlue();
        mMediaPlayerGlue.setTitle(getVideoTitle(data));
        mMediaPlayerGlue.setArtist(getVideoArtist(data));
        mMediaPlayerGlue.setVideoUrl(getTrailerUrl(data));

        mDetailsBackground.enableParallax();
        mDetailsBackground.setCoverBitmap(getCoverImage());

        mDetailsBackground.setupVideoPlayback(mMediaPlayerGlue);

    }
    @Override
    public void onDestroy() {
        super.onDestroy();
       if(mMediaPlayerGlue!=null)
       {
           mMediaPlayerGlue.reset();
           mDetailsBackground.setupVideoPlayback(null);
       }
    }

    protected Bitmap getCoverImage() {
        return BitmapFactory.decodeResource(getResources(),getDefaultDrableRes());
    }

    protected int getDefaultDrableRes() {
        return R.drawable._vid_defualt_preview;
    }

    protected abstract String getTrailerUrl(Object data);
    protected abstract String getVideoUrl(Object data);
    protected abstract String getVideoArtist(Object data);
    protected abstract String getVideoTitle(Object data);

    protected MediaPlayerGlue createMediaPlayerGlue() {
        return new MediaPlayerGlue(getActivity());
    }

    public void playMainVideo() {
        mMediaPlayerGlue.setTitle(getVideoTitle(getData()));
        mMediaPlayerGlue.setArtist(getVideoArtist(getData()));
        mMediaPlayerGlue.setMediaSource(Uri.parse(getVideoUrl(getData())));

        Fragment mVideoFragment = mDetailsBackground.findOrCreateVideoFragment();
        mVideoFragment.getView().requestFocus();
    }

}
