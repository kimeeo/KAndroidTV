package com.kimeeo.kAndroidTV.Demo.fragments;

import android.app.Fragment;
import android.support.v17.leanback.widget.PresenterSelector;

import com.kimeeo.kAndroid.dataProvider.DataProvider;
import com.kimeeo.kAndroidTV.Demo.dataProviders.HeaderDataProvider;
import com.kimeeo.kAndroidTV.core.IHeaderItem;
import com.kimeeo.kAndroidTV.pageFragments.AbstractPageFragment;
import com.kimeeo.kAndroidTV.pageFragments.PageRow;
import com.kimeeo.kAndroidTV.webViewFragment.WebViewFragment;

/**
 * Created by BhavinPadhiyar on 5/18/17.
 */

public class PageFragment extends AbstractPageFragment {

    protected DataProvider createDataProvider() {

        return new HeaderDataProvider();
    }

    @Override
    public Fragment getFragmentForItem(Object rowObj) {
        if(rowObj instanceof PageRow)
        {
            PageRow page= (PageRow) rowObj;
            if((page.getHeaderData()).getID().equals("1"))
            {
                return new MyVerticalGridFragment();
            }
        }
        return new MyRowsFragment();
    }
    public static class MyVerticalGridFragment extends VerticalGridFragment implements MainFragmentAdapterProvider
    {
        private MainFragmentAdapter mMainFragmentAdapter = new MainFragmentAdapter(this);
        @Override
        public MainFragmentAdapter getMainFragmentAdapter() {
            return mMainFragmentAdapter;
        }
    }

    public static class MyRowsFragment extends RowsFragment implements MainFragmentAdapterProvider
    {
        private MainFragmentAdapter mMainFragmentAdapter = new MainFragmentAdapter(this);
        @Override
        public MainFragmentAdapter getMainFragmentAdapter() {
            return mMainFragmentAdapter;
        }
    }


}