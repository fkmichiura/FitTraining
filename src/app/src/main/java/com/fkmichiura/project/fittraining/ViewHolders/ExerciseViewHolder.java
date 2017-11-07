package com.fkmichiura.project.fittraining.ViewHolders;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.Checkable;
import android.widget.CheckedTextView;
import android.widget.ImageView;
import android.widget.TextView;

import com.fkmichiura.project.fittraining.R;
import com.thoughtbot.expandablecheckrecyclerview.viewholders.CheckableChildViewHolder;

/**
 * Created by DarKnigHt on 11/05/2017.
 */

public class ExerciseViewHolder extends CheckableChildViewHolder {

    private ImageView exerciseImage;
    private CheckedTextView exerciseTitle;
    private Context context;

    public ExerciseViewHolder(View itemView) {
        super(itemView);

        exerciseImage = (ImageView)itemView.findViewById(R.id.exercise_image);
        exerciseTitle = (CheckedTextView)itemView.findViewById(R.id.exercise_title);
    }

    @Override
    public Checkable getCheckable() {
        return exerciseTitle;
    }

    public void setExercise(final int idImage, String title){
        exerciseImage.setImageResource(idImage);
        exerciseTitle.setText(title);

        exerciseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // custom dialog
                context = v.getContext();
                final Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.image_dialog);

                Button dialogBtn = (Button) dialog.findViewById(R.id.dialog_btn);
                ImageView image = (ImageView) dialog.findViewById(R.id.dialog_image);

                //Atribuição da Imagem na View
                image.setImageResource(idImage);

                // if button is clicked, close the custom dialog
                dialogBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                dialog.show();
            }
        });
    }
}