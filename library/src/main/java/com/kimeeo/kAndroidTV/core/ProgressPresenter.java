package com.kimeeo.kAndroidTV.core;

import android.content.Context;
import android.support.v17.leanback.widget.BaseCardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kimeeo.kAndroidTV.R;
import com.kimeeo.kAndroidTV.cards.*;

/**
 * Created by BhavinPadhiyar on 5/21/17.
 */
public class ProgressPresenter extends CustomViewCardPresenter {

    @Override
    public void updateItemView(View view, Object data) {

    }
    @Override
    final public boolean getFocusable() {return false;}

    @Override
    public int getLayoutRes() {
        return R.layout.progress_card;
    }

}
