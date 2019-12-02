package uk.ac.stir.cs.yh.rj.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import uk.ac.stir.cs.yh.rj.db.ConversionDatabaseContract.Conversions;

/**
 * Contains easily called statements to interact with the database.
 */
public class ConversionDbMethods {

    private SQLiteDatabase db;


    public ConversionDbMethods(Context context) {
        ConversionDbHelper dbHelper = new ConversionDbHelper(context);
        db = dbHelper.getWritableDatabase();

    }

    /**
     * Returns an arraylist populated with the return of the SQLiteDatabase.query() method
     * @param distinct true if you want each row to be unique, false otherwise.
     * @param columns A list of which columns to return. Passing null will return all columns, which is discouraged to prevent reading data from storage that isn't going to be used.
     * @param selection A filter declaring which rows to return, formatted as an SQL WHERE clause (excluding the WHERE itself). Passing null will return all rows for the given table.
     * @param selectionArgs You may include ?s in selection, which will be replaced by the values from selectionArgs, in order that they appear in the selection. The values will be bound as Strings.
     * @return An ArrayList that contains the values returned by the query
     */
    public ArrayList<String> selectStatement(boolean distinct, String[] columns, String selection, String[] selectionArgs) {

        Cursor cursor = db.query(
                distinct,
                Conversions.TABLE_NAME,
                columns,
                selection,
                selectionArgs,
                null,
                null,
                null,
                null);

        ArrayList<String> result = new ArrayList<>();
        while (cursor.moveToNext()) {
            String item = cursor.getString(
                    cursor.getColumnIndexOrThrow(columns[0]));
            result.add(item);
        }

        cursor.close();

        return result;
    }

    /**
     *
     * @param selection The optional WHERE clause to apply when deleting. Passing null will delete
     *                  all rows.
     * @param selectionArgs You may include ?s in the where clause, which will be replaced by the
     *                      values from selectionArgs. The values will be bound as Strings.
     * @return the number of rows affected if a selection is passed in, 0 otherwise. To remove all
     * rows and get a count pass "1" as the whereClause.
     */
    public int deleteStatement(String selection, String[] selectionArgs) {

        return db.delete(

                Conversions.TABLE_NAME,
                selection,
                selectionArgs
        );
    }

    /**
     * Inserts a row in to the database
     * @param name The category/name of the new conversion (this is what it will be under when
     *             selecting the unit type), also this is used in a UNIQUE marked column to allow
     *             the number of added conversions to be calculated with ease
     * @param unit1 The name of the unit that when multiplied by unitRate will give the value of unit2
     * @param unit2 The name of the unit that unit1 is being converted to
     * @param unitRate The number to multiply the value of unit1 by to get unit2
     * @return the row ID of the newly inserted row OR -1 if either the input parameter
     * conflictAlgorithm = CONFLICT_IGNORE or an error occurred.
     */
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

        cv.put(Conversions.COLUMN_NAME_PRIMARY_UNIT, unit2);
        cv.put(Conversions.COLUMN_NAME_SECONDARY_UNIT, unit1);
        // reverses the conversion rate for the other way round
        cv.put(Conversions.COLUMN_NAME_FORMULA, (1 / (Double.parseDouble(unitRate))));
        cv.put(Conversions.COLUMN_NAME_CATEGORY, name);
        cv.remove(Conversions.COLUMN_NAME_ADDED_CONVERSION_UNIQUE);

        db.insert(Conversions.TABLE_NAME, null, cv);

        return result;
    }

}
