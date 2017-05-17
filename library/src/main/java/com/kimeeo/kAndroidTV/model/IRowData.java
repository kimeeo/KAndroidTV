package com.kimeeo.kAndroidTV.model;

/**
 * Created by BhavinPadhiyar on 5/16/17.
 */

public interface IRowData extends IDataProvider {
    public static final int TYPE_DEFAULT = 0;
    public static final int TYPE_SECTION_HEADER = 1;
    public static final int TYPE_DIVIDER = 2;

    void setID(int id);
    int getID();

    void setType(int type);
    int getType();

    void setTitle(String title);
    String getTitle();

    void setShadow(boolean shadow);
    boolean getShadow();
}
