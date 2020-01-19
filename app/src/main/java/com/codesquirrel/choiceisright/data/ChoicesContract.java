package com.codesquirrel.choiceisright.data;

import android.provider.BaseColumns;

public class ChoicesContract {

    private ChoicesContract(){}

    public static final class ChoiceEntry implements BaseColumns {

        //  constant variable with Table Name
        public static final String TABLE_NAME = "choices";

        // constant variables of column names
        public static final String _ID = BaseColumns._ID;
        public static final String COLUMN_Choice_Catagory = "catagory_id";
        public static final String COLUMN_Choice = "Choice";


    }
}
