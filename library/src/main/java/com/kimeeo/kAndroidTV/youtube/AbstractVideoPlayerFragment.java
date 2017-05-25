package com.kimeeo.kAndroidTV.youtube;

import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.ColorRes;
import android.support.annotation.NonNull;
import android.support.v17.leanback.app.DetailsFragment;
import android.support.v17.leanback.app.DetailsFragmentBackgroundController;
import android.support.v17.leanback.app.PlaybackOverlayFragment;
import android.support.v17.leanback.widget.AbstractDetailsDescriptionPresenter;
import android.support.v17.leanback.widget.Action;
import android.support.v17.leanback.widget.ArrayObjectAdapter;
import android.support.v17.leanback.widget.ClassPresenterSelector;
import android.support.v17.leanback.widget.ControlButtonPresenterSelector;
import android.support.v17.leanback.widget.DetailsOverviewRow;
import android.support.v17.leanback.widget.FullWidthDetailsOverviewRowPresenter;
import android.support.v17.leanback.widget.FullWidthDetailsOverviewSharedElementHelper;
import android.support.v17.leanback.widget.HeaderItem;
import android.support.v17.leanback.widget.ListRow;
import android.support.v17.leanback.widget.ListRowPresenter;
import android.support.v17.leanback.widget.OnActionClickedListener;
import android.support.v17.leanback.widget.OnItemViewClickedListener;
import android.support.v17.leanback.widget.PlaybackControlsRow;
import android.support.v17.leanback.widget.PlaybackControlsRowPresenter;
import android.support.v17.leanback.widget.Presenter;
import android.support.v17.leanback.widget.PresenterSelector;
import android.support.v17.leanback.widget.Row;
import android.support.v17.leanback.widget.RowPresenter;
import android.widget.Toast;

import com.kimeeo.kAndroid.dataProvider.DataProvider;
import com.kimeeo.kAndroidTV.R;
import com.kimeeo.kAndroidTV.core.AbstractArrayObjectAdapter;
import com.kimeeo.kAndroidTV.core.BackgroundImageHelper;
import com.kimeeo.kAndroidTV.core.DefaultArrayObjectAdapter;
import com.kimeeo.kAndroidTV.core.IHeaderItem;
import com.kimeeo.kAndroidTV.core.RowBasedFragmentHelper;
import com.kimeeo.kAndroidTV.core.WatcherArrayObjectAdapter;

import java.util.HashMap;
import java.util.Map;

import fr.bmartel.youtubetv.YoutubeTvView;
import fr.bmartel.youtubetv.listener.IBufferStateListener;
import fr.bmartel.youtubetv.listener.IPlayerListener;
import fr.bmartel.youtubetv.listener.IProgressUpdateListener;
import fr.bmartel.youtubetv.model.VideoInfo;
import fr.bmartel.youtubetv.model.VideoQuality;
import fr.bmartel.youtubetv.model.VideoState;

/**
 * Created by BhavinPadhiyar on 5/20/17.
 */

abstract public class AbstractVideoPlayerFragment extends PlaybackOverlayFragment implements RowBasedFragmentHelper.HelperProvider,OnActionClickedListener,IPlayerListener,IProgressUpdateListener,IBufferStateListener
{
    public static final String TAG = "VideoPlayerFragment";

    private static final int PRIMARY_CONTROLS = 5;
    private static final int BACKGROUND_TYPE = PlaybackOverlayFragment.BG_LIGHT;

    private static final int DEFAULT_UPDATE_PERIOD = 1000;
    private static final int UPDATE_PERIOD = 16;

    private ArrayObjectAdapter mPrimaryActionsAdapter;
    private ArrayObjectAdapter mSecondaryActionsAdapter;

    private PlaybackControlsRow.PlayPauseAction mPlayPauseAction;
    private PlaybackControlsRow.RepeatAction mRepeatAction;
    private PlaybackControlsRow.ThumbsUpAction mThumbsUpAction;
    private PlaybackControlsRow.ThumbsDownAction mThumbsDownAction;
    private PlaybackControlsRow.ShuffleAction mShuffleAction;
    private PlaybackControlsRow.FastForwardAction mFastForwardAction;
    private PlaybackControlsRow.RewindAction mRewindAction;
    private PlaybackControlsRow.SkipNextAction mSkipNextAction;
    private PlaybackControlsRow.SkipPreviousAction mSkipPreviousAction;
    private PlaybackControlsRow mPlaybackControlsRow;


    @Override
    public boolean supportAutoPageLoader() {return true;}
    public boolean getSupportRowProgressBar()
    {
        return false;
    }

    final public BackgroundImageHelper getBackgroundImageHelper(){return null;}
    final public boolean supportBackgroundChange(){return false;}


    public void onActionClicked(Action action)
    {
        if (action.getId() == mPlayPauseAction.getId()) {
            togglePlayback();
        }
        else if (action.getId() == mFastForwardAction.getId()) {
            youtubeTvView.seekTo(seekSize());
        }
        else if (action.getId() == mRewindAction.getId()) {
            youtubeTvView.seekTo((int)youtubeTvView.getCurrentPosition()-seekSize());
        }
    }

    protected int seekSize() {
        return 20;
    }

    public Row getListRow(IHeaderItem headerItem, HeaderItem header, ArrayObjectAdapter listRowAdapter) {
        return new ListRow(header, listRowAdapter);
    }
    public void togglePlayback() {
        youtubeTvView.playPause();
    }

    private void notifyChanged(Action action) {
        ArrayObjectAdapter adapter = mPrimaryActionsAdapter;
        if (adapter.indexOf(action) >= 0) {
            adapter.notifyArrayItemRangeChanged(adapter.indexOf(action), 1);
            return;
        }
        adapter = mSecondaryActionsAdapter;
        if (adapter.indexOf(action) >= 0) {
            adapter.notifyArrayItemRangeChanged(adapter.indexOf(action), 1);
            return;
        }
    }

    protected void next() {

    }
    protected void prev() {

    }
    @Override
    public void onStop() {
        stopProgressAutomation();
        youtubeTvView.stopVideo();

        super.onStop();
    }


    public Object getData() {
        return data;
    }

    private Object data;

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (null != fragmentHelper) {
            fragmentHelper.onDestroy();
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupUi();
        setupUIElements();
    }
    protected RowBasedFragmentHelper createBrowseFragmentHelper() {
        return new RowBasedFragmentHelper(this,this);
    }
    abstract protected @NonNull DataProvider createDataProvider();
    protected DataProvider dataProvider;
    public DataProvider getDataProvider()
    {
        return dataProvider;
    }
    protected void configDataManager(DataProvider dataProvider) {}
    RowBasedFragmentHelper fragmentHelper;
    private YoutubeTvView youtubeTvView;

    final public PresenterSelector createMainRowPresenterSelector()
    {
        ClassPresenterSelector rowPresenterSelector = new ClassPresenterSelector();

        rowPresenterSelector.addClassPresenter(getPlaybackControlsRowClass(), playbackControlsRowPresenter);

        HashMap<Class<?>, Object> mClassMap = getClassPresenterMap();
        if(mClassMap!=null) {
            for (Map.Entry<Class<?>, Object> entry : mClassMap.entrySet()) {
                Class key = entry.getKey();
                Object value = entry.getValue();
                if(value instanceof Presenter)
                    rowPresenterSelector.addClassPresenter(key,(Presenter)value);
                else if(value instanceof PresenterSelector)
                    rowPresenterSelector.addClassPresenterSelector(key,(PresenterSelector)value);
            }
        }
        return rowPresenterSelector;
    }
    public AbstractArrayObjectAdapter createMainArrayObjectAdapter(PresenterSelector presenter)
    {
        return new DefaultArrayObjectAdapter(presenter);
    }

    final public Presenter createMainRowPresenter(){return null;}
    final public AbstractArrayObjectAdapter createMainArrayObjectAdapter(Presenter presenter){return null;}

    public HeaderItem getHeaderItem(int i,IHeaderItem headerItem, String name)
    {
        return new HeaderItem(i,name);
    }
    public void setPlaybackQuality(VideoQuality videoQuality) {
        youtubeTvView.setPlaybackQuality(videoQuality);
    }

    public void closePlayer() {
        youtubeTvView.closePlayer();
    }

    public PlaybackControlsRowPresenter getPlaybackControlsRowPresenter() {
        return playbackControlsRowPresenter;
    }

    PlaybackControlsRowPresenter playbackControlsRowPresenter;

    private void setupUi() {

        data =createDetailsData();
        playbackControlsRowPresenter = createActionDetailedViewPresenter();

        youtubeTvView = (YoutubeTvView) getActivity().findViewById(fr.bmartel.youtubetv.R.id.youtubetv_view);
        youtubeTvView.addPlayerListener(this);
        youtubeTvView.playVideo(getVideoID());
        fragmentHelper = createBrowseFragmentHelper();
        dataProvider=createDataProvider();
        configDataManager(dataProvider);
        fragmentHelper.build();
        fragmentHelper.next();
        addPlaybackControlsRow();
    }

    protected String getVideoID() {
        return getArguments().getString("videoId", "");
    }

    public void updateView(Bundle bundle) {
        youtubeTvView.updateView(bundle);
    }

    @Override
    public void onPlayerReady(VideoInfo videoInfo) {

    }

    VideoState videoState;
    @Override
    public void onPlayerStateChange(VideoState state, long position, float speed, float duration, VideoInfo videoInfo) {
        if (videoState!=state && VideoState.getPlayerState(state.getIndex()) == VideoState.PAUSED) {
            videoState=state;
            //stopProgressAutomation();
            mPlayPauseAction.setIcon(mPlayPauseAction.getDrawable(PlaybackControlsRow.PlayPauseAction.PLAY));
            notifyChanged(mPlayPauseAction);
        } else if (videoState!=state && VideoState.getPlayerState(state.getIndex()) == VideoState.PLAYING) {
            videoState=state;
            //startProgressAutomation();
            mPlayPauseAction.setIcon(mPlayPauseAction.getDrawable(PlaybackControlsRow.PlayPauseAction.PAUSE));
            notifyChanged(mPlayPauseAction);
        }
        else if (VideoState.getPlayerState(state.getIndex()) == VideoState.BUFFERING) {

        }
    }
    private Handler mHandler;
    private Runnable mRunnable= new Runnable() {
        @Override
        public void run() {

            float currentTime = youtubeTvView.getCurrentPosition();
            float totalTime = youtubeTvView.getDuration();
            mPlaybackControlsRow.setCurrentTime((int)currentTime);
            mPlaybackControlsRow.setTotalTime((int)totalTime);
            mHandler.postDelayed(mRunnable, getUpdatePeriod());
        }
    };
    private void startProgressAutomation() {
        if(mHandler==null)
            mHandler = new Handler();
        mHandler.postDelayed(mRunnable, getUpdatePeriod());
    }

    private int getUpdatePeriod() {
        if (getView() == null || youtubeTvView.getDuration() <= 0) {
            return DEFAULT_UPDATE_PERIOD;
        }
        return Math.max(UPDATE_PERIOD, (int)youtubeTvView.getDuration() / getView().getWidth());
    }


    private void stopProgressAutomation() {
        if (mHandler != null && mRunnable != null) {
            mHandler.removeCallbacks(mRunnable);
        }
    }

    @Override
    public void onProgressUpdate(float currentTime)
    {
        //mPlaybackControlsRow.setCurrentTime((int)currentTime);
        //mPlaybackControlsRow.setTotalTime((int)youtubeTvView.getDuration());
    }
    @Override
    public void onBufferUpdate(float videoDuration, float loadedFraction)
    {
        //mPlaybackControlsRow.setBufferedProgress((int)videoDuration);
        //mPlaybackControlsRow.setTotalTime((int)youtubeTvView.getDuration());
    }

    private void addPlaybackControlsRow() {
        if (getShowDetail()) {
            mPlaybackControlsRow = new PlaybackControlsRow(getData());
        } else {
            mPlaybackControlsRow = new PlaybackControlsRow();
        }
        fragmentHelper.getRowsAdapter().add(mPlaybackControlsRow);

        ControlButtonPresenterSelector presenterSelector = new ControlButtonPresenterSelector();
        mPrimaryActionsAdapter = new ArrayObjectAdapter(presenterSelector);
        mSecondaryActionsAdapter = new ArrayObjectAdapter(presenterSelector);

        mPlaybackControlsRow.setPrimaryActionsAdapter(mPrimaryActionsAdapter);
        mPlaybackControlsRow.setSecondaryActionsAdapter(mSecondaryActionsAdapter);

        mPlayPauseAction = new PlaybackControlsRow.PlayPauseAction(getActivity());
        mRepeatAction = new PlaybackControlsRow.RepeatAction(getActivity());
        mThumbsUpAction = new PlaybackControlsRow.ThumbsUpAction(getActivity());
        mThumbsDownAction = new PlaybackControlsRow.ThumbsDownAction(getActivity());
        mShuffleAction = new PlaybackControlsRow.ShuffleAction(getActivity());
        mSkipNextAction = new PlaybackControlsRow.SkipNextAction(getActivity());
        mSkipPreviousAction = new PlaybackControlsRow.SkipPreviousAction(getActivity());
        mFastForwardAction = new PlaybackControlsRow.FastForwardAction(getActivity());
        mRewindAction = new PlaybackControlsRow.RewindAction(getActivity());

        if (PRIMARY_CONTROLS > 5) {
            mPrimaryActionsAdapter.add(mThumbsUpAction);
        } else {
            mSecondaryActionsAdapter.add(mThumbsUpAction);
        }
        mPrimaryActionsAdapter.add(mSkipPreviousAction);
        if (PRIMARY_CONTROLS > 3) {
            mPrimaryActionsAdapter.add(new PlaybackControlsRow.RewindAction(getActivity()));
        }
        mPrimaryActionsAdapter.add(mPlayPauseAction);
        if (PRIMARY_CONTROLS > 3) {
            mPrimaryActionsAdapter.add(new PlaybackControlsRow.FastForwardAction(getActivity()));
        }
        mPrimaryActionsAdapter.add(mSkipNextAction);

        mSecondaryActionsAdapter.add(mRepeatAction);
        mSecondaryActionsAdapter.add(mShuffleAction);
        if (PRIMARY_CONTROLS > 5) {
            mPrimaryActionsAdapter.add(mThumbsDownAction);
        } else {
            mSecondaryActionsAdapter.add(mThumbsDownAction);
        }
        mSecondaryActionsAdapter.add(new PlaybackControlsRow.HighQualityAction(getActivity()));
        mSecondaryActionsAdapter.add(new PlaybackControlsRow.ClosedCaptioningAction(getActivity()));
    }

    protected boolean getShowDetail() {
        return false;
    }

    public ArrayObjectAdapter getRowArrayObjectAdapter(IHeaderItem headerItem,PresenterSelector presenterSelector) {
        return new WatcherArrayObjectAdapter(presenterSelector);
    }
    protected abstract HashMap<Class<?>,Object> getClassPresenterMap();

    protected Class<?> getPlaybackControlsRowClass() {
        return PlaybackControlsRow.class;
    }


    protected PlaybackControlsRowPresenter createActionDetailedViewPresenter() {
        PlaybackControlsRowPresenter playbackControlsRowPresenter;
        if (getShowDetail()) {
            playbackControlsRowPresenter = new PlaybackControlsRowPresenter(createDetailsDescriptionPresenter());
        } else {
            playbackControlsRowPresenter = new PlaybackControlsRowPresenter();
        }

        playbackControlsRowPresenter.setOnActionClickedListener(this);
        playbackControlsRowPresenter.setSecondaryActionsHidden(getHideMoreActions());
        return playbackControlsRowPresenter;
    }

    protected boolean getHideMoreActions() {
        return false;
    }

    abstract protected AbstractDetailsDescriptionPresenter createDetailsDescriptionPresenter();
    protected abstract Object createDetailsData();

    public ArrayObjectAdapter getRowArrayObjectAdapter(IHeaderItem headerItem,Presenter presenter) {
        return new ArrayObjectAdapter(presenter);
    }
    public Class getSearchActivity() {
        return null;
    }
    @Override
    final public String getFirstTimeLoaderMessage() {
        return null;
    }


    @Override
    public void onItemClicked(Presenter.ViewHolder itemViewHolder, Object item,RowPresenter.ViewHolder rowViewHolder, Row row) {

    }

    @Override
    public void onItemSelected(Presenter.ViewHolder itemViewHolder, Object item,RowPresenter.ViewHolder rowViewHolder, Row row) {

    }

    private void setupUIElements() {
        if(getSearchAffordanceColorRes()!=-1)
            setSearchAffordanceColor(getResources().getColor(getSearchAffordanceColorRes()));
        else if(getSearchAffordanceColorValue()!=-1)
            setSearchAffordanceColor(getSearchAffordanceColorValue());

        setBackgroundType(BACKGROUND_TYPE);
        setFadingEnabled(false);

    }
    protected int getSearchAffordanceColorValue()
    {
        return -1;
    }
    @ColorRes
    protected int getSearchAffordanceColorRes()
    {
        return R.color.fastlane_background;
    }
}
