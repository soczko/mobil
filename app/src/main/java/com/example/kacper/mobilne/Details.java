package com.example.kacper.mobilne;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
        String base = i.getStringExtra("items");
        String details = i.getStringExtra("details");
        String[] tab1 = base.split(" ");
        String[] tab2 = details.split(",");
        company.setText(tab1[0]);
        model.setText(tab1[1]);
        cores.setText(tab2[0]);
        threads.setText(tab2[1]);
        clock.setText(tab2[2] + "GHz");

    }
}
