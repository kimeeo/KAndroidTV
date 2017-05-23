package com.kimeeo.kAndroidTV.core;

import android.support.v17.leanback.widget.Presenter;
import android.support.v17.leanback.widget.PresenterSelector;

import com.kimeeo.kAndroid.dataProvider.DataProvider;
import com.kimeeo.kAndroid.dataProvider.MonitorList;

import java.util.List;
import java.util.logging.Handler;

/**
 * Created by BhavinPadhiyar on 5/17/17.
 */

public class WatcherArrayObjectAdapter extends AbstractArrayObjectAdapter implements MonitorList.OnChangeWatcher,DataProvider.OnFatchingObserve {
    private boolean support=false;

    public WatcherArrayObjectAdapter(PresenterSelector presenterSelector) {
        super(presenterSelector);
    }

    public WatcherArrayObjectAdapter(Presenter presenter) {
        super(presenter);
    }

    public DataProvider getDataProvider() {
        return dataProvider;
    }

    DataProvider dataProvider;
    public void setDataProvider(DataProvider rowData) {
        this.dataProvider=rowData;
        dataProvider.addDataChangeWatcher(this);
        dataProvider.addFatchingObserve(this);
    }
    public void setSupportRowProgressBar(boolean support) {
        this.support=support;
        //if(this.support)

    }
    @Override
    public void itemsAdded(final int i, final List list) {
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



    @Override
    public void onFetchingStart(boolean b) {
        if(dataProvider.size()!=0 && support)
            dataProvider.add(new ProgressCardVO());
    }

    @Override
    public void onFetchingFinish(boolean b) {
        if(dataProvider.size()!=0 && support) {
            if (dataProvider.get(dataProvider.size() - 1) instanceof ProgressCardVO)
                dataProvider.remove(dataProvider.size() - 1);
        }
    }

    @Override
    public void onFetchingEnd(List<?> list, boolean b) {

    }

    @Override
    public void onFetchingError(Object o) {

    }
}
