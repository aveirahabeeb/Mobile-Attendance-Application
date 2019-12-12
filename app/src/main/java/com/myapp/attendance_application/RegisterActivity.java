package com.myapp.attendance_application;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class RegisterActivity extends AppCompatActivity {

    EditText email_register;
    EditText Password_register;
    EditText confirmPassword_register;
    Button Register;
    TextView Login;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        firebaseAuth = FirebaseAuth.getInstance();
        email_register =  findViewById(R.id.txtEmail);
        Password_register =  findViewById(R.id.txtPassword);
        confirmPassword_register = findViewById(R.id.txtconfirmPassword);
        Register = findViewById(R.id.btnRegister);
        Login =  findViewById(R.id.btnLogin);


        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email = email_register.getText().toString().trim();
                String password = Password_register.getText().toString().trim();
                String confirmPassword = confirmPassword_register.getText().toString().trim();

                if(email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                    Toast.makeText(RegisterActivity.this, "Fill up fields", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (password.length() < 6) {
                    Toast.makeText(getApplicationContext(), "Password too short, enter minimum 6 characters!", Toast.LENGTH_SHORT).show();
                    return;
                }
                else {
                    if(password.equals(confirmPassword)) {

                        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                if(task.isSuccessful()){
                                    Toast.makeText(RegisterActivity.this, "Registration Successful", Toast.LENGTH_SHORT).show();

                                }else{
                                    Log.e("Error=", task.getException().getMessage());
                                    Toast.makeText(RegisterActivity.this, "Registration Failed, Please try another email account", Toast.LENGTH_SHORT).show();

                                }

                            }
                        });
                    }else{
                        Toast.makeText(RegisterActivity.this, "Password doesn't match", Toast.LENGTH_SHORT).show();

                    }


                }

            }
        });


        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent LoginIntent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(LoginIntent);
            }
        });


    }


}
