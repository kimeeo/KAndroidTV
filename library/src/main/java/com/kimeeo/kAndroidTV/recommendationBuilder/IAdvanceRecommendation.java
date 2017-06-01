package com.kimeeo.kAndroidTV.recommendationBuilder;

import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;

/**
 * Created by BhavinPadhiyar on 5/29/17.
 */

public interface IAdvanceRecommendation extends IRecommendation {
    Class getAcitivtyClass();

    int getCardWidth();
    int getCardHeight();
    boolean useCustomHeight();

    @DrawableRes
    int getIcon();

    @ColorRes
    int getFastLaneColorRes();
}
