package com.codesquirrel.choiceisright.data;

import android.provider.BaseColumns;

public class CategoriesContract {

    private CategoriesContract(){}

    public static final class CategoryEntry implements BaseColumns {

        //  constant variable with Table Name
        public static final String TABLE_NAME = "categories";

        // constant variables of column names
        public static final String _ID = BaseColumns._ID;
        public static final String COLUMN_Category = "category";


    }
}
