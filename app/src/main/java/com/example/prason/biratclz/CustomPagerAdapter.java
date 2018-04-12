package com.example.prason.biratclz;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;

/**
 * Created by Prason on 9/23/2017.
 */

class CustomPagerAdapter extends FragmentStatePagerAdapter {

    Context  applicationContext;
    public CustomPagerAdapter(Context applicationContext, FragmentManager fragmentManager) {
        super(fragmentManager);
        this.applicationContext = applicationContext;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if(position==0){
            return  "For Student";
        }
        else if(position ==1){
            return  "For Teacher";

        }
        if(position ==2){
            return  "For Admin";
        }
        else{
            return  "";
        }

    }

    @Override
    public Fragment getItem(int position) {
        if(position==0){
            return  new StudentSignupFrag();
        }
        else if(position ==1){
            return  new TeacherSignupFrag();

        }
        else if(position ==2){
            return  new AdminSignupFrag();
            }
            else{
            return  null;
        }
    }

    @Override
    public int getCount() {
        return 3;
    }
}
