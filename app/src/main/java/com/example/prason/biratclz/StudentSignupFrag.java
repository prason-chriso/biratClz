package com.example.prason.biratclz;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.prason.biratclz.miscellenious.Nullify;
import com.example.prason.biratclz.model.RequestNotificationDataModuleStudent;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Prason on 9/23/2017.
 */

public class StudentSignupFrag extends Fragment {
    EditText student_name, student_address, student_phone, student_email, student_pass;
    //for progressDialogue;
    ProgressDialog progressDialogue;
    private String name, address, phone, email, pass, faculty;
    private String nodeKey;
    private Button register;
    //getting the firebase references;

    private FirebaseDatabase  firebaseDatabase;
    private DatabaseReference firebaseDatabaseForNewRequest;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.student_signuppage,container,false);
    }

    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final Spinner spinner_faculty = (Spinner) view.findViewById(R.id.facultyspinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.faculty_name, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_faculty.setAdapter(adapter);

        //initialize the progressDialogue;
        progressDialogue = new ProgressDialog(getActivity());
        progressDialogue.setTitle("Student Signup");
        progressDialogue.setIndeterminate(true);
        progressDialogue.setProgress(ProgressDialog.STYLE_SPINNER);
        progressDialogue.setMessage("Registering...Please Wait...!!!");


        //initializing the Edittext;
        student_name = (EditText) view.findViewById(R.id.etsusername1);
        student_address = (EditText) view.findViewById(R.id.etsaddress);
        student_phone = (EditText) view.findViewById(R.id.etsphone);
        student_email = (EditText) view.findViewById(R.id.etsemail);
        student_pass = (EditText) view.findViewById(R.id.etspassword);
        register = (Button) view.findViewById(R.id.signupStudent);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                progressDialogue.show();
                //getting the value first

                name = student_name.getText().toString();
                address = student_address.getText().toString();
                phone = student_phone.getText().toString();
                email = student_email.getText().toString();
                pass = student_pass.getText().toString();
                faculty = spinner_faculty.getSelectedItem().toString();

                //now doing the registration task
                if (name.equals("") || address.equals("") || phone.equals("") || email.equals("") || pass.equals("")) {
                    Toast.makeText(getActivity(), "All field are required", Toast.LENGTH_SHORT).show();
                    progressDialogue.dismiss();

                } else {//do the registration process
                    //now registering the user ;

                    firebaseDatabaseForNewRequest = FirebaseDatabase.getInstance().getReference().child("pendingRequestApprovals");

                    DatabaseReference mynewRef = firebaseDatabaseForNewRequest.push();
                    String nodeKey = mynewRef.getKey();

                    final RequestNotificationDataModuleStudent userRequest = new RequestNotificationDataModuleStudent(nodeKey, name, email, pass, "student", address, faculty, Long.parseLong(phone));
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

                }
            }
        });


    }
}
