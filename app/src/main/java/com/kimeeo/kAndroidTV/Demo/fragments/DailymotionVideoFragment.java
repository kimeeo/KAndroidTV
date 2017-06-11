package com.kimeeo.kAndroidTV.Demo.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v17.leanback.widget.AbstractDetailsDescriptionPresenter;
import android.support.v17.leanback.widget.Action;
import android.support.v17.leanback.widget.ListRow;
import android.support.v17.leanback.widget.ListRowPresenter;
import android.support.v17.leanback.widget.PlaybackControlsRow;
import android.support.v17.leanback.widget.PresenterSelector;

import com.kimeeo.kAndroid.dataProvider.DataProvider;
import com.kimeeo.kAndroidTV.Demo.dataProviders.HeaderDataProvider;
import com.kimeeo.kAndroidTV.Demo.dataProviders.Movie;
import com.kimeeo.kAndroidTV.Demo.presenter.Row1PresenterSelector;
import com.kimeeo.kAndroidTV.Demo.presenter.Row2PresenterSelector;
import com.kimeeo.kAndroidTV.core.IHeaderItem;
import com.kimeeo.kAndroidTV.dailymotion.AbstractDailymotionVideoPlayerFragment;
import com.kimeeo.kAndroidTV.dailymotion.DMWebVideoView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by BhavinPadhiyar on 5/18/17.
 */

public class DailymotionVideoFragment extends AbstractDailymotionVideoPlayerFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    public DailymotionVideoFragment(DMWebVideoView view) {
        super(view);

    }
    public List<Action> getSecondaryActionsList() {
        List<Action> list= new ArrayList<>();
        list.add(new PlaybackControlsRow.RepeatAction(getActivity()));
        list.add(new PlaybackControlsRow.ThumbsUpAction(getActivity()));
        list.add(new PlaybackControlsRow.ThumbsDownAction(getActivity()));
        list.add(new PlaybackControlsRow.HighQualityAction(getActivity()));
        list.add(new PlaybackControlsRow.ClosedCaptioningAction(getActivity()));
        list.add(new PlaybackControlsRow.PictureInPictureAction(getActivity()));
        setSecondaryActionsList(list);
        return list;
    }

    public DailymotionVideoFragment() {

    }
    @Override
    public void onAction(Action action)
    {
        System.out.println(action);
    }


    @Override
    protected AbstractDetailsDescriptionPresenter createDetailsDescriptionPresenter() {
        return new DetailsDescriptionPresenter();
    }

    @NonNull
    @Override
    public DataProvider createDataProvider() {
        return new HeaderDataProvider();
    }

    @Override
    public PresenterSelector getRowItemPresenterSelector(IHeaderItem headerItem) {
        if(headerItem.getID().equals("0"))
            return new Row1PresenterSelector();
        return new Row2PresenterSelector();
    }
    @Override
    protected Object createDetailsData() {
        Movie data = new Movie();
        data.setTitle("My Moview");
        data.setStudio("Kimeeo");
        return data;
    }

    @Override
    protected HashMap<Class<?>, Object> getClassPresenterMap() {
        HashMap<Class<?>, Object> map = new HashMap<>();
        map.put(ListRow.class, new ListRowPresenter());
        return map;
    }

    static class DetailsDescriptionPresenter extends AbstractDetailsDescriptionPresenter {
        @Override
        protected void onBindDescription(ViewHolder viewHolder, Object item) {
            viewHolder.getTitle().setText(((Movie) item).getTitle());
            viewHolder.getSubtitle().setText(((Movie) item).getStudio());
        }
    }
}
