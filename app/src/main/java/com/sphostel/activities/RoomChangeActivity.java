package com.sphostel.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sphostel.R;
import com.sphostel.models.Student;

public class RoomChangeActivity extends AppCompatActivity {
    String sName, sDept, sYear, sRoll, sMobile, sCRoom, sNRoom, SReason;
    String UUID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_change);

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
                setValues();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void setValues() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("requests").child(UUID);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //  Rooms rooms = new Rooms()
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
