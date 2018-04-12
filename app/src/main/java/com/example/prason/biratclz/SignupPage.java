package com.example.prason.biratclz;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.astuetz.PagerSlidingTabStrip;

/**
 * Created by Prason on 9/23/2017.
 */

public class SignupPage extends AppCompatActivity{
    PagerSlidingTabStrip  pagerSlidingTabStrip ;
    ViewPager viewPager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_choice_page);
        //initializing the component
        pagerSlidingTabStrip = (PagerSlidingTabStrip)findViewById(R.id.signuppagerslidingtabstrip);
        viewPager = (ViewPager)findViewById(R.id.signupviewpager);
        FragmentManager fragmentManager = getSupportFragmentManager();
        //setting the adapter for displaying the different layour adt different tab
        viewPager.setAdapter(new CustomPagerAdapter(getApplicationContext(),fragmentManager));
        pagerSlidingTabStrip.setViewPager(viewPager);
    }
}
