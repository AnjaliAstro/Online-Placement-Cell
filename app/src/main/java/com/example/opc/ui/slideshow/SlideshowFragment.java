package com.example.opc.ui.slideshow;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.opc.R;
import com.example.opc.company_details;
import com.example.opc.uploadJAF;
import com.example.opc.uploadPDF;
import com.example.opc.view_company;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class SlideshowFragment extends Fragment {
    private SlideshowViewModel slideshowViewModel;
    Activity context;
    DatabaseReference databaseReference;
    EditText ecomp,eyear;
    String scomp,syear;
    String name_pdf,com_pdf,year_pdf,url_pdf;
    Button btnshow;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        context=getActivity();
        context.setTitle("Previous year papers");
        slideshowViewModel =
                ViewModelProviders.of(this).get(SlideshowViewModel.class);
        View root = inflater.inflate(R.layout.fragment_slideshow, container, false);
        ecomp=(EditText)root.findViewById(R.id.editcom);
        eyear=(EditText)root.findViewById(R.id.edityear);
        btnshow=(Button)root.findViewById(R.id.showbtn);
        btnshow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                scomp=ecomp.getText().toString();
                syear=eyear.getText().toString();
                viewPaper();
            }
        });
        return root;
    }

    private void viewPaper() {

        databaseReference= FirebaseDatabase.getInstance().getReference("Papers");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot postSnapshot:dataSnapshot.getChildren()){
                    //Log.i("valuedkfnkdnf",postSnapshot.getValue("year"));
                    uploadPDF uploadPDF = postSnapshot.getValue(com.example.opc.uploadPDF.class);
                    //name_pdf=uploadPDF.getName();
                    com_pdf=uploadPDF.getCompany();
                    year_pdf=uploadPDF.getYear();
                    url_pdf=uploadPDF.getUrl();
                    Log.i("com",com_pdf);
                    Log.i("year",year_pdf);
                    Log.i("url",url_pdf);
                    if(com_pdf.equals(scomp) && year_pdf.equals(syear)){
                        Intent intent=new Intent();
                        //intent.setType(Intent.ACTION_VIEW);
                        //intent.setData(Uri.parse(uploadPDF.getUrl()));

                        intent.setDataAndType(Uri.parse(uploadPDF.getUrl()),Intent.ACTION_VIEW);
                        startActivity(intent);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.i("valuedkfnkdnf","dg");

            }
        });
    }


}

