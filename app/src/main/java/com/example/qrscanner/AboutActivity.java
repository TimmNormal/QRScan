package com.example.qrscanner;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class AboutActivity extends AppCompatActivity {
    String userData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        userData = getIntent().getExtras().getString("info");
        API();

    }
    public void API(){
        TextView mainText = findViewById(R.id.InfoText);
        mainText.setText(userData);
    }
}