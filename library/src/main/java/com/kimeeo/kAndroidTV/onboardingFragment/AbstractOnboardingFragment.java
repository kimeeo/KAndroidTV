package com.kimeeo.kAndroidTV.onboardingFragment;

/**
 * Created by BhavinPadhiyar on 5/20/17.
 */

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.kimeeo.kAndroidTV.R;

import java.util.ArrayList;
import java.util.List;

abstract public class AbstractOnboardingFragment extends android.support.v17.leanback.app.OnboardingFragment {
    public static final String COMPLETED_ONBOARDING = "completed_onboarding";


    private List<Integer> pageTitles;
    private List<Integer> pageDescriptions;
    private List<Integer> pageImages;
    private static final long ANIMATION_DURATION = 500;
    private Animator mContentAnimator;
    private ImageView mContentView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =super.onCreateView(inflater, container, savedInstanceState);
        if (setLogoRes() != -1)
            setLogoResourceId(setLogoRes());

        pageTitles = createPageTitles();
        pageDescriptions = createPageDescriptions();
        pageImages = createPageImages();
        return view;
    }

    abstract protected List<Integer> createPageTitles();

    abstract protected List<Integer> createPageDescriptions();

    abstract protected List<Integer> createPageImages();


    @DrawableRes
    protected int setLogoRes() {
        return -1;
    }

    @Override
    protected void onFinishFragment() {
        super.onFinishFragment();
        SharedPreferences.Editor sharedPreferencesEditor = PreferenceManager.getDefaultSharedPreferences(getActivity()).edit();
        sharedPreferencesEditor.putBoolean(COMPLETED_ONBOARDING, true);
        sharedPreferencesEditor.apply();
        getActivity().finish();
    }

    @Override
    protected int getPageCount() {
        return getTotalCount();
    }

    abstract protected int getTotalCount();

    @Override
    protected String getPageTitle(int pageIndex) {
        return getString(pageTitles.get(pageIndex));
    }

    @Override
    protected String getPageDescription(int pageIndex) {
        return getString(pageDescriptions.get(pageIndex));
    }

    @Nullable
    @Override
    protected View onCreateBackgroundView(LayoutInflater inflater, ViewGroup container) {
        View bgView = new View(getActivity());
        if (getBackgroundColorRes() != -1)
            bgView.setBackgroundColor(getResources().getColor(getBackgroundColorRes()));
        else if (getBackgroundColor() != -1)
            bgView.setBackgroundColor(getBackgroundColor());
        return bgView;
    }

    protected int getBackgroundColor() {
        return -1;
    }

    protected int getBackgroundColorRes() {
        return -1;
    }

    @Nullable
    @Override
    protected View onCreateContentView(LayoutInflater inflater, ViewGroup container) {
        mContentView = new ImageView(getActivity());
        mContentView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        mContentView.setPadding(0, 32, 0, 32);
        return mContentView;
    }

    @Nullable
    @Override
    protected View onCreateForegroundView(LayoutInflater inflater, ViewGroup container) {
        return null;
    }

    @Override
    protected void onPageChanged(final int newPage, int previousPage) {
        if (mContentAnimator != null) {
            mContentAnimator.end();
        }
        ArrayList<Animator> animators = new ArrayList<>();
        Animator fadeOut = createFadeOutAnimator(mContentView);

        fadeOut.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mContentView.setImageDrawable(getResources().getDrawable(pageImages.get(newPage)));
                ((AnimationDrawable) mContentView.getDrawable()).start();
            }
        });
        animators.add(fadeOut);
        animators.add(createFadeInAnimator(mContentView));
        AnimatorSet set = new AnimatorSet();
        set.playSequentially(animators);
        set.start();
        mContentAnimator = set;
    }

    @Override
    protected Animator onCreateEnterAnimation()
    {
        mContentView.setImageDrawable(getResources().getDrawable(pageImages.get(0)));
        ((AnimationDrawable) mContentView.getDrawable()).start();
        mContentAnimator = createFadeInAnimator(mContentView);
        return mContentAnimator;
    }

    protected Animator createFadeInAnimator(View view) {
        return ObjectAnimator.ofFloat(view, View.ALPHA, 0.0f, 1.0f).setDuration(ANIMATION_DURATION);
    }

    protected Animator createFadeOutAnimator(View view) {
        return ObjectAnimator.ofFloat(view, View.ALPHA, 1.0f, 0.0f).setDuration(ANIMATION_DURATION);
    }
}