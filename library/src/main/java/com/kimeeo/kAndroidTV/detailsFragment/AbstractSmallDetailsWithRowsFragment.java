package com.kimeeo.kAndroidTV.detailsFragment;

import android.support.annotation.ColorRes;
import android.support.v17.leanback.app.BackgroundManager;
import android.support.v17.leanback.app.DetailsFragmentBackgroundController;
import android.support.v17.leanback.widget.DetailsOverviewRowPresenter;
import android.support.v17.leanback.widget.Presenter;
import android.support.v17.leanback.widget.RowPresenter;
import android.util.DisplayMetrics;

import com.kimeeo.kAndroidTV.R;
import com.kimeeo.kAndroidTV.core.BackgroundImageHelper;

/**
 * Created by BhavinPadhiyar on 5/24/17.
 */

abstract public class AbstractSmallDetailsWithRowsFragment extends AbstractDetailsWithRowsFragment implements BackgroundImageHelper.OnUpdate {

    private BackgroundImageHelper backgroundImageHelper;
    //private DisplayMetrics mMetrics;
    @Override
    final public void updateBackground(BackgroundManager mBackgroundManager, Object item, int width, int height)
    {

    }

    public void updateDetailsBackground(BackgroundManager mBackgroundManager, Object item, int width, int height)
    {

    }
    @Override
    public BackgroundImageHelper getBackgroundImageHelper()
    {
        backgroundImageHelper = new BackgroundImageHelper(getActivity(),this);
        return backgroundImageHelper;
    }
    @Override
    public boolean supportBackgroundChange(){return true;}

    @Override
    protected RowPresenter createActionDetailedViewPresenter() {
        DetailsOverviewRowPresenter detailsRowPresenter = createDetailsOverviewRowPresenter(createDetailsDescriptionPresenter());
        if(getDetailsBGColorRes()!=-1)
            detailsRowPresenter.setBackgroundColor(getResources().getColor(getDetailsBGColorRes()));
        detailsRowPresenter.setStyleLarge(getStyleLarge());
        return detailsRowPresenter;
    }

    final protected void updateBackground(DetailsFragmentBackgroundController mDetailsBackground, Object data) {
        if(backgroundImageHelper.getBackgroundManager()!=null)
            updateDetailsBackground(backgroundImageHelper.getBackgroundManager(),data,getWindowWidth(),getWindowHeight());
    }

    protected boolean getStyleLarge() {
        return true;
    }

    @ColorRes
    protected int getDetailsBGColorRes() {
        return R.color.fastlane_background;
    }

    protected  DetailsOverviewRowPresenter createDetailsOverviewRowPresenter(Presenter presenter)
    {
        return new DetailsOverviewRowPresenter(presenter);
    }

}
