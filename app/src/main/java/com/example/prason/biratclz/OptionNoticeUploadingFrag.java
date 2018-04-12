package com.example.prason.biratclz;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.MultiAutoCompleteTextView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.prason.biratclz.model.LoginLogout;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Prason on 10/24/2017.
 */

public class OptionNoticeUploadingFrag extends Fragment {
    private ProgressDialog progressDialog;
    private EditText notice_subject;
    private MultiAutoCompleteTextView notice_data;
    private EditText notice_input;
    private Spinner target_faculty, target_semester;
    private String oldVal, newVal;
    private Button upload_notice;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.option_noticeuploadinglayout,container,false);
    }

    private void setValueToSpinner(Spinner spinner, int arrayname) {

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), arrayname, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        Toast.makeText(getActivity(), "noticce uplaoding: "+ LoginLogout.currentUserName+ ""+LoginLogout.currentUserType, Toast.LENGTH_SHORT).show();
        //SETTING UP THE PROGRESS BAR
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Sending query, Please Wait...");
        progressDialog.setProgress(ProgressDialog.STYLE_SPINNER);
        progressDialog.setIndeterminate(true);


        notice_subject = (EditText) view.findViewById(R.id.querysubject);
        notice_data = (MultiAutoCompleteTextView) view.findViewById(R.id.multiAutoCompleteTextView);
        notice_input = (EditText) view.findViewById(R.id.queryinput);
        target_faculty = (Spinner) view.findViewById(R.id.target_faculty);
        target_semester = (Spinner) view.findViewById(R.id.target_semester);
        setValueToSpinner(target_faculty, R.array.faculty_name);
        setValueToSpinner(target_semester, R.array.semester_name);

        upload_notice = (Button) view.findViewById(R.id.submitQuery);
        //acception input and updating the TextView
        notice_input.addTextChangedListener(new TextWatcher() {
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
                notice_data.setText(newVal);

            }
        });


        //submit
        upload_notice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                upload_notice.setEnabled(false);
                try {
                    if ((!LoginLogout.currentUserType.equals("admin") || (!LoginLogout.currentUserEmail.equals("pra@gmail.com")))) {
                        upload_notice.setEnabled(true); //enable buttorn when anyone else tries to uplaod notice
                        Toast.makeText(getActivity(), "Failed to upload Notice, Check Internet Connection and Log in Yourself For Admin", Toast.LENGTH_SHORT).show();
                        return;
                    } else {
                        String sub = notice_subject.getText().toString();
                        String data = notice_data.getText().toString();
                        String semester = target_semester.getSelectedItem().toString();
                        String faculty = target_faculty.getSelectedItem().toString();

                        if (sub.equals("") || data.equals("")) {
                            Toast.makeText(getActivity(), "All Field Are Required", Toast.LENGTH_SHORT).show();
                            return;
                        } else {
                            progressDialog.show();
                            //creating the new object for the query to upload
                            DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("notificationData").push();
                            reference.child("heading").setValue(sub);
                            reference.child("content").setValue(data);
                            reference.child("for_semester").setValue(semester);
                            reference.child("for_faculty").setValue(faculty).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        progressDialog.dismiss();
                                        upload_notice.setEnabled(true);
                                        Toast.makeText(getActivity(), "notice Uploaded", Toast.LENGTH_SHORT).show();
                                        clearAllTextField();
                                    } else {
                                        progressDialog.dismiss();
                                        upload_notice.setEnabled(true);
                                        Toast.makeText(getActivity(), "Failed to upload Notice, Check Internet Connection", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }
                    }


                } catch (Exception e) {
                    upload_notice.setEnabled(true);
                    Toast.makeText(getActivity(), "Please Login Yourself first" + e.toString(), Toast.LENGTH_SHORT).show();

                }


            }
        });


    }

    private void clearAllTextField() {
        notice_subject.setText("");
        notice_data.setText("");
        notice_input.setText("");

    }

}

