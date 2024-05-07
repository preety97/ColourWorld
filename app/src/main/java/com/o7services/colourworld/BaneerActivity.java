package com.o7services.colourworld;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import ss.com.bannerslider.banners.Banner;
import ss.com.bannerslider.banners.DrawableBanner;
import ss.com.bannerslider.views.BannerSlider;

public class BaneerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_baneer);
        BannerSlider bannerSlider=findViewById(R.id.baner);
        List<Banner> banners=new ArrayList<>();
        banners.add(new DrawableBanner(R.drawable.ban1));
        banners.add(new DrawableBanner(R.drawable.ban2));
        banners.add(new DrawableBanner(R.drawable.ban3));
        banners.add(new DrawableBanner(R.drawable.ban4));
        bannerSlider.setBanners(banners);
    }
}
