package com.wolfie.regcomplain;

import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class viewcomplaints extends AppCompatActivity {
ArrayList<String> myArrayList = new ArrayList<>();
ListView lv;
DatabaseReference reff;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewcomplaints);
       final ArrayAdapter<String> ad = new ArrayAdapter<String>(getApplication(),android.R.layout.simple_list_item_1,myArrayList){
           @NonNull
           @Override
           public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
               View view = super.getView(position, convertView, parent);
               TextView tv = (TextView) view.findViewById(android.R.id.text1);

               // Set the text size 25 dip for ListView each item
               tv.setTextSize(TypedValue.COMPLEX_UNIT_DIP,25);
               return view;
           }
       };
        lv= findViewById(R.id.lv);
        lv.setAdapter(ad);
        reff = FirebaseDatabase.getInstance().getReference();

        reff.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                String value=null ;
                for (DataSnapshot messageSnapshot: dataSnapshot.getChildren()) {
                    String name = (String) messageSnapshot.child("name").getValue();
                    Long ph = (Long) messageSnapshot.child("ph").getValue();
                    String txt = (String) messageSnapshot.child("txt").getValue();
                    value = "Name - "+name
                            +"\n" +"Phone Number - "+ph
                            +"\n"+"Issue - "+ txt;
                    myArrayList.add(value+"\n");
                }
               ad.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                ad.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
