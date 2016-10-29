package hackathon_mobile_2016.randomio.activities;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import java.util.List;

import hackathon_mobile_2016.randomio.R;

/**
 * Created by Flynn on 10/29/2016.
 */

public class Unknown extends ArrayAdapter<Integer> implements SpinnerAdapter {

    public Unknown(Context context, int resource, List<Integer> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        int color = getItem(position);
//        Log.i("any view: ", String.valueOf(color));
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.spinner_color, parent, false);
        }
        convertView.setBackgroundColor(color);
        ((TextView) convertView).setText("");
        // Return the completed view to render on screen
        return convertView;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        super.getDropDownView(position, convertView, parent);

        int color = getItem(position);
//        Log.i("any dropdown view: ", String.valueOf(color));
        View rowView = convertView;

        if (rowView == null) {
            rowView = convertView = LayoutInflater.from(getContext()).inflate(R.layout.spinner_color, parent, false);
        }
        rowView.setBackgroundColor(color);
        ((TextView) rowView).setText("");

        return rowView;
    }
}
