package com.kimeeo.kAndroidTV.recommendationBuilder;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.util.DisplayMetrics;
import android.util.Log;

import com.kimeeo.kAndroidTV.R;

import java.io.InputStream;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static android.R.attr.id;
import static android.R.attr.priority;

/**
 * Created by BhavinPadhiyar on 5/29/17.
 */

public class RecommendationHelper {

    private static final String NOTIFICATION_ID = "notificationID";
    private static final String RECOMMENDATION_ID = "recommendationID";
    private static final String RECOMMENDATION_DATA = "recommendationData";
    private static final String RECOMMENDATION_DATA_STRING = "recommendationDataString";

    private Context mContext;
    private NotificationManager mNotificationManager;
    private Class mResponseActivityClass;
    private List<IRecommendation> recommendationList =new ArrayList<>();
    private int fastLaneColorRes= R.color.fastlane_background;

    private int backgroundWidth= 1920;
    private int backgroundHeight= 1080;

    private int cardWidth= 500;
    private int cardHeight= 500;
    private int icon;

    public RecommendationHelper(Activity context, Class responseActivityClass) {
        mContext = context;
        DisplayMetrics mMetrics = new DisplayMetrics();
        context.getWindowManager().getDefaultDisplay().getMetrics(mMetrics);
        backgroundWidth = mMetrics.widthPixels;
        backgroundHeight = mMetrics.heightPixels;

        this.mResponseActivityClass=responseActivityClass;
        if (mNotificationManager == null) {
            mNotificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
        }
    }

    public RecommendationHelper(Context context, Class responseActivityClass) {
        mContext = context;
        this.mResponseActivityClass=responseActivityClass;
        if (mNotificationManager == null) {
            mNotificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
        }
    }



    public RecommendationHelper fastLaneColorRes(@ColorRes int fastLaneColor) {
        this.fastLaneColorRes = fastLaneColor;
        return this;
    }

    public RecommendationHelper cardWidth(int cardWidth) {
        this.cardWidth = cardWidth;
        return this;
    }

    public RecommendationHelper cardHeight(int cardHeight) {
        this.cardHeight = cardHeight;
        return this;
    }

    public RecommendationHelper icon(@DrawableRes int icon) {
        this.icon = icon;
        return this;
    }



    public IRecommendation getRecommendation(int index)
    {
        return recommendationList.get(index);
    }

    public RecommendationHelper addRecommendation(IRecommendation recommendation)
    {
        recommendationList.add(recommendation);
        return this;
    }


    public RecommendationHelper addRecommendation(int id,String title,String imageUrl,String description)
    {
        IRecommendation recommendation = new Recommendation(id, title, imageUrl,description);
        recommendationList.add(recommendation);
        return this;
    }
    public RecommendationHelper addRecommendation(int id, String title, Bitmap image, String description)
    {
        IRecommendation recommendation = new Recommendation(id, title, image,description);
        recommendationList.add(recommendation);
        return this;
    }

    public RecommendationHelper addRecommendation(int id, String title, String imageUrl)
    {
        IRecommendation recommendation = new Recommendation(id, title, imageUrl,"");
        recommendationList.add(recommendation);
        return this;
    }
    public RecommendationHelper addRecommendation(int id, String title, Bitmap image)
    {
        IRecommendation recommendation = new Recommendation(id, title, image,"");
        recommendationList.add(recommendation);
        return this;
    }

    public RecommendationHelper addRecommendation(String title, String imageUrl)
    {
        IRecommendation recommendation = new Recommendation(nextID(), title, imageUrl,"");
        recommendationList.add(recommendation);
        return this;
    }
    public RecommendationHelper addRecommendation(String title, Bitmap image)
    {
        IRecommendation recommendation = new Recommendation(nextID(), title, image,"");
        recommendationList.add(recommendation);
        return this;
    }

    public void recommendAll() {
        recommendAll(recommendationList);
    }
    public void recommendAll(List<IRecommendation> recommendationList) {
        for (int i = 0; i < recommendationList.size(); i++) {
            recommend(recommendationList.get(i));
        }
    }

    public void recommend( IRecommendation recommendation) {
        if(recommendation.getImage()!=null && recommendation.getBackgroundBitmap()!=null)
            notifyRecommendation(recommendation);
        else
            loadImagesTask(recommendation,recommendation.getImage()==null,recommendation.getBackgroundBitmap()==null);
    }

    private void loadImagesTask(final IRecommendation recommendation,final boolean loadCardImage,final boolean loadBackgroundImage) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                if(loadBackgroundImage)
                {
                    Bitmap backgroundBitmap = prepareBitmap(recommendation.getBackgroundURL(), backgroundWidth, backgroundHeight);
                    recommendation.setBackgroundBitmap(backgroundBitmap);
                }
                if(loadCardImage) {
                    int cardWidth=RecommendationHelper.this.cardWidth;
                    int cardHeight=RecommendationHelper.this.cardHeight;
                    if(recommendation instanceof IAdvanceRecommendation && ((IAdvanceRecommendation)recommendation).useCustomHeight())
                    {
                        cardWidth=((IAdvanceRecommendation)recommendation).getCardWidth();
                        cardHeight=((IAdvanceRecommendation)recommendation).getCardHeight();
                    }
                    Bitmap cardImageBitmap = prepareBitmap(recommendation.getImageUrl(), cardWidth, cardHeight);
                    recommendation.setImage(cardImageBitmap);
                }
                notifyRecommendation(recommendation);
            }}).start();
    }

    protected void notifyRecommendation(IRecommendation recommendation) {
        PendingIntent intent = buildPendingIntent(recommendation);

        RecommendationBuilder recommendationBuilder = new RecommendationBuilder(mContext)
                .setId(recommendation.getId())
                .setPriority(priority)
                .setTitle(recommendation.getTitle())
                .setDescription(recommendation.getDescription())
                .setIntent(intent)
                .setBitmap(recommendation.getImage());

        if(recommendation.getBackgroundBitmap()!=null)
            recommendationBuilder.setBackground(recommendation.getBackgroundBitmap());

        if(recommendation instanceof IAdvanceRecommendation && ((IAdvanceRecommendation)recommendation).getFastLaneColorRes()>0)
            recommendationBuilder.setFastLaneColor(((IAdvanceRecommendation)recommendation).getFastLaneColorRes());
        else
            recommendationBuilder.setFastLaneColor(fastLaneColorRes);


        if(recommendation instanceof IAdvanceRecommendation && ((IAdvanceRecommendation)recommendation).getIcon()>0)
            recommendationBuilder.setSmallIcon(((IAdvanceRecommendation)recommendation).getIcon());
        else
            recommendationBuilder.setSmallIcon(icon);


        Notification recommendNotification = recommendationBuilder.build();
        mNotificationManager.notify(recommendation.getId(), recommendNotification);
    }

    private int nextID() {
        long range = 1234567L;
        Random r = new Random();
        int number = (int)(r.nextInt()*range);
        return number;
    }


    private PendingIntent buildPendingIntent(IRecommendation recommendation) {
        Intent detailsIntent;
        if(recommendation instanceof IAdvanceRecommendation && ((IAdvanceRecommendation)recommendation).getAcitivtyClass()!=null)
            detailsIntent= new Intent(mContext, ((IAdvanceRecommendation)recommendation).getAcitivtyClass());
        else
            detailsIntent= new Intent(mContext, mResponseActivityClass);

        detailsIntent.putExtra(RECOMMENDATION_ID, recommendation.getId());

        detailsIntent.putExtra(RECOMMENDATION_DATA_STRING, recommendation.toString());
        if(recommendation instanceof Parcelable)
            detailsIntent.putExtra(RECOMMENDATION_DATA, (Parcelable)recommendation);
        else if(recommendation instanceof Serializable)
            detailsIntent.putExtra(RECOMMENDATION_DATA, (Serializable)recommendation);

        detailsIntent.putExtra(NOTIFICATION_ID, recommendation.getId());

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(mContext);
        if(recommendation instanceof IAdvanceRecommendation && ((IAdvanceRecommendation)recommendation).getAcitivtyClass()!=null)
            stackBuilder.addParentStack(((IAdvanceRecommendation)recommendation).getAcitivtyClass());
        else
            stackBuilder.addParentStack(mResponseActivityClass);

        stackBuilder.addNextIntent(detailsIntent);
        detailsIntent.setAction(Long.toString(recommendation.getId()));
        return stackBuilder.getPendingIntent(recommendation.getId(), PendingIntent.FLAG_UPDATE_CURRENT);
    }


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
}
