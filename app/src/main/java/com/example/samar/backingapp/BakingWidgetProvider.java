package com.example.samar.backingapp;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

/**
 * Created by Samar on 20/06/2018.
 */

public class BakingWidgetProvider  extends AppWidgetProvider {


    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {

        for (int i = 0; i < appWidgetIds.length; ++i) {



            // Set up the intent that starts the ListViewService, which will

            // provide the views for this collection.
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget);
            Intent intent = new Intent(context, ListWidgetService.class);
            intent.putExtra("first",true);
            views.setRemoteAdapter(appWidgetIds[i],R.id.widget_grid_view, intent);

            Intent appIntent = new Intent(context,DetailsActivty.class);
            PendingIntent appPendingIntent = PendingIntent.getActivity(context, 0, appIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            views.setPendingIntentTemplate(R.id.widget_grid_view, appPendingIntent);
            appWidgetManager.updateAppWidget(appWidgetIds[i], views);


        }

        super.onUpdate(context, appWidgetManager, appWidgetIds);

    }
    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}