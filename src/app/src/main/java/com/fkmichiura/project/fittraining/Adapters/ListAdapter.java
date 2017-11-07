package com.fkmichiura.project.fittraining.Adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.fkmichiura.project.fittraining.R;

import java.util.ArrayList;

public class ListAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Integer> icons;
    private ArrayList<String> titles;

    public ListAdapter(Context context, ArrayList<Integer> icons, ArrayList<String> titles) {
        this.context = context;
        this.icons = icons;
        this.titles = titles;
    }

    @Override
    public int getCount() {
        return titles.size();
    }

    @Override
    public Object getItem(int position) {
        return titles.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = View.inflate(context, R.layout.list_row, null);
        }

        ImageView image = (ImageView)convertView.findViewById(R.id.row_icon);
        TextView title = (TextView)convertView.findViewById(R.id.tv_title);

        image.setImageResource(icons.get(position));
        title.setText(titles.get(position));

        return convertView;
    }

}
