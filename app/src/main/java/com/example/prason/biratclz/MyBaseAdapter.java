package com.example.prason.biratclz;

import android.content.Context;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.prason.biratclz.model.NotificationDataModule;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by Prason on 7/22/2017.
 */

class MyBaseAdapter extends BaseAdapter {
    Context context;

    ArrayList<NotificationDataModule> mydatalist;
    LayoutInflater inflator;
    private StorageReference storageReference;

    public MyBaseAdapter(FragmentActivity activity, ArrayList<NotificationDataModule> mydatalist) {
        this.context = activity;
        this.mydatalist = mydatalist;
        inflator = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return mydatalist.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = inflator.inflate(R.layout.notficationeachlist, parent, false);
        String head = mydatalist.get(position).getNotficationHeading();
        final String fac = mydatalist.get(position).getTarget_faculty();
        final String sem = mydatalist.get(position).getTarge_semester();
        final String content = mydatalist.get(position).getNotificationContent();


        TextView notificationhead = (TextView) convertView.findViewById(R.id.text1);
        TextView notifictiondata = (TextView) convertView.findViewById(R.id.text3);
        notificationhead.setText(head + " For " + fac + " : " + sem);
        notifictiondata.setText(content);

        if (mydatalist.get(position).getNotficationHeading().contains("loaded")) { //loaded is the part of the word "file uploaded: ""
            convertView.setOnLongClickListener(new View.OnLongClickListener() {

                @Override
                public boolean onLongClick(View v) {
                    Toast.makeText(context, "Download is initiated for :" + content + " will be complete in few minutes", Toast.LENGTH_LONG).show();
                    //perform the task for the downloading the file.....
                    //GETTING THE REFERENCE FOR THE FIREBASE STORAGE
                    storageReference = FirebaseStorage.getInstance().getReference("uploaded_data").child(fac).child(sem).child(content); //content variable contain file name in case of the file notification
                    final File root = new File(Environment.getExternalStorageDirectory(), "file_download");
                    if (!root.exists()) {
                        root.mkdirs();

                    }
                    File downloadedfileName = new File(root, content);
                    storageReference.getFile(downloadedfileName).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                            Toast.makeText(context, "file downloaded to " + root.getPath(), Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(context, "Failed to download " + storageReference.getPath() + "" + e.toString(), Toast.LENGTH_LONG).show();
                        }
                    });

                    return true;
                }
            });
        }

        return convertView;
    }
}
