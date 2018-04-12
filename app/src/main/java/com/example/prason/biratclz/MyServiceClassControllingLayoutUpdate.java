package com.example.prason.biratclz;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.prason.biratclz.miscellenious.CurrentUser;

/**
 * Created by Prason on 12/10/2017.
 */

public class MyServiceClassControllingLayoutUpdate extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
try {
    String userId = intent.getExtras().getString("ID");
    String emailId = intent.getExtras().getString("emailId");
    if (CurrentUser.getCurrentUserDetails(userId, emailId)) {
        Log.i("infor", "#892093208*#(@*@*(*#*@##################(@()))))))))))))))))#(@) ======= from controllayout service2 : Task Completed");
        stopSelf(); //stopping when the user is successfully fetched
    }
}
catch(Exception ex){
    Log.i("infor", "#892093208*#(@*@*(*#*@##################(@()))))))))))))))))#(@) ======= from controllayout service2 : "+ex);

}
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        MainPageRegulator.progressDialogShowingSigninginProgress.dismiss(); //whent he service task is completed then dismiss the dialogue
        Log.i("info","@##################(@())))))))) ::the Service is destroyed that is used for fetching the user Data using the id and the email");
        super.onDestroy();
    }
}
