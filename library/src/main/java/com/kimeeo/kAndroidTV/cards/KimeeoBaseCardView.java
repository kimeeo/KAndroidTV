package com.kimeeo.kAndroidTV.cards;

import android.content.Context;
import android.support.v17.leanback.widget.BaseCardView;
import android.support.v17.leanback.widget.Presenter;
import android.util.AttributeSet;

/**
 * Created by BhavinPadhiyar on 5/17/17.
 */

public class KimeeoBaseCardView extends BaseCardView {
    public KimeeoBaseCardView(Context context) {
        super(context);
    }

    public KimeeoBaseCardView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public KimeeoBaseCardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void updateItemView(Object item) {

    }

    public void onUnbindView() {

    }
}
