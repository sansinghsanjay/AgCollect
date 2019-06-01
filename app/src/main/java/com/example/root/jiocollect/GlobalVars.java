package com.example.root.jiocollect;

import android.app.Application;
import java.util.ArrayList;
import java.util.List;

public class GlobalVars extends Application {

    // global vars
    private String userName, crop, season, stage, healthStatus, captureTimestamp, city, weatherDescription, temperature, humidity,
                    pressure, updatedOn, sunrise, weedIntensity,waterStress;
    private double latitude, longitude;
    private List<String> pestAttacks = new ArrayList<String>();
    private List<String> diseases = new ArrayList<String>();
    private List<String> nutrientDeficiencies = new ArrayList<String>();

    // Database - global variables
    String databaseName = "jio_collect_db";
    int databaseVersion = 1;

    // resetting global vars
    public void resetVars() {
        userName = "";
        crop = "";
        season = "";
        stage = "";
        healthStatus = "";
        latitude = 0.0;
        longitude = 0.0;
        captureTimestamp = "";
        city = "";
        weatherDescription = "";
        temperature = "";
        humidity = "";
        pressure = "";
        updatedOn = "";
        sunrise = "";
        pestAttacks.clear();
        diseases.clear();
        nutrientDeficiencies.clear();
        weedIntensity = "";
        waterStress = "";
    }

    // set/get userName
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getUserName() {
        return userName;
    }

    // set/get crop
    public void setCrop(String crop) {
        this.crop = crop;
    }
    public String getCrop() {
        return this.crop;
    }

    // set/get season
    public void setSeason(String season) {
        this.season = season;
    }
    public String getSeason() {
        return this.season;
    }

    // set/get stage
    public void setStage(String stage) {
        this.stage = stage;
    }
    public String getStage() {
        return this.stage;
    }

    // set/get healthStatus
    public void setHealthStatus(String healthStatus) {
        this.healthStatus = healthStatus;
    }
    public String getHealthStatus() {
        return this.healthStatus;
    }

    // set/get latitude
    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }
    public double getLatitude() {
        return latitude;
    }

    // set/get longitude
    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
    public double getLongitude() {
        return longitude;
    }

    // set/get captureTimestamp
    public void setCaptureTimestamp(String captureTimestamp) {
        this.captureTimestamp = captureTimestamp;
    }
    public String getCaptureTimestamp() {
        return captureTimestamp;
    }

    // set/get city
    public void setCity(String city) {
        this.city = city;
    }
    public String getCity() {
        return city;
    }

    // set/get weatherDescription
    public void setWeatherDescription(String weatherDescription) {
        this.weatherDescription = weatherDescription;
    }
    public String getWeatherDescription() {
        return weatherDescription;
    }

    // set/get temperature
    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }
    public String getTemperature() {
        return temperature;
    }

    // set/get humidity
    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }
    public String getHumidity() {
        return humidity;
    }

    // set/get pressue
    public void setPressure(String pressure) {
        this.pressure = pressure;
    }
    public String getPressure() {
        return pressure;
    }

    // set/get updatedOn
    public void setUpdatedOn(String updatedOn) {
        this.updatedOn = updatedOn;
    }
    public String getUpdatedOn() {
        return updatedOn;
    }

    // set/get sunrise
    public void setSunrise(String sunrise) {
        this.sunrise = sunrise;
    }
    public String getSunrise() {
        return sunrise;
    }

    // set/get weedIntensity
    public String getWeedintensity() {
        return weedIntensity;
    }
    public void setWeedintensity(String weedIntensity) {
        this.weedIntensity = weedIntensity;
    }

    // set/get waterStress
    public String getWaterStress() { return waterStress; }
    public void setWaterStress(String waterStress) { this.waterStress = waterStress; }

    // set/get/remove/length/clear pestAttacks
    public void setPestAttacks(String pestAttack) {
        if(pestAttacks.contains(pestAttack) == false) {
            pestAttacks.add(pestAttack);
        }
    }
    public List<String> getPestAttacks() {
        return this.pestAttacks;
    }
    public void removePestAttacks(String pestAttack) {
        pestAttacks.remove(pestAttack);
    }
    public int lengthPestAttacks() {
        return pestAttacks.size();
    }
    public void clearPestAttacks() {
        pestAttacks.clear();
    }

    // set/get/remove/length/clear diseases
    public void setDiseases(String disease) {
        if(diseases.contains(disease) == false) {
            diseases.add(disease);
        }
    }
    public List<String> getDiseases() {
        return this.diseases;
    }
    public void removeDiseases(String disease) {
        diseases.remove(disease);
    }
    public int lengthDiseases() {
        return diseases.size();
    }
    public void clearDiseases() {
        diseases.clear();
    }

    // set/get/remove/length/clear nutrientDeficiencies
    public void setNutrientDeficiencies(String nutrientDeficiency) {
        if(nutrientDeficiencies.contains(nutrientDeficiency) == false) {
            nutrientDeficiencies.add(nutrientDeficiency);
        }
    }
    public List<String> getNutrientDeficiencies() {
        return this.nutrientDeficiencies;
    }
    public void removeNutrientDeficiencies(String nutrientDeficiency) {
        nutrientDeficiencies.remove(nutrientDeficiency);
    }
    public int lengthNutrientDeficiencies() {
        return nutrientDeficiencies.size();
    }
    public void clearNutrientDeficiencies() {
        nutrientDeficiencies.clear();
    }

}
