package com.fkmichiura.project.fittraining.Fragments;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.appodeal.ads.Appodeal;
import com.fkmichiura.project.fittraining.Models.Athlete;
import com.fkmichiura.project.fittraining.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment implements View.OnClickListener {

    private ImageView editName;
    private ImageView editAge;
    private ImageView editWeight;
    private ImageView editGym;
    private ImageView editEval;
    private ImageView editReval;
    private ImageView editProf;

    private EditText dbName;
    private EditText dbAge;
    private EditText dbWeight;
    private EditText dbGym;
    private TextView dbEval;
    private TextView dbNextEval;
    private EditText dbInstructor;

    private DatePickerDialog fromDatePickerDialog;
    private DatePickerDialog toDatePickerDialog;

    private SimpleDateFormat dateFormatter;

    private Athlete athlete;

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
        dateFormatter.setTimeZone(TimeZone.getTimeZone("Brazil/East"));

        athlete = Athlete.findById(Athlete.class, 1);

        dbName = (EditText)view.findViewById(R.id.dbName);
        dbAge = (EditText)view.findViewById(R.id.dbAge);
        dbWeight = (EditText)view.findViewById(R.id.dbWeight);
        dbGym = (EditText)view.findViewById(R.id.dbGym);
        dbEval = (TextView)view.findViewById(R.id.dbEval);
        dbNextEval = (TextView)view.findViewById(R.id.dbNextEval);
        dbInstructor = (EditText)view.findViewById(R.id.dbInstructor);

        //Botões de Edição dos dados
        editName = (ImageView) view.findViewById(R.id.edit_name_btn);
        editAge = (ImageView) view.findViewById(R.id.edit_age_btn);
        editWeight = (ImageView) view.findViewById(R.id.edit_weight_btn);
        editGym = (ImageView) view.findViewById(R.id.edit_gym_btn);
        editEval = (ImageView) view.findViewById(R.id.edit_eval_btn);
        editReval = (ImageView) view.findViewById(R.id.edit_reval_btn);
        editProf = (ImageView) view.findViewById(R.id.edit_prof_btn);

        //Recuperação dos dados no Banco e atribuição em seus respectivos EditTexts
        dbName.setText(athlete.getName());
        dbAge.setText(String.valueOf(athlete.getAge()));
        dbWeight.setText(String.valueOf(athlete.getWeight()));
        dbGym.setText(athlete.getGym());
        dbEval.setText(athlete.getEvalDate());
        dbNextEval.setText(athlete.getNextEvalDate());
        dbInstructor.setText(athlete.getInstructor());

        setDateTimeField();
        setClickListener();
        setEditorListener();

        //Inicialização do Appodeal Ads
        String appKey = "ca1ded8a1e55c84dddab31fbf2e9f5805c56342d5caafa0e";
        Appodeal.initialize(getActivity(), appKey, Appodeal.BANNER);
        Appodeal.show(getActivity(), Appodeal.BANNER_BOTTOM);
        Appodeal.setTesting(false); //Banner de teste

        // Inflate the layout for this fragment
        return view;
    }

    private void setDateTimeField() {

        Calendar newCalendar = Calendar.getInstance();
        fromDatePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Athlete athlete = Athlete.findById(Athlete.class, 1);

                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                dbEval.setText(dateFormatter.format(newDate.getTime()));

                athlete.setEvalDate(dateFormatter.format(newDate.getTime()));
                athlete.save();
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        toDatePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Athlete athlete = Athlete.findById(Athlete.class, 1);

                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                dbNextEval.setText(dateFormatter.format(newDate.getTime()));

                athlete.setNextEvalDate(dateFormatter.format(newDate.getTime()));
                athlete.save();
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

    }

    public void setClickListener(){

        editName.setOnClickListener(this);
        editAge.setOnClickListener(this);
        editWeight.setOnClickListener(this);
        editGym.setOnClickListener(this);
        editEval.setOnClickListener(this);
        editReval.setOnClickListener(this);
        editProf.setOnClickListener(this);
    }

    public void setEditorListener(){

        dbName.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {

                    athlete.setName(dbName.getText().toString());
                    athlete.save();

                    // hide virtual keyboard
                    hideSoftInput(dbName);
                    return true;
                }
                return false;
            }
        });

        dbAge.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {

                    athlete.setAge(Integer.parseInt(dbAge.getText().toString()));
                    athlete.save();

                    // hide virtual keyboard
                    hideSoftInput(dbAge);
                    return true;
                }
                return false;
            }
        });

        dbWeight.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {

                    athlete.setWeight(Float.parseFloat(dbWeight.getText().toString()));
                    athlete.save();

                    // hide virtual keyboard
                    hideSoftInput(dbWeight);
                    return true;
                }
                return false;
            }
        });

        dbGym.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {

                    athlete.setGym(dbGym.getText().toString());
                    athlete.save();

                    // hide virtual keyboard
                    hideSoftInput(dbGym);
                    return true;
                }
                return false;
            }
        });

        dbInstructor.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {

                    athlete.setInstructor(dbInstructor.getText().toString());
                    athlete.save();

                    // hide virtual keyboard
                    hideSoftInput(dbInstructor);
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){

            case R.id.edit_name_btn:
                dbName.setInputType(InputType.TYPE_CLASS_TEXT);
                showSoftInput(dbName);
                break;

            case R.id.edit_age_btn:
                dbAge.setInputType(InputType.TYPE_CLASS_NUMBER);
                showSoftInput(dbAge);
                break;

            case R.id.edit_weight_btn:
                dbWeight.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
                showSoftInput(dbWeight);
                break;

            case R.id.edit_gym_btn:
                dbGym.setInputType(InputType.TYPE_CLASS_TEXT);
                showSoftInput(dbGym);
                break;

            case R.id.edit_eval_btn:
                fromDatePickerDialog.show();
                break;

            case R.id.edit_reval_btn:
                toDatePickerDialog.show();
                break;

            case R.id.edit_prof_btn:
                dbInstructor.setInputType(InputType.TYPE_CLASS_TEXT);
                showSoftInput(dbInstructor);
                break;
        }
    }

    public void showSoftInput(EditText et){

        et.setCursorVisible(true);
        et.setFocusableInTouchMode(true);
        et.setSelection(et.length());
        et.requestFocus();
        et.setImeOptions(EditorInfo.IME_ACTION_DONE);

        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(et, InputMethodManager.SHOW_IMPLICIT);
    }

    public void hideSoftInput(EditText et){
        InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(et.getWindowToken(), 0);
        et.setCursorVisible(false);
    }
}
