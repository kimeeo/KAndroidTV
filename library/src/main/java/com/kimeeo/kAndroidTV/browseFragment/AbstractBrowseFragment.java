package com.kimeeo.kAndroidTV.browseFragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.v17.leanback.app.BrowseFragment;
import android.support.v17.leanback.widget.*;
import android.view.View;

import com.kimeeo.kAndroid.dataProvider.DataProvider;
import com.kimeeo.kAndroid.dataProvider.MonitorList;
import com.kimeeo.kAndroidTV.R;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by BhavinPadhiyar on 5/16/17.
 */

abstract public class AbstractBrowseFragment extends BrowseFragment implements DataProvider.OnFatchingObserve,MonitorList.OnChangeWatcher {

    protected AbstractArrayObjectAdapter mRowsAdapter;
    private boolean showBusyForFirstTimeLoad=true;

    abstract protected @NonNull DataProvider createDataProvider();
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
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        setupUIElements();

        if(getDataProvider()==null)
            setDataProvider(createDataProvider());
        getDataProvider().setRefreshEnabled(false);
        mRowsAdapter = createArrayObjectAdapter(createListRowPresenter());
        setAdapter(mRowsAdapter);
        setupEventListeners();

        if(getDataProvider().size()!=0 )
            itemsAdded(0,getDataProvider());

        getDataProvider().next();
    }

    protected Presenter createListRowPresenter() {
        return new ListRowPresenter();
    }
    protected AbstractArrayObjectAdapter createArrayObjectAdapter(Presenter presenter) {
        return new DefaultArrayObjectAdapter(presenter);
    }

    protected void loadRows()
    {

    }
    abstract protected Presenter getPresenter(IHeaderItem headerItem);

    @Override
    public void itemsAdded(int index, List list) {
        for (int i = 0; i < list.size(); i++) {
            if(list.get(i) instanceof IHeaderItem)
            {
                IHeaderItem headerItem= (IHeaderItem)list.get(i);
                Presenter presenter = getPresenter(headerItem);
                ArrayObjectAdapter listRowAdapter = getRowArrayObjectAdapter(headerItem,presenter);
                List data = headerItem.getData();
                for (int j = 0; j < data.size(); j++) {
                    listRowAdapter.add(data.get(j));
                }
                if(data instanceof DataProvider)
                {
                    RowUpdateWatcher rowUpdateWatcher =createRowUpdateWatcher();
                    rowUpdateWatcher.setDataProvider((DataProvider)data);
                    rowUpdateWatcher.setHeaderItem(headerItem);
                    rowUpdateWatcher.setListRowAdapter(listRowAdapter);
                    rowUpdateWatcher.startWatching();
                    rowUpdateWatcher.next();
                }
                HeaderItem header = getHeaderItem(i, headerItem.getName());
                mRowsAdapter.add(index+i,getListRow(header, listRowAdapter));
            }
        }
    }

    protected RowUpdateWatcher createRowUpdateWatcher() {
        return new RowUpdateWatcher();
    }

    protected Row getListRow(HeaderItem header, ArrayObjectAdapter listRowAdapter) {
        return new ListRow(header, listRowAdapter);
    }

    protected  HeaderItem getHeaderItem(int i, String name)
    {
        return new HeaderItem(i,name);
    }

    protected ArrayObjectAdapter getRowArrayObjectAdapter(IHeaderItem headerItem,Presenter presenter) {
        return new ArrayObjectAdapter(presenter);
    }

    @Override
    public void itemsRemoved(int index, List list) {
        mRowsAdapter.removeItems(index,list.size());
    }
    @Override
    public void itemsChanged(int index, List list) {
        mRowsAdapter.notifyArrayItemRangeChanged(index,list.size());
    }

    ProgressDialog progressDialog;
    private boolean fistTime=true;

    @Override
    public void onFetchingStart(boolean b) {
        if (fistTime && getShowBusyForFirstTimeLoad()) {
            fistTime=false;
            showBusy("Loading. Please wait...");
        }
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
            progressDialog = null;
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


        setHeadersState(defaultHeadersState());
        setHeadersTransitionOnBackEnabled(defaultHeadersTransitionOnBackEnabled());

        if(getBrandColorRes()!=-1)
            setBrandColor(getResources().getColor(getBrandColorRes()));
        else if(getBrandColorValue()!=-1)
            setBrandColor(getBrandColorValue());

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

    protected int getBrandColorValue()
    {
        return -1;
    }
    @ColorRes
    protected int getBrandColorRes()
    {
        return R.color.fastlane_background;
    }
    protected boolean defaultHeadersTransitionOnBackEnabled()
    {
        return false;
    }
    protected int defaultHeadersState()
    {
        return HEADERS_ENABLED;
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
        setOnSearchClickedListener(new SearchEventListeners());
        setOnItemViewClickedListener(new ItemViewClickedListener());
        setOnItemViewSelectedListener(new ItemViewSelectedListener());
    }
    protected void onItemClicked(Presenter.ViewHolder itemViewHolder, Object item,RowPresenter.ViewHolder rowViewHolder, Row row) {

    }
    protected void onItemSelected(Presenter.ViewHolder itemViewHolder, Object item,RowPresenter.ViewHolder rowViewHolder, Row row) {

    }
    protected void onSearch() {

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
            AbstractBrowseFragment.this.onItemClicked(itemViewHolder,item,rowViewHolder,row);
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
            AbstractBrowseFragment.this.onItemSelected(itemViewHolder,item,rowViewHolder,row);
        }
    }
    private final class SearchEventListeners implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            onSearch();
        }
    }
    public class RowUpdateWatcher implements MonitorList.OnChangeWatcher
    {
        public ArrayObjectAdapter getListRowAdapter() {
            return listRowAdapter;
        }

        public void setListRowAdapter(ArrayObjectAdapter listRowAdapter) {
            this.listRowAdapter = listRowAdapter;
        }

        protected ArrayObjectAdapter listRowAdapter;

        public void setDataProvider(DataProvider dataProvider) {
            this.dataProvider = dataProvider;
        }

        protected DataProvider dataProvider;

        public IHeaderItem getHeaderItem() {
            return headerItem;
        }

        public void setHeaderItem(IHeaderItem headerItem) {
            this.headerItem = headerItem;
        }

        protected IHeaderItem headerItem;


        public void startWatching()
        {
            dataProvider.addDataChangeWatcher(this);
        }
        public void next() {
            dataProvider.next();
        }

        @Override
        public void itemsAdded(int i, List list) {
            for (int j = 0; j < list.size(); j++) {
                listRowAdapter.add(list.get(j));
            }
        }

        @Override
        public void itemsRemoved(int i, List list) {
            listRowAdapter.removeItems(i,list.size());
        }

        @Override
        public void itemsChanged(int i, List list) {
            listRowAdapter.notifyArrayItemRangeChanged(i,list.size());
        }
    }
}
