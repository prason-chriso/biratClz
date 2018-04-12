package com.example.prason.biratclz;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

/**
 * Created by Prason on 10/24/2017.
 */

public class OptionFacilitiesDescriptionFrag extends Fragment {
    int[] img = {R.drawable.facilities_computerlab_icon,R.drawable.facilities_dlab_icon,R.drawable.facilities_cafeteria_icon,R.drawable.facilities_library_icon,R.drawable.facilities_seminar_icon,R.drawable.facilities_orgtieup_icon};
    String []img_desc = {"Computing Lab","Digital Logic Lab", "Cafeteria/Canteen","College Library", "Seminar/Workshop Hall","Organization MeetUp"};
    ListView facilitylist;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.option_facilitiesdesc,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        facilitylist = (ListView)view.findViewById(R.id.facilitesList);
        facilitylist.setAdapter(new FacilityListingBaseAdapter(getActivity(),img,img_desc));
    }
}
