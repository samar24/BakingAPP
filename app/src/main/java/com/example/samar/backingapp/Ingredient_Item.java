package com.example.samar.backingapp;

import android.annotation.SuppressLint;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Samar on 17/05/2018.
 */


public class Ingredient_Item implements Parcelable {
    protected Ingredient_Item(Parcel in) {
        quantity = in.readString();
        measure = in.readString();
        ingredient = in.readString();
    }

    public static final Creator<Ingredient_Item> CREATOR = new Creator<Ingredient_Item>() {
        @Override
        public Ingredient_Item createFromParcel(Parcel in) {
            return new Ingredient_Item(in);
        }

        @Override
        public Ingredient_Item[] newArray(int size) {
            return new Ingredient_Item[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(quantity);
        dest.writeString(measure);
        dest.writeString(ingredient);
    }

    private String quantity;
    private String measure;
    private String ingredient;
    public Ingredient_Item() {
    }

    public Ingredient_Item(String quantity, String measure, String ingredient) {
        this.quantity = quantity;
        this.measure = measure;
        this.ingredient = ingredient;

    }
    public String getquantity() {
        return quantity;
    }

    public void setquantity(String quantity) {
        this.quantity = quantity;
    }

    public String getmeasure() {
        return measure;
    }

    public void setmeasure(String measure) {
        this.measure = measure;
    }

    public String getingredient() {
        return ingredient;
    }

    public void setingredient(String ingredient) {
        this.ingredient = ingredient;
    }
}
