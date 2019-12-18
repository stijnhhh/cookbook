package be.thomasmore.cookbook.ui.helpers;

import android.util.Log;

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
}

