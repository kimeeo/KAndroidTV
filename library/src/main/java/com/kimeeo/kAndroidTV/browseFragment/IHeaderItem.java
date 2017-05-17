package com.kimeeo.kAndroidTV.browseFragment;

import java.util.List;

/**
 * Created by BhavinPadhiyar on 5/17/17.
 */

public interface IHeaderItem
{
    String getID();
    String getName();
    List<Object> getData();
}
