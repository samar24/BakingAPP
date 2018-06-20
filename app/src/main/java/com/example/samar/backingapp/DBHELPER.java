package com.example.samar.backingapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Samar on 20/06/2018.
 */

public class DBHELPER extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "ingredient.db";

    private static final int DATABASE_VERSION = 16;

    public DBHELPER(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        final String SQL_CREATE_INGREDIENTS_TABLE = "CREATE TABLE " + IngredientContract.IngredientEntry.TABLE_NAME + " (" +
                IngredientContract.IngredientEntry.COLUMN_INGREDIENTS_RECIPE_ID + " TEXT NOT NULL, " +
                IngredientContract.IngredientEntry.COLUMN_INGREDIENTS_RECIPE_NAME+ "  TEXT NOT NULL, " +
                IngredientContract.IngredientEntry.COLUMN_INGREDIENTS_MEASURE+ " TEXT NOT NULL, " +
                IngredientContract.IngredientEntry.COLUMN_INGREDIENTS_INGREDIENT+ " TEXT NOT NULL, " +
                IngredientContract.IngredientEntry.COLUMN_INGREDIENTS_QUANTITY+ " TEXT NOT NULL)";

        sqLiteDatabase.execSQL(SQL_CREATE_INGREDIENTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + IngredientContract.IngredientEntry.TABLE_NAME);
        onCreate(db);
    }
}
