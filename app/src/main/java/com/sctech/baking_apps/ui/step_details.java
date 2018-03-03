package com.sctech.baking_apps.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.sctech.baking_apps.R;
import com.sctech.baking_apps.recipes.steps;

public class step_details extends AppCompatActivity {
    private int mStepIndex;
    private int mTotalSteps = 0;
    private TextView mTextLongStepView;
    private String TAG = step_details.class.getSimpleName();
    private final String MY_STEP_INDEX = "step_index";
    private Button mNextButton;
    private Button mPrevButton;
    private VideoFragment mFragment;
    public static Context mContext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.step_details);
        if (savedInstanceState != null) { //the apps has rotated views
            mStepIndex = savedInstanceState.getInt(MY_STEP_INDEX);
        } else {
            mStepIndex = getIntent().getIntExtra("step_id", 0);
        }
        mTotalSteps = MainActivity.mCurrentRecipe.mSteps.size();
        Log.v(TAG, "total steps = " + mTotalSteps);
        steps step = MainActivity.mCurrentRecipe.mSteps.get(mStepIndex);
        this.setTitle(MainActivity.mCurrentRecipe.getName());
        mTextLongStepView = findViewById(R.id.stepLongText);
        mTextLongStepView.setText("Step " + mStepIndex + "\n" + step.getmLongDescription());

        // Create a new VideoFragment
        mFragment = new VideoFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        Bundle args = new Bundle();
        args.putInt(VideoFragment.RECIPE_STEPS_POS, mStepIndex);
        args.putBoolean(VideoFragment.SHOW_TWO_PANE, false);
        args.putString(VideoFragment.CALLING_ACTIVITY, "stepDetails");
        mContext = getApplicationContext();
        mFragment.setArguments(args);

        if (savedInstanceState == null) {
            fragmentManager.beginTransaction()
                    .add(R.id.video_container, mFragment)
                    .commit();
        }else {
            fragmentManager.beginTransaction()
                    .replace(R.id.video_container, mFragment)
                    .commit();
        }

        mNextButton = findViewById(R.id.next_button);
        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mStepIndex < (mTotalSteps - 1)) {
                    mStepIndex++;
                    Log.v(TAG, "mTotalSteps = " + mTotalSteps + "mStepIndex++ = " + mStepIndex);
                    recreate();

                }
            }
        });

        mPrevButton = findViewById(R.id.prev_button);
        mPrevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mStepIndex > 0) {
                    mStepIndex--;
                    Log.v(TAG, "mStepIndex -- = " + mStepIndex);
                    recreate();
                }
            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(MY_STEP_INDEX, mStepIndex);
        Log.v(TAG, "Save stepIndex" + mStepIndex);

        super.onSaveInstanceState(outState);
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
}

