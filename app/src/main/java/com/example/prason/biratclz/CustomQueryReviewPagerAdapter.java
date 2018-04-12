package com.example.prason.biratclz;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;

/**
 * Created by Prason on 11/18/2017.
 */

class CustomQueryReviewPagerAdapter extends FragmentStatePagerAdapter
{
    Context context;

    public CustomQueryReviewPagerAdapter(Context context, FragmentManager fm) {
    super(fm);
    this.context = context;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (position==0){
            return "Query";
        }
        else{
            return "Request";
        }
    }

    @Override
    public Fragment getItem(int position) {
        if(position==0){
            return new OptionAdmissionEnquiryReviewFrag();
        }
        else{
            return new OptionRequestReviewFrag();
        }
    }

    @Override
    public int getCount() {
        return 2;
    }
}
