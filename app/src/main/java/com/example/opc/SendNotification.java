package com.example.opc;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;

/*this fragment is for sending notification to
particular student or to all student
* */
public class SendNotification extends Fragment {

    //declaring all required variables and all elements used in ui
    private EditText message,recId;
    private String key1,key2,key3,key4;
    private int p;
    private Button sendNotif,sendToAll;
    //getting instance of firebasedDatabase
    private FirebaseDatabase db = FirebaseDatabase.getInstance();
    //getting reference of root node of database
    private DatabaseReference rootref = db.getReference();
    //getting ref of nptification child of rootref
    private DatabaseReference userref = rootref.child("notification");
    //getting ref of student child of rootref
    private DatabaseReference studentref = rootref.child("student");


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_send_notification, container, false);

        //finding all ui elements accord to theri ids
        message = v.findViewById(R.id.notifMessage);
        recId = v.findViewById(R.id.receiversId);
        sendNotif = v.findViewById(R.id.sendNotification);
        sendToAll = v.findViewById(R.id.sendToAll);

        //sending notification to all
        sendToAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendNotifToAll();
            }
        });

        //sending notif to particulars
        sendNotif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendNotification();
            }
        });



        return v;
    }

    public void sendNotifToAll(){
        //retriving msg and receivers id from user
        String msg = message.getText().toString();
        String iid = recId.getText().toString();

        //taking current date and time
        String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        String currentTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());

        final HashMap<String,String> map = new HashMap<String, String>();

        //receivers id should be empty for sending notif to all
        if(iid.length()>0)
            Toast.makeText(getActivity(),"please remove the entered id",Toast.LENGTH_SHORT).show();
        //msg should not be empty
        else if(msg.length()<=0)
            Toast.makeText(getActivity(), "empty message!", Toast.LENGTH_SHORT).show();
        else {
            //putting date time and msg in hashmap
            map.put("notif",msg);
            map.put("date",currentDate);
            map.put("time",currentTime);

            //iterating through each student in child student of rootref
            //finding their id which is key 3 in code
            //and creating new node of that id in notif if not exist
            //pushing the map into that child
            studentref.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                    Iterator i = dataSnapshot.getChildren().iterator();
                    while (i.hasNext()) {  //for each student
                        key1 = (String) ((DataSnapshot) i.next()).getValue();
                        key2 = (String) ((DataSnapshot) i.next()).getValue();
                        key3 = (String) ((DataSnapshot) i.next()).getValue();  //id of student
                        key4 = (String) ((DataSnapshot) i.next()).getValue();

//                        Log.d("TAG", key);
                        //setting the value of the existing or newly created key under notification reference as map
                        userref.child(key3).push().setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (!task.isSuccessful()) {
                                    Toast.makeText(getActivity(), "Something Went Wrong", Toast.LENGTH_SHORT).show();
                                    p = -1;
                                }
                            }
                        });

                    }
//                    if (p != -1)
//                        Toast.makeText(getActivity(), "Notification Sent To All", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

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

    public void sendNotification(){
        //retriving msg and receivers id from user
        String msg = message.getText().toString();
        String id = recId.getText().toString().trim();
        //receivers id should not be empty for sending notif
        if(id.length()<=0)
            Toast.makeText(getActivity(), "Id should Not be Empty", Toast.LENGTH_SHORT).show();
        //msg should not be empty
        if(msg.length()<=0)
            Toast.makeText(getActivity(), "Plaese Enter some message", Toast.LENGTH_SHORT).show();
        // finding current date and time from system
        String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        String currentTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());

        //putting msg date time in hashmap
        HashMap<String,String> map = new HashMap<String, String>();
        map.put("notif",msg);
        map.put("date",currentDate);
        map.put("time",currentTime);

        //setting the value of already existing or newly created node as map value
        userref.child(id).push().setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful())
                    Toast.makeText(getActivity(), "Notification Sent", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(getActivity(),"Something Went Wrong",Toast.LENGTH_SHORT).show();
            }
        });

    }



}
