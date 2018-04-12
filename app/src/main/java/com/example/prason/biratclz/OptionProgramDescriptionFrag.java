package com.example.prason.biratclz;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.astuetz.PagerSlidingTabStrip;
import com.example.prason.biratclz.model.LoginLogout;

/**
 * Created by Prason on 10/24/2017.
 */

public class OptionProgramDescriptionFrag extends Fragment {

    PagerSlidingTabStrip pagerSlidingTabStrip;
    ViewPager viewPager;
    FragmentManager fm;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.option_programlayout,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        pagerSlidingTabStrip  = (PagerSlidingTabStrip)view.findViewById(R.id.programPagerSlidingTabStrip);
        viewPager = (ViewPager)view.findViewById(R.id.programViewPager);
        fm = getChildFragmentManager();
        viewPager.setAdapter(new CustomProgramPagerAdapter(fm));
        Toast.makeText(getActivity(), "ProgramFrag: "+ LoginLogout.currentUserName+ ""+LoginLogout.currentUserType, Toast.LENGTH_SHORT).show();
    }
}
