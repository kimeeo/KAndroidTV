package com.kimeeo.kAndroidTV.recommendationBuilder;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.support.annotation.ColorRes;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;


/**
 * Created by BhavinPadhiyar on 5/28/17.
 */


import com.kimeeo.kAndroidTV.R;

/*
 * This class builds recommendations as notifications with videos as inputs.
 */
public class RecommendationBuilder {

    private static final String TAG = RecommendationBuilder.class.getSimpleName();
    private static final String BACKGROUND_URI_PREFIX = "content://com.kimeeo.kAndroidTV.recommendationBuilder/";

    private Context mContext;

    private int mId;
    private int mPriority;
    private int mFastLaneColor;
    private int mSmallIcon;
    private String mTitle;
    private String mDescription;
    private Bitmap mCardImageBitmap;
    private Bitmap mBackgroundBitmap;
    private String mGroupKey;
    private String mSort;
    private PendingIntent mIntent;


    public RecommendationBuilder(Context context) {
        mContext = context;
        setFastLaneColor(mContext.getResources().getColor(R.color.fastlane_background));
    }

    public RecommendationBuilder setFastLaneColor(int color) {
        mFastLaneColor = color;
        return this;
    }

    public RecommendationBuilder setId(int id) {
        mId = id;
        return this;
    }




    public RecommendationBuilder setPriority(int priority) {
        mPriority = priority;
        return this;
    }

    public RecommendationBuilder setTitle(String title) {
        mTitle = title;
        return this;
    }

    public RecommendationBuilder setDescription(String description) {
        mDescription = description;
        return this;
    }

    public RecommendationBuilder setBitmap(Bitmap bitmap) {
        mCardImageBitmap = bitmap;
        return this;
    }

    public RecommendationBuilder setBackground(Bitmap bitmap) {
        mBackgroundBitmap = bitmap;
        return this;
    }

    public RecommendationBuilder setIntent(PendingIntent intent) {
        mIntent = intent;
        return this;
    }

    public RecommendationBuilder setSmallIcon(int resourceId) {
        mSmallIcon = resourceId;
        return this;
    }

    public Notification build() {

        Bundle extras = new Bundle();
        File bitmapFile = getNotificationBackground(mContext, mId);

        mGroupKey = (mId < 3) ? "Group1" : (mId < 5) ? "Group2" : "Group3";
        mSort = (mId < 3) ? "1.0" : (mId < 5) ? "0.7" : "0.3";


        if (mBackgroundBitmap != null) {
            extras.putString(Notification.EXTRA_BACKGROUND_IMAGE_URI, Uri.parse(BACKGROUND_URI_PREFIX + Integer.toString(mId)).toString());

            try {
                bitmapFile.createNewFile();
                FileOutputStream fOut = new FileOutputStream(bitmapFile);
                mBackgroundBitmap.compress(Bitmap.CompressFormat.PNG, 85, fOut); // <- background bitmap must be created by mBackgroundUri, and not  mCardImageBitmap
                fOut.flush();
                fOut.close();
            } catch (IOException ioe) {
                Log.d(TAG, "Exception caught writing bitmap to file!", ioe);
            }
        }

        Notification notification = new NotificationCompat.BigPictureStyle(
                new NotificationCompat.Builder(mContext)
                        .setAutoCancel(true)
                        .setContentTitle(mTitle)
                        .setContentText(mDescription)
                        .setPriority(mPriority)
                        .setLocalOnly(true)
                        .setOngoing(true)
                        .setGroup(mGroupKey)
                        .setSortKey(mSort)
                        .setColor(mContext.getResources().getColor(R.color.fastlane_background))
                        .setCategory(Notification.CATEGORY_RECOMMENDATION)
                        .setLargeIcon(mCardImageBitmap)
                        .setSmallIcon(mSmallIcon)
                        .setContentIntent(mIntent)
                        .setExtras(extras))
                .build();
        return notification;
    }

    @Override
    public String toString() {
        return "RecommendationBuilder{" +
                "mId=" + mId +
                ", mPriority=" + mPriority +
                ", mSmallIcon=" + mSmallIcon +
                ", mTitle='" + mTitle + '\'' +
                ", mDescription='" + mDescription + '\'' +
                ", mCardImageBitmap='" + mCardImageBitmap + '\'' +
                ", mBackgroundBitmap='" + mBackgroundBitmap + '\'' +
                ", mIntent=" + mIntent +
                '}';
    }
    private static File getNotificationBackground(Context context, int notificationId) {
        return new File(context.getCacheDir(), "tmp" + Integer.toString(notificationId) + ".png");
    }
}