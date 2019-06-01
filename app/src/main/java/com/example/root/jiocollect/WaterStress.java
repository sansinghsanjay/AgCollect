package com.example.root.jiocollect;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class WaterStress extends AppCompatActivity {

    GlobalVars globalVars;
    RadioGroup radioGroup_waterstress;
    RadioButton radioButton_nostress,radioButton_mild,radioButton_medium,radioButton_high,radioButton_others,tempRadioButton;
    int selectedId;
    RemoveNoise removeNoise;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_water_stress);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // init objects
        removeNoise = new RemoveNoise();
        globalVars = (GlobalVars)getApplication();

        // add back button to MainActivity
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedId = radioGroup_waterstress.getCheckedRadioButtonId();
                tempRadioButton = (RadioButton) findViewById(selectedId);
                String selectedwaterStress = removeNoise.cleanValue(tempRadioButton.getText().toString());
                globalVars.setWaterStress(selectedwaterStress);
                Intent intent=new Intent(WaterStress.this,CameraUnhealthy.class);
                startActivity(intent);
            }
        });

        // radioGroup
        radioGroup_waterstress = (RadioGroup) findViewById(R.id.radiogrp_waterstress);

        // radioButton
        radioButton_nostress = (RadioButton) findViewById(R.id.waterstress_nostress);
        radioButton_mild = (RadioButton) findViewById(R.id.waterstress_mild);
        radioButton_medium = (RadioButton) findViewById(R.id.waterstress_medium);
        radioButton_high = (RadioButton) findViewById(R.id.waterstress_high);
        radioButton_others = (RadioButton) findViewById(R.id.waterstress_others);

        // radioGroup_waterstress: default selection - "noStress"
        radioGroup_waterstress.check(R.id.waterstress_nostress);
    }

}
