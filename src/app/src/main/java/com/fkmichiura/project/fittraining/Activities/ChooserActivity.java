package com.fkmichiura.project.fittraining.Activities;

import android.content.Intent;
import android.content.res.TypedArray;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.appodeal.ads.Appodeal;
import com.fkmichiura.project.fittraining.Adapters.CategoryAdapter;
import com.fkmichiura.project.fittraining.Models.Category;
import com.fkmichiura.project.fittraining.Models.Exercise;
import com.fkmichiura.project.fittraining.Models.ExerciseItem;
import com.fkmichiura.project.fittraining.Models.Training;
import com.fkmichiura.project.fittraining.R;
import com.orm.util.NamingHelper;
import com.thoughtbot.expandablecheckrecyclerview.listeners.OnCheckChildClickListener;
import com.thoughtbot.expandablecheckrecyclerview.models.CheckedExpandableGroup;

import java.util.ArrayList;
import java.util.List;

public class ChooserActivity extends AppCompatActivity {

    private List<Category> categories = new ArrayList<>();
    private ArrayList<Exercise> selectedExercises = new ArrayList<>();

    private RecyclerView recyclerView;
    private CategoryAdapter adapter;

    private int trainingIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chooser);

        //Recupera o valor do índice respectivo ao Treino da Fragment Pai
        trainingIndex = getIntent().getIntExtra("trainingIndex", 0);

        //Associa as Views em suas respectivas variáveis
        Button confirmButton = (Button)findViewById(R.id.confirm_button);
        recyclerView = (RecyclerView)findViewById(R.id.activity_chooser_container);

        //Chamada de teste dos métodos de recuperação dos dados dos Exercícios. Obs: Implementar
        //um método otimizado para criação desta Lista
        getCategories();

        if(!categories.isEmpty()){
            setAdapter(categories);
        }

        adapter.setChildClickListener(new OnCheckChildClickListener() {
            @Override
            public void onCheckChildCLick(View v, boolean checked, CheckedExpandableGroup group, int childIndex) {
            Exercise exercise = (Exercise)group.getItems().get(childIndex);

            if(checked){
                selectedExercises.add(exercise);
            }
            else {
                selectedExercises.remove(exercise);
            }
            }
        });

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            removeExercises();

            for(Exercise e: selectedExercises){
                Training training = Training.findById(Training.class, trainingIndex);
                Exercise exercise = new Exercise(e.getIdImage(), e.getExerciseName(), training);
                exercise.save();
            }

            Intent i = new Intent(ChooserActivity.this, MenuActivity.class);
            startActivity(i);
            }
        });

        //Inicialização do Appodeal Ads
        String appKey = "ca1ded8a1e55c84dddab31fbf2e9f5805c56342d5caafa0e";
        Appodeal.initialize(ChooserActivity.this, appKey, Appodeal.BANNER);
        Appodeal.show(ChooserActivity.this, Appodeal.BANNER_BOTTOM);
        Appodeal.setTesting(false); //Banner de teste
    }

    public List<Category> getExercises(){

        ArrayList<Exercise> exercises = new ArrayList<>();
        String[] array = getResources().getStringArray(R.array.exercises_array);
        TypedArray images = getResources().obtainTypedArray(R.array.img_peitoral);

        for (int i = 0; i < array.length; i++){

            int resourceId = images.getResourceId(i, -1);
            exercises.add(new Exercise(resourceId, array[i]));
        }
        categories.add(new Category("Exercícios de Peitoral", exercises));

        images.recycle();
        return categories;
    }

    public List<Category> getExercises2(){

        ArrayList<Exercise> exercises = new ArrayList<>();
        String[] array = getResources().getStringArray(R.array.exercises_array2);
        TypedArray images = getResources().obtainTypedArray(R.array.img_costas);

        for (int i = 0; i < array.length; i++){

            int resourceId = images.getResourceId(i, -1);
            exercises.add(new Exercise(resourceId, array[i]));
        }
        categories.add(new Category("Exercícios de Costas", exercises));

        images.recycle();
        return categories;
    }

    public List<Category> getExercises3(){

        ArrayList<Exercise> exercises = new ArrayList<>();
        String[] array = getResources().getStringArray(R.array.exercises_array3);
        TypedArray images = getResources().obtainTypedArray(R.array.img_ombros);

        for (int i = 0; i < array.length; i++){

            int resourceId = images.getResourceId(i, -1);
            exercises.add(new Exercise(resourceId, array[i]));
        }
        categories.add(new Category("Exercícios de Ombros", exercises));

        images.recycle();
        return categories;
    }

    public List<Category> getExercises4(){

        ArrayList<Exercise> exercises = new ArrayList<>();
        String[] array = getResources().getStringArray(R.array.exercises_array4);
        TypedArray images = getResources().obtainTypedArray(R.array.img_biceps);

        for (int i = 0; i < array.length; i++){

            int resourceId = images.getResourceId(i, -1);
            exercises.add(new Exercise(resourceId, array[i]));
        }
        categories.add(new Category("Exercícios de Bíceps", exercises));

        images.recycle();
        return categories;
    }

    public List<Category> getExercises5(){

        ArrayList<Exercise> exercises = new ArrayList<>();
        String[] array = getResources().getStringArray(R.array.exercises_array5);
        TypedArray images = getResources().obtainTypedArray(R.array.img_triceps);

        for (int i = 0; i < array.length; i++){

            int resourceId = images.getResourceId(i, -1);
            exercises.add(new Exercise(resourceId, array[i]));
        }
        categories.add(new Category("Exercícios de Tríceps", exercises));

        images.recycle();
        return categories;
    }

    public List<Category> getExercises6(){

        ArrayList<Exercise> exercises = new ArrayList<>();
        String[] array = getResources().getStringArray(R.array.exercises_array6);
        TypedArray images = getResources().obtainTypedArray(R.array.img_antebracos);

        for (int i = 0; i < array.length; i++){

            int resourceId = images.getResourceId(i, -1);
            exercises.add(new Exercise(resourceId, array[i]));
        }
        categories.add(new Category("Exercícios de Antebraços", exercises));

        images.recycle();
        return categories;
    }

    public List<Category> getExercises7(){

        ArrayList<Exercise> exercises = new ArrayList<>();
        String[] array = getResources().getStringArray(R.array.exercises_array7);
        TypedArray images = getResources().obtainTypedArray(R.array.img_abdominais);

        for (int i = 0; i < array.length; i++){

            int resourceId = images.getResourceId(i, -1);
            exercises.add(new Exercise(resourceId, array[i]));
        }
        categories.add(new Category("Exercícios de Abdominais", exercises));

        images.recycle();
        return categories;
    }

    public List<Category> getExercises8(){

        ArrayList<Exercise> exercises = new ArrayList<>();
        String[] array = getResources().getStringArray(R.array.exercises_array8);
        TypedArray images = getResources().obtainTypedArray(R.array.img_lombares);

        for (int i = 0; i < array.length; i++){

            int resourceId = images.getResourceId(i, -1);
            exercises.add(new Exercise(resourceId, array[i]));
        }
        categories.add(new Category("Exercícios de Lombares", exercises));

        images.recycle();
        return categories;
    }

    public List<Category> getExercises9(){

        ArrayList<Exercise> exercises = new ArrayList<>();
        String[] array = getResources().getStringArray(R.array.exercises_array9);
        TypedArray images = getResources().obtainTypedArray(R.array.img_quadriceps);

        for (int i = 0; i < array.length; i++){

            int resourceId = images.getResourceId(i, -1);
            exercises.add(new Exercise(resourceId, array[i]));
        }
        categories.add(new Category("Exercícios de Quadríceps", exercises));

        images.recycle();
        return categories;
    }

    public List<Category> getExercises10(){

        ArrayList<Exercise> exercises = new ArrayList<>();
        String[] array = getResources().getStringArray(R.array.exercises_array10);
        TypedArray images = getResources().obtainTypedArray(R.array.img_gluteos);

        for (int i = 0; i < array.length; i++){

            int resourceId = images.getResourceId(i, -1);
            exercises.add(new Exercise(resourceId, array[i]));
        }
        categories.add(new Category("Exercícios de Glúteos", exercises));

        images.recycle();
        return categories;
    }

    public List<Category> getExercises11(){

        ArrayList<Exercise> exercises = new ArrayList<>();
        String[] array = getResources().getStringArray(R.array.exercises_array11);
        TypedArray images = getResources().obtainTypedArray(R.array.img_isquiotibiais);

        for (int i = 0; i < array.length; i++){

            int resourceId = images.getResourceId(i, -1);
            exercises.add(new Exercise(resourceId, array[i]));
        }
        categories.add(new Category("Exercícios de Isquiotibiais", exercises));

        images.recycle();
        return categories;
    }

    public List<Category> getExercises12(){

        ArrayList<Exercise> exercises = new ArrayList<>();
        String[] array = getResources().getStringArray(R.array.exercises_array12);
        TypedArray images = getResources().obtainTypedArray(R.array.img_adutores);

        for (int i = 0; i < array.length; i++){

            int resourceId = images.getResourceId(i, -1);
            exercises.add(new Exercise(resourceId, array[i]));
        }
        categories.add(new Category("Exercícios de Adutores", exercises));

        images.recycle();
        return categories;
    }

    public List<Category> getExercises13(){

        ArrayList<Exercise> exercises = new ArrayList<>();
        String[] array = getResources().getStringArray(R.array.exercises_array13);
        TypedArray images = getResources().obtainTypedArray(R.array.img_abdutores);

        for (int i = 0; i < array.length; i++){

            int resourceId = images.getResourceId(i, -1);
            exercises.add(new Exercise(resourceId, array[i]));
        }
        categories.add(new Category("Exercícios de Abdutores", exercises));

        images.recycle();
        return categories;
    }

    public List<Category> getExercises14(){

        ArrayList<Exercise> exercises = new ArrayList<>();
        String[] array = getResources().getStringArray(R.array.exercises_array14);
        TypedArray images = getResources().obtainTypedArray(R.array.img_panturrilhas);

        for (int i = 0; i < array.length; i++){

            int resourceId = images.getResourceId(i, -1);
            exercises.add(new Exercise(resourceId, array[i]));
        }
        categories.add(new Category("Exercícios de Panturrilhas", exercises));

        images.recycle();
        return categories;
    }

    public List<Category> getCategories(){

        getExercises();
        getExercises2();
        getExercises3();
        getExercises4();
        getExercises5();
        getExercises6();
        getExercises7();
        getExercises8();
        getExercises9();
        getExercises10();
        getExercises11();
        getExercises12();
        getExercises13();
        getExercises14();

        return categories;
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

    public void setAdapter(List<Category> categories){

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        //Desligar o efeito de Flash ao clicar nos itens da ExpandedRecyclerView
        RecyclerView.ItemAnimator animator = recyclerView.getItemAnimator();
        if (animator instanceof DefaultItemAnimator) {
            ((DefaultItemAnimator) animator).setSupportsChangeAnimations(false);
        }

        adapter = new CategoryAdapter(categories);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        adapter.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        adapter.onRestoreInstanceState(savedInstanceState);
    }
}
