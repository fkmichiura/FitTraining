package com.fkmichiura.project.fittraining.Adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.fkmichiura.project.fittraining.R;

import java.util.ArrayList;

public class ProfileListAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Integer> icons;
    private ArrayList<String> titles;
    private ArrayList<String> descriptions;
    private ArrayList<String> mails;

    public ProfileListAdapter(Context context,
                              ArrayList<Integer> icons,
                              ArrayList<String> titles,
                              ArrayList<String> descriptions,
                              ArrayList<String> mails) {
        this.context = context;
        this.icons = icons;
        this.titles = titles;
        this.descriptions = descriptions;
        this.mails = mails;
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
            convertView = View.inflate(context, R.layout.profile_row, null);
        }

        ImageView image = (ImageView)convertView.findViewById(R.id.profile_ic);
        TextView title = (TextView)convertView.findViewById(R.id.tv_name);
        TextView description = (TextView)convertView.findViewById(R.id.tv_activity);
        TextView mail = (TextView)convertView.findViewById(R.id.tv_mail);

        image.setImageResource(icons.get(position));
        title.setText(titles.get(position));
        description.setText(descriptions.get(position));
        mail.setText(mails.get(position));

        return convertView;
    }

}