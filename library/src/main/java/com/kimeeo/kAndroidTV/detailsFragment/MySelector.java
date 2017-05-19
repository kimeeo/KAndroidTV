package com.kimeeo.kAndroidTV.detailsFragment;

import android.support.v17.leanback.widget.ClassPresenterSelector;
import android.support.v17.leanback.widget.Presenter;
import android.support.v17.leanback.widget.PresenterSelector;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by BhavinPadhiyar on 5/19/17.
 */

public class MySelector extends PresenterSelector {

        private final ArrayList<Presenter> mPresenters = new ArrayList<Presenter>();

        private final HashMap<Class<?>, Object> mClassMap = new HashMap<Class<?>, Object>();
        public MySelector addClassPresenter(Class<?> cls, Presenter presenter) {
            mClassMap.put(cls, presenter);
            if (!mPresenters.contains(presenter)) {
                mPresenters.add(presenter);
            }
            return this;
        }

        public MySelector addClassPresenterSelector(Class<?> cls,PresenterSelector presenterSelector) {
            mClassMap.put(cls, presenterSelector);
            Presenter[] innerPresenters = presenterSelector.getPresenters();
            for (int i = 0; i < innerPresenters.length; i++)
                if (!mPresenters.contains(innerPresenters[i])) {
                    mPresenters.add(innerPresenters[i]);
                }
            return this;
        }

        @Override
        public Presenter getPresenter(Object item) {
            Class<?> cls = item.getClass();
            Object presenter = null;

            do {
                presenter = mClassMap.get(cls);
                if (presenter instanceof PresenterSelector) {
                    Presenter innerPresenter = ((PresenterSelector) presenter).getPresenter(item);
                    if (innerPresenter != null) {
                        return innerPresenter;
                    }
                }
                cls = cls.getSuperclass();
            } while (presenter == null && cls != null);

            return (Presenter) presenter;
        }

        @Override
        public Presenter[] getPresenters() {
            return mPresenters.toArray(new Presenter[mPresenters.size()]);
        }
    }
