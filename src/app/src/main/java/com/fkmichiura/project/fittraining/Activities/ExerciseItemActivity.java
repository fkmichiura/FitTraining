package com.fkmichiura.project.fittraining.Activities;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.appodeal.ads.Appodeal;
import com.fkmichiura.project.fittraining.Adapters.ExerciseItemAdapter;
import com.fkmichiura.project.fittraining.Models.CurrentDate;
import com.fkmichiura.project.fittraining.Models.Exercise;
import com.fkmichiura.project.fittraining.Models.ExerciseItem;
import com.fkmichiura.project.fittraining.R;
import com.orm.util.NamingHelper;

import java.util.ArrayList;
import java.util.List;

public class ExerciseItemActivity extends AppCompatActivity {

    private TextView itemNotice;

    private int idExercise;
    private String exerciseRep;
    private String exerciseWeight;

    private RecyclerView recyclerView;
    private ExerciseItemAdapter adapter;

    private ExerciseItem exerciseItem = new ExerciseItem();

    //Objeto para trabalhar com armazenamento e recuperação da tabela
    private ExerciseItem dbItem;
    private List<ExerciseItem> exerciseItems = new ArrayList<>();

    private CurrentDate date = new CurrentDate();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_item);

        //Associa as Views em suas respectivas variáveis
        recyclerView = (RecyclerView)findViewById(R.id.exercise_item_container);
        ImageView exerciseImage = (ImageView)findViewById(R.id.exercise_image);
        TextView exerciseName = (TextView)findViewById(R.id.exercDBName);
        TextView exerciseSeriesAvg = (TextView)findViewById(R.id.exercDBSeries);
        TextView exerciseRepAvg = (TextView)findViewById(R.id.exercDBRepet);
        TextView exercisePauseAvg = (TextView)findViewById(R.id.exercDBPause);
        itemNotice = (TextView)findViewById(R.id.training_notice);

        Intent intent = getIntent();
        //ID Exercício
        idExercise = (int)intent.getLongExtra("exercisePosition", 0);
        //ID Treino
        int idTraining = intent.getIntExtra("trainingIndex", 0);

        //Recupera o registro do Exercício utilizando os IDs de Treino e Exercício
        final List<Exercise> exercise = Exercise.find(Exercise.class, "training = ? and id = ?", String.valueOf(idTraining), String.valueOf(idExercise));

        //Recupera os dados relativos ao Exercício da classe ExerciseItem
        exerciseImage.setImageResource(exercise.get(0).getIdImage());
        exerciseName.setText(exercise.get(0).getExerciseName());
        exerciseSeriesAvg.setText(String.valueOf(exerciseItem.getSeries()));
        exerciseRepAvg.setText(exerciseItem.getRepetetitionsAvg());
        exercisePauseAvg.setText(exerciseItem.getPauseAvg());

        //Recupera os registros referentes ao peso do exercício e repetições na Tabela
        exerciseItems = ExerciseItem.find(ExerciseItem.class, "exercise = ?", String.valueOf(idExercise));

        if((exerciseItems.isEmpty())){
            itemNotice.setVisibility(View.VISIBLE);
        }
        else{
            //Atribui a Lista de dados ao adaptador da RecyclerView
            setAdapter(exerciseItems);

            itemNotice.setVisibility(View.GONE);
        }

        //Chama a Activity de seleção dos Exercícios
        FloatingActionButton fab = (FloatingActionButton)findViewById(R.id.exFloatingButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });

        //ImageView clicável que chama o diálogo de expandir a imagem do exercício
        exerciseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // custom dialog
                final Dialog dialog = new Dialog(ExerciseItemActivity.this);
                dialog.setContentView(R.layout.image_dialog);

                Button dialogBtn = (Button) dialog.findViewById(R.id.dialog_btn);
                ImageView image = (ImageView) dialog.findViewById(R.id.dialog_image);

                //Atribuição da Imagem na View
                image.setImageResource(exercise.get(0).getIdImage());

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

        //Inicialização do Appodeal Ads
        String appKey = "ca1ded8a1e55c84dddab31fbf2e9f5805c56342d5caafa0e";
        Appodeal.initialize(ExerciseItemActivity.this, appKey, Appodeal.BANNER);
        Appodeal.show(ExerciseItemActivity.this, Appodeal.BANNER_BOTTOM);
        Appodeal.setTesting(false); //Banner de teste
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);//Menu Resource, Menu
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_delete:
                AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.MyAlertDialogStyle);
                LinearLayout layout = new LinearLayout(this);
                layout.setOrientation(LinearLayout.VERTICAL);
                builder.setTitle("Deseja apagar todos dados do seu Exercício?");

                // Set up the buttons
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        exerciseItems.clear();
                        setAdapter(exerciseItems);

                        //Remove todos os registros da Tabela AthleteData
                        removeExerciseItems();

                        itemNotice.setVisibility(View.VISIBLE);
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
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    //Remove todos os registros da Tabela Training e zera a chave primária autoincremental
    public void removeExerciseItems(){

        ExerciseItem.deleteAll(ExerciseItem.class);
        ExerciseItem.executeQuery("DELETE FROM SQLITE_SEQUENCE WHERE NAME = '" + NamingHelper.toSQLName(ExerciseItem.class) + "'");
    }

    public void setAdapter(List<ExerciseItem> data){

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        adapter = new ExerciseItemAdapter(data, this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    private void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(ExerciseItemActivity.this, R.style.MyAlertDialogStyle);
        LinearLayout layout = new LinearLayout(ExerciseItemActivity.this);
        layout.setOrientation(LinearLayout.VERTICAL);
        builder.setTitle("Entrar com os Valores Abaixo");

        final EditText repetitions = new EditText(ExerciseItemActivity.this);
        repetitions.setInputType(InputType.TYPE_CLASS_NUMBER);
        repetitions.setTextColor(Color.WHITE);
        repetitions.setHint("Repetições do exercício");
        layout.addView(repetitions);

        final EditText weight = new EditText(ExerciseItemActivity.this);
        weight.setInputType(InputType.TYPE_CLASS_NUMBER);
        weight.setTextColor(Color.WHITE);
        weight.setHint("Peso no exercício");
        layout.addView(weight);

        // Set up the buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                exerciseRep = repetitions.getText().toString();
                exerciseWeight  = weight.getText().toString();

                if((exerciseRep.matches("")) || (exerciseWeight.matches(""))){
                    Toast.makeText(ExerciseItemActivity.this, "Favor, preencher todos os campos solicitados", Toast.LENGTH_SHORT).show();
                }
                else {
                    //Captura o valor do peso e salva um novo registro na Tabela ExerciseData
                    Exercise exercise = Exercise.findById(Exercise.class, idExercise);
                    dbItem = new ExerciseItem(Integer.parseInt(exerciseRep), Integer.parseInt(exerciseWeight), date.getCurrentDate(), date.getCurrentTime(), exercise);
                    dbItem.save();

                    //Recupera os registros referentes ao peso do exercício e repetições na Tabela
                    exerciseItems = ExerciseItem.find(ExerciseItem.class, "exercise = ?", String.valueOf(idExercise));

                    //Atribui a Lista de dados ao adaptador da RecyclerView
                    setAdapter(exerciseItems);
                    adapter.notifyDataSetChanged();

                    itemNotice.setVisibility(View.GONE);
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
