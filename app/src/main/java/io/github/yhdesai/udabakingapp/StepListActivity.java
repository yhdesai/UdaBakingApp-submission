package io.github.yhdesai.udabakingapp;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fxn.stash.Stash;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;

import io.github.yhdesai.udabakingapp.data.Recipe;
import io.github.yhdesai.udabakingapp.utils.GetHTTPResponse;

/**
 * An activity representing a list of Recipes. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link RecipeDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class StepListActivity extends AppCompatActivity {

    Recipe[] result12;
    Recipe recipe234;
    RecipeAdapters recipeAdapter;
    /**
     * -----------------------
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_list);
        Stash.init(this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());


        if (findViewById(R.id.recipe_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;
        }

      /*  View recyclerView = findViewById(R.id.recipe_list);
        assert recyclerView != null;
        setupRecyclerView((RecyclerView) recyclerView);*/
        String uri = "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json";
        new RecipeFetchTask().execute(uri);
    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        recyclerView.setAdapter(new RecipeAdapters(this, result12, mTwoPane));
    }

    public class RecipeAdapters
            extends RecyclerView.Adapter<RecipeAdapters.ViewHolder> {

        private final StepListActivity mParentActivity;
        private final boolean mTwoPane;
        private final View.OnClickListener mOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Recipe item = (Recipe) view.getTag();
                if (mTwoPane) {
                    // Bundle arguments = new Bundle();
                    // arguments.putString(RecipeDetailFragment.ARG_ITEM_ID, item);
                    //TODO Fix this code here,,  fixed it but gonna leave this todo here as a mark for future
                    Stash.put("recipe_to_frag_tab", item);
                    RecipeDetailFragment fragment = new RecipeDetailFragment();
                    //  fragment.setArguments(arguments);
                    mParentActivity.getSupportFragmentManager().beginTransaction()
                            .replace(R.id.recipe_detail_container, fragment)
                            .commit();
                } else {
                    Context context = view.getContext();

                    Recipe recipe = (Recipe) view.getTag();
                    Intent intent = new Intent(context, RecipeDetailActivity.class);

                    intent.putExtra("MyClass", recipe);
                    context.startActivity(intent);
                }
            }
        };
        private Recipe[] mRecipe;


        RecipeAdapters(StepListActivity parent,
                      Recipe[] recipe,
                      boolean twoPane) {
            mRecipe = recipe;
            mParentActivity = parent;
            mTwoPane = twoPane;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.recipe_list_content, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
            //  holder.mIdView.setText(mValues.get(position).id);
            Recipe recipe1234 = mRecipe[position];

            String text = recipe1234.getName();
            holder.mContentView.setText(text);

//            holder.mContentView.setText(mRecipe.get(position).content);

            holder.itemView.setTag(mRecipe[position]);
            holder.itemView.setOnClickListener(mOnClickListener);
        }

        @Override
        public int getItemCount() {
            return mRecipe.length;
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            // final TextView mIdView;
            final TextView mContentView;

            ViewHolder(View view) {
                super(view);
                //mIdView = (TextView) view.findViewById(R.id.id_text);
                mContentView = view.findViewById(R.id.item_recipe_title);
            }
        }
    }

    public class RecipeFetchTask extends AsyncTask<String, Void, Recipe[]> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            // mRecipeRecyclerView.setVisibility(View.INVISIBLE);
        }

        @Override
        protected Recipe[] doInBackground(String... strings) {
            String uri = strings[0];
            URL url = GetHTTPResponse.parseUrl(uri);

            try {
                String result = GetHTTPResponse.getResponseFromHttpVideo(url);
                JSONArray jsonArray = new JSONArray(result);
                result12 = new Recipe[jsonArray.length()];
                for (int i = 0; i < jsonArray.length(); i++) {

                    JSONObject object = jsonArray.getJSONObject(i);

                    int id = object.optInt("id");
                    String name = object.optString("name");
                    String image = object.optString("image");
                    String ingredients = object.getString("ingredients");
                    int servings = object.getInt("servings");
                    String steps = object.getString("steps");

                 /*   Log.d("RESULTED IN",
                            String.valueOf(id) + "\n " +
                                    name + "\n " +
                                    image + "\n " +
                                    ingredients + "\n " +
                                    String.valueOf(servings) + "\n " +
                                    steps);*/

                    recipe234 = new Recipe();

                    recipe234.setName(name);
                    recipe234.setImage(image);
                    recipe234.setId(id);
                    recipe234.setIngredients(ingredients);
                    recipe234.setServings(String.valueOf(servings));
                    recipe234.setSteps(steps);


                    result12[i] = recipe234;

                }
            } catch (JSONException | IOException e1) {
                e1.printStackTrace();
            }


            return result12;
        }

        @Override
        protected void onPostExecute(Recipe[] recipes) {
            new RecipeFetchTask().cancel(true);
            if (recipes != null) {

                View recyclerView = findViewById(R.id.recipe_list);
                assert recyclerView != null;
                setupRecyclerView((RecyclerView) recyclerView);


                /*  Log.d("onposteecute", recipes.toString());*/

                //  mRecipeRecyclerView.setVisibility(View.VISIBLE);

             /*   recipeAdapter = new RecipeAdapter(recipes, MainActivity.this, MainActivity.this);
                mRecipeRecyclerView.setAdapter(recipeAdapter);
*/

                //   Log.d("recipeAdapter", recipeAdapter.toString());
            } else {
                Log.e("tag", "onPostExecute recipes is empty");
            }
        }


    }

}
