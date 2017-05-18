package com.kimeeo.kAndroidTV.Demo.dataProviders;

import android.os.Handler;

import com.kimeeo.kAndroid.dataProvider.StaticDataProvider;
import com.kimeeo.kAndroidTV.browseFragment.IHeaderItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by BhavinPadhiyar on 23/05/16.
 */
public class HeaderDataProvider extends StaticDataProvider {
    int count = 1;
    Handler h = new Handler();
    Runnable r = new Runnable() {
        @Override
        public void run() {
            List list = new ArrayList();
            for (int i = 0; i < 3; i++) {
                Header h=new Header();
                h.setId(i+"");
                h.setName("Category " +count);
                h.setData(new MovieListDataProvider());
                list.add(h);
                count++;
            }
            addData(list);
        }
    };
    private int pageCount = 1;
    public HeaderDataProvider() {
        setNextEnabled(true);
    }

    @Override
    protected void invokeLoadNext() {
        if (pageCount != 4) {
            h.postDelayed(r, 1000);
            pageCount += 1;
        } else {
            setCanLoadNext(false);
            dataLoadError(null);
        }
    }

    @Override
    protected void invokeLoadRefresh() {}


    public static class Header implements IHeaderItem
    {
        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setData(List<Object> data) {
            this.data = data;
        }

        private String id;
        private String name;
        private List<Object> data;

        @Override
        public String getID() {
            return id;
        }

        @Override
        public String getName() {
            return name;
        }

        @Override
        public List<Object> getData() {
            return data;
        }
    }
}