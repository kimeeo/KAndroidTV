package com.kimeeo.kAndroidTV.cards;

import android.support.v17.leanback.widget.BaseCardView;
import android.support.v17.leanback.widget.ImageCardView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.kimeeo.kAndroidTV.R;
import com.kimeeo.kAndroidTV.core.*;

/**
 * Created by BhavinPadhiyar on 5/17/17.
 */

abstract public class CustomViewCardPresenter extends AbstractCardPresenter {
    protected BaseCardView onCreateView(ViewGroup parent) {
        BaseCardView cardView;
        if(getStyleRes()!=-1)
            cardView= new BaseCardView(parent.getContext(), null, getStyleRes());
        else
            cardView= new BaseCardView(parent.getContext(), null);

        cardView.setFocusable(getFocusable());
        cardView.addView(LayoutInflater.from(parent.getContext()).inflate(getLayoutRes(), null));
        return cardView;
    };

    abstract public int getLayoutRes();

    public int getStyleRes() {
        return -1;
    }
    public boolean getFocusable() {
        return true;
    }
}