package com.kimeeo.kAndroidTV.recommendationBuilder;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * Created by BhavinPadhiyar on 6/11/17.
 */

abstract public class RecommendationBackgroundContentProvider extends ContentProvider {

    @Override
    public boolean onCreate() {
        return true;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        return null;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        return null;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
        /*
         * content provider serving files that are saved locally when recommendations are built
         */
    public ParcelFileDescriptor openFile(Uri uri, String mode) throws FileNotFoundException {
        int backgroundId = Integer.parseInt(uri.getLastPathSegment());
        File bitmapFile = getNotificationBackground(getContext(), backgroundId);
        return ParcelFileDescriptor.open(bitmapFile, ParcelFileDescriptor.MODE_READ_ONLY);
    }

    private static File getNotificationBackground(Context context, int notificationId) {
        return new File(context.getCacheDir(), "tmp" + Integer.toString(notificationId) + ".png");
    }
}


