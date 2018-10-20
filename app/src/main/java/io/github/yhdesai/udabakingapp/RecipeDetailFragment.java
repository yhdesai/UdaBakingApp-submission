package io.github.yhdesai.udabakingapp;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.fxn.stash.Stash;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

import io.github.yhdesai.udabakingapp.data.IngredientsItem;
import io.github.yhdesai.udabakingapp.data.Recipe;
import io.github.yhdesai.udabakingapp.data.StepsItem;

public class RecipeDetailFragment extends Fragment implements StepsAdapter.StepsClickListener {

    private Recipe mRecipe;
    private IngredientsItem[] ingredientsStepsArray;
    private IngredientsItem ingredientsSteps;
    private TextView ingredientsTextView;

    private StepsItem[] resultStepsArray;
    private StepsItem resultSteps;
    private StepsAdapter stepsAdapter;

    private String specialString;

    private Parcelable listState;

    String servings;

    private RecyclerView stepsRecyclerView;
    private boolean mTwoPane;


    public RecipeDetailFragment() {
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putParcelable("ListState", stepsRecyclerView.getLayoutManager().onSaveInstanceState());

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            listState = savedInstanceState.getParcelable("ListState");

        }


        DisplayMetrics metrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);


        if (getActivity().findViewById(R.id.step_detail_container) != null) {
            mTwoPane = true;
        }
        else{
            mTwoPane = false;
        }

    //    float yInches = metrics.heightPixels / metrics.ydpi;
   //     float xInches = metrics.widthPixels / metrics.xdpi;
     //   double diagonalInches = Math.sqrt(xInches * xInches + yInches * yInches);
     //   if (diagonalInches >= 6.5) {
        if(mTwoPane){
            //TAB ONLY
            mRecipe = (Recipe) Stash.getObject("recipe_to_frag_tab", Recipe.class);
            if (mRecipe != null) {
                Log.d("recipe", "got from first one");
            } else {
                Log.d("recipe", "first recipe is null");
            }
        } else {
            //ANDROID ONLY
            Bundle bundle = getArguments();
            assert bundle != null;
            mRecipe = (Recipe) bundle.getSerializable("recipeObject");

            if (mRecipe != null) {
                Log.d("recipe", "got from second one");
            } else {
                Log.d("recipe", "second recipe is null");
            }
        }


        /*mRecipe = (Recipe) bundle.getSerializable("recipeObject");*/
        Activity activity = this.getActivity();
        assert activity != null;
        if (mRecipe != null) {
            String name = mRecipe.getName();
            int id = mRecipe.getId();
            String image = mRecipe.getImage();

            servings = mRecipe.getServings();
            //   Log.d("THIIIs", servings);
            String ingredients = mRecipe.getIngredients();

            String steps = mRecipe.getSteps();

            Log.d("The Activity", activity.toString());
            if (steps != null) {
                Stash.put("steps", steps);
                new StepsFetchTask().execute(steps);
            }
            if (ingredients != null) {
                new IngredientsFetchTask().execute(ingredients);
            }


        } else {
            Log.d("RecipeDetailFragment", "mRecie is empty");
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.recipe_detail, container, false);

        // Recipe recipe = (Recipe) Stash.getObject("recipe_to_frag_tab", Recipe.class);
        Recipe recipe = mRecipe;
        if (recipe != null) {
            String name = recipe.getName();
            int id = recipe.getId();
            String image = recipe.getImage();
            String servings = recipe.getServings();
            //   Log.d("THIIIs", servings);
            String ingredients = recipe.getIngredients();

            String steps;
            steps = recipe.getSteps();


            ImageView recipe_detail = rootView.findViewById(R.id.recipe_detail_image);

            if (!image.isEmpty()) {
                Log.d("image", image);
                Picasso.get().load(image).into(recipe_detail);
            }
            TextView servingsTextView = rootView.findViewById(R.id.rv_servings);
            servingsTextView.setText(servings);


            new StepsFetchTask().execute(steps);
            new IngredientsFetchTask().execute(ingredients);
        }

        Button mhheButtonn = rootView.findViewById(R.id.meowButton);
        mhheButtonn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click

                Intent activityChangeIntent = new Intent(getActivity(), StepListActivity.class);
                startActivity(activityChangeIntent);
            }
        });


        return rootView;
    }

    @Override
    public void onClickSteps(int position) {
        String shortDescription = resultStepsArray[position].getShortDescription();
        String description = resultStepsArray[position].getDescription();
        int id = resultStepsArray[position].getId();
        String videoUrl = resultStepsArray[position].getVideoURL();
        String thumbnailUrl = resultStepsArray[position].getThumbnailURL();

        Intent intent = new Intent(getActivity(), StepsView.class);
        intent.putExtra("id", String.valueOf(id));
        intent.putExtra("description", description);
        intent.putExtra("shortDescription", shortDescription);
        intent.putExtra("videoUrl", videoUrl);
        intent.putExtra("thumbnailUrl", thumbnailUrl);
        startActivity(intent);
    }


    public class IngredientsFetchTask extends AsyncTask<String, Void, IngredientsItem[]> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            //.setVisibility(View.INVISIBLE);
        }

        @Override
        protected IngredientsItem[] doInBackground(String... strings) {
            String steps = strings[0];
            //   Log.d("initial steps", steps);


            JSONArray jsonArray;
            try {
                jsonArray = new JSONArray(steps);

                ingredientsStepsArray = new IngredientsItem[jsonArray.length()];
                //    Log.d("resultStepsArray", ingredientsStepsArray.toString());
                for (int i = 0; i < jsonArray.length(); i++) {


                    JSONObject object = jsonArray.getJSONObject(i);
                    //   Log.d("stepsjsonObject", object.toString());

                    int sQuantity = object.optInt("quantity");
                    String sMeasure = object.optString("measure");
                    String sIngredient = object.optString("ingredient");/*
                    Log.d("menow", sId + sDescription + sShortDescription + sVideoUrl + sThumbnailUrl);*/

                    ingredientsSteps = new IngredientsItem();
                    ingredientsSteps.setIngredient(sIngredient);
                    ingredientsSteps.setMeasure(sMeasure);
                    ingredientsSteps.setQuantity(sQuantity);

                    ingredientsStepsArray[i] = ingredientsSteps;

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return ingredientsStepsArray;
        }


        @Override
        protected void onPostExecute(IngredientsItem[] ingredientsItems) {
            new IngredientsFetchTask().cancel(true);

            if (ingredientsItems != null) {
                for (IngredientsItem ingredientsItem : ingredientsItems) {

                    ingredientsTextView = Objects.requireNonNull(getView()).findViewById(R.id.rv_ingredients);
                    String formerText = ingredientsTextView.getText().toString();
                    String quantity = String.valueOf(ingredientsItem.getQuantity());
                    String measure = ingredientsItem.getMeasure();
                    String ingredient = ingredientsItem.getIngredient();
                    ingredientsTextView.setText(formerText + "\n" + quantity + " " + measure + " " + ingredient);


                }
            } else {
                Log.e("tag", "onPostExecute recipes is empty");
            }
        }


    }

    public class StepsFetchTask extends AsyncTask<String, Void, StepsItem[]> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            //  mStepsRecyclerView.setVisibility(View.INVISIBLE);
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
                    // Log.d("stepsjsonObject", object.toString());

                    int sId = object.optInt("id");
                    String sDescription = object.optString("description");
                    String sShortDescription = object.optString("shortDescription");
                    String sVideoUrl = object.optString("videoURL");
                    String sThumbnailUrl = object.optString("thumbnailURL");
                    //Log.d("menow", sId + sDescription + sShortDescription + sVideoUrl + sThumbnailUrl);

                    resultSteps = new StepsItem();
                    resultSteps.setDescription(sDescription);
                    resultSteps.setId(sId);
                    resultSteps.setShortDescription(sShortDescription);
                    if (sVideoUrl != null) {
                        resultSteps.setThumbnailURL(sThumbnailUrl);
                    }
                    resultSteps.setVideoURL(sVideoUrl);

                    // Log.d("resultsteps", resultSteps.toString());
                    resultStepsArray[i] = resultSteps;

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return resultStepsArray;
        }


        @Override
        protected void onPostExecute(StepsItem[] stepsItems) {
            new StepsFetchTask().cancel(true);
            // Log.d("mewow", stepsItems.toString());
            if (stepsItems != null) {


                stepsRecyclerView = Objects.requireNonNull(getActivity()).findViewById(R.id.rv_steps);
                stepsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


                stepsAdapter = new StepsAdapter(stepsItems, RecipeDetailFragment.this);
                stepsRecyclerView.setAdapter(stepsAdapter);

                TextView servingsTextView = getActivity().findViewById(R.id.rv_servings);
                servingsTextView.setText(servings);
            } else {
                Log.e("tag", "onPostExecute recipes is empty");
            }
        }

    }


}
