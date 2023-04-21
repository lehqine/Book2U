package com.example.book2u;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class CustRegister extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth mAuth;
    private EditText etUsername, etPhoneNum, etemail, etpassword;
    private TextView registerUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cust_register);

        mAuth = FirebaseAuth.getInstance();


        registerUser = (Button) findViewById(R.id.regbtn);
        registerUser.setOnClickListener(this);

        etUsername = (EditText) findViewById(R.id.username);
        etPhoneNum = (EditText) findViewById(R.id.phoneNum);
        etemail = (EditText) findViewById(R.id.email);
        etpassword= (EditText) findViewById(R.id.password);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            //case R.id.ConstraintLayout:
            // startActivity(new Intent(this, MainActivity.class));
            //break;
            case R.id.regbtn:
                registerUser();
                break;

        }

    }
    private void registerUser() {
        String email = etemail.getText().toString().trim();
        String password = etpassword.getText().toString().trim();
        String username = etUsername.getText().toString().trim();
        String PhoneNum = etPhoneNum.getText().toString().trim();

        if (username.isEmpty()) {
            etUsername.setError("Please fill your username");
            etUsername.requestFocus();
            return;
        }

        if (PhoneNum.isEmpty()) {
            etPhoneNum.setError("Please fill your phone number");
            etPhoneNum.requestFocus();
            return;
        }

        if (email.isEmpty()) {
            etemail.setError("Please fill your Email");
            etemail.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            etemail.setError("Please provide valid Email");
            etemail.requestFocus();
            return;
        }
        if (password.isEmpty()) {
            etpassword.setError("Please fill your password");
            etpassword.requestFocus();
            return;
        }
        if (password.length() < 6) {
            etpassword.setError("Minimum password length should be 6 characters");
            etpassword.requestFocus();
            return;
        } else {

            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                User user = new User(username,PhoneNum, email,password);

                                FirebaseDatabase.getInstance().getReference("Users")
                                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                        .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    startActivity(new Intent(CustRegister.this, CustLogin.class));//intent to homepage
                                                    Toast.makeText(CustRegister.this, "User has been registered successfully", Toast.LENGTH_LONG).show();

                                                } else {
                                                    Toast.makeText(CustRegister.this, "Failed to registered! Please try again.", Toast.LENGTH_LONG).show();


                                                }
                                            }
                                        });
                            }
                            else{
                                Toast.makeText(CustRegister.this, "Failed to registered! Please try again.", Toast.LENGTH_LONG).show();

                            }
                        }
                    });


        }


    }
}