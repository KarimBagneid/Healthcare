package com.example.healthcare;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class PatientInfoActivity extends AppCompatActivity {
    private Button Recordings, Prescription;
    private TextView name, age , address, phone, superdoctor ,nurses, history;
    private Context context;
    private DatabaseReference Ref;
    private FirebaseDatabase fb = FirebaseDatabase.getInstance();
    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient);

        Recordings = (Button) findViewById(R.id.ButtonRecordings);
        Prescription = (Button) findViewById(R.id.ButtonPrescription);
        name = (TextView) findViewById(R.id.nameTextView);
        age = (TextView) findViewById(R.id.ageTextView);
        address = (TextView) findViewById(R.id.addressTextView);
        phone = (TextView) findViewById(R.id.telephoneNumberTextView);
        superdoctor = (TextView) findViewById(R.id.doctorsTextView);
        nurses = (TextView) findViewById(R.id.nursesTextView);
        history = (TextView) findViewById(R.id.historyTextView);


        DatabaseReference r =fb.getReference("/Users/"+user.getUid());
        r.addValueEventListener(new ValueEventListener() {
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
                Toast.makeText(PatientInfoActivity.this, "Could Not Access Database", Toast.LENGTH_SHORT).show();

            }
        });



        context = this;


    }
    public void onPatientClick (View view){
        switch (view.getId()){
            case R.id.ButtonRecordings:
                startActivity(new Intent(context, RecordingsActivity.class));
                break;
            case R.id.ButtonPrescription:
                startActivity(new Intent(context, PrescriptionActivity.class));
                break;
        }
        }

    }


