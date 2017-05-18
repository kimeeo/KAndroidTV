package com.kimeeo.kAndroidTV.Demo.verticalGridFragment;

import android.content.Intent;
import android.support.annotation.NonNull;
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
import com.kimeeo.kAndroidTV.Demo.SearchActivity;
import com.kimeeo.kAndroidTV.Demo.browseFragment.MainFragment;
import com.kimeeo.kAndroidTV.Demo.dataProviders.HeaderDataProvider;
import com.kimeeo.kAndroidTV.Demo.dataProviders.Movie;
import com.kimeeo.kAndroidTV.Demo.dataProviders.MovieListDataProvider;
import com.kimeeo.kAndroidTV.Demo.presenter.Row1PresenterSelector;
import com.kimeeo.kAndroidTV.Demo.presenter.Row2PresenterSelector;
import com.kimeeo.kAndroidTV.browseFragment.IHeaderItem;
import com.kimeeo.kAndroidTV.verticalGridFragment.AbstractVerticalGridFragment;

/**
 * Created by BhavinPadhiyar on 5/18/17.
 */

public class VerticalGridFragment extends AbstractVerticalGridFragment {

    @NonNull
    @Override
    protected DataProvider createDataProvider() {
        return new MovieListDataProvider();
    }

    @Override
    protected PresenterSelector getPresenterSelector() {
        return new Row2PresenterSelector();
    }
    protected void onItemClicked(Presenter.ViewHolder itemViewHolder, Object item, RowPresenter.ViewHolder rowViewHolder, Row row) {


    }
    protected Class getSearchActivity() {
        return SearchActivity.class;
    }
}
