package com.codesquirrel.choiceisright.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.codesquirrel.choiceisright.data.ChoicesContract.ChoiceEntry;

import java.util.ArrayList;


public class ChoicesDBHelper extends SQLiteOpenHelper {


    public static final String TAG = ChoicesDBHelper.class.getSimpleName();
    private static final String DATABASE_NAME = "Choices.db";
    private static final int DATABASE_VERSION = 1;


    public ChoicesDBHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

        Log.d(TAG, "ChoicesDBHelper: Database Created.");
    }



    @Override
    public void onCreate(SQLiteDatabase db) {

        createTables(db);

    }

    public static void createTables(SQLiteDatabase db){

        String sql_create_categories_table = "CREATE TABLE "+ ChoiceEntry.CATEGORIES_TABLE_NAME +"("
                + ChoiceEntry._ID +" Integer PRIMARY KEY AUTOINCREMENT, "
                + ChoiceEntry.COLUMN_Category + " text NOT NULL);";


        db.execSQL(sql_create_categories_table);

        String sql_create_choices_table = "CREATE TABLE "+ ChoiceEntry.CHOICES_TABLE_NAME +"("
                + ChoiceEntry._ID +" Integer PRIMARY KEY AUTOINCREMENT, "
                + ChoiceEntry.COLUMN_Category + " text NOT NULL,"
                + ChoiceEntry.COLUMN_Choice +" text NOT NULL);";

        db.execSQL(sql_create_choices_table);

    }

    public static void dropChoicesTable(SQLiteDatabase db){

        String sql_drop_table = "DROP TABLE if EXISTS " + ChoiceEntry.CHOICES_TABLE_NAME + ";";


        db.execSQL(sql_drop_table);

    }

    public static void dropCategoriesTable(SQLiteDatabase db){


        String sql_drop_table = "DROP TABLE if EXISTS " + ChoiceEntry.CATEGORIES_TABLE_NAME + ";";


        db.execSQL(sql_drop_table);

    }


    public static void setCategories(SQLiteDatabase db, ContentValues values){


        long newIndex = db.insert(ChoiceEntry.CATEGORIES_TABLE_NAME, null, values);

    }

    public static ArrayList<String> getCategories(SQLiteDatabase db){

        ArrayList<String> result = new ArrayList();

        Cursor cursor = db.rawQuery("SELECT "+ ChoiceEntry.COLUMN_Category + " FROM "+ ChoiceEntry.CATEGORIES_TABLE_NAME +";",null);

        int numberColumn = cursor.getColumnIndex(ChoiceEntry.COLUMN_Category);

        cursor.moveToFirst();

        if(cursor != null && (cursor.getCount() > 0)){

            int i = 0;

            do {

                result.add(cursor.getString(numberColumn));

            }while (cursor.moveToNext());

        }

        return result;

    }


    public static void setChoices(SQLiteDatabase db, ContentValues values){


        long newIndex = db.insert(ChoiceEntry.CHOICES_TABLE_NAME, null, values);

    }

    public static ArrayList<String> getChoices(SQLiteDatabase db, String cat){

        ArrayList<String> result = new ArrayList();

        Cursor cursor = db.rawQuery("SELECT "+ ChoiceEntry.COLUMN_Choice + " FROM "+ ChoiceEntry.CHOICES_TABLE_NAME +" WHERE " + ChoiceEntry.CATEGORIES_TABLE_NAME + " = ?;",new String[] {cat});

        int numberColumn = cursor.getColumnIndex(ChoiceEntry.COLUMN_Choice);

        cursor.moveToFirst();

        if(cursor != null && (cursor.getCount() > 0)){

            int i = 0;

            do {

                result.add(cursor.getString(numberColumn));

            }while (cursor.moveToNext());

        }

        return result;

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }


}
