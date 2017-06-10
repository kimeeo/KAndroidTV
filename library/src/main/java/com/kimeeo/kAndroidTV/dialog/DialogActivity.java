/*
 * Copyright (C) 2014 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package com.kimeeo.kAndroidTV.dialog;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;
import android.support.v17.leanback.app.GuidedStepFragment;
import android.support.v17.leanback.widget.GuidedAction;

import java.util.ArrayList;
import java.util.List;

public class DialogActivity extends Activity implements DialogFragment.OnActionClicked {



    @Override public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null)
        {
            DialogFragment fragment = createDialogFragment();

            String title=getIntent().getExtras().getString(TITLE);
            String description=getIntent().getExtras().getString(DESCRIPTION);
            int icon=getIntent().getExtras().getInt(ICON);
            int[] actionsIDs=getIntent().getExtras().getIntArray(ACTIONS_ID);
            String[] actionsLabels=getIntent().getExtras().getStringArray(ACTIONS_LABEL);
            String[] actionsDescription=getIntent().getExtras().getStringArray(ACTIONS_DESCRIPTION);
            int[] actionsIcon=getIntent().getExtras().getIntArray(ACTIONS_ICON);

            fragment.setTitle(title);
            fragment.setDescription(description);
            fragment.setIconRes(icon);

            fragment.setActionsIDs(actionsIDs);
            fragment.setActionsLabels(actionsLabels);
            fragment.setActionsDescriptions(actionsDescription);
            fragment.setActionsIcons(actionsIcon);

            fragment.setOnDone(this);
            try {
                int backgroundDrawable=getIntent().getExtras().getInt(BACKGROUND_DRAWABLE);
                int backgroundColor=getIntent().getExtras().getInt(BACKGROUND_COLOR);
                if(backgroundDrawable>0)
                    getWindow().setBackgroundDrawableResource(backgroundDrawable);
                else
                    getWindow().setBackgroundDrawable(new ColorDrawable(backgroundColor));


            }catch (Exception e)
            {

            }


            GuidedStepFragment.addAsRoot(this, fragment, android.R.id.content);
        }
    }

    protected DialogFragment createDialogFragment() {
        return new DialogFragment();
    }

    public void onActionClicked(GuidedAction action) {
        Intent intent = new Intent();
        int id=(int)action.getId();
        intent.putExtra(CHOICE,id);
        setResult(Activity.RESULT_OK, intent);
        finish();
    }
    public void onBackPressed() {
        setResult(Activity.RESULT_CANCELED);
        finish();
    }

    static final public String CHOICE ="choice";

    static final public String BACKGROUND_DRAWABLE ="backgroundDrawable";
    static final public String BACKGROUND_COLOR ="backgroundColor";
    static final public String TITLE ="title";
    static final public String ICON ="icon";
    static final public String DESCRIPTION ="description";
    static final public String ACTIONS_ID ="actionsID";
    static final public String ACTIONS_LABEL ="actionsLabel";
    static final public String ACTIONS_ICON ="actionsIcon";
    static final public String ACTIONS_DESCRIPTION ="actionsDescription";

    static final public int REQUEST_CODE =1222;
    static public void openDialog(Activity activity,Class dialogActivityClass, int[] actionsIDs, String[] actionsLabels,int[] actionsIcons,String[] actionsDescriptions,String title, String description,@DrawableRes int icon,@DrawableRes int background,int backgroundColor)
    {
        Intent intent=new Intent(activity,dialogActivityClass);
        intent.putExtra(TITLE,title);
        intent.putExtra(DESCRIPTION,description);
        intent.putExtra(ICON,icon);
        intent.putExtra(ACTIONS_ID,actionsIDs);
        intent.putExtra(ACTIONS_LABEL,actionsLabels);
        intent.putExtra(ACTIONS_DESCRIPTION,actionsDescriptions);
        intent.putExtra(ACTIONS_ICON,actionsIcons);

        intent.putExtra(BACKGROUND_DRAWABLE,background);
        intent.putExtra(BACKGROUND_COLOR,backgroundColor);

        activity.startActivityForResult(intent, REQUEST_CODE);
    }

    static public void openDialog(Activity activity,int[] actionsIDs, String[] actionsLabels,int[] actionsIcons,String[] actionsDescriptions,String title, String description,@DrawableRes int icon,@DrawableRes int background,int backgroundColor)
    {
        openDialog(activity,DialogActivity.class,actionsIDs,actionsLabels,actionsIcons,actionsDescriptions,title,description,icon,background,backgroundColor);
    }
    public static class Builder
    {
        private Activity activity;
        private String title;
        private String description;
        private int icon;
        private int background=-1;
        private int backgroundColor=-1;



        private List<Action> actions;

        public Builder(Activity activity)
        {
            this.activity =activity;
        }
        public Builder title(String title)
        {
            this.title =title;
            return this;
        }
        public Builder titleRes(int title)
        {
            this.title =activity.getString(title);
            return this;
        }
        public Builder icon(int icon)
        {
            this.icon =icon;
            return this;
        }
        public Builder background(int background)
        {
            this.background =background;
            return this;
        }

        public Builder backgroundColor(int color)
        {
            this.backgroundColor =color;
            return this;
        }


        public Builder description(String description)
        {
            this.description =description;
            return this;
        }
        public Builder descriptionRes(int description)
        {
            this.description =activity.getString(description);
            return this;
        }
        public Builder dialogActivityClass(Class value)
        {
            this.dialogActivityClass =value;
            return this;
        }

        public Builder actions(List<Action> actions)
        {
            this.actions =actions;
            return this;
        }
        public Builder addAction(Action action)
        {
            if(actions==null)
                actions = new ArrayList<>();
            actions.add(action);
            return this;
        }
        public Builder addAction(int id,String label)
        {
            if(actions==null)
                actions = new ArrayList<>();
            Action action= new Action(id,label);
            actions.add(action);
            return this;
        }
        public Builder addAction(int id,String label,String description,@DrawableRes int icon)
        {
            if(actions==null)
                actions = new ArrayList<>();
            Action action= new Action(id,label);
            action.setDescription(description);
            action.setIcon(icon);
            actions.add(action);
            return this;
        }
        public Builder addAction(int id,@StringRes int labelRes)
        {
            if(actions==null)
                actions = new ArrayList<>();
            Action action= new Action(id,activity.getResources().getString(labelRes));
            actions.add(action);
            return this;
        }
        public Builder addAction(int id, @StringRes int labelRes, @StringRes int descriptionRes, @DrawableRes int icon)
        {
            if(actions==null)
                actions = new ArrayList<>();
            Action action= new Action(id,activity.getResources().getString(labelRes));
            action.setDescription(activity.getResources().getString(descriptionRes));
            action.setIcon(icon);
            actions.add(action);
            return this;
        }


        private Class<?> dialogActivityClass = DialogActivity.class;

        public Builder open()
        {
            int[] actionsIDs =new int[actions.size()];
            String[] actionsLabels=new String[actions.size()];
            int[] actionsIcons=new int[actions.size()];
            String[] actionsDescriptions=new String[actions.size()];
            for (int i = 0; i < actions.size(); i++) {
                actionsIDs[i]=actions.get(i).getId();
                actionsLabels[i]=actions.get(i).getLabel();
                actionsIcons[i]=actions.get(i).getIcon();
                actionsDescriptions[i]=actions.get(i).getDescription();
            }
            openDialog(activity,dialogActivityClass,actionsIDs,actionsLabels,actionsIcons,actionsDescriptions,title,description,icon,background,backgroundColor);

            return this;
        }
    }
    public static class Action
    {
        private int id;
        private String label;
        private String description;
        private int icon;

        public Action(int id,String label)
        {
            this.label=label;
            this.id=id;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getLabel() {
            return label;
        }

        public void setLabel(String label) {
            this.label = label;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public int getIcon() {
            return icon;
        }

        public void setIcon(int icon) {
            this.icon = icon;
        }
    }
}
