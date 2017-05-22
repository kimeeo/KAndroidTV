package com.kimeeo.kAndroidTV.cards;

import android.view.View;

/**
 * Created by BhavinPadhiyar on 5/22/17.
 */

public interface IViewItem {
    void updateItemView(View view,Object item);
    void onUnbindView(View view);
}
