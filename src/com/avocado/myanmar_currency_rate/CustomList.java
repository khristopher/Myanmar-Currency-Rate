package com.avocado.myanmar_currency_rate;

/**
 * Created by kyawmyintcho on 6/27/14.
 */
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomList<Currency> extends ArrayAdapter<Currency>{
    private final Activity context;
    private final ArrayList<Currency> web;
    private final Integer[] imageId;
    public CustomList(Activity context,
                      ArrayList<Currency> web, Integer[] imageId) {
        super(context, R.layout.list_single, web);
        this.context = context;
        this.web = web;
        this.imageId = imageId;
    }
    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView= inflater.inflate(R.layout.list_single, null, true);
        TextView txtTitle = (TextView) rowView.findViewById(R.id.txt);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.img);
        txtTitle.setText(web.get(position).toString());
        imageView.setImageResource(imageId[position]);
        return rowView;
    }
}
