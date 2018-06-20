package com.example.samar.backingapp;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

/**
 * Created by Samar on 20/06/2018.
 */

public class ListWidgetService extends RemoteViewsService {

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {

        return new ListRemoteViewsFactory(this.getApplicationContext());

    }


    class ListRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

        Context mContext;
        Cursor mCursor;


        public ListRemoteViewsFactory(Context applicationContext) {
            mContext = applicationContext;

        }

        @Override
        public void onCreate() {

        }

        //called on start and when notifyAppWidgetViewDataChanged is called
        @Override
        public void onDataSetChanged() {

            mCursor = mContext.getContentResolver().query(
                    IngredientContract.IngredientEntry.CONTENT_URI,
                    null,
                    null,
                    null,
                    null
            );
        }
        @Override
        public void onDestroy() {
            mCursor.close();
        }

        @Override
        public int getCount() {
            if (mCursor == null) return 0;
            return mCursor.getCount();
        }

        @Override
        public RemoteViews getViewAt(int position) {
            if (mCursor == null || mCursor.getCount() == 0)
                return null;


            mCursor.moveToPosition(position);

            int recipeIdIndex=mCursor.getColumnIndex(IngredientContract.IngredientEntry.COLUMN_INGREDIENTS_RECIPE_ID);
            int recipeId=mCursor.getInt(recipeIdIndex);
            int recipeNameIndex=mCursor.getColumnIndex(IngredientContract.IngredientEntry.COLUMN_INGREDIENTS_RECIPE_NAME);
            String recipeName=mCursor.getString(recipeNameIndex);
            RemoteViews views = new RemoteViews(mContext.getPackageName(), R.layout.widget_item);

            views.setTextViewText(R.id.widget_Recipe_name,recipeName);
            views.setImageViewResource(R.id.widget_Recipe_image,R.drawable.cover);

            Bundle extras = new Bundle();
            extras.putInt(DetailsActivty.EXTRA_CURSOR_POSITION,position);
            Intent fillInIntent = new Intent();
            fillInIntent.putExtras(extras);
            views.setOnClickFillInIntent(R.id.widget_Recipe_image, fillInIntent);

            return views;

        }

        @Override
        public RemoteViews getLoadingView() {

            return null;
        }

        @Override
        public int getViewTypeCount() {
            return 1;
        }
        @Override
        public long getItemId(int i) {

            return i;
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }
    }
}