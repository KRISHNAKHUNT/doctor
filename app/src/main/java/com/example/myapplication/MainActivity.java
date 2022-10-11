package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.WindowManager;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
private CardView cvprofile,cvconsult,cvpost,cvmeet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //getSupportActionBar().hide();
        /*getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);*/
        setContentView(R.layout.activity_main);

        cvprofile=(CardView) findViewById(R.id.cvprofile);
        cvconsult=(CardView) findViewById(R.id.cvconsult);
        cvpost=(CardView) findViewById(R.id.cvpost);
        cvmeet=(CardView) findViewById(R.id.cvmeet);
        cvprofile.setOnClickListener(this);
        cvconsult.setOnClickListener(this);
        cvmeet.setOnClickListener(this);
        cvpost.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
    Intent i;
    switch (v.getId()){
        case R.id.cvprofile:
            i=new Intent(MainActivity.this,profilepage.class);
            startActivity(i);
            break;

        case R.id.cvconsult:
            i=new Intent(MainActivity.this,inbox.class);
            startActivity(i);
            break;

        case R.id.cvpost:
            String mail = "mailto:";
            Intent intent = new Intent(Intent.ACTION_SENDTO);
            intent.setData(Uri.parse(mail));
            startActivity(intent);
            break;
        case R.id.cvmeet:
            Uri uri1= Uri.parse("https://meet.google.com/");
            startActivity(new Intent(Intent.ACTION_VIEW,uri1));
            break;
    }
    }
}