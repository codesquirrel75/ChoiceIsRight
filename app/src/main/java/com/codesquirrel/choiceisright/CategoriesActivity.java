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
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;



import com.codesquirrel.choiceisright.data.ChoicesContract;
import com.codesquirrel.choiceisright.data.ChoicesDBHelper;

import java.sql.Array;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CategoriesActivity extends AppCompatActivity {

    @BindView(R.id.category_name_input)
    EditText categoryNameInput;

   @BindView(R.id.categories_total_text_view)
   TextView categoriesTotalTextView;

  // @BindView(R.id.categories_list_view)
  // ListView categoriesListView;

   @BindView(R.id.categories_spinner)
   Spinner categoriesSpinner;






    public static TextView data;
    public static ArrayList CategoriesList;
   // public static String CategoriesList = "No Categories loaded";
    public static String postData;
    SQLiteDatabase db;
    public ArrayList categories = new ArrayList<String>();
    public int size;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);
        ButterKnife.bind(this);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        // set up Database connection
        ChoicesDBHelper mDbHelper = new ChoicesDBHelper(this);
        db = mDbHelper.getWritableDatabase();

        getCategories();
        setCategoriesTotal();
        setCategoriesSpinner();
       // setCategoriesList();

    }

    public void getCategories(){

        categories = ChoicesDBHelper.getCategories(db);

    }

    public void setCategoriesTotal(){

        if(!categories.isEmpty()){
            size = categories.size();
        }else{
            size = 0;
        }

        categoriesTotalTextView.setText(Integer.toString(size));

    }

    public void setCategoriesSpinner(){

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

   /* public void setCategoriesList(){

        // create list view adapter
        ArrayAdapter ListViewAdapter = new ArrayAdapter<ArrayList>
                (CategoriesActivity.this,android.R.layout.simple_list_item_1, categories);
        //set Categories List View
        categoriesListView.setAdapter(ListViewAdapter);

    } */


    public void saveCategory(View view){


        String categoryName = categoryNameInput.getText().toString();
        ContentValues values = new ContentValues();

        values.put(ChoicesContract.ChoiceEntry.COLUMN_Category, categoryName);

        ChoicesDBHelper.setCategories(db, values);
        categoryNameInput.setText("");

        getCategories();
        setCategoriesTotal();
        setCategoriesSpinner();
       // setCategoriesList();

    }

    public void deleteCategory(View view){
        String cat = categoriesSpinner.getSelectedItem().toString();

        ChoicesDBHelper.deleteCategories(db, cat);

        getCategories();
        setCategoriesTotal();
        setCategoriesSpinner();

    }

    @Override
    protected void onStop() {
        super.onStop();
        db.close();
    }

}
