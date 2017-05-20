package com.kimeeo.kAndroidTV.detailsFragment;

import android.app.Fragment;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.ColorRes;
import android.support.annotation.NonNull;
import android.support.v17.leanback.app.DetailsFragment;
import android.support.v17.leanback.app.DetailsFragmentBackgroundController;
import android.support.v17.leanback.media.MediaPlayerGlue;
import android.support.v17.leanback.widget.Action;
import android.support.v17.leanback.widget.ArrayObjectAdapter;
import android.support.v17.leanback.widget.ClassPresenterSelector;
import android.support.v17.leanback.widget.DetailsOverviewRow;
import android.support.v17.leanback.widget.FullWidthDetailsOverviewRowPresenter;
import android.support.v17.leanback.widget.FullWidthDetailsOverviewSharedElementHelper;
import android.support.v17.leanback.widget.HeaderItem;
import android.support.v17.leanback.widget.Presenter;
import android.support.v17.leanback.widget.PresenterSelector;
import android.support.v17.leanback.widget.Row;
import android.support.v17.leanback.widget.RowPresenter;

import com.kimeeo.kAndroid.dataProvider.DataProvider;
import com.kimeeo.kAndroidTV.R;
import com.kimeeo.kAndroidTV.core.AbstractArrayObjectAdapter;
import com.kimeeo.kAndroidTV.core.DefaultArrayObjectAdapter;
import com.kimeeo.kAndroidTV.core.IHeaderItem;
import com.kimeeo.kAndroidTV.core.RowBasedFragmentHelper;
import com.kimeeo.kAndroidTV.core.WatcherArrayObjectAdapter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by BhavinPadhiyar on 5/19/17.
 */

abstract public class AbstractVideoDetailsFragment extends AbstractDetailsFragment
{

    private MediaPlayerGlue mMediaPlayerGlue;
    protected void initializeBackground(DetailsFragmentBackgroundController mDetailsBackground,Object data) {
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
