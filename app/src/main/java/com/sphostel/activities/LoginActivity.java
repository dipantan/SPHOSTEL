package com.sphostel.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.sphostel.R;
import com.sphostel.utils.PatternField;

public class LoginActivity extends AppCompatActivity {
    TextView textViewSignup;
    Button buttonLogin;
    TextInputLayout emailId, password;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        emailId = findViewById(R.id.text_input_userid);
        password = findViewById(R.id.text_input_password);
        buttonLogin = findViewById(R.id.buttonLogin);
        textViewSignup = findViewById(R.id.textViewSignUp);
        firebaseAuth = FirebaseAuth.getInstance();
        if (firebaseAuth.getCurrentUser() != null) {
            finish();
            startActivity(new Intent(this, HomeActivity.class));
        }
        textViewSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, SignupActivity.class));
            }
        });
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!validateEmail() | !validatePassword()) {
                    return;
                }
                //Firebase data uploads from here
                loginUser();
            }

            //email validation
            private boolean validateEmail() {
                String emailStd = emailId.getEditText().getText().toString().trim();
                if (emailStd.isEmpty()) {
                    emailId.setError("Email can't be blank");
                    //    emailId.setFocusable(true);
                    return false;
                } else if (!Patterns.EMAIL_ADDRESS.matcher(emailStd).matches()) {
                    emailId.setError("Not a valid email address");
                    //    emailId.setFocusable(true);
                    return false;
                } else {
                    emailId.setError(null);
                    return true;
                }
            }

            //password validation
            private boolean validatePassword() {
                String passwordStd = password.getEditText().getText().toString().trim();
                if (passwordStd.isEmpty()) {
                    password.setError("Password can't be blank");
                    //    emailId.setFocusable(true);
                    return false;
                } else if (!PatternField.PASSWORD_PATTERN.matcher(passwordStd).matches()) {
                    password.setError("Password too weak");
                    //     emailId.setFocusable(true);
                    return false;
                } else {
                    password.setError(null);
                    return true;
                }
            }
        });
    }

    private void loginUser() {
        String email = emailId.getEditText().getText().toString().trim();
        String pass = password.getEditText().getText().toString().trim();
        firebaseAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isComplete()) {
                    finish();
                    startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                } else {
                    Toast.makeText(LoginActivity.this, "Login error", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
