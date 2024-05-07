package com.o7services.colourworld;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.o7services.colourworld.fragment.ToolFragment;
import com.o7services.colourworld.fragment.ToolcatFragment;

public class ToolActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tool);

        Intent intent = getIntent();

        Bundle bundle = new Bundle();
        bundle.putString("tool",intent.getStringExtra("tool"));
        Class fragmentClass = ToolcatFragment.class;
        try {
            Fragment fragment = (Fragment)fragmentClass.newInstance();
            fragment.setArguments(bundle);
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.content,fragment).commit();

        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}


