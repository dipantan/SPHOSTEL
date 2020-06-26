package com.sphostel.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.sphostel.R;
import com.sphostel.models.Student;
import com.sphostel.utils.PatternField;

import java.io.IOException;

public class SignupActivity extends AppCompatActivity {
    TextInputLayout sName, sRoll, sRoom, sDOB, sMobile, sEmail, sEmergency, sBlood, sPass;
    //  String stName, stRoll, stRoom, stDOB, stMobile, stEmail, stEmergency, stBlood, stPass, stDept, academicYear;
    String academicYear;
    Spinner sDept;
    RadioGroup sYear;
    Button signUp;
    ImageView circleImage;
    //   FirebaseAuth firebaseAuth;
    ProgressDialog progressDialog;
    //   Student student;
    //  String imagePath;
    Uri uri;
    private int IMAGE_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        initView();
        progressDialog = new ProgressDialog(this);
        circleImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //ask for permission in SDK>22
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                //   intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, IMAGE_REQUEST);

            }
        });
        sYear.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.thirdYear:
                        academicYear = "2017-20";
                        break;
                    case R.id.secondYear:
                        academicYear = "2018-21";
                        break;
                    case R.id.firstYear:
                        academicYear = "2019-21";
                        break;
                }
            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });
    }

    private void registerUser() {
        final String stName = sName.getEditText().getText().toString().trim();
        final String stRoll = sRoll.getEditText().getText().toString().trim();
        final String stRoom = sRoom.getEditText().getText().toString().trim();
        final String stDOB = sDOB.getEditText().getText().toString().trim();
        final String stMobile = sMobile.getEditText().getText().toString().trim();
        final String stEmail = sEmail.getEditText().getText().toString().trim();
        final String stBlood = sBlood.getEditText().getText().toString().trim();
        final String stEmergency = sEmergency.getEditText().getText().toString().trim();
        final String stPass = sPass.getEditText().getText().toString().trim();
        final String department = sDept.getSelectedItem().toString();
        final String year = academicYear;

        if (stName.isEmpty()) {
            sName.setError("Name cant be empty");
            sName.requestFocus();
            return;
        } else if (stRoll.isEmpty()) {
            sRoll.setError("Roll cant be empty");
            sRoll.requestFocus();
            return;
        } else if (!PatternField.ROLL_NO.matcher(stRoll).matches()) {
            sRoll.setError("Invalid Roll");
            sRoll.requestFocus();
            return;
        } else if (stRoom.isEmpty()) {
            sRoom.setError("Room no cant be empty");
            sRoom.requestFocus();
            return;
        } else if (stDOB.isEmpty()) {
            sDOB.setError("Date of birth cant be empty");
            sDOB.requestFocus();
            return;
        } else if (stMobile.isEmpty()) {
            sMobile.setError("Mobile no cant be empty");
            sMobile.requestFocus();
            return;
        } else if (stEmail.isEmpty()) {
            sEmail.setError("Email cant be empty");
            sEmail.requestFocus();
            return;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(stEmail).matches()) {
            sEmail.setError("Enter a valid email");
            sEmail.requestFocus();
            return;
        } else if (stEmergency.isEmpty()) {
            sEmergency.setError("Emergency no cant be empty");
            sEmergency.requestFocus();
            return;
        } else if (stBlood.isEmpty()) {
            sBlood.setError("Blood group cant be empty");
            sBlood.requestFocus();
            return;
        } else if (stPass.isEmpty()) {
            sPass.setError("Password cant be empty");
            sPass.requestFocus();
            return;
        } else if (!PatternField.PASSWORD_PATTERN.matcher(stPass).matches()) {
            sPass.setError("Password too weak");
            sPass.requestFocus();
            return;
        } else if (year.isEmpty()) {
            Toast.makeText(this, "Select your year", Toast.LENGTH_SHORT).show();
        }
        progressDialog.setMessage("Registering");
        progressDialog.show();
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(stEmail, stPass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                try {
                    FirebaseAuth auth = FirebaseAuth.getInstance();
                    FirebaseUser user = auth.getCurrentUser();
                    final String UUID = user.getUid();
                    //   String UUID;
                    try {
                        //image upload
                        final Uri filePath = uri;
                        if (filePath != null) {
                            StorageReference reference = FirebaseStorage.getInstance().getReference("images/profiles/" + UUID + ".jpg");
                            reference.putFile(filePath).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    progressDialog.dismiss();
                                    Toast.makeText(SignupActivity.this, "Photo uploaded", Toast.LENGTH_SHORT).show();
                                    Student student = new Student(
                                            stName,
                                            department,
                                            year,
                                            stRoll,
                                            stRoom,
                                            stDOB,
                                            stMobile,
                                            stEmail,
                                            stEmergency,
                                            stBlood,
                                            taskSnapshot.getDownloadUrl().toString()
                                    );
                                    FirebaseDatabase.getInstance().getReference("students").
                                            child(UUID).
                                            setValue(student).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            FirebaseAuth firebaseAuth;
                                            firebaseAuth = FirebaseAuth.getInstance();
                                            firebaseAuth.signOut();
                                            startActivity(new Intent(SignupActivity.this, LoginActivity.class));
                                        }
                                    });
                                }
                            });
                        }
                    } catch (NullPointerException ignored) {

                    }

                } catch (NullPointerException ignored) {
                    if (task.getException() != null) {
                        progressDialog.dismiss();
                        String errorCode = ((FirebaseAuthException) task.getException()).getErrorCode();
                        Toast.makeText(SignupActivity.this, errorCode, Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            uri = data.getData();    //image path
            // imagePath = uri.toString();
            // Log.d("Imageurl",imagePath);
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);     //picasso or glide cam be used here instead
                circleImage = findViewById(R.id.circleImage);
                circleImage.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void initView() {
        circleImage = findViewById(R.id.circleImage);
        sName = findViewById(R.id.text_input_name);
        sRoll = findViewById(R.id.roll);
        sRoom = findViewById(R.id.room);
        sDOB = findViewById(R.id.dob);
        sMobile = findViewById(R.id.mobile);
        sEmail = findViewById(R.id.email);
        sBlood = findViewById(R.id.blood);
        sEmergency = findViewById(R.id.emergency_no);
        sPass = findViewById(R.id.password);
        sDept = findViewById(R.id.spinnerDept);
        signUp = findViewById(R.id.signup);
        sYear = findViewById(R.id.radioGroup);
    }

}
