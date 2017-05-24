package com.kimeeo.kAndroidTV.youtube;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v17.leanback.app.PlaybackOverlayFragment;
import android.support.v17.leanback.widget.Action;
import android.support.v17.leanback.widget.ArrayObjectAdapter;
import android.support.v17.leanback.widget.HeaderItem;
import android.support.v17.leanback.widget.PlaybackControlsRow;
import android.support.v17.leanback.widget.PlaybackControlsRowPresenter;
import android.support.v17.leanback.widget.Presenter;
import android.support.v17.leanback.widget.PresenterSelector;
import android.support.v17.leanback.widget.Row;
import android.support.v17.leanback.widget.RowPresenter;

import com.kimeeo.kAndroid.dataProvider.DataProvider;
import com.kimeeo.kAndroidTV.core.AbstractArrayObjectAdapter;
import com.kimeeo.kAndroidTV.core.BackgroundImageHelper;
import com.kimeeo.kAndroidTV.core.DefaultArrayObjectAdapter;
import com.kimeeo.kAndroidTV.core.IHeaderItem;
import com.kimeeo.kAndroidTV.core.RowBasedFragmentHelper;
import com.kimeeo.kAndroidTV.core.WatcherArrayObjectAdapter;

import fr.bmartel.youtubetv.YoutubeTvView;
import fr.bmartel.youtubetv.media.MediaPlayerGlue;
import fr.bmartel.youtubetv.media.VideoMediaPlayerGlue;
import fr.bmartel.youtubetv.model.VideoQuality;

/**
 * Created by BhavinPadhiyar on 5/20/17.
 */

abstract public class AbstractVideoPlayerFragmentBkup extends PlaybackOverlayFragment implements RowBasedFragmentHelper.HelperProvider, MediaPlayerGlue.OnMediaFileFinishedPlayingListener
{
    @Override
    public boolean supportAutoPageLoader() {return true;}

    public boolean getSupportRowProgressBar()
    {
        return false;
    }

    @Override
    final public boolean supportBackgroundChange() {
        return false;
    }
    @Override
    final public BackgroundImageHelper getBackgroundImageHelper() {return null;}
    @Override
    final public String getFirstTimeLoaderMessage() {return null;}
    public static final String TAG = "VideoPlayerFragment";
    //private ArrayObjectAdapter mRowsAdapter;
    private MediaPlayerGlue mGlue;
    private YoutubeTvView youtubeTvView;

    abstract protected @NonNull
    DataProvider createDataProvider();
    protected DataProvider dataProvider;
    protected void configDataManager(DataProvider dataProvider) {}
    public DataProvider getDataProvider()
    {
        return dataProvider;
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        if (null != fragmentHelper) {
            fragmentHelper.onDestroy();
        }
    }

    public RowBasedFragmentHelper getFragmentHelper() {
        return fragmentHelper;
    }

    RowBasedFragmentHelper fragmentHelper;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        youtubeTvView = (YoutubeTvView) getActivity().findViewById(fr.bmartel.youtubetv.R.id.youtubetv_view);
        youtubeTvView.updateView(getArguments());
        youtubeTvView.playVideo(getArguments().getString("videoId", ""));
        mGlue = new VideoMediaPlayerGlue(getActivity(), this, youtubeTvView) {
            @Override
            protected void onRowChanged(PlaybackControlsRow row) {
                /*
                if (fragmentHelper.getRowsAdapter() == null)
                    return;
                fragmentHelper.getRowsAdapter().notifyArrayItemRangeChanged(0, 1);*/
            }
        };
        mGlue.setOnMediaFileFinishedPlayingListener(this);
        mGlue.prepareMediaForPlaying();

        fragmentHelper = createBrowseFragmentHelper();
        dataProvider=createDataProvider();
        configDataManager(dataProvider);
        fragmentHelper.build();
        getFragmentHelper().getRowsAdapter().add(0,mGlue.getControlsRow());
        fragmentHelper.next();
    }
    protected RowBasedFragmentHelper createBrowseFragmentHelper() {
        return new RowBasedFragmentHelper(this,this);
    }

    @Override
    public void onStart() {
        super.onStart();
        mGlue.enableProgressUpdating(mGlue.hasValidMedia() && mGlue.isMediaPlaying());
    }

    @Override
    public void onStop() {
        super.onStop();
        mGlue.enableProgressUpdating(false);
        mGlue.reset();
    }


    @Override
    public void onMediaFileFinishedPlaying(MediaPlayerGlue.MetaData metaData) {
        mGlue.startPlayback();
    }

    public void setPlaybackQuality(VideoQuality videoQuality) {
        youtubeTvView.setPlaybackQuality(videoQuality);
    }

    public void closePlayer() {
        youtubeTvView.closePlayer();
    }

    final public PresenterSelector createMainRowPresenterSelector() {
        return null;
    }
    final public Presenter createMainRowPresenter() {
        final PlaybackControlsRowPresenter controlsPresenter = mGlue.createControlsRowAndPresenter();
        return controlsPresenter;
    }
    public AbstractArrayObjectAdapter createMainArrayObjectAdapter(Presenter presenter) {
        return new DefaultArrayObjectAdapter(presenter);
    }
    final public AbstractArrayObjectAdapter createMainArrayObjectAdapter(PresenterSelector presenter) {return null;}

    abstract public PresenterSelector getRowItemPresenterSelector(IHeaderItem headerItem);
    final public Row getListRow(IHeaderItem headerItem, HeaderItem header, ArrayObjectAdapter listRowAdapter) {
        return new PlaybackControlsRow(listRowAdapter);
    }

    public HeaderItem getHeaderItem(int i,IHeaderItem headerItem, String name)
    {
        return new HeaderItem(i,name);
    }
    public ArrayObjectAdapter getRowArrayObjectAdapter(IHeaderItem headerItem,Presenter presenter) {
        return new ArrayObjectAdapter(presenter);
    }
    public ArrayObjectAdapter getRowArrayObjectAdapter(IHeaderItem headerItem,PresenterSelector presenterSelector) {
        return new WatcherArrayObjectAdapter(presenterSelector);
    }
    public Class getSearchActivity() {
        return null;
    }
    public void onItemSelected(Presenter.ViewHolder itemViewHolder, Object item,RowPresenter.ViewHolder rowViewHolder, Row row){}
    public void onItemClicked(Presenter.ViewHolder itemViewHolder, Object item,RowPresenter.ViewHolder rowViewHolder, Row row) {
        if (!(item instanceof Action)) return;
                mGlue.onActionClicked((Action) item);
    }
}