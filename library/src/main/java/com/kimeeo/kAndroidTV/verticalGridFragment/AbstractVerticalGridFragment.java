package com.kimeeo.kAndroidTV.verticalGridFragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.v17.leanback.app.BackgroundManager;
import android.support.v17.leanback.app.VerticalGridFragment;
import android.support.v17.leanback.widget.FocusHighlight;
import android.support.v17.leanback.widget.OnItemViewClickedListener;
import android.support.v17.leanback.widget.OnItemViewSelectedListener;
import android.support.v17.leanback.widget.Presenter;
import android.support.v17.leanback.widget.PresenterSelector;
import android.support.v17.leanback.widget.Row;
import android.support.v17.leanback.widget.RowPresenter;
import android.support.v17.leanback.widget.VerticalGridPresenter;
import android.view.View;

import com.kimeeo.kAndroid.dataProvider.DataProvider;
import com.kimeeo.kAndroid.dataProvider.MonitorList;
import com.kimeeo.kAndroidTV.R;
import com.kimeeo.kAndroidTV.core.AbstractArrayObjectAdapter;
import com.kimeeo.kAndroidTV.core.BackgroundImageHelper;
import com.kimeeo.kAndroidTV.core.DefaultArrayObjectAdapter;

import java.net.URI;
import java.util.List;

/**
 * Created by BhavinPadhiyar on 5/18/17.
 */

abstract public class AbstractVerticalGridFragment extends VerticalGridFragment implements BackgroundImageHelper.OnUpdate,DataProvider.OnFatchingObserve,MonitorList.OnChangeWatcher {


    protected boolean supportBackgroundChange() {
        return false;
    }

    public void updateBackground(BackgroundManager mBackgroundManager, Object item, int width, int height)
    {

    }
    protected BackgroundImageHelper backgroundImageHelper;
    protected AbstractArrayObjectAdapter mRowsAdapter;
    private boolean showBusyForFirstTimeLoad=true;

    abstract protected @NonNull
    DataProvider createDataProvider();
    protected DataProvider dataProvider;
    protected void configDataManager(DataProvider dataProvider) {}
    public DataProvider getDataProvider()
    {
        return dataProvider;
    }
    public void setDataProvider(DataProvider dataProvider){
        if(this.dataProvider!=null)
        {
            this.dataProvider.removeFatchingObserve(this);
            this.dataProvider.removeDataChangeWatcher(this);
            this.dataProvider = null;
        }
        if(dataProvider!=null) {
            this.dataProvider = dataProvider;
            this.dataProvider.addFatchingObserve(this);
            this.dataProvider.addDataChangeWatcher(this);
            configDataManager(this.dataProvider);
        }
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        if (null != backgroundImageHelper) {
            backgroundImageHelper.cancel();
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setupUIElements();

        VerticalGridPresenter gridPresenter = createVerticalGridPresenter(getZoomFactor(),getNumberOfColumns());
        setGridPresenter(gridPresenter);

        if(getDataProvider()==null)
            setDataProvider(createDataProvider());
        getDataProvider().setRefreshEnabled(false);

        PresenterSelector presenterSelector = getRowItemPresenterSelector();
        mRowsAdapter = getRowArrayObjectAdapter(presenterSelector);

        setAdapter(mRowsAdapter);
        setupEventListeners();

        if(getDataProvider().size()!=0 )
            itemsAdded(0,getDataProvider());

        getDataProvider().next();
        if(supportBackgroundChange())
            backgroundImageHelper=getBackgroundImageHelper();

    }
    abstract protected PresenterSelector getRowItemPresenterSelector();


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

    protected BackgroundImageHelper getBackgroundImageHelper() {
        return new BackgroundImageHelper(getActivity(),this);
    }



    protected AbstractArrayObjectAdapter createArrayObjectAdapter(Presenter presenter) {
        return new DefaultArrayObjectAdapter(presenter);
    }
    protected AbstractArrayObjectAdapter createArrayObjectAdapter(PresenterSelector presenter) {
        return new DefaultArrayObjectAdapter(presenter);
    }
    @Override
    public void itemsAdded(int index, List list) {
        mRowsAdapter.addAll(index,list);
    }

    protected AbstractArrayObjectAdapter getRowArrayObjectAdapter(Presenter presenter) {
        return new DefaultArrayObjectAdapter(presenter);
    }
    protected AbstractArrayObjectAdapter getRowArrayObjectAdapter(PresenterSelector presenterSelector) {
        return new DefaultArrayObjectAdapter(presenterSelector);
    }

    @Override
    public void itemsRemoved(int index, List list) {
        mRowsAdapter.removeItems(index,list.size());
    }
    @Override
    public void itemsChanged(int index, List list) {
        mRowsAdapter.notifyArrayItemRangeChanged(index,list.size());
    }

    private ProgressDialog progressDialog;
    private boolean fistTime=true;

    @Override
    public void onFetchingStart(boolean b) {
        if (fistTime && getShowBusyForFirstTimeLoad()) {
            fistTime=false;
            showBusy(getFirstTimeLoaderMessageResID());
        }
    }

    protected int getFirstTimeLoaderMessageResID() {
        return R.string._busy_message;
    }

    protected void showBusy(@StringRes int messageRes) {
        showBusy(getActivity().getString(messageRes));
    }
    protected void showBusy(String message) {
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage(message);
        progressDialog.setIndeterminate(true);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

    }

    @Override
    public void onFetchingFinish(boolean b) {
        hideBusy();
    }

    private void hideBusy() {
        if(progressDialog!=null) {
            progressDialog.hide();
            progressDialog=null;
        }
    }

    @Override
    public void onFetchingEnd(List<?> list, boolean b) {

    }
    @Override
    public void onFetchingError(Object o) {

    }

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
    private void setupEventListeners() {
        if(getSearchActivity()!=null)
            setOnSearchClickedListener(new AbstractVerticalGridFragment.SearchEventListeners());

        setOnItemViewClickedListener(new AbstractVerticalGridFragment.ItemViewClickedListener());
        setOnItemViewSelectedListener(new AbstractVerticalGridFragment.ItemViewSelectedListener());
    }
    protected Class getSearchActivity() {
        return null;
    }

    protected void onItemClicked(Presenter.ViewHolder itemViewHolder, Object item, RowPresenter.ViewHolder rowViewHolder, Row row) {

    }
    protected void onItemSelected(Presenter.ViewHolder itemViewHolder, Object item,RowPresenter.ViewHolder rowViewHolder, Row row) {

    }
    protected void onSearch() {
        if(getSearchActivity()!=null) {
            Intent intent = new Intent(getActivity(), getSearchActivity());
            startActivity(intent);
        }
    }

    public boolean getShowBusyForFirstTimeLoad() {
        return showBusyForFirstTimeLoad;
    }

    public boolean isShowBusyForFirstTimeLoad() {
        return showBusyForFirstTimeLoad;
    }

    public void setShowBusyForFirstTimeLoad(boolean showBusyForFirstTimeLoad) {
        this.showBusyForFirstTimeLoad = showBusyForFirstTimeLoad;
    }

    private final class ItemViewClickedListener implements OnItemViewClickedListener {
        @Override
        public void onItemClicked(Presenter.ViewHolder itemViewHolder, Object item,RowPresenter.ViewHolder rowViewHolder, Row row) {
            AbstractVerticalGridFragment.this.onItemClicked(itemViewHolder,item,rowViewHolder,row);
        }
    }
    private final class ItemViewSelectedListener implements OnItemViewSelectedListener {
        @Override
        public void onItemSelected(Presenter.ViewHolder itemViewHolder, Object item,RowPresenter.ViewHolder rowViewHolder, Row row) {

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

            if(supportBackgroundChange())
                backgroundImageHelper.start(item);


            AbstractVerticalGridFragment.this.onItemSelected(itemViewHolder,item,rowViewHolder,row);
        }
    }
    private final class SearchEventListeners implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            onSearch();
        }
    }
}
