/*
 * Copyright (C) 2015 The Android Open Source Project
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

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v17.leanback.app.GuidedStepFragment;
import android.support.v17.leanback.widget.GuidanceStylist.Guidance;
import android.support.v17.leanback.widget.GuidedAction;

import java.util.List;
public class DialogFragment extends GuidedStepFragment {

    private String title;
    private int iconRes;
    private int[] actionsIDs;
    private String[] actionsLabels;
    private int[] actionsIcons;
    private String[] actionsDescriptions;

    private String description;
    private DialogFragment.OnActionClicked onDone;

    @NonNull
    @Override
    public Guidance onCreateGuidance(Bundle savedInstanceState) {
        Guidance guidance;
        if(getIconRes()!=-1)
        {
            try {
                if(getIconRes()!= -1) {
                    Drawable icon = getActivity().getDrawable(getIconRes());
                    guidance = new Guidance(getTitle(), getDescription(), "", icon);
                }
                else
                    guidance = new Guidance(getTitle(),getDescription(),"",null);
            }catch (Exception e)
            {
                guidance = new Guidance(getTitle(),getDescription(),"",null);
            }
        }
        else
            guidance = new Guidance(getTitle(),getDescription(),"",null);
        return guidance;
    }

    @Override
    public void onCreateActions(@NonNull List<GuidedAction> actions, Bundle savedInstanceState)
    {
        for (int i = 0; i < getActionsIDs().length; i++) {
            GuidedAction action = new GuidedAction.Builder().title(getActionsLabels()[i]).build();
            action.setId(getActionsIDs()[i]);

            if(getActionsDescriptions()!=null)
                action.setDescription(getActionsDescriptions()[i]);

            if(getActionsIcons()!=null)
            {

                try {
                    int iconID= getActionsIcons()[i];
                    action.setIcon(getResources().getDrawable(iconID));
                }catch (Exception e){}
            }


            actions.add(action);
        }

    }

    @Override
    public void onGuidedActionClicked(GuidedAction action)
    {
        onDone.onActionClicked(action);
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }



    public void setIconRes(int iconRes) {
        this.iconRes = iconRes;
    }

    public int getIconRes() {
        return iconRes;
    }



    public void setActionsIDs(int[] actionsIDs) {
        this.actionsIDs = actionsIDs;
    }

    public int[] getActionsIDs() {
        return actionsIDs;
    }

    public void setActionsLabels(String[] actionsLabels) {
        this.actionsLabels = actionsLabels;
    }

    public String[] getActionsLabels() {
        return actionsLabels;
    }

    public String[] getActionsDescriptions() {
        return actionsDescriptions;
    }
    public int[] getActionsIcons() {
        return actionsIcons;
    }



    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setOnDone(DialogFragment.OnActionClicked onDone) {
        this.onDone = onDone;
    }

    public DialogFragment.OnActionClicked getOnDone() {
        return onDone;
    }

    public void setActionsIcons(int[] actionsIcons) {
        this.actionsIcons = actionsIcons;
    }

    public void setActionsDescriptions(String[] actionsDescriptions) {
        this.actionsDescriptions = actionsDescriptions;
    }



    public static interface OnActionClicked
    {
        void onActionClicked(GuidedAction action);
    }
}
