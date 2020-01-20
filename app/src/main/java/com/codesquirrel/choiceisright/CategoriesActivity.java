package com.codesquirrel.choiceisright;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.codesquirrel.choiceisright.data.ChoicesContract;
import com.codesquirrel.choiceisright.data.ChoicesDBHelper;

import java.util.ArrayList;

public class CategoriesActivity extends AppCompatActivity {

    TextView categoriesTotal;
    public static TextView data;
    public static String CategoriesList = "No Categories loaded";
    public static String postData;
    SQLiteDatabase db;
    public ArrayList<String> categories = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveCategory();
            }
        });

        // set up Database connection
        ChoicesDBHelper mDbHelper = new ChoicesDBHelper(this);
        db = mDbHelper.getWritableDatabase();
        categories = ChoicesDBHelper.getCategories(db);

        if (!categories.isEmpty()){
            CategoriesList = "";
            for(int i =0; i < categories.size(); i++){

                CategoriesList = CategoriesList + categories.get(i) + "\n";
            }
        }
        int size;
        if(categories.size() > 0){
            size = categories.size();
        }else{
            size = 0;
        }
        categoriesTotal = findViewById(R.id.categories_total);
        categoriesTotal.setText(size);

        //  --- Categories spinner ---

        Spinner categorySpinner = (Spinner) findViewById(R.id.categories_spinner);
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter categoriesadapter = new ArrayAdapter<String>(CategoriesActivity.this,
                android.R.layout.simple_spinner_dropdown_item, categories);
// Specify the layout to use when the list of choices appears
        categoriesadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        categorySpinner.setAdapter(categoriesadapter);

    }



    public void saveCategory(){

        EditText category = findViewById(R.id.category_name_input);
        String categoryName = category.getText().toString();
        ContentValues values = new ContentValues();

        values.put(ChoicesContract.ChoiceEntry.COLUMN_Category, categoryName);

        ChoicesDBHelper.setCategories(db, values);

    }



}
