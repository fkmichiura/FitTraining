package com.fkmichiura.project.fittraining.Activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.appodeal.ads.Appodeal;
import com.fkmichiura.project.fittraining.Adapters.ExerciseAdapter;
import com.fkmichiura.project.fittraining.Models.Exercise;
import com.fkmichiura.project.fittraining.Models.ExerciseItem;
import com.fkmichiura.project.fittraining.R;
import com.orm.util.NamingHelper;

import java.util.ArrayList;
import java.util.List;

public class ExerciseListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private TextView textView;

    private int trainingIndex;

    private List<Exercise> exercisesList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise);

        //Recupera o valor do índice respectivo ao Treino da Fragment Pai
        trainingIndex = getIntent().getIntExtra("trainingIndex", 0);

        //Associa as Views em suas respectivas variáveis
        recyclerView = (RecyclerView)findViewById(R.id.activity_exercise_container);
        textView = (TextView)findViewById(R.id.noticeTv);

        //Recupera os registros vinculados ao Tipo de Treino
        exercisesList = Exercise.find(Exercise.class, "training = ?", String.valueOf(trainingIndex));

        if(exercisesList.isEmpty()){
            textView.setVisibility(View.VISIBLE);
        }
        else{
            setAdapter(exercisesList, this, trainingIndex);

            textView.setVisibility(View.GONE);
        }

        //Chama a Activity de seleção dos Exercícios
        FloatingActionButton fab = (FloatingActionButton)findViewById(R.id.exFloatingButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
        public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(ExerciseListActivity.this, R.style.MyAlertDialogStyle);
                LinearLayout layout = new LinearLayout(ExerciseListActivity.this);
                layout.setOrientation(LinearLayout.VERTICAL);
                builder.setTitle("Deseja selecionar novos exercícios para o Treino?");

                // Set up the buttons
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Intent intent = new Intent(ExerciseListActivity.this, ChooserActivity.class);
                        intent.putExtra("trainingIndex", trainingIndex);
                        startActivity(intent);
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
        });

        //Inicialização do Appodeal Ads
        String appKey = "ca1ded8a1e55c84dddab31fbf2e9f5805c56342d5caafa0e";
        Appodeal.initialize(ExerciseListActivity.this, appKey, Appodeal.BANNER);
        Appodeal.show(ExerciseListActivity.this, Appodeal.BANNER_BOTTOM);
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
                builder.setTitle("Deseja apagar todos os seus Exercícios?");

                // Set up the buttons
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        exercisesList.clear();
                        removeExercises();

                        setAdapter(exercisesList, ExerciseListActivity.this, trainingIndex);

                        textView.setVisibility(View.VISIBLE);
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

    //Remove todos os registros da Tabela Exercise e zera a chave primária autoincremental
    public void removeExercises(){
        //Recupera os registros referentes ao peso do exercício e repetições na Tabela
        List<Exercise> exercises = Exercise.find(Exercise.class, "training = ?", String.valueOf(trainingIndex));

        for(Exercise e : exercises){
            Long id = e.getId();
            ExerciseItem.deleteAll(ExerciseItem.class, "exercise = ?", String.valueOf(id));
        }
        ExerciseItem.executeQuery("DELETE FROM SQLITE_SEQUENCE WHERE NAME = '" + NamingHelper.toSQLName(ExerciseItem.class) + "'");

        Exercise.deleteAll(Exercise.class, "training = ?", String.valueOf(trainingIndex));
        Exercise.executeQuery("DELETE FROM SQLITE_SEQUENCE WHERE NAME = '" + NamingHelper.toSQLName(Exercise.class) + "'");
    }

    public void setAdapter(List<Exercise> list, Context context, int index){

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        ExerciseAdapter adapter = new ExerciseAdapter(list, context, index);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

}
