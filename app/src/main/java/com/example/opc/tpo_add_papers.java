package com.example.opc;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

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


public class tpo_add_papers extends Fragment {

    EditText pcomp,pyear;
    Button padd;
    StorageReference sr;
    DatabaseReference dbr;
    Activity context;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        context = getActivity();


        View v =  inflater.inflate(R.layout.fragment_tpo_add_papers, container, false);
        pcomp = (EditText)v.findViewById(R.id.editText1);
        pyear = (EditText)v.findViewById(R.id.editText2);
        padd = (Button)v.findViewById(R.id.btn);

        dbr= FirebaseDatabase.getInstance().getReference("Papers");
        sr= FirebaseStorage.getInstance().getReference();
        padd.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                selectPaper();
            }
        });

        return v;

    }

    private void selectPaper(){
        Intent intent=new Intent();
        intent.setType("application/pdf");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select PDF"),1);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode,Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==1 &&  data!=null && data.getData()!=null) {
            add_pdf(data.getData());
        }


    }

    private void add_pdf(Uri data){
        final ProgressDialog pd =new ProgressDialog(context);
        pd.setTitle("Uploading....");
        pd.show();
        StorageReference ref=sr.child("papers/"+System.currentTimeMillis()+".pdf");
        ref.putFile(data).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Task<Uri> uri = taskSnapshot.getStorage().getDownloadUrl();
                while(!uri.isComplete());
                Uri url =uri.getResult();

                uploadPDF paper=new uploadPDF(pcomp.getText().toString(),pyear.getText().toString(),url.toString());
                dbr.child(dbr.push().getKey()).setValue(paper);
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
