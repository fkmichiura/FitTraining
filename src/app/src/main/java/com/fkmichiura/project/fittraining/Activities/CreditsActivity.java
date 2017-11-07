package com.fkmichiura.project.fittraining.Activities;

import android.content.Intent;
import android.content.res.TypedArray;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.fkmichiura.project.fittraining.Adapters.ProfileListAdapter;
import com.fkmichiura.project.fittraining.R;

import java.util.ArrayList;

public class CreditsActivity extends AppCompatActivity {

    private ArrayList<Integer> images = new ArrayList<>();
    private ArrayList<String> titles = new ArrayList<>();
    private ArrayList<String> descriptions = new ArrayList<>();
    private ArrayList<String> mails = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credits);

        ListView listView = (ListView)findViewById(R.id.profile_list);

        getData();

        ProfileListAdapter adapter = new ProfileListAdapter(CreditsActivity.this, images, titles, descriptions, mails);
        listView.setAdapter(adapter);


        Button backBtn = (Button)findViewById(R.id.back_btn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(CreditsActivity.this, TitleActivity.class);
                startActivity(intent);

            }
        });
    }

    //Armazena os IDs referentes às imagens dos itens de Treino
    public void getData(){

        TypedArray icons = getResources().obtainTypedArray(R.array.img_profiles_array);
        String[] names = getResources().getStringArray(R.array.profile_name_array);
        String[] functions = getResources().getStringArray(R.array.profession_array);
        String[] emails = getResources().getStringArray(R.array.email_array);

        for(int i = 0; i < names.length; i++){

            int imgId = icons.getResourceId(i, -1);
            images.add(imgId);
            titles.add(names[i]);
            descriptions.add(functions[i]);
            mails.add(emails[i]);
        }

        icons.recycle();
    }
}
