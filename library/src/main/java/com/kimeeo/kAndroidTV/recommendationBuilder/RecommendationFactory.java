package com.kimeeo.kAndroidTV.recommendationBuilder;


import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Parcelable;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.kimeeo.kAndroidTV.R;

import java.io.InputStream;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;

public class RecommendationFactory {

    private static final String TAG = RecommendationFactory.class.getSimpleName();
    private static final String NOTIFICATION_ID = "notificationID";
    private static final String RECOMMENDATION_ID = "recommendationID";
    private static final String RECOMMENDATION_DATA = "recommendationData";
    private static final String RECOMMENDATION_DATA_STRING = "recommendationDataString";


    private Context mContext;
    private NotificationManager mNotificationManager;

    private Class mResponseActivityClass;
    private int fastLaneColorRes= R.color.fastlane_background;
    private int backgroundWidth= 1920;
    private int backgroundHeight= 1080;
    private int cardWidth= 500;
    private int cardHeight= 500;

    public RecommendationFactory(Context context, Class responseActivityClass) {
        mContext = context;
        this.mResponseActivityClass=responseActivityClass;
        if (mNotificationManager == null) {
            mNotificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
        }
    }

    public void recommend(final int id, final IRecommendation recommendation, final int priority,final int smallIcon) {
        Log.i(TAG, "recommend");
        new Thread(new Runnable() {
            @Override
            public void run() {

                Bitmap backgroundBitmap = prepareBitmap(recommendation.getImageUrl(), getBackgroundWidth(), getBackgroundHeight());
                Bitmap cardImageBitmap = prepareBitmap(recommendation.getImageUrl(), getCardWidth(), getCardHeight());

                PendingIntent intent = buildPendingIntent(recommendation, id);

                RecommendationBuilder recommendationBuilder = new RecommendationBuilder(mContext)
                        .setBackground(backgroundBitmap)
                        .setId(id)
                        .setPriority(priority)
                        .setTitle(recommendation.getTitle())
                        .setDescription(recommendation.getDescription())
                        .setIntent(intent)
                        .setSmallIcon(smallIcon)
                        .setFastLaneColor(getFastLaneColorRes())
                        .setBitmap(cardImageBitmap);



                Notification recommendNotification = recommendationBuilder.build();
                mNotificationManager.notify(id, recommendNotification);
            }}).start();
    }


    /**
     * prepare bitmap from URL string
     * @param url
     * @return
     */

    private Bitmap prepareBitmap(String url, int width, int height) {

        Bitmap bmp =null;
        try{
            URL ulrn = new URL(url);
            HttpURLConnection con = (HttpURLConnection)ulrn.openConnection();
            InputStream is = con.getInputStream();
            bmp = BitmapFactory.decodeStream(is);
            if (null != bmp) {
                bmp.setHeight(height);
                bmp.setWidth(width);
                return bmp;
            }

        }catch(Exception e){}
        return bmp;
    }


    private PendingIntent buildPendingIntent(IRecommendation recommendation, int id) {
        Intent detailsIntent = new Intent(mContext, mResponseActivityClass);
        detailsIntent.putExtra(RECOMMENDATION_ID, recommendation.getId());

        detailsIntent.putExtra(RECOMMENDATION_DATA_STRING, recommendation.toString());
        if(recommendation instanceof Parcelable)
            detailsIntent.putExtra(RECOMMENDATION_DATA, (Parcelable)recommendation);
        else if(recommendation instanceof Serializable)
            detailsIntent.putExtra(RECOMMENDATION_DATA, (Serializable)recommendation);

        detailsIntent.putExtra(NOTIFICATION_ID, id);

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(mContext);
        stackBuilder.addParentStack(mResponseActivityClass);
        stackBuilder.addNextIntent(detailsIntent);
        detailsIntent.setAction(Long.toString(recommendation.getId()));
        return stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
    }


    public int getFastLaneColorRes() {
        return fastLaneColorRes;
    }

    public void setFastLaneColorRes(@ColorRes int fastLaneColor) {
        this.fastLaneColorRes = fastLaneColor;
    }

    public int getBackgroundWidth() {
        return backgroundWidth;
    }

    public void setBackgroundWidth(int backgroundWidth) {
        this.backgroundWidth = backgroundWidth;
    }

    public int getBackgroundHeight() {
        return backgroundHeight;
    }

    public void setBackgroundHeight(int backgroundHeight) {
        this.backgroundHeight = backgroundHeight;
    }

    public int getCardWidth() {
        return cardWidth;
    }

    public void setCardWidth(int cardWidth) {
        this.cardWidth = cardWidth;
    }

    public int getCardHeight() {
        return cardHeight;
    }

    public void setCardHeight(int cardHeight) {
        this.cardHeight = cardHeight;
    }
}