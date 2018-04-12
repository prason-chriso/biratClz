package com.example.prason.biratclz;

import android.icu.text.DateFormat;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.MultiAutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.prason.biratclz.model.LoginLogout;
import com.example.prason.biratclz.model.QueryReviewingDataModel;
import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Prason on 10/24/2017.
 */

public class OptionAdmissionEnquiryReviewFrag extends Fragment {

    DatabaseReference databaseReference;
    FirebaseListAdapter<QueryReviewingDataModel> firebaseListAdapterForQuery;
    private ListView queryReviewList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.option_queryreviewingbyadmin,container,false);
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        queryReviewList = (ListView) view.findViewById(R.id.queryReviewdatalist);
        databaseReference = FirebaseDatabase.getInstance().getReference().child("queryData");
        databaseReference.keepSynced(true);

        if (displayQuery()) {
            Toast.makeText(getActivity(), "Query loaded", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getActivity(), "Query loading failed", Toast.LENGTH_SHORT).show();
        }
    }

    public boolean displayQuery() {
        //   Toast.makeText(getActivity(), "AdmissionEnquiry: "+ LoginLogout.currentUserName+ ""+LoginLogout.currentUserType, Toast.LENGTH_SHORT).show();
        try {
            if (LoginLogout.currentUserType.equalsIgnoreCase("admin")) {
                firebaseListAdapterForQuery = new FirebaseListAdapter<QueryReviewingDataModel>(getActivity(), QueryReviewingDataModel.class, R.layout.sindlequeryitem, databaseReference) {
                    @Override
                    protected void populateView(View v, QueryReviewingDataModel model, int position) {
                        MultiAutoCompleteTextView query_data = (MultiAutoCompleteTextView) v.findViewById(R.id.querydetailmessage);
                        TextView time = (TextView) v.findViewById(R.id.querysenttime);
                        TextView sender = (TextView) v.findViewById(R.id.querySender);

                        query_data.setText(model.getQueryText());
                        // time.setText(DateFormat.format("yy-mm-dd(HH:SS:MM)"),model.getSentTime());
                        time.setText(android.text.format.DateFormat.format("yy-mm-dd(HH:SS:MM)", model.getSentTime()));
                        sender.setText(model.getSenderMailID());
                    }
                };
                queryReviewList.setAdapter(firebaseListAdapterForQuery);
                return true;
            } else {
                return false;
            }
        } catch (Exception ex) {
            Toast.makeText(getActivity(), "Please Login Yourself first" + ex.getMessage(), Toast.LENGTH_SHORT).show();
            return false;
        }

    }
}
