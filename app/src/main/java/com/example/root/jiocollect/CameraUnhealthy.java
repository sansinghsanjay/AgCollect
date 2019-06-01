package com.example.root.jiocollect;

import android.accounts.AccountManager;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.IntentSender;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.drive.Drive;
import com.google.android.gms.drive.DriveApi;
import com.google.android.gms.drive.MetadataChangeSet;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class CameraUnhealthy extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    // GUI declaration
    Button buttonViewSelectedData, buttonCamera, buttonDrive;
    ImageView imageView;

    // other vars
    Intent intent;
    String crop, season, stage, healthStatus, pestAttacks, imageName, imageExtension,userName;
    double latitude, longitude;
    GlobalVars globalVars;
    DBHelper dbHelper;
    GPSTracker gpsTracker;
    JSONObject data = null;
    int imageWidth = 512, imageHeight = 512;
    Boolean imageCaptured;
    File imageDestFile;
    private static final int REQUEST_CODE = 1;

    //drive specifications
    private GoogleApiClient mGoogleApiClient;
    private static final String DIRECTORY = "JIO_TID";
    private static final int REQUEST_CODE_RESOLUTION = 1;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_unhealthy);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // add back button to NutrientDeficiency Activity
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // init GlobalVariable class object
        globalVars = (GlobalVars) getApplication();

        // init Database helper class object
        dbHelper = new DBHelper(getApplicationContext(), globalVars.databaseName, null,
                globalVars.databaseVersion);

        // init gpsTracker
        gpsTracker = new GPSTracker(getApplicationContext());

        // get latitude and longitude
        latitude = gpsTracker.getLatitude();
        longitude = gpsTracker.getLongitude();

        // setting latitude and longitude
        globalVars.setLatitude(latitude);
        globalVars.setLongitude(longitude);

        // set takeImage false
        imageCaptured = false;

        // init imageExtension
        imageExtension = ".png";

        // get intent values
        intent = getIntent();
        crop = intent.getStringExtra("crop");
        season = intent.getStringExtra("season");
        stage = intent.getStringExtra("stage");
        healthStatus = intent.getStringExtra("health_status");
        pestAttacks = intent.getStringExtra("pest_attacks");

        // get userName
        AccountManager accountManager = (AccountManager) getSystemService(ACCOUNT_SERVICE);
        userName = accountManager.getAccounts()[0].name;
        userName = userName.split("@")[0];
        userName = userName.replace(".", "-");
        // set userName in globalVars
        globalVars.setUserName(userName);

        // getting weather information
        WeatherAPI.placeIdTask asyncTask = new WeatherAPI.placeIdTask(new WeatherAPI.AsyncResponse() {
            public void processFinish(String weather_city, String weather_description, String weather_temperature,
                String weather_humidity, String weather_pressure, String weather_updatedOn,
                String sun_rise) {
                    // setting all values in globalVars
                    globalVars.setCity(weather_city);
                    globalVars.setWeatherDescription(weather_description);
                    globalVars.setTemperature(weather_temperature);
                    globalVars.setHumidity(weather_humidity);
                    globalVars.setPressure(weather_pressure);
                    globalVars.setSunrise(sun_rise);
                    String updatedOn = weather_updatedOn.replace(",", "");
                    updatedOn = updatedOn.replace(" ", "-");
                    globalVars.setUpdatedOn(updatedOn);
            }
        });
        asyncTask.execute(String.valueOf(latitude), String.valueOf(longitude));

        // buttonViewSelectedData
        buttonViewSelectedData=findViewById(R.id.buttonViewDetails_healthy);
        buttonViewSelectedData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // custom dialog
                final Dialog dialog = new Dialog(CameraUnhealthy.this);
                dialog.setContentView(R.layout.custom_popup);
                dialog.setTitle("SELECTED DATA");
                WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                lp.copyFrom(dialog.getWindow().getAttributes());
                lp.width = WindowManager.LayoutParams.MATCH_PARENT;
                lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
                lp.gravity = Gravity.CENTER;
                dialog.getWindow().setAttributes(lp);
                // collect all selected data
                String allData = "CROP: " + globalVars.getCrop() + "\n";
                allData += "SEASON: " + globalVars.getSeason() + "\n";
                allData += "STAGE: " + globalVars.getStage() + "\n";
                allData += "HEALTH STATUS: " + globalVars.getHealthStatus() + "\n";
                allData += "PEST ATTACKS: ";
                List<String> tempList = globalVars.getPestAttacks();
                for(int i=0; i<tempList.size(); i++) {
                    allData += tempList.get(i) + ",";
                }
                allData += "\n";
                allData += "DISEASES: ";
                tempList = globalVars.getDiseases();
                for(int i=0; i<tempList.size(); i++) {
                    allData += tempList.get(i) + ",";
                }
                allData += "\n";
                allData += "NUTRIENT DEFICIENCIES: ";
                tempList = globalVars.getNutrientDeficiencies();
                for(int i=0; i<tempList.size(); i++) {
                    allData += tempList.get(i) + ",";
                }
                allData += "\n";
                allData+="WEED INTENSITY: " + globalVars.getWeedintensity() + "\n";
                allData+="WATER STRESS: " + globalVars.getWaterStress() + "\n";
                allData += "CAPTURE TIMESTAMP: " + globalVars.getCaptureTimestamp() + "\n";
                allData += "LATITUDE: " + globalVars.getLatitude() + "\n";
                allData += "LONGITUDE: " + globalVars.getLongitude() + "\n";
                allData += "CITY: " + globalVars.getCity() + "\n";
                allData += "WEATHER: " + globalVars.getWeatherDescription() + "\n";
                allData += "TEMPERATURE: " + globalVars.getTemperature() + "\n";
                allData += "HUMIDITY: " + globalVars.getHumidity() + "\n";
                allData += "PRESSURE: " + globalVars.getPressure() + "\n";
                allData += "SUNRISE: " + globalVars.getSunrise() + "\n";
                allData += "UPDATED ON: " + globalVars.getUpdatedOn() + "\n";
                // set allData in textview
                TextView text = (TextView) dialog.findViewById(R.id.tvlabel);
                text.setText(allData);
                // show dialog box
                dialog.show();
            }
        });

        // init imageView
        imageView = (ImageView) findViewById(R.id.imageView);

        // cameraButton
        buttonCamera = (Button) findViewById(R.id.buttonCaptureImage_healthy);
        buttonCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // capture timestamp
                DateFormat df = new SimpleDateFormat("dd-MM-yy HH:mm:ss");
                Calendar calendar = Calendar.getInstance();
                globalVars.setCaptureTimestamp(df.format(calendar.getTime()));
                // start camera
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                //intent.putExtra(MediaStore.EXTRA_OUTPUT, imageToUploadUri);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });

        // buttonDrive
        buttonDrive = (Button) findViewById(R.id.buttonSave_healthy);
        buttonDrive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(imageDestFile != null) {
                    if(imageDestFile.exists()) {
                        imageCaptured = false;
                        saveFileToDrive(imageName);
                        imageCaptured = true;
                        imageDestFile.delete();
                        imageDestFile = null;
                    }
                    else {
                        Toast.makeText(getApplicationContext(), "Last Image Not Found", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }

    // onActivityResult for image
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            if (data != null) {
                File HOME_DIR = new File(Environment.getExternalStorageDirectory()
                        + File.separator + DIRECTORY);
                if (!HOME_DIR.exists()) {
                    HOME_DIR.mkdirs();
                }
                // build imageName
                imageName = getImageName();
                imageDestFile = new File(Environment.getExternalStorageDirectory()
                        + File.separator + DIRECTORY + File.separator + imageName + imageExtension);
                Bitmap photo = (Bitmap) data.getExtras().get("data");
                try {
                    FileOutputStream outputStream = new FileOutputStream(imageDestFile);
                    photo.compress(Bitmap.CompressFormat.PNG, 90, outputStream);
                    outputStream.flush();
                    outputStream.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                // set image on imageView
                imageView.setImageBitmap(photo);
            }
        }
    }

    public String getImageName() {
        String temp = "";
        // putting userName
        temp = globalVars.getUserName() + "_";
        // putting crop
        temp += dbHelper.getCode(dbHelper.tableName[0], globalVars.getCrop()) + "_";
        // putting season
        temp += dbHelper.getCode(dbHelper.tableName[1], globalVars.getSeason()) + "_";
        // putting stage
        temp += dbHelper.getCode(dbHelper.tableName[2], globalVars.getStage()) + "_";
        // putting healthStatus
        temp += dbHelper.getCode(dbHelper.tableName[3], globalVars.getHealthStatus()) + "_";
        // putting pestAttack
        List<String> tempList = globalVars.getPestAttacks();
        for(int i=0; i<tempList.size(); i++) {
            if(i < tempList.size() - 1) {
                temp += dbHelper.getCode(dbHelper.tableName[4], tempList.get(i)) + "-";
            } else {
                temp += dbHelper.getCode(dbHelper.tableName[4], tempList.get(i)) + "_";
            }
        }
        // putting disease
        tempList = globalVars.getDiseases();
        for(int i=0; i<tempList.size(); i++) {
            if(i < tempList.size() - 1) {
                temp += dbHelper.getCode(dbHelper.tableName[5], tempList.get(i)) + "-";
            } else {
                temp += dbHelper.getCode(dbHelper.tableName[5], tempList.get(i)) + "_";
            }
        }
        // putting nutrientDeficiency
        tempList = globalVars.getNutrientDeficiencies();
        for(int i=0; i<tempList.size(); i++) {
            if(i < tempList.size() - 1) {
                temp += dbHelper.getCode(dbHelper.tableName[6], tempList.get(i)) + "-";
            } else {
                temp += dbHelper.getCode(dbHelper.tableName[6], tempList.get(i)) + "_";
            }
        }
        //putting weedIntensity
        temp += dbHelper.getCode(dbHelper.tableName[7], globalVars.getWeedintensity()) + "_";
        //putting waterStress
        temp += dbHelper.getCode(dbHelper.tableName[8], globalVars.getWaterStress()) + "_";
        // putting latitude
        temp += globalVars.getLatitude() + "_";
        // putting longitude
        temp += globalVars.getLongitude() + "_";
        // putting city
        temp += globalVars.getCity() + "_";
        // putting weatherDescription
        temp += globalVars.getWeatherDescription() + "_";
        // putting temperature
        temp += globalVars.getTemperature() + "_";
        // putting humidity
        temp += globalVars.getHumidity() + "_";
        // putting pressure
        temp += globalVars.getPressure() + "_";
        // putting sunrise
        temp += globalVars.getSunrise() + "_";
        // putting updatedOn
        temp += globalVars.getUpdatedOn() + "_";
        // putting capturedTimestamp
        temp += globalVars.getCaptureTimestamp();
        return temp;
    }

    // saving image file to GDrive
    private void saveFileToDrive(final String localImageName) {
        // read image from storage
        final Bitmap image = BitmapFactory.decodeFile(Environment.getExternalStorageDirectory()
                + File.separator + DIRECTORY + File.separator + imageName + imageExtension);
        // resize image
        final Bitmap resize_image = Bitmap.createScaledBitmap(image, imageWidth, imageHeight, false);
        // upload code
        Drive.DriveApi.newDriveContents(mGoogleApiClient).setResultCallback(new ResultCallback<DriveApi.DriveContentsResult>() {
            @Override
            public void onResult(DriveApi.DriveContentsResult result) {
                if (!result.getStatus().isSuccess()) {
                    Log.i("ERROR", "Failed to create new contents.");
                    return;
                }
                OutputStream outputStream = result.getDriveContents().getOutputStream();
                // Write the bitmap data from it.
                ByteArrayOutputStream bitmapStream = new ByteArrayOutputStream();
                resize_image.compress(Bitmap.CompressFormat.PNG, 100, bitmapStream);
                try {
                    outputStream.write(bitmapStream.toByteArray());
                } catch (IOException e1) {
                    Log.i("ERROR", "Unable to write file contents.");
                }
                // Create the initial metadata - MIME type and title.
                // Note that the user will be able to change the title later.
                MetadataChangeSet metadataChangeSet = new MetadataChangeSet.Builder()
                    .setMimeType("image/jpeg").setTitle(localImageName + imageExtension).build();
                // Create an intent for the file chooser, and start it.
                IntentSender intentSender = Drive.DriveApi
                .newCreateFileActivityBuilder()
                .setInitialMetadata(metadataChangeSet)
                .setInitialDriveContents(result.getDriveContents())
                .build(mGoogleApiClient);
                try {
                startIntentSenderForResult(intentSender, 1, null, 0,
                        0, 0);
                } catch (IntentSender.SendIntentException e) {
                    Log.i("ERROR", "Failed to launch file chooser.");
                }
            }
        });
        imageCaptured = true;
        Intent intent = new Intent(CameraUnhealthy.this, MainActivity.class);
        startActivity(intent);
    }

    private GoogleSignInClient buildGoogleSignInClient() {
        GoogleSignInOptions signInOptions =
                new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                        .requestScopes(Drive.SCOPE_FILE)
                        .build();
        return GoogleSignIn.getClient(this, signInOptions);
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Log.i("Status: ", "In onConnected Method");
        Toast.makeText(getApplicationContext(), "Connected to Google Drive", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.i("Status: ", "GoogleApiClient connection suspended");
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        // Called whenever the API client fails to connect.
        Log.i("Status: ", "GoogleApiClient connection failed: " + connectionResult.toString());
        if (!connectionResult.hasResolution()) {
            // show the localized error dialog.
            GoogleApiAvailability.getInstance().getErrorDialog(this, connectionResult.getErrorCode(), 0).show();
            return;
        }
        try {
            connectionResult.startResolutionForResult(this, REQUEST_CODE_RESOLUTION);
        } catch (IntentSender.SendIntentException e) {
            Log.i("Status: ", "Exception while starting resolution activity", e);
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("Status: ", "In onResume Method");
        if (mGoogleApiClient == null) {
            /**
             * Create the API client and bind it to an instance variable.
             * We use this instance as the callback for connection and connection failures.
             * Since no account name is passed, the user is prompted to choose.
             */
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addApi(Drive.API)
                    .addScope(Drive.SCOPE_FILE)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .build();
        }
        mGoogleApiClient.connect();
    }

}