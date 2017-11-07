package com.fkmichiura.project.fittraining.Activities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.fkmichiura.project.fittraining.Models.Athlete;
import com.fkmichiura.project.fittraining.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class UserActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText nameField;
    private EditText gymField;
    private EditText ageField;
    private EditText weightField;
    private EditText evalField;
    private EditText nextEvalField;
    private EditText trainerField;

    private DatePickerDialog fromDatePickerDialog;
    private DatePickerDialog toDatePickerDialog;

    private SimpleDateFormat dateFormatter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        nameField = (EditText)findViewById(R.id.inputName);
        ageField = (EditText)findViewById(R.id.inputAge);
        weightField = (EditText)findViewById(R.id.inputWeight);
        gymField = (EditText)findViewById(R.id.inputGym);
        evalField = (EditText)findViewById(R.id.inputEvalInit);
        nextEvalField = (EditText)findViewById(R.id.inputEvalFinal);
        trainerField = (EditText)findViewById(R.id.inputInstructor);

        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
        setDateTimeField();

        Button regBtn = (Button)findViewById(R.id.regBtn);
        regBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            String strName = nameField.getText().toString();
            String strAge = ageField.getText().toString();
            String strWeight = nameField.getText().toString();
            String strGym = gymField.getText().toString();
            String strEval = evalField.getText().toString();
            String strNextEval = nextEvalField.getText().toString();
            String strInstructor = trainerField.getText().toString();

            if(strName.matches("") || strAge.matches("") || strWeight.matches("") || strGym.matches("") ||
                    strEval.matches("") || strNextEval.matches("") || strInstructor.matches("")){

                Toast.makeText(UserActivity.this, "Favor, preencher todos os campos conforme solicitado",
                        Toast.LENGTH_LONG).show();
            }
            else{
                Athlete athlete = new Athlete(
                        nameField.getText().toString(),
                        Integer.parseInt(ageField.getText().toString()),
                        Float.parseFloat(weightField.getText().toString()),
                        gymField.getText().toString(),
                        evalField.getText().toString(),
                        nextEvalField.getText().toString(),
                        trainerField.getText().toString());
                athlete.save();

                Intent intent = new Intent(UserActivity.this, MenuActivity.class);
                startActivity(intent);
            }
            }
        });
    }

    private void setDateTimeField() {
        evalField.setOnClickListener(this);
        nextEvalField.setOnClickListener(this);

        Calendar newCalendar = Calendar.getInstance();
        fromDatePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                evalField.setText(dateFormatter.format(newDate.getTime()));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        toDatePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                nextEvalField.setText(dateFormatter.format(newDate.getTime()));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){

            case R.id.inputEvalInit:
                fromDatePickerDialog.show();
                break;

            case R.id.inputEvalFinal:
                toDatePickerDialog.show();
                break;
        }
    }
}
