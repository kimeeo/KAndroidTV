package com.kimeeo.kAndroidTV.Demo.pageFragment;

import android.app.Fragment;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v17.leanback.app.BrowseFragment;
import android.support.v17.leanback.widget.ArrayObjectAdapter;
import android.support.v17.leanback.widget.HeaderItem;
import android.support.v17.leanback.widget.ListRow;
import android.support.v17.leanback.widget.ListRowPresenter;
import android.support.v17.leanback.widget.ObjectAdapter;
import android.support.v17.leanback.widget.Presenter;
import android.support.v17.leanback.widget.PresenterSelector;
import android.support.v17.leanback.widget.Row;
import android.support.v17.leanback.widget.RowPresenter;
import android.webkit.WebView;

import com.kimeeo.kAndroid.dataProvider.DataProvider;
import com.kimeeo.kAndroidTV.Demo.DetailsActivity;
import com.kimeeo.kAndroidTV.Demo.SearchActivity;
import com.kimeeo.kAndroidTV.Demo.VerticalGridActivity;
import com.kimeeo.kAndroidTV.Demo.browseFragment.MainFragment;
import com.kimeeo.kAndroidTV.Demo.dataProviders.HeaderDataProvider;
import com.kimeeo.kAndroidTV.Demo.dataProviders.Movie;
import com.kimeeo.kAndroidTV.Demo.presenter.Row1PresenterSelector;
import com.kimeeo.kAndroidTV.Demo.presenter.Row2PresenterSelector;
import com.kimeeo.kAndroidTV.Demo.verticalGridFragment.VerticalGridFragment;
import com.kimeeo.kAndroidTV.browseFragment.AbstractBrowseFragment;
import com.kimeeo.kAndroidTV.browseFragment.IHeaderItem;
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
    protected Fragment getFragmentForRow(Object rowObj) {
        if(rowObj instanceof PageRow)
        {
            PageRow page= (PageRow) rowObj;
            if((page.getHeaderData()).getID().equals("1"))
            {
                return new MyMainFragment();
            }
        }
        return new MyVerticalGridFragment();
    }
    public static class MyVerticalGridFragment extends VerticalGridFragment implements MainFragmentAdapterProvider
    {
        private BrowseFragment.MainFragmentAdapter mMainFragmentAdapter = new BrowseFragment.MainFragmentAdapter(this);
        @Override
        public BrowseFragment.MainFragmentAdapter getMainFragmentAdapter() {
            return mMainFragmentAdapter;
        }
    }

    public static class MyMainFragment extends MainFragment implements MainFragmentAdapterProvider
    {
        private BrowseFragment.MainFragmentAdapter mMainFragmentAdapter = new BrowseFragment.MainFragmentAdapter(this);
        @Override
        public BrowseFragment.MainFragmentAdapter getMainFragmentAdapter() {
            return mMainFragmentAdapter;
        }
    }

}