package com.wolfie.regcomplain;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
public class complainscreen extends AppCompatActivity {
Button view , menu;
EditText Name,PhoneNo,txt;
DatabaseReference reff;
Complaints c;
    private FirebaseAuth mAuth;
    FirebaseAuth.AuthStateListener mAuthListner;
    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListner);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complainscreen);
        mAuth = FirebaseAuth.getInstance();
        Name = findViewById(R.id.Name);
        PhoneNo = findViewById(R.id.PhoneNo);
        txt = findViewById(R.id.complaintbox);
        view = findViewById(R.id.complaint);
        view.setVisibility(View.GONE);
        menu = findViewById(R.id.menu);
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Creating the instance of PopupMenu
                PopupMenu popup = new PopupMenu(complainscreen.this,menu);
                //Inflating the Popup using xml file
                popup.getMenuInflater().inflate(R.menu.popup_menu, popup.getMenu());

                //registering popup with OnMenuItemClickListener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        int id = item.getItemId();
                        Toast.makeText(complainscreen.this,"You Clicked : " + item.getTitle(), Toast.LENGTH_SHORT).show();
                        if(id==R.id.one)
                        {
                            Intent i = new Intent(getApplicationContext(),home.class);
                            startActivity(i);
                        }
                        if(id==R.id.two)
                        {
                            Intent i = new Intent(getApplicationContext(),aboutUs.class);
                            startActivity(i);
                        }
                        return true;
                    }
                });

                popup.show();//showing popup menu
            }
        });
        mAuthListner = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                if (firebaseAuth.getCurrentUser()==null)
                {
                    startActivity(new Intent(getApplicationContext(),MainActivity.class));
                }
                else {
                    String currentuser = FirebaseAuth.getInstance().getCurrentUser().getUid();
                    if(currentuser.equals("rxT2RpCYA3Y3BiYH28jppLVdc3v2"))
                    {
                        view.setVisibility(View.VISIBLE);
                    }
                }
            }
        };

        c = new Complaints();
        reff = FirebaseDatabase.getInstance().getReference().child("Complaints");
findViewById(R.id.button3).setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        String name = Name.getText().toString();
        String p = PhoneNo.getText().toString();

        String txt1 = txt.getText().toString();
        if (TextUtils.isEmpty(name) ||TextUtils.isEmpty(p) || TextUtils.isEmpty(txt1) ) {
          Toast.makeText(getApplicationContext(),"Please fill all the fields",Toast.LENGTH_LONG).show();
            return;
        }
        else {
            Long ph = Long.parseLong(p);
        c.setName(name);
        c.setPh(ph);
        c.setTxt(txt1);
        reff.push().setValue(c);
        Toast.makeText(getApplicationContext(),"Complaint Registered",Toast.LENGTH_SHORT).show();}
    }
});
findViewById(R.id.logout).setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
                mAuth.signOut();


            }
        });
view.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
startActivity(new Intent(getApplicationContext(),viewcomplaints.class));
    }
});
    }
}

