package be.thomasmore.cookbook.ui.recipe;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import java.util.List;

import be.thomasmore.cookbook.R;
import be.thomasmore.cookbook.ui.helpers.HttpReader;
import be.thomasmore.cookbook.ui.helpers.JsonHelper;
import be.thomasmore.cookbook.ui.models.Category;
import be.thomasmore.cookbook.ui.models.RecipeAPI;

public class RecipeFragment extends Fragment {

    private Spinner spinner;
    private Button categoryButton;
    private EditText editText;
    private Button nameButton;
    private ListView listViewRecipes;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_recipe, container, false);
        spinner = root.findViewById(R.id.categoryspinner);
        editText = root.findViewById(R.id.name);
        categoryButton = root.findViewById(R.id.search_by_category);
        nameButton = root.findViewById(R.id.search_by_name);
        listViewRecipes = root.findViewById(R.id.listViewPlatforms);

        categoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String categoryTekst = spinner.getSelectedItem().toString();

                getRecipesByCategory(categoryTekst);
            }
        });

        nameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nameTekst = editText.getText().toString();

                getRecipesByName(nameTekst);
            }
        });

        readCategories();
        getAllRecipes();
        return root;
    }

    private void readCategories()
    {

        HttpReader httpReader = new HttpReader();
        httpReader.setOnResultReadyListener(new HttpReader.OnResultReadyListener() {
            @Override
            public void resultReady(String result) {
                JsonHelper jsonHelper = new JsonHelper();
                List<Category> categories = jsonHelper.getCategories(result);

                ArrayAdapter<Category> arrayAdapter = new ArrayAdapter<Category>(getContext(), android.R.layout.simple_spinner_item, categories);

                spinner.setAdapter(arrayAdapter);
            }
        });

        httpReader.execute("https://www.themealdb.com/api/json/v1/1/list.php?c=list");
    }

    private void getAllRecipes()
    {
        HttpReader httpReader = new HttpReader();
        httpReader.setOnResultReadyListener(new HttpReader.OnResultReadyListener() {
            @Override
            public void resultReady(String result) {
                JsonHelper jsonHelper = new JsonHelper();
                List<RecipeAPI> allRecipes = jsonHelper.getAllRecipes(result);

                PlatformAdapter recipeAdapter = new PlatformAdapter(getContext(), allRecipes);

                listViewRecipes.setAdapter(recipeAdapter);
            }
        });

        httpReader.execute("https://www.themealdb.com/api/json/v1/1/search.php?s=%");
    }

    private void getRecipesByCategory(String categoryName)
    {
        HttpReader httpReader = new HttpReader();
        httpReader.setOnResultReadyListener(new HttpReader.OnResultReadyListener() {
            @Override
            public void resultReady(String result) {
                JsonHelper jsonHelper = new JsonHelper();
                List<RecipeAPI> recipesByCategory = jsonHelper.getRecipesByCategory(result);

                PlatformAdapter recipeAdapter = new PlatformAdapter(getContext(), recipesByCategory);

                listViewRecipes.setAdapter(recipeAdapter);
            }
        });

        httpReader.execute("https://www.themealdb.com/api/json/v1/1/filter.php?c=" + categoryName);
    }

    private void getRecipesByName(String name)
    {
        HttpReader httpReader = new HttpReader();
        httpReader.setOnResultReadyListener(new HttpReader.OnResultReadyListener() {
            @Override
            public void resultReady(String result) {
                JsonHelper jsonHelper = new JsonHelper();
                List<RecipeAPI> recipesByName = jsonHelper.getRecipesByName(result);

                PlatformAdapter recipeAdapter = new PlatformAdapter(getContext(), recipesByName);

                listViewRecipes.setAdapter(recipeAdapter);
            }
        });

        httpReader.execute("https://www.themealdb.com/api/json/v1/1/search.php?s=%" + name + "%");
    }
}