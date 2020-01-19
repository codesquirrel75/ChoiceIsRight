package com.codesquirrel.choiceisright.data;

import android.provider.BaseColumns;

public class ChoicesContract {

    private ChoicesContract(){}

    public static final class ChoiceEntry implements BaseColumns {

        //  constant variable with Table Name
        public static final String CHOICES_TABLE_NAME = "choices";
        public static final String CATEGORIES_TABLE_NAME = "categories";

        // constant variables of column names
        public static final String _ID = BaseColumns._ID;
        public static final String COLUMN_Category = "category";
        public static final String COLUMN_Choice = "choice";


    }
}
