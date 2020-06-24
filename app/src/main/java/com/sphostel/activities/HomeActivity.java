package com.sphostel.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.ImageButton;

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

import de.hdodenhof.circleimageview.CircleImageView;
//import com.sphostel.models.Student;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageButton button;

    private CircleImageView mProfile;
    private CardView mEvent;
    private CardView mPay;
    private CardView mRoom;
    private CardView mRule;
    private CardView mComplain;
    private CardView mLeave;
    //   List<Student> students;
    // Student student;
    // private static final String FIREBASE_STORAGE_URL = "gs://sphostel-3ca65.appspot.com/images/profiles/";

    public boolean isOnline() {
        ConnectivityManager connectivityManager = (ConnectivityManager) this.getSystemService((Context.CONNECTIVITY_SERVICE));
        NetworkInfo info = connectivityManager.getActiveNetworkInfo();
        return !(info == null || !info.isConnected() || !info.isAvailable());
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
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
                    Glide.with(HomeActivity.this)
                            //   .asGif()
                            .load(student)
                            // .apply(new RequestOptions().placeholder(R.mipmap.))
                            //.listener(request)
                            //   .placeholder(R.drawable.loading)
                            .into(mProfile);
                } catch (NullPointerException ignored) {

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void initView() {
        button = findViewById(R.id.button);
        button.setOnClickListener(this);
        mProfile = findViewById(R.id.profileImage);
        mEvent = findViewById(R.id.event);
        mEvent.setOnClickListener(this);
        mPay = findViewById(R.id.pay);
        mPay.setOnClickListener(this);
        mRoom = findViewById(R.id.room);
        mRoom.setOnClickListener(this);
        mRule = findViewById(R.id.rule);
        mRule.setOnClickListener(this);
        mComplain = findViewById(R.id.complain);
        mComplain.setOnClickListener(this);
        mLeave = findViewById(R.id.leave);
        mLeave.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.event:
                // TODO 20/06/22
                break;
            case R.id.pay:
                // TODO 20/06/22
                break;
            case R.id.room:
                // TODO 20/06/22
                break;
            case R.id.rule:
                // TODO 20/06/22
                break;
            case R.id.complain:
                // TODO 20/06/22
                break;
            case R.id.leave:
                // TODO 20/06/22
                break;
            case R.id.button:
                final AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Logout")
                        .setMessage("Are you sure")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
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
