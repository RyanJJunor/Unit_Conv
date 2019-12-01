package uk.ac.stir.cs.yh.rj.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import uk.ac.stir.cs.yh.rj.db.ConversionDatabaseContract.Conversions;

public class ConversionDbMethods {

    private SQLiteDatabase db;


    public ConversionDbMethods(Context context) {
        ConversionDbHelper dbHelper = new ConversionDbHelper(context);
        db = dbHelper.getWritableDatabase();

    }

    public ArrayList<String> selectStatement(boolean distinct, String[] projection, String selection, String[] selectionArgs) {

        Cursor cursor = db.query(
                distinct,
                Conversions.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null,
                null);

        ArrayList<String> units = new ArrayList<>();
        while (cursor.moveToNext()) {
            String itemId = cursor.getString(
                    cursor.getColumnIndexOrThrow(projection[0]));
            units.add(itemId);
        }

        cursor.close();

        return units;
    }

    public int deleteStatement(String selection, String[] selectionArgs) {

        return db.delete(

                Conversions.TABLE_NAME,
                selection,
                selectionArgs
        );
    }

    public long insertStatement(String name, String unit1, String unit2, String unitRate) {
        ContentValues cv = new ContentValues();

        cv.put(Conversions.COLUMN_NAME_PRIMARY_UNIT, unit1);
        cv.put(Conversions.COLUMN_NAME_SECONDARY_UNIT, unit2);
        cv.put(Conversions.COLUMN_NAME_FORMULA, unitRate);
        cv.put(Conversions.COLUMN_NAME_CATEGORY, name);
        cv.put(Conversions.COLUMN_NAME_ADDED_CONVERSION_UNIQUE, name);

        long result = db.insertWithOnConflict(Conversions.TABLE_NAME, null, cv, SQLiteDatabase.CONFLICT_IGNORE);

        if (result == -1)
            return result;

        //todo add a messge to user
        //if row wasn't inserted, because a conversion with that name already exists
        cv.put(Conversions.COLUMN_NAME_PRIMARY_UNIT, unit2);
        cv.put(Conversions.COLUMN_NAME_SECONDARY_UNIT, unit1);
        cv.put(Conversions.COLUMN_NAME_FORMULA, (1 / (Double.parseDouble(unitRate))));
        cv.put(Conversions.COLUMN_NAME_CATEGORY, name);
        cv.remove(Conversions.COLUMN_NAME_ADDED_CONVERSION_UNIQUE);

        db.insert(Conversions.TABLE_NAME, null, cv);

        return result;
    }

}
