package com.o7services.colourworld.fragment;


import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.o7services.colourworld.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class ColorFragment extends Fragment {
    ImageView imageView1;
    private Bitmap bmp;
    private Bitmap operation;
//CardView red;


    public ColorFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_color, container, false);

        imageView1 = v.findViewById(R.id.imageView1);

        ImageView red = v.findViewById(R.id.red);
        ImageView orange = v.findViewById(R.id.orange);
        ImageView green = v.findViewById(R.id.green);
        ImageView blue = v.findViewById(R.id.blue);
        ImageView pink = v.findViewById(R.id.pink);
        ImageView yellow = v.findViewById(R.id.yellow);
        ImageView grey = v.findViewById(R.id.grey);
        ImageView teal = v.findViewById(R.id.teal);
        //BitmapDrawable abmp = (BitmapDrawable) imageView1.getDrawable();
        //bmp = abmp.getBitmap();


        red.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


//                imageView1.setImageDrawable(getResources().getDrawable(R.color.red,null));
//
                imageView1.setBackground(getResources().getDrawable(R.color.red,null));
            }
        });
        orange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                imageView1.setImageDrawable(getResources().getDrawable(R.color.orange,null));

            }
        });

        green.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                imageView1.setImageDrawable(getResources().getDrawable(R.color.green,null));
            }
        });


        blue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageView1.setImageDrawable(getResources().getDrawable(R.color.blue,null));


            }
        });

        grey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                imageView1.setImageDrawable(getResources().getDrawable(R.color.grey,null));
            }
        });

        pink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageView1.setImageDrawable(getResources().getDrawable(R.color.pink,null));
            }
        });

        yellow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageView1.setImageDrawable(getResources().getDrawable(R.color.yellow,null));
            }
        });

        /*teal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(view.getContext(), GalleryFragment.class);
                view.getContext().startActivity(intent);
                getActivity().finish();
            }
        });*/
        return v;
    }

}






