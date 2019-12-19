package be.thomasmore.cookbook.ui.recipe;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import be.thomasmore.cookbook.R;
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
        final TextView textCat = (TextView) rowView.findViewById(R.id.category);
        final ImageView mealImage = (ImageView) rowView.findViewById(R.id.meal_image);

        textName.setText(values.get(position).getName());
        textCat.setText(values.get(position).getCategory());
        mealImage.setImageURI(Uri.parse(values.get(position).getPicture()));

        return rowView;
    }
}
