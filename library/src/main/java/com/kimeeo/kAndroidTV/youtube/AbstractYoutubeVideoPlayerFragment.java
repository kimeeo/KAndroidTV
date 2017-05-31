package com.kimeeo.kAndroidTV.youtube;

import android.app.Activity;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.session.MediaSession;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.ColorRes;
import android.support.annotation.NonNull;
import android.support.v17.leanback.app.PlaybackOverlayFragment;
import android.support.v17.leanback.widget.AbstractDetailsDescriptionPresenter;
import android.support.v17.leanback.widget.Action;
import android.support.v17.leanback.widget.ArrayObjectAdapter;
import android.support.v17.leanback.widget.ClassPresenterSelector;
import android.support.v17.leanback.widget.ControlButtonPresenterSelector;
import android.support.v17.leanback.widget.HeaderItem;
import android.support.v17.leanback.widget.ListRow;
import android.support.v17.leanback.widget.OnActionClickedListener;
import android.support.v17.leanback.widget.PlaybackControlsRow;
import android.support.v17.leanback.widget.PlaybackControlsRowPresenter;
import android.support.v17.leanback.widget.Presenter;
import android.support.v17.leanback.widget.PresenterSelector;
import android.support.v17.leanback.widget.Row;
import android.support.v17.leanback.widget.RowPresenter;
import android.support.v7.app.AlertDialog;

import com.kimeeo.kAndroid.dataProvider.DataProvider;
import com.kimeeo.kAndroidTV.R;
import com.kimeeo.kAndroidTV.core.AbstractArrayObjectAdapter;
import com.kimeeo.kAndroidTV.core.BackgroundImageHelper;
import com.kimeeo.kAndroidTV.core.DefaultArrayObjectAdapter;
import com.kimeeo.kAndroidTV.core.IHeaderItem;
import com.kimeeo.kAndroidTV.core.RowBasedFragmentHelper;
import com.kimeeo.kAndroidTV.core.WatcherArrayObjectAdapter;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
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

abstract public class AbstractYoutubeVideoPlayerFragment extends PlaybackOverlayFragment implements RowBasedFragmentHelper.HelperProvider,OnActionClickedListener,IPlayerListener,IProgressUpdateListener,IBufferStateListener
{
    public YoutubeTvView getYoutubePlayer() {
        return youtubePlayer;
    }

    private YoutubeTvView youtubePlayer;
    private List<Action> secondaryActionsList;
    private String videoId;
    public AbstractYoutubeVideoPlayerFragment(YoutubeTvView view)
    {
        this.youtubePlayer=view;
    }
    public AbstractYoutubeVideoPlayerFragment()
    {
        youtubePlayer=null;
    }

    public static final String TAG = "VideoPlayerFragment";

    private static final int BACKGROUND_TYPE = PlaybackOverlayFragment.BG_DARK;


    private ArrayObjectAdapter mPrimaryActionsAdapter;
    private ArrayObjectAdapter mSecondaryActionsAdapter;


    private PlaybackControlsRow.PlayPauseAction mPlayPauseAction;
    private PlaybackControlsRow.FastForwardAction mFastForwardAction;
    private PlaybackControlsRow.RewindAction mRewindAction;



    private PlaybackControlsRow mPlaybackControlsRow;



    @Override
    public boolean supportAutoPageLoader() {return true;}
    public boolean getSupportRowProgressBar()
    {
        return false;
    }

    final public BackgroundImageHelper getBackgroundImageHelper(){return null;}
    final public boolean supportBackgroundChange(){return false;}

    public void setPlaybackQuality(int what) {
        setPlaybackQuality(youtubePlayer.getAvailableQualityLevels().get(what));
    }

    public void setPlaybackQuality(VideoQuality suggestedQuality) {
        youtubePlayer.setPlaybackQuality(suggestedQuality);
    }

    public void onActionClicked(Action action)
    {
        if (action.getId() == mPlayPauseAction.getId()) {
            togglePlayback();
        }
        else if (action.getId() == mFastForwardAction.getId()) {
            youtubePlayer.seekTo((int)youtubePlayer.getCurrentPosition()+seekSize());
        }
        else if (action.getId() == mRewindAction.getId()) {
            youtubePlayer.seekTo((int)youtubePlayer.getCurrentPosition()-seekSize());
        }
        else if (action instanceof PlaybackControlsRow.HighQualityAction) {
            List<VideoQuality> list=youtubePlayer.getAvailableQualityLevels();
             if(list!=null && list.size()>=2)
                ((AbstractYoutubeActivity)getActivity()).openQualitySelector(youtubePlayer.getAvailableQualityLevels());
        }
        else if (action instanceof PlaybackControlsRow.PictureInPictureAction) {
            if(getActivity() instanceof AbstractYoutubeActivity && ((AbstractYoutubeActivity) getActivity()).supportsPictureInPicture())
                getActivity().enterPictureInPictureMode();
        }
        else
        {

            onAction(action);
        }
    }

    public void onAction(Action action)
    {

    }

    protected int seekSize() {
        return 10;
    }

    public Row getListRow(IHeaderItem headerItem, HeaderItem header, ArrayObjectAdapter listRowAdapter) {
        return new ListRow(header, listRowAdapter);
    }
    public void togglePlayback() {
        youtubePlayer.playPause();
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
        youtubePlayer.stopVideo();
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

    public PlaybackControlsRowPresenter getPlaybackControlsRowPresenter() {
        return playbackControlsRowPresenter;
    }

    PlaybackControlsRowPresenter playbackControlsRowPresenter;

    protected void setupUi() {

        data =createDetailsData();
        playbackControlsRowPresenter = createPlaybackControlsRowPresenter();

        if(youtubePlayer==null)
            youtubePlayer = (YoutubeTvView) getActivity().findViewById(R.id.youtubetv_view);

        youtubePlayer.addPlayerListener(this);

        fragmentHelper = createBrowseFragmentHelper();
        dataProvider=createDataProvider();
        configDataManager(dataProvider);
    }

    public void updateView(Bundle bundle) {
        youtubePlayer.updateView(bundle);
    }



    VideoState videoState;
    @Override
    public void onPlayerStateChange(VideoState state, long position, float speed, float duration, VideoInfo videoInfo) {
        if (videoState!=state && VideoState.getPlayerState(state.getIndex()) == VideoState.PAUSED) {
            videoState=state;
            mPlayPauseAction.setIcon(mPlayPauseAction.getDrawable(PlaybackControlsRow.PlayPauseAction.PLAY));
            notifyChanged(mPlayPauseAction);
            setFadingEnabled(false);
        } else if (videoState!=state && VideoState.getPlayerState(state.getIndex()) == VideoState.PLAYING) {
            videoState=state;
            mPlayPauseAction.setIcon(mPlayPauseAction.getDrawable(PlaybackControlsRow.PlayPauseAction.PAUSE));
            notifyChanged(mPlayPauseAction);
            setFadingEnabled(true);
        }
        else if (VideoState.getPlayerState(state.getIndex()) == VideoState.BUFFERING) {

        }
    }


    int totalTime=0;
    @Override
    public void onPlayerReady(VideoInfo videoInfo) {
        videoId = videoInfo.getVideoId();
        youtubePlayer.setOnBufferingUpdateListener(this);
        youtubePlayer.setOnProgressUpdateListener(this);
    }
    @Override
    public void onProgressUpdate(float currentTime)
    {
        mPlaybackControlsRow.setCurrentTimeLong((long)currentTime*1000);
    }

    @Override
    public void onBufferUpdate(float videoDuration, float loadedFraction)
    {
        if(totalTime==0) {
            totalTime =(int)videoDuration;
            fragmentHelper.build();
            fragmentHelper.next();
            addPlaybackControlsRow();
            loadCoverImage(mPlaybackControlsRow);
        }
        mPlaybackControlsRow.setBufferedProgressLong((long)loadedFraction*1000);
    }

    private void addPlaybackControlsRow() {
        if (getShowDetail()) {
            mPlaybackControlsRow = new PlaybackControlsRow(getData());
        } else {
            mPlaybackControlsRow = new PlaybackControlsRow();
        }
        fragmentHelper.getRowsAdapter().add(mPlaybackControlsRow);
        loadCoverImage(mPlaybackControlsRow,getData());
        mPlaybackControlsRow.setTotalTimeLong(totalTime*1000);

        ControlButtonPresenterSelector presenterSelector = new ControlButtonPresenterSelector();
        mPrimaryActionsAdapter = new ArrayObjectAdapter(presenterSelector);
        mPlaybackControlsRow.setPrimaryActionsAdapter(mPrimaryActionsAdapter);

        mPlayPauseAction = new PlaybackControlsRow.PlayPauseAction(getActivity());
        mFastForwardAction = new PlaybackControlsRow.FastForwardAction(getActivity());
        mRewindAction = new PlaybackControlsRow.RewindAction(getActivity());
        mPrimaryActionsAdapter.add(mRewindAction);
        mPrimaryActionsAdapter.add(mPlayPauseAction);
        mPrimaryActionsAdapter.add(mFastForwardAction);
        setFadingEnabled(false);
        List<Action> list = getSecondaryActionsList();
        if(list!=null && list.size()!=0)
        {
            mSecondaryActionsAdapter = new ArrayObjectAdapter(presenterSelector);
            mPlaybackControlsRow.setSecondaryActionsAdapter(mSecondaryActionsAdapter);
            playbackControlsRowPresenter.setSecondaryActionsHidden(false);
            for (int i = 0; i < list.size(); i++) {
                if(list.get(i) instanceof PlaybackControlsRow.PictureInPictureAction )
                {
                    if(getActivity() instanceof AbstractYoutubeActivity && ((AbstractYoutubeActivity) getActivity()).supportsPictureInPicture())
                        mSecondaryActionsAdapter.add(list.get(i));
                }
                else
                    mSecondaryActionsAdapter.add(list.get(i));
            }
        }
    }

    protected void loadCoverImage(PlaybackControlsRow mPlaybackControlsRow,Object data) {
        mPlaybackControlsRow.setImageDrawable(getActivity().getDrawable(R.drawable._you_tube_image));
    }

    protected void loadCoverImage(PlaybackControlsRow mPlaybackControlsRow) {
        mPlaybackControlsRow.setImageDrawable(getActivity().getDrawable(R.drawable._you_tube_image));
        DownloadImagesTask task = new DownloadImagesTask(mPlaybackControlsRow);
        task.execute(videoId);
    }


    protected boolean getShowDetail() {
        return true;
    }

    public ArrayObjectAdapter getRowArrayObjectAdapter(IHeaderItem headerItem,PresenterSelector presenterSelector) {
        return new WatcherArrayObjectAdapter(presenterSelector);
    }
    protected abstract HashMap<Class<?>,Object> getClassPresenterMap();

    protected Class<?> getPlaybackControlsRowClass() {
        return PlaybackControlsRow.class;
    }


    protected PlaybackControlsRowPresenter createPlaybackControlsRowPresenter() {
        PlaybackControlsRowPresenter playbackControlsRowPresenter;
        if (getShowDetail()) {
            playbackControlsRowPresenter = new PlaybackControlsRowPresenter(createDetailsDescriptionPresenter());
        } else {
            playbackControlsRowPresenter = new PlaybackControlsRowPresenter();
        }

        playbackControlsRowPresenter.setOnActionClickedListener(this);
        if(getProgressColor()!=-1)
            playbackControlsRowPresenter.setProgressColor(getProgressColor());
        return playbackControlsRowPresenter;
    }

    protected int getProgressColor() {
        return -1;
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

    public void play(String videoId) {
        this.videoId=videoId;
        youtubePlayer.playVideo(videoId);
    }


    public List<Action> getSecondaryActionsList() {
        return secondaryActionsList;
    }

    public void setSecondaryActionsList(List<Action> secondaryActionsList) {
        this.secondaryActionsList = secondaryActionsList;
    }

    public void closePlayer() {
        youtubePlayer.closePlayer();
    }
    public boolean isPlaying() {
        return youtubePlayer.isPlaying();
    }
    public void stopVideo() {
        youtubePlayer.stopVideo();
    }



    public class DownloadImagesTask extends AsyncTask<String, Void, Bitmap> {

        PlaybackControlsRow mPlaybackControlsRow;
        public DownloadImagesTask(PlaybackControlsRow mPlaybackControlsRow)
        {
            this.mPlaybackControlsRow=mPlaybackControlsRow;
        }
        @Override
        protected Bitmap doInBackground(String... urls) {
            String url="https://img.youtube.com/vi/"+urls[0]+"/mqdefault.jpg";
            return downloadImage(url);
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            Drawable d = new BitmapDrawable(getResources(), bitmap);
            mPlaybackControlsRow.setImageDrawable(d);
            fragmentHelper.getRowsAdapter().notifyItemRangeChanged(0,1);
        }

        private Bitmap downloadImage(String url) {

            Bitmap bmp =null;
            try{
                URL ulrn = new URL(url);
                HttpURLConnection con = (HttpURLConnection)ulrn.openConnection();
                InputStream is = con.getInputStream();
                bmp = BitmapFactory.decodeStream(is);
                if (null != bmp)
                    return bmp;

            }catch(Exception e){}
            return bmp;
        }
    }


}
