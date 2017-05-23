package com.kimeeo.kAndroidTV.Demo.fragments;

import android.support.annotation.NonNull;
import android.support.v17.leanback.widget.Presenter;
import android.support.v17.leanback.widget.PresenterSelector;
import android.support.v17.leanback.widget.Row;
import android.support.v17.leanback.widget.RowPresenter;

import com.kimeeo.kAndroid.dataProvider.DataProvider;
import com.kimeeo.kAndroidTV.Demo.SearchActivity;
import com.kimeeo.kAndroidTV.Demo.dataProviders.MovieListDataProvider;
import com.kimeeo.kAndroidTV.Demo.presenter.Row2PresenterSelector;
import com.kimeeo.kAndroidTV.verticalGridFragment.AbstractVerticalGridFragment;

/**
 * Created by BhavinPadhiyar on 5/18/17.
 */

public class VerticalGridFragment extends AbstractVerticalGridFragment {

    public boolean getSupportRowProgressBar()
    {
        return true;
    }
    @NonNull
    @Override
    protected DataProvider createDataProvider() {
        return new MovieListDataProvider();
    }

    @Override
    public void onItemClicked(Presenter.ViewHolder itemViewHolder, Object item, RowPresenter.ViewHolder rowViewHolder, Row row) {

    }
    public PresenterSelector createMainRowPresenterSelector() {
        return new Row2PresenterSelector();
    }

    @Override
    public Class getSearchActivity() {
        return SearchActivity.class;
    }
}
