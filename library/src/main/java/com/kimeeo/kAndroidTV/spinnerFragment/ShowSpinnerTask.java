package com.kimeeo.kAndroidTV.spinnerFragment;

import android.app.FragmentManager;
import android.os.AsyncTask;
import android.os.SystemClock;
import android.support.annotation.IdRes;

import com.kimeeo.kAndroidTV.R;

/**
 * Created by BhavinPadhiyar on 5/27/17.
 */

public class ShowSpinnerTask extends AsyncTask<Void, Void, Void> {
    protected SpinnerFragment mSpinnerFragment;
    protected FragmentManager fragmentManager;
    protected int hostID;
    public ShowSpinnerTask(FragmentManager fragmentManager,@IdRes int hostID)
    {
        this.fragmentManager=fragmentManager;
        this.hostID =hostID;
    }

    @Override
    protected void onPreExecute() {
        mSpinnerFragment = new SpinnerFragment();
        fragmentManager.beginTransaction().add(hostID, mSpinnerFragment).commit();
    }

    @Override
    protected Void doInBackground(Void... params) {

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        fragmentManager.beginTransaction().remove(mSpinnerFragment).commit();
    }
}
