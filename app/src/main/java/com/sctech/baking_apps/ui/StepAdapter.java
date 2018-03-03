package com.sctech.baking_apps.ui;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.sctech.baking_apps.R;
import com.sctech.baking_apps.recipes.steps;
import java.util.ArrayList;

public class StepAdapter extends RecyclerView.Adapter<StepAdapter.StepViewHolder>  {

    private ArrayList<steps> mSteps;

    private StepAdapterOnClickHandler mClickHandler;

    public StepAdapter(StepAdapterOnClickHandler clickHandler) {
        mClickHandler = clickHandler;
    }

    class StepViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView stepDesc;

        public StepViewHolder(View itemView) {
            super(itemView);
            stepDesc = itemView.findViewById(R.id.step_desc);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int adapterPosition = getAdapterPosition();
            mClickHandler.onListItemClick(mSteps.get(adapterPosition), adapterPosition);
        }
    }

    @Override
    public StepViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.step_list_item, parent, false);
        return new StepViewHolder(view);
    }

    /**
     * onBindViewHolder extracts a movie poster's url_recipe based on the adapter position and displays the poster.
     */
    @Override
    public void onBindViewHolder(StepViewHolder holder, int position) {
        steps stepItem = mSteps.get(position);
        String strStep = " Step " + position + ":" + stepItem.getShortDescription();
        holder.stepDesc.setText(strStep);
    }

    @Override
    public int getItemCount() {
        if (null == mSteps) return 0;
        return mSteps.size();
    }

    /**
     * setMovieList updates the adapter with the data retrieved from the server, which it uses to populate the Main Activity's
     * recycler view.
     */
    public void setStepsList(ArrayList<steps> steps) {
        mSteps = steps;
        notifyDataSetChanged();
    }

    /**
     * This interface is implemented by the Main Activity. The interface is informed by the viewholder's onClick method, which provides
     * it with the appropriate adapter position when an item is clicked.
     */
    public interface StepAdapterOnClickHandler {
        void onListItemClick(steps stepItem, int position);
    }
}

