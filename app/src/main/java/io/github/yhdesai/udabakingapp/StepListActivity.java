package io.github.yhdesai.udabakingapp;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fxn.stash.Stash;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import io.github.yhdesai.udabakingapp.data.Recipe;
import io.github.yhdesai.udabakingapp.data.StepsItem;


public class StepListActivity extends AppCompatActivity implements StepsAdapter.StepsClickListener {

    Recipe[] result12s;
    private StepsItem[] resultStepsArray;
    private StepsItem resultSteps;
    private StepsAdapter stepsAdapter;
    String servings;
    private RecyclerView stepsRecyclerView;
    private boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_list);
        Stash.init(this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());


        if (findViewById(R.id.step_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;
        }


        String stepss = Stash.getString("steps");
        new StepsFetchTasks().execute(stepss);
    }

  /*  private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        recyclerView.setAdapter(new RecipeAdapters(this, result12s, mTwoPane));
    }*/

    @Override
    public void onClickSteps(int position) {
        String shortDescription = resultStepsArray[position].getShortDescription();
        String description = resultStepsArray[position].getDescription();
        int id = resultStepsArray[position].getId();
        String videoUrl = resultStepsArray[position].getVideoURL();
        String thumbnailUrl = resultStepsArray[position].getThumbnailURL();

      /*  if (findViewById(R.id.step_detail_container != null)) {
            Log.d("test", "step detail container present");
        }
*/
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);

        float yInches = metrics.heightPixels / metrics.ydpi;
        float xInches = metrics.widthPixels / metrics.xdpi;
        double diagonalInches = Math.sqrt(xInches * xInches + yInches * yInches);
        if (diagonalInches >= 6.5) {


            Log.d("mTwoPane", "steps mTwoPane Detected");


            Stash.put("shortDescription", shortDescription);
            Stash.put("description", description);
            Stash.put("id", String.valueOf(id));
            Stash.put("videoUrl", videoUrl);
            Stash.put("thumbnailUrl", thumbnailUrl);
            StepDetailFragment fragment = new StepDetailFragment();
            getSupportFragmentManager().beginTransaction()
                    .replace(step_detail_container, fragment)
                    .commit();


        } else {
            Intent intent = new Intent(StepListActivity.this, StepDetailActivity.class);
            intent.putExtra("id", String.valueOf(id));
            intent.putExtra("description", description);
            intent.putExtra("shortDescription", shortDescription);
            intent.putExtra("videoUrl", videoUrl);
            intent.putExtra("thumbnailUrl", thumbnailUrl);
            startActivity(intent);

        }
    }

    public class StepsFetchTasks extends AsyncTask<String, Void, StepsItem[]> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected StepsItem[] doInBackground(String... strings) {
            String steps = strings[0];
            JSONArray jsonArray;
            try {
                jsonArray = new JSONArray(steps);
                resultStepsArray = new StepsItem[jsonArray.length()];
                //Log.d("resultStepsArray", resultStepsArray.toString());
                for (int i = 0; i < jsonArray.length(); i++) {

                    JSONObject object = jsonArray.getJSONObject(i);

                    int sId = object.optInt("id");
                    String sDescription = object.optString("description");
                    String sShortDescription = object.optString("shortDescription");
                    String sVideoUrl = object.optString("videoURL");
                    String sThumbnailUrl = object.optString("thumbnailURL");
                    resultSteps = new StepsItem();
                    resultSteps.setDescription(sDescription);
                    resultSteps.setId(sId);
                    resultSteps.setShortDescription(sShortDescription);
                    if (sVideoUrl != null) {
                        resultSteps.setThumbnailURL(sThumbnailUrl);
                    }
                    resultSteps.setVideoURL(sVideoUrl);
                    resultStepsArray[i] = resultSteps;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return resultStepsArray;
        }

        @Override
        protected void onPostExecute(StepsItem[] stepsItems) {
            new StepsFetchTasks().cancel(true);
            if (stepsItems != null) {
                stepsRecyclerView = findViewById(R.id.rv_stepsss);
                stepsRecyclerView.setLayoutManager(new LinearLayoutManager(StepListActivity.this));
                stepsAdapter = new StepsAdapter(stepsItems, StepListActivity.this);
                stepsRecyclerView.setAdapter(stepsAdapter);
            } else {
                Log.e("tag", "onPostExecute recipes is empty");
            }
        }


    }

}
