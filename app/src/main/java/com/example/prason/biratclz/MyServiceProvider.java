package com.example.prason.biratclz;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.widget.Toast;

import com.example.prason.biratclz.model.NotificationDataModule;
import com.example.prason.biratclz.model.QueryReviewingDataModel;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Prason on 7/24/2017.
 */

public class MyServiceProvider extends Service {

    NotificationManager notificationManager;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {

        return null;
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(this, "service is started dear for nitice///", Toast.LENGTH_SHORT).show();
        MyThread thread1 = new MyThread(startId, intent);
        Thread t1 = new Thread(thread1);
        t1.start();
        //we also need to define the seervvice in the manifeest file
        return Service.START_STICKY;

    }

    @Override
    public void onDestroy() {
        Toast.makeText(this, "service is destroyed dear for notice///", Toast.LENGTH_SHORT).show();
        super.onDestroy();
    }


    private class MyThread implements Runnable {
        DatabaseReference databaseReference, databaseReference1, databaseReference2;
        NotificationDataModule notificationdataholder;
        private int serviceID;
        private Intent intent;

        public MyThread(int serviceID, Intent intent) {
            this.serviceID = serviceID;
            this.intent = intent;
            databaseReference = FirebaseDatabase.getInstance().getReference().child("uploadedFileInfo");
            databaseReference1 = FirebaseDatabase.getInstance().getReference().child("notificationData");
            databaseReference2 = FirebaseDatabase.getInstance().getReference().child("queryData");
        }

        @Override
        public void run() {
            generateChildEventListener(databaseReference, "uploadRef");
            generateChildEventListener(databaseReference1, "generalNoticeRef");
            generateChildEventListener(databaseReference2, "newQueryDataRef");

        }

        private void generateChildEventListener(final DatabaseReference databaseReference, final String refKey) {

            databaseReference.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                    TopLevelChildAddedEventManager obj = new TopLevelChildAddedEventManager();
                    if (refKey.contains("upload")) {
                        notificationdataholder = obj.getFileUploadInfoSet(dataSnapshot, MyServiceProvider.this);
                    } else if (refKey.contains("general")) {
                        notificationdataholder = obj.getNoticesInfoSet(dataSnapshot, MyServiceProvider.this);
                    } else if (refKey.contains("newQuery")) {
                        QueryReviewingDataModel qrdm = obj.getQueryReceivedInfoSet(dataSnapshot, MyServiceProvider.this);
                        notificationdataholder = new NotificationDataModule("Incoming Query (BiratClz)", qrdm.getQuerySubject());
                    }
                    notificationFactory(notificationdataholder, refKey);
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


        public void notificationFactory(NotificationDataModule dataModule, String refKey) {
            notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            //for navigating to the activity i.e defining the notification actions
            Intent actionintent = new Intent(MyServiceProvider.this, PanelFragmentRegulator.class);
            //   PendingIntent pendingIntent = PendingIntent.getActivity(MyServiceProvider.this, 2, intent, 0);
            //displaying the notificcatioon
            NotificationCompat.Builder builder = new NotificationCompat.Builder(MyServiceProvider.this)
                    //                   .setContentTitle("BiratCLZ:" + dataModule.getNotficationHeading())
                    .setContentText(":-> \n" + dataModule.getNotificationContent())
                    .setSmallIcon(R.mipmap.ic_launcher);
            if (refKey.contains("upload")) {
                builder.setContentTitle("BiratCLZ: uploaded " + dataModule.getNotficationHeading());
            } else if (refKey.contains("general")) {
                builder.setContentTitle("BiratCLZ:" + dataModule.getNotficationHeading());

            } else if (refKey.contains("newQuery")) {
                builder.setContentTitle("BiratCLZ: Query received: " + dataModule.getNotficationHeading());

            }


            TaskStackBuilder taskStackBuilder = TaskStackBuilder.create(MyServiceProvider.this);
            taskStackBuilder.addParentStack(MainActivity.class);
            taskStackBuilder.addNextIntent(actionintent);
            PendingIntent pendingIntent = taskStackBuilder.getPendingIntent(000, PendingIntent.FLAG_UPDATE_CURRENT);
            builder.setContentIntent(pendingIntent);
            Notification notification = builder.build();
            notification.flags |= Notification.FLAG_AUTO_CANCEL;
            notification.flags |= Notification.FLAG_SHOW_LIGHTS;
            notification.defaults |= Notification.DEFAULT_SOUND;
            notification.defaults |= Notification.DEFAULT_LIGHTS;
            notification.defaults |= Notification.DEFAULT_VIBRATE;

            notificationManager.notify((int) System.currentTimeMillis() + 22, notification);

        }
    }


}
