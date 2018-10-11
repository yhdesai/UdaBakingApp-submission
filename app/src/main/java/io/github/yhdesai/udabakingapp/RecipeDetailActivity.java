package io.github.yhdesai.udabakingapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;
import android.widget.TextView;

import com.fxn.stash.Stash;

import io.github.yhdesai.udabakingapp.data.Recipe;
import io.github.yhdesai.udabakingapp.utils.utils;

/**
 * An activity representing a single Recipe detail screen. This
 * activity is only used on narrow width devices. On tablet-size devices,
 * item details are presented side-by-side with a list of items
 * in a {@link RecipeListActivity}.
 */
public class RecipeDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.detail_toolbar);
        setSupportActionBar(toolbar);


        // Show the Up button in the action bar.
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        // savedInstanceState is non-null when there is fragment state
        // saved from previous configurations of this activity
        // (e.g. when rotating the screen from portrait to landscape).
        // In this case, the fragment will automatically be re-added
        // to its container so we don't need to manually add it.
        // For more information, see the Fragments API guide at:
        //
        // http://developer.android.com/guide/components/fragments.html
        //
        if (savedInstanceState == null) {
            // Create the detail fragment and add it to the activity
            // using a fragment transaction.

            Object object = getIntent().getSerializableExtra("MyClass");
            Recipe recipe = (Recipe) object;
           Bundle recipeBundle = new Bundle();
           recipeBundle.putSerializable("recipeObject", recipe);
          /*  arguments.putString("recipe",
                    getIntent().getStringExtra(RecipeDetailFragment.ARG_ITEM_ID));*/
            RecipeDetailFragment fragment = new RecipeDetailFragment();
            fragment.setArguments(recipeBundle);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.recipe_detail_container, fragment)
                    .commit();
        }

     FloatingActionButton fab =findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //getting title

                CollapsingToolbarLayout appBarLayout = findViewById(R.id.toolbar_layout);
               // if (appBarLayout != null) {
                    String strings = appBarLayout.getTitle().toString();




                TextView textView = findViewById(R.id.rv_ingredients);
                String ingre = textView.getText().toString();
                Stash.put("INGRE_TITLE", strings);
                Stash.put("INGRE_CONTENT", ingre);

                WidgetUpdateService.startActionUpdateIngredientWidget(RecipeDetailActivity.this);
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            // This ID represents the Home or Up button. In the case of this
            // activity, the Up button is shown. For
            // more details, see the Navigation pattern on Android Design:
            //
            // http://developer.android.com/design/patterns/navigation.html#up-vs-back
            //
            navigateUpTo(new Intent(this, RecipeListActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void steps(View view) {
      startActivity(new Intent(RecipeDetailActivity.this, StepListActivity.class));
    }
}
