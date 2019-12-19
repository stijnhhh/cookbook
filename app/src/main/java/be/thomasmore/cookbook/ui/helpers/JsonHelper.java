package be.thomasmore.cookbook.ui.helpers;

import android.util.Log;
import be.thomasmore.cookbook.ui.models.Ingredient;
import be.thomasmore.cookbook.ui.models.Recipe;
import be.thomasmore.cookbook.ui.models.RecipeAPI;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import be.thomasmore.cookbook.ui.models.Category;

public class JsonHelper {

    public List<Category> getCategories(String jsonTekst) {
        List<Category> lijst = new ArrayList<Category>();

        try {

            JSONObject jsonObjectCategory = new JSONObject(jsonTekst);
            JSONArray jsonArrayCategories = jsonObjectCategory.getJSONArray("meals");
            
            for (int i = 0; i < jsonArrayCategories.length(); i++) {
                JSONObject jsonObjectCategory2 = jsonArrayCategories.getJSONObject(i);

                Category category = new Category();
                category.setCategoryId(i + 1);
                category.setName(jsonObjectCategory2.getString("strCategory"));
                lijst.add(category);
            }
        } catch (JSONException e) {
            Log.e("JSON Parser", "Error parsing data " + e.toString());
        }

        return lijst;
    }

    public List<RecipeAPI> getAllRecipes(String jsonTekst) {
        List<RecipeAPI> lijst = new ArrayList<RecipeAPI>();

        try {
            JSONObject eersteJsonObject = new JSONObject(jsonTekst);
            JSONArray mealsArray = eersteJsonObject.getJSONArray("meals");

            for (int i = 0; i < mealsArray.length(); i++) {
                JSONObject recipeObject = mealsArray.getJSONObject(i);

                ArrayList<String> ingredients = new ArrayList<String>();
                ArrayList<String> measurements = new ArrayList<String>();

                for (int j = 1; j < 21; j++) {
                    if (recipeObject.getString("strIngredient" + j) != "") {
                        ingredients.add(recipeObject.getString("strIngredient" + j));
                        measurements.add(recipeObject.getString("strMeasure" + j));
                    } else
                    {
                        j = 21;
                    }
                }

                RecipeAPI recipeAPI = new RecipeAPI();
                recipeAPI.setCategory(recipeObject.getString("strCategory"));
                recipeAPI.setIngredients(ingredients);
                recipeAPI.setInstructions(recipeObject.getString("strInstructions"));
                recipeAPI.setMeasurements(measurements);
                recipeAPI.setName(recipeObject.getString("strMeal"));
                recipeAPI.setPicture(recipeObject.getString("strMealThumb"));
                recipeAPI.setRecipeId(Integer.parseInt(recipeObject.getString("idMeal")));

                lijst.add(recipeAPI);
            }
        } catch (JSONException e){
            Log.e("JSON Parser", "Error parsing data " + e.toString());
        }

        return lijst;
    }

    public List<RecipeAPI> getRecipesByCategory(String jsonTekst) {
        List<RecipeAPI> lijst = new ArrayList<RecipeAPI>();

        try {
            JSONObject eersteJsonObject = new JSONObject(jsonTekst);
            JSONArray mealsArray = eersteJsonObject.getJSONArray("meals");

            for (int i = 0; i < mealsArray.length(); i++) {
                JSONObject recipeObject = mealsArray.getJSONObject(i);

                RecipeAPI recipeAPI = new RecipeAPI();
                recipeAPI.setName(recipeObject.getString("strMeal"));
                recipeAPI.setPicture(recipeObject.getString("strMealThumb"));
                recipeAPI.setRecipeId(Integer.parseInt(recipeObject.getString("idMeal")));

                lijst.add(recipeAPI);
            }
        } catch (JSONException e){
            Log.e("JSON Parser", "Error parsing data " + e.toString());
        }

        return lijst;
    }

    public List<RecipeAPI> getRecipesByName(String jsonTekst) {
        List<RecipeAPI> lijst = new ArrayList<RecipeAPI>();

        try {
            JSONObject eersteJsonObject = new JSONObject(jsonTekst);
            JSONArray mealsArray = eersteJsonObject.getJSONArray("meals");

            for (int i = 0; i < mealsArray.length(); i++) {
                JSONObject recipeObject = mealsArray.getJSONObject(i);

                RecipeAPI recipeAPI = new RecipeAPI();
                recipeAPI.setName(recipeObject.getString("strMeal"));
                recipeAPI.setPicture(recipeObject.getString("strMealThumb"));
                recipeAPI.setRecipeId(Integer.parseInt(recipeObject.getString("idMeal")));

                lijst.add(recipeAPI);
            }
        } catch (JSONException e){
            Log.e("JSON Parser", "Error parsing data " + e.toString());
        }

        return lijst;
    }

    public RecipeAPI getRecipe(String jsonTekst) {
        RecipeAPI recipe = new RecipeAPI();

        try {
            JSONObject jsonObjectStudent = new JSONObject(jsonTekst);
            recipe.setRecipeId(jsonObjectStudent.getJSONArray("meals").getJSONObject(0).getInt("idMeal"));
            recipe.setName(jsonObjectStudent.getJSONArray("meals").getJSONObject(0).getString("strMeal"));
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
