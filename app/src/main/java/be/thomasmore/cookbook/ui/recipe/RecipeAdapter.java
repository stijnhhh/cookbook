package be.thomasmore.cookbook.ui.recipe;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;

import java.util.List;

import be.thomasmore.cookbook.R;
import be.thomasmore.cookbook.ui.detail.DetailFragment;
import be.thomasmore.cookbook.ui.helpers.DownloadImageTask;
import be.thomasmore.cookbook.ui.models.RecipeAPI;

public class RecipeAdapter extends ArrayAdapter<RecipeAPI> {
    private final Context context;
    private final List<RecipeAPI> values;

    public RecipeAdapter(Context context, List<RecipeAPI> values) {
        super(context, R.layout.recipelistviewitem, values);
        this.context = context;
        this.values = values;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(R.layout.recipelistviewitem, parent, false);

        final TextView textName = (TextView) rowView.findViewById(R.id.name);
        final ImageView mealImage = (ImageView) rowView.findViewById(R.id.meal_image);
        final Button addToFavoriteButton = (Button) rowView.findViewById(R.id.go_to_details);

        String tekstName;
        if (values.get(position).getName().length() > 15)
        {
            tekstName = values.get(position).getName().substring(0, 15) + "...";
        } else
        {
            tekstName = values.get(position).getName();
        }
        textName.setText(tekstName);

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
}
