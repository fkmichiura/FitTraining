package com.fkmichiura.project.fittraining.ViewHolders;

import android.view.View;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.fkmichiura.project.fittraining.R;
import com.thoughtbot.expandablerecyclerview.viewholders.GroupViewHolder;

import static android.view.animation.Animation.RELATIVE_TO_SELF;

/**
 * Created by DarKnigHt on 11/05/2017.
 */

public class CategoryViewHolder extends GroupViewHolder {

    private ImageView arrow;
    private TextView categoryTitle;

    public CategoryViewHolder(View itemView) {
        super(itemView);

        arrow = (ImageView)itemView.findViewById(R.id.arrow_expand);
        categoryTitle = (TextView)itemView.findViewById(R.id.category_title);
    }

    public void setCategoryTitle(String title){
        categoryTitle.setText(title);
    }

}
