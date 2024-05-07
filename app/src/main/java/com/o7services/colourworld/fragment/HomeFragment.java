package com.o7services.colourworld.fragment;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.o7services.colourworld.HomeActivity;
import com.o7services.colourworld.R;
import com.o7services.colourworld.shareddata.ShareData;

import java.util.ArrayList;
import java.util.List;

import ss.com.bannerslider.banners.Banner;
import ss.com.bannerslider.banners.DrawableBanner;
import ss.com.bannerslider.views.BannerSlider;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {
ImageButton imgrg,imgcat,imgcart;
ImageView imgtl,fone,emai;
ImageButton imglg;
TextView rg,lg,tl,texcart,texcat;

    ShareData shareData;
    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_home, container, false);
       BannerSlider bannerSlider = (BannerSlider)view.findViewById(R.id.banner_slider1);

        List<Banner> banners=new ArrayList<>();
        ((HomeActivity) getActivity())
                .setActionBarTitle("COLOR WORLD");

        banners.add(new DrawableBanner(R.drawable.ban1));
        banners.add(new DrawableBanner(R.drawable.ban2));
        banners.add(new DrawableBanner(R.drawable.ban3));
        banners.add(new DrawableBanner(R.drawable.ban4));
        banners.add(new DrawableBanner(R.drawable.ban5));
       // banners.add(new DrawableBanner(R.drawable.ban6));
       // banners.add(new DrawableBanner(R.drawable.bank));
        imgrg=view.findViewById(R.id.regimg);
        imglg=view.findViewById(R.id.logimg);
        imgtl=view.findViewById(R.id.toolimg);
        imgcat=view.findViewById(R.id.prodimg);
        imgcart=view.findViewById(R.id.cartimg);
        rg=view.findViewById(R.id.regtext);
        lg=view.findViewById(R.id.logtext);
        tl=view.findViewById(R.id.tooltext);
        texcart=view.findViewById(R.id.carttext);
        texcat=view.findViewById(R.id.cattext);
        fone=view.findViewById(R.id.fn);
        emai=view.findViewById(R.id.em);

        bannerSlider.setBanners(banners);

        shareData = new ShareData(getContext());
        if (shareData.getUserEmailId().equals(""))
        {
            imglg.setVisibility(View.VISIBLE);
            imgrg.setVisibility(View.VISIBLE);
            rg.setVisibility(View.VISIBLE);
            lg.setVisibility(View.VISIBLE);
            imgcart.setVisibility(View.GONE);
            imgcat.setVisibility(View.GONE);
            texcat.setVisibility(View.GONE);
            texcart.setVisibility(View.GONE);
        }
        else
        {
            imglg.setVisibility(View.GONE);
            imgrg.setVisibility(View.GONE);
            rg.setVisibility(View.GONE);
            lg.setVisibility(View.GONE);
            imgcart.setVisibility(View.VISIBLE);
            imgcat.setVisibility(View.VISIBLE);
            texcat.setVisibility(View.VISIBLE);
            texcart.setVisibility(View.VISIBLE);
        }

        imgrg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager frag=getActivity().getSupportFragmentManager();
                frag.beginTransaction().replace(R.id.content,new RegisterFragment()).commit();

            }
        });
        fone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:918437365007"));
                startActivity(intent);
            }

        });
        emai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i=new Intent(Intent.ACTION_SEND);
                i.setData(Uri.parse("email"));
                String[] s={"to.er.preety@gmail.com"};
                i.putExtra(Intent.EXTRA_EMAIL,s);
                i.putExtra(Intent.EXTRA_SUBJECT,"Enter your Views");
                i.setType("message/rfc822");
                Intent chooser=Intent.createChooser(i,"Launch Email");
                startActivity(chooser);

            }
        });

        imglg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager frag=getActivity().getSupportFragmentManager();
                frag.beginTransaction().replace(R.id.content,new LoginFragment()).commit();

            }
        });
        imgcart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager frag=getActivity().getSupportFragmentManager();
                frag.beginTransaction().replace(R.id.content,new CartFragment()).commit();

            }
        });

        imgcat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager frag=getActivity().getSupportFragmentManager();
                frag.beginTransaction().replace(R.id.content,new CategoryFragment()).commit();

            }
        });
        texcart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager frag=getActivity().getSupportFragmentManager();
                frag.beginTransaction().replace(R.id.content,new CartFragment()).commit();

            }
        });
        rg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager frag=getActivity().getSupportFragmentManager();
                frag.beginTransaction().replace(R.id.content,new RegisterFragment()).commit();

            }
        });
        texcat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager frag=getActivity().getSupportFragmentManager();
                frag.beginTransaction().replace(R.id.content,new CategoryFragment()).commit();

            }
        });


        lg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager frag=getActivity().getSupportFragmentManager();
                frag.beginTransaction().replace(R.id.content,new LoginFragment()).commit();

            }
        });
        tl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager frag=getActivity().getSupportFragmentManager();
                frag.beginTransaction().replace(R.id.content,new ToolsFragment()).commit();

            }
        });

        imgtl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager frag=getActivity().getSupportFragmentManager();
                frag.beginTransaction().replace(R.id.content,new ToolsFragment()).commit();

            }
        });
     return view;
    }

}
