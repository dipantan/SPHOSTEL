package com.sphostel.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
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
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;
import com.sphostel.R;
import com.sphostel.utils.PatternField;

public class LoginActivity extends AppCompatActivity {
    TextView textViewSignup;
    Button buttonLogin;
    TextInputLayout emailId, password;
    FirebaseAuth firebaseAuth;
    ProgressDialog progressDialog;

    @Override
    protected void onStart() {
        super.onStart();
        firebaseAuth = FirebaseAuth.getInstance();
        if (firebaseAuth.getCurrentUser() != null) {
            startActivity(new Intent(this, HomeActivity.class));
            finish();
        }
    }

    public boolean isOnline() {
        ConnectivityManager connectivityManager = (ConnectivityManager) this.getSystemService((Context.CONNECTIVITY_SERVICE));
        NetworkInfo info = connectivityManager.getActiveNetworkInfo();
        return !(info == null || !info.isConnected() || !info.isAvailable());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //check network state
        if (!isOnline()) {
            final AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("No Internet Connection")
                    .setMessage("Go to settings and enable data connection")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            startActivity(new Intent(Settings.ACTION_WIRELESS_SETTINGS));
                        }
                    }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    finish();
                }
            });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }
        //    networkState.isOnline();
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
                    password.requestFocus();
                    return false;
                } else if (!PatternField.PASSWORD_PATTERN.matcher(passwordStd).matches()) {
                    password.setError("Password too weak");
                    password.requestFocus();
                    return false;
                } else {
                    password.setError(null);
                    return true;
                }
            }
        });
    }

    private void loginUser() {
        final String email = emailId.getEditText().getText().toString().trim();
        final String pass = password.getEditText().getText().toString().trim();
        progressDialog.setMessage("Logging in ");
        progressDialog.show();
        firebaseAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                try {
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    final String UUID = user.getUid();
                    progressDialog.dismiss();
                    //     Toast.makeText(LoginActivity.this, "Success", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                    finish();
                }
                //GetUid
                catch (NullPointerException ignored) {
                    if (task.getException() != null) {
                        progressDialog.dismiss();
                        String errorCode = ((FirebaseAuthException) task.getException()).getErrorCode();
                        Toast.makeText(LoginActivity.this, errorCode, Toast.LENGTH_LONG).show();
                    }
                }

            }
        });
    }
}
