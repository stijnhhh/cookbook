package be.thomasmore.cookbook.ui.favorite;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import java.util.ArrayList;
import java.util.List;

import be.thomasmore.cookbook.R;
import be.thomasmore.cookbook.ui.detail.CustomListview;
import be.thomasmore.cookbook.ui.detail.DetailActivity;
import be.thomasmore.cookbook.ui.detail.DetailFragment;
import be.thomasmore.cookbook.ui.helpers.DatabaseHelper;
import be.thomasmore.cookbook.ui.helpers.HttpReader;
import be.thomasmore.cookbook.ui.helpers.JsonHelper;
import be.thomasmore.cookbook.ui.models.Favorite;
import be.thomasmore.cookbook.ui.models.RecipeAPI;

public class FavoriteFragment extends Fragment {

    private FavoriteViewModel favoriteViewModel;
    private View root;
    private DatabaseHelper db;
    private List<Favorite> listFavs;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        favoriteViewModel =
                ViewModelProviders.of(this).get(FavoriteViewModel.class);
        root = inflater.inflate(R.layout.fragment_favorite, container, false);
        final TextView textView = root.findViewById(R.id.text_home);
        CustomListview customListview = (CustomListview) root.findViewById(R.id.customListFavorites);
        db = new DatabaseHelper(getActivity());
        listFavs = db.getFavorites();

        getFavorites();

        return root;
    }

    public void getFavorites(){
        final List<RecipeAPI>recipes = new ArrayList<RecipeAPI>();

        for (Favorite favorite : listFavs) {
            HttpReader httpReader = new HttpReader();
            httpReader.setOnResultReadyListener(new HttpReader.OnResultReadyListener() {
                @Override
                public void resultReady(String result) {
                    JsonHelper jsonHelper = new JsonHelper();
                    RecipeAPI recipe = jsonHelper.getRecipe(result);
                    List<String> ingredients = recipe.getIngredients();
                    List<String> measurements = recipe.getMeasurements();
                    recipes.add(recipe);
                    if(listFavs.size() == recipes.size()){
                        Log.i("findme", "" + listFavs.size());
                        Log.i("findme", "" + recipes.size());
                        fillListView(recipes);
                    }
                }
            });
            httpReader.execute("https://www.themealdb.com/api/json/v1/1/lookup.php?i=" + favorite.getRecipeId());
        }
    }

    public void fillListView(List<RecipeAPI> recipes){
        final ArrayAdapter<RecipeAPI> adapter = new ArrayAdapter<RecipeAPI>(getContext(),
                android.R.layout.simple_list_item_1, recipes);


        final ListView listViewFavs = (ListView) root.findViewById(R.id.customListFavorites);
        listViewFavs.setAdapter(adapter);
        listViewFavs.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parentView,
                                    View childView, int position, long id) {
                onClick(listFavs.get(position).getRecipeId());
            }
        });
    }

    public void onClick(int recipeId){
        Fragment fragment = new DetailFragment();
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.nav_detail, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}