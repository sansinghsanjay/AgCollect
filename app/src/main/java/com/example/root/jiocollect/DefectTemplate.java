package com.example.root.jiocollect;

public class DefectTemplate {

    //String diseasename;
    String defectName;
    Boolean checkboxStatus=false;
    int image;
    int id;

    public DefectTemplate(String defectName, int image) {
        this.defectName = defectName;
        this.image = image;
    }

    public DefectTemplate(String defectName, Boolean checkboxStatus, int image) {
        this.defectName = defectName;
        this.checkboxStatus = checkboxStatus;
        this.image = image;
    }

    public DefectTemplate(String defectName, Boolean checkboxStatus, int image, int id) {
        this.defectName = defectName;
        this.checkboxStatus = checkboxStatus;
        this.image = image;
        this.id = id;
    }

    public String getDefectName() {
        return defectName;
    }

    public void setDefectName(String defectName) {
        this.defectName = defectName;
    }

    public Boolean getCheckboxStatus() {
        return checkboxStatus;
    }

    public void setCheckboxStatus(Boolean checkboxStatus) {
        this.checkboxStatus = checkboxStatus;
    }

    public int getImage() {
        image = R.drawable.leaf;
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

}