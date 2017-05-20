package com.kimeeo.kAndroidTV.core;

import android.content.Context;
import android.support.v17.leanback.widget.BaseCardView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.kimeeo.kAndroidTV.R;
import com.kimeeo.kAndroidTV.cards.*;

/**
 * Created by BhavinPadhiyar on 5/21/17.
 */

public class ProgressPresenter  extends com.kimeeo.kAndroidTV.cards.AbstractCardPresenter {
    @Override
    protected BaseCardView onCreateView(ViewGroup parent) {
        return new ProgressCardView(parent.getContext());
    }

    public class ProgressCardView extends KimeeoBaseCardView {

        public ProgressCardView(Context context) {
            super(context);
            LayoutInflater.from(getContext()).inflate(R.layout.progress_card, this);
            setFocusable(false);
        }

        public void updateItemView(Object data) {


        }
    }
}
