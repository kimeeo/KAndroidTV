package com.kimeeo.kAndroidTV.cards;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.kimeeo.kAndroidTV.R;

public class PreviewCardView extends FrameLayout {

    FrameLayout mMainContainer;

    LoopingVideoView mVideoView;

    ImageView mImageView;

    View mOverlayView;

    ProgressBar mProgressCard;

    private String mVideoUrl;

    public PreviewCardView(Context context) {
        super(context);
        init();
    }

    public PreviewCardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PreviewCardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public PreviewCardView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        View view = inflate(getContext(), R.layout._widget_preview_card, this);
        mMainContainer = (FrameLayout) view.findViewById(R.id.main_container);
        mVideoView = (LoopingVideoView) view.findViewById(R.id.main_video);
        mImageView= (ImageView) view.findViewById(R.id.main_image);
        mOverlayView= (View) view.findViewById(R.id.view_overlay);
        mProgressCard= (ProgressBar) view.findViewById(R.id.progress_card);
    }

    public void setVideoUrl(String videoUrl) {
        mVideoUrl = videoUrl;
    }

    public ImageView getImageView() {
        return mImageView;
    }

    public void setLoading() {
        mOverlayView.setVisibility(View.VISIBLE);
        mProgressCard.setVisibility(View.VISIBLE);
        mVideoView.setVisibility(View.VISIBLE);
        mVideoView.setupMediaPlayer(mVideoUrl, new LoopingVideoView.OnVideoReadyListener() {
            @Override
            public void onVideoReady() {
                mOverlayView.setVisibility(View.INVISIBLE);
                mProgressCard.setVisibility(View.INVISIBLE);
                mImageView.setVisibility(View.INVISIBLE);
            }
        });
    }

    public void setFinished() {
        mVideoView.setVisibility(View.INVISIBLE);
        mVideoView.stopMediaPlayer();
        mImageView.setVisibility(View.VISIBLE);
        mOverlayView.setVisibility(View.INVISIBLE);
        mProgressCard.setVisibility(View.INVISIBLE);
    }

}
