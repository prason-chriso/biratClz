package com.example.prason.biratclz.miscellenious;

import android.app.ProgressDialog;
import android.content.Context;

/**
 * Created by Prason on 12/10/2017.
 */

public class MyProgressDialogueCreator {



    public static ProgressDialog create(Context context, String title, String message, int type){
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage(message);
        progressDialog.setTitle(title);
        progressDialog.setIndeterminate(true);
        progressDialog.setProgress(type);
        return progressDialog;
    }
}
