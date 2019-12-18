package be.thomasmore.cookbook.ui.detail;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import be.thomasmore.cookbook.R;
import be.thomasmore.cookbook.ui.helpers.DatabaseHelper;
import be.thomasmore.cookbook.ui.helpers.HttpReader;
import be.thomasmore.cookbook.ui.helpers.JsonHelper;
import be.thomasmore.cookbook.ui.models.Favorite;
import be.thomasmore.cookbook.ui.models.Recipe;
import be.thomasmore.cookbook.ui.models.RecipeAPI;
import be.thomasmore.cookbook.ui.models.RecipeIngredient;

public class DetailCustomRecipe extends Fragment {

    private DetailCustomRecipeViewModel mViewModel;
    private View root;
    private DatabaseHelper db;
    private Recipe recipe;
    private List<Favorite> listFavs;
    private int recipeId;
    private Button favorite;

    public static DetailCustomRecipe newInstance() {
        return new DetailCustomRecipe();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel =
                ViewModelProviders.of(this).get(DetailCustomRecipeViewModel.class);
        root = inflater.inflate(R.layout.detail_custom_recipe_fragment, container, false);
        db = new DatabaseHelper(getActivity());
        Bundle args = getArguments();
        recipeId = args.getInt("id", 0);
        readRecipe(recipeId);
        return root;
    }
    private void readRecipe(int recipeId)
    {
        recipe = db.getRecipe(recipeId);
        TextView recipeName = (TextView) root.findViewById(R.id.recipe_name);
        recipeName.setText(recipe.getName());
        TextView recipeInstructions = (TextView) root.findViewById(R.id.recipe_instructions);
        recipeInstructions.setText(recipe.getInstructions());

        List<RecipeIngredient> recipeIngredients = db.getRecipeIngredients(recipeId);

        List<String> ingredientList = new ArrayList<String>();
        for(RecipeIngredient recipeIngredient: recipeIngredients){
            ingredientList.add(recipeIngredient.getMeasurement() + " " + db.getIngredient(recipeIngredient.getIngredientId()));
        }

        ArrayAdapter<String> adapterIngredients = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_list_item_1, ingredientList);

        final ListView listViewIngredients = (ListView) root.findViewById(R.id.customList);
        listViewIngredients.setAdapter(adapterIngredients);
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(DetailCustomRecipeViewModel.class);
        // TODO: Use the ViewModel
    }
}
