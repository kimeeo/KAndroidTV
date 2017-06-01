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

package com.kimeeo.kAndroidTV.Demo;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;

import com.kimeeo.kAndroidTV.Demo.fragments.BrowseFragment;
import com.kimeeo.kAndroidTV.recommendationBuilder.IAdvanceRecommendation;
import com.kimeeo.kAndroidTV.recommendationBuilder.Recommendation;
import com.kimeeo.kAndroidTV.recommendationBuilder.RecommendationHelper;

import me.angrybyte.goose.Article;
import me.angrybyte.goose.Configuration;
import me.angrybyte.goose.ContentExtractor;

/*
 * MainActivity class that loads MainFragment1
 */
public class MainActivity extends Activity {

    /**
     * Called when the activity is first created.
     */
    @Override public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);
        if (savedInstanceState == null) {
            Fragment fragment = new BrowseFragment();
            getFragmentManager().beginTransaction().replace(R.id.fragmentContainer, fragment).commit();


            recommendationDemo();

        }
        /*
        DownloadWebPageTask task = new DownloadWebPageTask();
        task.execute(new String[]{"http://gujaratsamachar.com/index.php/articles/display_article/gandhinagar/while-the-prime-minister-stayed-in-gandhinagar-the-congressmen-were-kept-in-captivity"});
         */
       // startActivity(new Intent(this, OnBoardActivity.class));
        /*
        tts = new TextToSpeech(this.getBaseContext(),new TextToSpeech.OnInitListener(){
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {

                    int result = tts.setLanguage(new Locale("hin-IND"));

                    if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                        System.out.println("a");
                    } else {
                        tts.speak("नई दिल्ली: भारत ने फ़ाइनल में कोलंबिया को हराकर वर्ल्ड कप कंपाउंड तीरंदाज़ी का गोल्ड मेडल अपने नाम कर लिया है. भारत ने कोलंबिया के ख़िलाफ़ 5 अंकों के अंतर से जीत हासिल की. भारत के अभिषेक वर्मा, अमनजीत सिंह और चिन्नाराजू श्रीथर की तिकड़ी ने रोमांचक फ़ाइनल में अपना संयम बनाए रखा और कोलंबिया को शुरुआत से ही दबाव में रखा. कोलंबिया ने 221 अंक हासिल किए जबकि भारत ने 226 अंक.\n" +
                                "\n" +
                                "वर्ल्ड कप में भारतीय टीम की ये ऐतिहासिक जीत है. वर्ल्ड कप में भारतीय कंपाउंड टीम ने पहली बार स्वर्णिम सफ़लता हासिल की है. फ़ाइनल में पहुंचने से पहले भारत ने अमेरिका,  वियतनाम और वर्ल्ड चैंपियन ईरान जैसी टीम को शिकस्त दी. इस जीत के बाद भारतीय टीम के हौसले बुलंद हो गए हैं और तीरंदाज़ी के जानकार उनसे अभी से ही दूसरे वर्ल्ड कप (जून में तुर्की) में बड़ी उम्मीदें करने लगे हैं.\n" +
                                "\n" +
                                "इंचियन एशियाई गेम्स में गोल्ड मेडल जीतने वाले अभिषेक वर्मा ने इस टूर्नामेंट में भी शानदार प्रदर्शन किया. उनके भारतीय कोच लोकेश कुमार ने उनकी जीत के फ़ौरन बाद उनसे बात की, तो उन्होंने इस बात की खुशी जताई कि दो साल पहले 2015 में उन्होंने पहली बार पोलैंड में वर्ल्ड कप का निजी गोल्ड मेडल जीता था और अब टीम के ऐतिहासिक गोल्ड में भी उनका नाम शामिल है.\n" +
                                "\n" +
                                "अभिषेक ने बताया कि इस बार फ़ाइनल में उनकी टीम कंट्रोल में थी. अभिषेक ये भी कहते हैं कि अमेरिका जैसी मज़बूत टीम को हराना बड़ी चुनौती थी. वो कहते हैं कि इस जीत की वजह से आने वाले टूर्नामेंट में भारतीय टीम और बेहतर प्रदर्शन कर सकती है.\n" +
                                "\n" +
                                "अभिषेक के कोच लोकेश चंद कहते हैं, \"इस बार टीम कॉम्बिनेशन बहुत अच्छा था. दो सीनियर खिलाड़ी- अभिषेक और चिन्नाराजू श्रीथर के साथ एकदम नए खिलाड़ी पंजाब के अमनजीत सिंह के बीच अच्छा तालमेल बन गया जिसका फ़ायदा उन्हें पूरे टूर्नामेंट में हुआ. वो मानते हैं कि नए खिलाड़ियों का फ़ायदा टीम को लंबे समय तक मिलता रह सकता है.\"", TextToSpeech.QUEUE_FLUSH, null);
                    }
                } else {

                }
            }
        });*/
    }

    private void recommendationDemo() {
        /*
        RecommendationFactory recommendationFactory = new RecommendationFactory(getApplicationContext(),RecommendationActivity.class);
        recommendationFactory.setFastLaneColorRes(R.color.fastlane_background);
        IRecommendation recommendation = new IRecommendation(){

            @Override
            public String getImageUrl() {
                return "https://i.ytimg.com/vi/ijOlFh0PT7Y/hqdefault.jpg";
            }

            @Override
            public String getTitle() {
                return "Recommendation";
            }

            @Override
            public String getDescription() {
                return "Recommendation Description";
            }

            @Override
            public Bitmap getImage() {
                return null;
            }

            @Override
            public long getId() {
                return 10;
            }

            @Override
            public void setImage(Bitmap bitmap) {

            }

            @Override
            public Bitmap getBackgroundBitmap() {
                return null;
            }

            @Override
            public void setBackgroundBitmap(Bitmap backgroundBitmap) {

            }
        };
        recommendationFactory.recommend(1, recommendation, NotificationCompat.PRIORITY_HIGH,R.drawable.ic_android_black_24dp);
        */

        RecommendationHelper recommendationHelper = new RecommendationHelper(this,RecommendationActivity.class);
        recommendationHelper.icon(R.drawable.ic_android_black_24dp);
        recommendationHelper.addRecommendation(1,"Title 1","https://i.ytimg.com/vi/ijOlFh0PT7Y/hqdefault.jpg","Details 1").getRecommendation(0).setBackgroundURL("https://i.ytimg.com/vi/ijOlFh0PT7Y/hqdefault.jpg");
        recommendationHelper.addRecommendation(2,"Title 2","https://i.ytimg.com/vi/OLtrfo6Ejbc/hqdefault.jpg","Details 6").getRecommendation(1).setBackgroundURL("https://i.ytimg.com/vi/OLtrfo6Ejbc/hqdefault.jpg");
        recommendationHelper.addRecommendation(3,"Title 3","https://i.ytimg.com/vi/pK7W5npkhho/hqdefault.jpg","Details 5").getRecommendation(2).setBackgroundURL("https://i.ytimg.com/vi/pK7W5npkhho/hqdefault.jpg");
        recommendationHelper.addRecommendation(4,"Title 4","https://i.ytimg.com/vi/9fbrH7XOuLY/hqdefault.jpg","Details 4").getRecommendation(3).setBackgroundURL("https://i.ytimg.com/vi/9fbrH7XOuLY/hqdefault.jpg");

        recommendationHelper.addRecommendation(new AdvanceRecommendation(5,"Advance","https://i.ytimg.com/vi/CzLWdNfNj-4/hqdefault.jpg","Details 4"));
        recommendationHelper.recommendAll();
    }
    public static class AdvanceRecommendation extends Recommendation implements IAdvanceRecommendation
    {

        public AdvanceRecommendation(int id, String title, String imageUrl, String description) {
            super(id, title, imageUrl, description);
        }

        @Override
        public Class getAcitivtyClass() {
            return null;
        }

        @Override
        public int getCardWidth() {
            return 100;
        }

        @Override
        public int getCardHeight() {
            return 100;
        }

        @Override
        public boolean useCustomHeight() {
            return true;
        }

        @Override
        public int getIcon() {
            return R.drawable.ic_insert_emoticon_black_24dp;
        }

        @Override
        public int getFastLaneColorRes() {
            return R.color.lb_playback_progress_color_no_theme;
        }
    }


    private class DownloadWebPageTask extends AsyncTask<String, Void, Article> {
        @Override
        protected Article doInBackground(String... urls) {

            Configuration config = new Configuration(getCacheDir().getAbsolutePath());
            ContentExtractor extractor = new ContentExtractor(config);

            Article article = extractor.extractContent(urls[0]);
            if (article == null) {
                return null;
            }

            return article;
        }


    @Override
    protected void onPostExecute(Article result) {
        System.out.println(result);
    }
};

    private TextToSpeech tts;
    @Override
    public boolean onSearchRequested() {
        startActivity(new Intent(this, SearchActivity.class));
        return true;
    }

}
