package io.github.yhdesai.udabakingapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import com.fxn.stash.Stash;

import io.github.yhdesai.udabakingapp.data.Recipe;

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


        if (savedInstanceState == null) {

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


        FloatingActionButton fab = findViewById(R.id.fab);
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
        } else {
            Log.d("id", String.valueOf(id));
        }
        return super.onOptionsItemSelected(item);
    }
   /*  public void steps(View view) {
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);

        float yInches= metrics.heightPixels/metrics.ydpi;
        float xInches= metrics.widthPixels/metrics.xdpi;
        double diagonalInches = Math.sqrt(xInches*xInches + yInches*yInches);
        if (diagonalInches>=6.5){
            startActivity(new Intent(RecipeDetailActivity.this, StepDetailActivity.class));
        }else{
            Log.d("Weird", "this is weird");

        }
    }*/

}
