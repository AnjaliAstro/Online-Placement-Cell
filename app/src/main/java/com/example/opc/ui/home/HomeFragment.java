package com.example.opc.ui.home;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;


import com.example.opc.Alumni_main;
import com.example.opc.MainActivity;
import com.example.opc.R;
import com.example.opc.TPO_add_company;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Iterator;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;

    //textview for storing notifications
    private TextView viewNotif;
    //getting instance of datatabse
    private FirebaseDatabase db = FirebaseDatabase.getInstance();
    //getting reference of root of database
    private DatabaseReference rootref = db.getReference();
    //gettong reference of notification section of database
    private  DatabaseReference notifRef = rootref.child("notification");
    //getting instance of firebase authentication
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseUser currentUser;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        //inflating layout
        View v = inflater.inflate(R.layout.fragment_home, container, false);
        //finding tectview by its id.
        viewNotif = v.findViewById(R.id.dispNotif);
        //getiing email and id from current user
        currentUser = mAuth.getCurrentUser();
        String myEmail = currentUser.getEmail();
        int index = myEmail.indexOf('@');
        String node = myEmail.substring(0,index);
        DatabaseReference mesRef = notifRef.child(node);

        //action to be happened when new botification added
        mesRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                appendNotifications(dataSnapshot);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                appendNotifications(dataSnapshot);
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

        return v;
    }

    public void appendNotifications(DataSnapshot dataSnapshot)
    {
        //declaring field of notification
        String notif,date,time;
        //iterartor for each notification
        Iterator i = dataSnapshot.getChildren().iterator();
        viewNotif.append(Html.fromHtml("<html><body>"));
        while(i.hasNext()){ //for each notif
            date = (String)((DataSnapshot) i.next()).getValue(); //notif's date
            notif = (String)((DataSnapshot) i.next()).getValue();   //notif's content
            time = (String)((DataSnapshot) i.next()).getValue();    //notif's time
            //appending it in textView (viewNotif)
            viewNotif.append(Html.fromHtml("</br><font><small>"+notif+"</small></font><br/><font color=blue><small><small>"+date+"</small></small></font><br/></font><font color=blue><small><small>"+time+"</small></small></font><br/><br/>"));

        }
        viewNotif.append(Html.fromHtml("</html></body>"));

    }
    public void onStart(){
        super.onStart();
        /*Button bt=(Button)context.findViewById(R.id.bt);
        Button bt2=(Button)context.findViewById(R.id.bt2);
        bt.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){

                Intent intent=new Intent(context, Alumni_main.class);
                //add data to the Intent object
                //intent.putExtra("text", et.getText().toStri  ng());
                //start the second activity
                startActivity(intent);
            }

        });

        bt2.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){

                Intent intent=new Intent(context, TPO_add_company.class);
                //add data to the Intent object
                //intent.putExtra("text", et.getText().toString());
                //start the second activity
                startActivity(intent);
            }

        });*/

    }
}