package com.example.healthcare;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
     private EditText LoginEmail, LoginPassword;
     private Button Login, Register;
     private ProgressBar progressBarMain;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
        context=this;
        Login = (Button) findViewById(R.id.Login);
        Register = (Button) findViewById(R.id.Register);
        LoginEmail = (EditText) findViewById(R.id.editTextEmailLogin);
        LoginPassword = (EditText) findViewById(R.id.editTextPasswordLogin);

        progressBarMain = (ProgressBar) findViewById(R.id.progressBarMain);



    }

    public void onRegisterClick (View view){

        switch (view.getId()){
            case R.id.Register:
            startActivity(new Intent(this, Register.class));
            break;

        }
    }
    public void onLoginClick (View view){
        switch (view.getId()){
        case R.id.Login:
            useLogin();
            break;

    }

}

    private void useLogin() {
        String Email = LoginEmail.getText().toString();
        String Password = LoginPassword.getText().toString();

        if (Email.isEmpty()) {
            LoginEmail.setError("Email required");
            LoginEmail.requestFocus();
            return;

        }
        if (Password.isEmpty()) {
            LoginPassword.setError("Please enter Password");
            LoginPassword.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(Email).matches()){
            LoginEmail.setError("Please enter a valid Email address");
            LoginEmail.requestFocus();
            return;
        }
        progressBarMain.setVisibility(View.VISIBLE);
        mAuth.signInWithEmailAndPassword(Email, Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    //redirect to user Profile
                    FirebaseDatabase fb = FirebaseDatabase.getInstance();
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    DatabaseReference r=fb.getReference("/Users/"+user.getUid()+"/Title");
                    final String[] title = {""};
                    r.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(snapshot != null){
                                Log.d("xxxxxxx",snapshot.getValue().toString());

                                switch(snapshot.getValue().toString()){
                                    case "Physician":
                                        startActivity(new Intent(context, PhysicianActivity.class));
                                        break;
                                    case "Nurse":
                                        startActivity(new Intent(context, NurseActivity.class));
                                        break;
                                    case "Pharmacist":
                                        startActivity(new Intent(context, PharmActivity.class));
                                        break;
                                    case "Patient":
                                        startActivity(new Intent(context, PatientInfoActivity.class));
                                        break;
                                    default:
                                        break;
                                        
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Toast.makeText(MainActivity.this, "Could Not Access Database", Toast.LENGTH_SHORT).show();

                        }
                    });


                    Toast.makeText(MainActivity.this, "Login successful",Toast.LENGTH_LONG).show();

                    progressBarMain.setVisibility(View.GONE);
                }else{
                    Toast.makeText(MainActivity.this, "Login Unsuccessful Please Try Again",Toast.LENGTH_LONG).show();
                    progressBarMain.setVisibility(View.GONE);
                }
            }
        });

    }
    }