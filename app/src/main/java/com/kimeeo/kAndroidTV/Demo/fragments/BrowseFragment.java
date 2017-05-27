/*
 * Copyright (C) 2014 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package com.kimeeo.kAndroidTV.Demo.fragments;


import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v17.leanback.app.BackgroundManager;
import android.support.v17.leanback.widget.ArrayObjectAdapter;
import android.support.v17.leanback.widget.HeaderItem;
import android.support.v17.leanback.widget.ListRow;
import android.support.v17.leanback.widget.ListRowPresenter;
import android.support.v17.leanback.widget.ObjectAdapter;
import android.support.v17.leanback.widget.Presenter;
import android.support.v17.leanback.widget.PresenterSelector;
import android.support.v17.leanback.widget.Row;
import android.support.v17.leanback.widget.RowPresenter;

import com.kimeeo.kAndroidTV.Demo.DailymotionActivityRow;
import com.kimeeo.kAndroidTV.Demo.DetailsActivity;
import com.kimeeo.kAndroidTV.Demo.R;
import com.kimeeo.kAndroidTV.Demo.SearchActivity;
import com.kimeeo.kAndroidTV.Demo.dataProviders.Movie;
import com.kimeeo.kAndroidTV.Demo.presenter.*;
import com.kimeeo.kAndroid.dataProvider.DataProvider;
import com.kimeeo.kAndroidTV.Demo.dataProviders.HeaderDataProvider;
import com.kimeeo.kAndroidTV.browseFragment.AbstractBrowseFragment;
import com.kimeeo.kAndroidTV.core.IHeaderItem;
import com.kimeeo.kAndroidTV.onboardingFragment.OnBoardActivity;

public class BrowseFragment extends AbstractBrowseFragment {
    public boolean getSupportRowProgressBar()
    {
        return true;
    }



    /*
    protected RowHeaderPresenter createRowHeaderPresenter() {
        return new IconHeaderPresenter();
    }
    protected  HeaderItem getHeaderItem(int i,IHeaderItem headerItem, String name)
    {
        return new IconHeaderItem(i,name, R.drawable.ic_android_black_24dp);
    }*/
    @Override
    public boolean supportBackgroundChange() {
        return true;
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

    @NonNull
    @Override
    public DataProvider createDataProvider() {
        return new HeaderDataProvider();
    }
    public void onItemClicked(Presenter.ViewHolder itemViewHolder, Object item, RowPresenter.ViewHolder rowViewHolder, Row row) {

        Movie movie = (Movie) item;
        Intent intent = new Intent(getActivity(), DailymotionActivityRow.class);
        intent.putExtra(DetailsActivity.MOVIE, movie);
        getActivity().startActivity(intent);

        /*
        OnBoardActivity.Builder builder=new OnBoardActivity.Builder(getActivity());
        builder.addPage("My Title 1","My descriptions 1",R.drawable.image);
        builder.addPage("My Title 2","My descriptions 2",R.drawable.image);
        builder.addPage("My Title 3","My descriptions 3",R.drawable.image);
        builder.addPage("My Title 4","My descriptions 4",R.drawable.image);
        builder.addPage("My Title 5","My descriptions 5",R.drawable.image);
        builder.setLogoRes(R.drawable.background_canyon);
        builder.startButtonLabel("Open APP");
        builder.backgroundColorRes(R.color.fastlane_background);
        builder.show();
        builder.forceShow();
        */
    }

    @Override
    public PresenterSelector getRowItemPresenterSelector(IHeaderItem headerItem) {
        if(headerItem.getID().equals("0"))
            return new Row1PresenterSelector();
        return new Row2PresenterSelector();
    }
    public Row getListRow(IHeaderItem headerItem, HeaderItem header, ArrayObjectAdapter listRowAdapter) {
        return new ListRow(header, listRowAdapter);
    }

    public static class ShodowListRow extends ListRow
    {
        public ShodowListRow(HeaderItem header, ObjectAdapter adapter) {
            super(header, adapter);

        }
    }
    public PresenterSelector createMainRowPresenterSelector() {
        return new ShadowRowPresenterSelector();
    }

    public static class ShadowRowPresenterSelector extends PresenterSelector {

        private ListRowPresenter mShadowEnabledRowPresenter = new ListRowPresenter();
        private ListRowPresenter mShadowDisabledRowPresenter = new ListRowPresenter();

        public ShadowRowPresenterSelector() {
            mShadowEnabledRowPresenter.setNumRows(1);
            mShadowDisabledRowPresenter.setShadowEnabled(false);
        }

        @Override public Presenter getPresenter(Object item) {
            if (item instanceof ShodowListRow)
                return mShadowDisabledRowPresenter;
            else
                return mShadowEnabledRowPresenter;
        }

        @Override
        public Presenter[] getPresenters() {
            return new Presenter [] {
                    mShadowDisabledRowPresenter,
                    mShadowEnabledRowPresenter
            };
        }
    }

    public Class getSearchActivity() {
        return SearchActivity.class;
    }


}
