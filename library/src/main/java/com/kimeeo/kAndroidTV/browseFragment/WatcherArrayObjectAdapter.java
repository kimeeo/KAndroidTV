package com.kimeeo.kAndroidTV.browseFragment;

import android.support.v17.leanback.widget.Presenter;
import android.support.v17.leanback.widget.PresenterSelector;

import com.kimeeo.kAndroid.dataProvider.DataProvider;
import com.kimeeo.kAndroid.dataProvider.MonitorList;

import java.util.List;

/**
 * Created by BhavinPadhiyar on 5/17/17.
 */

public class WatcherArrayObjectAdapter extends AbstractArrayObjectAdapter implements MonitorList.OnChangeWatcher {
    public WatcherArrayObjectAdapter(PresenterSelector presenterSelector) {
        super(presenterSelector);
    }

    public WatcherArrayObjectAdapter(Presenter presenter) {
        super(presenter);
    }

    public void setDataProvider(DataProvider rowData) {
        rowData.addDataChangeWatcher(this);
    }
    @Override
    public void itemsAdded(int i, List list) {
        for (int j = 0; j < list.size(); j++) {
            add(list.get(j));
        }
    }
    @Override
    public void itemsRemoved(int i, List list) {
        removeItems(i,list.size());
    }

    @Override
    public void itemsChanged(int i, List list) {
        notifyArrayItemRangeChanged(i,list.size());
    }
}
