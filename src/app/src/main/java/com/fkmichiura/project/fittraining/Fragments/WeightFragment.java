package com.fkmichiura.project.fittraining.Fragments;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.appodeal.ads.Appodeal;
import com.fkmichiura.project.fittraining.Adapters.AthleteItemAdapter;
import com.fkmichiura.project.fittraining.Models.Athlete;
import com.fkmichiura.project.fittraining.Models.AthleteData;
import com.fkmichiura.project.fittraining.Models.CurrentDate;
import com.fkmichiura.project.fittraining.Models.Training;
import com.fkmichiura.project.fittraining.R;
import com.orm.util.NamingHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class WeightFragment extends Fragment {

    private FloatingActionButton fab;
    private TextView registerNotice;

    private RecyclerView recyclerView;
    private AthleteItemAdapter adapter;

    private List<AthleteData> athleteData = new ArrayList<>();
    private CurrentDate date = new CurrentDate();

    public WeightFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_weight, container, false);
        setHasOptionsMenu(true);

        //Associa as Views em suas respectivas variáveis
        recyclerView = (RecyclerView)view.findViewById(R.id.weight_list_container);
        registerNotice = (TextView)view.findViewById(R.id.training_notice);

        //Recupera os registros referentes ao peso do atleta na Tabela
        athleteData = AthleteData.find(AthleteData.class, "athlete = ?", String.valueOf(1));

        //Verifica se a Lista de Dados do Peso está vazia
        if((athleteData.isEmpty())){
            registerNotice.setVisibility(View.VISIBLE);
        }
        else{
            setAdapter(athleteData);
            registerNotice.setVisibility(View.GONE);
        }

        fab = (FloatingActionButton)view.findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddDialog();
            }
        });

        //Inicialização do Appodeal Ads
        String appKey = "ca1ded8a1e55c84dddab31fbf2e9f5805c56342d5caafa0e";
        Appodeal.initialize(getActivity(), appKey, Appodeal.BANNER);
        Appodeal.show(getActivity(), Appodeal.BANNER_BOTTOM);
        Appodeal.setTesting(false); //Banner de teste

        return view;
    }

    private void showAddDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.MyAlertDialogStyle);
        LinearLayout layout = new LinearLayout(getActivity());
        layout.setOrientation(LinearLayout.VERTICAL);
        builder.setTitle("Entrar com o Valor Abaixo");

        final EditText weight = new EditText(getActivity());
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
                    Toast.makeText(getActivity(), "Favor, preencher o campo solicitado", Toast.LENGTH_SHORT).show();
                }
                else {
                    //Captura o valor do peso e salva um novo registro na Tabela AthleteData
                    Athlete athlete = Athlete.findById(Athlete.class, 1);
                    AthleteData data = new AthleteData(Float.parseFloat(athleteWeight), date.getCurrentDate(), date.getCurrentTime(), athlete);
                    data.save();

                    //Recupera os registros referentes ao peso do atleta na Tabela
                    athleteData = AthleteData.find(AthleteData.class, "athlete = ?", String.valueOf(1));
                    setAdapter(athleteData);

                    registerNotice.setVisibility(View.GONE);
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

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.main_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_delete:

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.MyAlertDialogStyle);
                LinearLayout layout = new LinearLayout(getActivity());
                layout.setOrientation(LinearLayout.VERTICAL);
                builder.setTitle("Deseja apagar todos os dados do seu Peso Corporal?");

                // Set up the buttons
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Limpa o conteúdo da ArrayList
                        athleteData.clear();
                        setAdapter(athleteData);

                        //Remove todos os registros da Tabela AthleteData
                        removeWeightData();

                        registerNotice.setVisibility(View.VISIBLE);
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

    public void setAdapter(List<AthleteData> data){

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        adapter = new AthleteItemAdapter(data, getActivity());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    //Remove todos os registros da Tabela Training e zera a chave primária autoincremental
    public void removeWeightData(){

        AthleteData.deleteAll(AthleteData.class);
        Training.executeQuery("DELETE FROM SQLITE_SEQUENCE WHERE NAME = '" + NamingHelper.toSQLName(AthleteData.class) + "'");
    }
}
