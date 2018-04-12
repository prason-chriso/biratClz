package com.example.prason.biratclz;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.astuetz.PagerSlidingTabStrip;

/**
 * Created by Prason on 11/18/2017.
 */

public class OptionEnquiryReviewingFragUPdated extends AppCompatActivity {

    PagerSlidingTabStrip queryReviewPagerSlidingTab;
    ViewPager queryReviewViewPager;
    PagerAdapter pagerAdapter;
    FragmentManager fragmentManager;
    Context context;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(OptionEnquiryReviewingFragUPdated.this,PanelFragmentRegulator.class));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.option_query_and_loginrequestreview);
        queryReviewPagerSlidingTab = (PagerSlidingTabStrip)findViewById(R.id.queryreviewpagerslidingtabstrip);
        queryReviewViewPager = (ViewPager)findViewById(R.id.queryreviewviewpager);
        fragmentManager = this.getSupportFragmentManager();
    }


    @Override
    protected void onStart() {
        super.onStart();
        pagerAdapter = new CustomQueryReviewPagerAdapter(context,fragmentManager);
        queryReviewViewPager.setAdapter(pagerAdapter);
        queryReviewPagerSlidingTab.setViewPager(queryReviewViewPager);

    }
}
