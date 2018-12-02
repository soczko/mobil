package com.example.kacper.mobilne;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TextView;

public class Details extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details);
        //TextView txtProduct = (TextView) findViewById(R.id.label);

        Intent i = getIntent();
        // getting attached intent data
        String product = i.getStringExtra("details");
        // displaying selected product name
        //txtProduct.setText(product);


    }
}
