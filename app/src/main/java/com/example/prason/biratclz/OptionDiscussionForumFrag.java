package com.example.prason.biratclz;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.prason.biratclz.model.LoginLogout;
import com.example.prason.biratclz.model.MessageDataModel;
import com.firebase.ui.database.FirebaseListAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Date;

/**
 * Created by Prason on 7/17/2017.
 */

public class OptionDiscussionForumFrag extends Fragment {
    ListView messageViewer;
    EditText messageInput;
    Button send_message;

    DatabaseReference reference ;
    FirebaseListAdapter<MessageDataModel> adapter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.option_discussionforum,container,false);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        reference = FirebaseDatabase.getInstance().getReference().child("discussionForum_data");
        reference.keepSynced(true);
        messageViewer = (ListView)view.findViewById(R.id.messageViewerList);

        messageInput = (EditText)view.findViewById(R.id.messageInput);
        send_message =(Button)view.findViewById(R.id.sendMessage);

        send_message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
try{
                if (LoginLogout.currentUserName.equals(null) || LoginLogout.currentUserName.equals("")) {
                    messageInput.setText("");
                    Toast.makeText(getActivity(), "Sending Failed, Sign in Required", Toast.LENGTH_LONG).show();
                    return;
                } else {
                    String newMessage = messageInput.getText().toString();
                    String sender = LoginLogout.currentUserName;
                    String senderID = LoginLogout.currentUserId;
                    DatabaseReference post = reference.push();
                    post.child("senderId").setValue(senderID);
                    post.child("messageText").setValue(newMessage);
                    post.child("sentTimestamp").setValue(new Date().getTime());
                    post.child("messageSender").setValue(sender).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            messageInput.setText("");
                            Toast.makeText(getActivity(), "your messaege is saved in the firebase", Toast.LENGTH_SHORT).show();

                        }
                    }).addOnFailureListener(getActivity(), new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                            messageInput.setText("");
                            Toast.makeText(getActivity(), "Message Sending Failed" + FirebaseAuth.getInstance().getCurrentUser().getEmail() + " exe: " + e.toString(), Toast.LENGTH_LONG).show();

                        }
                    });

                }

            }//end of try
            catch(Exception ex){
                messageInput.setText("");
                Toast.makeText(getActivity(), "Sending Failed, Sign in Required", Toast.LENGTH_LONG).show();
            }
            }
        });

        displayMessage();
    }

    private void displayMessage() {
        try {
            if (LoginLogout.currentUserName.equals(null) || LoginLogout.currentUserName.equals("")) {
                Toast.makeText(getActivity(), "Sign in Required", Toast.LENGTH_LONG).show();
                return;
            }
            adapter = new FirebaseListAdapter<MessageDataModel>(getActivity(), MessageDataModel.class, R.layout.listviewitem_eachmessage, reference) {
                @Override
                protected void populateView(View v, MessageDataModel model, int position) {
                    TextView data = (TextView) v.findViewById(R.id.messageData);
                    TextView sender = (TextView) v.findViewById(R.id.messageSender);
                    TextView time = (TextView) v.findViewById(R.id.messageTime);

                    if (model.getUsername().equals(LoginLogout.currentUserName)) {
                        int color = data.getCurrentTextColor();
                        data.setTextColor(Color.parseColor("#920192"));
                        data.setText("" + model.getMessageText());
                        data.setGravity(Gravity.RIGHT);
                        time.setGravity(Gravity.RIGHT);
                        data.setTextColor(color);

                    } else {
                        data.setGravity(Gravity.NO_GRAVITY);
                        data.setText("" + model.getMessageText());
                        sender.setGravity(Gravity.LEFT);
                        time.setGravity(Gravity.LEFT);
                        sender.setText("" + model.getUsername());
                    }

                    time.setText(DateFormat.format("dd-mm-yy(HH:MM:SS)", model.getSentTimestamp()));

                }
            };

            messageViewer.setAdapter(adapter);

        } catch (Exception ex) {
            Log.e("dISPLAY_DISCUSSION", "))))(#(#(#((#(#((#(#((#(#(#((#(#(#( " + ex.getMessage());
        }
    }


}
