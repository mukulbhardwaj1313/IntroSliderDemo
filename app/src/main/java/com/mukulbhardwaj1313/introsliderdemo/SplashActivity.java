package com.mukulbhardwaj1313.introsliderdemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.mukulbhardwaj1313.introslider.IntroSlider;
import com.mukulbhardwaj1313.introslider.OnSlideChangeListner;

public class SplashActivity extends AppCompatActivity {

    private static final String TAG = "SplashActivity";

    AppCompatTextView proceed;

    int[] titles = new int[]{R.string.app_name, R.string.Page2Title, R.string.Page3Title, R.string.Page4Title, R.string.Page5Title,};

    int[] messages = new int[]{R.string.Page1Message, R.string.Page2Message, R.string.Page2Message, R.string.Page2Message, R.string.Page2Message,};

    int[] title_image_logo = new int[]{R.drawable.ic_analytics, R.drawable.ic_infinity_logo, R.drawable.ic_analytics, R.drawable.ic_lock_2, R.drawable.ic_infinity_logo};

    int[] bg_image = new int[]{R.drawable.welc1, R.drawable.welc2, R.drawable.welc3, R.drawable.welc1, R.drawable.welc3};

    int[] text_color = new int[]{R.color.colorDarkGray, R.color.colorWhite, R.color.colorDarkGray, R.color.colorDarkGray, R.color.colorDarkGray};




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_splash);
        IntroSlider introSlider=findViewById(R.id.intro);
        proceed=findViewById(R.id.get_started_button);

        introSlider
                .provideBackgroundImages(bg_image)
                .provideSubTitles(messages)
                .provideTitleImages(title_image_logo)
                .provideTitles(titles)
                .provideTextColor(text_color)      //provideTextColor(int) method will have priority above provideTextColor(int[])
//                .provideTextColor(R.color.colorAccent)

                .setDelayDuration(900)         //default 300
                .setAnimationDuration(900)     //default 900
                .setTranslationUpwards(400)     //default 500
                .setTitleInCenter(true)         //default false. title will be at bottom
                .setBackGroundImageScaleType(ImageView.ScaleType.CENTER_CROP)   //default FIT_XY
                .setbottomDotsRadiusInDP(15)     //default 8dp
                .showbottomDots(true);         //default true



        final SharedPreferences sharedPreferences=getSharedPreferences("firstTimeUser", Context.MODE_PRIVATE);
        boolean isFirsttimeUser=sharedPreferences.getBoolean("isFirstTimeUser",true);


        introSlider.setOnSlideChangeListner(new OnSlideChangeListner() {
            @Override
            public void onPageChanged(int page) {
                Log.d(TAG, "onPageChanged: "+page );
                if(page==4){

                    proceed.setVisibility(View.VISIBLE);
                }else {
                    proceed.setVisibility(View.GONE);

                }
            }
        });

        proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SplashActivity.this,MainActivity.class));
                finish();
                sharedPreferences.edit().putBoolean("isFirstTimeUser",false).apply();
            }
        });



        if (isFirsttimeUser){
            introSlider.initiateAnimation();       // initiateAnimation method must be called after provideTitles

        }else {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(SplashActivity.this,MainActivity.class));
                    finish();
                }
            },1000);
        }

    }

}
