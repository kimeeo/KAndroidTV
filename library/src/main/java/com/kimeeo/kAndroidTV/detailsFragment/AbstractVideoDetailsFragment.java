package com.kimeeo.kAndroidTV.detailsFragment;

import android.app.Fragment;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v17.leanback.app.DetailsFragmentBackgroundController;
import android.support.v17.leanback.media.MediaPlayerGlue;
import android.support.v17.leanback.widget.ArrayObjectAdapter;
import android.support.v17.leanback.widget.HeaderItem;
import android.support.v17.leanback.widget.Presenter;
import android.support.v17.leanback.widget.PresenterSelector;
import android.support.v17.leanback.widget.Row;

import com.kimeeo.kAndroid.dataProvider.DataProvider;
import com.kimeeo.kAndroid.dataProvider.StaticDataProvider;
import com.kimeeo.kAndroidTV.R;
import com.kimeeo.kAndroidTV.core.IHeaderItem;

import java.util.HashMap;

/**
 * Created by BhavinPadhiyar on 5/19/17.
 */

abstract public class AbstractVideoDetailsFragment extends AbstractVideoDetailsWithRowsFragment
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
