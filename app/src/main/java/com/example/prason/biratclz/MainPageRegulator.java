package com.example.prason.biratclz;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.NotificationCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.astuetz.PagerSlidingTabStrip;
import com.example.prason.biratclz.miscellenious.CurrentUser;
import com.example.prason.biratclz.miscellenious.MyProgressDialogueCreator;
import com.example.prason.biratclz.miscellenious.Nullify;
import com.example.prason.biratclz.model.LoginLogout;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

import static java.lang.Thread.sleep;

/**
 * Created by Prason on 9/25/2017.
 */

public class MainPageRegulator extends AppCompatActivity implements View.OnClickListener {
    // creating  constant identifier for tthe logged in state of the different user;
    public static final int LOGGED_OUT = 0;
    public static final int LOGGED_IN_ADMIN = 1;
    public static final int LOGGED_IN_TEACHER = 2;
    public static final int LOGGED_IN_STUDENT = 3;
    public static int current_logged_in_State = -1;  //represent the initialstate
    //initializing the component references
    public static PagerSlidingTabStrip pagerSlidingTabStrip;
    public static ViewPager viewPager;
    public static Toolbar toolbar;
    public static Button toolbarLoginBtn;
    //initializing the alertDialogue
    public static AlertDialog confirmRefresherDialogue;
    public static ProgressDialog progressDialogShowingSigninginProgress;
    //For the result status in he refreshAction initialized as false
    public static boolean refresshActionYesOrNo = false;
    Context context;
    //initializing the Fragment Manager for the managing the fragments
    FragmentManager fm;
    //creating the alertDialoge
    private AlertDialog dialogueForLogInSignUpInput;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainpageregulator);

        context = this;

        pagerSlidingTabStrip = (PagerSlidingTabStrip) findViewById(R.id.mainpagepagerslidingstrip);
        viewPager = (ViewPager) findViewById(R.id.mainpageviewpager);
        //check if the user has been logged in as any of the user or not
        if (current_logged_in_State == -1) {
            current_logged_in_State = LOGGED_OUT;  //when the user has not logged into the system
        }

        toolbar = (Toolbar) findViewById(R.id.mytoolbar);
        toolbarLoginBtn = (Button) findViewById(R.id.toolbarbutton);
        toolbarLoginBtn.setOnClickListener(this);
        //now setting up the toolbar as the actionbar
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setElevation(13.39f);
        actionBar.setTitle(R.string.organizationName); //setting the title of the actionbar

    }

    @Override
    protected void onStart() {
        super.onStart();
        //first initiating the fragment manager to switch the fragments on swiping
        fm = getSupportFragmentManager();
        viewPager.setAdapter(new MainPageSlidingAdapter(MainPageRegulator.this, fm));
        pagerSlidingTabStrip.setViewPager(viewPager);
        pagerSlidingTabStrip.notifyDataSetChanged();
    } //end of on Start of the activity life cycle

    //executed when the log in button of the toolbar is clicked
    @Override
    public void onClick(View v) {

        if (current_logged_in_State == LOGGED_OUT) {
            controlAllSigninigInProcess();
            //    Toast.makeText(this, "state = " + current_logged_in_State, Toast.LENGTH_SHORT).show();

        } else {
            controlAllSigningOutProcess();
            //        Toast.makeText(this, "state = " + current_logged_in_State, Toast.LENGTH_SHORT).show();

        }
    } // end of button on click for the login button on the toolbar

    private boolean updatePanelLayoutNow(final String actionFor) {

        AlertDialog.Builder confirmation = new AlertDialog.Builder(context);
        confirmation.setTitle("confirmation")
                .setMessage("You Need to Refresh app to get your priviledges...");
        confirmation.setPositiveButton("Refresh Now", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ((MainPageRegulator) context).onStart(); //refresh from here

                confirmRefresherDialogue.dismiss();
                refresshActionYesOrNo = true;
                Toast.makeText(context, "" + actionFor, Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(context, MyServiceProvider.class);
                if (actionFor.equals("Signing Out")) { //deny all notification receiving
                    stopService(intent); //after the user has successfully sighned out of the system with the refreshed layout then stop the service
                } else if (actionFor.equals("Signing in")) { //notification serrvice
                    startService(intent);//after the user has successfully sighned in to the system with the refreshed layout then start the service
                }

            }
        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(context, "your " + actionFor + " request was terminated", Toast.LENGTH_SHORT).show();
                logOutPreProcess(dialog);
                confirmRefresherDialogue.dismiss();
                refresshActionYesOrNo = false;
                progressDialogShowingSigninginProgress.dismiss();
                //no need to start the service(for new sighin actioon key ) or end the service (for sighnout operation cancelled) when the layout is not refreshed
            }
        });

        confirmRefresherDialogue = confirmation.create();
        confirmRefresherDialogue.show();

        return refresshActionYesOrNo;
    } //this block will update the layout based on the user currently logged in

    private void controlAllSigningOutProcess() {
        //creating the alertDialoge
        final AlertDialog dialogue;
        AlertDialog.Builder builder = new AlertDialog.Builder(MainPageRegulator.this);
        builder.setTitle("Are you sure to Log out?");
        final View view = getLayoutInflater().inflate(R.layout.logoutpage, null);
        TextView name, email, address, type;
        name = (TextView) view.findViewById(R.id.namelogout);
        email = (TextView) view.findViewById(R.id.emaillogout);
        address = (TextView) view.findViewById(R.id.addresslogout);
        type = (TextView) view.findViewById(R.id.typelogout);

        name.setText(LoginLogout.currentUserName);
        email.setText(LoginLogout.currentUserEmail);
        address.setText(LoginLogout.currentUserAddress);
        type.setText(LoginLogout.currentUserType);

        builder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                logOutPreProcess(dialog);

            }
        });
        dialogue = builder.setView(view).create();
        dialogue.show();


    } // block end for the  logging out operation of the user  and also update the layout

    private void logOutPreProcess(DialogInterface dialog) {
        if (updatePanelLayoutNow("Signing Out")) {

            FirebaseAuth.getInstance().signOut(); //signout the current user
            toolbarLoginBtn.setText("Log In"); //change the text to log in
            current_logged_in_State = LOGGED_OUT;
            //also we have to remove all the reference of the current users
            LoginLogout.currentUserId = LoginLogout.currentUserType = LoginLogout.currentUserAddress = LoginLogout.currentUserEmail = LoginLogout.currentUserName = LoginLogout.currentUserPhone = "";
            dialog.dismiss();
        } else {
            Toast.makeText(context, "Log out without refresh May cause your data Misuse by other user", Toast.LENGTH_LONG).show();
        }

    }

    private void controlAllSigninigInProcess() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainPageRegulator.this);
        final View view = getLayoutInflater().inflate(R.layout.loginpage, null);

        final EditText email, password;
        TextView newUserSignup;
        Button logInCheck;
        final FirebaseAuth firebaseAuth;


        email = (EditText) view.findViewById(R.id.etemail);
        password = (EditText) view.findViewById(R.id.etpassword);
        logInCheck = (Button) view.findViewById(R.id.loginButton);
        newUserSignup = (TextView) view.findViewById(R.id.newuser);

        //initializing the progress Dialogue
        progressDialogShowingSigninginProgress = MyProgressDialogueCreator.create(this, "Log In", "Signing in ...Please Wait...!!!", ProgressDialog.STYLE_SPINNER);
        /*progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Signing in ...Please Wait...!!!");
        progressDialog.setTitle("Log In");
        progressDialog.setIndeterminate(true);
        progressDialog.setProgress(ProgressDialog.STYLE_SPINNER);*/



        //intializing the firebase authentication detail
        firebaseAuth = FirebaseAuth.getInstance();

        //creating and showing the dialoge box for signining in
        builder.setView(view);
        dialogueForLogInSignUpInput = builder.create();
        dialogueForLogInSignUpInput.show();


        //handling the click listener on the text view for the signup task
        newUserSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), SignupPage.class));
            }
        });

        //when user enter the data and try to log in
        logInCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialogShowingSigninginProgress.show();
                String input_email = email.getText().toString();
                String input_pass = password.getText().toString();

                if (input_email.equals("") || input_pass.equals("")) {
                    progressDialogShowingSigninginProgress.dismiss();
                    return;
                }
                //authenticating the user name and the password;
                firebaseAuth.signInWithEmailAndPassword(input_email, input_pass)
                        .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                            @Override
                            public void onSuccess(AuthResult authResult) {

                                String uId = authResult.getUser().getUid();
                                String uEmail = authResult.getUser().getEmail();
                                LoginLogout.currentUserId = uId;

                                //    Log.i("infor","#892093208*#(@*@*(*#*@#######################@)@()@(#@)#()@(########################(@()))))))))))))))))#(@) ======= 1");
                                //CurrentUser.getCurrentUserDetails(uId, uEmail);
                                //when the sign in is success the current user information must be
                                // stored for further usees and also set the current user log in state
                                //it is done by the seperate thread created and the service is responsible for storing process
                                MyThreadControllingUserdata mythread = new MyThreadControllingUserdata(uId, uEmail);
                                Thread t1 = new Thread(mythread);
                                t1.start();
                                MainPageRegulator.toolbarLoginBtn.setText("Log Out");  //change the state of the button at the toolbaar of the mainPageRegulator
                                //  Log.i("infor","#892093208*#(@*@*(*#*@#######################@)@()@(#@)#()@(########################(@()))))))))))))))))#(@) ======= 3");
                                //   Toast.makeText(getApplicationContext(), "Sign in successful !!!" + LoginLogout.currentUserType + LoginLogout.currentUserName + LoginLogout.currentUserEmail, Toast.LENGTH_SHORT).show();
                                //       progressDialogShowingSigninginProgress.dismiss(); //shwoing sighning in process
                                Nullify.nullifyAllEditText(view);
                                dialogueForLogInSignUpInput.dismiss(); //dialogue showing the  log in view

                                updatePanelLayoutNow("Signing in");


                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialogShowingSigninginProgress.dismiss();
                        Toast.makeText(getApplicationContext(), ">>" + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });


    } //end of the controll sighnin work processs block

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }

    public class MyThreadControllingUserdata implements Runnable {
        private String userId, emailId;

        public MyThreadControllingUserdata(String userId, String emailId) {
            this.userId = userId;
            this.emailId = emailId;
        }

        @Override
        public void run() {
            Intent intent = new Intent(MainPageRegulator.this, MyServiceClassControllingLayoutUpdate.class);
            intent.putExtra("ID", userId);
            intent.putExtra("emailId", emailId);
            startService(intent);
        }
    }

}


