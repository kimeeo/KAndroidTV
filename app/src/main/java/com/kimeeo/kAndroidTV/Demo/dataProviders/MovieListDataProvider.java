package com.kimeeo.kAndroidTV.Demo.dataProviders;

import android.os.Handler;

import com.kimeeo.kAndroid.dataProvider.StaticDataProvider;
import com.kimeeo.kAndroidTV.browseFragment.IHeaderItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by BhavinPadhiyar on 23/05/16.
 */
public class MovieListDataProvider extends StaticDataProvider {
    private final int row;
    Handler h = new Handler();
    Runnable r = new Runnable() {
        @Override
        public void run() {
            List list = new ArrayList();
            for (int i = 0; i < 10; i++) {
                Movie m = new Movie();
                m.setTitle(" Page:"+pageCount+" Item:"+i);
                m.setStudio("Youtunbe");
                list.add(m);
            }
            addData(list);
        }
    };
    private int pageCount = 1;

    public MovieListDataProvider(int index,IHeaderItem h) {
        setNextEnabled(true);
        setRefreshEnabled(true);
        this.row =index;
    }

    @Override
    protected void invokeLoadNext() {
        if (pageCount != 3) {
            h.postDelayed(r, 2000);
            pageCount += 1;
        } else {
            setCanLoadNext(false);
            dataLoadError(null);
        }
    }

    @Override
    protected void invokeLoadRefresh() {}
}