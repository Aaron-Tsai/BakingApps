package com.sctech.baking_apps.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.sctech.baking_apps.R;
import com.sctech.baking_apps.recipes.ingredient;
import com.sctech.baking_apps.recipes.recipes;
import com.sctech.baking_apps.recipes.steps;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements MasterListFragment.OnImageClickListener {
    public final static String url_recipe = "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json";
    public static ArrayList<recipes> mRecipeList;
    public static recipes mCurrentRecipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getRecipeFromNetwork(url_recipe, this);
    }

    private void getRecipeFromNetwork(String url, Context context) {

        RequestQueue queue = Volley.newRequestQueue(this);
        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        mRecipeList = extractRecipeList(response);
                        setContentView(R.layout.activity_main);

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

    public static ArrayList<recipes> extractRecipeList(String response) {
        ArrayList<recipes> recipeList = new ArrayList<recipes>();

        if (TextUtils.isEmpty(response)) {return null;}
        try {

            JSONArray jsonRecipes = new JSONArray(response); //get recipe array list

            for (int i = 0; i < jsonRecipes.length(); i++) {
                JSONObject rcpIndex = jsonRecipes.getJSONObject(i); //get our recipe object
                recipes recipe = new recipes(rcpIndex.getInt("id"),rcpIndex.getString("name"));
                JSONArray jIng = rcpIndex.getJSONArray("ingredients"); //get ingredients
                for (int j=0; j<jIng.length(); j++) {
                    JSONObject jIngObj = jIng.getJSONObject(j);
                    ingredient ing = new ingredient(jIngObj.getInt("quantity"), jIngObj.getString("measure"), jIngObj.getString("ingredient"));
                    recipe.mIngredients.add(ing);
                }

                JSONArray jStep = rcpIndex.getJSONArray("steps"); //get ingredients
                for (int j=0; j<jStep.length(); j++) {
                    JSONObject jStepObj = jStep.getJSONObject(j);
                    steps step = new steps(jStepObj.getInt("id"), jStepObj.getString("shortDescription"), jStepObj.getString("description"), jStepObj.getString("videoURL"));
                    recipe.mSteps.add(step);
                }
                recipeList.add(recipe);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return recipeList;
    }

    // Define the behavior for onImageSelected
    public void onImageSelected(int position) {
        Bundle b = new Bundle();
        b.putInt("recipe_id", position);
        final Intent intent = new Intent(this, RecipeDetailed.class);
        intent.putExtras(b);
        startActivity(intent);
    }
}