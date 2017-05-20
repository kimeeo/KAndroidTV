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

package com.kimeeo.kAndroidTV.Demo.presenter;

import android.content.Context;
import android.support.v17.leanback.widget.BaseCardView;
import android.support.v17.leanback.widget.ImageCardView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.kimeeo.kAndroidTV.Demo.R;
import com.kimeeo.kAndroidTV.Demo.dataProviders.Movie;
import com.kimeeo.kAndroidTV.cards.AbstractCardPresenter;
import com.kimeeo.kAndroidTV.cards.KimeeoBaseCardView;


/*
 * A CardPresenter is used to generate Views and bind Objects to them on demand.
 * It contains an Image CardView
 */
public class ProgressPresenter extends AbstractCardPresenter {
    @Override
    protected BaseCardView onCreateView(ViewGroup parent) {
        return new ProgressPresenter.TextCardView(parent.getContext());
    }

    public class TextCardView extends KimeeoBaseCardView {

        public TextCardView(Context context) {
            super(context);
            LayoutInflater.from(getContext()).inflate(R.layout.text_line_progress_card, this);
            setFocusable(false);
        }

        public void updateItemView(Object data) {

        }

    }
}
