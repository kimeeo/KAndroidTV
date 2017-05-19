package com.kimeeo.kAndroidTV.pageFragments;

import android.app.Fragment;

/**
 * Created by BhavinPadhiyar on 5/19/17.
 */

public interface FragmentProvider {
    Fragment getFragmentForItem(Object rowObj);
}
