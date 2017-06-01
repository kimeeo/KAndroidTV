package com.kimeeo.kAndroidTV.recommendationBuilder;

import android.graphics.Bitmap;

/**
 * Created by BhavinPadhiyar on 5/28/17.
 */

public interface IRecommendation {
    String getImageUrl();
    String getTitle();
    String getDescription();
    Bitmap getImage();
    String getBackgroundURL();
    void setBackgroundURL(String backgroundURL);

    int getId();
    void setImage(Bitmap bitmap);
    Bitmap getBackgroundBitmap();
    void setBackgroundBitmap(Bitmap backgroundBitmap);

}
