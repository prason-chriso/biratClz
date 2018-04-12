package com.example.prason.biratclz;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.example.prason.biratclz.model.NotificationDataModule;
import com.example.prason.biratclz.model.QueryReviewingDataModel;
import com.google.firebase.database.DataSnapshot;

import java.util.Map;

/**
 * Created by Prason on 12/8/2017.
 */

public class TopLevelChildAddedEventManager {
    private DataSnapshot dataSnapshot;
    private String whoseChild;


    public NotificationDataModule getNoticesInfoSet(DataSnapshot dataSnapshot, Context context) {
        try {

            NotificationDataModule obj;
            Map<String, String> map = (Map) dataSnapshot.getValue();
            String nsemester = map.get("for_semester");
            String nfaculty = map.get("for_faculty");

            String ndata = map.get("content");
            String nhead = map.get("heading");
       /* if (nhead.equals(null) || ndata.equals(null)) {
            //    Toast.makeText(context, "null object found", Toast.LENGTH_LONG).show();
            return null;
        }*/
            obj = new NotificationDataModule(nhead, ndata, nsemester, nfaculty);
            return obj;
        } catch (Exception ex) {
            Log.e("TOPLEVELCHILDEVENTMNGER", "(#)#()#(#)#)#()##)#)(#)#(#)#)>>>NOTICES INFO SET :" + ex.getMessage());
            return null;
        }


    }

    public NotificationDataModule getFileUploadInfoSet(DataSnapshot dataSnapshot, Context context) {
        NotificationDataModule obj;
        Map<String, String> map = (Map) dataSnapshot.getValue();
        String nfaculty, nsemester, ndata;
        try {
            nfaculty = map.get("faculty");
            nsemester = map.get("semester");
            ndata = map.get("filename");
        } catch (Exception ex) {
            //Toast.makeText(context, "null object found", Toast.LENGTH_LONG).show();
            return null;
        }
        obj = new NotificationDataModule("file Uploaded: ", ndata, nsemester, nfaculty);
        return obj;
    }

    public QueryReviewingDataModel getQueryReceivedInfoSet(DataSnapshot dataSnapshot, Context context) {
        try {
            QueryReviewingDataModel obj;

            String queryText, sender, subject;
            long sentTime;
            Map<String, Object> map = (Map) dataSnapshot.getValue();
            subject = (String) map.get("querySubject");
            queryText = (String) map.get("queryText");
            sender = (String) map.get("senderMailID");
            sentTime = Long.parseLong("" + map.get("sentTime"));


            obj = new QueryReviewingDataModel(queryText, sender, subject, sentTime);
            return obj;
        } catch (Exception ex) {
            Log.e("TOPLEVELCHILDEVENTMNGER", "(#)#()#(#)#)#()##)#)(#)#(#)#)>>>NOTICES INFO SET :" + ex.getMessage());
            return null;
        }

    }
}
