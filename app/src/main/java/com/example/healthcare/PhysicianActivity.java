package com.example.healthcare;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class PhysicianActivity extends AppCompatActivity {
    private TextView Patient1, Patient2, Patient3;
    private DatabaseReference Ref;
    public FirebaseDatabase fb = FirebaseDatabase.getInstance();
    public Button Recordings, Prescription;
    public TextView name, age , address, phone, superdoctor ,nurses, history;
    private  Context context;
    public String PatientID;

    private ArrayList<Button> buttonArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_physician);
        buttonArrayList=new ArrayList<Button>();

        Recordings = (Button) findViewById(R.id.ButtonRecordings);
        Prescription = (Button) findViewById(R.id.ButtonPrescription);


        context = this;

        DatabaseReference r = fb.getReference("/Users/");
        ArrayList<String> idList =new ArrayList<String>();
        r.addValueEventListener(new ValueEventListener() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot d: snapshot.getChildren()) {
                    boolean isPatient =false;
                    if (d.hasChild("Title"))
                            if(d.child("Title").getValue().equals("Patient"))
                                isPatient =true;
                    if (isPatient)
                        idList.add(d.getValue().toString());

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        for (String s:idList) {
            Button b =new Button(context);
            b.setText("");
            buttonArrayList.add(b);
        }
    }


    public void onPatientClick (View view){
        setContentView(R.layout.activity_patient);


        Recordings = (Button) findViewById(R.id.ButtonRecordings);
        Prescription = (Button) findViewById(R.id.ButtonPrescription);

        switch (view.getId()){
            case R.id.Patient1:
                PatientID = "ezrWajYRenTaFQMVzjlhSUe4GDJ2";
                break;
            case R.id.Patient2:
                PatientID = "codZBHCMocPkSGgOOBd2ywF46lG3";
                break;
            case R.id.Patient3:
                PatientID = "TSsQmFOt2pN03tt8P9XYyj5mxjh1";
                break;

        }
        name = (TextView) findViewById(R.id.nameTextView);
        age = (TextView) findViewById(R.id.ageTextView);
        address = (TextView) findViewById(R.id.addressTextView);
        phone = (TextView) findViewById(R.id.telephoneNumberTextView);
        superdoctor = (TextView) findViewById(R.id.doctorsTextView);
        nurses = (TextView) findViewById(R.id.nursesTextView);
        history = (TextView) findViewById(R.id.historyTextView);


        DatabaseReference ro = fb.getReference("/Users/"+PatientID);
        ro.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                String Name = snapshot.child("/FullName").getValue().toString();
                String Age = snapshot.child("/BirthDate").getValue().toString();
                String Address = snapshot.child("/Address").getValue().toString();
                String Phone = snapshot.child("/Phone").getValue().toString();
                String History = snapshot.child("/History").getValue().toString();

                name.setText(Name);
                age.setText(Age);
                address.setText(Address);
                phone.setText(Phone);
                history.setText(History);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        switch (view.getId()){
            case(R.id.ButtonRecordings):
                Intent intent = (new Intent(context, RecordingsActivity.class));
                intent.putExtra("PatientID", PatientID);
                startActivity(intent);
                break;
            case(R.id.ButtonPrescription):
                Intent intent2 = (new Intent(context, PrescriptionActivity.class));
                intent2.putExtra("PatientID", PatientID);
                startActivity(intent2);
                break;
        }


    }


}
