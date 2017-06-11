package com.kimeeo.kAndroidTV.errorFragment;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;
import android.support.v17.leanback.app.ErrorFragment;
import android.view.View;

import com.kimeeo.kAndroidTV.R;
import com.kimeeo.kAndroidTV.onboardingFragment.AbstractOnboardingFragment;
import com.kimeeo.kAndroidTV.onboardingFragment.OnboardingFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by BhavinPadhiyar on 5/18/17.
 */

public class ErrorActivity extends Activity implements OnboardingFragment.OnFinish {

    public static final String TITLE="title";
    public static final String MESSAGE="message";
    public static final String BUTTON_LABEL="buttonLabel";
    public static final String IMAGE_RES="imageRes";
    public static final String BACKGROUND_COLOR="backgroundColor";
    public static final String BACKGROUND_DRAWABLER_RES="backgroundDrawablerRes";
    public static final String REQUEST_CODE="requestCode";
    public static final String BUTTON_CLICK="buttonClick";


    int requestCode;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout._onboarding_activity_fragment);
        if (savedInstanceState == null) {
            String title=getIntent().getExtras().getString(TITLE);
            String message=getIntent().getExtras().getString(MESSAGE);
            String buttonLabel=getIntent().getExtras().getString(BUTTON_LABEL);
            int imageRes=getIntent().getExtras().getInt(IMAGE_RES);
            int backgroundColor=getIntent().getExtras().getInt(BACKGROUND_COLOR);
            int backgroundDrawablerRes=getIntent().getExtras().getInt(BACKGROUND_DRAWABLER_RES);



            ErrorFragmentHelper helper = new ErrorFragmentHelper(this);
            helper.title(title);
            helper.message(message);
            helper.buttonText(buttonLabel);
            helper.imageDrawableRes(imageRes);
            helper.onClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    done();
                }
            });
            ErrorFragment fragment = helper.build();


            getFragmentManager().beginTransaction().replace(R.id.onboardingFragmentContainer, fragment).commit();
            try {
                if(backgroundDrawablerRes>0)
                    getWindow().setBackgroundDrawableResource(backgroundDrawablerRes);
                else
                    getWindow().setBackgroundDrawable(new ColorDrawable(backgroundColor));
            }catch (Exception e){}

            requestCode=getIntent().getExtras().getInt(REQUEST_CODE);
        }
    }

    protected void done() {
        if(requestCode!=-1)
        {
            Intent intent = new Intent();
            intent.putExtra(BUTTON_CLICK,true);
            setResult(Activity.RESULT_OK, intent);
        }
        finish();

    }
    public void onBackPressed() {
        setResult(Activity.RESULT_CANCELED);
        finish();
    }
    @Override
    public void onFinish() {
        if(requestCode!=-1)
        {
            Intent intent = new Intent();
            intent.putExtra(BUTTON_CLICK,false);
            setResult(Activity.RESULT_OK, intent);
        }
        finish();
    }


    public static class Builder
    {

        private final Activity activity;

        private String title;
        private String message;
        private String buttonLabel;
        private int imageRes;
        private int backgroundColor;
        private int backgroundDrawablerRes;
        private Class<?> errorActivityClass=ErrorActivity.class;


        public Builder(Activity activity)
        {
            this.activity=activity;
        }

        public Builder title(String value) {
            this.title=value;
            return this;
        }

        public Builder errorActivityClass(Class<?> value)
        {
            this.errorActivityClass=value;
            return this;
        }

        public Builder titleRes(@StringRes int value) {
            this.title=activity.getString(value);
            return this;
        }


        public Builder message(String value) {
            this.message=value;
            return this;
        }

        public Builder messageRes(@StringRes int value) {
            this.message=activity.getString(value);
            return this;
        }

        public Builder buttonLabel(String value) {
            this.buttonLabel=value;
            return this;
        }

        public Builder buttonLabelRes(@StringRes int value) {
            this.buttonLabel=activity.getString(value);
            return this;
        }

        public Builder imageRes(@DrawableRes int value) {
            this.imageRes=value;
            return this;
        }




        public Builder backgroundColor(int color) {
            this.backgroundColor=color;
            return this;
        }

        public Builder backgroundColorRes(@ColorRes int value) {
            this.backgroundColor=activity.getResources().getColor(value);
            return this;
        }


        public Builder backgroundDrawablerRes(@DrawableRes int value) {
            this.backgroundDrawablerRes =value;
            return this;
        }

        public void forceShow()
        {
            forceShow(-1);
        }
        public void forceShow(int requestCode)
        {
            Intent intent=new Intent(activity,errorActivityClass);
            intent.putExtra(TITLE,title);
            intent.putExtra(MESSAGE,message);
            intent.putExtra(IMAGE_RES,imageRes);
            intent.putExtra(BUTTON_LABEL,buttonLabel);
            intent.putExtra(BACKGROUND_COLOR,backgroundColor);
            intent.putExtra(BACKGROUND_DRAWABLER_RES,backgroundDrawablerRes);
            intent.putExtra(REQUEST_CODE,requestCode);
            if(requestCode==-1)
                activity.startActivity(intent);
            else
                activity.startActivityForResult(intent,requestCode);
        }
        public boolean show(int requestCode)
        {
            forceShow(requestCode);
            return true;
        }
        public boolean show()
        {
            forceShow();
            return true;
        }
    }
}

