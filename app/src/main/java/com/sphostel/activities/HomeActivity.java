package com.sphostel.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sphostel.R;
import com.sphostel.models.Student;
//import com.sphostel.models.Student;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView mProfile;
    //   List<Student> students;
    // Student student;
    // private static final String FIREBASE_STORAGE_URL = "gs://sphostel-3ca65.appspot.com/images/profiles/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ///     students = new ArrayList<>();
        initView();
        // Toast.makeText(this, "Welcome to HomeActivity", Toast.LENGTH_SHORT).show();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        final String UUID = user.getUid();
        //setting user profile image

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("students").child(UUID);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String student = dataSnapshot.getValue(Student.class).getPhotoUrl();
                try {
                    Glide.with(HomeActivity.this).load(student).into(mProfile);
                } catch (NullPointerException ignored) {

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        //  Log.d("URl:", student.getPhotoUrl());
    }

    private void initView() {
        ImageButton mButton = findViewById(R.id.button);
        mButton.setOnClickListener(this);
        //   TextView mEmail = findViewById(R.id.email);
        mProfile = findViewById(R.id.profile);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button:
                final AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("Are you sure").setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        FirebaseAuth firebaseAuth;
                        firebaseAuth = FirebaseAuth.getInstance();
                        firebaseAuth.signOut();
                        startActivity(new Intent(HomeActivity.this, LoginActivity.class));
                    }
                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
                break;
            default:
                break;
        }
    }
}
