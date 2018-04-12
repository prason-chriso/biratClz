package com.example.prason.biratclz;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;

/**
 * Created by Prason on 10/25/2017.
 */

public class CustomProgramPagerAdapter extends FragmentStatePagerAdapter {
    FragmentManager fm;

    public CustomProgramPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
       if(position==0){
           return new ProgramBTechFoodDetailFrag();
       }
       else if(position == 1){
           return new ProgramMicrobiologyDetailFrag();
       }
       else {
           return new ProgramCsitDetailFrag();
       }
    }



    @Override
    public int getCount() {
        return 3;
    }
}
