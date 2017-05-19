package com.kimeeo.kAndroidTV.browseFragment;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.v17.leanback.app.BackgroundManager;
import android.support.v17.leanback.app.BrowseFragment;
import android.support.v17.leanback.widget.*;

import com.kimeeo.kAndroid.dataProvider.DataProvider;
import com.kimeeo.kAndroidTV.R;
import com.kimeeo.kAndroidTV.core.AbstractArrayObjectAdapter;
import com.kimeeo.kAndroidTV.core.BackgroundImageHelper;
import com.kimeeo.kAndroidTV.core.RowBasedFragmentHelper;
import com.kimeeo.kAndroidTV.core.DefaultArrayObjectAdapter;
import com.kimeeo.kAndroidTV.core.IHeaderItem;
import com.kimeeo.kAndroidTV.core.WatcherArrayObjectAdapter;

import java.net.URI;

/**
 * Created by BhavinPadhiyar on 5/16/17.
 */

abstract public class AbstractBrowseFragment extends BrowseFragment implements RowBasedFragmentHelper.HelperProvider, BackgroundImageHelper.OnUpdate{


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
        if (null != backgroundImageHelper) {
            backgroundImageHelper.cancel();
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

        fragmentHelper = createBrowseFragmentHelper();
        dataProvider=createDataProvider();
        configDataManager(dataProvider);
        fragmentHelper.build();
        fragmentHelper.next();

        if(supportBackgroundChange())
            backgroundImageHelper=getBackgroundImageHelper();
    }

    protected RowBasedFragmentHelper createBrowseFragmentHelper() {
        return new RowBasedFragmentHelper(this,this);
    }

    protected BackgroundImageHelper getBackgroundImageHelper() {
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
    public Class getSearchActivity() {
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

        final RowHeaderPresenter rowHeaderPresenter = createRowHeaderPresenter();
        if(rowHeaderPresenter!=null) {
            PresenterSelector presenterSelector= new PresenterSelector() {
                @Override
                public Presenter getPresenter(Object o) {
                    return rowHeaderPresenter;
                }
            };

            setHeaderPresenterSelector(presenterSelector );
        }
    }
    @Override
    public String getFirstTimeLoaderMessage() {
        return getActivity().getString(R.string._busy_message);
    }


    protected RowHeaderPresenter createRowHeaderPresenter() {
        return null;
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
}
