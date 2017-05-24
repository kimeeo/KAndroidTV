package com.kimeeo.kAndroidTV.Demo.presenter;

import android.support.v17.leanback.widget.Presenter;
import android.support.v17.leanback.widget.PresenterSelector;

import com.kimeeo.kAndroidTV.Demo.R;
import com.kimeeo.kAndroidTV.Demo.dataProviders.Movie;
import com.kimeeo.kAndroidTV.core.ProgressCardVO;

import java.util.HashMap;

/**
 * Created by BhavinPadhiyar on 5/17/17.
 */

public class Row2PresenterSelector extends PresenterSelector {
    private final HashMap<Integer, Presenter> presenters = new HashMap<Integer, Presenter>();
    @Override
    public Presenter getPresenter(Object item) {
        Presenter presenter;
        if(item instanceof ProgressCardVO)
        {

            presenter = presenters.get(5000);
            if (presenter == null) {
                presenter = new ProgressViewPresenter();
            }
            presenters.put(5000, presenter);
        }
        else {
            Movie m = (Movie) item;
            presenter = presenters.get(m.getType());
            if (presenter == null) {
                if (m.getType() == 1)
                    presenter = new CardPresenter();
                else
                    presenter = new CardPresenter(R.color.fastlane_background);
            }
            presenters.put(m.getType(), presenter);
        }
        return presenter;
    }
}
