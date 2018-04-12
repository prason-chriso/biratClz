package com.example.prason.biratclz;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.example.prason.biratclz.model.LoginLogout;
import com.example.prason.biratclz.model.NotificationDataModule;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

/**
 * Created by Prason on 10/24/2017.
 */

public class OptionNoticeBoardFrag extends Fragment {
    Context c;
    ListView noticesList;
    ArrayList<NotificationDataModule> mydatalist;
    DatabaseReference databaseReference, databaseReference1;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.option_noticeboardlayout, container, false);
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //  Toast.makeText(getActivity(), "noticeBoard frag: "+ LoginLogout.currentUserName+ ""+LoginLogout.currentUserType, Toast.LENGTH_SHORT).show();
        c = getActivity();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("uploadedFileInfo");
        databaseReference1 = FirebaseDatabase.getInstance().getReference().child("notificationData");

        mydatalist = new ArrayList<NotificationDataModule>();
        noticesList = (ListView) view.findViewById(R.id.noticeslist);
        final MyBaseAdapter baseAdapter = new MyBaseAdapter((FragmentActivity) c, mydatalist);
        noticesList.setAdapter(baseAdapter);

        databaseReference1.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                try {

                    if ((LoginLogout.currentUserId.equals("") || LoginLogout.currentUserId.equals(null))) {
                        Toast.makeText(c, "log in require for review the Notices ", Toast.LENGTH_SHORT).show();

                        return;
                    } else {
                        TopLevelChildAddedEventManager childAddedEventManager = new TopLevelChildAddedEventManager();
                        NotificationDataModule obj = childAddedEventManager.getNoticesInfoSet(dataSnapshot, c);
                        mydatalist.add(obj);
                        baseAdapter.notifyDataSetChanged();
                        // createNotificationGeneratingModule(nfaculty, nsemester, nhead + " : " + ndata, "general"); //call method to create the notification


                        //        Toast.makeText(c, "new notice added", Toast.LENGTH_LONG).show();
                    }
                } catch (Exception ex) {
                    Toast.makeText(c, "Exception at notice board" + ex.toString(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                try {

                    if ((LoginLogout.currentUserId.equals("") || LoginLogout.currentUserId.equals(null))) {
                        Toast.makeText(c, "log in require for review the Notices ", Toast.LENGTH_SHORT).show();

                        return;
                    } else {


                        NotificationDataModule obj = new TopLevelChildAddedEventManager().getFileUploadInfoSet(dataSnapshot, c);
                        mydatalist.add(obj);
                        baseAdapter.notifyDataSetChanged();
//                        createNotificationGeneratingModule(nfaculty, nsemester, ndata, "upload_notice"); //call method to create the notification

                        //        Toast.makeText(c, "new notice added", Toast.LENGTH_LONG).show();
                    }
                } catch (Exception ex) {
                    Toast.makeText(c, "Exception at notice board" + ex.toString(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
