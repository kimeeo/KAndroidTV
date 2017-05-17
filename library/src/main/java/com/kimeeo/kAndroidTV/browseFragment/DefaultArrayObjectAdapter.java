package com.kimeeo.kAndroidTV.browseFragment;

import android.support.v17.leanback.widget.Presenter;
import android.support.v17.leanback.widget.PresenterSelector;

/**
 * Created by BhavinPadhiyar on 5/17/17.
 */

public class DefaultArrayObjectAdapter extends AbstractArrayObjectAdapter {
    public DefaultArrayObjectAdapter(PresenterSelector presenterSelector) {
        super(presenterSelector);
    }

    public DefaultArrayObjectAdapter(Presenter presenter) {
        super(presenter);
    }
}
