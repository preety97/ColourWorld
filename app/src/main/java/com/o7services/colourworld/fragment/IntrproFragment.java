package com.o7services.colourworld.fragment;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.o7services.colourworld.R;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class IntrproFragment extends Fragment {

    public ViewPager viewPager;
    public TabLayout tabLayout;
    public IntrproFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_intrpro, container, false);
        viewPager = v.findViewById(R.id.viewpager);
        tabLayout = v.findViewById(R.id.tabs);

        if (viewPager != null) {
            setupViewPager(viewPager);
            tabLayout.setupWithViewPager(viewPager);
        }

        return v;

    }

    private void setupViewPager(ViewPager viewPager) {

        Adapters adapter = new Adapters(getActivity().getSupportFragmentManager());

        TextureFragment fragment = new TextureFragment();
        adapter.addFragment(fragment, "TEXTURES");

        StencilFragment stencilFragment = new StencilFragment();
        adapter.addFragment(stencilFragment,"STENCILS");

        KidFragment kidFragment = new KidFragment();
        adapter.addFragment(kidFragment,"KIDS WORLD");
        viewPager.setAdapter(adapter);

    }
    static class Adapters extends FragmentStatePagerAdapter {
        private final List<Fragment> mFragments = new ArrayList<>();
        private final List<String> mFragmentTitles = new ArrayList<>();

        public Adapters(FragmentManager fm) {
            super(fm);
        }

        public void addFragment(Fragment fragment, String title) {
            mFragments.add(fragment);
            mFragmentTitles.add(title);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitles.get(position);
        }
    }


}
