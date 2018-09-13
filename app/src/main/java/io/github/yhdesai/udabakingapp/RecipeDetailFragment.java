package io.github.yhdesai.udabakingapp;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

/**
 * A fragment representing a single Recipe detail screen.
 * This fragment is either contained in a {@link RecipeListActivity}
 * in two-pane mode (on tablets) or a {@link RecipeDetailActivity}
 * on handsets.
 */
public class RecipeDetailFragment extends Fragment implements StepsAdapter.StepsClickListener {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    // public static final String ARG_ITEM_ID = "id";

    /**
     * The dummy content this fragment is presenting.
     */
    private Recipe mRecipe;
    private IngredientsItem[] ingredientsStepsArray;
    private IngredientsItem ingredientsSteps;
    private TextView ingredientsTextView;

    private StepsItem[] resultStepsArray;
    private StepsItem resultSteps;
    private StepsAdapter stepsAdapter;

    private String specialString;

    private Parcelable listState;

    private RecyclerView stepsRecyclerView;


    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public RecipeDetailFragment() {
    }

   /* private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        recyclerView.setAdapter(new StepsAdapter(this, resultStepsArray, null));
    }*/

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putParcelable("ListState", stepsRecyclerView.getLayoutManager().onSaveInstanceState());

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        listState = savedInstanceState.getParcelable("ListState");

        //  if (getArguments().containsKey(ARG_ITEM_ID)) {
        // Load the dummy content specified by the fragment
        // arguments. In a real-world scenario, use a Loader
        // to load content from a content provider.
        // mRecipe = Recipe.ITEM_MAP.get(getArguments().getString(ARG_ITEM_ID));
        Bundle bundle = getArguments();
        assert bundle != null;
        mRecipe = (Recipe) bundle.getSerializable("recipeObject");
        Activity activity = this.getActivity();
        assert activity != null;
        CollapsingToolbarLayout appBarLayout = activity.findViewById(R.id.toolbar_layout);
        if (appBarLayout != null) {
            appBarLayout.setTitle(mRecipe.getName());
        }
        String name = mRecipe.getName();
        int id = mRecipe.getId();
        String image = mRecipe.getImage();
        String servings = mRecipe.getServings();
        //   Log.d("THIIIs", servings);
        String ingredients = mRecipe.getIngredients();

        String steps = mRecipe.getSteps();


        ImageView recipe_detail = getActivity().findViewById(R.id.recipe_detail_image);
        Picasso.get().load(image).into(recipe_detail);

        //    TextView nameTextView = activity.findViewById(R.id.);

        // TextView ingredientsTextView = activity.findViewById(R.id.rv_ingredients);
        TextView servingsTextView = getActivity().findViewById(R.id.rv_servings);
        servingsTextView.setText(servings);


        new StepsFetchTask().execute(steps);
        new IngredientsFetchTask().execute(ingredients);
        //   Stash.put("TAG_DATA_STRING", ingredientsTextView.getText().toString());
        // WidgetUpdateService.startActionUpdateIngredientWidget(getContext());


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.recipe_detail, container, false);

        Recipe recipe = (Recipe) Stash.getObject("recipe_to_frag_tab", Recipe.class);

        if (recipe != null) {
            String name = recipe.getName();
            int id = recipe.getId();
            String image = recipe.getImage();
            String servings = recipe.getServings();
            //   Log.d("THIIIs", servings);
            String ingredients = recipe.getIngredients();

            String steps = recipe.getSteps();


            // Show the dummy content as text in a TextView.
       /* if (mItem != null) {
            ((TextView) rootView.findViewById(R.id.recipe_detail)).setText(mItem.details);
        }

*/ //((TextView) rootView.findViewById(R.id.)).setText(mRecipe.getName());


            ImageView recipe_detail = rootView.findViewById(R.id.recipe_detail_image);
            Picasso.get().load(image).into(recipe_detail);

            //    TextView nameTextView = activity.findViewById(R.id.);

            // TextView ingredientsTextView = activity.findViewById(R.id.rv_ingredients);
            TextView servingsTextView = rootView.findViewById(R.id.rv_servings);
            servingsTextView.setText(servings);


            new StepsFetchTask().execute(steps);
            new IngredientsFetchTask().execute(ingredients);

        }

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


   /* private void setupStepsRecyclerView(@NonNull RecyclerView recyclerView, StepsItem[] stepsItems) {
        recyclerView.setAdapter(new StepsAdapter(RecipeDetailFragment.this, stepsItems));

    }
*/

    /*public class StepsAdapter
            extends RecyclerView.Adapter<StepsAdapter.StepsHolder> {

        private final RecipeDetailFragment mParentActivity;
        private StepsItem[] mSteps;
        private final View.OnClickListener mOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //  StepsItem steps = (StepsItem) view.getTag();

                //   Context context = view.getContext();

                // Intent intent = new Intent(context, RecipeDetailActivity.class);
                // intent.putExtra("MySteps", steps);
                //   context.startActivity(intent);

            }
        };

        StepsAdapter(RecipeDetailFragment parent,
                     StepsItem[] steps
        ) {
            Log.d("step 2 from stepadapter", steps[1].toString());
            mSteps = steps;
            mParentActivity = parent;
        }


        @Override
        public StepsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_steps, parent, false);
            return new StepsHolder(view);
        }


        @Override
        public void onBindViewHolder(final StepsHolder holder, int position) {
            StepsItem stepsItem = mSteps[position];
            Log.d("input onbindstepHolder", mSteps[position].toString().toString());

            holder.mId.setText(stepsItem.getId());
            holder.mShortDescription.setText(stepsItem.getDescription());
            holder.mDescription.setText(stepsItem.getDescription());
            holder.mThumbnailUrl.setText(stepsItem.getThumbnailURL());
            holder.mVideoUrl.setText(stepsItem.getVideoURL());

            holder.itemView.setTag(mSteps[position]);
            holder.itemView.setOnClickListener(mOnClickListener);


        }

        @Override
        public int getItemCount() {
            return mSteps.length;
        }

        class StepsHolder extends RecyclerView.ViewHolder {
            final TextView mId;
            final TextView mShortDescription;
            final TextView mDescription;
            final TextView mVideoUrl;
            final TextView mThumbnailUrl;


            StepsHolder(View view) {
                super(view);
                mId = view.findViewById(R.id.stepsId);
                mShortDescription = view.findViewById(R.id.stepsShortDescription);
                mDescription = view.findViewById(R.id.stepsDescription);
                mVideoUrl = view.findViewById(R.id.stepsVideoUrl);
                mThumbnailUrl = view.findViewById(R.id.stepsThumbnailUrl);
            }
        }
    }*/


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


                    /*  Log.d("onposteecute", recipes.toString());*/

                    /* mStepsRecyclerView.setVisibility(View.VISIBLE);*/

               /* stepsAdapter = new StepsAdapter(stepsItems, RecipeView.this, RecipeView.this);
                mStepsRecyclerView.setAdapter(stepsAdapter);*/

               /* ingredientsAdapter = new IngredientsAdapter(ingredientsItemss, MainActivity.this, MainActivity.this);
                mRecipeRecyclerView.setAdapter(recipeAdapter);*/
                    //  Log.d("recipeAdapter", stepsAdapter.toString());
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
               /* for (int i = 0; i < stepsItems.length; i++) {
                    Log.d("going to stepsAdapter", stepsItems[i].getDescription());
                }*/


                stepsRecyclerView = Objects.requireNonNull(getActivity()).findViewById(R.id.rv_steps);

                stepsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


                stepsAdapter = new StepsAdapter(stepsItems, RecipeDetailFragment.this);
                stepsRecyclerView.setAdapter(stepsAdapter);

               /* ingredientsAdapter = new IngredientsAdapter(ingredientsItemss, MainActivity.this, MainActivity.this);
                mRecipeRecyclerView.setAdapter(recipeAdapter);*/
                // Log.d("recipeAdapter", stepsAdapter.toString());
            } else {
                Log.e("tag", "onPostExecute recipes is empty");
            }
        }


    }
}
