package com.kimeeo.kAndroidTV.presenter;

import android.support.v17.leanback.widget.BaseCardView;
import android.view.ViewGroup;

import com.kimeeo.kAndroidTV.cards.AbstractCardPresenter;
import com.kimeeo.kAndroidTV.widget.LoadingCardView;

/**
 * Created by BhavinPadhiyar on 5/21/17.
 */
public class ProgressPresenter extends AbstractCardPresenter {

    @Override
    final public boolean getFocusable() {return false;}

    protected BaseCardView onCreateView(ViewGroup parent) {
        BaseCardView cardView= new LoadingCardView(parent.getContext());

        //cardView.addView(LayoutInflater.from(parent.getContext()).inflate(getLayoutRes(), null));
        return cardView;
    };

}
