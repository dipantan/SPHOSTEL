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
import com.sphostel.models.Rooms;
import com.sphostel.models.Student;

public class RoomChangeActivity extends AppCompatActivity implements View.OnClickListener {
    String sName, sDept, sYear, sRoll, sMobile, sCRoom, sNRoom, sReason, sStatus;
    String UUID;
    ProgressDialog dialog;
    private TextView mName;
    private TextView mMobile;
    private TextView mDepartment;
    private TextView mYear;
    private TextView mRoll;
    private TextView mRoomNoCurrent;
    private EditText mRoomNoNew;
    private EditText mReason;
    private MaterialButton mSubmitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_change);
        initView();
        dialog = new ProgressDialog(this);
        //get user UUID
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        UUID = user.getUid();

        //database reference for fetching values
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("students").child(UUID);    //root node
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                sName = dataSnapshot.getValue(Student.class).getName();
                sDept = dataSnapshot.getValue(Student.class).getDepartment();
                sYear = dataSnapshot.getValue(Student.class).getAcademicYear();
                sRoll = dataSnapshot.getValue(Student.class).getRoll_no();
                sMobile = dataSnapshot.getValue(Student.class).getMobile_no();
                sCRoom = dataSnapshot.getValue(Student.class).getRoom_no();

                //setting the values after fetching
                mName.setText(sName);
                mDepartment.setText(sDept);
                mYear.setText(sYear);
                mRoll.setText(sRoll);
                mMobile.setText(sMobile);
                mRoomNoCurrent.setText(sCRoom);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void initView() {
        mName = findViewById(R.id.name);
        mMobile = findViewById(R.id.mobile);
        mDepartment = findViewById(R.id.department);
        mYear = findViewById(R.id.year);
        mRoll = findViewById(R.id.roll);
        mRoomNoCurrent = findViewById(R.id.current_room_no);
        mRoomNoNew = findViewById(R.id.new_room_no);
        mReason = findViewById(R.id.reason);
        mSubmitButton = findViewById(R.id.button_submit);
        mSubmitButton.setOnClickListener(this);
    }

    private void setValues() {

        final DatabaseReference reference = FirebaseDatabase.getInstance().getReference("requests").child(UUID);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                sNRoom = mRoomNoNew.getText().toString().trim();
                sReason = mReason.getText().toString().trim();
                if (sNRoom.equals(sCRoom)) {
                    Toast.makeText(RoomChangeActivity.this, "Enter different room no", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (sReason.isEmpty()) {
                    Toast.makeText(RoomChangeActivity.this, "Enter valid reason", Toast.LENGTH_SHORT).show();
                    return;
                }
                dialog.setMessage("Submitting");
                dialog.show();
                Rooms rooms = new Rooms(sName, sDept, sYear, sRoll, sMobile, sCRoom, sNRoom, sReason, sStatus);

                //check if user has pending request
                try {
                    String status = dataSnapshot.getValue(Rooms.class).getStatus();
                    if (status.equals("Pending")) {
                        dialog.dismiss();
                        Toast.makeText(RoomChangeActivity.this, "Previous request pending", Toast.LENGTH_SHORT).show();
                        return;
                    }
                } catch (NullPointerException ignored) {

                }

                sStatus = "Pending";
                reference.setValue(rooms).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        dialog.dismiss();
                        Toast.makeText(RoomChangeActivity.this, "Submitted", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(RoomChangeActivity.this, HomeActivity.class));
                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_submit:
                // TODO 20/06/27
                setValues();
                break;
            default:
                break;
        }
    }
}
