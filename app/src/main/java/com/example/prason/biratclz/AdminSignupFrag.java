package com.example.prason.biratclz;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.prason.biratclz.miscellenious.Nullify;
import com.example.prason.biratclz.model.AdminDataModel;
import com.example.prason.biratclz.model.RequestNotificationDataModuleStudent;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Prason on 9/23/2017.
 */

public class AdminSignupFrag extends Fragment {

    //initializing the firebase related object references
    DatabaseReference firebaseDatabaseForNewRequest;

    //for components
    EditText name_admin, email_admin, pass_admin;
    Button register_admin;

    //for the progressDialogue
    ProgressDialog progressDialogue;

    private String userId;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return  inflater.inflate(R.layout.admin_signuppage,container,false);
    }

    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //initializing the progressDialogueBOx
        progressDialogue = new ProgressDialog(getActivity());
        progressDialogue.setMessage("Registering Please Wait ...!!!");
        progressDialogue.setProgress(ProgressDialog.STYLE_SPINNER);
        progressDialogue.setIndeterminate(true);
        progressDialogue.setTitle("Admin  Signup ");


        //initializing the component
        name_admin = (EditText)view.findViewById(R.id.etausername22);
        email_admin = (EditText)view.findViewById(R.id.etaemail22);
        pass_admin = (EditText)view.findViewById(R.id.etapassword22);
        register_admin = (Button)view.findViewById(R.id.signupAdmin);

       register_admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialogue.show();
                //getting the values
                final String name = name_admin.getText().toString();
                final String email = email_admin.getText().toString();
                final String pass = pass_admin.getText().toString();

                if(name.equals("") || email.equals("") || pass.equals("")){
                    Toast.makeText(getActivity(), "All Fields are required ", Toast.LENGTH_SHORT).show();
                    //after the work we need to close the progress dialogue box
                    progressDialogue.dismiss();
                }
                else{

                    firebaseDatabaseForNewRequest = FirebaseDatabase.getInstance().getReference().child("pendingRequestApprovals");

                    DatabaseReference mynewRef = firebaseDatabaseForNewRequest.push();
                    String nodeKey = mynewRef.getKey();

                    final RequestNotificationDataModuleAdmin userRequest = new RequestNotificationDataModuleAdmin(nodeKey, name, email, pass, "admin");
                    mynewRef.setValue(userRequest).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getActivity(), "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                            Nullify.nullifyAllEditText(view);
                            progressDialogue.dismiss();
                        }
                    }).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(getActivity(), "your request has been sent for Acceptance you will receive and email very soon", Toast.LENGTH_LONG).show();
                            Nullify.nullifyAllEditText(view);
                            progressDialogue.dismiss();
                        }
                    });


                   /*firebaseAuth = FirebaseAuth.getInstance();
                    firebaseAuth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                       //TO DO THE TASK
                            addAdminDetailInDatabase(name,task.getResult().getUser().getUid());
                            //after the work we need to close the progress dialogue box

                            Nullify.nullifyAllEditText(view); //to empty the edit
                            progressDialogue.dismiss();


                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getActivity(), "REgistration Failed"+e, Toast.LENGTH_SHORT).show();
                            //after the work we need to close the progress dialogue box

                            Nullify.nullifyAllEditText(view); //to empty the edit text
                            progressDialogue.dismiss();
                        }
                    });*/
                }
            }
        });
    }
/*
    protected  void  addAdminDetailInDatabase(final String name,String userId){
        //now setting the value of the admin user in the databse
      // userId = firebaseAuth.getCurrentUser().getUid();
        firebaseDatabase = FirebaseDatabase.getInstance();
        AdminDataModel adminDataModel = new AdminDataModel(name,"admin");
        firebaseDatabase.getReference("users").child(userId).setValue(adminDataModel).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(getActivity(), "SuccessFully added the new admin :"+name, Toast.LENGTH_SHORT).show();
            }
        });

    }*/
}