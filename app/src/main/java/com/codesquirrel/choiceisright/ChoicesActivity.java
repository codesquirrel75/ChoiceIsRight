package com.codesquirrel.choiceisright;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;



import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.codesquirrel.choiceisright.data.ChoicesContract;
import com.codesquirrel.choiceisright.data.ChoicesDBHelper;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ChoicesActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    @BindView(R.id.categories_spinner)
    Spinner categoriesSpinner;

    @BindView(R.id.remove_option_categories_spinner)
    Spinner removeOptionCategoriesSpinner;

    @BindView(R.id.options_spinner)
    Spinner optionsSpinner;

    @BindView(R.id.option_input)
    EditText optionInput;




    public ArrayList categories = new ArrayList<String>();
    public ArrayList options = new ArrayList<String>();
    SQLiteDatabase db;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choices);
        ButterKnife.bind(this);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // set up Database connection
        ChoicesDBHelper mDbHelper = new ChoicesDBHelper(this);
        db = mDbHelper.getWritableDatabase();

        getCategories();
        setCategoriesSpinner();
        setRemoveOptionCategoriesSpinner();


        if(ChoicesDBHelper.TableExists(db, ChoicesContract.ChoiceEntry.CHOICES_TABLE_NAME)) {

            getOptions();

        }


        if(options.size() > 0){

            setOptionSpinner();
            optionsSpinner.setVisibility(View.VISIBLE);

        }else {

            optionsSpinner.setVisibility(View.INVISIBLE);

        }




    }



    public void setCategoriesSpinner(){

        //  --- Categories spinner ---

        Spinner categorySpinner = (Spinner) findViewById(R.id.categories_spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter categoriesadapter = new ArrayAdapter<String>(ChoicesActivity.this,
                android.R.layout.simple_spinner_dropdown_item, categories);
        // Specify the layout to use when the list of choices appears
        categoriesadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        categorySpinner.setAdapter(categoriesadapter);

    }

    public void setRemoveOptionCategoriesSpinner(){

        //  --- Categories spinner ---

        Spinner removeOptionCategorySpinner = (Spinner) findViewById(R.id.remove_option_categories_spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter removeoptionscategoriesadapter = new ArrayAdapter<String>(ChoicesActivity.this,
                android.R.layout.simple_spinner_dropdown_item, categories);
        // Specify the layout to use when the list of choices appears
        removeoptionscategoriesadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        removeOptionCategorySpinner.setAdapter(removeoptionscategoriesadapter);
        removeOptionCategorySpinner.setOnItemSelectedListener(this);

    }

    public void getCategories(){

        categories = ChoicesDBHelper.getCategories(db);

    }

    public void setOptionSpinner(){

        //  --- Categories spinner ---

        Spinner categorySpinner = (Spinner) findViewById(R.id.options_spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter optionsadapter = new ArrayAdapter<String>(ChoicesActivity.this,
                android.R.layout.simple_spinner_dropdown_item, options);
        // Specify the layout to use when the list of choices appears
        optionsadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        categorySpinner.setAdapter(optionsadapter);

    }
    public void getOptions(){

        String cat = removeOptionCategoriesSpinner.getSelectedItem().toString();


        options = ChoicesDBHelper.getChoices(db, cat);

    }

    public void saveOption(View view){

        String cat = categoriesSpinner.getSelectedItem().toString();
        String option = optionInput.getText().toString();
        ContentValues values = new ContentValues();
        values.put(ChoicesContract.ChoiceEntry.COLUMN_Category, cat);
        values.put(ChoicesContract.ChoiceEntry.COLUMN_Choice, option);

        ChoicesDBHelper.setChoices(db, values);
        optionInput.setText("");

        getOptions();
        setOptionSpinner();

    }

    public void deleteOption(View view) {
        String option = optionsSpinner.getSelectedItem().toString();
        ChoicesDBHelper.deleteChoice(db, option);

        getOptions();
        setOptionSpinner();
    }


    @Override
    protected void onStop() {
        super.onStop();
        db.close();
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        getOptions();
        setOptionSpinner();
        optionsSpinner.setVisibility(View.VISIBLE);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
