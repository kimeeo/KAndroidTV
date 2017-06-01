package com.kimeeo.kAndroidTV.recommendationBuilder;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by BhavinPadhiyar on 5/29/17.
 */

public class Recommendation implements IRecommendation, Parcelable {
    private Bitmap image;
    private int id;
    private String title;
    private String backgroundURL;
    private String imageUrl;
    private String description;
    private Bitmap backgroundBitmap;

    public Recommendation(int id, String title, String imageUrl, String description)
    {
        this.id=id;
        this.title=title;
        this.imageUrl=imageUrl;
        this.description=description;
    }
    public Recommendation(int id, String title, Bitmap image, String description)
    {
        this.id=id;
        this.title=title;
        this.image=image;
        this.description=description;
    }
    @Override
    public String getImageUrl() {
        return imageUrl;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public int getId() {
        return id;
    }
    @Override
    public Bitmap getImage() {
        return image;
    }

    @Override
    public String getBackgroundURL() {
        return backgroundURL;
    }

    @Override
    public void setImage(Bitmap bitmap)
    {
        image=bitmap;
    }

    @Override
    public void setBackgroundBitmap(Bitmap backgroundBitmap) {
        this.backgroundBitmap=backgroundBitmap;
    }

    @Override
    public void setBackgroundURL(String backgroundURL) {
        this.backgroundURL=backgroundURL;
    }

    @Override
    public Bitmap getBackgroundBitmap() {
        return backgroundBitmap;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.image, flags);
        dest.writeInt(this.id);
        dest.writeString(this.title);
        dest.writeString(this.imageUrl);
        dest.writeString(this.description);
        dest.writeString(this.backgroundURL);
    }

    protected Recommendation(Parcel in) {
        this.image = in.readParcelable(Bitmap.class.getClassLoader());
        this.id = in.readInt();
        this.title = in.readString();
        this.imageUrl = in.readString();
        this.description = in.readString();
        this.backgroundURL = in.readString();
    }

    public static final Parcelable.Creator<Recommendation> CREATOR = new Parcelable.Creator<Recommendation>() {
        @Override
        public Recommendation createFromParcel(Parcel source) {
            return new Recommendation(source);
        }

        @Override
        public Recommendation[] newArray(int size) {
            return new Recommendation[size];
        }
    };
}
