package com.kimeeo.kAndroidTV.verticalGridFragment;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.v17.leanback.app.BackgroundManager;
import android.support.v17.leanback.app.BrowseFragment;
import android.support.v17.leanback.app.VerticalGridFragment;
import android.support.v17.leanback.widget.ArrayObjectAdapter;
import android.support.v17.leanback.widget.FocusHighlight;
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
import android.support.v17.leanback.widget.VerticalGridPresenter;
import android.view.View;

import com.kimeeo.kAndroid.dataProvider.DataProvider;
import com.kimeeo.kAndroid.dataProvider.MonitorList;
import com.kimeeo.kAndroidTV.R;
import com.kimeeo.kAndroidTV.core.AbstractArrayObjectAdapter;
import com.kimeeo.kAndroidTV.core.BackgroundImageHelper;
import com.kimeeo.kAndroidTV.core.DefaultArrayObjectAdapter;
import com.kimeeo.kAndroidTV.core.IHeaderItem;
import com.kimeeo.kAndroidTV.core.ProgressCardVO;
import com.kimeeo.kAndroidTV.core.RowBasedFragmentHelper;
import com.kimeeo.kAndroidTV.core.WatcherArrayObjectAdapter;

import java.util.List;

/**
 * Created by BhavinPadhiyar on 5/18/17.
 */

abstract public class AbstractVerticalGridFragment extends VerticalGridFragment implements RowBasedFragmentHelper.HelperProvider, BackgroundImageHelper.OnUpdate{

    @Override
    public boolean supportAutoPageLoader() {return true;}
    public boolean getSupportRowProgressBar()
    {
        return false;
    }

    public boolean supportBackgroundChange() {
        return false;
    }

    public void updateBackground(BackgroundManager mBackgroundManager, Object item, int width, int height)
    {

    }
    @Override
    final public PresenterSelector getRowItemPresenterSelector(IHeaderItem headerItem) {
        return null;
    }


    abstract protected @NonNull DataProvider createDataProvider();
    protected DataProvider dataProvider;
    protected void configDataManager(DataProvider dataProvider) {}
    public DataProvider getDataProvider()
    {
        return dataProvider;
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        if (null != fragmentHelper) {
            fragmentHelper.onDestroy();
        }
    }

    public RowBasedFragmentHelper getFragmentHelper() {
        return fragmentHelper;
    }

    RowBasedFragmentHelper fragmentHelper;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupUIElements();
        VerticalGridPresenter gridPresenter = createVerticalGridPresenter(getZoomFactor(),getNumberOfColumns());
        setGridPresenter(gridPresenter);

        fragmentHelper = createBrowseFragmentHelper();
        dataProvider=createDataProvider();
        configDataManager(dataProvider);
        fragmentHelper.build();
        fragmentHelper.next();

    }

    protected int getNumberOfColumns() {
        return 4;
    }

    protected int getZoomFactor() {
        return FocusHighlight.ZOOM_FACTOR_MEDIUM;
    }

    protected VerticalGridPresenter createVerticalGridPresenter(int zoom,int NumberOfColumns) {
        VerticalGridPresenter verticalGridPresenter= new VerticalGridPresenter(zoom);
        verticalGridPresenter.setNumberOfColumns(NumberOfColumns);
        return verticalGridPresenter;
    }

    protected RowBasedFragmentHelper createBrowseFragmentHelper() {
        return new VerticalGridRowBasedFragmentHelper(this,this);
    }
    public  class VerticalGridRowBasedFragmentHelper extends com.kimeeo.kAndroidTV.core.RowBasedFragmentHelper
    {
        public VerticalGridRowBasedFragmentHelper(Fragment host, HelperProvider helperProvider) {
            super(host, helperProvider);

        }
        /*
        public void build() {
            super.build();

            if(getHelperProvider().getSupportRowProgressBar() && mRowsAdapter instanceof WatcherArrayObjectAdapter)
            {
                //((WatcherArrayObjectAdapter)mRowsAdapter).getDataProvider().addFatchingObserve((WatcherArrayObjectAdapter)mRowsAdapter);
                ((WatcherArrayObjectAdapter)mRowsAdapter).setSupportRowProgressBar(getHelperProvider().getSupportRowProgressBar());
            }
        }
        */
        public void onFetchingStart(boolean b) {
            super.onFetchingStart(b);
            if(dataProvider.size()!=0 && getHelperProvider().getSupportRowProgressBar())
                dataProvider.add(new ProgressCardVO());
        }
        public void onFetchingFinish(boolean b) {
            super.onFetchingFinish(b);
            if(dataProvider.size()!=0 && getHelperProvider().getSupportRowProgressBar()) {
                if (dataProvider.get(dataProvider.size() - 1) instanceof ProgressCardVO)
                    dataProvider.remove(dataProvider.size() - 1);
            }
        }
        @Override
        public void itemsAdded(int index, List list) {
            getRowsAdapter().addAll(index,list);
        }
        @Override
        protected void handelPaging(Presenter.ViewHolder itemViewHolder, Object item,RowPresenter.ViewHolder rowViewHolder, Row row) {
            DataProvider dataProvider = getDataProvider();
            if(dataProvider!=null)
            {
                if(dataProvider.getNextEnabled() && dataProvider.getCanLoadNext())
                {
                    if (dataProvider.size() > getNumberOfColumns())
                    {
                        int lastRowItems = dataProvider.size()%getNumberOfColumns();
                        if(lastRowItems==0)
                            lastRowItems=getNumberOfColumns();
                        List lastRow = dataProvider.subList(dataProvider.size()-lastRowItems,dataProvider.size());
                        for (int i = 0; i < lastRow.size(); i++) {
                            if(lastRow.get(i)==item)
                            {
                                dataProvider.next();
                                break;
                            }
                        }
                    }
                }
            }
        }
    }
    public BackgroundImageHelper getBackgroundImageHelper() {
        return new BackgroundImageHelper(getActivity(),this);
    }


    public PresenterSelector createMainRowPresenterSelector() {
        return null;
    }
    public Presenter createMainRowPresenter() {
        return new ListRowPresenter();
    }
    public AbstractArrayObjectAdapter createMainArrayObjectAdapter(Presenter presenter) {
        return new DefaultArrayObjectAdapter(presenter);
    }
    public AbstractArrayObjectAdapter createMainArrayObjectAdapter(PresenterSelector presenter) {
        return new DefaultArrayObjectAdapter(presenter);
    }

    public Row getListRow(IHeaderItem headerItem, HeaderItem header, ArrayObjectAdapter listRowAdapter) {
        return new ListRow(header, listRowAdapter);
    }

    public HeaderItem getHeaderItem(int i,IHeaderItem headerItem, String name)
    {
        return new HeaderItem(i,name);
    }
    public ArrayObjectAdapter getRowArrayObjectAdapter(IHeaderItem headerItem,Presenter presenter) {
        return new WatcherArrayObjectAdapter(presenter);
    }
    public ArrayObjectAdapter getRowArrayObjectAdapter(IHeaderItem headerItem,PresenterSelector presenterSelector) {
        return new WatcherArrayObjectAdapter(presenterSelector);
    }
    public Class getSearchActivity() {
        return null;
    }
    public void onItemSelected(Presenter.ViewHolder itemViewHolder, Object item,RowPresenter.ViewHolder rowViewHolder, Row row){}
    public void onItemClicked(Presenter.ViewHolder itemViewHolder, Object item,RowPresenter.ViewHolder rowViewHolder, Row row) {}






    //UI Customise


    private void setupUIElements() {
        if(getTitleRes()!=-1)
            setTitle(getString(getTitleRes()));
        else if(getTitleValue()!=null)
            setTitle(getTitleValue());

        if(getBadgeDrawableRes()!=-1)
            setBadgeDrawable(getResources().getDrawable(getBadgeDrawableRes(), null));
        else if(getBadgeDrawableDrawable()!=null)
            setBadgeDrawable(getBadgeDrawableDrawable());

        if(getSearchAffordanceColorRes()!=-1)
            setSearchAffordanceColor(getResources().getColor(getSearchAffordanceColorRes()));
        else if(getSearchAffordanceColorValue()!=-1)
            setSearchAffordanceColor(getSearchAffordanceColorValue());

    }
    @Override
    public String getFirstTimeLoaderMessage() {
        return getActivity().getString(R.string._busy_message);
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



    @StringRes
    protected int getTitleRes()
    {
        return R.string.app_name;
    }
    protected String getTitleValue()
    {
        return null;
    }
    @DrawableRes
    protected int getBadgeDrawableRes()
    {
        return -1;
    }
    protected Drawable getBadgeDrawableDrawable()
    {
        return null;
    }
}
