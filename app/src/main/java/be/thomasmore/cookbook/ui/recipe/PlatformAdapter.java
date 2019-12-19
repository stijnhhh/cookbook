package be.thomasmore.cookbook.ui.recipe;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import java.util.List;

import be.thomasmore.cookbook.R;
import be.thomasmore.cookbook.ui.detail.DetailFragment;
import be.thomasmore.cookbook.ui.helpers.DatabaseHelper;
import be.thomasmore.cookbook.ui.helpers.DownloadImageTask;
import be.thomasmore.cookbook.ui.models.Favorite;
import be.thomasmore.cookbook.ui.models.RecipeAPI;

public class PlatformAdapter extends ArrayAdapter<RecipeAPI> {
    private final Context context;
    private final List<RecipeAPI> values;

    public PlatformAdapter(Context context, List<RecipeAPI> values) {
        super(context, R.layout.platformlistviewitem, values);
        this.context = context;
        this.values = values;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(R.layout.platformlistviewitem, parent, false);

        final TextView textName = (TextView) rowView.findViewById(R.id.name);
        final ImageView mealImage = (ImageView) rowView.findViewById(R.id.meal_image);
        final Button addToFavoriteButton = (Button) rowView.findViewById(R.id.go_to_details);

        textName.setText(values.get(position).getName());

        new DownloadImageTask(mealImage).execute(values.get(position).getPicture());
        mealImage.getLayoutParams().width = 200;

        addToFavoriteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int recipeID =  values.get(position).getRecipeId();
                DetailFragment fragment = new DetailFragment();
                Bundle args = new Bundle();
                args.putInt("id", recipeID);
                fragment.setArguments(args);
                ((FragmentActivity) getContext()).getFragmentManager().beginTransaction()
                        .replace(R.id.nav_host_fragment, fragment)
                        .commit();


            }
        });

        return rowView;
    }

    public void toon(String tekst)
    {
        Toast.makeText(getContext(),tekst ,Toast.LENGTH_SHORT).show();
    }
}
