package com.kimeeo.kAndroidTV.detailsFragment;

import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.ColorRes;
import android.support.annotation.NonNull;
import android.support.v17.leanback.app.DetailsFragment;
import android.support.v17.leanback.app.DetailsFragmentBackgroundController;
import android.support.v17.leanback.widget.Action;
import android.support.v17.leanback.widget.ArrayObjectAdapter;
import android.support.v17.leanback.widget.ClassPresenterSelector;
import android.support.v17.leanback.widget.DetailsOverviewRow;
import android.support.v17.leanback.widget.FullWidthDetailsOverviewRowPresenter;
import android.support.v17.leanback.widget.FullWidthDetailsOverviewSharedElementHelper;
import android.support.v17.leanback.widget.HeaderItem;
import android.support.v17.leanback.widget.OnActionClickedListener;
import android.support.v17.leanback.widget.Presenter;
import android.support.v17.leanback.widget.PresenterSelector;
import android.support.v17.leanback.widget.Row;
import android.support.v17.leanback.widget.RowPresenter;

import com.kimeeo.kAndroid.dataProvider.DataProvider;
import com.kimeeo.kAndroid.dataProvider.StaticDataProvider;
import com.kimeeo.kAndroidTV.R;
import com.kimeeo.kAndroidTV.core.AbstractArrayObjectAdapter;
import com.kimeeo.kAndroidTV.core.BackgroundImageHelper;
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

abstract public class AbstractDetailsFragment extends AbstractDetailsWithRowsFragment
{
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
