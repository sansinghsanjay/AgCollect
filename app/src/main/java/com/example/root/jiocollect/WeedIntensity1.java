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

public class WeedIntensity1 extends AppCompatActivity {

    GlobalVars globalVars;
    RadioGroup radioGroup_weedintensity;
    RadioButton radioButton_weedfree,radioButton_low,radioButton_medium,radioButton_high,radioButton_others,tempRadioButton;
    int selectedId;
    RemoveNoise removeNoise;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weed_intensity1);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        removeNoise = new RemoveNoise();
        globalVars = (GlobalVars)getApplication();

        // add back button to MainActivity
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedId = radioGroup_weedintensity.getCheckedRadioButtonId();
                tempRadioButton = (RadioButton) findViewById(selectedId);
                String selectedweedintensity = removeNoise.cleanValue(tempRadioButton.getText().toString());
                globalVars.setWeedintensity(selectedweedintensity);
                Intent intent=new Intent(WeedIntensity1.this,WaterStress.class);
                startActivity(intent);
            }
        });

        // radioGroup
        radioGroup_weedintensity = (RadioGroup) findViewById(R.id.radiogrp_weedintensity);

        // radioButton
        radioButton_weedfree = (RadioButton) findViewById(R.id.weedintensity_weedfree);
        radioButton_low = (RadioButton) findViewById(R.id.weedintensity_low);
        radioButton_medium = (RadioButton) findViewById(R.id.weedintensity_medium);
        radioButton_high = (RadioButton) findViewById(R.id.weedintensity_high);
        radioButton_others = (RadioButton) findViewById(R.id.weedintensity_others);

        // radioGroup_healthyNonhealthy: default selection - "radioButton_nonhealthy"
        radioGroup_weedintensity.check(R.id.weedintensity_weedfree);





    }

}
