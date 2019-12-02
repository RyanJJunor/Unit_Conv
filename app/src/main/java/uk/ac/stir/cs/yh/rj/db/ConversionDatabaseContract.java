package uk.ac.stir.cs.yh.rj.db;

import android.provider.BaseColumns;

public final class ConversionDatabaseContract {

    /**
     * Defines the table and column names
     */
    private ConversionDatabaseContract() {
    }

    public static class Conversions implements BaseColumns {
        static final String TABLE_NAME = "conversion";
        public static final String COLUMN_NAME_PRIMARY_UNIT = "unit_1";
        public static final String COLUMN_NAME_SECONDARY_UNIT = "unit_2";
        public static final String COLUMN_NAME_FORMULA = "formula";
        public static final String COLUMN_NAME_CATEGORY = "category";
        public static final String COLUMN_NAME_ADDED_CONVERSION_UNIQUE = "added";
    }
}
