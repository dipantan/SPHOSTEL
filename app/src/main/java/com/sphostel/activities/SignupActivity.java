package com.sphostel.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.sphostel.R;
import com.sphostel.models.Student;
import com.sphostel.utils.PatternField;

import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;

public class SignupActivity extends AppCompatActivity {
    TextInputLayout sName, sRoll, sRoom, sDOB, sMobile, sEmail, sEmergency, sBlood, sPass;
    String stName, stRoll, stRoom, stDOB, stMobile, stEmail, stEmergency, stBlood, stPass, stDept, academicYear, uuid;
    Spinner sDept;
    RadioGroup sYear;
    Button signUp;
    DatabaseReference reference;
    CircleImageView circleImage;
    FirebaseAuth firebaseAuth;
    //  String imagePath;
    Uri uri;
    private int IMAGE_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
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
        firebaseAuth = FirebaseAuth.getInstance();
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
                if (!validatePassword() | !validateRest()) {
                    return;
                } else if (!stEmail.isEmpty() && !stPass.isEmpty()) {
                    registerUser();
                }
                stDept = sDept.getSelectedItem().toString();
                uuid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                reference = FirebaseDatabase.getInstance().getReference("students").child(academicYear);
                Student student = new Student(stName, stDept, academicYear, stRoll, stRoom, stDOB, stMobile, stEmail, stEmergency, stBlood, uuid);
                //   String roll = stRoll.replace("/","");
                reference.child(stDept).child(uuid).setValue(student);
                startActivity(new Intent(SignupActivity.this, DummyActivity.class));
            }
        });
    }

    private void registerUser() {
        String email = sEmail.getEditText().getText().toString().trim();
        String password = sPass.getEditText().getText().toString().trim();
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(SignupActivity.this, "Registration Success", Toast.LENGTH_SHORT).show();
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

    //validate rest
    boolean validateRest() {
        stName = sName.getEditText().getText().toString().trim();
        stRoll = sRoll.getEditText().getText().toString().trim();
        stRoom = sRoom.getEditText().getText().toString().trim();
        stDOB = sDOB.getEditText().getText().toString().trim();
        stMobile = sMobile.getEditText().getText().toString().trim();
        stEmail = sEmail.getEditText().getText().toString().trim();
        stBlood = sBlood.getEditText().getText().toString().trim();
        stEmergency = sEmergency.getEditText().getText().toString().trim();
        if (stName.isEmpty() && stRoll.isEmpty() && stRoom.isEmpty() && stDOB.isEmpty() && stMobile.isEmpty() && stEmail.isEmpty() && stEmergency.isEmpty() && stBlood.isEmpty()) {
            sName.setError("Field cant be empty");
            sRoll.setError("Field cant be empty");
            sRoom.setError("Field cant be empty");
            sDOB.setError("Field cant be empty");
            sMobile.setError("Field cant be empty");
            sEmail.setError("Field cant be empty");
            sEmergency.setError("Field cant be empty");
            sBlood.setError("Field cant be empty");
            return false;
        } else {
            sName.setError(null);
            sRoll.setError(null);
            sRoom.setError(null);
            sDOB.setError(null);
            sMobile.setError(null);
            sEmail.setError(null);
            sEmergency.setError(null);
            sBlood.setError(null);
            return true;
        }
    }

    //validate password
    boolean validatePassword() {
        stPass = sPass.getEditText().getText().toString().trim();
        if (stPass.isEmpty()) {
            sPass.setError("Field cant be empty");
            return false;
        } else if (!PatternField.PASSWORD_PATTERN.matcher(stPass).matches()) {
            sPass.setError("Password too weak");
            return false;
        } else {
            sPass.setError(null);
            return true;
        }
    }

}
