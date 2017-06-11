package com.kimeeo.kAndroidTV.detailsFragment;

import android.support.annotation.ColorRes;
import android.support.annotation.NonNull;
import android.support.v17.leanback.app.BackgroundManager;
import android.support.v17.leanback.app.DetailsFragmentBackgroundController;
import android.support.v17.leanback.widget.ArrayObjectAdapter;
import android.support.v17.leanback.widget.DetailsOverviewRowPresenter;
import android.support.v17.leanback.widget.HeaderItem;
import android.support.v17.leanback.widget.Presenter;
import android.support.v17.leanback.widget.PresenterSelector;
import android.support.v17.leanback.widget.Row;
import android.support.v17.leanback.widget.RowPresenter;
import android.util.DisplayMetrics;

import com.kimeeo.kAndroid.dataProvider.DataProvider;
import com.kimeeo.kAndroid.dataProvider.StaticDataProvider;
import com.kimeeo.kAndroidTV.R;
import com.kimeeo.kAndroidTV.core.BackgroundImageHelper;
import com.kimeeo.kAndroidTV.core.IHeaderItem;

import java.util.HashMap;

/**
 * Created by BhavinPadhiyar on 5/24/17.
 */

abstract public class AbstractSmallDetailsFragment extends AbstractSmallDetailsWithRowsFragment implements BackgroundImageHelper.OnUpdate {
    final public String getFirstTimeLoaderMessage() {
        return null;
    }
    @NonNull
    @Override
    final public DataProvider createDataProvider() {
        DataProvider dataProvider = new StaticDataProvider();
        dataProvider.setNextEnabled(false);
        dataProvider.setRefreshEnabled(false);
        dataProvider.setCanLoadNext(false);
        dataProvider.setCanLoadRefresh(false);
        return dataProvider;
    }
    @Override
    final protected Presenter getCardPresenterSelector() {
        return null;
    }
    @Override
    final public PresenterSelector getRowItemPresenterSelector(IHeaderItem headerItem) {
        return null;
    }
    final public Row getListRow(IHeaderItem headerItem, HeaderItem header, ArrayObjectAdapter listRowAdapter) {
        return null;
    }
    @Override
    final protected HashMap<Class<?>, Object> getClassPresenterMap() {return null;}
}
