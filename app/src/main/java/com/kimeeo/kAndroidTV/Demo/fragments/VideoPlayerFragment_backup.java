package com.kimeeo.kAndroidTV.Demo.fragments;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v17.leanback.app.BackgroundManager;
import android.support.v17.leanback.widget.ArrayObjectAdapter;
import android.support.v17.leanback.widget.HeaderItem;
import android.support.v17.leanback.widget.ListRow;
import android.support.v17.leanback.widget.ListRowPresenter;
import android.support.v17.leanback.widget.ObjectAdapter;
import android.support.v17.leanback.widget.Presenter;
import android.support.v17.leanback.widget.PresenterSelector;
import android.support.v17.leanback.widget.Row;
import android.support.v17.leanback.widget.RowPresenter;

import com.kimeeo.kAndroid.dataProvider.DataProvider;
import com.kimeeo.kAndroidTV.Demo.DetailsActivity;
import com.kimeeo.kAndroidTV.Demo.PageActivity;
import com.kimeeo.kAndroidTV.Demo.R;
import com.kimeeo.kAndroidTV.Demo.SearchActivity;
import com.kimeeo.kAndroidTV.Demo.dataProviders.HeaderDataProvider;
import com.kimeeo.kAndroidTV.Demo.dataProviders.Movie;
import com.kimeeo.kAndroidTV.Demo.presenter.Row1PresenterSelector;
import com.kimeeo.kAndroidTV.Demo.presenter.Row2PresenterSelector;
import com.kimeeo.kAndroidTV.core.IHeaderItem;
import com.kimeeo.kAndroidTV.youtube.AbstractVideoPlayerFragment;

/**
 * Created by BhavinPadhiyar on 5/20/17.
 */

public class VideoPlayerFragment_backup extends AbstractVideoPlayerFragment {
    @NonNull
    @Override
    public DataProvider createDataProvider() {
        return new HeaderDataProvider();
    }
    public void onItemClicked(Presenter.ViewHolder itemViewHolder, Object item, RowPresenter.ViewHolder rowViewHolder, Row row) {

        Movie movie = (Movie) item;
        Intent intent = new Intent(getActivity(), PageActivity.class);
        intent.putExtra(DetailsActivity.MOVIE, movie);
        getActivity().startActivity(intent);
    }

    @Override
    public PresenterSelector getRowItemPresenterSelector(IHeaderItem headerItem) {
        if(headerItem.getID().equals("0"))
            return new Row1PresenterSelector();
        return new Row2PresenterSelector();
    }
    public static class ShodowListRow extends ListRow
    {
        public ShodowListRow(HeaderItem header, ObjectAdapter adapter) {
            super(header, adapter);

        }
    }

    public static class ShadowRowPresenterSelector extends PresenterSelector {

        private ListRowPresenter mShadowEnabledRowPresenter = new ListRowPresenter();
        private ListRowPresenter mShadowDisabledRowPresenter = new ListRowPresenter();

        public ShadowRowPresenterSelector() {
            mShadowEnabledRowPresenter.setNumRows(1);
            mShadowDisabledRowPresenter.setShadowEnabled(false);
        }

        @Override public Presenter getPresenter(Object item) {
            if (item instanceof BrowseFragment.ShodowListRow)
                return mShadowDisabledRowPresenter;
            else
                return mShadowEnabledRowPresenter;
        }

        @Override
        public Presenter[] getPresenters() {
            return new Presenter [] {
                    mShadowDisabledRowPresenter,
                    mShadowEnabledRowPresenter
            };
        }
    }

    public Class getSearchActivity() {
        return SearchActivity.class;
    }
}
