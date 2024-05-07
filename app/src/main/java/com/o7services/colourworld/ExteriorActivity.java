package com.o7services.colourworld;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.o7services.colourworld.fragment.ExterFragment;
import com.o7services.colourworld.fragment.ExtrcatFragment;
import com.o7services.colourworld.fragment.TextureFragment;

public class ExteriorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exterior);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        Intent intent = getIntent();
        Toast.makeText(this,intent.getStringExtra("category"), Toast.LENGTH_SHORT).show();
        Bundle bundle = new Bundle();
        bundle.putString("category",intent.getStringExtra("category"));
        Class fragmentClass = ExtrcatFragment.class;
        try {
            Fragment fragment = (Fragment)fragmentClass.newInstance();
            fragment.setArguments(bundle);
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.exter,fragment).commit();

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

