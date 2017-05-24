package com.kimeeo.kAndroidTV.core;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.support.v17.leanback.app.BackgroundManager;
import android.util.DisplayMetrics;

import com.kimeeo.kAndroidTV.R;

import java.lang.ref.WeakReference;
import java.net.URI;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by BhavinPadhiyar on 5/17/17.
 */

public class BackgroundImageHelper {

    private final OnUpdate onUpdate;
    private BackgroundManager mBackgroundManager;
    private DisplayMetrics mMetrics;
    private final Handler mHandler = new Handler();
    private long backgroundUpdateDelay=1000;

    public BackgroundImageHelper(Activity activity,OnUpdate onUpdate) {
        prepareBackgroundManager(activity);
        this.onUpdate=onUpdate;
    }

    protected void prepareBackgroundManager(Activity activity) {

        mBackgroundManager = BackgroundManager.getInstance(activity);
        mBackgroundManager.attach(activity.getWindow());
        mMetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(mMetrics);
    }
    protected void updateBackground(Object item) {
        int width = mMetrics.widthPixels;
        int height = mMetrics.heightPixels;
        if(onUpdate!=null)
            onUpdate.updateBackground(mBackgroundManager,item,width,height);
    }

    Runnable runnable= new Runnable() {
        @Override
        public void run() {
            if(itemHolder!=null && itemHolder.get()!=null)
                updateBackground(itemHolder.get());
        }
    };

    protected void startBackgroundTimer() {
        if (mHandler != null)
            mHandler.removeCallbacks(runnable);
        mHandler.postDelayed(runnable, getBackgroundUpdateDelay());
    }

    public void cancel() {
        if (mHandler != null)
            mHandler.removeCallbacks(runnable);
    }
    WeakReference<Object> itemHolder;
    public void start(Object item) {

        itemHolder = new WeakReference<Object>(item);
        startBackgroundTimer();
    }

    public void setDrawable(Drawable o) {
        mBackgroundManager.setDrawable(o);
    }

    public long getBackgroundUpdateDelay() {
        return backgroundUpdateDelay;
    }

    public void setBackgroundUpdateDelay(long backgroundUpdateDelay) {
        this.backgroundUpdateDelay = backgroundUpdateDelay;
    }

    public BackgroundManager getBackgroundManager() {
        return mBackgroundManager;
    }

    public static interface OnUpdate
    {
        void updateBackground(BackgroundManager mBackgroundManager, Object item, int width, int height);
    }
}
