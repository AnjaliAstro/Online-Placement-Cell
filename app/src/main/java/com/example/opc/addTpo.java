package com.example.opc;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;


public class addTpo extends Fragment {


    //declaring variables according to elements in ui of add tpo fragment
    private EditText name,email,joiningDate,password;
    private Button register;
    //declring authentication variable
    private FirebaseAuth mAuth;
    //getting instance of databse
    private FirebaseDatabase db = FirebaseDatabase.getInstance();
    //getting reference of root node of database
    private DatabaseReference rootref = db.getReference();
    //getting reference of chid of rootref that is 'tpo'
    private DatabaseReference userref = rootref.child("tpo");

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment.
        View v =  inflater.inflate(R.layout.fragment_add_tpo, container, false);

        //finding the elemrnts in this view corrsponding thier assigned ids
        name = v.findViewById(R.id.addTpoName);
        email = v.findViewById(R.id.addtpoEmail);
        joiningDate = v.findViewById(R.id.addTpoJd);
        password = v.findViewById(R.id.addtpoPass);
        register = v.findViewById(R.id.addregisterTPO);
        mAuth = FirebaseAuth.getInstance();

        //on clicking the register button
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //following method call will create tpo acc in firebase authentication section
                createTpoAccount();
                //following method call will add details of tpo in 'tpo' section of database
                addTpoInDatabase();
            }
        });

        return v;
    }

    public void createTpoAccount(){
        //getting email and password from user
        final String myEmail = email.getText().toString();
        final String myPassword = password.getText().toString();

        //if fields are empty then displaying Toast msg to fill it
        if(myEmail.length()<=0 || myPassword.length()<=0)
            Toast.makeText(getContext(),"please fill required fields",Toast.LENGTH_SHORT).show();

        //creating acc using firebase method
        mAuth.createUserWithEmailAndPassword(myEmail, myPassword)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.i("TAG", "createUserWithEmail:success");
                            Toast.makeText(getActivity(),"Sign Up Successful",Toast.LENGTH_SHORT).show();

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.i("TAG", "createUserWithEmail:failure", task.getException());
                            Toast.makeText(getActivity(), "Sign Up failed.", Toast.LENGTH_SHORT).show();
                        }

                        // ...
                    }
                });
    }

    public void addTpoInDatabase(){
        //getting email and password from user
        final String myEmail = email.getText().toString().trim();
        final String joinDate = joiningDate.getText().toString().trim();
        final String myname = name.getText().toString().trim();

        //getting username from email by trimming string before @
        int index = myEmail.indexOf('@');
        String dbIdOftpo = myEmail.substring(0,index);

        //if fields are empty then displaying Toast msg to fill it
        if(myEmail.length()<=0 || joinDate.length()<=0 || myname.length()<=0)
            Toast.makeText(getActivity(),"please fill required fields",Toast.LENGTH_SHORT).show();

        //putting the email,name, and all the inserted details in hashmap
        HashMap<String,String > tpoMap = new HashMap<String, String>();
        tpoMap.put("name",myname);
        tpoMap.put("email",myEmail);
        tpoMap.put("joining Date",joinDate);

        //adding data in database as child of tpo node
        userref.child(dbIdOftpo).setValue(tpoMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful())
                        Toast.makeText(getActivity(),"Registration Successfull",Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(getActivity(),"Something Went Wrong",Toast.LENGTH_SHORT).show();
            }
        });
    }

}
