package com.example.opc;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


//A.Main Activity is a login page.

public class MainActivity extends AppCompatActivity {

    //1.declaring fields according to UI

    private EditText email,password;
    private RadioButton adminRB,tpoRB,studentRB,alumniRB;
    private Button SignIn;
    private  String myEmail,myPassword;
    private ProgressDialog dialog;
    private FirebaseAuth mAuth;   //2. Authentication Variable of firebase.
    private FirebaseDatabase db = FirebaseDatabase.getInstance();  //3.Getting instance of firebase database
    private DatabaseReference myRef = db.getReference();  //4. getting reference of root node of database.
//    private DatabaseReference adminrref = myRef.child("admin");
//    private DatabaseReference tporef = myRef.child("tpo");
//    private DatabaseReference studentref = myRef.child("student");







    @Override
    public void onStart() {
        super.onStart();
//        updateUI();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); //5. setting layout as activity main layout in layout directory

        //6.finding all the elements in ui according to their assigned ids
        mAuth = FirebaseAuth.getInstance();
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        adminRB = findViewById(R.id.admin);
        studentRB = findViewById(R.id.student);
        alumniRB = findViewById(R.id.alumni);
        tpoRB = findViewById(R.id.tpo);
        SignIn = findViewById(R.id.signIn);


        //following method call changes the uI of app according to already signed in User.
        updateUI();
    }

    //to update ui according to type of current user
    private void updateUI() {

        //getting currently signed in user from firebase and checking wether it is null or not.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser!=null){
            String myEmail = currentUser.getEmail();
            int index = myEmail.indexOf('@');
            String node = myEmail.substring(0,index);

            //if current user is in admin then opening admin page ui.
            myRef.child("admin").child(node).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if(dataSnapshot.exists()){
                        Intent intent = new Intent(MainActivity.this,adminActivity1.class);
                        startActivity(intent);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

            //if current user is in student then opening student page ui.
            myRef.child("student").child(node).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if(dataSnapshot.exists()){
                        Intent intent = new Intent(MainActivity.this,student_main.class);
                        startActivity(intent);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

            //if current user is in Alumni then opening alumni page ui.
            myRef.child("Alumni").child(node).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if(dataSnapshot.exists()){
                        Intent intent = new Intent(MainActivity.this,Alumni_main.class);
                        startActivity(intent);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

            //if current user is in tpo then opening tpo page ui.
            myRef.child("tpo").child(node).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if(dataSnapshot.exists()){
                        Intent intent = new Intent(MainActivity.this,TpoActivity.class);
                        startActivity(intent);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });


        }
        else
            Log.i("user email","null");
    }

    //following method opens admin page if the entered details are of admin
    //if not then displays msg to check type
    public void checkAdmin(){

        int index = myEmail.indexOf('@');
        String node = myEmail.substring(0,index);
        myRef.child("admin").child(node).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    mAuth.signInWithEmailAndPassword(myEmail, myPassword)
                            .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        Intent intent = new Intent(MainActivity.this, adminActivity1.class);
                                        startActivity(intent);

                                    } else {
//                                        Log.d(TAG, "signInWithEmail:failure", task.getException());
                                        Toast.makeText(MainActivity.this, "Authentication failed.",
                                                Toast.LENGTH_SHORT).show();
                                        dialog.setCanceledOnTouchOutside(true);
                                    }

                                    // ...
                                }
                            });
                }
                else
                    Toast.makeText(MainActivity.this,"Admin Account Doesnt Exist",Toast.LENGTH_SHORT).show();
                dialog.setCanceledOnTouchOutside(true);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    //following method opens tpo page if the entered details are of tpo
    //if not then displays msg to check type
    public void checkTpo(){

        int index = myEmail.indexOf('@');
        String node = myEmail.substring(0,index);

        myRef.child("tpo").child(node).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    mAuth.signInWithEmailAndPassword(myEmail, myPassword)
                            .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        Intent intent = new Intent(MainActivity.this, TpoActivity.class);
                                        startActivity(intent);

                                    } else {
//                                        Log.d(TAG, "signInWithEmail:failure", task.getException());
                                        Toast.makeText(MainActivity.this, "Authentication failed.",
                                                Toast.LENGTH_SHORT).show();
                                        dialog.setCanceledOnTouchOutside(true);
                                    }

                                    // ...
                                }
                            });
                }
                else
                    Toast.makeText(MainActivity.this,"Tpo Account Doesn't Exist",Toast.LENGTH_SHORT).show();
                dialog.setCanceledOnTouchOutside(true);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }


    //following method opens student page if the entered details are of student
    //if not then displays msg to check type
    public void checkStudent(){

        int index = myEmail.indexOf('@');
        String node = myEmail.substring(0,index);
        myRef.child("student").child(node).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    mAuth.signInWithEmailAndPassword(myEmail, myPassword)
                            .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        Intent intent = new Intent(MainActivity.this, student_main.class);
                                        startActivity(intent);

                                    } else {
//                                        Log.d(TAG, "signInWithEmail:failure", task.getException());
                                        Toast.makeText(MainActivity.this, "Authentication failed.",
                                                Toast.LENGTH_SHORT).show();
                                        dialog.setCanceledOnTouchOutside(true);
                                    }

                                    // ...
                                }
                            });
                }
                else
                    Toast.makeText(MainActivity.this,"Student Account Doesn't Exist",Toast.LENGTH_SHORT).show();
                dialog.setCanceledOnTouchOutside(true);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    //following method opens Alumni page if the entered details are of Alumni
    //if not then displays msg to check type
    public void checkAlumni(){

        int index = myEmail.indexOf('@');
        String node = myEmail.substring(0,index);
        myRef.child("Alumni").child(node).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    mAuth.signInWithEmailAndPassword(myEmail, myPassword)
                            .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        Intent intent = new Intent(MainActivity.this, Alumni_main.class);
                                        startActivity(intent);

                                    } else {
//                                        Log.d(TAG, "signInWithEmail:failure", task.getException());
                                        Toast.makeText(MainActivity.this, "Authentication failed.",
                                                Toast.LENGTH_SHORT).show();
                                        dialog.setCanceledOnTouchOutside(true);
                                    }

                                    // ...
                                }
                            });
                }
                else
                    Toast.makeText(MainActivity.this,"Alumni Account Doesn't Exist",Toast.LENGTH_SHORT).show();
                dialog.setCanceledOnTouchOutside(true);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }



    // This method will get called when sign in button get pressed

    public void signIn(View view) {
        //getting email and password entered by user

        myEmail = email.getText().toString().trim();
        myPassword = password.getText().toString().trim();
        dialog = ProgressDialog.show(MainActivity.this, "",
                "Loading. Please wait...", true);
        dialog.setCanceledOnTouchOutside(true);

        //displaying message to user if any of the field is empty

        if (myEmail.length() <= 0 || myPassword.length() <= 0)
            Toast.makeText(MainActivity.this, "please complete all the fields", Toast.LENGTH_SHORT).show();
        else{

                //changing the activity corresponding to radio button selected.
                //if not selected then displaying msg to select one.
            if(adminRB.isChecked()){
                    checkAdmin();
                }
                else if(tpoRB.isChecked()){
                    checkTpo();
                }
                else if(studentRB.isChecked()){
                    checkStudent();
                }
                else if(alumniRB.isChecked()){
                    checkAlumni();
                }
                else
                    Toast.makeText(MainActivity.this,"Opt One Of The Choice",Toast.LENGTH_SHORT).show();
            }
    }


    //the following method ensures if back button of mobile is pressed then application should get closed.

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            finishAffinity();
        }
        finish();
    }
}
