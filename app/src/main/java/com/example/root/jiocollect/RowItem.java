package com.example.root.jiocollect;

/**
 * Created by Vidhiben.Sherathia on 9/25/2018.
 */

public class RowItem {
    private int imageId;
    private String title;
    private String desc;
    Boolean checkboxStatus=false;

    public RowItem(int imageId, String desc) {
        this.imageId = imageId;
        this.desc = desc;
    }

    public RowItem(int imageId, String desc, Boolean checkboxStatus) {
        this.imageId = imageId;
        this.desc = desc;
        this.checkboxStatus = checkboxStatus;
    }

    public int getImageId() {return imageId;}
    public void setImageId(int imageId) {this.imageId = imageId;}

    public String getDesc() {return desc;}
    public void setDesc(String desc) {this.desc = desc;}

    public String getTitle() {return title;}
    public void setTitle(String title) {this.title = title;}

    public Boolean getCheckboxStatus() {
        return checkboxStatus;
    }
    public void setCheckboxStatus(Boolean checkboxStatus) {
        this.checkboxStatus = checkboxStatus;
    }

    @Override
    public String toString() {
        return title + "\n" + desc;
    }
}

