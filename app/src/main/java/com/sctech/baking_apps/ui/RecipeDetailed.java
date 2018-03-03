package com.sctech.baking_apps.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import com.sctech.baking_apps.R;
import com.sctech.baking_apps.recipes.ingredient;
import com.sctech.baking_apps.recipes.recipes;
import com.sctech.baking_apps.recipes.steps;

public class RecipeDetailed  extends AppCompatActivity implements StepAdapter.StepAdapterOnClickHandler {

    private StepAdapter mStepAdapter;
    private static GridLayoutManager layoutManager;
    public static Context mContext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe_detailed);

        int index = getIntent().getIntExtra("recipe_id", 0);
        recipes recipe = MainActivity.mRecipeList.get(index);
        MainActivity.mCurrentRecipe = recipe;
        TextView ingView = findViewById(R.id.ingredientTextView);
        this.setTitle(recipe.getName());
        String strIng = "Ingredients: \n";
        for (int i = 0; i < recipe.mIngredients.size(); i++) {
            ingredient ings = recipe.mIngredients.get(i);
            strIng += ings.getQty() + " " + ings.getMes() + " of " + ings.getName() + "  \n";
        }
        ingView.setText(strIng);

        RecyclerView stepRecyclerView = findViewById(R.id.rv_steps);
        layoutManager = new GridLayoutManager(this, 1, GridLayoutManager.VERTICAL, false);
        stepRecyclerView.setLayoutManager(layoutManager);
        stepRecyclerView.setHasFixedSize(true);
        mStepAdapter = new StepAdapter(this);
        stepRecyclerView.setAdapter(mStepAdapter);
        mStepAdapter.setStepsList(recipe.mSteps);

        Log.v("DetailedRecipe", "Name = " + recipe.getName() + "# ingredients = " + recipe.mIngredients.size());
        if (findViewById(R.id.recipe_detailed_tablet_view) != null) {
            Recipe_tablet_detailed_view(recipe.mSteps.get(0), 0, true);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.menu_back:
                finish();
                break;

            case R.id.menu_home:
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void Recipe_tablet_detailed_view(steps stepItem, int position, boolean firstTime) {
        TextView TextLongStepView = findViewById(R.id.stepLongText);
        TextLongStepView.setText("Step "+ position + "\n" + stepItem.getmLongDescription());
        // Create a new VideoFragment
        mContext = getApplicationContext();
        // Create a new VideoFragment
        VideoFragment mFragment = new VideoFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        Bundle args = new Bundle();
        args.putInt(VideoFragment.RECIPE_STEPS_POS, position);
        args.putBoolean(VideoFragment.SHOW_TWO_PANE, true);
        args.putString(VideoFragment.CALLING_ACTIVITY, "RecipeDetails");
        mContext = getApplicationContext();
        mFragment.setArguments(args);

        if (firstTime) {
            fragmentManager.beginTransaction()
                    .add(R.id.video_container, mFragment)
                    .commit();
        }else {
            fragmentManager.beginTransaction()
                    .replace(R.id.video_container, mFragment)
                    .commit();
        }
    }

    public void onListItemClick(steps stepItem, int position) {
        if (findViewById(R.id.recipe_detailed_tablet_view) == null) {
            Bundle b = new Bundle();
            b.putInt("step_id", position);
            // Attach the Bundle to an intent
            final Intent intent = new Intent(this, step_details.class);
            intent.putExtras(b);
            startActivity(intent);
        } else {
            Recipe_tablet_detailed_view(stepItem, position, false);
        }
        Log.v ("RcpDetailed", "Got Step " + stepItem.getShortDescription());
    }
}
