package com.fkmichiura.project.fittraining.Fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.appodeal.ads.Appodeal;
import com.fkmichiura.project.fittraining.Activities.ExerciseListActivity;
import com.fkmichiura.project.fittraining.Adapters.ListAdapter;
import com.fkmichiura.project.fittraining.Models.Athlete;
import com.fkmichiura.project.fittraining.Models.Exercise;
import com.fkmichiura.project.fittraining.Models.ExerciseItem;
import com.fkmichiura.project.fittraining.Models.Training;
import com.fkmichiura.project.fittraining.R;
import com.orm.util.NamingHelper;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class TrainingFragment extends Fragment{

    private TextView trainingNotice;
    private ListView listView;
    private ListAdapter listAdapter;

    private int counter = 0;
    private int[] arrayIcons = new int[]{R.drawable.ic_training, R.drawable.ic_training, R.drawable.ic_training, R.drawable.ic_training, R.drawable.ic_training};

    private ArrayList<Integer> images = new ArrayList<>();
    private ArrayList<String> titles = new ArrayList<>();
    private ArrayList<String> descriptions = new ArrayList<>();

    public TrainingFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_training, container, false);

        trainingNotice = (TextView)view.findViewById(R.id.training_notice);
        listView = (ListView)view.findViewById(R.id.listView);

        //Carrega o número de treinos criados
        SharedPreferences preferences = getActivity().getSharedPreferences("pref", Context.MODE_PRIVATE);
        counter = preferences.getInt("list_counter", 0);

        images = getIcons(counter);
        titles = getTitlesTable(counter);
        descriptions = getDescrTable(counter);

        if((!images.isEmpty()) || (!titles.isEmpty()) || (!descriptions.isEmpty())){
            trainingNotice.setVisibility(View.GONE);

            listAdapter = new ListAdapter(getActivity(), images, titles);
            listView.setAdapter(listAdapter);
        }
        else {
            trainingNotice.setVisibility(View.VISIBLE);
        }

        FloatingActionButton fab = (FloatingActionButton)view.findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.MyAlertDialogStyle);
            LinearLayout layout = new LinearLayout(getActivity());
            layout.setOrientation(LinearLayout.VERTICAL);
            builder.setTitle("Deseja criar um novo tipo de Treino?");

            // Set up the buttons
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    //Criar caixa de Diálogo
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.Theme_AppCompat_Dialog_Alert);
                    builder.setTitle("Selecione o tipo de Treino")
                        .setItems(R.array.training_array, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // The 'which' argument contains the index position
                                // of the selected item

                                if(!(which == 0) || !(which == 1) || !(which == 2) || !(which == 3)){
                                    removeTrainings();
                                }

                                counter = which+2;

                                saveTrainings(counter);

                                //Salva o número de treinos criados para carregamento posterior
                                SharedPreferences preferences = getActivity().getSharedPreferences("pref", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = preferences.edit();
                                editor.putInt("list_counter", counter);
                                editor.commit();

                                images = getIcons(counter);
                                titles = getTitlesTable(counter);
                                descriptions = getDescrTable(counter);

                                listAdapter = new ListAdapter(getActivity(), images, titles);
                                listView.setAdapter(listAdapter);

                                trainingNotice.setVisibility(View.GONE);

                            }
                        });
                        AlertDialog alertDialog = builder.create();
                        ListView listView = alertDialog.getListView();
                        listView.setDivider(new ColorDrawable(Color.GREEN)); // set color
                        listView.setDividerHeight(1); // set height
                        alertDialog.show();
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

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //Cria Intent para chamar a próxima Activity
                Intent intent = new Intent(getActivity(), ExerciseListActivity.class);
                intent.putExtra("trainingIndex", position+1);
                startActivity(intent);
            }
        });

        //Inicialização do Appodeal Ads
        String appKey = "ca1ded8a1e55c84dddab31fbf2e9f5805c56342d5caafa0e";
        Appodeal.initialize(getActivity(), appKey, Appodeal.BANNER);
        Appodeal.show(getActivity(), Appodeal.BANNER_BOTTOM);
        Appodeal.setTesting(false); //Banner de teste

        // Inflate the layout for this fragment
        return view;
    }

    //Armazena os registros dos treinos na tabela Training
    public void saveTrainings(int counter){

        String[] arrayTitles = getResources().getStringArray(R.array.training_type_array);
        String[] arrayDescriptions = getResources().getStringArray(R.array.training_desc_array);

        //Atribui o objeto treino à classe Athlete
        Athlete athlete = Athlete.findById(Athlete.class, 1);

        for (int i = 0; i < counter; i++) {
            Training training = new Training(arrayTitles[i], arrayDescriptions[i], athlete);
            training.save();
        }
    }

    //Armazena os IDs referentes às imagens dos itens de Treino
    public ArrayList<Integer> getIcons(int count){

        ArrayList<Integer> icons = new ArrayList<>();

        for(int i = 0; i < count; i++){
            icons.add(arrayIcons[i]);
        }
        return icons;
    }

    //Recupera os dados relativos aos Títulos dos treinos na tabela Training
    public ArrayList<String> getTitlesTable(int count){

        ArrayList<String> titles = new ArrayList<>();

        for(int i = 1; i <= count; i++){
            Training training = Training.findById(Training.class, i);
            if(training != null){
                titles.add(training.getTrainingName());
            }
            else {
                break;
            }
        }
        return titles;
    }

    //Recupera os dados relativos às Descrições dos treinos na tabela Training
    public ArrayList<String> getDescrTable(int count){

        ArrayList<String> descriptions = new ArrayList<>();

        for(int i = 1; i <= count; i++){
            Training training = Training.findById(Training.class, i);
            if(training != null) {
                descriptions.add(training.getTrainingDesc());
            }
            else {
                break;
            }
        }
        return descriptions;
    }

    //Remove todos os registros da Tabela Training e zera a chave primária autoincremental
    public void removeTrainings(){

        Training.deleteAll(Training.class);
        Exercise.deleteAll(Exercise.class);
        ExerciseItem.deleteAll(ExerciseItem.class);
        Training.executeQuery("DELETE FROM SQLITE_SEQUENCE WHERE NAME = '" + NamingHelper.toSQLName(Training.class) + "'");
        Exercise.executeQuery("DELETE FROM SQLITE_SEQUENCE WHERE NAME = '" + NamingHelper.toSQLName(Exercise.class) + "'");
        ExerciseItem.executeQuery("DELETE FROM SQLITE_SEQUENCE WHERE NAME = '" + NamingHelper.toSQLName(ExerciseItem.class) + "'");
    }
}
