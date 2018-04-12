package com.example.prason.biratclz;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by Prason on 10/24/2017.
 */

public class PanelFragmentRegulator extends AppCompatActivity{
    String fragmentKeySupplied;

    FragmentManager fragmentManager;
    public static FragmentTransaction fragmentTransaction;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.panelfragmentregulator);
     try {
         fragmentManager = getSupportFragmentManager();

         fragmentTransaction = fragmentManager.beginTransaction();
         fragmentKeySupplied = this.getIntent().getExtras().getString("key");
     }
     catch(Exception ex){
         Toast.makeText(this, "Your should be logged in to the application First", Toast.LENGTH_SHORT).show();
         Log.i("Error_panalFrag","()(#)(@#*#($*($*(@*($*#)@#(#)#(#_@@#(#(#_@)##"+ex);
     }
    }

    @Override
    protected void onStart() {
        super.onStart();


        //now we have to initialize the different layout for different fragment ...acording to the value of the key
        switch (fragmentKeySupplied){

            case "program_all":
                fragmentTransaction.replace(R.id.panelContainer,new OptionProgramDescriptionFrag());
                fragmentTransaction.commit();
                break;

            case "facilities_all":
                fragmentTransaction.replace(R.id.panelContainer,new OptionFacilitiesDescriptionFrag());
                fragmentTransaction.commit();
                break;

            case "admission_query_all":
                fragmentTransaction.replace(R.id.panelContainer,new OptionAdmissionEnquiry());
                fragmentTransaction.commit();
                break;

            case "notices_teacher":
            case"notices_admin":
            fragmentTransaction.replace(R.id.panelContainer,new OptionNoticeBoardFrag());
            fragmentTransaction.commit();
            break;


            case "news_events_teacher":
            case "news_events_default":
            case "news_events_admin":
                fragmentTransaction.replace(R.id.panelContainer,new OptionNewsEventsFrag());
                fragmentTransaction.commit();
                break;

            case "discussion_forum_teacher":
            case "discussion_forum_default":
            case "discussion_forum_admin":
                fragmentTransaction.replace(R.id.panelContainer,new OptionDiscussionForumFrag());
                fragmentTransaction.commit();
                break;

            case "aboutUs_all":
                fragmentTransaction.replace(R.id.panelContainer,new OptionAboutUsFrag());
                fragmentTransaction.commit();
                break;

            case "downloads_teacher":
            case "downloads_admin": // in this case the fragmen of the upload and download activity useds the sub activiy
                // for browsing the data and on the basis of tehe result the file name is to be displayed which requeres
                //the fragment to be updated later on which cant be done once the fragment is commmitted so in this case
                // we use the concept of the activity for managing such situation to avoid application crashing
                startActivity(new Intent(getApplicationContext(), OptionDownloadUploadManaging.class));
                break;

            case "notice_update_admin":
                fragmentTransaction.replace(R.id.panelContainer,new OptionNoticeUploadingFrag());
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
                break;

            case "query_review_admin":
                // fragmentTransaction.replace(R.id.panelContainer,new OptionAdmissionEnquiryReviewFrag());
               /* fragmentTransaction.replace(R.id.panelContainer, new OptionEnquiryReviewingFragUPdated());
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();*/
                startActivity(new Intent(getApplicationContext(), OptionEnquiryReviewingFragUPdated.class));

                break;
        }//end for the switch case

    }//end for the onStart method

    @Override
    protected void onResume() {
        super.onResume();
    }
}

