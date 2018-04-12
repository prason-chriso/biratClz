package com.example.prason.biratclz;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;

/**
 * Created by Prason on 10/9/2017.
 */

class MainPageSlidingAdapter extends FragmentStatePagerAdapter {

    Context context;
    public MainPageSlidingAdapter(Context context, FragmentManager fm)
    {
        super(fm);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        if(position==0)
            return new HomeLayoutFrag();
        else
            return new PanelLayoutFrag();
       }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (position==0)
            return "HOME";
        else
            return "PANEL";
    }
}
