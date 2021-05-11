package com.example.opc.ui.send;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.opc.AccountActivity;
import com.example.opc.MainActivity;
import com.example.opc.R;
import com.google.firebase.auth.FirebaseAuth;


/* this java file corresponds to sign out fragment
    sign out the currently signed in user.
* */

public class SendFragment extends Fragment {

    private SendViewModel sendViewModel;
    Activity context;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        context=getActivity();
        context.setTitle("Log out");

        sendViewModel =
                ViewModelProviders.of(this).get(SendViewModel.class);
        View root = inflater.inflate(R.layout.fragment_send, container, false);

        return root;
    }

    //this method is always callend first when sign out item(Button) in nav drawer get pressed
    public void onStart(){
        super.onStart();
        FirebaseAuth fAuth = FirebaseAuth.getInstance();
        //signing out current user
        fAuth.signOut();

        //changing intent to login page
        Intent intent=new Intent(context, MainActivity.class);
        startActivity(intent);
    }
}