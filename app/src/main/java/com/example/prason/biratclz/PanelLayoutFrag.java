package com.example.prason.biratclz;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

/**
 * Created by Prason on 10/9/2017.
 */

public class PanelLayoutFrag extends Fragment {
        //reference for all possible options for different users;
        ImageView program, facilities, admission_query,discussion_forum ,news_events, aboutUs; //default option
        ImageView notices,downloads ;// for teacher-- additional
        ImageView notice_update, query_review; //for admin ...additional

    //now creating the bundle to transfer tthe value to the nexta activity as per the different button is clicked
    Bundle bundle;



    @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            //initialize the bundle first
        bundle = new Bundle();

            if (MainPageRegulator.current_logged_in_State == MainPageRegulator.LOGGED_IN_ADMIN) {
                Toast.makeText(getActivity(), "admin layout", Toast.LENGTH_LONG).show();
                MainPageRegulator.progressDialogShowingSigninginProgress.dismiss();
                return inflater.inflate(R.layout.panellayout_for_admin, container, false);
            } else if (MainPageRegulator.current_logged_in_State == MainPageRegulator.LOGGED_IN_TEACHER) {
                Toast.makeText(getActivity(), "Teacher layout", Toast.LENGTH_LONG).show();
                MainPageRegulator.progressDialogShowingSigninginProgress.dismiss();
                return inflater.inflate(R.layout.panellayout_for_teacher, container, false);
            } else if (MainPageRegulator.current_logged_in_State == MainPageRegulator.LOGGED_IN_STUDENT) {
                Toast.makeText(getActivity(), "student layout", Toast.LENGTH_LONG).show();
                MainPageRegulator.progressDialogShowingSigninginProgress.dismiss();
                return inflater.inflate(R.layout.panellayout_for_teacher, container, false);
            }
            // return inflater.inflate(R.layout.panellayout_for_student,container,false);
            else {
                Toast.makeText(getActivity(), "default layout", Toast.LENGTH_LONG).show();
                return inflater.inflate(R.layout.panellayout_for_loggedout, container, false); //when the user is at logged out state
            }
        } //end of the on create view block

        @Override
        public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);
            //now we have to initialize the every image view to open up the required layout in the new activity window
            if (MainPageRegulator.current_logged_in_State == MainPageRegulator.LOGGED_IN_ADMIN) {
                program = (ImageView)view.findViewById(R.id.laprograms);
                facilities = (ImageView)view.findViewById(R.id.lafailities);
                admission_query = (ImageView)view.findViewById(R.id.laadmissionenquery);
                discussion_forum  =(ImageView)view.findViewById(R.id.ladiscussionforum);
                news_events = (ImageView)view.findViewById(R.id.lanewevents);
                aboutUs = (ImageView)view.findViewById(R.id.laaboutus);
                notices = (ImageView)view.findViewById(R.id.lanotices);
                downloads = (ImageView)view.findViewById(R.id.ladownload);
                notice_update = (ImageView)view.findViewById(R.id.lauploadNotice);
                query_review = (ImageView)view.findViewById(R.id.laqueryReceived);
                //now we have define the click listener for loading the different layour as per the click event on the different choices
                discussion_forum.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        jumpForward("discussion_forum_admin");
                    }
                });

                news_events.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        jumpForward("news_events_admin");
                    }
                });

                notices.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        jumpForward("notices_admin");
                    }
                });
                notice_update.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        jumpForward("notice_update_admin");
                    }
                });
                query_review.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        jumpForward("query_review_admin");
                    }
                });
                downloads.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        jumpForward("downloads_admin");
                    }
                });


            }else if (MainPageRegulator.current_logged_in_State == MainPageRegulator.LOGGED_IN_TEACHER || MainPageRegulator.current_logged_in_State == MainPageRegulator.LOGGED_IN_STUDENT) {
                program = (ImageView)view.findViewById(R.id.programs);
                facilities = (ImageView)view.findViewById(R.id.failities);
                admission_query = (ImageView)view.findViewById(R.id.admissionenquery);
                discussion_forum  =(ImageView)view.findViewById(R.id.discussionforum);
                news_events = (ImageView)view.findViewById(R.id.newevents);
                aboutUs = (ImageView)view.findViewById(R.id.aboutus);
                notices = (ImageView)view.findViewById(R.id.notices);
                downloads = (ImageView)view.findViewById(R.id.download);
                //now we have define the click listener for loading the different layour as per the click event on the different choices
                discussion_forum.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        jumpForward("discussion_forum_teacher");
                    }
                });

                news_events.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        jumpForward("news_events_teacher");
                    }
                });

                notices.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        jumpForward("notices_teacher");
                    }
                });
                downloads.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        jumpForward("downloads_teacher");
                    }
                });

            }
            // return inflater.inflate(R.layout.panellayout_for_student,container,false);
            else {

                program = (ImageView)view.findViewById(R.id.loprograms);
                facilities = (ImageView)view.findViewById(R.id.lofailities);
                admission_query = (ImageView)view.findViewById(R.id.loadmissionenquery);
                discussion_forum  =(ImageView)view.findViewById(R.id.lodiscussionforum);
                news_events = (ImageView)view.findViewById(R.id.lonewevents);
                aboutUs = (ImageView)view.findViewById(R.id.loaboutus);
                //now we have define the click listener for loading the different layour as per the click event on the different choices
                discussion_forum.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        jumpForward("discussion_forum_default");
                    }
                });

                news_events.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        jumpForward("news_events_default");
                    }
                });
              }

              //besides, the click listener on the imageview that will require the jummped layout filled with the dynamic content to be displayyed
                // as per the the use type is declared on the respective block of different users, now here we define the click listener for the image view
            //with the static content that will be unchanged irrespective of which users have been using the applications ...total of 4 in nummber
            program.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    jumpForward("program_all");
                }
            });
            facilities.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    jumpForward("facilities_all");
                }
            });
            admission_query.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    jumpForward("admission_query_all");
                }
            });
            aboutUs.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    jumpForward("aboutUs_all");
                }
            });
        }


    //creating the common method for the operation of the intenting on button clicck
    public void jumpForward(String keyVal) {
        bundle.putString("key",keyVal);
        Intent i = new Intent(getActivity(),PanelFragmentRegulator.class);
        i.putExtras(bundle);
        startActivity(i);
    }
}
