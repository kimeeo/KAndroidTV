package com.kimeeo.kAndroidTV.detailsFragment;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.v17.leanback.app.BackgroundManager;
import android.support.v17.leanback.app.DetailsFragment;
import android.support.v17.leanback.app.DetailsFragmentBackgroundController;
import android.support.v17.leanback.media.MediaPlayerGlue;
import android.support.v17.leanback.widget.Action;
import android.support.v17.leanback.widget.ArrayObjectAdapter;
import android.support.v17.leanback.widget.ClassPresenterSelector;
import android.support.v17.leanback.widget.DetailsOverviewRow;
import android.support.v17.leanback.widget.FullWidthDetailsOverviewRowPresenter;
import android.support.v17.leanback.widget.FullWidthDetailsOverviewSharedElementHelper;
import android.support.v17.leanback.widget.HeaderItem;
import android.support.v17.leanback.widget.ListRow;
import android.support.v17.leanback.widget.ListRowPresenter;
import android.support.v17.leanback.widget.OnItemViewClickedListener;
import android.support.v17.leanback.widget.OnItemViewSelectedListener;
import android.support.v17.leanback.widget.Presenter;
import android.support.v17.leanback.widget.PresenterSelector;
import android.support.v17.leanback.widget.Row;
import android.support.v17.leanback.widget.RowHeaderPresenter;
import android.support.v17.leanback.widget.RowPresenter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.kimeeo.kAndroid.dataProvider.DataProvider;
import com.kimeeo.kAndroid.dataProvider.MonitorList;
import com.kimeeo.kAndroidTV.R;
import com.kimeeo.kAndroidTV.browseFragment.AbstractBrowseFragment;
import com.kimeeo.kAndroidTV.core.AbstractArrayObjectAdapter;
import com.kimeeo.kAndroidTV.core.BackgroundImageHelper;
import com.kimeeo.kAndroidTV.core.DefaultArrayObjectAdapter;
import com.kimeeo.kAndroidTV.core.IHeaderItem;
import com.kimeeo.kAndroidTV.core.RowBasedFragmentHelper;
import com.kimeeo.kAndroidTV.core.WatcherArrayObjectAdapter;
import com.kimeeo.kAndroidTV.demoModel.Movie;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by BhavinPadhiyar on 5/19/17.
 */

abstract public class AbstractDetailsFragment extends DetailsFragment implements RowBasedFragmentHelper.HelperProvider
{

    @Override
    public boolean supportAutoPageLoader() {return true;}
    public boolean getSupportRowProgressBar()
    {
        return false;
    }

    final public BackgroundImageHelper getBackgroundImageHelper(){return null;}
    final public boolean supportBackgroundChange(){return false;}


    private List<Action> actionlist;
    private ArrayObjectAdapter actionAdapter;
    private MediaPlayerGlue mMediaPlayerGlue;
    public Object getData() {
        return data;
    }

    private Object data;

    protected ArrayObjectAdapter createActionArrayObjectAdapter() {
        return new DefaultArrayObjectAdapter();
    }
    protected  List<Action> createActionlist()
    {
        return null;
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        if (null != fragmentHelper) {
            fragmentHelper.onDestroy();
        }
    }
    protected final DetailsFragmentBackgroundController mDetailsBackground = new DetailsFragmentBackgroundController(this);

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupUi();
        setupUIElements();
    }
    protected RowBasedFragmentHelper createBrowseFragmentHelper() {
        return new MyRowBasedFragmentHelper(this,this);
    }
    public class MyRowBasedFragmentHelper extends RowBasedFragmentHelper
    {
        public MyRowBasedFragmentHelper(Fragment host, HelperProvider helperProvider) {
            super(host, helperProvider);
        }
        @Override
        public void itemsAdded(int index, List list) {
            super.itemsAdded(index,list);
            initializeBackground(mDetailsBackground,data);
        }
    }
    abstract protected @NonNull DataProvider createDataProvider();
    protected DataProvider dataProvider;
    public DataProvider getDataProvider()
    {
        return dataProvider;
    }
    protected void configDataManager(DataProvider dataProvider) {}
    RowBasedFragmentHelper fragmentHelper;

    final public PresenterSelector createMainRowPresenterSelector()
    {
        ClassPresenterSelector rowPresenterSelector = new ClassPresenterSelector();
        rowPresenterSelector.addClassPresenter(getDetailsOverviewRowClass(), rowPresenter);

        HashMap<Class<?>, Object> mClassMap = getClassPresenterMap();
        if(mClassMap!=null) {
            for (Map.Entry<Class<?>, Object> entry : mClassMap.entrySet()) {
                Class key = entry.getKey();
                Object value = entry.getValue();
                if(value instanceof Presenter)
                    rowPresenterSelector.addClassPresenter(key,(Presenter)value);
                else if(value instanceof PresenterSelector)
                    rowPresenterSelector.addClassPresenterSelector(key,(PresenterSelector)value);
            }
        }
        return rowPresenterSelector;
    }
    public AbstractArrayObjectAdapter createMainArrayObjectAdapter(PresenterSelector presenter)
    {
        return new DefaultArrayObjectAdapter(presenter);
    }

    final public Presenter createMainRowPresenter(){return null;}
    final public AbstractArrayObjectAdapter createMainArrayObjectAdapter(Presenter presenter){return null;}

    public HeaderItem getHeaderItem(int i,IHeaderItem headerItem, String name)
    {
        return new HeaderItem(i,name);
    }

    public FullWidthDetailsOverviewRowPresenter getRowPresenter() {
        return rowPresenter;
    }

    FullWidthDetailsOverviewRowPresenter rowPresenter;

    private void setupUi() {

        data =createDetailsData();
        rowPresenter = createActionDetailedViewPresenter();
        DetailsOverviewRow detailsOverview = createDetailsOverviewRow();

        setImage(detailsOverview,data);

        actionlist=createActionlist();
        if(actionlist!=null && actionlist.size()!=0)
        {
            actionAdapter = createActionArrayObjectAdapter();
            actionAdapter.addAll(0,actionlist);
            detailsOverview.setActionsAdapter(actionAdapter);
        }


        fragmentHelper = createBrowseFragmentHelper();
        dataProvider=createDataProvider();
        configDataManager(dataProvider);
        fragmentHelper.build();
        fragmentHelper.next();

        fragmentHelper.getRowsAdapter().add(0,detailsOverview);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startEntranceTransition();
            }
        }, 500);
        initializeBackground(mDetailsBackground,data);
    }

    protected void setImage(DetailsOverviewRow detailsOverview, Object data) {

    }

    public ArrayObjectAdapter getRowArrayObjectAdapter(IHeaderItem headerItem,PresenterSelector presenterSelector) {
        return new WatcherArrayObjectAdapter(presenterSelector);
    }
    protected abstract HashMap<Class<?>,Object> getClassPresenterMap();

    protected Class<?> getDetailsOverviewRowClass() {
        return DetailsOverviewRow.class;
    }


    protected FullWidthDetailsOverviewRowPresenter createActionDetailedViewPresenter() {
        FullWidthDetailsOverviewRowPresenter detailsRowPresenter = createDetailsRowPresenter(createDetailsDescriptionPresenter());
        FullWidthDetailsOverviewSharedElementHelper mHelper = createDtailsSharedElementHelper();
        if(getTransitionName()!=null)
            mHelper.setSharedElementEnterTransition(getActivity(), getTransitionName());
        else
            mHelper.setSharedElementEnterTransition(getActivity(), "name");

        detailsRowPresenter.setListener(mHelper);
        detailsRowPresenter.setParticipatingEntranceTransition(false);
        prepareEntranceTransition();

        return detailsRowPresenter;
    }
    protected DetailsOverviewRow createDetailsOverviewRow()
    {
        return new DetailsOverviewRow(data);
    }
    protected abstract Object createDetailsData();
    protected String getTransitionName() {
        return null;
    }

    protected FullWidthDetailsOverviewSharedElementHelper createDtailsSharedElementHelper() {
        return new FullWidthDetailsOverviewSharedElementHelper();
    }

    abstract protected FullWidthDetailsOverviewRowPresenter createDetailsRowPresenter(Presenter detailsDescriptionPresenter);
    abstract protected Presenter createDetailsDescriptionPresenter();

    abstract protected Presenter getCardPresenterSelector();
    public ArrayObjectAdapter getRowArrayObjectAdapter(IHeaderItem headerItem,Presenter presenter) {
        return new ArrayObjectAdapter(presenter);
    }
    protected void initializeBackground(DetailsFragmentBackgroundController mDetailsBackground,Object data) {

    }
    public Class getSearchActivity() {
        return null;
    }
    @Override
    public String getFirstTimeLoaderMessage() {
        return getActivity().getString(R.string._busy_message);
    }
    @Override
    public void onItemClicked(Presenter.ViewHolder itemViewHolder, Object item,RowPresenter.ViewHolder rowViewHolder, Row row) {

    }

    @Override
    public void onItemSelected(Presenter.ViewHolder itemViewHolder, Object item,RowPresenter.ViewHolder rowViewHolder, Row row) {

    }


    private void setupUIElements() {
        if(getSearchAffordanceColorRes()!=-1)
            setSearchAffordanceColor(getResources().getColor(getSearchAffordanceColorRes()));
        else if(getSearchAffordanceColorValue()!=-1)
            setSearchAffordanceColor(getSearchAffordanceColorValue());

    }
    protected int getSearchAffordanceColorValue()
    {
        return -1;
    }
    @ColorRes
    protected int getSearchAffordanceColorRes()
    {
        return R.color.fastlane_background;
    }

}
