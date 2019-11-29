package uk.ac.stir.cs.yh.rj.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import uk.ac.stir.cs.yh.rj.db.ConversionDatabaseContract.Conversions;


public class ConversionDbHelper extends SQLiteOpenHelper {
    public static final int DATABASE_Version = 1;
    public static final String DATABASE_NAME = "Conversions.db";

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE IF NOT EXISTS " + Conversions.TABLE_NAME + " (" +
                    Conversions._ID + " INTEGER PRIMARY KEY," +
                    Conversions.COLUMN_NAME_PRIMARY_UNIT + " TEXT," +
                    Conversions.COLUMN_NAME_SECONDARY_UNIT + " TEXT," +
                    Conversions.COLUMN_NAME_FORMULA + " TEXT," +
                    Conversions.COLUMN_NAME_CATEGORY + " TEXT)";

    private static final String GRAM_TO_KILOGRAM = "('Gram', 'Kilogram', '0.001', 'Mass')";
    private static final String GRAM_TO_POUND = "('Gram', 'Pound', '0.00220462', 'Mass')";
    private static final String GRAM_TO_STONE = "('Gram', 'Stone', '0.000157473', 'Mass')";

    private static final String KILOGRAM_TO_GRAM = "('Kilogram', 'Gram', '1000', 'Mass')";
    private static final String KILOGRAM_TO_POUND = "('Kilogram', 'Pound', '2.20462', 'Mass')";
    private static final String KILOGRAM_TO_STONE = "('Kilogram', 'Stone', '0.157473', 'Mass')";

    private static final String POUND_TO_GRAM = "('Pound', 'Gram', '453.592', 'Mass')";
    private static final String POUND_TO_KILOGRAM = "('Pound', 'Kilogram', '0.453592', 'Mass')";
    private static final String POUND_TO_STONE = "('Pound', 'Stone', '0.0714286', 'Mass')";

    private static final String STONE_TO_GRAM = "('Stone', 'Gram', '6350.293', 'Mass')";
    private static final String STONE_TO_KILOGRAM = "('Stone', 'Kilogram', '6.35029', 'Mass')";
    private static final String STONE_TO_POUND = "('Stone', 'Pound', '14', 'Mass')";


    private static final String METRE_TO_KILOMETRE = "('Metre', 'Kilometre', '0.001', 'Distance')";
    private static final String METRE_TO_FOOT = "('Metre', 'Foot', '3.28084', 'Distance')";
    private static final String METRE_TO_MILE = "('Metre', 'Mile', '0.000621371', 'Distance')";

    private static final String KILOMETRE_TO_METRE = "('Kilometre', 'Metre', '1000', 'Distance')";
    private static final String KILOMETRE_TO_FOOT = "('Kilometre', 'Foot', '3280.84', 'Distance')";
    private static final String KILOMETRE_TO_MILE = "('Kilometre', 'Mile', '0.621371', 'Distance')";

    private static final String FOOT_TO_METRE = "('Foot', 'Metre', '0.3048', 'Distance')";
    private static final String FOOT_TO_KILOMETRE = "('Foot', 'Kilometre', '0.0003048', 'Distance')";
    private static final String FOOT_TO_MILE = "('Foot', 'Mile', '0.000189394', 'Distance')";

    private static final String MILE_TO_METRE = "('Mile', 'Metre', '1609.34', 'Distance')";
    private static final String MILE_TO_KILOMETRE = "('Mile', 'Kilometre', '1.60934', 'Distance')";
    private static final String MILE_TO_FOOT = "('Mile', 'Foot', '5280', 'Distance')";


    private static final String MILLILITRE_TO_LITRE = "('Millilitre', 'Litre', '0.001', 'Volume')";
    private static final String MILLILITRE_TO_CUP = "('Millilitre', 'Cup', '0.00351951', 'Volume')";
    private static final String MILLILITRE_TO_PINT = "('Millilitre', 'Pint', '0.00175975', 'Volume')";

    private static final String LITRE_TO_MILLILITRE = "('Litre', 'Millilitre', '1000', 'Volume')";
    private static final String LITRE_TO_CUP = "('Litre', 'Cup', '3.51951', 'Volume')";
    private static final String LITRE_TO_PINT = "('Litre', 'Pint', '1.75975', 'Volume')";

    private static final String CUP_TO_MILLILITRE = "('Cup', 'Millilitre', '284.131', 'Volume')";
    private static final String CUP_TO_LITRE = "('Cup', 'Litre', '0.284131', 'Volume')";
    private static final String CUP_TO_PINT = "('Cup', 'Pint', '0.5', 'Volume')";

    private static final String PINT_TO_MILLILITRE = "('Pint', 'Millilitre', '568.261', 'Volume')";
    private static final String PINT_TO_LITRE = "('Pint', 'Litre', '0.568261', 'Volume')";
    private static final String PINT_TO_CUP = "('Pint', 'Cup', '2', 'Volume')";


    private static final String SECOND_TO_MINUTE = "('Second', 'Minute', '0.0166667', 'Time')";
    private static final String SECOND_TO_HOUR = "('Second', 'Hour', '0.000277778', 'Time')";
    private static final String SECOND_TO_DAY = "('Second', 'Day', '0.000011574', 'Time')";

    private static final String MINUTE_TO_SECOND = "('Minute', 'Second', '60', 'Time')";
    private static final String MINUTE_TO_HOUR = "('Minute', 'Hour', '0.0166667', 'Time')";
    private static final String MINUTE_TO_DAY = "('Minute', 'Day', '0.000694444', 'Time')";

    private static final String HOUR_TO_SECOND = "('Hour', 'Second', '3600', 'Time')";
    private static final String HOUR_TO_MINUTE = "('Hour', 'Minute', '60', 'Time')";
    private static final String HOUR_TO_DAY = "('Hour', 'Day', '0.0416667', 'Time')";

    private static final String DAY_TO_SECOND = "('Day', 'Second', '86400', 'Time')";
    private static final String DAY_TO_MINUTE = "('Day', 'Minute', '1440', 'Time')";
    private static final String DAY_TO_HOUR = "('Day', 'Hour', '24', 'Time')";


    private static final String SQL_POPULATE_ENTRIES =
            "INSERT INTO " + Conversions.TABLE_NAME + " (" +
                    Conversions.COLUMN_NAME_PRIMARY_UNIT + "," +
                    Conversions.COLUMN_NAME_SECONDARY_UNIT + "," +
                    Conversions.COLUMN_NAME_FORMULA + ", " +
                    Conversions.COLUMN_NAME_CATEGORY + ") " +
                    "VALUES " +
                    GRAM_TO_KILOGRAM + "," +
                    GRAM_TO_POUND + "," +
                    GRAM_TO_STONE + "," +
                    KILOGRAM_TO_GRAM + "," +
                    KILOGRAM_TO_POUND + "," +
                    KILOGRAM_TO_STONE + "," +
                    POUND_TO_GRAM + "," +
                    POUND_TO_KILOGRAM + "," +
                    POUND_TO_STONE + "," +
                    STONE_TO_GRAM + "," +
                    STONE_TO_KILOGRAM + "," +
                    STONE_TO_POUND + "," +
                    METRE_TO_KILOMETRE + "," +
                    METRE_TO_FOOT + "," +
                    METRE_TO_MILE + "," +
                    KILOMETRE_TO_METRE + "," +
                    KILOMETRE_TO_FOOT + "," +
                    KILOMETRE_TO_MILE + "," +
                    FOOT_TO_METRE + "," +
                    FOOT_TO_KILOMETRE + "," +
                    FOOT_TO_MILE + "," +
                    MILE_TO_METRE + "," +
                    MILE_TO_KILOMETRE + "," +
                    MILE_TO_FOOT + "," +
                    MILLILITRE_TO_LITRE + "," +
                    MILLILITRE_TO_CUP + "," +
                    MILLILITRE_TO_PINT + "," +
                    LITRE_TO_MILLILITRE + "," +
                    LITRE_TO_CUP + "," +
                    LITRE_TO_PINT + "," +
                    CUP_TO_MILLILITRE + "," +
                    CUP_TO_LITRE + "," +
                    CUP_TO_PINT + "," +
                    PINT_TO_MILLILITRE + "," +
                    PINT_TO_LITRE + "," +
                    PINT_TO_CUP + "," +
                    SECOND_TO_MINUTE + "," +
                    SECOND_TO_HOUR + "," +
                    SECOND_TO_DAY + "," +
                    MINUTE_TO_SECOND + "," +
                    MINUTE_TO_HOUR + "," +
                    MINUTE_TO_DAY + "," +
                    HOUR_TO_SECOND + "," +
                    HOUR_TO_MINUTE + "," +
                    HOUR_TO_DAY + "," +
                    DAY_TO_SECOND + "," +
                    DAY_TO_MINUTE + "," +
                    DAY_TO_HOUR;

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + Conversions.TABLE_NAME;

    public ConversionDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_Version);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
        db.execSQL(SQL_POPULATE_ENTRIES);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    //todo put all statements here? Use Threads

}
