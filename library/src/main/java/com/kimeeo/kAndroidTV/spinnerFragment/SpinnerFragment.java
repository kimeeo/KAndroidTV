package com.kimeeo.kAndroidTV.spinnerFragment;

/**
 * Created by BhavinPadhiyar on 5/27/17.
 */
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import java.lang.ref.WeakReference;

public class SpinnerFragment extends Fragment {

    private static final int SPINNER_WIDTH = 100;
    private static final int SPINNER_HEIGHT = 100;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        ProgressBar progressBar = new ProgressBar(container.getContext());
        if (container instanceof FrameLayout) {
            FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(SPINNER_WIDTH, SPINNER_HEIGHT, Gravity.CENTER);
            progressBar.setLayoutParams(layoutParams);
        }
        return progressBar;
    }



    private static WeakReference<SpinnerFragment> mSpinnerFragment;
    public static void showSpinner(FragmentManager fragmentManager,@IdRes int hostID)
    {
        mSpinnerFragment = new WeakReference(new SpinnerFragment());
        if(mSpinnerFragment==null)
            fragmentManager.beginTransaction().add(hostID, mSpinnerFragment.get()).commit();
    }
    public static void hideSpinner(FragmentManager fragmentManager)
    {
        if(mSpinnerFragment!=null && mSpinnerFragment.get()!=null)
            fragmentManager.beginTransaction().remove(mSpinnerFragment.get()).commit();

        mSpinnerFragment=null;
    }


}
