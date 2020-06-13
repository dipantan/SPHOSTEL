package com.sphostel.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import com.sphostel.R;

public class HomeActivity extends AppCompatActivity {
    private TextView textvView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initView();

        Toast.makeText(this, "Welocome to homeactivity", Toast.LENGTH_SHORT).show();
        // TextView textView = new TextView(this);

    }

    private void initView() {
        textvView = findViewById(R.id.t);
    }

}
