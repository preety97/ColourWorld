package com.o7services.colourworld;

import android.animation.ObjectAnimator;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class SplashActivity extends AppCompatActivity {
    ImageView image,bac;
    TextView txt,txty;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        bac=findViewById(R.id.back);
        Animation animation2 =
                AnimationUtils.loadAnimation(getApplicationContext(),
                        R.anim.move);
        bac.startAnimation(animation2);

        image=findViewById(R.id.fone);
        Animation animation1 =
                AnimationUtils.loadAnimation(getApplicationContext(),
                        R.anim.blink);
        image.startAnimation(animation1);

        txt=findViewById(R.id.text);
        Animation animation3 =
                AnimationUtils.loadAnimation(getApplicationContext(),
                        R.anim.blink);
        txt.startAnimation(animation3);

        txty=findViewById(R.id.text2);
        Animation animation4 =
                AnimationUtils.loadAnimation(getApplicationContext(),
                        R.anim.blink);
        txty.startAnimation(animation4);




        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this, HomeActivity.class);
                ActivityOptions activityOptions = ActivityOptions.makeCustomAnimation(
                        SplashActivity.this, android.R.anim.fade_in, android.R.anim.fade_out

                );
                startActivity(intent, activityOptions.toBundle());
                finish();
            }
        }, 3000);
    }
}

