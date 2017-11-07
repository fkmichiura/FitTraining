package com.fkmichiura.project.fittraining.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.fkmichiura.project.fittraining.Models.Athlete;
import com.fkmichiura.project.fittraining.R;

public class TitleActivity extends AppCompatActivity {

    private Athlete athlete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_title);

        athlete = Athlete.findById(Athlete.class, 1);

        Log.i("TAG", "Athlete Null? " + athlete);

        Button userBtn = (Button)findViewById(R.id.user_btn);
        userBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!(athlete == null)){
                    Intent intent = new Intent(TitleActivity.this, MenuActivity.class);
                    startActivity(intent);
                }
                else{
                    Intent intent = new Intent(TitleActivity.this, UserActivity.class);
                    startActivity(intent);
                }
            }
        });


        Button creditsBtn = (Button)findViewById(R.id.credits_btn);
        creditsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            Intent intent = new Intent(TitleActivity.this, CreditsActivity.class);
            startActivity(intent);

            }
        });
    }
}
