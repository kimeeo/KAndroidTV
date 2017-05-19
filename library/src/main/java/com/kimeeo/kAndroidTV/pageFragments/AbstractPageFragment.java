package com.kimeeo.kAndroidTV.pageFragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v17.leanback.widget.ArrayObjectAdapter;
import android.support.v17.leanback.widget.HeaderItem;
import android.support.v17.leanback.widget.Presenter;
import android.support.v17.leanback.widget.PresenterSelector;
import android.support.v17.leanback.widget.Row;
import android.support.v17.leanback.widget.RowPresenter;

import com.kimeeo.kAndroid.dataProvider.DataProvider;
import com.kimeeo.kAndroidTV.R;
import com.kimeeo.kAndroidTV.browseFragment.AbstractBrowseFragment;
import com.kimeeo.kAndroidTV.core.RowBasedFragmentHelper;
import com.kimeeo.kAndroidTV.core.IHeaderItem;

import java.util.List;

/**
 * Created by BhavinPadhiyar on 5/18/17.
 */

abstract public class AbstractPageFragment extends AbstractBrowseFragment implements FragmentProvider {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getMainFragmentRegistry().registerFragment(getPageRowClass(),createPageRowFragmentFactory());
    }
    protected FragmentFactory createPageRowFragmentFactory() {
        return new PageRowFragmentFactory(backgroundImageHelper,this);
    }
    protected RowBasedFragmentHelper createBrowseFragmentHelper() {
        return new PageBrowseFragmentHelper(this,this);
    }
    public static class PageBrowseFragmentHelper extends RowBasedFragmentHelper
    {

        public PageBrowseFragmentHelper(Fragment host, HelperProvider helperProvider) {
            super(host, helperProvider);
        }
        @Override
        public void itemsAdded(int index, List list) {
            for (int i = 0; i < list.size(); i++) {
                if(list.get(i) instanceof IHeaderItem)
                {
                    IHeaderItem headerItem= (IHeaderItem)list.get(i);
                    int row=index+i;
                    HeaderItem header = getHelperProvider().getHeaderItem(row,headerItem, headerItem.getName());
                    PageRow pageRow = new PageRow(header,headerItem);
                    mRowsAdapter.add(pageRow);
                }
            }
        }
        @Override
        public void handelPaging(Presenter.ViewHolder itemViewHolder, Object item, RowPresenter.ViewHolder rowViewHolder, Row row) {
            DataProvider dataProvider = getHelperProvider().getDataProvider();
            if(dataProvider!=null)
            {
                if(dataProvider.getNextEnabled() && dataProvider.getCanLoadNext())
                {

                }
            }
        }
    }
    @Override
    public String getFirstTimeLoaderMessage() {
        return null;
    }

    protected Class getPageRowClass() {
        return PageRow.class;
    }



    final public PresenterSelector createMainRowPresenterSelector() {return null;}
    final public Row getListRow(IHeaderItem headerItem,HeaderItem header, ArrayObjectAdapter listRowAdapter) {return null;}
    final public PresenterSelector getRowItemPresenterSelector(IHeaderItem headerItem){return null;}
}
