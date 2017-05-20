package com.kimeeo.kAndroidTV.Demo.fragments;

import com.kimeeo.kAndroidTV.Demo.R;
import com.kimeeo.kAndroidTV.onboardingFragment.AbstractOnboardingFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by BhavinPadhiyar on 5/20/17.
 */

public class OnboardingFragment extends AbstractOnboardingFragment {
    @Override
    protected List<Integer> createPageTitles() {
        List<Integer> list = new ArrayList<>();
        list.add(R.string._busy_message);
        list.add(R.string._busy_message);
        list.add(R.string._busy_message);
        return list;
    }

    @Override
    protected List<Integer> createPageDescriptions() {
        List<Integer> list = new ArrayList<>();
        list.add(R.string._busy_message);
        list.add(R.string._busy_message);
        list.add(R.string._busy_message);
        return list;
    }

    @Override
    protected List<Integer> createPageImages() {
        List<Integer> list = new ArrayList<>();
        list.add(R.drawable.image);
        list.add(R.drawable.image);
        return list;
    }

    @Override
    protected int getTotalCount() {
        return 3;
    }
}
