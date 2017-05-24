package com.kimeeo.kAndroidTV.Demo.fragments;

import android.support.annotation.NonNull;
import android.support.v17.leanback.app.BackgroundManager;
import android.support.v17.leanback.widget.Presenter;
import android.support.v17.leanback.widget.PresenterSelector;
import android.support.v17.leanback.widget.Row;
import android.support.v17.leanback.widget.RowPresenter;

import com.kimeeo.kAndroid.dataProvider.DataProvider;
import com.kimeeo.kAndroidTV.Demo.R;
import com.kimeeo.kAndroidTV.Demo.SearchActivity;
import com.kimeeo.kAndroidTV.Demo.dataProviders.MovieListDataProvider;
import com.kimeeo.kAndroidTV.Demo.presenter.Row2PresenterSelector;
import com.kimeeo.kAndroidTV.verticalGridFragment.AbstractVerticalGridFragment;

/**
 * Created by BhavinPadhiyar on 5/18/17.
 */

public class VerticalGridFragment extends AbstractVerticalGridFragment {


    @Override
    public boolean supportBackgroundChange() {
        return true;
    }
    int count=1;
    @Override
    public void updateBackground(BackgroundManager mBackgroundManager, Object item, int width, int height)
    {
        if(count==1) {
            count=0;
            mBackgroundManager.setDrawable(getResources().getDrawable(R.drawable.background_canyon));
        }
        else {
            count=1;
            mBackgroundManager.setDrawable(getResources().getDrawable(R.drawable.image));
        }
    }

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
