package be.thomasmore.cookbook.ui.helpers;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import be.thomasmore.cookbook.ui.models.Ingredient;
import be.thomasmore.cookbook.ui.models.Recipe;
import be.thomasmore.cookbook.ui.models.RecipeAPI;

public class JsonHelper {
    public RecipeAPI getRecipe(String jsonTekst) {
        RecipeAPI recipe = new RecipeAPI();

        Log.i("findme", jsonTekst + "in json helper");

        try {
            JSONObject jsonObjectStudent = new JSONObject(jsonTekst);
            recipe.setRecipeId(jsonObjectStudent.getJSONArray("meals").getJSONObject(0).getInt("idMeal"));
            recipe.setName(jsonObjectStudent.getJSONArray("meals").getJSONObject(0).getString("strMeal"));
            Log.i("findme", recipe.getName());
            recipe.setInstructions(jsonObjectStudent.getJSONArray("meals").getJSONObject(0).getString("strInstructions"));
            recipe.setPicture(jsonObjectStudent.getJSONArray("meals").getJSONObject(0).getString("strMealThumb"));
            recipe.setCategory(jsonObjectStudent.getJSONArray("meals").getJSONObject(0).getString("strCategory"));
            int teller = 1;
            List<String> ingredients = new ArrayList<String>();
            while(teller <= 20){
                String ingredient = jsonObjectStudent.getJSONArray("meals").getJSONObject(0).getString("strIngredient" + teller);
                if(!ingredient.isEmpty()){
                    ingredients.add(ingredient);
                }
                teller++;
            }
            recipe.setIngredients(ingredients);
            teller = 1;
            List<String> measurements = new ArrayList<String>();
            while(teller <= 20){
                String measure = jsonObjectStudent.getJSONArray("meals").getJSONObject(0).getString("strMeasure" + teller);
                if(!measure.isEmpty()){
                    measurements.add(measure);
                }
                teller++;
            }
            recipe.setMeasurements(measurements);

        } catch (JSONException e) {
            Log.e("JSON Parser", "Error parsing data " + e.toString());
        }

        return recipe;
    }

}
