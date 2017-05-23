package com.kimeeo.kAndroidTV.core;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v17.leanback.app.BrowseFragment;
import android.support.v17.leanback.app.DetailsFragment;
import android.support.v17.leanback.app.PlaybackOverlayFragment;
import android.support.v17.leanback.app.RowsFragment;
import android.support.v17.leanback.app.SearchFragment;
import android.support.v17.leanback.app.VerticalGridFragment;
import android.support.v17.leanback.widget.ArrayObjectAdapter;
import android.support.v17.leanback.widget.HeaderItem;
import android.support.v17.leanback.widget.ListRowPresenter;
import android.support.v17.leanback.widget.OnItemViewClickedListener;
import android.support.v17.leanback.widget.OnItemViewSelectedListener;
import android.support.v17.leanback.widget.Presenter;
import android.support.v17.leanback.widget.PresenterSelector;
import android.support.v17.leanback.widget.Row;
import android.support.v17.leanback.widget.RowPresenter;
import android.view.View;

import com.kimeeo.kAndroid.dataProvider.DataProvider;
import com.kimeeo.kAndroid.dataProvider.MonitorList;
import com.kimeeo.kAndroidTV.browseFragment.AbstractBrowseFragment;

import java.util.List;

/**
 * Created by BhavinPadhiyar on 5/19/17.
 */

public class RowBasedFragmentHelper implements DataProvider.OnFatchingObserve,MonitorList.OnChangeWatcher {
    private BackgroundImageHelper backgroundImageHelper;

    public HelperProvider getHelperProvider() {
        return helperProvider;
    }

    private final HelperProvider helperProvider;
    private final Fragment host;

    public DataProvider getDataProvider() {
        return dataProvider;
    }

    protected DataProvider dataProvider;

    public AbstractArrayObjectAdapter getRowsAdapter() {
        return mRowsAdapter;
    }

    protected AbstractArrayObjectAdapter mRowsAdapter;
    public RowBasedFragmentHelper(Fragment host, HelperProvider helperProvider)
    {
        this.helperProvider = helperProvider;
        this.host = host;
    }

    public void build() {

        dataProvider = helperProvider.getDataProvider();
        dataProvider.setRefreshEnabled(false);

        dataProvider.addFatchingObserve(this);
        dataProvider.addDataChangeWatcher(this);
        PresenterSelector presenterSelector=helperProvider.createMainRowPresenterSelector();
        if(presenterSelector!=null)
            mRowsAdapter = helperProvider.createMainArrayObjectAdapter(presenterSelector);
        else
            mRowsAdapter = helperProvider.createMainArrayObjectAdapter(helperProvider.createMainRowPresenter());

        if(host instanceof BrowseFragment) {
            BrowseFragment browseFragment = (BrowseFragment) host;
            browseFragment.setAdapter(mRowsAdapter);
        }
        else if(host instanceof RowsFragment) {
            RowsFragment browseFragment = (RowsFragment) host;
            browseFragment.setAdapter(mRowsAdapter);
        }
        else if(host instanceof PlaybackOverlayFragment) {
            PlaybackOverlayFragment browseFragment = (PlaybackOverlayFragment)host;
            browseFragment.setAdapter(mRowsAdapter);
        }
        else if(host instanceof DetailsFragment) {
            DetailsFragment browseFragment = (DetailsFragment)host;
            browseFragment.setAdapter(mRowsAdapter);
        }
        else if(host instanceof VerticalGridFragment) {
            VerticalGridFragment browseFragment = (VerticalGridFragment)host;
            browseFragment.setAdapter(mRowsAdapter);
        }
        setupEventListeners();

        if(dataProvider.size()!=0 )
            itemsAdded(0,dataProvider);

        if(helperProvider.supportBackgroundChange())
            backgroundImageHelper=helperProvider.getBackgroundImageHelper();

    }
    public void next() {
        dataProvider.next();
    }

    protected void setupEventListeners() {
        if(host instanceof BrowseFragment) {
            BrowseFragment browseFragment = (BrowseFragment)host;
            if (helperProvider.getSearchActivity() != null)
                browseFragment.setOnSearchClickedListener(new SearchEventListeners());

            browseFragment.setOnItemViewClickedListener(new ItemViewClickedListener());
            browseFragment.setOnItemViewSelectedListener(new ItemViewSelectedListener());
        }
        else if(host instanceof RowsFragment) {
            RowsFragment browseFragment = (RowsFragment)host;

            browseFragment.setOnItemViewClickedListener(new ItemViewClickedListener());
            browseFragment.setOnItemViewSelectedListener(new ItemViewSelectedListener());
        }
        else if(host instanceof SearchFragment) {
            SearchFragment browseFragment = (SearchFragment)host;

            browseFragment.setOnItemViewClickedListener(new ItemViewClickedListener());
            browseFragment.setOnItemViewSelectedListener(new ItemViewSelectedListener());
        }
        else if(host instanceof PlaybackOverlayFragment) {
            PlaybackOverlayFragment browseFragment = (PlaybackOverlayFragment)host;
            if (helperProvider.getSearchActivity() != null)
                browseFragment.setOnSearchClickedListener(new SearchEventListeners());
            browseFragment.setOnItemViewClickedListener(new ItemViewClickedListener());
            browseFragment.setOnItemViewSelectedListener(new ItemViewSelectedListener());
        }
        else if(host instanceof DetailsFragment) {
            DetailsFragment browseFragment = (DetailsFragment)host;
            if (helperProvider.getSearchActivity() != null)
                browseFragment.setOnSearchClickedListener(new SearchEventListeners());
            browseFragment.setOnItemViewClickedListener(new ItemViewClickedListener());
            browseFragment.setOnItemViewSelectedListener(new ItemViewSelectedListener());
        }
        else if(host instanceof VerticalGridFragment) {
            VerticalGridFragment browseFragment = (VerticalGridFragment)host;
            if (helperProvider.getSearchActivity() != null)
                browseFragment.setOnSearchClickedListener(new SearchEventListeners());
            browseFragment.setOnItemViewClickedListener(new ItemViewClickedListener());
            browseFragment.setOnItemViewSelectedListener(new ItemViewSelectedListener());
        }
    }


    @Override
    public void itemsAdded(int index, List list) {
        for (int i = 0; i < list.size(); i++) {
            if(list.get(i) instanceof IHeaderItem)
            {
                IHeaderItem headerItem= (IHeaderItem)list.get(i);
                PresenterSelector presenterSelector = helperProvider.getRowItemPresenterSelector(headerItem);
                ArrayObjectAdapter listRowAdapter= helperProvider.getRowArrayObjectAdapter(headerItem,presenterSelector);


                List data = headerItem.getData();
                for (int j = 0; j < data.size(); j++) {
                    listRowAdapter.add(data.get(j));
                }
                if(data instanceof DataProvider)
                {
                    DataProvider rowData = (DataProvider)data;
                    if(listRowAdapter instanceof WatcherArrayObjectAdapter) {
                        ((WatcherArrayObjectAdapter) listRowAdapter).setDataProvider(rowData);
                        ((WatcherArrayObjectAdapter) listRowAdapter).setSupportRowProgressBar(helperProvider.getSupportRowProgressBar());
                    }
                    rowData.next();
                }
                int row=index+i;
                HeaderItem header = helperProvider.getHeaderItem(row,headerItem, headerItem.getName());
                mRowsAdapter.add(helperProvider.getListRow(headerItem,header, listRowAdapter));
            }
        }
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
        if (fistTime && helperProvider.getFirstTimeLoaderMessage()!=null && helperProvider.getFirstTimeLoaderMessage().trim().equals("")==false) {
            fistTime=false;
            showBusy(helperProvider.getFirstTimeLoaderMessage());
        }
    }
    protected void showBusy(String message) {
        progressDialog = new ProgressDialog(host.getActivity());
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
    public static ProgressPresenter getProgressPresenter()
    {
        return new ProgressPresenter();
    }

    @Override
    public void onFetchingEnd(List<?> list, boolean b) {

    }
    @Override
    public void onFetchingError(Object o) {

    }

    protected void onSearch() {
        if(helperProvider.getSearchActivity()!=null) {
            Intent intent = new Intent(host.getActivity(), helperProvider.getSearchActivity());
            host.startActivity(intent);
        }
    }

    public void onDestroy() {
        if(backgroundImageHelper!=null)
            backgroundImageHelper.cancel();
    }


    private final class SearchEventListeners implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            onSearch();
        }
    }

    private final class ItemViewClickedListener implements OnItemViewClickedListener {
        @Override
        public void onItemClicked(Presenter.ViewHolder itemViewHolder, Object item,RowPresenter.ViewHolder rowViewHolder, Row row) {
            helperProvider.onItemClicked(itemViewHolder,item,rowViewHolder,row);
        }
    }
    private final class ItemViewSelectedListener implements OnItemViewSelectedListener {
        @Override
        public void onItemSelected(Presenter.ViewHolder itemViewHolder, Object item,RowPresenter.ViewHolder rowViewHolder, Row row) {
            if(helperProvider.supportAutoPageLoader())
                handelPaging(itemViewHolder,item,rowViewHolder,row);

            if(item!=null &&  helperProvider.supportBackgroundChange() && backgroundImageHelper!=null)
                backgroundImageHelper.start(item);

            helperProvider.onItemSelected(itemViewHolder,item,rowViewHolder,row);
        }
    }
    protected void handelPaging(Presenter.ViewHolder itemViewHolder, Object item,RowPresenter.ViewHolder rowViewHolder, Row row) {
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



    public static interface HelperProvider
    {
        BackgroundImageHelper getBackgroundImageHelper();
        boolean supportBackgroundChange();
        DataProvider getDataProvider();
        PresenterSelector createMainRowPresenterSelector();
        Presenter createMainRowPresenter();
        AbstractArrayObjectAdapter createMainArrayObjectAdapter(Presenter presenter);
        AbstractArrayObjectAdapter createMainArrayObjectAdapter(PresenterSelector presenter);
        PresenterSelector getRowItemPresenterSelector(IHeaderItem headerItem);
        ArrayObjectAdapter getRowArrayObjectAdapter(IHeaderItem headerItem,Presenter presenter);
        ArrayObjectAdapter getRowArrayObjectAdapter(IHeaderItem headerItem,PresenterSelector presenterSelector);
        HeaderItem getHeaderItem(int i,IHeaderItem headerItem, String name);
        Row getListRow(IHeaderItem headerItem,HeaderItem header, ArrayObjectAdapter listRowAdapter);
        Class getSearchActivity();
        String getFirstTimeLoaderMessage();
        void onItemClicked(Presenter.ViewHolder itemViewHolder, Object item,RowPresenter.ViewHolder rowViewHolder, Row row);
        void onItemSelected(Presenter.ViewHolder itemViewHolder, Object item,RowPresenter.ViewHolder rowViewHolder, Row row);
        boolean getSupportRowProgressBar();
        boolean supportAutoPageLoader();
    }
}
