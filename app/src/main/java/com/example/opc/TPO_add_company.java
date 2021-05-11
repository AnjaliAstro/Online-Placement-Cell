package com.example.opc;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;




public class TPO_add_company extends Fragment {
    EditText cname,cstip,cjob,clink,cjaf;
    Button cadd;
    StorageReference sr;
    DatabaseReference dbr;
    Activity context;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        context = getActivity();
        View v =  inflater.inflate(R.layout.fragment_tpo_add_company, container, false);
        cname=(EditText)v.findViewById(R.id.editText1);

        cstip=(EditText)v.findViewById(R.id.editText2);
        cjob=(EditText)v.findViewById(R.id.editText3);
        clink=(EditText)v.findViewById(R.id.editText4);

        cadd=(Button)v.findViewById(R.id.button);

        dbr= FirebaseDatabase.getInstance().getReference("Company");
        sr= FirebaseStorage.getInstance().getReference();

        cadd.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                selectJaf();
            }
        });




        return v;
    }

    private void selectJaf(){
        Intent intent=new Intent();
        intent.setType("application/pdf");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select PDF for jaf"),1);
    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==1 && data!=null && data.getData()!=null) {
            add_jaf(data.getData());
        }


    }

    private void add_jaf(Uri data){
        final ProgressDialog pd =new ProgressDialog(context);
        pd.setTitle("Uploading....");
        pd.show();
        StorageReference ref=sr.child("jafs/"+System.currentTimeMillis()+".pdf");
        ref.putFile(data).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Task<Uri> uri = taskSnapshot.getStorage().getDownloadUrl();
                while(!uri.isComplete());
                Uri url =uri.getResult();

                uploadJAF paper=new uploadJAF(cname.getText().toString(),cstip.getText().toString(),cjob.getText().toString(),clink.getText().toString(),url.toString());
                dbr.child(dbr.child(cname.getText().toString()).getKey()).setValue(paper);
                Toast.makeText(context,"Paper uploaded",Toast.LENGTH_SHORT).show();
                pd.dismiss();
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                double progress=(100.0*taskSnapshot.getBytesTransferred())/taskSnapshot.getTotalByteCount();
                pd.setMessage("Uploaded: "+(int)progress+"%");
            }
        });


    }











}
