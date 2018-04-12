package com.example.prason.biratclz;

import android.content.Context;
import android.media.Image;
import android.support.v4.app.FragmentActivity;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import java.util.zip.Inflater;

/**
 * Created by Prason on 10/24/2017.
 */

public class FacilityListingBaseAdapter extends BaseAdapter {
    Context context;
    String[]img_desc;
    int[] img;
    LayoutInflater layoutInflator;
    public FacilityListingBaseAdapter(FragmentActivity activity, int[] img, String[] img_desc) {
    this.context = activity;
        this.img  = img;
        this.img_desc= img_desc;
        layoutInflator = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return img.length;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = layoutInflator.inflate(R.layout.facilitiessinglelist,parent,false);
        ImageView fac_img = (ImageView)convertView.findViewById(R.id.facImg);
        TextView fac_desc = (TextView)convertView.findViewById(R.id.facText);
       fac_img.setImageResource(img[position]);
        fac_desc.setText(img_desc[position]);
        return convertView;
    }
}
