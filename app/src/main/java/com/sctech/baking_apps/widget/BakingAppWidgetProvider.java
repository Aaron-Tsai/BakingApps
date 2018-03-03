package com.sctech.baking_apps.widget;

import android.annotation.TargetApi;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.sctech.baking_apps.R;
import com.sctech.baking_apps.recipes.recipes;

import java.util.ArrayList;

import static com.sctech.baking_apps.ui.MainActivity.extractRecipeList;
import static com.sctech.baking_apps.ui.MainActivity.url_recipe;

/**
 * Implementation of App Widget functionality.
 */
public class BakingAppWidgetProvider extends AppWidgetProvider {

    private static int[] mAppWidgetIds;
    private static AppWidgetManager mAppWidgetManager;
    public static ArrayList<recipes> mRecipeList;
    private Context mContext;

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {
        // Get current width to decide on single plant vs garden grid view
        Bundle options = appWidgetManager.getAppWidgetOptions(appWidgetId);
        int width = options.getInt(AppWidgetManager.OPTION_APPWIDGET_MIN_WIDTH);
        RemoteViews rv;

        rv = getRecipeGridRemoteView(context);
        appWidgetManager.updateAppWidget(appWidgetId, rv);

    }

    /**
     * Updates all widget instances given the widget Ids and display information
     *
     * @param context          The calling context
     * @param appWidgetManager The widget manager
     * @param appWidgetIds     Array of widget Ids to be updated
     */
    public static void updateRecipeWidgets(Context context, AppWidgetManager appWidgetManager,
                                           int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        //Start the intent service update widget action, the service takes care of updating the widgets UI
        mAppWidgetIds = appWidgetIds;
        mAppWidgetManager = appWidgetManager;
        mContext = context;

        if (mRecipeList == null) {
            getRecipeFromNetwork(url_recipe, context);
        }


   //     BakingWidgetService.startActionUpdateWidgets(context);
        Log.v ("Baking", "Widget onUpdate");
    }

    private void updateRecipe() {
             //Trigger data update to handle the GridView widgets and force a data refresh
        if ((mAppWidgetManager != null) && (mAppWidgetIds != null)) {
            mAppWidgetManager.notifyAppWidgetViewDataChanged(mAppWidgetIds, R.id.widget_grid_view);
            updateRecipeWidgets(mContext, mAppWidgetManager, mAppWidgetIds);

            Log.v("Baking_widget", "update recipe");
        }

        else {
            Log.v ("Baking_widget", "appWidgetManager" + mAppWidgetManager + mAppWidgetIds);
        }
    }

    private void getRecipeFromNetwork(String url, Context context) {

        RequestQueue queue = Volley.newRequestQueue(context);
        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        mRecipeList = extractRecipeList(response);
                        updateRecipe();

                    }
                }, new Response.ErrorListener() {
            @Override

            public void onErrorResponse(VolleyError error) {
                Log.e("BakingApps", "That didn't work!" + error.getMessage());
            }
        });
        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }

      /**
     * Creates and returns the RemoteViews to be displayed in the GridView mode widget
     *
     * @param context The context
     * @return The RemoteViews for the GridView mode widget
     */
    private static RemoteViews getRecipeGridRemoteView(Context context) {
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.baking_app_widget);
        // Set the GridWidgetService intent to act as the adapter for the GridView
        Intent intent = new Intent(context, BakingWidgetService.class);
        views.setRemoteAdapter(R.id.widget_grid_view, intent);
        // Set the PlantDetailActivity intent to launch when clicked
        Intent appIntent = new Intent(context, WidgetDetailed.class);
        PendingIntent appPendingIntent = PendingIntent.getActivity(context, 0, appIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        views.setPendingIntentTemplate(R.id.widget_grid_view, appPendingIntent);
        // Handle empty gardens
        views.setEmptyView(R.id.widget_grid_view, R.id.empty_view);
        return views;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onAppWidgetOptionsChanged(Context context, AppWidgetManager appWidgetManager,
                                          int appWidgetId, Bundle newOptions) {
     //   BakingWidgetService.startActionUpdateWidgets(context);
        super.onAppWidgetOptionsChanged(context, appWidgetManager, appWidgetId, newOptions);
    }

    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        // Perform any action when one or more AppWidget instances have been deleted
    }

    @Override
    public void onEnabled(Context context) {
        // Perform any action when an AppWidget for this provider is instantiated
    }

    @Override
    public void onDisabled(Context context) {
        // Perform any action when the last AppWidget instance for this provider is deleted
    }

}

