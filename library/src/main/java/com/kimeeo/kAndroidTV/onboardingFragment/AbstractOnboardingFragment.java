package com.kimeeo.kAndroidTV.onboardingFragment;

/**
 * Created by BhavinPadhiyar on 5/20/17.
 */

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.kimeeo.kAndroidTV.R;

import java.util.ArrayList;
import java.util.List;

abstract public class AbstractOnboardingFragment extends android.support.v17.leanback.app.OnboardingFragment {
    public static final String COMPLETED_ONBOARDING = "completed_onboarding";


    private List<String> pageTitlesData;
    private List<String> pageDescriptionsData;
    private List<Integer> pageImagesData;
    private Animator mContentAnimator;
    private ImageView mContentView;
    private String startButtonLabel;
    private int logoRes=-1;
    private int backgroundColor=-1;
    private int backgroundDrawablerRes=-1;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =super.onCreateView(inflater, container, savedInstanceState);
        if (getLogoRes() != -1)
            setLogoResourceId(getLogoRes());

        pageTitlesData = createPageTitles();
        pageDescriptionsData = createPageDescriptions();
        pageImagesData = createPageImages();

        Button buttonStart = (Button) view.findViewById(R.id.button_start);
        if(getStartButtonLabel()!=null && getStartButtonLabel().equals("")==false)
            buttonStart.setText(getStartButtonLabel());
        return view;
    }

    protected String getStartButtonLabel() {
        return startButtonLabel;
    }
    protected void setStartButtonLabel(String value) {
        startButtonLabel=value;
    }
    abstract protected List<String> createPageTitles();

    abstract protected List<String> createPageDescriptions();

    abstract protected List<Integer> createPageImages();


    public int getLogoRes() {
        return logoRes;
    }


    public void setLogoRes(@DrawableRes int value) {
        logoRes=value;
    }

    @Override
    protected void onFinishFragment() {
        super.onFinishFragment();
        SharedPreferences.Editor sharedPreferencesEditor = PreferenceManager.getDefaultSharedPreferences(getActivity()).edit();
        sharedPreferencesEditor.putBoolean(COMPLETED_ONBOARDING, true);
        sharedPreferencesEditor.apply();
        finish();
    }

    public static boolean isCompleted(Context context) {
        SharedPreferences sharedPreferencesEditor = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferencesEditor.getBoolean(COMPLETED_ONBOARDING, false);
    }
    public static void resetCompleted(Context context) {
        SharedPreferences.Editor sharedPreferencesEditor = PreferenceManager.getDefaultSharedPreferences(context).edit();
        sharedPreferencesEditor.putBoolean(COMPLETED_ONBOARDING, false);
        sharedPreferencesEditor.apply();
    }

    protected abstract void finish();

    @Override
    protected int getPageCount() {
        return pageTitlesData.size();
    }


    @Override
    protected String getPageTitle(int pageIndex) {
        return pageTitlesData.get(pageIndex);
    }

    @Override
    protected String getPageDescription(int pageIndex) {
        return pageDescriptionsData.get(pageIndex);
    }

    @Nullable
    @Override
    protected View onCreateBackgroundView(LayoutInflater inflater, ViewGroup container) {
        View bgView = new View(getActivity());
        if (getBackgroundDrawablerRes() != -1)
            bgView.setBackgroundResource(getBackgroundDrawablerRes());
        else if (getBackgroundColor() != -1)
            bgView.setBackgroundColor(getBackgroundColor());
        return bgView;
    }

    public int getBackgroundColor() {
        return backgroundColor;
    }
    public void setBackgroundColor(int value) {
        backgroundColor=value;
    }



    protected Drawable getBackgroundDrawable() {
        return null;
    }

    protected int getBackgroundDrawablerRes() {
        return backgroundDrawablerRes;
    }

    protected void setBackgroundDrawablerRes(@DrawableRes int value) {
        backgroundDrawablerRes=value;
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
                mContentView.setImageDrawable(getResources().getDrawable(pageImagesData.get(newPage)));
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
        mContentView.setImageDrawable(getResources().getDrawable(pageImagesData.get(0)));
        mContentAnimator = createFadeInAnimator(mContentView);
        return mContentAnimator;
    }

    protected Animator createFadeInAnimator(View view) {
        return ObjectAnimator.ofFloat(view, View.ALPHA, 0.0f, 1.0f).setDuration(getAnimationDuration());
    }

    protected Animator createFadeOutAnimator(View view) {
        return ObjectAnimator.ofFloat(view, View.ALPHA, 1.0f, 0.0f).setDuration(getAnimationDuration());
    }

    protected long getAnimationDuration() {
        return 500;
    }
}