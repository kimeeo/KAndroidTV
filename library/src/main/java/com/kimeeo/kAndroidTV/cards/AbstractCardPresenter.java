/*
 * Copyright (C) 2015 The Android Open Source Project
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
package com.kimeeo.kAndroidTV.cards;

import android.support.v17.leanback.widget.BaseCardView;
import android.support.v17.leanback.widget.Presenter;
import android.view.ViewGroup;

public abstract class AbstractCardPresenter<T extends BaseCardView> extends Presenter {

    @Override public final ViewHolder onCreateViewHolder(ViewGroup parent) {
        T cardView = onCreateView(parent);
        return new ViewHolder(cardView);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, Object item) {
        if(viewHolder.view instanceof KimeeoBaseCardView)
            ((KimeeoBaseCardView)viewHolder.view).updateItemView(item);
    }

    @Override
    public void onUnbindViewHolder(ViewHolder viewHolder) {
        if(viewHolder.view instanceof KimeeoBaseCardView)
            ((KimeeoBaseCardView)viewHolder.view).onUnbindView();
    }

    protected abstract T onCreateView(ViewGroup parent);
}
