package com.kimeeo.kAndroidTV.browseFragment;

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
    private Timer mBackgroundTimer;
    private BackgroundManager mBackgroundManager;
    private DisplayMetrics mMetrics;
    private final Handler mHandler = new Handler();
    private static final int BACKGROUND_UPDATE_DELAY = 300;

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
        mBackgroundTimer.cancel();
    }


    protected void startBackgroundTimer() {
        if (null != mBackgroundTimer) {
            mBackgroundTimer.cancel();
        }
        mBackgroundTimer = new Timer();
        mBackgroundTimer.schedule(new UpdateBackgroundTask(), BACKGROUND_UPDATE_DELAY);
    }

    public void cancel() {
        mBackgroundTimer.cancel();
    }
    WeakReference<Object> itemHolder;
    public void start(Object item) {
        WeakReference<Object> itemHolder = new WeakReference<Object>(item);
        startBackgroundTimer();
    }

    protected class UpdateBackgroundTask extends TimerTask {

        @Override
        public void run() {
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    if(itemHolder.get()!=null)
                        updateBackground(itemHolder.get());
                }
            });

        }
    }
    public static interface OnUpdate
    {
        void updateBackground(BackgroundManager mBackgroundManager, Object item, int width, int height);
    }
}
