package be.thomasmore.cookbook.ui.detail;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.app.Fragment;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
//import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import be.thomasmore.cookbook.R;
import be.thomasmore.cookbook.ui.addRecipe.AddRecipeViewModel;
import be.thomasmore.cookbook.ui.helpers.DatabaseHelper;
import be.thomasmore.cookbook.ui.helpers.HttpReader;
import be.thomasmore.cookbook.ui.helpers.JsonHelper;
import be.thomasmore.cookbook.ui.models.Favorite;
import be.thomasmore.cookbook.ui.models.Recipe;
import be.thomasmore.cookbook.ui.models.RecipeAPI;

public class DetailFragment extends Fragment {

    private DetailViewModel detailViewModel;
    private View root;
    private Button removeFromFavoriteButton;
    private Button addToFavoriteButton;
    private DatabaseHelper db;
    private RecipeAPI recipe;
    private List<Favorite> listFavs;
    private int recipeId;

    List<Integer> favIds = new ArrayList<Integer>();

    public static DetailFragment newInstance() {
        return new DetailFragment();
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        if (container != null) {
            container.removeAllViews();
        }
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Details");

        root = inflater.inflate(R.layout.fragment_detail, container, false);
        Bundle args = getArguments();
        recipeId = args.getInt("id", 0);
        removeFromFavoriteButton = root.findViewById(R.id.remove_from_favorite);
        addToFavoriteButton = root.findViewById(R.id.add_to_favorite);

        db = new DatabaseHelper(getActivity());

        if (db.getFavorite(recipeId).size() == 0)
        {
            addToFavoriteButton.setVisibility(View.VISIBLE);
            removeFromFavoriteButton.setVisibility(View.INVISIBLE);
        } else
        {
            removeFromFavoriteButton.setVisibility(View.VISIBLE);
            addToFavoriteButton.setVisibility(View.INVISIBLE);
        }

        removeFromFavoriteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.deleteFavorite(recipeId);
                addToFavoriteButton.setVisibility(View.VISIBLE);
                removeFromFavoriteButton.setVisibility(View.INVISIBLE);

                Snackbar.make(root, "Removed " + recipe.getName() + " from favorites!", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        addToFavoriteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addToFavorite();
                addToFavoriteButton.setVisibility(View.INVISIBLE);
                removeFromFavoriteButton.setVisibility(View.VISIBLE);
            }
        });


        db = new DatabaseHelper(getActivity());
        CustomListview customListview = (CustomListview) root.findViewById(R.id.customList);
        listFavs = db.getFavorites();
        for (Favorite favorite: listFavs) {
            favIds.add(favorite.getRecipeId());
        }
        readRecipe(recipeId);
        return root;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    private void readRecipe(int recipeId)
    {
        HttpReader httpReader = new HttpReader();
        httpReader.setOnResultReadyListener(new HttpReader.OnResultReadyListener() {
            @Override
            public void resultReady(String result) {
                JsonHelper jsonHelper = new JsonHelper();

                recipe = jsonHelper.getRecipe(result);
                TextView recipeName = (TextView) root.findViewById(R.id.recipe_name);
                recipeName.setText(recipe.getName());
                TextView recipeInstructions = (TextView) root.findViewById(R.id.recipe_instructions);
                recipeInstructions.setText(recipe.getInstructions());

                List<String> ingredients = recipe.getIngredients();
                List<String> measurements = recipe.getMeasurements();
                List<String> ingredientList = new ArrayList<String>();

                for(String ingredient: ingredients){
                    if(!ingredient.equals("null")){
                        ingredientList.add(measurements.get(ingredients.indexOf(ingredient)) + " " + ingredient);
                    }
                }

                ArrayAdapter<String> adapterIngredients = new ArrayAdapter<String>(getContext(),
                        android.R.layout.simple_list_item_1, ingredientList);

                final ListView listViewIngredients = (ListView) root.findViewById(R.id.customList);
                listViewIngredients.setAdapter(adapterIngredients);
            }
        });
        httpReader.execute("https://www.themealdb.com/api/json/v1/1/lookup.php?i=" + recipeId);
    }

    private void addToFavorite(){
        listFavs = db.getFavorites();
        if(listFavs.size()>0){
            for (Favorite favorite: listFavs) {
                favIds.add(favorite.getRecipeId());
            }
            if(!favIds.contains(recipe.getRecipeId())){
                db.insertFavorite(recipe.getRecipeId());
                Snackbar.make(root, "Added " + recipe.getName() + " to favorites!", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }else{
                Snackbar.make(root, "Already added", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        }else{
            db.insertFavorite(recipe.getRecipeId());
            Snackbar.make(root, "Added " + recipe.getName() + " to favorites!", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }
    }

}
