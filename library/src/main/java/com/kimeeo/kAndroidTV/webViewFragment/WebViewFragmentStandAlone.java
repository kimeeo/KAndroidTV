package com.kimeeo.kAndroidTV.webViewFragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;

/**
 * Created by BhavinPadhiyar on 5/18/17.
 */

abstract public class WebViewFragmentStandAlone extends Fragment{

    private WebView mWebview;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FrameLayout root = new FrameLayout(getActivity());
        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,FrameLayout.LayoutParams.MATCH_PARENT);
        mWebview = new WebView(getActivity());
        mWebview.setWebViewClient(new WebViewClient());
        mWebview.getSettings().setJavaScriptEnabled(true);
        root.addView(mWebview, lp);
        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        mWebview.loadUrl(getURL());
    }
    abstract protected String getURL();
}
