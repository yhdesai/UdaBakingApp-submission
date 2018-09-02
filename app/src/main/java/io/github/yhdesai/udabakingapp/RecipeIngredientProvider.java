package io.github.yhdesai.udabakingapp;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;

import com.fxn.stash.Stash;

/**
 * Implementation of App Widget functionality.
 */
public class RecipeIngredientProvider extends AppWidgetProvider {
    private String ingreTitle;
    private String ingreString;

     void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {


        CharSequence widgetText = context.getString(R.string.appwidget_text);
        // CharSequence widgetText = utils.getIngredient();
        // Construct the RemoteViews object
         RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.recipe_ingredient_provider);

         ingreTitle = Stash.getString("INGRE_TITLE");
         ingreString = Stash.getString("INGRE_CONTENT");
         views.setTextViewText(R.id.appwidget_title, ingreTitle);
         views.setTextViewText(R.id.appwidget_ingredients, ingreString);
       //  Log.d("updateAppWidgetIngre", ingre);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent.getLongExtra(WidgetUpdateService.USER_ID_EXTRA, -1) != -1) {
          //  userId = intent.getLongExtra(WidgetUpdateService.USER_ID_EXTRA, -1);
        //    ingreTitle =    Stash.getString("TAG_DATA_STRING");
          //  Log.d("onReceiveIngre", "test" + ingre + " test");
        }
        super.onReceive(context, intent);
    }


    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevanKt functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

