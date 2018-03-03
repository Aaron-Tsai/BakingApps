package com.sctech.baking_apps.ui;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.sctech.baking_apps.R;
import com.sctech.baking_apps.recipes.recipes;
import java.util.List;

public class MasterListAdapter extends BaseAdapter {
    // Keeps track of the context and list of images to display
    private Context mContext;
    private List<recipes> mRecipeList;

    public MasterListAdapter(Context context, List<recipes> recipeList) {
        mContext = context;
        mRecipeList = recipeList;
    }

    @Override
    public int getCount() {
        if (mRecipeList == null) {
            return 0;
        }
        else {
            return mRecipeList.size();
        }
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        TextView textView;
        if (convertView == null) {
            // If the view is not recycled, this creates a new ImageView to hold an image
            textView = new TextView(mContext);
            // Define the layout parameters
            textView.setTextColor(ContextCompat.getColor(mContext, R.color.colorFront));
            textView.setBackground(ContextCompat.getDrawable(mContext, R.drawable.button_shape));
            textView.setTextSize(20);
            //textView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            textView.setPadding(8, 8, 8, 8);
        } else {
            textView = (TextView) convertView;
        }

        // Set the image resource and return the newly created ImageView
        textView.setText(mRecipeList.get(position).getName());
        return textView;
    }
}
