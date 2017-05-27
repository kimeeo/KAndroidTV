package com.kimeeo.kAndroidTV.onboardingFragment;

import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by BhavinPadhiyar on 5/27/17.
 */

public class OnboardingFragment extends AbstractOnboardingFragment {
    private int[] pageImagesRes;

    @Override
    protected List<String> createPageTitles() {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < pageTitles.length; i++) {
            list.add(pageTitles[i]);
        }
        return list;
    }

    @Override
    protected List<String> createPageDescriptions() {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < pageDescriptions.length; i++) {
            list.add(pageDescriptions[i]);
        }
        return list;
    }

    @Override
    protected List<Integer> createPageImages() {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < pageImagesRes.length; i++) {
            list.add(pageImagesRes[i]);
        }
        return list;
    }


    public OnFinish getOnFinish() {
        return onFinish;
    }

    public void setOnFinish(OnFinish onFinish) {
        this.onFinish = onFinish;
    }

    OnFinish onFinish;
    @Override
    protected void finish() {
        if(onFinish!=null)
            onFinish.onFinish();
    }




    private String[] pageTitles;
    public void setPageTitles(String[] pageTitles) {
        this.pageTitles = pageTitles;
    }
    public String[] getPageTitles() {
        return pageTitles;
    }


    private String[] pageDescriptions;
    public void setPageDescriptions(String[] pageDescriptions) {
        this.pageDescriptions = pageDescriptions;
    }

    public String[] getPageDescriptions() {
        return pageDescriptions;
    }

    public void setPageImagesRes(int[] pageImagesRes) {
        this.pageImagesRes = pageImagesRes;
    }

    public int[] getPageImagesRes() {
        return pageImagesRes;
    }

    public interface OnFinish
    {
        void onFinish();
    }
}