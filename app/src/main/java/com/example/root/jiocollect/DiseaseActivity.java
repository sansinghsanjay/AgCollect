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

public class DiseaseActivity extends AppCompatActivity {

    // GUI elements
    AlertDialog.Builder dialogBox;

    // other vars
    Intent intent;
    String crop, season, stage, healthStatus;
    RemoveNoise removeNoise;
    GlobalVars globalVars;

    GridView gridView;      /*<<----------------Changed Value*/
    List<RowItem> rowItems;      /*<<----------------Changed Value*/
    CustomListViewAdapter boxAdapter;       /*<<----------------Changed Value*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disease);
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
                    if (defectTemplate_iter.checkboxStatus)
                    {
                        globalVars.setDiseases(tempString);
                    }
                    else {    // if value is not checked then remove it from global variable
                        globalVars.removeDiseases(tempString);
                    }
                }
                // validation for - "no", "other", "unknown"
                List<String> tempList = globalVars.getDiseases();
                for(int i=0; i<tempList.size(); i++) {
                    String tempString = tempList.get(i);
                    if(tempString.equalsIgnoreCase(getResources().getString(R.string.disease_noDisease))) {
                        if(tempList.size() > 1) {
                            globalVars.clearDiseases();
                            break;
                        }
                    }
                    if(tempString.equalsIgnoreCase(getResources().getString(R.string.disease_otherDisease))) {
                        if(tempList.size() > 1) {
                            globalVars.clearDiseases();
                            break;
                        }
                    }
                    if(tempString.equalsIgnoreCase(getResources().getString(R.string.disease_unknown))) {
                        if(tempList.size() > 1) {
                            globalVars.clearDiseases();
                            break;
                        }
                    }
                }
                if(globalVars.lengthDiseases() > 0) {   // move to DiseaseActivity
                    Intent intent_nextActivity = new Intent(DiseaseActivity.this, NutrientDeficiency.class);
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
        gridView = (GridView) findViewById(R.id.disease_gridview);

        // setting gridView for RICE
        if(crop.equalsIgnoreCase(getResources().getStringArray(R.array.mainActivity_spinner_selectCrop)[1])) {
            String[] disease_array = this.getResources().getStringArray(R.array.disease_rice);
            final Integer[] images = {
                    R.drawable.rice_disease_neck_blast, R.drawable.rice_disease_leaf_blast,
                    R.drawable.rice_disease_sheath_blight, R.drawable.rice_disease_bacterial_blight,
                    R.drawable.rice_disease_tungro_disease, R.drawable.rice_disease_brown_leaf_spot,
                    R.drawable.noimage,R.drawable.others,R.drawable.question_mark };

            rowItems = new ArrayList<RowItem>();
            for (int i = 0; i < disease_array.length; i++)
            {
                RowItem item = new RowItem(images[i], disease_array[i]);
                rowItems.add(item);
            }

            CustomListViewAdapter adapter = new CustomListViewAdapter(this, R.layout.list_item, rowItems);
            gridView.setAdapter(adapter);
        }
        // setting gridView for COTTON
        else if(crop.equalsIgnoreCase(getResources().getStringArray(R.array.mainActivity_spinner_selectCrop)[2])) {
            String[] disease_array = this.getResources().getStringArray(R.array.disease_cotton);
            final Integer[] images = {
                    R.drawable.cotton_disease_alternaria_leaf_spot, R.drawable.cotton_disease_grey_mildew,
                    R.drawable.cotton_disease_myrothecium_leaf_spot, R.drawable.cotton_disease_fusarium_wilt,
                    R.drawable.cotton_disease_bacterial_leaf_blight, R.drawable.cotton_disease_cotton_leaf_curl_virus,
                    R.drawable.leafanimated,
                    R.drawable.noimage,R.drawable.others,R.drawable.question_mark  };

            rowItems = new ArrayList<RowItem>();
            for (int i = 0; i < disease_array.length; i++)
            {
                RowItem item = new RowItem(images[i], disease_array[i]);
                rowItems.add(item);
            }

            CustomListViewAdapter adapter = new CustomListViewAdapter(this, R.layout.list_item, rowItems);
            gridView.setAdapter(adapter);
        }
        // setting gridView for WHEAT
        else if(crop.equalsIgnoreCase(getResources().getStringArray(R.array.mainActivity_spinner_selectCrop)[3])) {
            String[] disease_array = this.getResources().getStringArray(R.array.disease_wheat);
            final Integer[] images = {
                    R.drawable.wheat_disease_brown_rust, R.drawable.wheat_disease_stripe_rust,
                    R.drawable.wheat_disease_black_rust, R.drawable.wheat_disease_loose_smut,
                    R.drawable.wheat_disease_powdery_mildew, R.drawable.wheat_disease_alternaria_leaf_blight,
                    R.drawable.wheat_disease_karnal_bunt, R.drawable.leafanimated,
                    R.drawable.noimage,R.drawable.others,R.drawable.question_mark };

            rowItems = new ArrayList<RowItem>();
            for (int i = 0; i < disease_array.length; i++)
            {
                RowItem item = new RowItem(images[i], disease_array[i]);
                rowItems.add(item);
            }

            CustomListViewAdapter adapter = new CustomListViewAdapter(this, R.layout.list_item, rowItems);
            gridView.setAdapter(adapter);
        }
        // setting gridView for MAIZE
        else if(crop.equalsIgnoreCase(getResources().getStringArray(R.array.mainActivity_spinner_selectCrop)[4])) {
            String[] disease_array = this.getResources().getStringArray(R.array.disease_maize);
            final Integer[] images = {
                    R.drawable.leafanimated, R.drawable.maize_disease_maydis_leaf_blight,
                    R.drawable.maize_disease_rust, R.drawable.maize_disease_downy_mildew,
                    R.drawable.maize_disease_banded_leaf_and_sheath_blight, R.drawable.maize_disease_fusarium_stalk_rot,
                    R.drawable.maize_disease_charcoal_rot,
                    R.drawable.noimage,R.drawable.others,R.drawable.question_mark };

            rowItems = new ArrayList<RowItem>();
            for (int i = 0; i < disease_array.length; i++)
            {
                RowItem item = new RowItem(images[i], disease_array[i]);
                rowItems.add(item);
            }

            CustomListViewAdapter adapter = new CustomListViewAdapter(this, R.layout.list_item, rowItems);
            gridView.setAdapter(adapter);
        }
        // setting gridView for RED GRAM
        else if(crop.equalsIgnoreCase(getResources().getStringArray(R.array.mainActivity_spinner_selectCrop)[5])) {
            String[] disease_array = this.getResources().getStringArray(R.array.disease_redGram);
            final Integer[] images = {
                    R.drawable.redgram_disease_fusarium_wilt, R.drawable.redgram_disease_root_rot,
                    R.drawable.redgram_disease_sterility_mosaic_virus, R.drawable.redgram_disease_yellow_mosaic,
                    R.drawable.leafanimated,
                    R.drawable.noimage,R.drawable.others,R.drawable.question_mark  };

            rowItems = new ArrayList<RowItem>();
            for (int i = 0; i < disease_array.length; i++)
            {
                RowItem item = new RowItem(images[i], disease_array[i]);
                rowItems.add(item);
            }

            CustomListViewAdapter adapter = new CustomListViewAdapter(this, R.layout.list_item, rowItems);
            gridView.setAdapter(adapter);
        }
        // setting gridView for SOYABEAN
        else if(crop.equalsIgnoreCase(getResources().getStringArray(R.array.mainActivity_spinner_selectCrop)[6])) {
            String[] disease_array = this.getResources().getStringArray(R.array.disease_soyabean);
            final Integer[] images = {
                    R.drawable.soyabean_disease_rust, R.drawable.soyabean_disease_collar_rot,
                    R.drawable.soyabean_disease_charcoal_rot, R.drawable.soyabean_disease_rhizoctonia_root_rot,
                    R.drawable.soyabean_disease_anthracnose,
                    R.drawable.noimage,R.drawable.others,R.drawable.question_mark  };

            rowItems = new ArrayList<RowItem>();
            for (int i = 0; i < disease_array.length; i++)
            {
                RowItem item = new RowItem(images[i], disease_array[i]);
                rowItems.add(item);
            }

            CustomListViewAdapter adapter = new CustomListViewAdapter(this, R.layout.list_item, rowItems);
            gridView.setAdapter(adapter);
        }
        // setting gridView for GROUNDNUT
        else if(crop.equalsIgnoreCase(getResources().getStringArray(R.array.mainActivity_spinner_selectCrop)[7])) {
            String[] disease_array = this.getResources().getStringArray(R.array.disease_groundnut);
            final Integer[] images = {
                    R.drawable.groundnut_disease_alternaria_blight, R.drawable.groundnut_disease_collar_rot,
                    R.drawable.leafanimated, R.drawable.groundnut_disease_early_leaf_spot,
                    R.drawable.groundnut_disease_rust, R.drawable.groundnut_disease_bud_necrosis_virus,
                    R.drawable.noimage,R.drawable.others,R.drawable.question_mark };

            rowItems = new ArrayList<RowItem>();
            for (int i = 0; i < disease_array.length; i++)
            {
                RowItem item = new RowItem(images[i], disease_array[i]);
                rowItems.add(item);
            }

            CustomListViewAdapter adapter = new CustomListViewAdapter(this, R.layout.list_item, rowItems);
            gridView.setAdapter(adapter);
        }
        // setting gridView for BLACK GRAM
        else if(crop.equalsIgnoreCase(getResources().getStringArray(R.array.mainActivity_spinner_selectCrop)[8])) {
            String[] disease_array = this.getResources().getStringArray(R.array.disease_blackGram);
            final Integer[] images = {
                    R.drawable.leafanimated, R.drawable.blackgram_disease_leaf_blight,
                    R.drawable.blackgram_disease_cercospora_leaf_blight, R.drawable.blackgram_disease_powdery_mildew,
                    R.drawable.blackgram_disease_root_rot_and_leaf_blight, R.drawable.blackgram_disease_rust,
                    R.drawable.blackgram_disease_yellow_mosaic, R.drawable.blackgram_disease_leaf_crinkle,
                    R.drawable.noimage,R.drawable.others,R.drawable.question_mark  };

            rowItems = new ArrayList<RowItem>();
            for (int i = 0; i < disease_array.length; i++)
            {
                RowItem item = new RowItem(images[i], disease_array[i]);
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
        boxAdapter = new CustomListViewAdapter(DiseaseActivity.this,
                R.layout.gridviewlayout, rowItems);
        gridView.setAdapter(boxAdapter);
    }

}
