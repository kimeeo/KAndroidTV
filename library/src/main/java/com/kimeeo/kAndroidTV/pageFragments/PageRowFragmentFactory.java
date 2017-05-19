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
    private final BackgroundImageHelper backgroundImageHelper;
    private final FragmentProvider fragmentProvider;
    PageRowFragmentFactory(BackgroundImageHelper backgroundImageHelper, FragmentProvider fragmentProvider) {
        this.backgroundImageHelper = backgroundImageHelper;
        this.fragmentProvider = fragmentProvider;
    }

    @Override
    public Fragment createFragment(Object rowObj) {
        if(backgroundImageHelper!=null)
            backgroundImageHelper.setDrawable(null);
        if(rowObj instanceof PageRow)
        {
            IHeaderItem headerData= ((PageRow)rowObj).getHeaderData();
            if(headerData instanceof FragmentProvider)
                ((FragmentProvider)rowObj).getFragmentForItem(rowObj);
        }

        return fragmentProvider.getFragmentForItem(rowObj);
    }
}
