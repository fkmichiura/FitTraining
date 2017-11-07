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

import com.fkmichiura.project.fittraining.Models.AthleteData;
import com.fkmichiura.project.fittraining.R;

import java.util.ArrayList;
import java.util.List;

public class AthleteItemAdapter extends RecyclerView.Adapter<AthleteItemAdapter.AthleteViewHolder> {

    private LayoutInflater layoutInflater;
    private List<AthleteData> data = new ArrayList<>();
    private Context context;

    public AthleteItemAdapter(List<AthleteData> data, Context ctx){
        layoutInflater = LayoutInflater.from(ctx);
        this.data = data;
        this.context = ctx;
    }

    @Override
    public AthleteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.weight_row, parent, false);
        return new AthleteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AthleteViewHolder holder, final int position) {

        AthleteData current = data.get(position);
        String athleteWeight = String.valueOf(current.getAthleteWeight()) + " Kg";

        holder.weight.setText(athleteWeight);
        holder.date.setText(current.getDate());
        holder.time.setText(current.getTime());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class AthleteViewHolder extends RecyclerView.ViewHolder {

        private TextView weight;
        private TextView date;
        private TextView time;
        private Button icon;

        AthleteViewHolder(View itemView) {
            super(itemView);

            weight = (TextView)itemView.findViewById(R.id.DBw_weight);
            date = (TextView) itemView.findViewById(R.id.DBw_date);
            time = (TextView) itemView.findViewById(R.id.DBw_time);
            icon = (Button) itemView.findViewById(R.id.edit_btn);

            icon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showAddDialog(getAdapterPosition(), weight);
                }
            });
        }
    }

    private void showAddDialog(int position, final TextView tv) {
        final int pos = position + 1;

        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.MyAlertDialogStyle);
        LinearLayout layout = new LinearLayout(context);
        layout.setOrientation(LinearLayout.VERTICAL);
        builder.setTitle("Entrar com o Novo Valor Abaixo");

        final EditText weight = new EditText(context);
        weight.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        weight.setTextColor(Color.WHITE);
        weight.setHint("Peso Corporal");
        layout.addView(weight);

        // Set up the buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                String athleteWeight  = weight.getText().toString();

                if((athleteWeight.matches(""))){
                    Toast.makeText(context, "Favor, preencher o campo solicitado", Toast.LENGTH_SHORT).show();
                }
                else {
                    //Captura o valor do peso e salva um novo registro na Tabela AthleteData
                    AthleteData data = AthleteData.findById(AthleteData.class, pos);
                    data.setAthleteWeight(Float.parseFloat(athleteWeight));
                    data.save();

                    tv.setText(String.valueOf(data.getAthleteWeight() + " Kg"));
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