package com.example.prason.biratclz.miscellenious;

import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

/**
 * Created by Prason on 9/24/2017.
 */

public class Nullify{


    public static void nullifyAllEditText(View view) {
        ViewGroup group = (ViewGroup)view;
        for(int i = 0; i<=group.getChildCount();i++){
            if(group.getChildAt(i) instanceof EditText){
                ((EditText) group.getChildAt(i)).setText("");
            }
        }
    }
}
