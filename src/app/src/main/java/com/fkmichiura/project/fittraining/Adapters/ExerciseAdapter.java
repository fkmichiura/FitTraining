package com.fkmichiura.project.fittraining.Adapters;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.fkmichiura.project.fittraining.Activities.ExerciseItemActivity;
import com.fkmichiura.project.fittraining.Models.Exercise;
import com.fkmichiura.project.fittraining.R;

import java.util.ArrayList;
import java.util.List;

public class ExerciseAdapter extends RecyclerView.Adapter<ExerciseAdapter.ExerciseViewHolder> {

    private LayoutInflater layoutInflater;
    private List<Exercise> exercData = new ArrayList<>();
    private Context context;
    private int idTraining;

    public ExerciseAdapter(List<Exercise> data, Context context, int index){
        layoutInflater = LayoutInflater.from(context);
        this.exercData = data;
        this.context = context;
        this.idTraining = index;
    }

    @Override
    public ExerciseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.exercise, parent, false);
        return new ExerciseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ExerciseViewHolder holder, final int position) {

        Exercise current = exercData.get(position);

        holder.image.setImageResource(current.getIdImage());
        holder.title.setText(current.getExerciseName());
    }

    @Override
    public int getItemCount() {
        return exercData.size();
    }

    private Long getExerciseId(int position){
        return exercData.get(position).getId();
    }

    class ExerciseViewHolder extends RecyclerView.ViewHolder {

        private ImageView image;
        private TextView title;

        ExerciseViewHolder(View itemView) {
            super(itemView);

            image = (ImageView)itemView.findViewById(R.id.exercise_image);
            title = (TextView)itemView.findViewById(R.id.exercise_title);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, ExerciseItemActivity.class);

                    //Retorna a ID do Exercício, baseado na posição clicada na RecyclerView
                    Long id = getExerciseId(getAdapterPosition());

                    //Passa pra próxima Activity o valor correspondente ao ID Exercício e ID Treino
                    intent.putExtra("exercisePosition", id);
                    intent.putExtra("trainingIndex", idTraining);
                    context.startActivity(intent);
                }
            });

            image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    // custom dialog
                    final Dialog dialog = new Dialog(context);
                    Exercise e = exercData.get(getAdapterPosition());
                    dialog.setContentView(R.layout.image_dialog);

                    Button dialogBtn = (Button) dialog.findViewById(R.id.dialog_btn);
                    ImageView image = (ImageView) dialog.findViewById(R.id.dialog_image);

                    //Atribuição da Imagem na View
                    image.setImageResource(e.getIdImage());

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
}
