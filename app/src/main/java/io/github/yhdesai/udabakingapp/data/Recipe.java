package io.github.yhdesai.udabakingapp.data;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.io.Serializable;
import java.util.List;

import com.google.gson.annotations.SerializedName;

/*public class Recipe extends RecyclerView.ViewHolder {*/
public class Recipe /*extends RecyclerView.ViewHolder*/ implements Serializable {
    @SerializedName("image")
    private String image;
    @SerializedName("servings")
    private String servings;
    @SerializedName("name")
    private String name;
    @SerializedName("ingredients")
   /* private List<IngredientsItem> ingredients;*/
   private String ingredients;
    @SerializedName("id")
    private int id;
    @SerializedName("steps")
   /* private List<StepsItem> steps;*/
    private String steps;

    public Recipe() {
    }

   /*public Recipe(View itemView) {
        super(itemView);
    }*/

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String  getServings() {
        return servings;
    }

    public void setServings(String servings) {
        this.servings = servings;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

   /* *//*public List<IngredientsItem> getIngredients() {
        return ingredients;
    }*/
    public String getIngredients() {
        return ingredients;
    }

   /* public void setIngredients(List<IngredientsItem> ingredients) {
        this.ingredients = ingredients;
    }*/

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

   /* public List<StepsItem> getSteps() {
        return steps;
    }*/
   public String getSteps() {
       return steps;
   }

    /*public void setSteps(List<StepsItem> steps) {
        this.steps = steps;
    }
*/
    public void setSteps(String steps) {
        this.steps = steps;
    }

    @Override
    public String toString() {
        return
                "Response{" +
                        "image = '" + image + '\'' +
                        ",servings = '" + servings + '\'' +
                        ",name = '" + name + '\'' +
                        ",ingredients = '" + ingredients + '\'' +
                        ",id = '" + id + '\'' +
                        ",steps = '" + steps + '\'' +
                        "}";
    }
}