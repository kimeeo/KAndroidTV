package com.kimeeo.kAndroidTV.pageFragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v17.leanback.app.BackgroundManager;
import android.support.v17.leanback.app.BrowseFragment;
import android.support.v17.leanback.widget.ArrayObjectAdapter;
import android.support.v17.leanback.widget.HeaderItem;
import android.support.v17.leanback.widget.ListRow;
import android.support.v17.leanback.widget.Presenter;
import android.support.v17.leanback.widget.PresenterSelector;
import android.support.v17.leanback.widget.Row;
import android.support.v17.leanback.widget.RowPresenter;
import android.webkit.WebViewFragment;

import com.kimeeo.kAndroid.dataProvider.DataProvider;
import com.kimeeo.kAndroidTV.browseFragment.AbstractBrowseFragment;
import com.kimeeo.kAndroidTV.browseFragment.BackgroundImageHelper;
import com.kimeeo.kAndroidTV.browseFragment.IHeaderItem;
import com.kimeeo.kAndroidTV.browseFragment.WatcherArrayObjectAdapter;

import java.util.List;

/**
 * Created by BhavinPadhiyar on 5/18/17.
 */

abstract public class AbstractPageFragment extends AbstractBrowseFragment {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getMainFragmentRegistry().registerFragment(getPageRowClass(),createPageRowFragmentFactory());
    }

    final protected PresenterSelector createListRowPresenterSelector() {
        return null;
    }

    protected FragmentFactory createPageRowFragmentFactory() {
        return new PageRowFragmentFactory(backgroundImageHelper);
    }
    abstract protected Fragment getFragmentForRow(Object rowObj);

    @Override
    public void itemsAdded(int index, List list) {
        for (int i = 0; i < list.size(); i++) {
            if(list.get(i) instanceof IHeaderItem)
            {
                IHeaderItem headerItem= (IHeaderItem)list.get(i);
                HeaderItem header = getHeaderItem(i,headerItem, headerItem.getName());
                PageRow pageRow = new PageRow(header,headerItem);
                int row=index+i;
                mRowsAdapter.add(row,pageRow);
            }
        }
    }

    protected Class getPageRowClass() {
        return PageRow.class;
    }

    public class PageRowFragmentFactory extends BrowseFragment.FragmentFactory {
        private final BackgroundImageHelper backgroundImageHelper;

        PageRowFragmentFactory(BackgroundImageHelper backgroundImageHelper) {
            this.backgroundImageHelper = backgroundImageHelper;
        }

        @Override
        public Fragment createFragment(Object rowObj) {
            if(backgroundImageHelper!=null)
                backgroundImageHelper.setDrawable(null);
            return getFragmentForRow(rowObj);
        }
    }
    @Override
    protected void handelPaging(Presenter.ViewHolder itemViewHolder, Object item, RowPresenter.ViewHolder rowViewHolder, Row row) {
        DataProvider dataProvider = getDataProvider();
        if(dataProvider!=null)
        {
            if(dataProvider.getNextEnabled() && dataProvider.getCanLoadNext())
            {

            }
        }
    }

    final protected Row getListRow(IHeaderItem headerItem,HeaderItem header, ArrayObjectAdapter listRowAdapter) {
        return null;
    }
    final protected PresenterSelector getPresenterSelector(IHeaderItem headerItem)
    {
        return null;
    }
}
