
package com.example.healthcare;

        import androidx.appcompat.app.AppCompatActivity;

        import android.content.Context;
        import android.content.Intent;
        import android.os.Bundle;
        import android.view.View;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.TextView;

public class PhysicianActivity extends AppCompatActivity {
    private Button Recordings, Prescription;
    private TextView name, age , address, phone, superdoctor ,nurses, history;
    private Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient);

        Recordings = (Button) findViewById(R.id.ButtonRecordings);
        Prescription = (Button) findViewById(R.id.ButtonPrescription);
        name = (TextView) findViewById(R.id.nameLabel);
        age = (TextView) findViewById(R.id.ageLabel);
        address = (TextView) findViewById(R.id.addressLabel);
        phone = (TextView) findViewById(R.id.telephoneNumberLabel);
        superdoctor = (TextView) findViewById(R.id.doctorsLabel);
        nurses = (TextView) findViewById(R.id.nursesLabel);
        history = (TextView) findViewById(R.id.historyLabel);

        context = this;


    }
    public void onRecordingsClick (View view){
        switch (view.getId()){
            case R.id.ButtonRecordings:
                startActivity(new Intent(context, RecordingsActivity.class));
                break;
        }

    }
    public void onPrescriptionClick (View view){
        switch (view.getId()){
            case R.id.ButtonPrescription:
                startActivity(new Intent(context, PrescriptionActivity.class));
                break;
        }
    }
}
