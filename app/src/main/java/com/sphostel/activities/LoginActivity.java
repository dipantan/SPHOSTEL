package com.sphostel.activities;

import android.app.ProgressDialog;
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
    String email, pass;
    FirebaseAuth firebaseAuth;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //  firebaseAuth = FirebaseAuth.getInstance();
      /*  if(firebaseAuth.getCurrentUser() != null){
            startActivity(new Intent(this,HomeActivity.class));
            finish();
        } */
        setContentView(R.layout.activity_login);
        emailId = findViewById(R.id.text_input_userid);
        password = findViewById(R.id.text_input_password);
        progressDialog = new ProgressDialog(this);
        buttonLogin = findViewById(R.id.buttonLogin);
        textViewSignup = findViewById(R.id.textViewSignUp);
        firebaseAuth = FirebaseAuth.getInstance();
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
                loginUser();
                //   Intent intent = new Intent(LoginActivity.this,HomeActivity.class);
                //   startActivity(intent);

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
        email = emailId.getEditText().getText().toString().trim();
        pass = password.getEditText().getText().toString().trim();
        progressDialog.setMessage("Logging in ");
        progressDialog.show();
        firebaseAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(LoginActivity.this, "Success", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                    finish();
                } else {
                    Toast.makeText(LoginActivity.this, "You are not registered", Toast.LENGTH_SHORT).show();
                }
                progressDialog.dismiss();
            }
        });
    }
}
