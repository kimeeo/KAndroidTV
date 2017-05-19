package com.kimeeo.kAndroidTV.demoModel;

import android.os.Handler;

import com.kimeeo.kAndroid.dataProvider.StaticDataProvider;
import com.kimeeo.kAndroidTV.core.IHeaderItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by BhavinPadhiyar on 23/05/16.
 */
public class HeaderDataProvider extends StaticDataProvider {
    public HeaderDataProvider() {
        setNextEnabled(false);
        List list = new ArrayList();
        for (int i = 0; i < 3; i++) {
            Header h=new Header();
            h.setId(i+"");
            h.setName("Category " +i);
            h.setData(new MovieListDataProvider());
            list.add(h);
        }
        addData(list);
    }

    @Override
    protected void invokeLoadNext() {

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