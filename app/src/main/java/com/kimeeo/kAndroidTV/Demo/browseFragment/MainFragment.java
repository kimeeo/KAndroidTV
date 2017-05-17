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


import android.support.annotation.NonNull;
import android.support.v17.leanback.widget.Presenter;
import com.kimeeo.kAndroidTV.Demo.presenter.*;
import com.kimeeo.kAndroid.dataProvider.DataProvider;
import com.kimeeo.kAndroidTV.Demo.dataProviders.HeaderDataProvider;
import com.kimeeo.kAndroidTV.browseFragment.AbstractBrowseFragment;
import com.kimeeo.kAndroidTV.browseFragment.IHeaderItem;
public class MainFragment extends AbstractBrowseFragment {

    @NonNull
    @Override
    protected DataProvider createDataProvider() {
        return new HeaderDataProvider();
    }

    protected Presenter getPresenter(IHeaderItem headerItem) {
        if(headerItem.getID().equals("1"))
            return new TextCardPresenter();
        else
            return new CardPresenter();
    }
}
