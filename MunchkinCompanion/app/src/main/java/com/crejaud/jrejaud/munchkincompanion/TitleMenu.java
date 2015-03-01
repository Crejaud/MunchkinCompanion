package com.crejaud.jrejaud.munchkincompanion;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
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
public class TitleMenu extends Activity {

    private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Get rid of top title bar
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.setContentView(R.layout.activity_title_menu);

        mAdView = (AdView) findViewById(R.id.adView);
        mAdView.setAdListener(new ToastAdListener(this));
        mAdView.loadAd(new AdRequest.Builder().build());


        //Jordan's Code, custom fonts
        //Declare typefaces
        Typeface header1 = Typeface.createFromAsset(this.getResources().getAssets(),"windlass.ttf");
        Typeface header2 = Typeface.createFromAsset(this.getResources().getAssets(),"pala.ttf");

        final TextView titleText = (TextView) findViewById(R.id.textTitle);
        final Button startBtn = (Button) findViewById(R.id.btnStart);
        final Button aboutBtn = (Button) findViewById(R.id.btnAbout);
        final Button buyBtn = (Button) findViewById(R.id.btnBuy);

        titleText.setTypeface(header1);
        startBtn.setTypeface(header2);
        aboutBtn.setTypeface(header2);
        buyBtn.setTypeface(header2);

        // Leads user to MainMenu
        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(TitleMenu.this, MainMenu.class));
            }
        });

        // Leads user to AboutMenu
        aboutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(TitleMenu.this, AboutMenu.class));
            }
        });

        // Leads user to buy ad-free version
        buyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String appURL = "market://details?id=<package_name>"; //fill in package_name to be the app id
                Uri uri = Uri.parse(appURL);
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
