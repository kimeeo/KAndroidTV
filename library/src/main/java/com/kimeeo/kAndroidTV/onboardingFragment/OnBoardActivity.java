package com.kimeeo.kAndroidTV.onboardingFragment;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;

import com.kimeeo.kAndroidTV.R;
import com.kimeeo.kAndroidTV.dialog.DialogActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by BhavinPadhiyar on 5/18/17.
 */

public class OnBoardActivity extends Activity implements OnboardingFragment.OnFinish {

    public static final String PAGE_TITLES="pageTitles";
    public static final String PAGE_DESCRIPTIONS="pageDescriptions";
    public static final String PAGE_IMAGES_RES="pageImagesRes";
    public static final String START_BUTTON_LABEL="startButtonLabel";
    public static final String LOGO_RES="logoRes";
    public static final String BACKGROUND_COLOR="backgroundColor";
    public static final String BACKGROUND_DRAWABLER_RES="backgroundDrawablerRes";
    public static final String REQUEST_CODE="requestCode";


    int requestCode;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout._onboarding_activity_fragment);
        if (savedInstanceState == null) {
            OnboardingFragment fragment = new OnboardingFragment();

            String[] pageTitles=getIntent().getExtras().getStringArray(PAGE_TITLES);
            String[] pageDescriptions=getIntent().getExtras().getStringArray(PAGE_DESCRIPTIONS);
            int[] pageImagesRes=getIntent().getExtras().getIntArray(PAGE_IMAGES_RES);


            String startButtonLabel=getIntent().getExtras().getString(START_BUTTON_LABEL);
            int logoRes=getIntent().getExtras().getInt(LOGO_RES);
            int backgroundColor=getIntent().getExtras().getInt(BACKGROUND_COLOR);
            int backgroundDrawablerRes=getIntent().getExtras().getInt(BACKGROUND_DRAWABLER_RES);


            fragment.setPageTitles(pageTitles);
            fragment.setPageDescriptions(pageDescriptions);
            fragment.setPageImagesRes(pageImagesRes);
            if(startButtonLabel!=null && startButtonLabel.equals("")==false)
                fragment.setStartButtonLabel(startButtonLabel);

            if(logoRes>0)
                fragment.setLogoRes(logoRes);

            if(backgroundColor>0)
                fragment.setBackgroundColor(backgroundColor);

            fragment.setBackgroundDrawablerRes(backgroundDrawablerRes);

            getFragmentManager().beginTransaction().replace(R.id.onboardingFragmentContainer, fragment).commit();
            try {
                if(backgroundDrawablerRes>0)
                    getWindow().setBackgroundDrawableResource(backgroundDrawablerRes);
                else
                    getWindow().setBackgroundDrawable(new ColorDrawable(backgroundColor));
            }catch (Exception e){}

            requestCode=getIntent().getExtras().getInt(REQUEST_CODE);
            fragment.setOnFinish(this);
        }
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
            setResult(Activity.RESULT_OK, intent);
        }
        finish();
    }


    public static class Builder
    {

        private final Activity activity;

        private String startButtonLabel;
        private int logoRes;
        private int backgroundColor;
        private int backgroundDrawablerRes;
        private List<Page> pageList = new ArrayList<>();




        public Builder(Activity activity)
        {
            this.activity=activity;
        }
        public boolean isCompleted()
        {
            return AbstractOnboardingFragment.isCompleted(activity);
        }

        public Builder addPage(Page value) {
            pageList.add(value);
            return this;
        }

        public Builder addPage(String pageTitles,String pageDescriptions,@DrawableRes int pageImagesRes) {
            pageList.add(new Page( pageTitles, pageDescriptions,pageImagesRes));
            return this;
        }

        public Builder addPage(@StringRes int pageTitlesRes,@StringRes int pageDescriptionsRes,@DrawableRes int pageImagesRes) {
            pageList.add(new Page( activity.getResources().getString(pageTitlesRes), activity.getResources().getString(pageDescriptionsRes),pageImagesRes));
            return this;
        }
        public Builder startButtonLabel(String value) {
            this.startButtonLabel=value;
            return this;
        }


        public Builder startButtonLabelRes(@StringRes int value) {
            this.startButtonLabel=activity.getString(value);
            return this;
        }

        public Builder setLogoRes(@DrawableRes int value) {
            this.logoRes=value;
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
            String[] pageTitles=new String[pageList.size()];
            String[] pageDescriptions=new String[pageList.size()];
            int[] pageImagesRes=new int[pageList.size()];

            for (int i = 0; i < pageList.size(); i++) {
                pageTitles[i]=pageList.get(i).getTitle();
                pageDescriptions[i]=pageList.get(i).getDescriptions();
                pageImagesRes[i]=pageList.get(i).getImagesRes();
            }

            Intent intent=new Intent(activity,OnBoardActivity.class);
            intent.putExtra(PAGE_TITLES,pageTitles);
            intent.putExtra(PAGE_DESCRIPTIONS,pageDescriptions);
            intent.putExtra(PAGE_IMAGES_RES,pageImagesRes);
            intent.putExtra(START_BUTTON_LABEL,startButtonLabel);
            intent.putExtra(LOGO_RES,logoRes);
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
            if(isCompleted())
                return false;
            else
            {
                forceShow(requestCode);
                return true;
            }
        }
        public boolean show()
        {
            if(isCompleted())
                return false;
            else
            {
                forceShow();
                return true;
            }
        }
    }

    public static class Page
    {
        private String title;
        private String descriptions;
        private int imagesRes;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDescriptions() {
            return descriptions;
        }

        public void setDescriptions(String descriptions) {
            this.descriptions = descriptions;
        }

        public int getImagesRes() {
            return imagesRes;
        }

        public void setImagesRes(int imagesRes) {
            this.imagesRes = imagesRes;
        }

        public Page(String title, String descriptions, int imagesRes)
        {
            this.title=title;
            this.descriptions=descriptions;
            this.imagesRes=imagesRes;
        }
    }
}

