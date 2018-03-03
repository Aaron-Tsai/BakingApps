package com.sctech.baking_apps.widget;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.sctech.baking_apps.R;
import com.sctech.baking_apps.recipes.ingredient;
import com.sctech.baking_apps.recipes.recipes;

import java.util.ArrayList;

import static com.sctech.baking_apps.widget.BakingAppWidgetProvider.mRecipeList;


 public class BakingWidgetService extends RemoteViewsService {

    public static final String ACTION_UPDATE_WIDGETS = "com.sctech.baking_apps.action.update_widgets";

        @Override
        public RemoteViewsFactory onGetViewFactory(Intent intent) {
            return new GridRemoteViewsFactory(this.getApplicationContext());
        }


    /**
     * Starts this service to perform BakingWidgetWidgets action with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * /
    public static void startActionUpdateWidgets(Context context) {
        Intent intent = new Intent(context, BakingWidgetService.class);
        intent.setAction(ACTION_UPDATE_WIDGETS);
        context.startService(intent);
    }
    */

}

 class GridRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

        Context mContext;
       private ArrayList<recipes> recipeList;

        public GridRemoteViewsFactory(Context applicationContext) {
            mContext = applicationContext;

        }

        @Override
        public void onCreate() {

        }

        //called on start and when notifyAppWidgetViewDataChanged is called
        @Override
        public void onDataSetChanged() {
            recipeList = BakingAppWidgetProvider.mRecipeList;
        }

        @Override
        public void onDestroy() {

        }

        @Override
        public int getCount() {
            Log.v ("Baking", "recipeList" + recipeList);
            if (recipeList == null) return 0;
            return recipeList.size();
        }

        /**
         * This method acts like the onBindViewHolder method in an Adapter
         *
         * @param position The current position of the item in the GridView to be displayed
         * @return The RemoteViews object to display for the provided postion
         */
        @Override
        public RemoteViews getViewAt(int position) {
            Log.v ("Baking View", "recipeList" + mRecipeList);
            if (mRecipeList == null || mRecipeList.size() == 0) return null;
            recipes recipe = mRecipeList.get(position);

            RemoteViews views = new RemoteViews(mContext.getPackageName(), R.layout.baking_recipe);

            views.setTextViewText(R.id.recipe_name, recipe.getName());

            // Fill in the onClick PendingIntent Template using the specific plant Id for each item individually
            Bundle extras = new Bundle();
            extras.putString(WidgetDetailed.EXTRA_RECIPE_NAME, recipe.getName());
            String strIng = "Ingredients: \n\n";
            for (int i=0; i<recipe.mIngredients.size(); i++) {
                ingredient ings = recipe.mIngredients.get(i);
                strIng += ings.getQty() + " " + ings.getMes() + " of " + ings.getName() + "  \n";
            }
            extras.putString(WidgetDetailed.EXTRA_RECIPE_ID, strIng);
            Intent fillInIntent = new Intent();
            fillInIntent.putExtras(extras);
            views.setOnClickFillInIntent(R.id.recipe_name, fillInIntent);

            return views;
        }

        @Override
        public RemoteViews getLoadingView() {
            return null;
        }

        @Override
        public int getViewTypeCount() {
            return 1; // Treat all items in the GridView the same
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