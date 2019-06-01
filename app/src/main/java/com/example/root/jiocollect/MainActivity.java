package com.example.root.jiocollect;

import android.accounts.AccountManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.GET_ACCOUNTS;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.RECORD_AUDIO;
import static android.Manifest.permission.SEND_SMS;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class MainActivity extends AppCompatActivity {

    // permissions
    public static final int RequestPermissionCode = 7;

    // GUI declarations
    Spinner spinner_selectCrop, spinner_selectSeason, spinner_selectStage;
    RadioGroup radioGroup_healthyNonhealthy;
    RadioButton radioButton_healthy, radioButton_nonhealthy;
    AlertDialog.Builder dialogBox;

    // database vars
    DBHelper dbHelper;
    String databaseName = "jio_collect_db";
    int databaseVersion = 1;

    // other vars
    RemoveNoise removeNoise;
    GlobalVars globalVars;
    String crop, season, stage, healthStatus, userName;
    ArrayAdapter<CharSequence> cropArrayAdapter, seasonArrayAdapter, stageArrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // initialize objects
        dbHelper = new DBHelper(getApplicationContext(), databaseName, null, databaseVersion);
        removeNoise = new RemoveNoise();
        globalVars = (GlobalVars)getApplication();

        // floating button
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // get selected crop, season and stage
                String selectedCrop = spinner_selectCrop.getSelectedItem().toString();
                String selectedSeason = spinner_selectSeason.getSelectedItem().toString();
                String selectedStage = spinner_selectStage.getSelectedItem().toString();
                // get selected health status
                int selectedId = radioGroup_healthyNonhealthy.getCheckedRadioButtonId();
                RadioButton tempRadioButton = (RadioButton) findViewById(selectedId);
                String selectedHealthStatus = tempRadioButton.getText().toString();
                // verifying selected values
                if(selectedCrop.equalsIgnoreCase(getResources().getStringArray(R.array.mainActivity_spinner_selectCrop)[0]) ||
                        selectedSeason.equalsIgnoreCase(getResources().getStringArray(R.array.mainActivity_spinner_selectSeason)[0]) ||
                        selectedStage.equalsIgnoreCase(getResources().getStringArray(R.array.mainActivity_spinner_initStageValues)[0])) {
                    // show alert message if values are invalid
                    dialogBox.setTitle("Alert");
                    dialogBox.setMessage("Please select valid value for CROP, SEASON and STAGE");
                    dialogBox.create().show();
                } else {
                    // cleaning values and setting into global vars
                    crop = removeNoise.cleanValue(selectedCrop);
                    season = removeNoise.cleanValue(selectedSeason);
                    stage = removeNoise.cleanValue(selectedStage);
                    healthStatus = removeNoise.cleanValue(selectedHealthStatus);
                    globalVars.setCrop(crop);
                    globalVars.setSeason(season);
                    globalVars.setStage(stage);
                    globalVars.setHealthStatus(healthStatus);
                    // if healthStatus is "healthy" then bypass all screens and move to Camera activity
                    if(selectedHealthStatus.equalsIgnoreCase(getResources().getString(R.string.mainActivity_rb_healthy))) {
                        // setting other globalVars as "NA"
                        globalVars.setPestAttacks("NA");
                        globalVars.setDiseases("NA");
                        globalVars.setNutrientDeficiencies("NA");
                        globalVars.setWeedintensity("NA");
                        globalVars.setWaterStress("NA");
                        // creating intent and inserting into intent
                        Intent intent = new Intent(MainActivity.this, CameraActivity.class);
                        intent.putExtra("crop", crop);
                        intent.putExtra("season", season);
                        intent.putExtra("stage", stage);
                        intent.putExtra("health_status", healthStatus);
                        startActivity(intent);
                    } else { // else move to PestAttack activity
                        Intent intent = new Intent(MainActivity.this, PestAttackActivity.class);
                        intent.putExtra("crop", crop);
                        intent.putExtra("season", season);
                        intent.putExtra("stage", stage);
                        intent.putExtra("health_status", healthStatus);
                        startActivity(intent);
                    }
                }
            }
        });

        // spinner_selectCrop
        spinner_selectCrop = (Spinner) findViewById(R.id.spinner_selectCrop);
        cropArrayAdapter = ArrayAdapter.createFromResource(this,
                R.array.mainActivity_spinner_selectCrop, android.R.layout.simple_spinner_item);
        cropArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_selectCrop.setAdapter(cropArrayAdapter);

        // spinner_selectSeason
        spinner_selectSeason = (Spinner) findViewById(R.id.spinner_selectSeason);
        seasonArrayAdapter = ArrayAdapter.createFromResource(this,
                R.array.mainActivity_spinner_selectSeason, android.R.layout.simple_spinner_item);
        seasonArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_selectSeason.setAdapter(seasonArrayAdapter);

        // spinner_selectStage - setting dummy value
        spinner_selectStage = (Spinner) findViewById(R.id.spinner_selectStage);
        stageArrayAdapter = ArrayAdapter.createFromResource(this,
                R.array.mainActivity_spinner_initStageValues, android.R.layout.simple_spinner_item);
        stageArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_selectStage.setAdapter(stageArrayAdapter);

        // spinner_selectStage - set values on runtime - dependent on value of spinner_selectCrop
        spinner_selectCrop.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // resetting all global vars
                globalVars.resetVars();
                // RICE
                String selectCrop= String.valueOf(spinner_selectCrop.getSelectedItem());
                if(selectCrop.equalsIgnoreCase("RICE")) {
                    ArrayAdapter<CharSequence> stringArrayAdapter4 = ArrayAdapter.createFromResource(MainActivity.this,
                            R.array.mainActivity_spinner_riceStage,android.R.layout.simple_spinner_item);
                    stringArrayAdapter4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner_selectStage.setAdapter(stringArrayAdapter4);
                }
                // COTTON
                if(selectCrop.equalsIgnoreCase("COTTON")) {
                    ArrayAdapter<CharSequence> stringArrayAdapter4 = ArrayAdapter.createFromResource(MainActivity.this,
                            R.array.mainActivity_spinner_cottonStage,android.R.layout.simple_spinner_item);
                    stringArrayAdapter4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner_selectStage.setAdapter(stringArrayAdapter4);
                }
                // WHEAT
                if(selectCrop.equalsIgnoreCase("WHEAT")) {
                    ArrayAdapter<CharSequence> stringArrayAdapter4 = ArrayAdapter.createFromResource(MainActivity.this,
                            R.array.mainActivity_spinner_wheatStage,android.R.layout.simple_spinner_item);
                    stringArrayAdapter4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner_selectStage.setAdapter(stringArrayAdapter4);
                }
                // MAIZE
                if(selectCrop.equalsIgnoreCase("MAIZE")) {
                    ArrayAdapter<CharSequence> stringArrayAdapter4 = ArrayAdapter.createFromResource(MainActivity.this,
                            R.array.mainActivity_spinner_maizeStage,android.R.layout.simple_spinner_item);
                    stringArrayAdapter4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner_selectStage.setAdapter(stringArrayAdapter4);
                }
                // RED GRAM
                if(selectCrop.equalsIgnoreCase("REDGRAM")) {
                    ArrayAdapter<CharSequence> stringArrayAdapter4 = ArrayAdapter.createFromResource(MainActivity.this,
                            R.array.mainActivity_spinner_redgramStage,android.R.layout.simple_spinner_item);
                    stringArrayAdapter4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner_selectStage.setAdapter(stringArrayAdapter4);
                }
                // SOYABEAN
                if(selectCrop.equalsIgnoreCase("SOYABEAN")) {
                    ArrayAdapter<CharSequence> stringArrayAdapter4 = ArrayAdapter.createFromResource(MainActivity.this,
                            R.array.mainActivity_spinner_soyabeanStage,android.R.layout.simple_spinner_item);
                    stringArrayAdapter4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner_selectStage.setAdapter(stringArrayAdapter4);
                }
                // GROUNDNUT
                if(selectCrop.equalsIgnoreCase("GROUNDNUT")) {
                    ArrayAdapter<CharSequence> stringArrayAdapter4 = ArrayAdapter.createFromResource(MainActivity.this,
                            R.array.mainActivity_spinner_groundnutStage,android.R.layout.simple_spinner_item);
                    stringArrayAdapter4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner_selectStage.setAdapter(stringArrayAdapter4);
                }
                // BLACK GRAM
                if(selectCrop.equalsIgnoreCase("BLACKGRAM")) {
                    ArrayAdapter<CharSequence> stringArrayAdapter4 = ArrayAdapter.createFromResource(MainActivity.this,
                            R.array.mainActivity_spinner_blackgramStage,android.R.layout.simple_spinner_item);
                    stringArrayAdapter4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner_selectStage.setAdapter(stringArrayAdapter4);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // radioGroup_healthyNonhealthy
        radioGroup_healthyNonhealthy = (RadioGroup) findViewById(R.id.radioGroup_helathyNonhealthy);

        // radioButton_healthy
        radioButton_healthy = (RadioButton) findViewById(R.id.radioButton_healthy);

        // radioButton_nonhealthy
        radioButton_nonhealthy = (RadioButton) findViewById(R.id.radioButton_nonhealthy);

        // radioGroup_healthyNonhealthy: default selection - "radioButton_nonhealthy"
        radioGroup_healthyNonhealthy.check(R.id.radioButton_nonhealthy);

        // AlertDialog Box
        dialogBox = new AlertDialog.Builder(this);
        dialogBox.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                //dismiss the dialog
            }
        });

        if(CheckingPermissionIsEnabledOrNot()) {
            Toast.makeText(MainActivity.this, "All Permissions Granted Successfully", Toast.LENGTH_LONG).show();
        } else {
            RequestMultiplePermission();
        }

        // get userName
        /*AccountManager accountManager = (AccountManager) getSystemService(ACCOUNT_SERVICE);
        userName = accountManager.getAccounts()[0].name;
        userName = userName.split("@")[0];
        userName = userName.replace(".", "-");
        // set userName in globalVars
        globalVars.setUserName(userName);*/

    }//OnCreate

    // Permission function starts from here
    private void RequestMultiplePermission() {
        ActivityCompat.requestPermissions(MainActivity.this, new String[] {
                        CAMERA,
                        ACCESS_FINE_LOCATION,
                        READ_EXTERNAL_STORAGE,
                        WRITE_EXTERNAL_STORAGE,
                        GET_ACCOUNTS
                }, RequestPermissionCode);
    }

    // Calling override method.
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults)
    {
        switch (requestCode) {
            case RequestPermissionCode:
                if (grantResults.length > 0) {
                    boolean CameraPermission = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean LocationPermission = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                    boolean ReadPermission = grantResults[2] == PackageManager.PERMISSION_GRANTED;
                    boolean WritePermission = grantResults[3] == PackageManager.PERMISSION_GRANTED;
                    boolean GetAccountsPermission = grantResults[4] == PackageManager.PERMISSION_GRANTED;
                    if (CameraPermission && LocationPermission && ReadPermission && WritePermission && GetAccountsPermission) {
                        Toast.makeText(MainActivity.this, "Permission Granted", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(MainActivity.this,"Permission Denied",Toast.LENGTH_LONG).show();

                    }
                }
                break;
        }
    }

    // Checking permission is enabled or not using function starts from here.
    public boolean CheckingPermissionIsEnabledOrNot()
    {

        int FirstPermissionResult = ContextCompat.checkSelfPermission(getApplicationContext(), CAMERA);
        int SecondPermissionResult = ContextCompat.checkSelfPermission(getApplicationContext(), RECORD_AUDIO);
        int ThirdPermissionResult = ContextCompat.checkSelfPermission(getApplicationContext(), SEND_SMS);
        int ForthPermissionResult = ContextCompat.checkSelfPermission(getApplicationContext(), GET_ACCOUNTS);

        return FirstPermissionResult == PackageManager.PERMISSION_GRANTED &&
                SecondPermissionResult == PackageManager.PERMISSION_GRANTED &&
                ThirdPermissionResult == PackageManager.PERMISSION_GRANTED &&
                ForthPermissionResult == PackageManager.PERMISSION_GRANTED ;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
