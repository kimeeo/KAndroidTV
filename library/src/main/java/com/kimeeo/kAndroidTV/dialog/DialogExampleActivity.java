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
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.DrawableRes;
import android.support.v17.leanback.app.GuidedStepFragment;
import android.support.v17.leanback.widget.Action;
import android.support.v17.leanback.widget.GuidedAction;

import java.util.List;

public class DialogExampleActivity extends Activity implements DialogExampleFragment.OnActionClicked {

    @Override public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null)
        {
            DialogExampleFragment fragment = new DialogExampleFragment();
            String title=getIntent().getExtras().getString(TITLE);
            String description=getIntent().getExtras().getString(DESCRIPTION);
            int icon=getIntent().getExtras().getInt(ICON);
            int[] actionsIDs=getIntent().getExtras().getIntArray(ACTIONS_ID);
            String[] actionsLabels=getIntent().getExtras().getStringArray(ACTIONS_LABEL);
            fragment.setTitle(title);
            fragment.setDescription(description);
            fragment.setIconRes(icon);
            fragment.setActionsIDs(actionsIDs);
            fragment.setActionsLabels(actionsLabels);
            fragment.setOnDone(this);

            GuidedStepFragment.addAsRoot(this, fragment, android.R.id.content);
        }
    }

    public void onActionClicked(GuidedAction action) {
        Intent intent = new Intent();
        intent.putExtra(CHOICE, action.getId());
        setResult(Activity.RESULT_OK, intent);
        finish();
    }
    public void onBackPressed() {
        setResult(Activity.RESULT_CANCELED);
        finish();
    }

    static final public String CHOICE ="choice";
    static final public String TITLE ="title";
    static final public String ICON ="icon";
    static final public String DESCRIPTION ="description";
    static final public String ACTIONS_ID ="actionsID";
    static final public String ACTIONS_LABEL ="actionsLabel";
    static final public int REQUEST_CODE =1222;
    static public void openDialog(Activity activity, int[] actionsIDs, String[] actionsLabels,String title, String description,@DrawableRes int icon)
    {
        Intent intent=new Intent(activity,DialogExampleActivity.class);
        intent.putExtra(TITLE,title);
        intent.putExtra(DESCRIPTION,description);
        intent.putExtra(ICON,icon);
        intent.putExtra(ACTIONS_ID,actionsIDs);
        intent.putExtra(ACTIONS_LABEL,actionsLabels);

        activity.startActivityForResult(intent, REQUEST_CODE);

    }
}
