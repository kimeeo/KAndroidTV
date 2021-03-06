package com.kimeeo.kAndroidTV.pageFragments;

import android.app.Fragment;
import android.support.v17.leanback.app.BrowseFragment;
import android.view.View;

import com.kimeeo.kAndroidTV.core.BackgroundImageHelper;
import com.kimeeo.kAndroidTV.core.IHeaderItem;

/**
 * Created by BhavinPadhiyar on 5/19/17.
 */

public class PageRowFragmentFactory extends BrowseFragment.FragmentFactory {
    private final FragmentProvider fragmentProvider;
    PageRowFragmentFactory(FragmentProvider fragmentProvider) {
        this.fragmentProvider = fragmentProvider;
    }

    @Override
    public Fragment createFragment(Object rowObj) {
        if(rowObj instanceof PageRow)
        {
            IHeaderItem headerData= ((PageRow)rowObj).getHeaderData();
            if(headerData instanceof FragmentProvider) {
                Fragment fragment=((FragmentProvider) rowObj).getFragmentForItem(rowObj);
                if(fragment instanceof BrowseFragment.MainFragmentAdapterProvider )
                    return fragment;
            }
        }
        Fragment fragment=fragmentProvider.getFragmentForItem(rowObj);
        if(fragment instanceof BrowseFragment.MainFragmentAdapterProvider )
            return fragment;
        return null;
    }
}