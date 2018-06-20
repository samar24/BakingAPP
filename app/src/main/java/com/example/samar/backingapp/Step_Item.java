package com.example.samar.backingapp;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Samar on 17/05/2018.
 */

public class Step_Item implements Parcelable{
    protected Step_Item(Parcel in) {
        ID = in.readString();
        shortDescription = in.readString();
        description = in.readString();
        videoURL = in.readString();
        thumbnailURL = in.readString();
    }

    public static final Creator<Step_Item> CREATOR = new Creator<Step_Item>() {
        @Override
        public Step_Item createFromParcel(Parcel in) {
            return new Step_Item(in);
        }

        @Override
        public Step_Item[] newArray(int size) {
            return new Step_Item[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(ID);
        dest.writeString(shortDescription);
        dest.writeString(description);
        dest.writeString(videoURL);
        dest.writeString(thumbnailURL);
    }

    private String ID;
    private String shortDescription;
    private String description;
    private String videoURL;
    private String thumbnailURL;
    public Step_Item() {
    }

    public Step_Item(String ID, String shortDescription, String description,String videoURL,String thumbnailURL) {
        this.ID = ID;
        this.shortDescription = shortDescription;
        this.description = description;
        this.videoURL = videoURL;
        this.thumbnailURL = thumbnailURL;
    }
    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getshortDescription() {
        return shortDescription;
    }

    public void setshortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getdescription() {
        return description;
    }

    public void setdescription(String description) {
        this.description = description;
    }

    public String getvideoURL() {
        return videoURL;
    }

    public void setvideoURL(String videoURL) {
        this.videoURL = videoURL;
    }

    public String getthumbnailURL() {
        return thumbnailURL;
    }

    public void setthumbnailURL(String thumbnailURL) {
        this.thumbnailURL = thumbnailURL;
    }
}
