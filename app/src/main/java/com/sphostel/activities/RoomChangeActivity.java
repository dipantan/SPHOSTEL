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
import com.sphostel.models.Rooms;

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
                sName = dataSnapshot.getValue(Rooms.class).getsName();
                sDept = dataSnapshot.getValue(Rooms.class).getsDept();
                sYear = dataSnapshot.getValue(Rooms.class).getsYear();
                sRoll = dataSnapshot.getValue(Rooms.class).getsRoll();
                sMobile = dataSnapshot.getValue(Rooms.class).getsMobile();
                sCRoom = dataSnapshot.getValue(Rooms.class).getsCRoom();
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

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
