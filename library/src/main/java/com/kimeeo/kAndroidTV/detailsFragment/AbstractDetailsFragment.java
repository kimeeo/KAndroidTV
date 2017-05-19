package com.kimeeo.kAndroidTV.detailsFragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.v17.leanback.app.BackgroundManager;
import android.support.v17.leanback.app.DetailsFragment;
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
import android.view.View;
import android.view.ViewGroup;

import com.kimeeo.kAndroid.dataProvider.DataProvider;
import com.kimeeo.kAndroid.dataProvider.MonitorList;
import com.kimeeo.kAndroidTV.R;
import com.kimeeo.kAndroidTV.browseFragment.AbstractBrowseFragment;
import com.kimeeo.kAndroidTV.core.AbstractArrayObjectAdapter;
import com.kimeeo.kAndroidTV.core.BackgroundImageHelper;
import com.kimeeo.kAndroidTV.core.DefaultArrayObjectAdapter;
import com.kimeeo.kAndroidTV.core.IHeaderItem;
import com.kimeeo.kAndroidTV.core.WatcherArrayObjectAdapter;

import java.net.URI;
import java.util.List;

/**
 * Created by BhavinPadhiyar on 5/19/17.
 */

abstract public class AbstractDetailsFragment extends DetailsFragment implements BackgroundImageHelper.OnUpdate,DataProvider.OnFatchingObserve,MonitorList.OnChangeWatcher
{

    private List<Action> actionlist;

    protected boolean supportBackgroundChange() {
        return false;
    }
    protected URI getBackgroundImageURI(Object item) {
        return null;
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
        setupView();
        if(getDataProvider()==null)
            setDataProvider(createDataProvider());
        getDataProvider().setRefreshEnabled(false);

        setupEventListeners();

        if(getDataProvider().size()!=0 )
            itemsAdded(0,getDataProvider());

        getDataProvider().next();
        if(supportBackgroundChange())
            backgroundImageHelper=getBackgroundImageHelper();

        prepareEntranceTransition();
    }



    protected  List<Action> createActionlist()
    {
        return null;
    }

    protected ArrayObjectAdapter createActionArrayObjectAdapter() {
        return new DefaultArrayObjectAdapter();
    }
    FullWidthDetailsOverviewRowPresenter detailsRowPresenter;
    protected  void setupView()
    {
        ClassPresenterSelector rowPresenterSelector = new ClassPresenterSelector();
        mRowsAdapter = createArrayObjectAdapter(rowPresenterSelector);

        detailsRowPresenter = createDetailsRowPresenter(createDetailsDescriptionPresenter());
        configDetails(detailsRowPresenter);
        rowPresenterSelector.addClassPresenter(DetailsOverviewRow.class, detailsRowPresenter);

        PresenterSelector presenterSelector = createListRowPresenterSelector();
        rowPresenterSelector.addClassPresenterSelector(ListRow.class, presenterSelector);

        setAdapter(mRowsAdapter);
    }

    protected void configDetails(FullWidthDetailsOverviewRowPresenter detailsRowPresenter) {
        detailsRowPresenter.setInitialState(FullWidthDetailsOverviewRowPresenter.STATE_HALF);
        FullWidthDetailsOverviewSharedElementHelper mHelper = createDtailsSharedElementHelper();
        if(getTransitionName()!=null)
            mHelper.setSharedElementEnterTransition(getActivity(), getTransitionName());
        detailsRowPresenter.setListener(mHelper);
        detailsRowPresenter.setParticipatingEntranceTransition(false);
        DetailsOverviewRow detailsOverview = createDetailsOverviewRow();
        actionlist=createActionlist();
        if(actionlist!=null && actionlist.size()!=0)
        {
            actionAdapter = createActionArrayObjectAdapter();
            actionAdapter.addAll(0,actionlist);
            detailsOverview.setActionsAdapter(actionAdapter);
        }
        mRowsAdapter.add(detailsOverview);
    }

    abstract protected PresenterSelector createListRowPresenterSelector();


    private ArrayObjectAdapter actionAdapter;

    protected DetailsOverviewRow createDetailsOverviewRow()
    {
        return new DetailsOverviewRow(createDetailsData());
    }

    protected abstract Object createDetailsData();

    protected FullWidthDetailsOverviewSharedElementHelper createDtailsSharedElementHelper() {
        return new FullWidthDetailsOverviewSharedElementHelper();
    }

    abstract protected FullWidthDetailsOverviewRowPresenter createDetailsRowPresenter(Presenter detailsDescriptionPresenter);
    protected abstract Presenter createDetailsDescriptionPresenter();

    protected String getTransitionName() {
        return null;
    }

    protected BackgroundImageHelper getBackgroundImageHelper() {
        return new BackgroundImageHelper(getActivity(),this);
    }
    protected Presenter createListRowPresenter() {
        return new ListRowPresenter();
    }
    protected AbstractArrayObjectAdapter createArrayObjectAdapter(PresenterSelector presenter) {
        return new DefaultArrayObjectAdapter(presenter);
    }
    @Override
    public void itemsAdded(int index, List list) {
        for (int i = 0; i < list.size(); i++) {
            if(list.get(i) instanceof IHeaderItem)
            {
                IHeaderItem headerItem= (IHeaderItem)list.get(i);
                PresenterSelector presenterSelector = getPresenterSelector(headerItem);
                final ArrayObjectAdapter listRowAdapter = getRowArrayObjectAdapter(headerItem,presenterSelector);

                List data = headerItem.getData();
                for (int j = 0; j < data.size(); j++) {
                    listRowAdapter.add(data.get(j));
                }
                if(data instanceof DataProvider)
                {
                    DataProvider rowData = (DataProvider)data;
                    if(listRowAdapter instanceof WatcherArrayObjectAdapter)
                        ((WatcherArrayObjectAdapter)listRowAdapter).setDataProvider(rowData);
                    rowData.next();
                }
                int row=index+i;
                HeaderItem header = getHeaderItem(row,headerItem, headerItem.getName());
                mRowsAdapter.add(getListRow(headerItem,header, listRowAdapter));
            }
        }
    }


    abstract protected PresenterSelector getPresenterSelector(IHeaderItem headerItem);
    protected Row getListRow(IHeaderItem headerItem, HeaderItem header, ArrayObjectAdapter listRowAdapter) {
        return new ListRow(header, listRowAdapter);
    }

    protected  HeaderItem getHeaderItem(int i,IHeaderItem headerItem, String name)
    {
        return new HeaderItem(i,name);
    }

    protected ArrayObjectAdapter getRowArrayObjectAdapter(IHeaderItem headerItem,Presenter presenter) {
        return new ArrayObjectAdapter(presenter);
    }
    protected ArrayObjectAdapter getRowArrayObjectAdapter(IHeaderItem headerItem,PresenterSelector presenterSelector) {
        return new WatcherArrayObjectAdapter(presenterSelector);
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
            setOnSearchClickedListener(new AbstractDetailsFragment.SearchEventListeners());

        setOnItemViewClickedListener(new AbstractDetailsFragment.ItemViewClickedListener());
        setOnItemViewSelectedListener(new AbstractDetailsFragment.ItemViewSelectedListener());
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
            AbstractDetailsFragment.this.onItemClicked(itemViewHolder,item,rowViewHolder,row);
        }
    }
    private final class ItemViewSelectedListener implements OnItemViewSelectedListener {
        @Override
        public void onItemSelected(Presenter.ViewHolder itemViewHolder, Object item,RowPresenter.ViewHolder rowViewHolder, Row row) {
            handelPaging(itemViewHolder,item,rowViewHolder,row);
            if(supportBackgroundChange())
                backgroundImageHelper.start(item);


            AbstractDetailsFragment.this.onItemSelected(itemViewHolder,item,rowViewHolder,row);
        }
    }

    protected void handelPaging(Presenter.ViewHolder itemViewHolder, Object item,RowPresenter.ViewHolder rowViewHolder, Row row) {
        DataProvider dataProvider = getDataProvider();
        if(dataProvider!=null)
        {
            if(dataProvider.getNextEnabled() && dataProvider.getCanLoadNext())
            {
                if (dataProvider.size() > 1)
                {
                    if(dataProvider.get(dataProvider.size() - 1) instanceof IHeaderItem)
                    {
                        IHeaderItem headerItem = (IHeaderItem)dataProvider.get(dataProvider.size() - 1);
                        DataProvider rowDataProvider=(DataProvider)headerItem.getData();
                        for (int i = 0; i < rowDataProvider.size(); i++) {
                            if(rowDataProvider.get(i)==item)
                            {
                                dataProvider.next();
                                break;
                            }
                        }
                    }
                }
            }
            for (int i = 0; i < dataProvider.size(); i++) {
                if(dataProvider.get(i) instanceof IHeaderItem)
                {
                    IHeaderItem headerItem = (IHeaderItem)dataProvider.get(i);
                    if(headerItem.getData()!=null && headerItem.getData() instanceof DataProvider)
                    {
                        DataProvider rowDataProvider=(DataProvider)headerItem.getData();
                        if(rowDataProvider.getNextEnabled() && rowDataProvider.getCanLoadNext()) {
                            if (rowDataProvider.size() > 1 && item == rowDataProvider.get(rowDataProvider.size() - 1)) {
                                rowDataProvider.next();
                                break;
                            }
                        }
                    }
                }
            }
        }

    }


    private final class SearchEventListeners implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            onSearch();
        }
    }
}
