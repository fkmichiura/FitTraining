package com.fkmichiura.project.fittraining.Adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.fkmichiura.project.fittraining.Models.ExerciseItem;
import com.fkmichiura.project.fittraining.R;

import java.util.ArrayList;
import java.util.List;

public class ExerciseItemAdapter extends RecyclerView.Adapter<ExerciseItemAdapter.ExerciseViewHolder> {

    private LayoutInflater layoutInflater;
    private List<ExerciseItem> data = new ArrayList<>();
    private Context context;

    public ExerciseItemAdapter(List<ExerciseItem> data, Context ctx){
        layoutInflater = LayoutInflater.from(ctx);
        this.data = data;
        this.context = ctx;
    }

    @Override
    public ExerciseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.exercise_item_row, parent, false);
        return new ExerciseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ExerciseViewHolder holder, final int position) {

        ExerciseItem current = data.get(position);
        String exerciseWeight = String.valueOf(current.getWeight()) + " Kg";

        holder.date.setText(current.getCurrentDate());
        holder.time.setText(current.getCurrentTime());
        holder.repetitions.setText(String.valueOf(current.getRepetitions()));
        holder.weight.setText(exerciseWeight);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class ExerciseViewHolder extends RecyclerView.ViewHolder {

        private TextView date;
        private TextView time;
        private TextView repetitions;
        private TextView weight;
        private Button editBtn;

        ExerciseViewHolder(View itemView) {
            super(itemView);

            date = (TextView) itemView.findViewById(R.id.DBdate);
            time = (TextView) itemView.findViewById(R.id.DBtime);
            repetitions = (TextView) itemView.findViewById(R.id.DBrepetitions);
            weight = (TextView)itemView.findViewById(R.id.DBweight);
            editBtn = (Button) itemView.findViewById(R.id.edit_btn);

            editBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showAddDialog(getAdapterPosition(), repetitions, weight);
                }
            });
        }
    }

    private void showAddDialog(int position, final TextView tv, final TextView tv2) {
        final int pos = position + 1;

        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.MyAlertDialogStyle);
        LinearLayout layout = new LinearLayout(context);
        layout.setOrientation(LinearLayout.VERTICAL);
        builder.setTitle("Entrar com os Novos Valores Abaixo");

        final EditText exerciseRep = new EditText(context);
        exerciseRep.setInputType(InputType.TYPE_CLASS_NUMBER);
        exerciseRep.setTextColor(Color.WHITE);
        exerciseRep.setHint("Repetições do exercício");
        layout.addView(exerciseRep);

        final EditText exerciseWeight = new EditText(context);
        exerciseWeight.setInputType(InputType.TYPE_CLASS_NUMBER);
        exerciseWeight.setTextColor(Color.WHITE);
        exerciseWeight.setHint("Peso no exercício");
        layout.addView(exerciseWeight);

        // Set up the buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            String exRep = exerciseRep.getText().toString();
            String exWeight = exerciseWeight.getText().toString();

            if((exRep.matches("")) || (exWeight.matches(""))){
                Toast.makeText(context, "Favor, preencher todos os campos solicitados", Toast.LENGTH_SHORT).show();
            }
            else {
                //Captura o valor do peso e salva um novo registro na Tabela ExerciseData
                ExerciseItem dbItem = ExerciseItem.findById(ExerciseItem.class, pos);
                dbItem.setRepetitions(Integer.parseInt(exRep));
                dbItem.setWeight(Integer.parseInt(exWeight));
                dbItem.save();

                //Recupera os registros referentes ao peso do exercício e repetições na Tabela
                tv.setText(String.valueOf(dbItem.getRepetitions()));
                String value = dbItem.getWeight() + " Kg";
                tv2.setText(String.valueOf(value));
            }
            }
        });

        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.setView(layout);
        builder.show();
    }
}