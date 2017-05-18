package com.kimeeo.kAndroidTV.searchFragment;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.v17.leanback.app.BackgroundManager;
import android.support.v17.leanback.app.SearchFragment;
import android.support.v17.leanback.widget.ArrayObjectAdapter;
import android.support.v17.leanback.widget.HeaderItem;
import android.support.v17.leanback.widget.ListRow;
import android.support.v17.leanback.widget.ListRowPresenter;
import android.support.v17.leanback.widget.ObjectAdapter;
import android.support.v17.leanback.widget.OnItemViewClickedListener;
import android.support.v17.leanback.widget.OnItemViewSelectedListener;
import android.support.v17.leanback.widget.Presenter;
import android.support.v17.leanback.widget.PresenterSelector;
import android.support.v17.leanback.widget.Row;
import android.support.v17.leanback.widget.RowPresenter;
import android.support.v17.leanback.widget.SpeechRecognitionCallback;
import android.widget.Toast;

import com.kimeeo.kAndroid.dataProvider.DataProvider;
import com.kimeeo.kAndroid.dataProvider.MonitorList;
import com.kimeeo.kAndroidTV.R;
import com.kimeeo.kAndroidTV.browseFragment.AbstractArrayObjectAdapter;
import com.kimeeo.kAndroidTV.browseFragment.AbstractBrowseFragment;
import com.kimeeo.kAndroidTV.browseFragment.BackgroundImageHelper;
import com.kimeeo.kAndroidTV.browseFragment.DefaultArrayObjectAdapter;
import com.kimeeo.kAndroidTV.browseFragment.IHeaderItem;
import com.kimeeo.kAndroidTV.browseFragment.WatcherArrayObjectAdapter;

import java.net.URI;
import java.util.List;

/**
 * Created by BhavinPadhiyar on 5/18/17.
 */


abstract public class AbstractSearchFragment extends SearchFragment implements SearchFragment.SearchResultProvider,SpeechRecognitionCallback,BackgroundImageHelper.OnUpdate,DataProvider.OnFatchingObserve,MonitorList.OnChangeWatcher {

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
















    private static final int REQUEST_SPEECH = 0x00000010;

    private ArrayObjectAdapter mRowsAdapter;
    public String getQuery() {
        return mQuery;
    }


    private String mQuery;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setSpeechRecognitionCallback(this);
        setSearchResultProvider(this);

        setupUIElements();

        if(getDataProvider()==null)
            setDataProvider(createDataProvider());
        getDataProvider().setRefreshEnabled(false);
        PresenterSelector presenterSelector=createListRowPresenterSelector();
        if(presenterSelector!=null)
            mRowsAdapter = createArrayObjectAdapter(presenterSelector);
        else
            mRowsAdapter = createArrayObjectAdapter(createListRowPresenter());

        setupEventListeners();

        if(getDataProvider().size()!=0 )
            itemsAdded(0,getDataProvider());

        if(supportBackgroundChange())
            backgroundImageHelper=getBackgroundImageHelper();
    }
    private BackgroundImageHelper getBackgroundImageHelper() {
        return new BackgroundImageHelper(getActivity(),this);
    }


    protected PresenterSelector createListRowPresenterSelector() {
        return null;
    }
    protected Presenter createListRowPresenter() {
        return new ListRowPresenter();
    }
    protected AbstractArrayObjectAdapter createArrayObjectAdapter(Presenter presenter) {
        return new DefaultArrayObjectAdapter(presenter);
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
                HeaderItem header = getHeaderItem(i, headerItem.getName());
                int row=index+i;
                mRowsAdapter.add(row,getListRow(headerItem,header, listRowAdapter));
            }
        }
    }


    abstract protected PresenterSelector getPresenterSelector(IHeaderItem headerItem);
    protected Row getListRow(IHeaderItem headerItem, HeaderItem header, ArrayObjectAdapter listRowAdapter) {
        return new ListRow(header, listRowAdapter);
    }

    protected  HeaderItem getHeaderItem(int i, String name)
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

    ProgressDialog progressDialog;
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
            progressDialog = null;
        }
    }

    @Override
    public void onFetchingEnd(List<?> list, boolean b) {
        if(getEmptySearchMessageRes()!=-1 && dataProvider!=null  && dataProvider.size()==0)
            Toast.makeText(getActivity(), getString(getEmptySearchMessageRes()), Toast.LENGTH_SHORT).show();
        else if(getEmptySearchMessage()!=null && getEmptySearchMessage().equals("")==false && dataProvider!=null && dataProvider.size()==0)
            Toast.makeText(getActivity(), getEmptySearchMessage(), Toast.LENGTH_SHORT).show();
    }

    protected String getEmptySearchMessage() {
        return "";
    }
    @StringRes
    protected int getEmptySearchMessageRes() {
        return -1;
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
        setOnItemViewClickedListener(new AbstractSearchFragment.ItemViewClickedListener());
        setOnItemViewSelectedListener(new AbstractSearchFragment.ItemViewSelectedListener());
    }
    protected void onItemClicked(Presenter.ViewHolder itemViewHolder, Object item, RowPresenter.ViewHolder rowViewHolder, Row row) {

    }
    protected void onItemSelected(Presenter.ViewHolder itemViewHolder, Object item,RowPresenter.ViewHolder rowViewHolder, Row row) {

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
            AbstractSearchFragment.this.onItemClicked(itemViewHolder,item,rowViewHolder,row);
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

            if(supportBackgroundChange())
                backgroundImageHelper.start(item);


            AbstractSearchFragment.this.onItemSelected(itemViewHolder,item,rowViewHolder,row);
        }
    }




    @Override
    public void recognizeSpeech() {
        startActivityForResult(getRecognizerIntent(), REQUEST_SPEECH);
    }

    @Override
    public ObjectAdapter getResultsAdapter() {
        return mRowsAdapter;
    }

    @Override
    public boolean onQueryTextChange(String query) {
        return search(query);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return search(query);
    }

    protected boolean search(String query) {
        if(query!=null && query.length()>=threshold()) {
            if(mQuery!=null && mQuery.equals(query))
                return false;
            mQuery = query;
            getDataProvider().reset();
            getDataProvider().next();
            return true;
        }
        return false;
    }

    public int threshold() {
        return 3;
    }

    public boolean hasResults() {
        return mRowsAdapter.size() > 0;
    }
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == REQUEST_SPEECH && resultCode== Activity.RESULT_OK)
        {
            setSearchQuery(data, true);
        }

    }
}
