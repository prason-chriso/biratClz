package com.example.prason.biratclz;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.MultiAutoCompleteTextView;
import android.widget.Toast;

import com.example.prason.biratclz.model.LoginLogout;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Date;

/**
 * Created by Prason on 10/24/2017.
 */

public class OptionAdmissionEnquiry extends Fragment {


    EditText query_subject, query_mailing, query_input;
    MultiAutoCompleteTextView query_data;
    Button send_query;

    DatabaseReference referenceDatabase;
    ProgressDialog progressDialog;

    String oldVal, newVal;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.option_admissionenquirylayout,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Toast.makeText(getActivity(), "Admission enquiry: "+ LoginLogout.currentUserName+ ""+LoginLogout.currentUserType, Toast.LENGTH_SHORT).show();
        referenceDatabase = FirebaseDatabase.getInstance().getReference().child("queryData");
        referenceDatabase.keepSynced(true);

        //SETTING UP THE PROGRESS BAR
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Sending query, Please Wait...");
        progressDialog.setProgress(ProgressDialog.STYLE_SPINNER);
        progressDialog.setIndeterminate(true);


        query_subject = (EditText) view.findViewById(R.id.querysubject);
        query_data = (MultiAutoCompleteTextView) view.findViewById(R.id.multiAutoCompleteTextView);
        query_input = (EditText) view.findViewById(R.id.queryinput);
        query_mailing = (EditText) view.findViewById(R.id.mailingAddress);
        send_query = (Button) view.findViewById(R.id.submitQuery);


        //we shouldn't allow the user to write in the querry data mmultiline textView
        query_data.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                Snackbar.make(v, "you shouldn't write here ...", Snackbar.LENGTH_LONG).show();
                return false;

            }
        });

        //acception input and updating the TextView
        query_input.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                oldVal = "" + s;
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // newVal = oldVal+""+s;
                newVal = "" + s;

            }

            @Override
            public void afterTextChanged(Editable s) {
                query_data.setText(newVal);

            }
        });


        //submit
        send_query.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String sub = query_subject.getText().toString();
                String data = query_data.getText().toString();
                String mailing_address = query_mailing.getText().toString();
                if (sub.equals("") || data.equals("") || mailing_address.equals("")) {
                    Toast.makeText(getActivity(), "All Field Are Required", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    progressDialog.show();
                    //creating the new object for the query to upload
                    DatabaseReference reference = referenceDatabase.push();
                    reference.child("querySubject").setValue(sub);
                    reference.child("queryText").setValue(data);
                    reference.child("senderMailID").setValue(mailing_address);
                    reference.child("sentTime").setValue(new Date().getTime()).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                progressDialog.dismiss();
                                Toast.makeText(getActivity(), "query sent", Toast.LENGTH_SHORT).show();
                            } else {
                                progressDialog.dismiss();
                                Toast.makeText(getActivity(), "Failed to upload Query", Toast.LENGTH_SHORT).show();
                            }

                            clearAllTextField();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(getActivity(), "Failed to upload Query" + e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });


    }

    private void clearAllTextField() {
        query_subject.setText("");
        query_data.setText("");
        query_input.setText("");
        query_mailing.setText("");
    }
}
