package com.sphostel.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.button.MaterialButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sphostel.R;
import com.sphostel.models.Letter;
import com.sphostel.models.Student;

public class LeaveApplication extends AppCompatActivity implements View.OnClickListener {
    String name, dept, year, roll, room, mobile, from_date, to_date, reason;
    String status, sStatus;
    String UUID;
    private TextView mLetterName;
    private TextView mLetterDepartment;
    private TextView mLetterYear;
    private TextView mLetterRoll;
    private TextView mLetterRoom;
    private TextView mLetterMobile;
    private EditText mFromLetterDate;
    private EditText mToLetterDate;
    private EditText mLetterReason;
    private MaterialButton mLetterSubmit;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leave_application);
        initView();
        progressDialog = new ProgressDialog(this);

        //get user UUID
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        UUID = user.getUid();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("students").child(UUID);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                name = dataSnapshot.getValue(Student.class).getName();
                dept = dataSnapshot.getValue(Student.class).getDepartment();
                year = dataSnapshot.getValue(Student.class).getAcademicYear();
                roll = dataSnapshot.getValue(Student.class).getRoll_no();
                room = dataSnapshot.getValue(Student.class).getRoom_no();
                mobile = dataSnapshot.getValue(Student.class).getMobile_no();

                //set values to views
                mLetterName.setText(name);
                mLetterDepartment.setText(dept);
                mLetterYear.setText(year);
                mLetterRoll.setText(roll);
                mLetterRoom.setText(room);
                mLetterMobile.setText(mobile);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private void initView() {
        mLetterName = findViewById(R.id.name_letter);
        mLetterDepartment = findViewById(R.id.department_letter);
        mLetterYear = findViewById(R.id.year_letter);
        mLetterRoll = findViewById(R.id.roll_letter);
        mLetterRoom = findViewById(R.id.room_letter);
        mLetterMobile = findViewById(R.id.mobile_letter);
        mFromLetterDate = findViewById(R.id.date_from_letter);
        mToLetterDate = findViewById(R.id.date_to_letter);
        mLetterReason = findViewById(R.id.reason_letter);
        mLetterSubmit = findViewById(R.id.submit_letter);
        mLetterSubmit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.submit_letter:
                // TODO 20/06/28
                setValues();
                break;
            default:
                break;
        }
    }

    private void setValues() {
        progressDialog.setMessage("Submitting");
        final DatabaseReference reference = FirebaseDatabase.getInstance().getReference("letters").child(UUID);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                from_date = mFromLetterDate.getText().toString().trim();
                to_date = mToLetterDate.getText().toString().trim();
                reason = mLetterReason.getText().toString().trim();
                if (from_date.isEmpty() || to_date.isEmpty() || reason.isEmpty()) {
                    progressDialog.dismiss();
                    Toast.makeText(LeaveApplication.this, "Fill all the fields", Toast.LENGTH_SHORT).show();
                    return;
                }
                progressDialog.show();
                Letter letter = new Letter(name, dept, year, roll, room, mobile, from_date, to_date, reason, sStatus);

                status = dataSnapshot.getValue(Letter.class).getStatus();
                if (status != null && status.equals("Pending")) {
                    progressDialog.dismiss();
                    Toast.makeText(LeaveApplication.this, "Previous request is pending", Toast.LENGTH_LONG).show();
                    return;
                }
                sStatus = "Pending";
                reference.setValue(letter).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        progressDialog.dismiss();
                        Toast.makeText(LeaveApplication.this, "Submitted", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(LeaveApplication.this, HomeActivity.class));
                    }
                });


            }


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}

