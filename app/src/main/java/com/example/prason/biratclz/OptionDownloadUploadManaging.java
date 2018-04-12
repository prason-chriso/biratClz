package com.example.prason.biratclz;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.prason.biratclz.model.LoginLogout;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

/**
 * Created by Prason on 7/21/2017.
 */

public class OptionDownloadUploadManaging extends AppCompatActivity {

    private final int REQUEST_CODE_OF_UPLOAD = 0;
    Spinner forFaculty, forSemester;
    Button upload, download, okayBtn;
    TextView fileLocation;
    //creating the reference for the database and the storage
    StorageReference storageref;
    //creating the storage variable to store the uploaded file information
    String uploaded_in_faculty, uploaded_in_semester, uploaded_file_name;
    private Uri filePath;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.option_downloadbrowser);

        //    Toast.makeText(getActivity(), "Download frag: "+ LoginLogout.currentUserName+ ""+LoginLogout.currentUserType, Toast.LENGTH_SHORT).show();
        forFaculty = (Spinner) findViewById(R.id.faculty);
        forSemester = (Spinner) findViewById(R.id.semester);

        //initiating the buttons
        upload = (Button) findViewById(R.id.uploadbtn);
        download = (Button) findViewById(R.id.downloadbtn);
        okayBtn = (Button) findViewById(R.id.okaybtn);
        fileLocation = (TextView) findViewById(R.id.fileUriHolder);

        //seting up the reference for the storage and data base
        storageref = FirebaseStorage.getInstance().getReference("uploaded_data");

    }


    @Override
    protected void onStart() {
        super.onStart();
        //adding up the value to the spinner
        setValueToSpinner(forFaculty, R.array.faculty_name);
        setValueToSpinner(forSemester, R.array.semester_name);


//now the task for the downloading
        download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                AlertDialog.Builder builder = new AlertDialog.Builder(OptionDownloadUploadManaging.this);


                builder.setTitle("Browsing the file");
                builder.setPositiveButton("Download", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(OptionDownloadUploadManaging.this, "download choosen", Toast.LENGTH_LONG).show();
                    }
                }).setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(OptionDownloadUploadManaging.this, "dialog closed", Toast.LENGTH_SHORT).show();
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();

            }
        });


        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if ((LoginLogout.currentUserId.equals("") || LoginLogout.currentUserId.equals(null))) {
                        Toast.makeText(OptionDownloadUploadManaging.this, "Please log in yourself to upload the file", Toast.LENGTH_SHORT).show();
                        return;
                    } else {
                        Intent i = new Intent();

                        i.setType("application/pdf|text/*|images/*");
                        i.setAction(Intent.ACTION_GET_CONTENT);
                        startActivityForResult(i, REQUEST_CODE_OF_UPLOAD);
                    }
                } catch (Exception ex) {
                    Toast.makeText(OptionDownloadUploadManaging.this, "Please Log In first:" + ex.toString(), Toast.LENGTH_SHORT).show();
                }

            }
        });

        //perform the task of uploading when the okay button is clicked;
        okayBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                try {
                    if (filePath != null) {
                        if (fileLocation.getText().toString().contains("File uploaded")) {
                            Snackbar.make(v, "Already uploaded this file", Snackbar.LENGTH_LONG).show();
                            return;
                        }
                        final ProgressDialog progressDialog = new ProgressDialog(OptionDownloadUploadManaging.this);
                        progressDialog.setTitle("uploading...\nPlease Wait !!!");
                        progressDialog.setIndeterminate(true);
                        progressDialog.setProgress(ProgressDialog.STYLE_HORIZONTAL);
                        progressDialog.show();

                        uploaded_in_faculty = forFaculty.getSelectedItem().toString();
                        uploaded_in_semester = forSemester.getSelectedItem().toString();
                        uploaded_file_name = filePath.getLastPathSegment();
                        StorageReference fileref = storageref.child(uploaded_in_faculty + "/" + uploaded_in_semester + "/" + uploaded_file_name);
                        fileref.putFile(filePath).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                                //when the file is uploaded to the storage also store the information of the file in the database table
                                progressDialog.dismiss();
                                if (saveInfoToFireBaseTable(uploaded_in_faculty, uploaded_in_semester, uploaded_file_name)) {
                                    Snackbar.make(v, "upload Successful", Snackbar.LENGTH_LONG).show();
                                    fileLocation.setText("File uploaded From: " + fileLocation.getText());
                                } else {
                                    Snackbar.make(v, "upload UnSuccessful in the database", Snackbar.LENGTH_LONG).show();
                                }

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                progressDialog.dismiss();
                                Snackbar.make(v, "upload failed" + e.toString(), Snackbar.LENGTH_LONG).show();
                            }
                        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                            @Override
                            @SuppressWarnings("VisibleForTests")
                            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                                double progress = (taskSnapshot.getBytesTransferred()) * 100 / taskSnapshot.getTotalByteCount();
                                progressDialog.setMessage("Uploaded " + progress + " OK ");
                            }
                        });
                    } else {
                        Toast.makeText(OptionDownloadUploadManaging.this, "Chose the file to Upload First", Toast.LENGTH_LONG).show();
                    }
                } catch (NullPointerException ex) {
                    Log.e("message_NULLPOINER>>>>", "From the uplaoding section >>>>>>>>>>>>>>>>>>>>>>>>>" + ex.getMessage());
                }
            }
        });


    }

    //this block will create the new informmation table about the file uploaded in the storage;
    private boolean saveInfoToFireBaseTable(String uploaded_in_faculty, String uploaded_in_semester, String uploaded_file_name) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("uploadedFileInfo");
        databaseReference.keepSynced(true);

        try {
            if (!LoginLogout.currentUserId.equals(null)) {
                DatabaseReference post = databaseReference.push();
                post.child("faculty").setValue("" + uploaded_in_faculty);
                post.child("semester").setValue("" + uploaded_in_semester);
                post.child("filename").setValue("" + uploaded_file_name);
                post.child("uploaderID").setValue(LoginLogout.currentUserId);
                return true;
            } else {
                return false;
            }
        } catch (NullPointerException ex) {
            return false;
        }

    }

    private void setValueToSpinner(Spinner spinner, int arrayname) {

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(OptionDownloadUploadManaging.this, arrayname, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (requestCode == REQUEST_CODE_OF_UPLOAD && resultCode == RESULT_OK && data != null) {
                filePath = data.getData();
                fileLocation.setText(filePath.toString());
            }
        } catch (NullPointerException ex) {
            Log.e("message_NULLPOINER>>>>", ex.getMessage());
        }
    }


}
