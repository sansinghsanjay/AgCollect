package com.example.root.jiocollect;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.GridView;
import java.util.ArrayList;
import java.util.List;

public class NutrientDeficiency extends AppCompatActivity {

    // GUI elements
    AlertDialog.Builder dialogBox;
    /*GridView gridView;
    GridViewAdapter nutrientDeficiencyGridViewAdapter;
    ArrayList<DefectTemplate> nutrientDeficiency_list;
    DefectTemplate defectTemplate;*/

    // other vars
    Intent intent;
    GlobalVars globalVars;
    String crop, season, stage, healthStatus;
    RemoveNoise removeNoise;

    GridView gridView;              /*<<----------------Changed Value*/
    List<RowItem> rowItems;             /*<<----------------Changed Value*/
    CustomListViewAdapter boxAdapter;           /*<<----------------Changed Value*/


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nutrient_deficiency);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // init vars
        removeNoise = new RemoveNoise();
        globalVars = (GlobalVars)getApplication();

        // add back button to MainActivity
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // snack button action
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (RowItem defectTemplate_iter : boxAdapter.getBox()) {
                    String tempString = removeNoise.cleanValue(defectTemplate_iter.getDesc());
                    if (defectTemplate_iter.checkboxStatus) {
                        globalVars.setNutrientDeficiencies(tempString);
                    } else {    // if value is not checked then remove it from global variable
                        globalVars.removeNutrientDeficiencies(tempString);
                    }
                }
                // validation for - "no", "other", "unknown"
                List<String> tempList = globalVars.getNutrientDeficiencies();
                for(int i=0; i<tempList.size(); i++) {
                    String tempString = tempList.get(i);
                    if(tempString.equalsIgnoreCase(getResources().getString(R.string.nutrientDef_noDef))) {
                        if(tempList.size() > 1) {
                            globalVars.clearNutrientDeficiencies();
                            break;
                        }
                    }
                    if(tempString.equalsIgnoreCase(getResources().getString(R.string.nutrientDef_otherDef))) {
                        if(tempList.size() > 1) {
                            globalVars.clearNutrientDeficiencies();
                            break;
                        }
                    }
                    if(tempString.equalsIgnoreCase(getResources().getString(R.string.nutrientDef_unknown))) {
                        if(tempList.size() > 1) {
                            globalVars.clearNutrientDeficiencies();
                            break;
                        }
                    }
                }
                if(globalVars.lengthNutrientDeficiencies() > 0) {   // move to DiseaseActivity
                    Intent intent_nextActivity = new Intent(NutrientDeficiency.this, WeedIntensity1.class);
                    intent_nextActivity.putExtra("crop", crop);
                    intent_nextActivity.putExtra("season", season);
                    intent_nextActivity.putExtra("stage", stage);
                    intent_nextActivity.putExtra("health_status", healthStatus);
                    startActivity(intent_nextActivity);
                } else {    // raise error message
                    dialogBox.setTitle("Alert");
                    dialogBox.setMessage("NO/INVALID SELECTIONS");
                    dialogBox.create().show();
                }
            }
        });

        // get previous data
        intent = getIntent();
        crop = intent.getStringExtra("crop");
        season = intent.getStringExtra("season");
        stage = intent.getStringExtra("stage");
        healthStatus = intent.getStringExtra("health_status");

        // init gridView
        gridView = (GridView) findViewById(R.id.nutrientDeficiency_gridview);

        // setting gridView for RICE
        if(crop.equalsIgnoreCase(getResources().getStringArray(R.array.mainActivity_spinner_selectCrop)[1])) {
            String[] nutrientDeficiency_array = this.getResources().getStringArray(R.array.nutrientDeficiency_rice);
            final Integer[] images = {
                    R.drawable.rice_nut_zinc, R.drawable.rice_nut_iron,
                    R.drawable.rice_nut_potassium,
                    R.drawable.noimage,R.drawable.others,R.drawable.question_mark };

            rowItems = new ArrayList<RowItem>();
            for (int i = 0; i < nutrientDeficiency_array.length; i++)
            {
                RowItem item = new RowItem(images[i], nutrientDeficiency_array[i]);
                rowItems.add(item);
            }

            CustomListViewAdapter adapter = new CustomListViewAdapter(this, R.layout.list_item, rowItems);
            gridView.setAdapter(adapter);
        }
        // setting gridView for COTTON
        else if(crop.equalsIgnoreCase(getResources().getStringArray(R.array.mainActivity_spinner_selectCrop)[2])) {
            String[] nutrientDeficiency_array = this.getResources().getStringArray(R.array.nutrientDeficiency_cotton);
            final Integer[] images = {
                    R.drawable.cotton_nut_magnesium, R.drawable.cotton_nut_boron,
                    R.drawable.leafanimated,R.drawable.cotton_nut_iron,
                    R.drawable.cotton_nut_potassium,
                    R.drawable.noimage,R.drawable.others,R.drawable.question_mark };

            rowItems = new ArrayList<RowItem>();
            for (int i = 0; i < nutrientDeficiency_array.length; i++)
            {
                RowItem item = new RowItem(images[i], nutrientDeficiency_array[i]);
                rowItems.add(item);
            }

            CustomListViewAdapter adapter = new CustomListViewAdapter(this, R.layout.list_item, rowItems);
            gridView.setAdapter(adapter);
        }
        // setting gridView for WHEAT
        else if(crop.equalsIgnoreCase(getResources().getStringArray(R.array.mainActivity_spinner_selectCrop)[3])) {
            String[] nutrientDeficiency_array = this.getResources().getStringArray(R.array.nutrientDeficiency_wheat);
            final Integer[] images = {
                    R.drawable.noimage,R.drawable.others,R.drawable.question_mark };

            rowItems = new ArrayList<RowItem>();
            for (int i = 0; i < nutrientDeficiency_array.length; i++)
            {
                RowItem item = new RowItem(images[i], nutrientDeficiency_array[i]);
                rowItems.add(item);
            }

            CustomListViewAdapter adapter = new CustomListViewAdapter(this, R.layout.list_item, rowItems);
            gridView.setAdapter(adapter);
        }
        // setting gridView for MAIZE
        else if(crop.equalsIgnoreCase(getResources().getStringArray(R.array.mainActivity_spinner_selectCrop)[4])) {
            String[] nutrientDeficiency_array = this.getResources().getStringArray(R.array.nutrientDeficiency_maize);
            final Integer[] images = {
                    R.drawable.maize_nut_iron, R.drawable.maize_nut_potassium,
                    R.drawable.maize_nut_sulphur,
                    R.drawable.noimage,R.drawable.others,R.drawable.question_mark };

            rowItems = new ArrayList<RowItem>();
            for (int i = 0; i < nutrientDeficiency_array.length; i++)
            {
                RowItem item = new RowItem(images[i], nutrientDeficiency_array[i]);
                rowItems.add(item);
            }

            CustomListViewAdapter adapter = new CustomListViewAdapter(this, R.layout.list_item, rowItems);
            gridView.setAdapter(adapter);
        }
        // setting gridView for RED GRAM
        else if(crop.equalsIgnoreCase(getResources().getStringArray(R.array.mainActivity_spinner_selectCrop)[5])) {
            String[] nutrientDeficiency_array = this.getResources().getStringArray(R.array.nutrientDeficiency_redGram);
            final Integer[] images = {
                    R.drawable.noimage,R.drawable.others,R.drawable.question_mark };

            rowItems = new ArrayList<RowItem>();
            for (int i = 0; i < nutrientDeficiency_array.length; i++)
            {
                RowItem item = new RowItem(images[i], nutrientDeficiency_array[i]);
                rowItems.add(item);
            }

            CustomListViewAdapter adapter = new CustomListViewAdapter(this, R.layout.list_item, rowItems);
            gridView.setAdapter(adapter);
        }
        // setting gridView for SOYABEAN
        else if(crop.equalsIgnoreCase(getResources().getStringArray(R.array.mainActivity_spinner_selectCrop)[6])) {
            String[] nutrientDeficiency_array = this.getResources().getStringArray(R.array.nutrientDeficiency_soyabean);
            final Integer[] images = {
                    R.drawable.noimage,R.drawable.others,R.drawable.question_mark };

            rowItems = new ArrayList<RowItem>();
            for (int i = 0; i < nutrientDeficiency_array.length; i++)
            {
                RowItem item = new RowItem(images[i], nutrientDeficiency_array[i]);
                rowItems.add(item);
            }

            CustomListViewAdapter adapter = new CustomListViewAdapter(this, R.layout.list_item, rowItems);
            gridView.setAdapter(adapter);
        }
        // setting gridView for GROUNDNUT
        else if(crop.equalsIgnoreCase(getResources().getStringArray(R.array.mainActivity_spinner_selectCrop)[7])) {
            String[] nutrientDeficiency_array = this.getResources().getStringArray(R.array.nutrientDeficiency_groundnut);
            final Integer[] images = {
                    R.drawable.groundnut_nut_iron, R.drawable.groundnut_nut_zinc,
                    R.drawable.noimage,R.drawable.others,R.drawable.question_mark };

            rowItems = new ArrayList<RowItem>();
            for (int i = 0; i < nutrientDeficiency_array.length; i++)
            {
                RowItem item = new RowItem(images[i], nutrientDeficiency_array[i]);
                rowItems.add(item);
            }

            CustomListViewAdapter adapter = new CustomListViewAdapter(this, R.layout.list_item, rowItems);
            gridView.setAdapter(adapter);
        }
        // setting gridView for BLACK GRAM
        else if(crop.equalsIgnoreCase(getResources().getStringArray(R.array.mainActivity_spinner_selectCrop)[8])) {
            String[] nutrientDeficiency_array = this.getResources().getStringArray(R.array.nutrientDeficiency_blackGram);
            final Integer[] images = {
                    R.drawable.blackgram_nut_zinc, R.drawable.blackgram_nut_boron,
                    R.drawable.blackgram_nut_iron, R.drawable.blackgram_nut_magnesium,
                    R.drawable.blackgram_nut_sulphur, R.drawable.leafanimated,
                    R.drawable.noimage,R.drawable.others,R.drawable.question_mark };

            rowItems = new ArrayList<RowItem>();
            for (int i = 0; i < nutrientDeficiency_array.length; i++)
            {
                RowItem item = new RowItem(images[i], nutrientDeficiency_array[i]);
                rowItems.add(item);
            }

            CustomListViewAdapter adapter = new CustomListViewAdapter(this, R.layout.list_item, rowItems);
            gridView.setAdapter(adapter);
        }

        // AlertDialog Box
        dialogBox = new AlertDialog.Builder(this);
        dialogBox.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                //dismiss the dialog
            }
        });

    }

    @Override
    public void onRestart() {
        super.onRestart();
        boxAdapter = new CustomListViewAdapter(NutrientDeficiency.this,
                R.layout.gridviewlayout, rowItems);
        gridView.setAdapter(boxAdapter);
    }

}
