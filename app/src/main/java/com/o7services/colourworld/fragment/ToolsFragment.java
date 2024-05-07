package com.o7services.colourworld.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.o7services.colourworld.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ToolsFragment extends Fragment {


    public ToolsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_tools, container, false);

        CardView cv=v.findViewById(R.id.sports);
        CardView cv1=v.findViewById(R.id.rollers);

        cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Bundle bundle = new Bundle();
                    bundle.putString(Intent.EXTRA_TEXT, "Brushes");

                    Fragment fragment=null;
                    Class fragmentClass = ToolcatFragment.class;
                    try {
                        fragment = (Fragment) fragmentClass.newInstance();
                        fragment.setArguments(bundle);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.content, fragment).commit();
                }
                catch (Exception e){
                    Log.e("Error",e.getMessage());
                }
            }
        });
        cv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Bundle bundle = new Bundle();
                    bundle.putString(Intent.EXTRA_TEXT, "Rollers");

                    Fragment fragment=null;
                    Class fragmentClass = ToolcatFragment.class;
                    try {
                        fragment = (Fragment) fragmentClass.newInstance();
                        fragment.setArguments(bundle);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.content, fragment).commit();
                }
                catch (Exception e){
                    Log.e("Error",e.getMessage());
                }
            }
        });


        return  v;

    }

}
