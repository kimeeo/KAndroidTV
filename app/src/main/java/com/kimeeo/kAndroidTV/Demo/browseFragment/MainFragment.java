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

package com.kimeeo.kAndroidTV.Demo.browseFragment;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v17.leanback.widget.ArrayObjectAdapter;
import android.support.v17.leanback.widget.HeaderItem;
import android.support.v17.leanback.widget.ImageCardView;
import android.support.v17.leanback.widget.ListRow;
import android.support.v17.leanback.widget.ListRowPresenter;
import android.support.v17.leanback.widget.ObjectAdapter;
import android.support.v17.leanback.widget.Presenter;
import android.support.v17.leanback.widget.PresenterSelector;
import android.support.v17.leanback.widget.Row;
import android.support.v17.leanback.widget.RowHeaderPresenter;
import android.support.v17.leanback.widget.RowPresenter;
import android.support.v4.app.ActivityOptionsCompat;
import android.util.Log;

import com.kimeeo.kAndroidTV.Demo.DetailsActivity;
import com.kimeeo.kAndroidTV.Demo.PageActivity;
import com.kimeeo.kAndroidTV.Demo.R;
import com.kimeeo.kAndroidTV.Demo.SearchActivity;
import com.kimeeo.kAndroidTV.Demo.VerticalGridActivity;
import com.kimeeo.kAndroidTV.Demo.dataProviders.Movie;
import com.kimeeo.kAndroidTV.Demo.presenter.*;
import com.kimeeo.kAndroid.dataProvider.DataProvider;
import com.kimeeo.kAndroidTV.Demo.dataProviders.HeaderDataProvider;
import com.kimeeo.kAndroidTV.Demo.verticalGridFragment.VerticalGridFragment;
import com.kimeeo.kAndroidTV.browseFragment.AbstractBrowseFragment;
import com.kimeeo.kAndroidTV.browseFragment.IHeaderItem;
import com.kimeeo.kAndroidTV.headers.IconHeaderItem;
import com.kimeeo.kAndroidTV.presenter.IconHeaderPresenter;

public class MainFragment extends AbstractBrowseFragment {

    /*
    protected RowHeaderPresenter createRowHeaderPresenter() {
        return new IconHeaderPresenter();
    }
    protected  HeaderItem getHeaderItem(int i,IHeaderItem headerItem, String name)
    {
        return new IconHeaderItem(i,name, R.drawable.ic_android_black_24dp);
    }*/


    @NonNull
    @Override
    protected DataProvider createDataProvider() {

        return new HeaderDataProvider();
    }
    protected void onItemClicked(Presenter.ViewHolder itemViewHolder, Object item, RowPresenter.ViewHolder rowViewHolder, Row row) {

        Movie movie = (Movie) item;
        Intent intent = new Intent(getActivity(), PageActivity.class);
        intent.putExtra(DetailsActivity.MOVIE, movie);
        //Bundle bundle = ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(),((ImageCardView) itemViewHolder.view).getMainImageView(),DetailsActivity.SHARED_ELEMENT_NAME).toBundle();
        //getActivity().startActivity(intent, bundle);
        getActivity().startActivity(intent);
    }

    @Override
    protected PresenterSelector getPresenterSelector(IHeaderItem headerItem) {
        if(headerItem.getID().equals("0"))
            return new Row1PresenterSelector();
        return new Row2PresenterSelector();
    }
    protected Row getListRow(IHeaderItem headerItem, HeaderItem header, ArrayObjectAdapter listRowAdapter) {
        return new ListRow(header, listRowAdapter);
    }

    public static class ShodowListRow extends ListRow
    {
        public ShodowListRow(HeaderItem header, ObjectAdapter adapter) {
            super(header, adapter);

        }
    }
    protected PresenterSelector createListRowPresenterSelector() {
        return new ShadowRowPresenterSelector();
    }

    public class ShadowRowPresenterSelector extends PresenterSelector {

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
    protected Class getSearchActivity() {
        return SearchActivity.class;
    }
}
