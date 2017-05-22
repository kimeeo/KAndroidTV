package com.kimeeo.kAndroidTV.cards;

import android.support.annotation.StyleRes;
import android.support.v17.leanback.widget.BaseCardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by BhavinPadhiyar on 5/17/17.
 */

abstract public class CustomViewCardPresenter extends AbstractCardPresenter implements IViewItem {
    protected BaseCardView onCreateView(ViewGroup parent) {
        BaseCardView cardView;
        if(getStyleRes()!=-1)
            cardView= new BaseCardView(parent.getContext(), null, getStyleRes());
        else
            cardView= new BaseCardView(parent.getContext(), null);
        cardView.addView(LayoutInflater.from(parent.getContext()).inflate(getLayoutRes(), null));
        return cardView;
    };

    abstract public int getLayoutRes();

    @StyleRes
    public int getStyleRes() {
        return -1;
    }

    @Override
    public void onUnbindView(View view) {

    }
}