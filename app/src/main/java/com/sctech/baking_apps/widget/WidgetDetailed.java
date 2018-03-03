package com.sctech.baking_apps.widget;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.sctech.baking_apps.R;


public class WidgetDetailed extends AppCompatActivity {

    public static final String EXTRA_RECIPE_ID = "com.sctech.baking_apps.extra.RECIPE_ID";
    public static final String EXTRA_RECIPE_NAME = "com.sctech.baking_apps.extra.RECIPE_NAME";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.widget_detailed);

        Bundle bundle = getIntent().getExtras();
        String recipeName = bundle.getString(EXTRA_RECIPE_NAME);
        String ingredient = bundle.getString(EXTRA_RECIPE_ID);
        TextView ingView = findViewById(R.id.text_ingredient);
        this.setTitle(recipeName);
        ingView.setText(ingredient);

    }
}
