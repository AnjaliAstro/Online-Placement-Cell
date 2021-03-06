package com.example.opc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AccountActivity extends AppCompatActivity {
    //declaring all the variables as per requirement and ui element
    String em,idfromem;
    TextView tname,tid,tmail,tbranch;
    FirebaseAuth auth;
    DatabaseReference databaseReference;
    FirebaseDatabase rtdb;
    ListView l;
    ArrayAdapter<String> adapter;
    String[] default_items=new String[]{"Name","Email","Id","Branch"};
    FirebaseUser user;
    List<String> itemList;
    Button ep,cp;
    EditText newpass;
    String newp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setting layput as accountactivity in layout directory
        setContentView(R.layout.activity_account);
        //initializing the authentication
        auth= FirebaseAuth.getInstance();
        //getting current user from FirebaseUser
        user=FirebaseAuth.getInstance().getCurrentUser();
        //if user!= null then getting email and username
        if(user!=null){
            em=user.getEmail();
            int index=em.indexOf('@');
            idfromem=em.substring(0,index);
            Log.d("idfromemail",idfromem);
        }
        //finding all ui elements accord to their assignes ids
        l=(ListView)findViewById(R.id.listview);
        ep=(Button)findViewById(R.id.editbtn);
        cp=(Button)findViewById(R.id.confirmbtn);
        newpass=(EditText)findViewById(R.id.enternew);
        cp.setVisibility(View.GONE);
        newpass.setVisibility(View.GONE);
        itemList = new ArrayList<>();
        //getting database reference
        databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {

            //for existing data or when data get changed
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //clearing the list
                itemList.clear();
                //getting values of keys from datasnapshot
                String lname=dataSnapshot.child("student").child(idfromem).child("name").getValue(String.class);
                String lemail=dataSnapshot.child("student").child(idfromem).child("email").getValue(String.class);
                String lbranch=dataSnapshot.child("student").child(idfromem).child("branch").getValue(String.class);
                String lid=dataSnapshot.child("student").child(idfromem).child("id").getValue(String.class);

                //adding the values in list
                itemList.add("Student Name:"+lname);
                itemList.add("Institute Mail:" +lemail);
                itemList.add("Branch:" +lbranch);
                itemList.add("Institute I.D.:"+lid);

                //setting the values in listview with the help of array adapter
                adapter=new ArrayAdapter<>(AccountActivity.this,android.R.layout.simple_list_item_1,itemList);
                l.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(),"Network error! Please check your connection",Toast.LENGTH_SHORT);
            }
        });

        //setting new password fiels and change password button visible whwn clicked on edit password
        ep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cp.setVisibility(View.VISIBLE);
                newpass.setVisibility(View.VISIBLE);
                //changing password
                changePassword();
            }
        });
    }
    void changePassword(){
        //changing password with the help of updatePassword method of firebase
        cp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newp=newpass.getText().toString();
                if(user!=null ){
                    user.updatePassword(newp).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(getApplicationContext(),"Your password has been changed",Toast.LENGTH_SHORT);
                                Intent intent = new Intent(AccountActivity.this, MainActivity.class);
                                startActivity(intent);
                            }
                            else{
                                Toast.makeText(getApplicationContext(),"Your password could not been changed",Toast.LENGTH_SHORT);

                            }
                        }
                    });
                }
            }
        });

    }
}
