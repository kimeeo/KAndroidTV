package com.kimeeo.kAndroidTV.searchFragment;

import android.app.Activity;
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
import android.support.v17.leanback.widget.RowHeaderPresenter;
import android.support.v17.leanback.widget.RowPresenter;
import android.support.v17.leanback.widget.SpeechRecognitionCallback;
import android.widget.Toast;

import com.kimeeo.kAndroid.dataProvider.DataProvider;
import com.kimeeo.kAndroid.dataProvider.MonitorList;
import com.kimeeo.kAndroidTV.R;
import com.kimeeo.kAndroidTV.core.AbstractArrayObjectAdapter;
import com.kimeeo.kAndroidTV.core.BackgroundImageHelper;
import com.kimeeo.kAndroidTV.core.DefaultArrayObjectAdapter;
import com.kimeeo.kAndroidTV.core.IHeaderItem;
import com.kimeeo.kAndroidTV.core.RowBasedFragmentHelper;
import com.kimeeo.kAndroidTV.core.WatcherArrayObjectAdapter;

import java.net.URI;
import java.util.List;

/**
 * Created by BhavinPadhiyar on 5/18/17.
 */


abstract public class AbstractSearchFragment extends SearchFragment implements SearchFragment.SearchResultProvider,SpeechRecognitionCallback,BackgroundImageHelper.OnUpdate,RowBasedFragmentHelper.HelperProvider{
    private static final int REQUEST_SPEECH = 0x00000010;
    public String getQuery() {
        return mQuery;
    }
    private String mQuery;
    @Override
    public void recognizeSpeech() {
        startActivityForResult(getRecognizerIntent(), REQUEST_SPEECH);
    }

    @Override
    public ObjectAdapter getResultsAdapter() {
        return fragmentHelper.getRowsAdapter();
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
            dataProvider.reset();
            dataProvider.next();
            return true;
        }
        return false;
    }

    public int threshold() {
        return 3;
    }

    public boolean hasResults() {
        return fragmentHelper.getRowsAdapter().size() > 0;
    }
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == REQUEST_SPEECH && resultCode== Activity.RESULT_OK)
        {
            setSearchQuery(data, true);
        }
    }



    @Override
    public void onDestroy() {
        super.onDestroy();
        if (null != fragmentHelper) {
            fragmentHelper.onDestroy();
        }
    }

    public boolean supportBackgroundChange() {
        return false;
    }

    public void updateBackground(BackgroundManager mBackgroundManager, Object item, int width, int height)
    {

    }



    protected BackgroundImageHelper backgroundImageHelper;


    abstract protected @NonNull DataProvider createDataProvider();
    protected DataProvider dataProvider;
    protected void configDataManager(DataProvider dataProvider) {}
    public DataProvider getDataProvider()
    {
        return dataProvider;
    }


    public RowBasedFragmentHelper getFragmentHelper() {
        return fragmentHelper;
    }

    RowBasedFragmentHelper fragmentHelper;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupUIElements();
        setSearchResultProvider(this);
        fragmentHelper = createBrowseFragmentHelper();
        dataProvider=createDataProvider();
        configDataManager(dataProvider);
        fragmentHelper.build();

        if(supportBackgroundChange())
            backgroundImageHelper=getBackgroundImageHelper();
    }

    protected RowBasedFragmentHelper createBrowseFragmentHelper() {
        return new SearchRowBasedFragmentHelper(this,this);
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

    abstract public PresenterSelector getRowItemPresenterSelector(IHeaderItem headerItem);
    public Row getListRow(IHeaderItem headerItem,HeaderItem header, ArrayObjectAdapter listRowAdapter) {
        return new ListRow(header, listRowAdapter);
    }

    public HeaderItem getHeaderItem(int i,IHeaderItem headerItem, String name)
    {
        return new HeaderItem(i,name);
    }
    public ArrayObjectAdapter getRowArrayObjectAdapter(IHeaderItem headerItem,Presenter presenter) {
        return new ArrayObjectAdapter(presenter);
    }
    public ArrayObjectAdapter getRowArrayObjectAdapter(IHeaderItem headerItem,PresenterSelector presenterSelector) {
        return new WatcherArrayObjectAdapter(presenterSelector);
    }
    final public Class getSearchActivity() {
        return null;
    }
    public void onItemSelected(Presenter.ViewHolder itemViewHolder, Object item,RowPresenter.ViewHolder rowViewHolder, Row row)
    {
        if(supportBackgroundChange())
            backgroundImageHelper.start(item);
    }
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
    }
    @Override
    public String getFirstTimeLoaderMessage() {
        return null;
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


    protected String getEmptySearchMessage() {
        return null;
    }

    public class SearchRowBasedFragmentHelper extends RowBasedFragmentHelper
    {

        public SearchRowBasedFragmentHelper(Fragment host, HelperProvider helperProvider) {
            super(host, helperProvider);
        }
        @Override
        public void onFetchingEnd(List<?> list, boolean b) {
            if(getEmptySearchMessage()!=null && getEmptySearchMessage().equals("")==false && dataProvider!=null && dataProvider.size()==0)
                Toast.makeText(getActivity(), getEmptySearchMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}



