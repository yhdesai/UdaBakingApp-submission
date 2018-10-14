package io.github.yhdesai.udabakingapp;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;
import io.github.yhdesai.udabakingapp.data.Recipe;

public class StepDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_detail);
        Toolbar toolbar = findViewById(R.id.detail_toolbar);
        setSupportActionBar(toolbar);


        // Show the Up button in the action bar.
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }


        if (savedInstanceState == null) {


            Object object = getIntent().getSerializableExtra("MyClass");
            Recipe recipe = (Recipe) object;
            Bundle stepBundle = new Bundle();
            stepBundle.putSerializable("stepObject", recipe);
          /*  arguments.putString("recipe",
                    getIntent().getStringExtra(RecipeDetailFragment.ARG_ITEM_ID));*/
            StepDetailFragment fragment = new StepDetailFragment();
            fragment.setArguments(stepBundle);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.step_detail_container, fragment)
                    .commit();
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            navigateUpTo(new Intent(this, StepListActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


}
