package com.crejaud.jrejaud.munchkincompanion;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

/**
 * Created by creja_000 on 8/14/2014.
 */
public class AboutMenu extends Activity {

    private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Get rid of top title bar
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.setContentView(R.layout.activity_about_menu_new);

        //Jordan's Code, custom fonts
        //Declare typefaces
        Typeface header1 = Typeface.createFromAsset(this.getResources().getAssets(), "windlass.ttf");
        Typeface header2 = Typeface.createFromAsset(this.getResources().getAssets(), "pala.ttf");

        // Initialize Advert
        mAdView = (AdView) findViewById(R.id.adView);
        mAdView.setAdListener(new ToastAdListener(this));
        mAdView.loadAd(new AdRequest.Builder().build());


        final Button backBtn = (Button) findViewById(R.id.btnBack);
        final Button twitterBtn = (Button) findViewById(R.id.btnTwitter);
        final Button websiteBtn = (Button) findViewById(R.id.btnWebsite);
        final TextView aboutText1 = (TextView) findViewById(R.id.textAbout1);
        TextView aboutText2 = (TextView) findViewById(R.id.textAbout2);

        String corentin = "<font color='#2B6D19'>Corentin</font>";
        String and = " and ";
        String jordan = "<font color='#1D4999'>Jordan</font>";
        String rejaud = " Réjaud";
        aboutText2.setText(Html.fromHtml(corentin + and + jordan + rejaud));

        backBtn.setTypeface(header2);
        twitterBtn.setTypeface(header2);
        websiteBtn.setTypeface(header2);
        aboutText1.setTypeface(header2);
        aboutText2.setTypeface(header2);


        // Kills screen
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        // Goes to Twitter
        twitterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = "I like Munchkin Level Counter: ";
                String catid = "40";
                String id = "12546";

                Uri.Builder b = Uri.parse("http://www.mywebsite.com/").buildUpon();
                b.path("index.php");
                b.appendQueryParameter("option=com_content&catid", catid);
                b.appendQueryParameter("id", id);
                b.appendQueryParameter("view=article", null);
                b.build();

                String url = b.build().toString();

                String tweetUrl = "https://twitter.com/intent/tweet?text=" + title + "&url=" + url;

                Uri uri = Uri.parse(tweetUrl);
                startActivity(new Intent(Intent.ACTION_VIEW, uri));
            }
        });

        // Goes to Jordan's Website
        websiteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String jordanURL = "http://www.jordanrejaud.com";
                Uri uri = Uri.parse(jordanURL);
                Intent browse = new Intent( Intent.ACTION_VIEW , uri);

                startActivity( browse );
            }
        });

    }

    // When Advert is paused
    @Override
    protected void onPause() {
        mAdView.pause();
        super.onPause();
    }
    // When Advert resumes
    @Override
    protected void onResume() {
        super.onResume();
        mAdView.resume();
    }
    // When Advert is destroyed
    @Override
    protected void onDestroy() {
        mAdView.destroy();
        super.onDestroy();
    }
}