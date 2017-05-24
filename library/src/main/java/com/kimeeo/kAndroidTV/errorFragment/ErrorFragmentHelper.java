package com.kimeeo.kAndroidTV.errorFragment;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;
import android.view.View;
import android.support.v17.leanback.app.ErrorFragment;

import com.kimeeo.kAndroidTV.R;

/**
 * Created by BhavinPadhiyar on 5/24/17.
 */

public class ErrorFragmentHelper {

    private final Context context;
    private String title;
    private String message;
    private Drawable imageDrawable;
    private String buttonText;
    private View.OnClickListener onClickListener;

    public ErrorFragmentHelper(Context context)
    {
        this.context=context;
    }

    public ErrorFragmentHelper titleRes(@StringRes int titleRes)
    {
        if(titleRes!=-1)
            try {
                this.title=context.getString(titleRes);
            }catch (Exception e){}

        return this;
    }

    public ErrorFragmentHelper title(String title)
    {
        this.title=title;
        return this;
    }

    public ErrorFragmentHelper messageRes(@StringRes int value)
    {
        if(value!=-1)
            try {
                this.message=context.getString(value);
            }catch (Exception e){}

        return this;
    }

    public ErrorFragmentHelper message(String value)
    {
        this.message=value;
        return this;
    }

    public ErrorFragmentHelper buttonTextRes(@StringRes int value)
    {
        if(value!=-1)
            try {
                this.buttonText=context.getString(value);
            }catch (Exception e){}

        return this;
    }

    public ErrorFragmentHelper buttonText(String value)
    {
        this.buttonText=value;
        return this;
    }


    public ErrorFragmentHelper imageDrawableRes(@DrawableRes int value)
    {
        if(value!=-1)
            try {
                this.imageDrawable=context.getDrawable(value);
            }catch (Exception e){}

        return this;
    }

    public ErrorFragmentHelper imageDrawable(Drawable value)
    {
        this.imageDrawable=value;
        return this;
    }

    public ErrorFragmentHelper onClickListener(View.OnClickListener value)
    {
        this.onClickListener=value;
        return this;
    }


    public ErrorFragment build(String title,String message,String buttonText,Drawable imageDrawable,View.OnClickListener onClickListener)
    {
        ErrorFragment errorFragment = createErrorFragment();
        if(title!=null)
            errorFragment.setTitle(title);

        if(message!=null)
            errorFragment.setMessage(message);

        if(imageDrawable!=null)
            errorFragment.setImageDrawable(imageDrawable);


        if(buttonText!=null)
            errorFragment.setButtonText(buttonText);

        if(onClickListener!=null)
            errorFragment.setButtonClickListener(onClickListener);

        return errorFragment;
    }

    public ErrorFragment build(int title,int message,int buttonText,int imageDrawable,View.OnClickListener onClickListener)
    {
        ErrorFragment errorFragment = createErrorFragment();
        if(title!=-1)
            try {
                errorFragment.setTitle(context.getString(title));
            }catch (Exception e){}


        if(message!=-1)
            try {
                errorFragment.setMessage(context.getString(message));
            }catch (Exception e){}

        if(buttonText!=-1)
            try {
                errorFragment.setButtonText(context.getString(buttonText));
            }catch (Exception e){}


        if(imageDrawable!=-1)
            try {
                errorFragment.setImageDrawable(context.getDrawable(imageDrawable));
            }catch (Exception e){}


        if(onClickListener!=null)
            errorFragment.setButtonClickListener(onClickListener);

        return errorFragment;
    }


    public ErrorFragment build()
    {
        ErrorFragment errorFragment = createErrorFragment();
        if(title!=null)
            errorFragment.setTitle(title);

        if(message!=null)
            errorFragment.setMessage(message);

        if(imageDrawable!=null)
            errorFragment.setImageDrawable(imageDrawable);


        if(buttonText!=null)
            errorFragment.setButtonText(buttonText);

        if(onClickListener!=null)
            errorFragment.setButtonClickListener(onClickListener);

        return errorFragment;
    }

    protected ErrorFragment createErrorFragment() {
        return new ErrorFragment();
    }
}
