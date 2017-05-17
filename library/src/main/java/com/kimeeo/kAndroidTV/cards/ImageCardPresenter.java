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

import android.animation.ObjectAnimator;
import android.graphics.drawable.Drawable;
import android.support.v17.leanback.widget.BaseCardView;
import android.support.v17.leanback.widget.ImageCardView;
import android.support.v17.leanback.widget.Presenter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public abstract class ImageCardPresenter extends AbstractCardPresenter {

    private static final int ANIMATION_DURATION = 200;

    private void animateIconBackground(Drawable drawable, boolean hasFocus) {
        if (hasFocus) {
            ObjectAnimator.ofInt(drawable, "alpha", 0, 255).setDuration(ANIMATION_DURATION).start();
        } else {
            ObjectAnimator.ofInt(drawable, "alpha", 255, 0).setDuration(ANIMATION_DURATION).start();
        }
    }
    protected ImageCardView onCreateView(ViewGroup parent)
    {
        ImageCardView cardView = new ImageCardView(parent.getContext());
        final ImageView image = cardView.getMainImageView();
        image.getBackground().setAlpha(0);
        cardView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                animateIconBackground(image.getBackground(), hasFocus);
            }
        });

        return cardView;
    }
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, Object item) {
        ImageCardView cardView = (ImageCardView)viewHolder.view;
        cardView.setTag(item);
        cardView.setTitleText(getTitle(item));
        cardView.setContentText(getDescription(item));
        loadImage(cardView.getMainImageView(),item);
    }



    protected CharSequence getDescription(Object item) {
        return "";
    }

    protected CharSequence getTitle(Object item) {
        return "";
    }
    abstract void loadImage(ImageView mainImageView, Object item);
}
