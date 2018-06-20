package com.example.samar.backingapp;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Samar on 20/06/2018.
 */

public class BackingAppContentProvider extends ContentProvider {


    public static final int INGREDIENTS = 100;
    public static final int INGREDIENTS_WITH_RECIPE_ID = 101;

    private static final UriMatcher sUriMatcher = buildUriMatcher();
    private static final String TAG = BackingAppContentProvider.class.getName();

    public static UriMatcher buildUriMatcher() {
        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(IngredientContract.AUTHORITY, IngredientContract.PATH_INGREDIENTS, INGREDIENTS);
        uriMatcher.addURI(IngredientContract.AUTHORITY, IngredientContract.PATH_INGREDIENTS + "/#", INGREDIENTS_WITH_RECIPE_ID);
        return uriMatcher;
    }

    private DBHELPER mIngredientDBHelper;

    @Override
    public boolean onCreate() {
        Context context = getContext();
        mIngredientDBHelper = new DBHELPER(context);
        return true;
    }

    @Override
    public Uri insert(@NonNull Uri uri, ContentValues values) {

        final SQLiteDatabase db = mIngredientDBHelper.getWritableDatabase();

        int match = sUriMatcher.match(uri);
        Uri returnUri;
        switch (match) {
            case INGREDIENTS:
                long id = db.insert(IngredientContract.IngredientEntry.TABLE_NAME, null, values);
                if (id > 0) {
                    returnUri = ContentUris.withAppendedId(IngredientContract.IngredientEntry.CONTENT_URI, id);
                    getContext().getContentResolver().notifyChange(returnUri, null);
                } else {
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                }
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);

        return returnUri;
    }


    @Override
    public Cursor query(@NonNull Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {

        final SQLiteDatabase db = mIngredientDBHelper.getReadableDatabase();

        int match = sUriMatcher.match(uri);
        Cursor retCursor;

        switch (match) {
            case INGREDIENTS:
                retCursor = db.query(IngredientContract.IngredientEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        null);
                break;
            case INGREDIENTS_WITH_RECIPE_ID:
                String id = uri.getPathSegments().get(1);
                retCursor = db.query(IngredientContract.IngredientEntry.TABLE_NAME,
                        projection,
                        "recipeID=?",
                        new String[]{id},
                        null,
                        null,
                        null);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        retCursor.setNotificationUri(getContext().getContentResolver(), uri);

        return retCursor;
    }

    @Override
    public int delete(@NonNull Uri uri, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = mIngredientDBHelper.getWritableDatabase();
        int match = sUriMatcher.match(uri);

        int IngredientsDeleted;
        switch (match) {

            case INGREDIENTS:

                IngredientsDeleted = db.delete(IngredientContract.IngredientEntry.TABLE_NAME, null,null);
                break;
            case INGREDIENTS_WITH_RECIPE_ID:
                String id = uri.getPathSegments().get(1);
                IngredientsDeleted = db.delete(IngredientContract.IngredientEntry.TABLE_NAME, "recipeID=?", new String[]{id});
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        // Notify the resolver of a change and return the number of recipes items deleted
        if (IngredientsDeleted != 0) {
            // A recipe(or more) was deleted, set notification
            getContext().getContentResolver().notifyChange(uri, null);
        }
        // Return the number of recipes deleted
        return IngredientsDeleted;
    }

    @Override
    public int update(@NonNull Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        final SQLiteDatabase db = mIngredientDBHelper.getWritableDatabase();
        int match = sUriMatcher.match(uri);
        int IngredientsUpdated;

        switch (match) {
            case INGREDIENTS:
                IngredientsUpdated = db.update(IngredientContract.IngredientEntry.TABLE_NAME, values, selection, selectionArgs);
                break;
            case INGREDIENTS_WITH_RECIPE_ID:
                if (selection == null) selection = IngredientContract.IngredientEntry.COLUMN_INGREDIENTS_RECIPE_ID + "=?";
                else selection += " AND " + IngredientContract.IngredientEntry.COLUMN_INGREDIENTS_RECIPE_ID + "=?";
                String id = uri.getPathSegments().get(1);

                if (selectionArgs == null) selectionArgs = new String[]{id};
                else {
                    ArrayList<String> selectionArgsList = new ArrayList<String>();
                    selectionArgsList.addAll(Arrays.asList(selectionArgs));
                    selectionArgsList.add(id);
                    selectionArgs = selectionArgsList.toArray(new String[selectionArgsList.size()]);
                }
                IngredientsUpdated = db.update(IngredientContract.IngredientEntry.TABLE_NAME, values, selection, selectionArgs);
                break;

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        // Notify the resolver of a change and return the number of items updated
        if (IngredientsUpdated != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return IngredientsUpdated;
    }


    @Override
    public String getType(@NonNull Uri uri) {
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
