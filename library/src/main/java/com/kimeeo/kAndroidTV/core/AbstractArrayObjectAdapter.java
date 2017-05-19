package com.kimeeo.kAndroidTV.core;

import android.support.v17.leanback.widget.ArrayObjectAdapter;
import android.support.v17.leanback.widget.HeaderItem;
import android.support.v17.leanback.widget.ListRow;
import android.support.v17.leanback.widget.Presenter;
import android.support.v17.leanback.widget.PresenterSelector;

import com.kimeeo.kAndroid.dataProvider.DataProvider;
import com.kimeeo.kAndroid.dataProvider.MonitorList;

import java.util.Collections;
import java.util.List;

/**
 * Created by BhavinPadhiyar on 5/17/17.
 */

abstract public class AbstractArrayObjectAdapter extends ArrayObjectAdapter{
    public AbstractArrayObjectAdapter(PresenterSelector presenterSelector) {
        super(presenterSelector);
    }

    public AbstractArrayObjectAdapter(Presenter presenter) {
        super(presenter);
    }
    public AbstractArrayObjectAdapter() {
        super();
    }


}
