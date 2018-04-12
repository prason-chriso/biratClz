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
import com.example.prason.biratclz.model.RequestNotificationDataModuleStudent;
import com.example.prason.biratclz.model.RequestNotificationDataModuleTeacher;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Prason on 9/23/2017.
 */

public class TeacherSignupFrag extends Fragment {

    EditText teacher_name, teacher_address, teacher_phone, teacher_email, teacher_pass;
    Button register;
    ProgressDialog progressDialogue;
    private String name, address, phone, email, pass;
    //initialize the firebase references
    private FirebaseDatabase firebaseDatabase;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference firebaseDatabaseForNewRequest;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return  inflater.inflate(R.layout.teacher_signuppage,container,false);
    }

    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //initialize the progressDialogue;
        progressDialogue = new ProgressDialog(getActivity());
        progressDialogue.setTitle("Teacher Signup");
        progressDialogue.setIndeterminate(true);
        progressDialogue.setProgress(ProgressDialog.STYLE_SPINNER);
        progressDialogue.setMessage("Registering...Please Wait...!!!");




        //initializing the component
        teacher_name = (EditText)view.findViewById(R.id.ettusername1);
        teacher_address = (EditText)view.findViewById(R.id.ettaddress);
        teacher_phone = (EditText)view.findViewById(R.id.ettphone);
        teacher_email = (EditText)view.findViewById(R.id.ettemail);
        teacher_pass = (EditText)view.findViewById(R.id.ettpassword);
        register = (Button)view.findViewById(R.id.signupTeacher);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialogue.show();
                name = teacher_name.getText().toString();
                address = teacher_address.getText().toString();
                phone = teacher_phone.getText().toString();
                email = teacher_email.getText().toString();
                pass = teacher_pass.getText().toString();

                //now doing the registration task
                if (name.equals("")|| address.equals("")|| phone.equals("")|| email.equals("")||pass.equals("")){
                    Toast.makeText(getActivity(), "All field are required", Toast.LENGTH_SHORT).show();
                    progressDialogue.dismiss();

                }
                else{//do the registration process
                    firebaseDatabaseForNewRequest = FirebaseDatabase.getInstance().getReference().child("pendingRequestApprovals");

                    DatabaseReference mynewRef = firebaseDatabaseForNewRequest.push();
                    String nodeKey = mynewRef.getKey();

                    final RequestNotificationDataModuleTeacher userRequest = new RequestNotificationDataModuleTeacher(nodeKey, name, email, pass, "teacher", address, Long.parseLong(phone));
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



                    /*
                    firebaseAuth = FirebaseAuth.getInstance();
                    firebaseAuth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            addDetailInDatabase(name,address,phone);
                            progressDialogue.dismiss();
                            Nullify.nullifyAllEditText(view);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getActivity(), "Failed Registratioon"+e, Toast.LENGTH_SHORT).show();
                            progressDialogue.dismiss();
                        }
                    });*/

                }

            }
        });


    }
/*
    private void addDetailInDatabase(final String name, String address, String phone) {
        String userId = firebaseAuth.getCurrentUser().getUid();
        firebaseDatabase = FirebaseDatabase.getInstance();
        RequestNotificationDataModuleTeacher teacherData = new RequestNotificationDataModuleTeacher(name,address,phone,"teacher");
        firebaseDatabase.getReference("users").child(userId).setValue(teacherData).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(getActivity(),"SuccessFully added the new admin :"+name,Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getActivity(), ""+e, Toast.LENGTH_SHORT).show();
            }
        });
    }*/
}
