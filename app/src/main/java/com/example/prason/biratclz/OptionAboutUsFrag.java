package com.example.prason.biratclz;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.prason.biratclz.model.LoginLogout;

/**
 * Created by Prason on 10/24/2017.
 */

public class OptionAboutUsFrag extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.option_aboutus,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Toast.makeText(getActivity(), "about Us frag: "+ LoginLogout.currentUserName+ ""+LoginLogout.currentUserType, Toast.LENGTH_SHORT).show();

    }
}
