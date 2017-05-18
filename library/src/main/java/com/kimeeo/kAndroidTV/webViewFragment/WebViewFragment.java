package com.kimeeo.kAndroidTV.webViewFragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v17.leanback.app.BrowseFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;

/**
 * Created by BhavinPadhiyar on 5/18/17.
 */

abstract public class WebViewFragment extends Fragment implements BrowseFragment.MainFragmentAdapterProvider {

    private BrowseFragment.MainFragmentAdapter mMainFragmentAdapter = new BrowseFragment.MainFragmentAdapter(this);
    private WebView mWebview;

    @Override
    public BrowseFragment.MainFragmentAdapter getMainFragmentAdapter() {
        return mMainFragmentAdapter;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getMainFragmentAdapter().getFragmentHost().showTitleView(false);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FrameLayout root = new FrameLayout(getActivity());
        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,FrameLayout.LayoutParams.MATCH_PARENT);
        lp.setMarginStart(32);
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
        getMainFragmentAdapter().getFragmentHost().notifyDataReady(getMainFragmentAdapter());
    }

    abstract protected String getURL();
}
