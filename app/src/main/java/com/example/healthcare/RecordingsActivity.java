package com.example.healthcare;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class RecordingsActivity extends AppCompatActivity {
    public String PatientID, UserID;
    public EditText Diagnosis, Prescription;
    private Button UpdateButton;
    private DatabaseReference Ref;
    public FirebaseDatabase fb = FirebaseDatabase.getInstance();
    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recordings);

        PatientID = getIntent().getStringExtra("PatientID");
        // Toast.makeText(PrescriptionActivity.this, PatientID,Toast.LENGTH_LONG).show();

        Diagnosis = (EditText) findViewById(R.id.diagnosisedittext);
        Prescription = (EditText) findViewById(R.id.MEDICINEedittext);
        UpdateButton = (Button) findViewById(R.id.UpdateButton);

        UserID = user.getUid();

        Ref = fb.getReference().child("/Users/"+PatientID);



        Ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String CurrentPrescription;

                if (snapshot.hasChild("/Prescription"))
                    CurrentPrescription = snapshot.child("/Prescription").getValue().toString();
                else
                    CurrentPrescription ="";

                String CurrentDiagnosis;
                if (snapshot.hasChild("/Diagnosis"))
                    CurrentDiagnosis = snapshot.child("/Diagnosis").getValue().toString();
                else
                    CurrentDiagnosis ="";

                Prescription.setText(CurrentPrescription);
                Diagnosis.setText(CurrentDiagnosis);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(RecordingsActivity.this, "Could Not Access Database", Toast.LENGTH_SHORT).show();

            }
        });

    }
    public void onUpdateClick (View view){
        switch(view.getId()){
            case (R.id.UpdateButton):
                useUpdate();

        }
    }

    private void useUpdate() {
        String prescription = Prescription.getText().toString();
        String diagnosis = Diagnosis.getText().toString();

        Ref.child("/Diagnosis").setValue(diagnosis);
        Ref.child("/Prescription").setValue(prescription).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(RecordingsActivity.this, "information updated successfully",Toast.LENGTH_LONG).show();

            }
        });
    }
}
