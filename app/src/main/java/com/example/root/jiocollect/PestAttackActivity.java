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

public class PestAttackActivity extends AppCompatActivity {

    // GUI declarations
    AlertDialog.Builder dialogBox;

    // other vars
    Intent intent;
    GlobalVars globalVars;
    String crop, season, stage, healthStatus;
    RemoveNoise removeNoise;

    GridView gridView;      /*<<----------------Changed Value*/
    List<RowItem> rowItems;      /*<<----------------Changed Value*/
    CustomListViewAdapter boxAdapter;       /*<<----------------Changed Value*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pest_attack);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // init vars
        removeNoise = new RemoveNoise();
        globalVars = (GlobalVars)getApplication();

        // add back button to MainActivity
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // floating button
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                for (RowItem defectTemplate_iter : boxAdapter.getBox()) {
                    String tempString = removeNoise.cleanValue(defectTemplate_iter.getDesc());
                    if (defectTemplate_iter.checkboxStatus) {
                        globalVars.setPestAttacks(tempString);
                    } else {    // if value is not checked then remove it from global variable
                        globalVars.removePestAttacks(tempString);
                    }
                }
                // validation for - "no", "other", "unknown"
                List<String> tempList = globalVars.getPestAttacks();
                for(int i=0; i<tempList.size(); i++) {
                    String tempString = tempList.get(i);
                    if(tempString.equalsIgnoreCase(getResources().getString(R.string.pestAttack_noInsect))) {
                        if(tempList.size() > 1) {
                            globalVars.clearPestAttacks();
                            break;
                        }
                    }
                    if(tempString.equalsIgnoreCase(getResources().getString(R.string.pestAttack_otherInsect))) {
                        if(tempList.size() > 1) {
                            globalVars.clearPestAttacks();
                            break;
                        }
                    }
                    if(tempString.equalsIgnoreCase(getResources().getString(R.string.pestAttack_unknown))) {
                        if(tempList.size() > 1) {
                            globalVars.clearPestAttacks();
                            break;
                        }
                    }
                }
                if(globalVars.lengthPestAttacks() > 0) {   // move to DiseaseActivity
                    Intent intent_nextActivity = new Intent(PestAttackActivity.this, DiseaseActivity.class);
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

        // get intent values
        intent = getIntent();
        crop = intent.getStringExtra("crop");
        season = intent.getStringExtra("season");
        stage = intent.getStringExtra("stage");
        healthStatus = intent.getStringExtra("health_status");

        // init gridView
        gridView = (GridView) findViewById(R.id.pestAttack_gridview);

        // setting gridView for RICE
        if(crop.equalsIgnoreCase(getResources().getStringArray(R.array.mainActivity_spinner_selectCrop)[1]))
        {
            String[] insect_array = this.getResources().getStringArray(R.array.pestAttack_rice);
            final Integer[] images = {
                    R.drawable.rice_pest_stem_borer, R.drawable.rice_pest_leaf_folder,
                    R.drawable.rice_pest_brown_plant_hopper, R.drawable.rice_pest_green_leaf_hopper,
                    R.drawable.rice_pest_rice_hispa, R.drawable.rice_pest_gall_midge,
                    R.drawable.rice_pest_case_worm,
                    R.drawable.noimage,R.drawable.others,R.drawable.question_mark  };

            rowItems = new ArrayList<RowItem>();
            for (int i = 0; i < insect_array.length; i++)
            {
                RowItem item = new RowItem(images[i], insect_array[i]);
                rowItems.add(item);
            }

            CustomListViewAdapter adapter = new CustomListViewAdapter(this, R.layout.list_item, rowItems);
            gridView.setAdapter(adapter);
        }

        // setting gridView for COTTON
        else if(crop.equalsIgnoreCase(getResources().getStringArray(R.array.mainActivity_spinner_selectCrop)[2]))
        {
            String[] insect_array = this.getResources().getStringArray(R.array.pestAttack_cotton);
            final Integer[] images = {
                    R.drawable.leafanimated, R.drawable.cotton_pest_pink_bollworm,
                    R.drawable.cotton_pest_spotted_bollworm, R.drawable.leafanimated,
                    R.drawable.cotton_pest_leaf_hopper, R.drawable.cotton_pest_aphid,
                    R.drawable.cotton_pest_white_fly, R.drawable.cotton_pest_mealybugs,
                    R.drawable.cotton_pest_dusky_cotton_bug,
                    R.drawable.noimage,R.drawable.others,R.drawable.question_mark };

            rowItems = new ArrayList<RowItem>();
            for (int i = 0; i < insect_array.length; i++)
            {
                RowItem item = new RowItem(images[i], insect_array[i]);
                rowItems.add(item);
            }

            CustomListViewAdapter adapter = new CustomListViewAdapter(this, R.layout.list_item, rowItems);
            gridView.setAdapter(adapter);
        }
        // setting gridView for WHEAT
        else if(crop.equalsIgnoreCase(getResources().getStringArray(R.array.mainActivity_spinner_selectCrop)[3]))
        {
            String[] insect_array = this.getResources().getStringArray(R.array.pestAttack_wheat);
            final Integer[] images = {
                    R.drawable.wheat_pest_aphid, R.drawable.wheat_pest_brown_mite,
                    R.drawable.wheat_pest_termite, R.drawable.wheat_pest_pink_stem_borer,
                    R.drawable.leafanimated, R.drawable.wheat_pest_army_worm,
                    R.drawable.leafanimated,
                    R.drawable.noimage,R.drawable.others,R.drawable.question_mark  };

            rowItems = new ArrayList<RowItem>();
            for (int i = 0; i < insect_array.length; i++)
            {
                RowItem item = new RowItem(images[i], insect_array[i]);
                rowItems.add(item);
            }

            CustomListViewAdapter adapter = new CustomListViewAdapter(this, R.layout.list_item, rowItems);
            gridView.setAdapter(adapter);
        }
        // setting gridView for MAIZE
        else if(crop.equalsIgnoreCase(getResources().getStringArray(R.array.mainActivity_spinner_selectCrop)[4]))
        {
            String[] insect_array = this.getResources().getStringArray(R.array.pestAttack_maize);
            final Integer[] images = {
                    R.drawable.maize_pest_shoot_fly, R.drawable.maize_pest_pink_borer,
                    R.drawable.maize_pest_stem_borer, R.drawable.maize_pest_aphid,
                    R.drawable.maize_pest_cab_borer,
                    R.drawable.noimage,R.drawable.others,R.drawable.question_mark  };

            rowItems = new ArrayList<RowItem>();
            for (int i = 0; i < insect_array.length; i++)
            {
                RowItem item = new RowItem(images[i], insect_array[i]);
                rowItems.add(item);
            }

            CustomListViewAdapter adapter = new CustomListViewAdapter(this, R.layout.list_item, rowItems);
            gridView.setAdapter(adapter);
        }
        // setting gridView for RED GRAM
        else if(crop.equalsIgnoreCase(getResources().getStringArray(R.array.mainActivity_spinner_selectCrop)[5]))
        {
            String[] insect_array = this.getResources().getStringArray(R.array.pestAttack_redGram);
            final Integer[] images = {
                    R.drawable.redgram_pest_blister_beetle, R.drawable.redgram_pest_plum_moth,
                    R.drawable.leafanimated, R.drawable.redgram_pest_spotted_pod_borer,
                    R.drawable.leafanimated, R.drawable.redgram_pest_pod_fly,
                    R.drawable.leafanimated, R.drawable.leafanimated,
                    R.drawable.noimage,R.drawable.others,R.drawable.question_mark  };

            rowItems = new ArrayList<RowItem>();
            for (int i = 0; i < insect_array.length; i++)
            {
                RowItem item = new RowItem(images[i], insect_array[i]);
                rowItems.add(item);
            }

            CustomListViewAdapter adapter = new CustomListViewAdapter(this, R.layout.list_item, rowItems);
            gridView.setAdapter(adapter);
        }
        // setting gridView for SOYABEAN
        else if(crop.equalsIgnoreCase(getResources().getStringArray(R.array.mainActivity_spinner_selectCrop)[6]))
        {
            String[] insect_array = this.getResources().getStringArray(R.array.pestAttack_soyabean);
            final Integer[] images = {
                    R.drawable.soyabean_pest_stem_fly, R.drawable.soyabean_pest_leaf_defoliator,
                    R.drawable.soyabean_pest_pod_borer, R.drawable.leafanimated,
                    R.drawable.soyabean_pest_semi_looper, R.drawable.soyabean_pest_leaf_miner,
                    R.drawable.soyabean_pest_white_fly, R.drawable.leafanimated,
                    R.drawable.noimage,R.drawable.others,R.drawable.question_mark  };

            rowItems = new ArrayList<RowItem>();
            for (int i = 0; i < insect_array.length; i++)
            {
                RowItem item = new RowItem(images[i], insect_array[i]);
                rowItems.add(item);
            }

            CustomListViewAdapter adapter = new CustomListViewAdapter(this, R.layout.list_item, rowItems);
            gridView.setAdapter(adapter);
        }
        // setting gridView for GROUNDNUT
        else if(crop.equalsIgnoreCase(getResources().getStringArray(R.array.mainActivity_spinner_selectCrop)[7]))
        {
            String[] insect_array = this.getResources().getStringArray(R.array.pestAttack_groundnut);
            final Integer[] images = {
                    R.drawable.groundnut_pest_aphid, R.drawable.groundnut_pest_leaf_miner,
                    R.drawable.leafanimated, R.drawable.leafanimated,
                    R.drawable.groundnut_pest_red_hairy_caterpillar, R.drawable.leafanimated,
                    R.drawable.groundnut_pest_thrips, R.drawable.groundnut_pest_jassids,
                    R.drawable.noimage,R.drawable.others,R.drawable.question_mark  };

            rowItems = new ArrayList<RowItem>();
            for (int i = 0; i < insect_array.length; i++)
            {
                RowItem item = new RowItem(images[i], insect_array[i]);
                rowItems.add(item);
            }

            CustomListViewAdapter adapter = new CustomListViewAdapter(this, R.layout.list_item, rowItems);
            gridView.setAdapter(adapter);
        }
        // setting gridView for BLACK GRAM
        else if(crop.equalsIgnoreCase(getResources().getStringArray(R.array.mainActivity_spinner_selectCrop)[8]))
        {
            String[] insect_array = this.getResources().getStringArray(R.array.pestAttack_blackGram);
            final Integer[] images = {
                    R.drawable.blackgram_pest_pod_borer, R.drawable.leafanimated,
                    R.drawable.blackgram_pest_blue_butterfly_larva, R.drawable.blackgram_pest_aphid,
                    R.drawable.leafanimated, R.drawable.leafanimated,
                    R.drawable.blackgram_pest_stink_bug, R.drawable.blackgram_pest_white_fly,
                    R.drawable.leafanimated, R.drawable.leafanimated,
                    R.drawable.leafanimated, R.drawable.leafanimated,
                    R.drawable.noimage,R.drawable.others,R.drawable.question_mark  };

            rowItems = new ArrayList<RowItem>();
            for (int i = 0; i < insect_array.length; i++)
            {
                RowItem item = new RowItem(images[i], insect_array[i]);
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
        boxAdapter = new CustomListViewAdapter(PestAttackActivity.this,
                R.layout.gridviewlayout, rowItems);
        gridView.setAdapter(boxAdapter);
    }

}
