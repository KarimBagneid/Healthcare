package com.example.healthcare;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Pattern;

public class Register extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private Context context;
    private Button buttonReg ;
    private TextView banner;
    private EditText editTextFullName, editTextEmail, editTextPassword, editTextPhone, editTextAddress, editTextSpeciality, editTextBirthDate;
    private Spinner spinnerProf;
    private ProgressBar progressBarMain, progressBarReg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();
        context = this;
        buttonReg = (Button) findViewById(R.id.buttonReg);
        banner = (TextView) findViewById(R.id.RegBanner);

        editTextFullName = (EditText) findViewById(R.id.editTextFullName);
        editTextEmail= (EditText) findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        editTextPhone = (EditText) findViewById(R.id.editTextPhone);
        editTextAddress = (EditText) findViewById(R.id.editTextAddress);
        editTextSpeciality = (EditText) findViewById(R.id.editTextSpeciality);
        editTextBirthDate = (EditText) findViewById(R.id.editTextBirthDate);

        progressBarMain = (ProgressBar) findViewById(R.id.progressBarMain);
        progressBarReg = (ProgressBar) findViewById(R.id.progressBarReg);

        spinnerProf = (Spinner) findViewById(R.id.spinnerProf);


    }


    public void onbuttonRegClick (View view){

        switch (view.getId()){

            case R.id.buttonReg:
                buttonReg();
                break;
}

    }

    private void buttonReg() {
        String Full_Name = editTextFullName.getText().toString();
        String phone_Number = editTextPhone.getText().toString();
        String Email = editTextEmail.getText().toString();
        String Password = editTextPassword.getText().toString();
        String Address = editTextAddress.getText().toString();
        String Title = spinnerProf.getSelectedItem().toString();
        String speciality = editTextSpeciality.getText().toString();
        String BirthDate = editTextBirthDate.getText().toString();

        if (Full_Name.isEmpty()) {
            editTextFullName.setError("Full name required");
            editTextFullName.requestFocus();
            return;

        }
        if (phone_Number.isEmpty()) {
            editTextPhone.setError("Phone number required");
            editTextPhone.requestFocus();
            return;
        }
        if (Email.isEmpty()) {
            editTextEmail.setError("Email required");
            editTextEmail.requestFocus();
            return;

        }
        if (Password.isEmpty()) {
            editTextPassword.setError("Please enter Password");
            editTextPassword.requestFocus();
            return;

        }
        if (Address.isEmpty()) {
            editTextAddress.setError("Address required");
            editTextAddress.requestFocus();
            return;
        }
        if (BirthDate.isEmpty()) {
            editTextFullName.setError("Date of Birth required");
            editTextFullName.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(Email).matches()){
            editTextEmail.setError("Please enter a valid Email address");
            editTextEmail.requestFocus();
            return;

        }
        if(Password.length() < 6 ){
            editTextPassword.setError("Password must be at least 6 characters");
            editTextPassword.requestFocus();
            return;

        }

        progressBarReg.setVisibility(View.VISIBLE);

        mAuth.createUserWithEmailAndPassword(Email, Password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            User user = new User(Full_Name, phone_Number, Email, Address, speciality,Title, BirthDate);

                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()){
                                        Toast.makeText(Register.this, "user has registered successfully",Toast.LENGTH_LONG).show();
                                        startActivity(new Intent(context, MainActivity.class));

                                    }else{
                                        Toast.makeText(Register.this, "Registration Failed", Toast.LENGTH_LONG).show();
                                    }
                                    progressBarReg.setVisibility(View.GONE);

                                }
                            });
                        }else {
                            Toast.makeText(Register.this, "Registration Failed", Toast.LENGTH_LONG).show();
                            progressBarReg.setVisibility(View.GONE);
                        }
                    }
                });
    }
}
