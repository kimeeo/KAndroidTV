package com.kimeeo.kAndroidTV.demoModel;

import android.os.Handler;

import com.kimeeo.kAndroid.dataProvider.StaticDataProvider;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by BhavinPadhiyar on 23/05/16.
 */
public class MovieListDataProvider extends StaticDataProvider {
    public MovieListDataProvider() {
        setNextEnabled(false);
        setRefreshEnabled(false);
        List list = new ArrayList();
        for (int i = 0; i < 10; i++) {
            Movie m = new Movie();
            m.setTitle("Item:"+i);
            m.setStudio("Youtunbe");
            if(i>3)
                m.setType(1);
            else
                m.setType(2);
            list.add(m);
        }
        addData(list);
    }

    @Override
    protected void invokeLoadNext() {}

    @Override
    protected void invokeLoadRefresh() {}
}