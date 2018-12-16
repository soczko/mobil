package com.example.kacper.mobilne;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;


public class Details extends AppCompatActivity{
    private TextView company;
    private TextView model;
    private TextView cores;
    private TextView threads;
    private TextView clock;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details);
        company = (TextView) findViewById(R.id.text_company);
        model = (TextView) findViewById(R.id.text_model);
        cores = (TextView) findViewById(R.id.text_cores);
        threads = (TextView) findViewById(R.id.text_threads);
        clock = (TextView) findViewById(R.id.text_clock);

        Intent i = getIntent();
        // getting attached intent data
        String oCompany = i.getStringExtra("company");
        String oModel = i.getStringExtra("model");
        String oCores = i.getStringExtra("cores");
        String oThreads = i.getStringExtra("threads");
        String oClock = i.getStringExtra("clock");
        company.setText(oCompany);
        model.setText(oModel);
        cores.setText(oCores);
        threads.setText(oThreads);
        clock.setText(oClock + "GHz");

        if( oCompany.equalsIgnoreCase("intel")) {
            ImageView myImageView = (ImageView) findViewById(R.id.my_image_view);
            myImageView.setImageResource(R.drawable.intel);
        }
        else{
            ImageView myImageView = (ImageView) findViewById(R.id.my_image_view);
            myImageView.setImageResource(R.drawable.amd);
        }


    }
}
