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
import android.widget.LinearLayout;
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
    LinearLayout lolo;
    private ArrayList<Button> buttonArrayList;
    private Button buttonR,buttonP;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_physician);
        buttonArrayList=new ArrayList<Button>();

        Recordings = (Button) findViewById(R.id.ButtonRecordings);



        context = this;
        lolo = findViewById(R.id.Lolo);
        DatabaseReference r = fb.getReference("/Users/");
        ArrayList<String> idList =new ArrayList<String>();
        r.addValueEventListener(new ValueEventListener() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot d: snapshot.getChildren()) {
                    boolean isPatient = false;
                    if (d.hasChild("Title"))
                        if (d.child("Title").getValue().equals("Patient"))
                            isPatient = true;
                    if (isPatient) {
                        idList.add(d.getKey());
                        Button b =new Button(context);
                        b.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                onPatientClick(d.getKey());
                            }
                        });
                        b.setText(d.child("FullName").getValue().toString());
                        lolo.addView(b);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    public void onPatientClick (String p){
        PatientID = p;

        setContentView(R.layout.activity_patient);

        Recordings = (Button) findViewById(R.id.ButtonRecordings);


        name = (TextView) findViewById(R.id.nameTextView);
        age = (TextView) findViewById(R.id.ageTextView);
        address = (TextView) findViewById(R.id.addressTextView);
        phone = (TextView) findViewById(R.id.telephoneNumberTextView);
        superdoctor = (TextView) findViewById(R.id.doctorsTextView);
        nurses = (TextView) findViewById(R.id.nursesTextView);
        history = (TextView) findViewById(R.id.historyTextView);

        Log.d("koloxxxxxx",PatientID);

        DatabaseReference ro = fb.getReference("/Users/"+PatientID);
        ro.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                String Name = snapshot.child("/FullName").getValue().toString();
                String Age = snapshot.child("/BirthDate").getValue().toString();
                String Address = snapshot.child("/Address").getValue().toString();
                String Phone = snapshot.child("/Phone").getValue().toString();
                String History;
                if (snapshot.hasChild("/History"))
                    History = snapshot.child("/History").getValue().toString();
                else
                    History ="";

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



        buttonR =findViewById(R.id.ButtonRecordings);
        buttonR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = (new Intent(context, RecordingsActivity.class));
                intent.putExtra("PatientID", PatientID);
                startActivity(intent);
            }
        });



    }




}
