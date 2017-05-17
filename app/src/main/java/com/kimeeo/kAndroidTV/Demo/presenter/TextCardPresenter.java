package com.kimeeo.kAndroidTV.Demo.presenter;

import android.content.Context;
import android.support.v17.leanback.widget.BaseCardView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.kimeeo.kAndroidTV.Demo.R;
import com.kimeeo.kAndroidTV.Demo.dataProviders.Movie;
import com.kimeeo.kAndroidTV.cards.AbstractCardPresenter;
import com.kimeeo.kAndroidTV.cards.KimeeoBaseCardView;

/**
 * Created by BhavinPadhiyar on 5/17/17.
 */

public class TextCardPresenter extends AbstractCardPresenter {
    @Override
    protected BaseCardView onCreateView(ViewGroup parent) {
        return new TextCardView(parent.getContext());
    }

    public class TextCardView extends KimeeoBaseCardView {

        public TextCardView(Context context) {
            super(context);
            LayoutInflater.from(getContext()).inflate(R.layout.text_icon_card, this);
            setFocusable(true);
        }

        public void updateItemView(Object data) {
            TextView extraText = (TextView) findViewById(R.id.extra_text);
            TextView primaryText = (TextView) findViewById(R.id.primary_text);
            final ImageView imageView = (ImageView) findViewById(R.id.main_image);

            if(data instanceof Movie)
            {
                Movie m = (Movie) data;
                extraText.setText(m.getStudio());
                primaryText.setText(m.getTitle());
            }

           imageView.setImageResource(R.drawable.image);
        }

    }
}
