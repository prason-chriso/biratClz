package com.example.prason.biratclz;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.prason.biratclz.model.AdminDataModel;
import com.example.prason.biratclz.model.RequestNotificationDataModuleStudent;
import com.example.prason.biratclz.model.StudentDataModel;
import com.example.prason.biratclz.model.TeacherDataModel;
import com.firebase.ui.database.FirebaseListAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Prason on 11/18/2017.
 */

public class OptionRequestReviewFrag extends Fragment {

    FirebaseAuth firebaseAuth;
    FirebaseDatabase firebasedatabse;

    RequestNotificationDataModuleStudent object;

    FragmentManager fm ;
    Button confirm, cancel;
    DatabaseReference referenceToPendingRequestNode;
    FirebaseListAdapter<RequestNotificationDataModuleStudent> firebaseListAdapter;
    ListView requestDisplayingList;
    //private int REQUEST_CODE_FOR_EMAIL_SENDING=5;

    Context context;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.option_facilitiesdesc,container,false);
    }
/*
* Undder this block the request for the log in from any type of user is sent to the  admin request reviewing fragment
* and the admin is provided with the choice to either accept or delete the request
* IF the user requesst is accepted then the user is allowed to log in to the application otherwise it will not grant acceess
* If the User request is deleted then the user is not considered the authorized user for the systeem so the user wont be able to gain any access priviledges
* Further detail of the new request will be displayed in the alertdialogue when the notice is long pressed to give the admin more information to make his/her decision
* */
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        context =getActivity();
        referenceToPendingRequestNode = FirebaseDatabase.getInstance().getReference().child("pendingRequestApprovals");
        referenceToPendingRequestNode.keepSynced(true);
        requestDisplayingList = (ListView)view.findViewById(R.id.facilitesList); //faciliteslist ..named listview is reused here ...to avoid mmemory waste to create new one
        firebaseListAdapter = new FirebaseListAdapter<RequestNotificationDataModuleStudent>(getActivity(),RequestNotificationDataModuleStudent.class,R.layout.singlelist_item_for_request_approval,referenceToPendingRequestNode) {
            @Override
            protected void populateView(View v, final RequestNotificationDataModuleStudent model, final int position) {
                object = model;
                String sname = model.getRequestSenderUsername();
                final String emailID = model.getRequestSenderUserEmail();
                String pass = model.getRequestSenderUserPass();
                final String type = model.getRequestSenderUserType();
                final String nodeKey = model.getRequestSenderNodeKey();
//                Toast.makeText(context, "populating begin", Toast.LENGTH_SHORT).show();
                TextView name = (TextView)v.findViewById(R.id.requestDetailWording);


                name.setText(sname+" sends you joint Request");
                confirm = (Button)v.findViewById(R.id.requestConfirm);
                cancel = (Button)v.findViewById(R.id.requestReject);
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                            removeCurrentNode("cancel",nodeKey);
                    }
                });

                confirm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        sendEmailForJoinRequest(emailID,"Approval of Sign in To Birat Clz"
                                ,"Dear "+model.getRequestSenderUsername()+"" +
                                "" +",\nyour Request to log in to Birat college as "+type+
                                " has been approved. you can now log in to the System with following authentication " +
                                "\nMail ID: "+model.getRequestSenderUserEmail()+
                                "\nPassKey: "+model.getRequestSenderUserPass()+
                                "\nEntryPassCode: "+nodeKey.substring(1,5),nodeKey);
                    }


                });
                v.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                        builder.setTitle("Join Request "+position);
                        View myuserDetailView = View.inflate(context,R.layout.logoutpage,null);
                        TextView  name = (TextView)myuserDetailView.findViewById(R.id.namelogout);
                        TextView  email = (TextView)myuserDetailView.findViewById(R.id.emaillogout);
                        TextView address  = (TextView)myuserDetailView.findViewById(R.id.addresslogout);
                        TextView  type = (TextView)myuserDetailView.findViewById(R.id.typelogout);

                        name.setText(model.getRequestSenderUsername());
                        email.setText(model.getRequestSenderUserEmail());
                        type.setText(model.getRequestSenderUserType());
                        address.setText(model.getRequestSenderUserAddress());
                        builder.setView(myuserDetailView);

                        AlertDialog dialog= builder.create();
                        dialog.show();

                        return true;
                    }
                });
            }
        };
        //setting the adapter to the listview
        requestDisplayingList.setAdapter(firebaseListAdapter);

    }

    //block that control the email sending for the join request received

    public  void sendEmailForJoinRequest(String receiverName, String emailSubject, String emailContent, String key){
        Intent emailIntent=new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto",receiverName,null));
        emailIntent.putExtra(Intent.EXTRA_SUBJECT,emailSubject);
        emailIntent.putExtra(Intent.EXTRA_TEXT,emailContent);
        // emailIntent.putExtras(Intent.EXTRA_SUBJECT,"welcome to findnerd");
        Log.i("from_JOinReq_block","#()#(#)#)(#)(#()#))#(# BEFORE");
        context.startActivity(emailIntent);
        fm = getFragmentManager();

        removeCurrentNode("confirm",key);
        //PanelFragmentRegulator.fragmentTransaction = fm.beginTransaction();
        Log.i("from_JOinReq_block","#()#(#)#)(#)(#()#))#(# i think email is sent and the node is removed now");
    }


    /*@Override
        public void onActivityResult(int requestCode, int resultCode, Intent data) {
            Log.i("ONACtivityREsult","data include  (#)(#(#(#()#(#(#(#((#)()#(#()#((#)()#"+requestCode +"-------"+resultCode);

            if(requestCode==REQUEST_CODE_FOR_EMAIL_SENDING && requestCode == Activity.RESULT_OK){
                removeCurrentNode("confirm",object.getRequestSenderNodeKey());
                Toast.makeText(context,"Email is delivered",Toast.LENGTH_LONG).show();
                Log.i("value_intent","data include "+data.getData().toString());
                Log.i("value_intent","key include from the on activiyt resulty include "+object.getRequestSenderNodeKey());
    //PanelFragmentRegulator.fragmentTransaction = fm.beginTransaction();
            }else{
                Toast.makeText(context,"Confirmation email sending failed...Check your network",Toast.LENGTH_LONG).show();
            }
        }
    */
    private void addStudentDetail(String userId, final String name, String address, String phone, String faculty) {

        firebasedatabse = FirebaseDatabase.getInstance();
        StudentDataModel studentData = new StudentDataModel(name,address,phone,faculty,"student");
        firebasedatabse.getReference("users").child(userId).setValue(studentData).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
//we can send the email to the user for the request acceptance

               Toast.makeText(context,"SuccessFully added the new student:"+name, Toast.LENGTH_SHORT).show();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {


                Toast.makeText(context, ""+e, Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void addTeacherDetail(String userId, final String name, String address, long phone){
        firebasedatabse = FirebaseDatabase.getInstance();
        TeacherDataModel teacherData = new TeacherDataModel(name,address,""+phone,"teacher");
        firebasedatabse.getReference("users").child(userId).setValue(teacherData).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
//we can send the email to the user for the request acceptance

                Toast.makeText(context,"SuccessFully added the new Teacher:"+name, Toast.LENGTH_SHORT).show();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {


                Toast.makeText(context, ""+e, Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void addAdminDetail(String userId, final String name, String address, long phone) {

        firebasedatabse = FirebaseDatabase.getInstance();
        AdminDataModel adminData  = new AdminDataModel(name, "admin");
        firebasedatabse.getReference("users").child(userId).setValue(adminData).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
//we can send the email to the user for the request acceptance
        Toast.makeText(context,"SuccessFully added the new admin:"+name, Toast.LENGTH_SHORT).show();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(context, ""+e, Toast.LENGTH_SHORT).show();
            }
        });
    }



    private void removeCurrentNode(final String action, String key) {

        referenceToPendingRequestNode.child(key).removeValue();
        Log.i("Fromm_removecurrentNode","90()#(()#)(#()#()#()#("+action+"---"+key);
        if(action.equals("confirm")){
            //remove the node and do the task of creating neew user acount in the authentication
            firebaseAuth = FirebaseAuth.getInstance();
            firebaseAuth.createUserWithEmailAndPassword(object.getRequestSenderUserEmail(), object.getRequestSenderUserPass()).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                @Override
                public void onSuccess(AuthResult authResult) {
                    //do the task of creataing the seperate node for the users
                    if(object.getRequestSenderUserType().equals("student")){
                        addStudentDetail(authResult.getUser().getUid(),object.getRequestSenderUsername(), object.getRequestSenderUserAddress() , ""+object.getRequestSenderUserPhone(), object.getRequestSenderFaculty());
                    }
                    else if (object.getRequestSenderUserType().equals("teacher")){
                        addTeacherDetail(authResult.getUser().getUid(), object.getRequestSenderUsername(), object.getRequestSenderUserAddress() , object.getRequestSenderUserPhone());
                    }
                    else if (object.getRequestSenderUserType().equals("admin")){
                        addAdminDetail(authResult.getUser().getUid(), object.getRequestSenderUsername(), object.getRequestSenderUserAddress(), object.getRequestSenderUserPhone());
                    }

                }
            });

        }
        else if (action.equals("cancel")){
            //only remove the current node from the database done  earlier

        }
    }





}
