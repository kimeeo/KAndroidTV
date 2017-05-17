package com.kimeeo.kAndroidTV.model;

import com.kimeeo.kAndroid.dataProvider.DataProvider;

/**
 * Created by BhavinPadhiyar on 5/16/17.
 */

public interface IDataProvider {
    void setDataProvider(DataProvider dataProvider);
    DataProvider getDataProvider();
}
