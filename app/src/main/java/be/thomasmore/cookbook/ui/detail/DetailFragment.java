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
    private Button favorite;
    private DatabaseHelper db;
    private RecipeAPI recipe;
    private List<Favorite> listFavs;

    public static DetailFragment newInstance() {
        return new DetailFragment();
    }

    public DetailFragment(){}

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        detailViewModel =
                ViewModelProviders.of(this).get(DetailViewModel.class);
        root = inflater.inflate(R.layout.fragment_detail, container, false);
        final TextView textView = root.findViewById(R.id.text_send);

        favorite = root.findViewById(R.id.favBtn);
        favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addToFavorite();
            }
        });
        db = new DatabaseHelper(getActivity());
        CustomListview customListview = (CustomListview) root.findViewById(R.id.customList);
        listFavs = db.getFavorites();
        readRecipe();
        return root;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        detailViewModel = ViewModelProviders.of(this).get(DetailViewModel.class);
        // TODO: Use the ViewModel
    }

    private void readRecipe()
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
        httpReader.execute("https://www.themealdb.com/api/json/v1/1/lookup.php?i=52773");
    }

    private void addToFavorite(){
        List<Integer> favIds = new ArrayList<Integer>();
        listFavs = db.getFavorites();
        if(listFavs.size()>0){
            for (Favorite favorite: listFavs) {
                favIds.add(favorite.getRecipeId());
            }
        }else{
            db.insertFavorite(recipe.getRecipeId());
            Snackbar.make(root, "Added " + recipe.getName() + " to favorites!", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }
        Log.i("findme", favIds + "");
        if(!favIds.contains(recipe.getRecipeId())){
            db.insertFavorite(recipe.getRecipeId());
            Snackbar.make(root, "Added " + recipe.getName() + " to favorites!", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }else{
            Snackbar.make(root, "Already added", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }
    }

}
