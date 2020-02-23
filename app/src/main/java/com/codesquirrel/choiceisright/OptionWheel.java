package com.codesquirrel.choiceisright;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;


import android.util.Log;
import android.view.View;
import android.widget.NumberPicker;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OptionWheel extends AppCompatActivity {

    @BindView(R.id.selection_wheel)
    NumberPicker picker;

    @BindView(R.id.final_choice)
    TextView finalChoice;


    public ArrayList options = new ArrayList<String>();
    String[] data = new String[options.size()];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_option_wheel);
        ButterKnife.bind(this);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        options = intent.getStringArrayListExtra("options");
        String Tag = "OPTION WHEEL ACTIVITY: ";

        setData();
        picker.setMinValue(0);
        picker.setMaxValue(data.length-1);
        picker.setDisplayedValues(data);


        picker.setOnScrollListener((numberPicker, i) -> {
           if(i == 2)
           {
               int n = new Random().nextInt(data.length);

               picker.setVisibility(View.INVISIBLE);
               finalChoice.setVisibility(View.VISIBLE);
               Log.d(Tag, "final Choice is " + data[n]);


                   setFinalChoice(n);

           }
        });


    }

    public void setData(){

        data = (String[]) options.toArray(new String[options.size()]);

    }

    public void setFinalChoice(int n) {

        finalChoice.setText(data[n]);
    }





}
