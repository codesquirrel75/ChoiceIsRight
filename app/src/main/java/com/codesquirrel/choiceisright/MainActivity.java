package com.codesquirrel.choiceisright;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.codesquirrel.choiceisright.data.ChoicesDBHelper;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {



    @BindView(R.id.categories_spinner)
    Spinner categoriesSpinner;


    SQLiteDatabase db;
    public ArrayList categories = new ArrayList<String>();
    public ArrayList options = new ArrayList<String>();
    String[] data = new String[options.size()];


    public Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // set up Database connection
        ChoicesDBHelper mDbHelper = new ChoicesDBHelper(this);
        db = mDbHelper.getWritableDatabase();


        getCategories();
        setCategoriesSpinner();



    }

    public void getCategories(){

        categories = ChoicesDBHelper.getCategories(db);

    }

    public void setCategoriesSpinner(){

        //  --- Categories spinner ---

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter categoriesadapter = new ArrayAdapter<String>(MainActivity.this,
                android.R.layout.simple_spinner_dropdown_item, categories);
        // Specify the layout to use when the list of choices appears
        categoriesadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        categoriesSpinner.setAdapter(categoriesadapter);

    }

    public void getOptions(){

        String cat = categoriesSpinner.getSelectedItem().toString();

        options = ChoicesDBHelper.getChoices(db, cat);

    }



    public void moveToWheel(View view){

        getOptions();
        intent = new Intent(MainActivity.this, OptionWheel.class);
        intent.putExtra("options", options);
        startActivity(intent);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_add_categories) {
            startActivity(new Intent(this, CategoriesActivity.class));
            return true;
        }else if (id == R.id.action_add_choices) {
            startActivity(new Intent(this, ChoicesActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
