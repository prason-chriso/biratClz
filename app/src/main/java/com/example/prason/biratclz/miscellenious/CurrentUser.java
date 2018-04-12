package com.example.prason.biratclz.miscellenious;

import android.util.Log;

import com.example.prason.biratclz.MainPageRegulator;
import com.example.prason.biratclz.model.LoginLogout;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

/**
 * Created by Prason on 10/12/2017.
 */

public class CurrentUser
{
    private static boolean result = false;


    public static boolean getCurrentUserDetails(final String userId, final String email) {

    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference reference = firebaseDatabase.getReference("users").child(userId);
    //for eveent on any changes made on the child to given reference
    reference.addValueEventListener(new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            Map<String, String > userdata = (Map) dataSnapshot.getValue();
            LoginLogout.currentUserName = userdata.get("name");
            LoginLogout.currentUserType = userdata.get("type");
            LoginLogout.currentUserPhone = userdata.get("phone");
            LoginLogout.currentUserAddress = userdata.get("address");
            LoginLogout.currentUserEmail = email; //from arguement;
            //now  we also have to set the user login type to distinguish the different type of the useer that has logged in successful
            if(LoginLogout.currentUserType.equals("teacher")){
                MainPageRegulator.current_logged_in_State = MainPageRegulator.LOGGED_IN_TEACHER;     }
            else if(LoginLogout.currentUserType.equals("student")){
                MainPageRegulator.current_logged_in_State = MainPageRegulator.LOGGED_IN_STUDENT;     }
            else if(LoginLogout.currentUserType.equals("admin")){
                MainPageRegulator.current_logged_in_State = MainPageRegulator.LOGGED_IN_ADMIN;       }
         //  Log.i("info ","????????????????_____###########$%%^&&***((&^%$#   info are "+MainPageRegulator.current_logged_in_State+ LoginLogout.currentUserName+LoginLogout.currentUserPhone+LoginLogout.currentUserAddress+LoginLogout.currentUserEmail+LoginLogout.currentUserType);
            //      Log.i("infor","#892093208*#(@*@*(*#*@#######################@)@()@(#@)#()@(########################(@()))))))))))))))))#(@) ======= 2");
            result = true;
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    });


        return result;
}
}
