package com.kimeeo.kAndroidTV.pageFragments;

import android.support.v17.leanback.widget.HeaderItem;

import com.kimeeo.kAndroidTV.browseFragment.IHeaderItem;

/**
 * Created by BhavinPadhiyar on 5/18/17.
 */

public class PageRow extends android.support.v17.leanback.widget.PageRow {
    public IHeaderItem getHeaderData() {
        return headerData;
    }

    private IHeaderItem headerData;
    public PageRow(HeaderItem headerItem) {
        super(headerItem);
    }
    public PageRow(HeaderItem headerItem, IHeaderItem headerData) {
        super(headerItem);
        this.headerData=headerData;
    }
}
