package com.kimeeo.kAndroidTV.Demo.presenter;

import android.content.Context;
import android.support.v17.leanback.widget.BaseCardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.kimeeo.kAndroidTV.Demo.R;
import com.kimeeo.kAndroidTV.Demo.dataProviders.Movie;
import com.kimeeo.kAndroidTV.cards.AbstractCardPresenter;
import com.kimeeo.kAndroidTV.cards.CustomViewCardPresenter;

/**
 * Created by BhavinPadhiyar on 5/17/17.
 */

public class TextCardPresenter extends CustomViewCardPresenter {
    @Override
    public void updateItemView(View view, Object data) {
        TextView extraText = (TextView) view.findViewById(R.id.extra_text);
        TextView primaryText = (TextView) view.findViewById(R.id.primary_text);
        final ImageView imageView = (ImageView) view.findViewById(R.id.main_image);

        if(data instanceof Movie)
        {
            Movie m = (Movie) data;
            extraText.setText(m.getStudio());
            primaryText.setText(m.getTitle());
        }

        imageView.setImageResource(R.drawable.image);
    }

    @Override
    public int getLayoutRes() {
        return R.layout.text_icon_card;
    }
}
