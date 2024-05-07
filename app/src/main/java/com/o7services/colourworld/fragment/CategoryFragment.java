package com.o7services.colourworld.fragment;


import android.os.Bundle;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.o7services.colourworld.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class CategoryFragment extends Fragment {

    RadioGroup radioGroup;
    RadioButton radioButton,intr,extr;

    RadioButton radioButtons;
    public CategoryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       final View v=inflater.inflate(R.layout.fragment_category, container, false);


        radioGroup=v.findViewById(R.id.radiogroup);
        ImageView intriror=v.findViewById(R.id.inter);
        ImageView extr=v.findViewById(R.id.ext);
        intriror.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.content,new IntrproFragment()).commit();
            }

        });
        extr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.content,new ExterFragment()).commit();
            }

        });

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int radio = radioGroup.getCheckedRadioButtonId();
                radioButton = v.findViewById(radio);
                String a = radioButton.getText().toString();

                Toast.makeText(getContext(), a, Toast.LENGTH_SHORT).show();
                if(a.equals("INTERIOR")){
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.content,new IntrproFragment()).commit();
                }

                if(a.equals("EXTERIOR")){
                    Toast.makeText(getContext(), radioButton.getText().toString(), Toast.LENGTH_SHORT).show();
                    FragmentManager fragmentManager=getActivity().getSupportFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.content,new ExterFragment()).commit();
                }
            }

        });

        return  v;
    }

}
