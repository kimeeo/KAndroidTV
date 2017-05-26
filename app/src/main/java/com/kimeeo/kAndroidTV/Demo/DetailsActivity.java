package com.kimeeo.kAndroidTV.Demo;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.kimeeo.kAndroidTV.Demo.fragments.DetailsFullFragment;
import com.kimeeo.kAndroidTV.Demo.fragments.DetailsSmallFragment;
import com.kimeeo.kAndroidTV.Demo.fragments.VideoDetailsFragment;
import com.kimeeo.kAndroidTV.dialog.DialogActivity;
import com.kimeeo.kAndroidTV.errorFragment.ErrorFragmentHelper;

/**
 * Created by BhavinPadhiyar on 5/18/17.
 */

public class DetailsActivity extends Activity {

    public static final String SHARED_ELEMENT_NAME = "hero";
    public static final String MOVIE = "Movie";

    @Override public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);
        DialogActivity.Builder builder=new DialogActivity.Builder(this).title("What screen").description("Check").icon(R.drawable.image).background(R.drawable.background_canyon);
        builder.addAction(1,"Full Width","Open Full Screen",R.drawable.ic_android_black_24dp);
        builder.addAction(2,"Small","Open Small Screen",R.drawable.ic_android_black_24dp);
        builder.addAction(3,"Video","Open Video Screen",R.drawable.ic_android_black_24dp);
        builder.addAction(4,"Error","Open Error Screen",R.drawable.ic_android_black_24dp);
        builder.open();

    }


    @Override
   protected void onActivityResult(int requestCode, int resultCode, Intent data) {
       super.onActivityResult(requestCode, resultCode, data);
       if (requestCode == DialogActivity.REQUEST_CODE && resultCode == Activity.RESULT_OK) {
           int what=data.getIntExtra(DialogActivity.CHOICE,-1);
           Fragment fragment = null;
           if(what==1)
               fragment = new DetailsFullFragment();
           else if(what==2)
               fragment = new DetailsSmallFragment();
           else if(what==3)
               fragment = new VideoDetailsFragment();

           if(fragment==null)
           {
               ErrorFragmentHelper helper = new ErrorFragmentHelper(this);
               helper.title("Error");
               helper.message("Unknon Choice");
               helper.buttonText("Kill APP");
               helper.imageDrawableRes(R.drawable.image);
               helper.onClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View view) {
                       finish();
                   }
               });
               fragment = helper.build();

           }
           getFragmentManager().beginTransaction().replace(R.id.fragmentContainer, fragment).commit();
       }

   }

    @Override
    public boolean onSearchRequested() {
        startActivity(new Intent(this, SearchActivity.class));
        return true;
    }
}

