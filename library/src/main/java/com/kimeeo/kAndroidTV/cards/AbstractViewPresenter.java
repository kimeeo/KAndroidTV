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

import android.content.Context;
import android.support.v17.leanback.widget.Presenter;
import android.view.View;
import android.view.ViewGroup;

public abstract class AbstractViewPresenter<T extends View> extends Presenter {

    private Context mContext;
    public AbstractViewPresenter(Context context) {
        super();
        mContext = context;
    }
    public AbstractViewPresenter() {
        super();
    }


    public Context getContext() {
        return mContext;
    }

    @Override public final ViewHolder onCreateViewHolder(ViewGroup parent) {
        final T cardView = onCreateView(parent);
        cardView.setFocusable(getFocusable());
        if(supportFocusChange()) {
            cardView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    animateOnFocus((T)v, hasFocus);
                }

            });
        }

        return new ViewHolder(cardView);
    }

    protected boolean getFocusable() {
        return false;
    }

    protected boolean supportFocusChange() {
        return false;
    }

    protected void animateOnFocus(T background, boolean hasFocus) {

    }


    @Override
    public void onBindViewHolder(ViewHolder viewHolder, Object item) {
        if(viewHolder.view instanceof IViewItem)
            ((IViewItem)viewHolder.view).updateItemView(viewHolder.view,item);

        updateView((T)viewHolder.view,item);
    }

    protected void updateView(T view, Object item) {

    }

    @Override
    public void onUnbindViewHolder(ViewHolder viewHolder) {
        if(viewHolder.view instanceof IViewItem)
            ((IViewItem)viewHolder.view).onUnbindView(viewHolder.view);
    }

    protected abstract T onCreateView(ViewGroup parent);
}
