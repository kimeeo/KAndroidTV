package com.kimeeo.kAndroidTV.Demo.presenter;

import android.content.Context;
import android.support.v17.leanback.widget.ControlButtonPresenterSelector;
import android.support.v17.leanback.widget.Presenter;
import android.support.v17.leanback.widget.PresenterSelector;

import com.kimeeo.kAndroidTV.Demo.R;
import com.kimeeo.kAndroidTV.Demo.dataProviders.Movie;

import java.util.HashMap;

/**
 * Created by BhavinPadhiyar on 5/17/17.
 */

public class Row1PresenterSelector extends PresenterSelector {

    private final HashMap<Integer, Presenter> presenters = new HashMap<Integer, Presenter>();
    @Override
    public Presenter getPresenter(Object item) {
        Movie m = (Movie) item;
        Presenter presenter = presenters.get(m.getType());
        if (presenter == null) {
            if(m.getType()==1)
                presenter = new CardPresenter();
            else
                presenter = new CardPresenter(R.color.fastlane_background);
        }
        presenters.put(m.getType(), presenter);
        return presenter;
    }

}
