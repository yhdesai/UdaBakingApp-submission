package io.github.yhdesai.udabakingapp.data;

import android.os.Parcel;
import android.os.Parcelable;
import android.view.View;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class IngredientsItem  {

  /*  public static final Creator<IngredientsItem> CREATOR = new Creator<IngredientsItem>() {
        @Override
        public IngredientsItem createFromParcel(Parcel in) {
            return new IngredientsItem(in);
        }

        @Override
        public IngredientsItem[] newArray(int size) {
            return new IngredientsItem[size];
        }
    };*/
    @SerializedName("quantity")
    private int quantity;
    @SerializedName("measure")
    private String measure;
    @SerializedName("ingredient")
    private String ingredient;

   /* protected IngredientsItem(Parcel in) {
        quantity = in.readInt();
        measure = in.readString();
        ingredient = in.readString();
    }
*/



    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getMeasure() {
        return measure;
    }

    public void setMeasure(String measure) {
        this.measure = measure;
    }

    public String getIngredient() {
        return ingredient;
    }

    public void setIngredient(String ingredient) {
        this.ingredient = ingredient;
    }

    @Override
    public String toString() {
        return
                "IngredientsItem{" +
                        "quantity = '" + quantity + '\'' +
                        ",measure = '" + measure + '\'' +
                        ",ingredient = '" + ingredient + '\'' +
                        "}";
    }


 /*   @Override
    public int describeContents() {
        return 0;
    }

    *//**
     * Flatten this object in to a Parcel.
     *
     * @param dest  The Parcel in which the object should be written.
     * @param flags Additional flags about how the object should be written.
     *              May be 0 or {@link #PARCELABLE_WRITE_RETURN_VALUE}.
     */
   /* @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(String.valueOf(quantity));
        dest.writeString(measure);
        dest.writeString(ingredient);

    }*/
}