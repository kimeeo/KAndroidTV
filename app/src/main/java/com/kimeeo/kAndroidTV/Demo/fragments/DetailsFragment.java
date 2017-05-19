package com.kimeeo.kAndroidTV.Demo.fragments;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v17.leanback.widget.Action;
import android.support.v17.leanback.widget.DetailsOverviewLogoPresenter;
import android.support.v17.leanback.widget.FullWidthDetailsOverviewRowPresenter;
import android.support.v17.leanback.widget.Presenter;
import android.support.v17.leanback.widget.PresenterSelector;
import android.support.v17.leanback.widget.RowPresenter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kimeeo.kAndroid.dataProvider.DataProvider;
import com.kimeeo.kAndroidTV.Demo.R;
import com.kimeeo.kAndroidTV.Demo.dataProviders.HeaderDataProvider;
import com.kimeeo.kAndroidTV.Demo.dataProviders.Movie;
import com.kimeeo.kAndroidTV.Demo.presenter.Row1PresenterSelector;
import com.kimeeo.kAndroidTV.Demo.presenter.Row2PresenterSelector;
import com.kimeeo.kAndroidTV.core.IHeaderItem;
import com.kimeeo.kAndroidTV.detailsFragment.AbstractDetailsFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by BhavinPadhiyar on 5/18/17.
 */

public class DetailsFragment extends AbstractDetailsFragment {
    @NonNull
    @Override
    protected DataProvider createDataProvider() {
        return new HeaderDataProvider();
    }

    protected List<Action> createActionlist()
    {
        List<Action> list =new ArrayList<>();
        list.add(new Action(1, "Buy"));
        list.add(new Action(2,"Wish"));
        list.add(new Action(3, "related"));
        return list;
    }
    protected Object createDetailsData()
    {
        Movie m = new Movie();
        m.setTitle("Item:");
        m.setStudio("Youtunbe");
        return m;
    }

    @Override
    protected FullWidthDetailsOverviewRowPresenter createDetailsRowPresenter(Presenter detailsDescriptionPresenter) {
        return new MyFullWidthDetailsOverviewRowPresenter(detailsDescriptionPresenter);
    }

    protected PresenterSelector createListRowPresenterSelector() {
        return new BrowseFragment.ShadowRowPresenterSelector();
    }

    @Override
    protected Presenter createDetailsDescriptionPresenter() {
        return new DetailsDescriptionPresenter(getActivity());
    }
    @Override
    protected PresenterSelector getPresenterSelector(IHeaderItem headerItem) {
        if (headerItem.getID().equals("0"))
            return new Row1PresenterSelector();
        return new Row2PresenterSelector();
    }

    public class MyFullWidthDetailsOverviewRowPresenter extends FullWidthDetailsOverviewRowPresenter
    {
        public MyFullWidthDetailsOverviewRowPresenter(Presenter detailsPresenter) {
            super(detailsPresenter);
        }
        public MyFullWidthDetailsOverviewRowPresenter(Presenter detailsPresenter,DetailsOverviewLogoPresenter logoPresenter) {
            super(detailsPresenter,logoPresenter);
        }
        @Override
        protected RowPresenter.ViewHolder createRowViewHolder(ViewGroup parent) {
            RowPresenter.ViewHolder viewHolder = super.createRowViewHolder(parent);
            View actionsView = viewHolder.view.findViewById(R.id.details_overview_actions_background);
            //actionsView.setBackgroundColor(getResources().getColor(R.color.fastlane_background));
            View detailsView = viewHolder.view.findViewById(R.id.details_frame);
            return viewHolder;
        }
    }
    public class DetailsDescriptionPresenter extends Presenter {
        private Context mContext;

        public DetailsDescriptionPresenter(Context context) {
            mContext = context;
        }



        @Override public ViewHolder onCreateViewHolder(ViewGroup parent) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.detail_view_content, null);
            return new ViewHolder(view);
        }

        @Override public void onBindViewHolder(ViewHolder viewHolder, Object item) {

        }

        @Override public void onUnbindViewHolder(ViewHolder viewHolder) {
            // Nothing to do here.
        }
    }
}
