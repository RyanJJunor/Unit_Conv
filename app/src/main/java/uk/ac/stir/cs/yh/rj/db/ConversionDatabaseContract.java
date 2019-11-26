package uk.ac.stir.cs.yh.rj.db;

import android.provider.BaseColumns;

import uk.ac.stir.cs.yh.rj.SharedViewModel;

public final class ConversionDatabaseContract {

    private ConversionDatabaseContract(){
    }

    public static class Conversions implements BaseColumns {
        public static final String TABLE_NAME = "conversion";
        public static final String COLUMN_NAME_NAME = "name";
        public static final String COLUMN_NAME_PRIMARY_UNIT = "unit_1";
        public static final String COLUMN_NAME_SECONDARY_UNIT = "unit_2";
        public static final String COLUMN_NAME_FORMULA = "formula";
        public static final String COLUMN_NAME_CATEGORY = "category";
    }
}
