package io.github.yhdesai.udabakingapp;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;

import com.fxn.stash.Stash;

public class WidgetUpdateService extends IntentService {

    public static final String ACTION_UPDATE_INGREDIENT_WIDGET = "io.github.yhdesai.udabakingapp.action.update_ingredient_widget";
    public static final String USER_ID_EXTRA = "1";


    public WidgetUpdateService() {
        super("WidgetUpdateService");
    }


    public static void startActionUpdateIngredientWidget(Context context) {
       /* Intent intent = new Intent(context, WidgetUpdateService.class);
        intent.setAction(ACTION_UPDATE_INGREDIENT_WIDGET);
        context.startService(intent);*/
        Intent intent = new Intent(context, RecipeIngredientProvider.class);
        intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        String ingre = Stash.getString("TAG_DATA_STRING");
        intent.putExtra(WidgetUpdateService.USER_ID_EXTRA, ingre);
        int[] ids = AppWidgetManager.getInstance(context).getAppWidgetIds(new ComponentName(context, RecipeIngredientProvider.class));
        if (ids != null && ids.length > 0) {
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, ids);
            context.sendBroadcast(intent);

        }
    }


    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();

            if (ACTION_UPDATE_INGREDIENT_WIDGET.equals(action)) {
                handleActionUpdateIngredientWidget();
            }
        }

    }

    private void handleActionUpdateIngredientWidget() {
    }
}
