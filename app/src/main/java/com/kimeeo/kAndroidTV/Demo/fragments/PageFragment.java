package com.kimeeo.kAndroidTV.Demo.fragments;

import android.app.Fragment;
import android.support.v17.leanback.app.BackgroundManager;

import com.kimeeo.kAndroid.dataProvider.DataProvider;
import com.kimeeo.kAndroidTV.Demo.R;
import com.kimeeo.kAndroidTV.Demo.dataProviders.HeaderDataProvider;
import com.kimeeo.kAndroidTV.pageFragments.AbstractPageFragment;
import com.kimeeo.kAndroidTV.pageFragments.PageRow;

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
        @Override
        public boolean supportBackgroundChange() {
            return false;
        }
        int count=1;
        @Override
        public void updateBackground(BackgroundManager mBackgroundManager, Object item, int width, int height)
        {
            if(count==1) {
                count=0;
                mBackgroundManager.setDrawable(getResources().getDrawable(R.drawable.background_canyon));
            }
            else {
                count=1;
                mBackgroundManager.setDrawable(getResources().getDrawable(R.drawable.image));
            }
        }
    }

    public static class MyRowsFragment extends RowsFragment implements MainFragmentAdapterProvider
    {
        int count=1;
        @Override
        public void updateBackground(BackgroundManager mBackgroundManager, Object item, int width, int height)
        {
            if(count==1) {
                count=0;
                mBackgroundManager.setDrawable(getResources().getDrawable(R.drawable.background_canyon));
            }
            else {
                count=1;
                mBackgroundManager.setDrawable(getResources().getDrawable(R.drawable.image));
            }
        }

        @Override
        public boolean supportBackgroundChange() {
            return false;
        }

        private MainFragmentAdapter mMainFragmentAdapter = new MainFragmentAdapter(this);
        @Override
        public MainFragmentAdapter getMainFragmentAdapter() {
            return mMainFragmentAdapter;
        }
    }


}